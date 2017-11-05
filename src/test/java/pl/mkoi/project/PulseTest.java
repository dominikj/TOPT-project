package pl.mkoi.project;

import org.apache.commons.math3.util.Precision;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dominik on 29.10.17.
 */
public abstract class PulseTest {
    private static int SCALE = 10;

    protected List<Double> roundValues(List<Double> values) {
        return values.stream().map(value -> Precision.round(value, SCALE)).collect(Collectors.toList());
    }
}
