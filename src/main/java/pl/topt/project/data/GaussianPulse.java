package pl.topt.project.data;

import org.apache.commons.math3.analysis.function.Gaussian;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dominik on 24.10.17.
 */
public class GaussianPulse implements Pulse {

    private Gaussian gaussianFunction;

    public static GaussianPulse createGaussianPulseForExpectedValueAndStandardDeviation(double expectedValue, double standardDeviation) {
        return new GaussianPulse(expectedValue, standardDeviation);
    }

    private GaussianPulse(double expectedValue, double standardDeviation) {
        gaussianFunction = new Gaussian(1, expectedValue, standardDeviation);
    }

    @Override
    public List<Double> getValuesForArgumentRange(ArgumentRange argumentRange) {
        List<Double> arguments = argumentRange.getArguments();

        return arguments.stream().map(gaussianFunction::value).collect(Collectors.toList());
    }

}
