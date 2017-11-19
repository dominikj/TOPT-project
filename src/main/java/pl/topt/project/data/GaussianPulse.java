package pl.topt.project.data;

import org.apache.commons.math3.analysis.function.Gaussian;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dominik on 24.10.17.
 */
public class GaussianPulse implements Pulse {

    private Gaussian gaussianFunction;
    private double standardDeviation;

    public static GaussianPulse createGaussianPulseForExpectedValueAndStandardDeviation(double expectedValue,
                                                                                        double standardDeviation) {
        return new GaussianPulse(expectedValue, standardDeviation);
    }

    public static double calculateStandardDeviationForPulseWidth(double width) {
        return width / 2.3548;
    }

    private GaussianPulse(double expectedValue, double standardDeviation) {
        gaussianFunction = new Gaussian(1, expectedValue, standardDeviation);
        this.standardDeviation = standardDeviation;
    }

    @Override
    public List<Double> getValuesForArgumentRange(ArgumentRange argumentRange, boolean adjustMeanToArgumentRange) {
        List<Double> arguments = argumentRange.getArguments();

        if (adjustMeanToArgumentRange) {
            gaussianFunction = new Gaussian(1, argumentRange.getMax() / 2D, standardDeviation);
        }

        return arguments.stream().map(gaussianFunction::value).collect(Collectors.toList());
    }

}
