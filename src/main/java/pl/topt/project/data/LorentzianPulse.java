package pl.topt.project.data;

import com.google.common.base.Preconditions;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.pow;

/**
 * Created by dominik on 24.10.17.
 */
public class LorentzianPulse implements Pulse {

    private double halfOfThePeakWidth;
    private double mean; //theta zero
    private double isiRate;

    private LorentzianPulse(double halfOfThePeakWidth, double mean, double isiRate) {
        this.halfOfThePeakWidth = halfOfThePeakWidth;
        this.mean = mean;
        this.isiRate = isiRate;
    }

    public static LorentzianPulse createLorentzianPulseForHalfWidthAndMean(double halfOfThePeakWidth, double mean,
                                                                           double isiRate) {
        Preconditions.checkArgument(isiRate > 0);

        return new LorentzianPulse(halfOfThePeakWidth, mean, isiRate);
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
        double scaledHalfOfThePeakWidth = this.halfOfThePeakWidth * this.isiRate;
        double doublePowWidth = pow(scaledHalfOfThePeakWidth, 2);
        return doublePowWidth / (doublePowWidth + pow(argument - this.mean, 2));
    }

}
