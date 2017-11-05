package pl.topt.project.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.topt.project.constants.Constants;
import pl.topt.project.data.*;
import pl.topt.project.forms.SimulationParametersForm;
import pl.topt.project.utils.SignalUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        for (int i = 0; i < bits; ++i) {
            binarySequence.add(random.nextBoolean());
        }

        binaryData.setBinarySequence(binarySequence);
        return binaryData;
    }

    public Signal generateSignal(BinaryData binaryData, SimulationParametersForm parametersForm) {

        Constants.PulseType pulseType = parametersForm.getPulseType();
        Signal signal;

        if (Constants.PulseType.GAUSSIAN.equals(pulseType)) {
            signal = generateGaussianBinarySignal(binaryData);
        } else if (Constants.PulseType.LORENTZIAN.equals(pulseType)) {
            signal = generateLorentzianBinarySignal(binaryData);
        } else if (Constants.PulseType.RAISED_COSINE.equals(pulseType)) {
            signal = generateRaisedCosineBinarySignal(binaryData);
        } else {
            throw new IllegalArgumentException();
        }

        if (parametersForm.isAddNoise()) {
            signal = addNoiseToSignalWithSNR(signal, parametersForm.getNoiseSNR());
        }

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

    private Signal generateGaussianBinarySignal(BinaryData binaryData) {
        ArgumentRange arguments = ArgumentRange.createArgumentRangeForParameters(
                Constants.PulseArgument.MIN, Constants.PulseArgument.MAX, Constants.PulseArgument.STEP);
        List<Double> pulse = GaussianPulse.createGaussianPulseForExpectedValueAndStandardDeviation(5, 1.5)
                .getValuesForArgumentRange(arguments);
        List<Double> zeroPulse = new ZeroPulse().getValuesForArgumentRange(arguments);

        return generateSignalData(pulse, zeroPulse, arguments, binaryData.getBinarySequence());

    }

    private Signal generateLorentzianBinarySignal(BinaryData binaryData) {
        ArgumentRange arguments = ArgumentRange.createArgumentRangeForParameters(
                Constants.PulseArgument.MIN, Constants.PulseArgument.MAX, Constants.PulseArgument.STEP);
        List<Double> pulse = LorentzianPulse.createLorentzianPulseForHalfWidthAndMean(0.5, 5)
                .getValuesForArgumentRange(arguments);
        List<Double> zeroPulse = new ZeroPulse().getValuesForArgumentRange(arguments);

        return generateSignalData(pulse, zeroPulse, arguments, binaryData.getBinarySequence());

    }

    private Signal generateRaisedCosineBinarySignal(BinaryData binaryData) {
        ArgumentRange arguments = ArgumentRange.createArgumentRangeForParameters(
                Constants.PulseArgument.MIN, Constants.PulseArgument.MAX, Constants.PulseArgument.STEP);
        List<Double> pulse = RaisedCosinePulse.createRaisedCosinePulseForBandwidthAndMean(0.2, 5)
                .getValuesForArgumentRange(arguments);
        List<Double> zeroPulse = new ZeroPulse().getValuesForArgumentRange(arguments);

        return generateSignalData(pulse, zeroPulse, arguments, binaryData.getBinarySequence());

    }

    private Signal generateSignalData(List<Double> pulse, List<Double> zeroPulse, ArgumentRange arguments,
                                      List<Boolean> binarySequence) {

        int sequenceSize = binarySequence.size();
        List<Double> completeSignalValues = new ArrayList<>(sequenceSize * pulse.size());
        List<Double> completeSignalArguments = new ArrayList<>(sequenceSize * arguments.getArguments().size());

        binarySequence.forEach(
                bit -> {
                    if (bit) {
                        completeSignalValues.addAll(pulse);
                    } else {
                        completeSignalValues.addAll(zeroPulse);
                    }
                    completeSignalArguments.addAll(arguments.getArguments());
                    arguments.shiftArgumentsOneTime();
                });

        return Signal.createSignalForValuesAndArguments(completeSignalValues, completeSignalArguments);
    }
}
