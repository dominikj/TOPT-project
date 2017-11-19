package pl.topt.project.data;

import pl.topt.project.constants.Constants.PulseArgument;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pl.topt.project.constants.Constants.PulseArgument.CleanPulse.MAX;
import static pl.topt.project.constants.Constants.PulseArgument.CleanPulse.MIN;

/**
 * Created by dominik on 18.11.17.
 */
public class RectangularPulse implements Pulse {

    @Override
    public List<Double> getValuesForArgumentRange(ArgumentRange argumentRange, boolean adjustMeanToArgumentRange) {
        int argumentsSize = argumentRange.getArguments().size();
        int cleanPulseSize = (int) ((MAX - MIN) / PulseArgument.STEP);
        int center = cleanPulseSize;
        int highValueArgStart = center - cleanPulseSize / 2;
        int highValueArgStop = highValueArgStart + cleanPulseSize;
        List<Double> rectangularPulse = new ArrayList<>();
        rectangularPulse.addAll(generateLowValue(highValueArgStart));
        rectangularPulse.addAll(generateHighValue(highValueArgStop - highValueArgStart));
        rectangularPulse.addAll(generateLowValue(argumentsSize - highValueArgStop));
        return rectangularPulse;
    }

    private List<Double> generateHighValue(int numberOfValues) {
        return Stream.generate(() -> 1D).limit(numberOfValues).collect(Collectors.toList());
    }

    private List<Double> generateLowValue(int numberOfValues) {
        return Stream.generate(() -> 0D).limit(numberOfValues).collect(Collectors.toList());
    }
}
