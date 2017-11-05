package pl.topt.project.data;

import java.util.List;

/**
 * Created by dominik on 29.10.17.
 */
public class Signal {
    private List<Double> values;
    private List<Double> arguments;

    public Signal() {
    }

    public static Signal createSignalForValuesAndArguments(List<Double> values, List<Double> arguments) {
        return new Signal(values, arguments);
    }

    private Signal(List<Double> values, List<Double> arguments) {
        this.values = values;
        this.arguments = arguments;
    }

    public List<Double> getValues() {
        return values;
    }

    public void setValues(List<Double> values) {
        this.values = values;
    }

    public List<Double> getArguments() {
        return arguments;
    }

    public void setArguments(List<Double> arguments) {
        this.arguments = arguments;
    }
}
