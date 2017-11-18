package pl.topt.project.data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by dominik on 18.11.17.
 */
public class RectangularPulse implements Pulse {
    @Override
    public List<Double> getValuesForArgumentRange(ArgumentRange argumentRange, boolean adjustMeanToArgumentRange) {

        int argumentsFactor = argumentRange.getArguments().size() / 3;
        List<Double> lowValues = generateLowValue(argumentsFactor);
        List<Double> highValues = generateHighValue(argumentsFactor);
        List<Double> pulseValues = new ArrayList<>();
        pulseValues.addAll(lowValues);
        pulseValues.addAll(highValues);
        pulseValues.addAll(lowValues);
        return pulseValues;
    }

    @Override
    public double calculateParameterForPulseWidth(double width) {
        return 0;
    }

    private List<Double> generateHighValue(int numberOfValues) {
        return Stream.generate(() -> 1D).limit(numberOfValues).collect(Collectors.toList());
    }

    private List<Double> generateLowValue(int numberOfValues) {
        return Stream.generate(() -> 0D).limit(numberOfValues).collect(Collectors.toList());
    }
}
