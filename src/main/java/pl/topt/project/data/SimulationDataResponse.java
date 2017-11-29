package pl.topt.project.data;

/**
 * Created by dominik on 20.11.17.
 */
public class SimulationDataResponse {
    private Signal simulatedSignal;
    private String lowMeanValue;
    private String highMeanValue;
    private String lowStandardDeviation;
    private String highStandardDeviation;
    private String qParameter;
    private String bitErrorRate;
    private String receiverSensitivityLoss;

    public String getLowMeanValue() {
        return lowMeanValue;
    }

    public void setLowMeanValue(String lowMeanValue) {
        this.lowMeanValue = lowMeanValue;
    }

    public String getHighMeanValue() {
        return highMeanValue;
    }

    public void setHighMeanValue(String highMeanValue) {
        this.highMeanValue = highMeanValue;
    }

    public String getLowStandardDeviation() {
        return lowStandardDeviation;
    }

    public void setLowStandardDeviation(String lowStandardDeviation) {
        this.lowStandardDeviation = lowStandardDeviation;
    }

    public String getHighStandardDeviation() {
        return highStandardDeviation;
    }

    public void setHighStandardDeviation(String highStandardDeviation) {
        this.highStandardDeviation = highStandardDeviation;
    }

    public String getqParameter() {
        return qParameter;
    }

    public void setqParameter(String qParameter) {
        this.qParameter = qParameter;
    }

    public String getBitErrorRate() {
        return bitErrorRate;
    }

    public void setBitErrorRate(String bitErrorRate) {
        this.bitErrorRate = bitErrorRate;
    }

    public Signal getSimulatedSignal() {
        return simulatedSignal;
    }

    public void setSimulatedSignal(Signal simulatedSignal) {
        this.simulatedSignal = simulatedSignal;
    }

    public String getReceiverSensitivityLoss() {
        return receiverSensitivityLoss;
    }

    public void setReceiverSensitivityLoss(String receiverSensitivityLoss) {
        this.receiverSensitivityLoss = receiverSensitivityLoss;
    }
}
