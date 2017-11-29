package pl.topt.project.services;

import org.springframework.stereotype.Component;
import pl.topt.project.constants.Constants.PulseArgument.CleanPulse;
import pl.topt.project.constants.Constants.PulseArgument.InterferedPulse;
import pl.topt.project.data.Signal;
import pl.topt.project.data.builders.SimulationDataResponseBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.math3.util.FastMath.*;
import static pl.topt.project.constants.Constants.PulseArgument.STEP;

/**
 * Created by dominik on 20.11.17.
 */
@Component
public class SignalMeasureService {

    private static final double TRESHOLD_LEVEL = 0.5;

    public SimulationDataResponseBuilder measureSignal(Signal signal) {
        List<Double> receivedData = samplingSignal(signal);
        return calculateElectricalSignalToNoiseRatio(receivedData);
    }

    public double calculateReceiverSensitivityLoss(double isiRate) {
        return 5 * log10(1 + 2 * Math.PI * pow(isiRate, 2));
    }

    private List<Double> samplingSignal(Signal signal) {
        double pulseCenter = (InterferedPulse.MAX - InterferedPulse.MIN) / 2;
        int signalOffset = (int) (pulseCenter / STEP);
        double pulseStep = (CleanPulse.MAX - CleanPulse.MIN) / STEP;
        List<Double> samples = new ArrayList<>();
        List<Double> signalValues = signal.getValues();

        for (int i = signalOffset; i < signalValues.size(); i += pulseStep) {
            samples.add(signalValues.get(i));
        }

        return samples;
    }

    //Calculate Q parameter
    private SimulationDataResponseBuilder calculateElectricalSignalToNoiseRatio(List<Double> samples) {

        List<Double> highValueSamples = samples.stream()
                .filter(sample -> sample > TRESHOLD_LEVEL)
                .collect(Collectors.toList());
        List<Double> lowValueSamples = samples.stream()
                .filter(sample -> sample <= TRESHOLD_LEVEL)
                .collect(Collectors.toList());

        double highValueMean = calculateMeanValue(highValueSamples);
        double lowValueMean = calculateMeanValue(lowValueSamples);
        double highValueStandardDeviation = calculateStandardDeviationEstimator(highValueSamples, highValueMean);
        double lowValueStandardDeviation = calculateStandardDeviationEstimator(lowValueSamples, lowValueMean);

        double qParameter = (highValueMean - lowValueMean) / (lowValueStandardDeviation + highValueStandardDeviation);

        return new SimulationDataResponseBuilder()
                .addHighMeanValue(highValueMean)
                .addLowMeanValue(lowValueMean)
                .addHighStandardDeviation(highValueStandardDeviation)
                .addLowStandardDeviation(lowValueStandardDeviation)
                .addBitErrorRate(calculateBitErrorRate(qParameter))
                .addQParameter(qParameter);
    }

    private double calculateMeanValue(List<Double> samples) {
        double valuesSum = samples.stream().mapToDouble(value -> value).sum();
        return valuesSum / samples.size();
    }

    private double calculateStandardDeviationEstimator(List<Double> samples, double meanValue) {
        double sum = samples.stream().mapToDouble(sample -> pow(sample - meanValue, 2)).sum();
        double factor = 1D / (samples.size() - 1);

        return sqrt(sum * factor);
    }

    private double calculateBitErrorRate(double qParameter) {
        double numerator = pow(Math.E, (-pow(qParameter, 2)) / 2);
        double fraction = sqrt(2 * Math.PI) * qParameter;
        return numerator / fraction;
    }
}
