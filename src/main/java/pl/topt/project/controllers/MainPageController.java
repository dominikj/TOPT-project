package pl.topt.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.topt.project.constants.Constants;
import pl.topt.project.data.BinaryData;
import pl.topt.project.data.Signal;
import pl.topt.project.forms.SimulationParametersForm;
import pl.topt.project.services.SignalGeneratorService;
import pl.topt.project.utils.SignalUtils;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
@SessionAttributes("binaryData")
public class MainPageController {

    private static final String SIMULATION_BINARY_SEQUENCE_SIZE_KEY = "simulation.binarySequence.size";
    private static final int BITS_TO_SHOW = 5;
    private static final int PRECISION_TO_SHOW = 2;

    private final Environment environment;
    private final SignalGeneratorService signalGeneratorService;

    @Autowired
    public MainPageController(Environment environment, SignalGeneratorService signalGeneratorService) {
        this.environment = environment;
        this.signalGeneratorService = signalGeneratorService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showMainPage(Model model) {
        int bits = Integer.parseInt(environment.getProperty(SIMULATION_BINARY_SEQUENCE_SIZE_KEY));
        BinaryData binaryData = signalGeneratorService.generateBinaryData(bits);
        SimulationParametersForm simulationParametersForm = new SimulationParametersForm();
        Signal signal = signalGeneratorService.generateSignal(binaryData, simulationParametersForm);
        signal = SignalUtils.scaleAndRoundData(1D, PRECISION_TO_SHOW, signal);

        model.addAttribute("simulationParameters", simulationParametersForm);
        model.addAttribute("binaryData", binaryData);

        model.addAttribute("binaryDataToShow", limitBinaryDataToShow(binaryData));
        model.addAttribute("signal", SignalUtils.limitData(BITS_TO_SHOW, signal));

        return "home";
    }

    @PostMapping(value = "/simulate")
    @ResponseBody
    public Signal getSignalFigure(@ModelAttribute("binaryData") BinaryData binaryData,
                                  @RequestBody @Valid SimulationParametersForm simulationParametersForm) {

        Signal signal = signalGeneratorService.generateSignal(binaryData, simulationParametersForm);
        signal = SignalUtils.scaleAndRoundData(1D, PRECISION_TO_SHOW, signal);

        return SignalUtils.limitData(BITS_TO_SHOW, signal);

    }

    @GetMapping("/binary-sequence/new")
    @ResponseBody
    public BinaryData generateNewBinaryData(Model model) {
        int bits = Integer.parseInt(environment.getProperty(SIMULATION_BINARY_SEQUENCE_SIZE_KEY));
        BinaryData binaryData = signalGeneratorService.generateBinaryData(bits);
        model.addAttribute("binaryData", binaryData);
        return limitBinaryDataToShow(binaryData);
    }

    @ModelAttribute("pulseTypes")
    public List<Constants.PulseType> addPulseTypes() {
        return Arrays.asList(Constants.PulseType.values());
    }

    private BinaryData limitBinaryDataToShow(BinaryData binaryData) {
        BinaryData reducedBinaryData = new BinaryData();
        reducedBinaryData.setBinarySequence(binaryData.getBinarySequence().subList(0, BITS_TO_SHOW));
        return reducedBinaryData;
    }
}
