package pl.topt.project.services;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.topt.project.constants.Constants;
import pl.topt.project.constants.Constants.PulseArgument;
import pl.topt.project.constants.Constants.PulseArgument.InterferedPulse;
import pl.topt.project.data.*;
import pl.topt.project.forms.SimulationParametersForm;
import pl.topt.project.utils.SignalUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.math3.util.FastMath.pow;
import static org.apache.commons.math3.util.FastMath.sqrt;
import static pl.topt.project.constants.Constants.WebConstants.BITS_TO_SHOW;

/**
 * Created by dominik on 30.10.17.
 */
@Component
public class SignalGeneratorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SignalGeneratorService.class);

    public BinaryData generateBinaryData(int bits) {
        Preconditions.checkArgument(bits > 0);

        Random random = new Random();
        BinaryData binaryData = new BinaryData();
        List<Boolean> binarySequence = new ArrayList<>(bits);
        ArgumentRange arguments = ArgumentRange.createArgumentRangeForParameters(
                InterferedPulse.MIN, InterferedPulse.MAX, PulseArgument.STEP);

        for (int i = 0; i < bits; ++i) {
            binarySequence.add(random.nextBoolean());
        }

        binaryData.setBinarySequence(binarySequence);
        Signal signalData = generateSignalData(new RectangularPulse().getValuesForArgumentRange(arguments, false),
                arguments, binarySequence.subList(0, BITS_TO_SHOW), false);
        binaryData.setBinarySignal(signalData);
        binaryData.setNumberOfBits(bits);
        return binaryData;
    }

    public Signal generateSignal(BinaryData binaryData, SimulationParametersForm parametersForm) {

        Signal signal;
        double pulseWidth = 0;
        if (parametersForm.isAddIsi()) {
            pulseWidth = calculatePulseWidthForIsiRate(parametersForm.getIsiRate());
        }
        Pulse pulse = createPulse(parametersForm.getPulseType(), pulseWidth);
        ArgumentRange arguments = ArgumentRange.createArgumentRangeForParameters(
                InterferedPulse.MIN, InterferedPulse.MAX, PulseArgument.STEP);
        signal = generateSignalData(pulse.getValuesForArgumentRange(arguments, true),
                arguments, binaryData.getBinarySequence(), true);


        if (parametersForm.isAddNoise()) {
            signal = addNoiseToSignalWithSNR(signal, parametersForm.getNoiseSNR());
        }

        return signal;
    }

    private double calculatePulseWidthForIsiRate(double isiRate) {
        double extensionFactor = sqrt(1 + pow(isiRate, 2));
        return (PulseArgument.CleanPulse.MAX - PulseArgument.CleanPulse.MIN) * extensionFactor;
    }

    private Signal addNoiseToSignalWithSNR(final Signal signal, double snr) {
        double signalRms = SignalUtils.calculateSignalRms(signal);
        double noisePower = SignalUtils.calculateNoisePowerForSNR(signalRms, snr);
        LOGGER.info("Signal power: {}", signalRms);

        List<Double> noise = generateWhiteNoise(signal.getArguments(), noisePower);
        List<Double> noisedValues = addNoiseToSignal(noise, signal.getValues());

        LOGGER.info("Real SNR: {}", SignalUtils.calculateSNR(signal.getValues(), noise));
        return Signal.createSignalForValuesAndArguments(noisedValues, signal.getArguments());
    }

    private List<Double> addNoiseToSignal(List<Double> noise, List<Double> signalValues) {
        List<Double> noisedValues = new ArrayList<>();
        noisedValues.addAll(signalValues);

        for (int i = 0; i < noisedValues.size(); ++i) {
            noisedValues.set(i, noise.get(i) + noisedValues.get(i));
        }

        return noisedValues;
    }

    private List<Double> generateWhiteNoise(List<Double> arguments, double noisePower) {
        int numberOfArguments = arguments.size();
        List<Double> gaussianNoiseValues = new ArrayList<>(numberOfArguments);

        Random random = new Random();
        for (int i = 0; i < numberOfArguments; ++i) {
            gaussianNoiseValues.add(noisePower * random.nextGaussian()); //random.nextGaussian() * Math.sqrt(variance) + mean; - possible variant for adjust SNR
        }

        LOGGER.info("Generated noise power: {}",
                SignalUtils.calculateSignalRms(Signal.createSignalForValuesAndArguments(gaussianNoiseValues, arguments)));
        return gaussianNoiseValues;
    }

    private Pulse createPulse(Constants.PulseType pulseType, double pulseWidth) {
        LOGGER.info("Selected pulse: {}", pulseType);
        if (Constants.PulseType.GAUSSIAN.equals(pulseType)) {
            double standardDeviation = pulseWidth == 0 ? 1.5 : GaussianPulse.calculateStandardDeviationForPulseWidth(pulseWidth);
            return GaussianPulse.createGaussianPulseForExpectedValueAndStandardDeviation(5, standardDeviation);
        } else if (Constants.PulseType.LORENTZIAN.equals(pulseType)) {
            double halfWidth = pulseWidth == 0 ? 0.5 : LorentzianPulse.calculatehalfWidthForPulseWidth(pulseWidth);
            return LorentzianPulse.createLorentzianPulseForHalfWidthAndMean(halfWidth, 5);

        } else if (Constants.PulseType.RAISED_COSINE.equals(pulseType)) {
            double bandwidth = pulseWidth == 0 ? 0.2 : RaisedCosinePulse.calculateBandwidthForPulseWidth(pulseWidth);
            return RaisedCosinePulse.createRaisedCosinePulseForBandwidthAndMean(bandwidth, 5);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private Signal generateSignalData(List<Double> pulse, ArgumentRange arguments, List<Boolean> binarySequence, boolean withIsi) {

        LOGGER.info("Pulse width: {}", SignalUtils.calculatePulseWidth(pulse, arguments.getArguments()));

        int sequenceSize = binarySequence.size();
        List<Double> completeSignalValues = initializeCompleteSignalValues(sequenceSize * pulse.size() + pulse.size() * 2);
        List<Double> completeSignalArguments = ArgumentRange.createArgumentRangeForParameters(
                0, completeSignalValues.size() / PulseArgument.STEP, PulseArgument.STEP).getArguments();

        int currentPosition = 0;
        int bitSize = arguments.getArguments().size();

        for (boolean bit : binarySequence) {
            if (bit) {
                addPulse(pulse, completeSignalValues, currentPosition);
            }

            currentPosition += bitSize / InterferedPulse.ISI_RATE;
        }

        return Signal.createSignalForValuesAndArguments(completeSignalValues, completeSignalArguments);

    }

    private void addPulse(List<Double> pulse, List<Double> values, int startPosition) {

        for (int i = 0; i < pulse.size(); ++i) {
            double currentValue = values.get(startPosition + i);
            values.set(startPosition + i, currentValue + pulse.get(i));
        }
    }

    private List<Double> initializeCompleteSignalValues(int numberOfValues) {
        return Stream.generate(() -> 0D).limit(numberOfValues).collect(Collectors.toCollection(LinkedList::new));
    }

}
