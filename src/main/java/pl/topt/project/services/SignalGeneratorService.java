package pl.topt.project.services;

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

import static pl.topt.project.constants.Constants.WebConstants.BITS_TO_SHOW;

/**
 * Created by dominik on 30.10.17.
 */
@Component
public class SignalGeneratorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SignalGeneratorService.class);

    public BinaryData generateBinaryData(int bits) {
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
                arguments, binarySequence.subList(0, BITS_TO_SHOW));
        binaryData.setBinarySignal(signalData);
        return binaryData;
    }

    public Signal generateSignal(BinaryData binaryData, SimulationParametersForm parametersForm) {

        Signal signal;
        Pulse pulse = createPulse(parametersForm.getPulseType(), parametersForm.getIsiRate());
        ArgumentRange arguments = ArgumentRange.createArgumentRangeForParameters(
                InterferedPulse.MIN, InterferedPulse.MAX, PulseArgument.STEP);
        signal = generateSignalData(pulse.getValuesForArgumentRange(arguments, true),
                arguments, binaryData.getBinarySequence());


        if (parametersForm.isAddNoise()) {
            signal = addNoiseToSignalWithSNR(signal, parametersForm.getNoiseSNR());
        }

        LOGGER.info("Pulse width: {}", SignalUtils.calculatePulseWidth(signal));

        return signal;
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

    private Pulse createPulse(Constants.PulseType pulseType, double isiRate) {
        if (Constants.PulseType.GAUSSIAN.equals(pulseType)) {
            return GaussianPulse.createGaussianPulseForExpectedValueAndStandardDeviation(5, 1.5, isiRate);
        } else if (Constants.PulseType.LORENTZIAN.equals(pulseType)) {
            return LorentzianPulse.createLorentzianPulseForHalfWidthAndMean(0.5, 5, isiRate);
        } else if (Constants.PulseType.RAISED_COSINE.equals(pulseType)) {
            return RaisedCosinePulse.createRaisedCosinePulseForBandwidthAndMean(0.2, 5, isiRate);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private Signal generateSignalData(List<Double> pulse, ArgumentRange arguments, List<Boolean> binarySequence) {

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
