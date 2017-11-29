package pl.topt.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.topt.project.constants.Constants;
import pl.topt.project.data.BinaryData;
import pl.topt.project.data.Signal;
import pl.topt.project.data.SimulationDataResponse;
import pl.topt.project.data.builders.SimulationDataResponseBuilder;
import pl.topt.project.forms.SimulationParametersForm;
import pl.topt.project.services.SignalGeneratorService;
import pl.topt.project.services.SignalMeasureService;
import pl.topt.project.utils.SignalUtils;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

import static pl.topt.project.constants.Constants.WebConstants.BITS_TO_SHOW;

@Controller
@SessionAttributes("binaryData")
public class MainPageController {

    private static final String SIMULATION_BINARY_SEQUENCE_SIZE_KEY = "simulation.binarySequence.size";
    private static final int PRECISION_TO_SHOW = 2;

    private final Environment environment;
    private final SignalGeneratorService signalGeneratorService;
    private final SignalMeasureService signalMeasureService;

    @Autowired
    public MainPageController(Environment environment, SignalGeneratorService signalGeneratorService,
                              SignalMeasureService signalMeasureService) {
        this.environment = environment;
        this.signalGeneratorService = signalGeneratorService;
        this.signalMeasureService = signalMeasureService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showMainPage(Model model) {
        int bits = Integer.parseInt(environment.getProperty(SIMULATION_BINARY_SEQUENCE_SIZE_KEY));
        BinaryData binaryData = signalGeneratorService.generateBinaryData(bits);
        SimulationParametersForm simulationParametersForm = new SimulationParametersForm();
        Signal signal = signalGeneratorService.generateSignal(binaryData, simulationParametersForm);

        model.addAttribute("simulationParameters", simulationParametersForm);
        model.addAttribute("binaryData", binaryData);
        model.addAttribute("simulationData", buildSimulationResponse(signal, simulationParametersForm.getIsiRate()));

        return "home";
    }

    @PostMapping(value = "/simulate")
    @ResponseBody
    public SimulationDataResponse getSignalFigure(@ModelAttribute("binaryData") BinaryData binaryData,
                                                  @RequestBody @Valid SimulationParametersForm simulationParametersForm) {
        Signal signal = signalGeneratorService.generateSignal(binaryData, simulationParametersForm);
        return buildSimulationResponse(signal, simulationParametersForm.getIsiRate());
    }

    @GetMapping("/binary-sequence/new/{numberOfBits}")
    @ResponseBody
    public BinaryData generateNewBinaryData(@PathVariable int numberOfBits, Model model) {
        BinaryData binaryData = signalGeneratorService.generateBinaryData(numberOfBits);
        model.addAttribute("binaryData", binaryData);
        return binaryData;
    }

    @ModelAttribute("pulseTypes")
    public List<Constants.PulseType> addPulseTypes() {
        return Arrays.asList(Constants.PulseType.values());
    }

    @ModelAttribute("bitsToShow")
    public int addBitsToShow() {
        return BITS_TO_SHOW;
    }

    private SimulationDataResponse buildSimulationResponse(Signal signal, double isiRate) {
        SimulationDataResponseBuilder builder = signalMeasureService.measureSignal(signal);
        signal = SignalUtils.scaleAndRoundData(1D, PRECISION_TO_SHOW, signal);
        builder.addSignal(SignalUtils.limitData(BITS_TO_SHOW, signal));
        builder.addReceiverSensitivityLoss(signalMeasureService.calculateReceiverSensitivityLoss(isiRate));
        return builder.build();
    }
}
