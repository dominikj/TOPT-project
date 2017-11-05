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

    public void shiftArgumentsOneTime() {
        double delta = max - min;
        min = max;
        max = max + delta;
    }
}
