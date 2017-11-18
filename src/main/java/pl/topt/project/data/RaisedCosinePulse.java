package pl.topt.project.data;

import com.google.common.base.Preconditions;
import org.apache.commons.math3.analysis.function.Sinc;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.pow;

/**
 * Created by dominik on 24.10.17.
 */
public class RaisedCosinePulse implements Pulse {

    private double bandwidth;
    private double mean;
    private Sinc sincFunction;
    private double isiRate;

    private RaisedCosinePulse(double bandwidth, double mean, double isiRate) {
        this.bandwidth = bandwidth;
        this.mean = mean;
        this.isiRate = isiRate;
        sincFunction = new Sinc();
    }

    public static RaisedCosinePulse createRaisedCosinePulseForBandwidthAndMean(double bandwidth, double mean,
                                                                               double isiRate) {
        Preconditions.checkArgument(isiRate > 0);

        return new RaisedCosinePulse(bandwidth, mean, isiRate);
    }

    @Override
    public List<Double> getValuesForArgumentRange(ArgumentRange argumentRange, boolean adjustMeanToArgumentRange) {
        List<Double> arguments = argumentRange.getArguments();

        if (adjustMeanToArgumentRange) {
            mean = argumentRange.getMax() / 2D;
        }

        return arguments.stream().map(this::value).collect(Collectors.toList());
    }

    @Override
    public double calculateParameterForPulseWidth(double width) {
        return 0;
    }

    private double value(double argument) {
        double scaledBandwidth = bandwidth / isiRate;

        double sinc = sincFunction.value((4 * scaledBandwidth * (argument - mean)) * Math.PI);
        return sinc / (1 - 16 * pow(scaledBandwidth, 2) * pow((argument - mean), 2));
    }
}
