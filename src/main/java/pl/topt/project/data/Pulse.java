package pl.topt.project.data;


import java.util.List;

public interface Pulse {

    List<Double> getValuesForArgumentRange(ArgumentRange argumentRange, boolean adjustMeanToArgumentRange);

}
