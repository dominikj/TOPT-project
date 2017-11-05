package pl.topt.project.forms;

import pl.topt.project.constants.Constants;

import javax.validation.constraints.NotNull;

/**
 * Created by dominik on 30.10.17.
 */
public class SimulationParametersForm {
    private boolean addNoise;
    @NotNull
    private Constants.PulseType pulseType;
    //    private boolean addIsi;
    private Double noiseSNR;

    public SimulationParametersForm() {
        pulseType = Constants.PulseType.GAUSSIAN;
        noiseSNR = Double.valueOf(0);
    }

    public Double getNoiseSNR() {
        return noiseSNR;
    }

    public void setNoiseSNR(Double noiseSNR) {
        this.noiseSNR = noiseSNR;
    }

//    public boolean isAddIsi() {
//        return addIsi;
//    }
//
//    public void setAddIsi(boolean addIsi) {
//        this.addIsi = addIsi;
//    }

    public Constants.PulseType getPulseType() {
        return pulseType;
    }

    public void setPulseType(Constants.PulseType pulseType) {
        this.pulseType = pulseType;
    }

    public boolean isAddNoise() {
        return addNoise;
    }

    public void setAddNoise(boolean addNoise) {
        this.addNoise = addNoise;
    }
}
