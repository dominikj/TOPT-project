package pl.topt.project.data.builders;

import pl.topt.project.data.Signal;
import pl.topt.project.data.SimulationDataResponse;

import java.text.DecimalFormat;

/**
 * Created by dominik on 20.11.17.
 */
public class SimulationDataResponseBuilder {

    private SimulationDataResponse response = new SimulationDataResponse();
    private DecimalFormat formatter = new DecimalFormat("##.###E0");

    public SimulationDataResponseBuilder addSignal(Signal signal) {
        response.setSimulatedSignal(signal);
        return this;
    }

    public SimulationDataResponseBuilder addLowMeanValue(double lowMeanValue) {
        response.setLowMeanValue(formatter.format(lowMeanValue));
        return this;
    }

    public SimulationDataResponseBuilder addHighMeanValue(double highMeanValue) {
        response.setHighMeanValue(formatter.format(highMeanValue));
        return this;
    }

    public SimulationDataResponseBuilder addLowStandardDeviation(double lowStandardDeviation) {
        response.setLowStandardDeviation(formatter.format(lowStandardDeviation));
        return this;
    }

    public SimulationDataResponseBuilder addHighStandardDeviation(double highStandardDeviation) {
        response.setHighStandardDeviation(formatter.format(highStandardDeviation));
        return this;
    }

    public SimulationDataResponseBuilder addQParameter(double qParameter) {
        response.setqParameter(formatter.format(qParameter));
        return this;
    }

    public SimulationDataResponseBuilder addBitErrorRate(double bitErrorRate) {
        response.setBitErrorRate(formatter.format(bitErrorRate));
        return this;
    }

    public SimulationDataResponseBuilder addReceiverSensitivityLoss(double receiverSensitivityLoss) {
        response.setReceiverSensitivityLoss(formatter.format(receiverSensitivityLoss));
        return this;
    }

    public SimulationDataResponse build() {
        return response;
    }


}
