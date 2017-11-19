package pl.topt.project.data;

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

    private RaisedCosinePulse(double bandwidth, double mean) {
        this.bandwidth = bandwidth;
        this.mean = mean;
        sincFunction = new Sinc();
    }

    public static RaisedCosinePulse createRaisedCosinePulseForBandwidthAndMean(double bandwidth, double mean) {

        return new RaisedCosinePulse(bandwidth, mean);
    }

    public static double calculateBandwidthForPulseWidth(double width) {
        return 1 / (2 * width);
    }

    @Override
    public List<Double> getValuesForArgumentRange(ArgumentRange argumentRange, boolean adjustMeanToArgumentRange) {
        List<Double> arguments = argumentRange.getArguments();

        if (adjustMeanToArgumentRange) {
            mean = argumentRange.getMax() / 2D;
        }

        return arguments.stream().map(this::value).collect(Collectors.toList());
    }

    private double value(double argument) {
        double scaledBandwidth = bandwidth + 0.000000000000001;

        double sinc = sincFunction.value((4 * scaledBandwidth * (argument - mean)) * Math.PI);
        return sinc / (1 - 16 * pow(scaledBandwidth, 2) * pow((argument - mean), 2));
    }
}
