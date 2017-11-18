package pl.topt.project.data;

import com.google.common.base.Preconditions;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.math3.analysis.function.Gaussian;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dominik on 24.10.17.
 */
public class GaussianPulse implements Pulse {

    private Gaussian gaussianFunction;
    @SuppressFBWarnings
    private double isiRate;
    private double standardDeviation;

    public static GaussianPulse createGaussianPulseForExpectedValueAndStandardDeviation(double expectedValue,
                                                                                        double standardDeviation,
                                                                                        double isiRate) {
        Preconditions.checkArgument(isiRate > 0);

        return new GaussianPulse(expectedValue, standardDeviation, isiRate);
    }

    private GaussianPulse(double expectedValue, double standardDeviation, double isiRate) {
        gaussianFunction = new Gaussian(1, expectedValue, standardDeviation * isiRate);
        this.isiRate = isiRate;
        this.standardDeviation = standardDeviation;
    }

    @Override
    public List<Double> getValuesForArgumentRange(ArgumentRange argumentRange, boolean adjustMeanToArgumentRange) {
        List<Double> arguments = argumentRange.getArguments();

        if (adjustMeanToArgumentRange) {
            gaussianFunction = new Gaussian(1, argumentRange.getMax() / 2D, standardDeviation * isiRate);
        }

        return arguments.stream().map(gaussianFunction::value).collect(Collectors.toList());
    }

    @Override
    public double calculateParameterForPulseWidth(double width) {
        return 0;
    }

}
