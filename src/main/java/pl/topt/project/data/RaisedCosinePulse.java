package pl.topt.project.data;

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

    public static LorentzianPulse createLorentzianPulse(double halfOfThePeakWidth, double mean) {
        return new LorentzianPulse(halfOfThePeakWidth, mean);
    }

    @Override
    public double[] getValuesForArgumentRange(double min, double max, double step) {
        int numberOfElements = (int) ((max - min) / step);
        double[] values = new double[numberOfElements];
        for (int i = 0; i < numberOfElements; ++i) {
            values[i] = value(min + i * step);
        }
        return values;
    }

    private double value(double argument) {
        double doublePowWidth = pow(halfOfThePeakWidth, 2);
        return doublePowWidth / (doublePowWidth + pow(argument - mean, 2));
    }
}
