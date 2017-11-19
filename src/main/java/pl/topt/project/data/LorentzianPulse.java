package pl.topt.project.data;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.pow;

/**
 * Created by dominik on 24.10.17.
 */
public class LorentzianPulse implements Pulse {

    private double halfOfThePeakWidth;
    private double mean; //theta zero

    private LorentzianPulse(double halfOfThePeakWidth, double mean) {
        this.halfOfThePeakWidth = halfOfThePeakWidth;
        this.mean = mean;
    }

    public static LorentzianPulse createLorentzianPulseForHalfWidthAndMean(double halfOfThePeakWidth, double mean) {

        return new LorentzianPulse(halfOfThePeakWidth, mean);
    }

    public static double calculatehalfWidthForPulseWidth(double width) {
        return width / 2;
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
        double doublePowWidth = pow(halfOfThePeakWidth, 2);
        return doublePowWidth / (doublePowWidth + pow(argument - this.mean, 2));
    }

}
