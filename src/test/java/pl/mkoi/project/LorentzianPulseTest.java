package pl.mkoi.project;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import pl.topt.project.data.ArgumentRange;
import pl.topt.project.data.LorentzianPulse;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by dominik on 28.10.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class LorentzianPulseTest extends PulseTest {

    private final List<Double> lorentzianPulseReferene = ImmutableList.of(
            0.058823529411765, 0.059946288125839, 0.061100791866263, 0.062288220051824, 0.063509805914033, 0.064766839378238,
            0.066060670119438, 0.067392710804399, 0.068764440532512, 0.070177408488659, 0.071633237822350, 0.073133629768313,
            0.074680368024854, 0.076275323407371, 0.077920458795661, 0.079617834394904, 0.081369613331597, 0.083178067607133,
            0.085045584433256, 0.086974672975230, 0.088967971530249, 0.091028255170405, 0.093158443881353, 0.095361611229783,
            0.097640993594751, 0.100000000000000, 0.102442222586461, 0.104971447766208, 0.107591668101222, 0.110307094952347,
            0.113122171945701, 0.116041589305607, 0.119070299104591, 0.122213531482206, 0.125476811885164, 0.128865979381443,
            0.132387206100402, 0.136047017849369, 0.139852315954352, 0.143810400368155, 0.147928994082840, 0.152216268874817,
            0.156680872399097, 0.161331956633970, 0.166179207657538, 0.171232876712329, 0.176503812482350, 0.182003494467094,
            0.187744067287474, 0.193738375697458, 0.200000000000000, 0.206543291473893, 0.213383407306248, 0.220536344389556,
            0.228018971178402, 0.235849056603773, 0.244045294806716, 0.252627324171383, 0.261615738802846, 0.271032090199479,
            0.280898876404494, 0.291239515377446, 0.302078298695022, 0.313440320962889, 0.325351379489849, 0.337837837837838,
            0.350926445816957, 0.364644107351225, 0.379017586416010, 0.394073139974779, 0.409836065573771, 0.426330150068213,
            0.443577004968062, 0.461595273264402, 0.480399692544197, 0.500000000000000, 0.520399666944213, 0.541594454072790,
            0.563570784490533, 0.586303939962477, 0.609756097560975, 0.633874239350913, 0.658587987355111, 0.683807439824945,
            0.709421112372304, 0.735294117647059, 0.761266747868453, 0.787153652392947, 0.812743823146944, 0.837801608579089,
            0.862068965517241, 0.885269121813032, 0.907111756168360, 0.927299703264095, 0.945537065052950, 0.961538461538462,
            0.975039001560062, 0.985804416403785, 0.993640699523052, 0.998402555910543, 1.000000000000000, 0.998402555910543,
            0.993640699523052, 0.985804416403785, 0.975039001560062, 0.961538461538461, 0.945537065052950, 0.927299703264094,
            0.907111756168360, 0.885269121813031, 0.862068965517241, 0.837801608579088, 0.812743823146944, 0.787153652392946,
            0.761266747868453, 0.735294117647058, 0.709421112372304, 0.683807439824945, 0.658587987355110, 0.633874239350912,
            0.609756097560975, 0.586303939962477, 0.563570784490532, 0.541594454072789, 0.520399666944213, 0.500000000000000,
            0.480399692544196, 0.461595273264401, 0.443577004968062, 0.426330150068213, 0.409836065573770, 0.394073139974779,
            0.379017586416009, 0.364644107351225, 0.350926445816956, 0.337837837837838, 0.325351379489849, 0.313440320962889,
            0.302078298695021, 0.291239515377446, 0.280898876404494, 0.271032090199479, 0.261615738802846, 0.252627324171382,
            0.244045294806716, 0.235849056603773, 0.228018971178402, 0.220536344389555, 0.213383407306248, 0.206543291473893,
            0.200000000000000, 0.193738375697458, 0.187744067287474, 0.182003494467094, 0.176503812482350, 0.171232876712329,
            0.166179207657538, 0.161331956633970, 0.156680872399097, 0.152216268874817, 0.147928994082840, 0.143810400368155,
            0.139852315954352, 0.136047017849369, 0.132387206100402, 0.128865979381443, 0.125476811885164, 0.122213531482206,
            0.119070299104591, 0.116041589305607, 0.113122171945701, 0.110307094952347, 0.107591668101222, 0.104971447766207,
            0.102442222586461, 0.100000000000000, 0.097640993594751, 0.095361611229783, 0.093158443881353, 0.091028255170405,
            0.088967971530249, 0.086974672975230, 0.085045584433256, 0.083178067607133, 0.081369613331597, 0.079617834394904,
            0.077920458795661, 0.076275323407371, 0.074680368024854, 0.073133629768313, 0.071633237822350, 0.070177408488659,
            0.068764440532512, 0.067392710804399, 0.066060670119438, 0.064766839378238, 0.063509805914033, 0.062288220051824,
            0.061100791866263, 0.059946288125839);

    @Test
    public void LorentzianPulseTest() {
        LorentzianPulse pulse = LorentzianPulse.createLorentzianPulseForHalfWidthAndMean(0.25, 3);
        ArgumentRange arguments = ArgumentRange.createArgumentRangeForParameters(2, 4, 0.01);
        List<Double> values = pulse.getValuesForArgumentRange(arguments);

        assertTrue(roundValues(values).equals(roundValues(lorentzianPulseReferene)));
    }
}
