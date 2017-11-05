package pl.topt.project.data;

import java.util.List;

/**
 * Created by dominik on 29.10.17.
 */
public class SignalFigure {
    private List<Double> values;
    private List<Double> arguments;

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
