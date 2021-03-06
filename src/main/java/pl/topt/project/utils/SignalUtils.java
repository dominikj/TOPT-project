package pl.topt.project.utils;

import com.google.common.base.Preconditions;
import org.apache.commons.math3.util.Precision;
import pl.topt.project.constants.Constants.PulseArgument.CleanPulse;
import pl.topt.project.constants.Constants.PulseArgument.InterferedPulse;
import pl.topt.project.data.Signal;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.math3.util.FastMath.*;
import static pl.topt.project.constants.Constants.PulseArgument.STEP;

/**
 * Created by dominik on 31.10.17.
 */
public class SignalUtils {

    public static Signal scaleAndRoundData(Double scaleFactor, int numberOfDigits, Signal signal) {
        Preconditions.checkArgument(scaleFactor != 0D);
        Signal refactoredSignal = new Signal();
        refactoredSignal.setValues(scaleAndRound(scaleFactor, numberOfDigits, signal.getValues()));
        refactoredSignal.setArguments(scaleAndRound(scaleFactor, numberOfDigits, signal.getArguments()));
        return refactoredSignal;
    }

    public static Signal limitData(int bits, Signal signal) {
        int numberOfSamples = calculateNumerOfSamples(bits);
        double pulseCenter = (InterferedPulse.MAX - InterferedPulse.MIN) / 2;
        double cleanPulseWidth = CleanPulse.MAX - CleanPulse.MIN;
        int figureOffset = (int) ((pulseCenter - cleanPulseWidth) / STEP);
        int sampleOffset = (int) ((pulseCenter - (cleanPulseWidth / 2)) / STEP);
        return Signal.createSignalForValuesAndArguments(signal.getValues().subList(figureOffset, sampleOffset + numberOfSamples),
                signal.getArguments().subList(figureOffset, sampleOffset + numberOfSamples));
    }

    private static int calculateNumerOfSamples(int bits) {
        int bitSize = (int) ((CleanPulse.MAX - CleanPulse.MIN) / STEP);
        return (bits) * bitSize;
    }

    public static final double calculateSignalRms(Signal signal) {
        double euclideanVectorNorm = calculateEuclideanVectorNorm(signal.getValues());

        return euclideanVectorNorm / sqrt(signal.getValues().size());
    }

    public static final double calculateSNR(List<Double> signal, List<Double> noise) {
        double signalRms = calculateSignalRms(Signal.createSignalForValuesAndArguments(signal, null));
        double noiseRms = calculateSignalRms(Signal.createSignalForValuesAndArguments(noise, null));
        return 20 * log10(signalRms / noiseRms);
    }

    public static final double calculateNoisePowerForSNR(double signalRms, double snr) {
        double denominator = pow(10, snr / 20);
        return signalRms / denominator;
    }

    public static final double calculatePulseWidth(List<Double> pulse, List<Double> arguments) {
        int sampleStart = 0, sampleStop = 0;

        for (int i = 0; i < pulse.size(); ++i) {
            if (pulse.get(i) >= 0.5) {
                sampleStart = i;
                break;
            }
        }
        for (int i = sampleStart + 1; i < pulse.size(); ++i) {
            if (pulse.get(i) <= 0.5) {
                sampleStop = i;
                break;
            }
        }

        return arguments.get(sampleStop) - arguments.get(sampleStart);
    }

    private static double calculateEuclideanVectorNorm(List<Double> vector) {
        double euclideanNorm = 0;

        for (Double value : vector) {
            euclideanNorm += pow(value, 2);
        }

        return sqrt(euclideanNorm);
    }

    private static List<Double> scaleAndRound(Double scaleFactor, int numberOfDigits, List<Double> data) {
        return data.stream()
                .map(arg -> scaleFactor * Precision.round(arg, numberOfDigits)).collect(Collectors.toList());
    }
}
