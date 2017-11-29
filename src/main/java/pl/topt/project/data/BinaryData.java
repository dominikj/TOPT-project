package pl.topt.project.data;

import java.util.List;

/**
 * Created by dominik on 30.10.17.
 */
public class BinaryData {
    private List<Boolean> binarySequence;
    private Signal binarySignal;
    private int numberOfBits;
    public Signal getBinarySignal() {
        return binarySignal;
    }

    public void setBinarySignal(Signal binarySignal) {
        this.binarySignal = binarySignal;
    }

    public List<Boolean> getBinarySequence() {
        return binarySequence;
    }

    public void setBinarySequence(List<Boolean> binarySequence) {
        this.binarySequence = binarySequence;
    }

    public int getNumberOfBits() {
        return numberOfBits;
    }

    public void setNumberOfBits(int numberOfBits) {
        this.numberOfBits = numberOfBits;
    }
}
