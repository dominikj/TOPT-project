package pl.topt.project.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dominik on 29.10.17.
 */
public class ArgumentRange {
    private double min;
    private double max;
    private double step;

    private ArgumentRange(double min, double max, double step) {
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public static ArgumentRange createArgumentRangeForParameters(double min, double max, double step) {
        return new ArgumentRange(min, max, step);
    }

    public List<Double> getArguments() {
        int numberOfElements = (int) ((max - min) / step);
        List<Double> arguments = new ArrayList<>(numberOfElements);
        for (int i = 0; i < numberOfElements; ++i) {
            arguments.add(min + i * step);
        }
        return arguments;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }
}
