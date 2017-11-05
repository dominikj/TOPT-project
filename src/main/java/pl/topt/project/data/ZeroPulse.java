package pl.topt.project.data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dominik on 30.10.17.
 */
public class ZeroPulse implements Pulse {
    @Override
    public List<Double> getValuesForArgumentRange(ArgumentRange argumentRange) {
        List<Double> arguments = argumentRange.getArguments();

        return arguments.stream().map(arg -> 0D).collect(Collectors.toList());
    }
}
