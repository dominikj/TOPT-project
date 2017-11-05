package pl.topt.project.data;

import pl.topt.project.constants.Constants;

import java.util.List;

/**
 * Created by dominik on 30.10.17.
 */
public class Signal {
    private List<Boolean> data;
    private boolean noised;
    private Constants.PulseType pulseType;

    public List<Boolean> getData() {
        return data;
    }

    public void setData(List<Boolean> data) {
        this.data = data;
    }

    public boolean isNoised() {
        return noised;
    }

    public void setNoised(boolean noised) {
        this.noised = noised;
    }

    public Constants.PulseType getPulseType() {
        return pulseType;
    }

    public void setPulseType(Constants.PulseType pulseType) {
        this.pulseType = pulseType;
    }
}
