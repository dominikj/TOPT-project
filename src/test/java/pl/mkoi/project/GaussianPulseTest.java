package pl.mkoi.project;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import pl.topt.project.data.ArgumentRange;
import pl.topt.project.data.GaussianPulse;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class GaussianPulseTest extends PulseTest {
    private final List<Double> gaussianPulseReference = ImmutableList.of(
            0.011108996538242, 0.011793579567951, 0.012515342341224, 0.013275965284957, 0.014077183271575, 0.014920786069068,
            0.015808618705242, 0.016742581740099, 0.017724631440178, 0.018756779848581, 0.019841094744370, 0.020979699484926,
            0.022174772724855, 0.023428548005008, 0.024743313205154, 0.026121409853918, 0.027565232289593, 0.029077226665547,
            0.030659889794008, 0.032315767822159, 0.034047454734599, 0.035857590676414, 0.037748860091299, 0.039723989669419,
            0.041785746099929, 0.043936933623407, 0.046180391379735, 0.048518990547354, 0.050955631270194, 0.053493239368986,
            0.056134762834134, 0.058883168097810, 0.061741436083429, 0.064712558031227, 0.067799531099248, 0.071005353739637,
            0.074333020850790, 0.077785518706572, 0.081365819664525, 0.085076876655682, 0.088921617459386, 0.092902938767263,
            0.097023700041287, 0.101286717171718, 0.105694755941489, 0.110250525304485, 0.114956670486035, 0.119815765914756,
            0.124830307995833, 0.130002707736651, 0.135335283236613, 0.140830252053841, 0.146489723462366, 0.152315690614242,
            0.158310022621939, 0.164474456577155, 0.170810589523065, 0.177319870397797, 0.184003591967720, 0.190862882769880,
            0.197898699083615, 0.205111816952077, 0.212502824275022, 0.220072112994796, 0.227819871398009, 0.235746076555863,
            0.243850486926525, 0.252132635143300, 0.260591821012689, 0.269227104746599, 0.278037300453194, 0.287020969910917,
            0.296176416650267, 0.305501680367805, 0.314994531696764, 0.324652467358350, 0.334472705717564, 0.344452182766909,
            0.354587548560899, 0.364875164123679, 0.375311098851399, 0.385891128430219, 0.396610733289965, 0.407465097612528,
            0.418449108913035, 0.429557358210739, 0.440784140805324, 0.452123457673083, 0.463569017495998, 0.475114239335359,
            0.486752255959972, 0.498475917837466, 0.510277797795504, 0.522150196357980, 0.534085147759517, 0.546074426639710,
            0.558109555416683, 0.570181812337605, 0.582282240201813, 0.594401655750219, 0.606530659712633, 0.618659647502622,
            0.630778820547428, 0.642878198238472, 0.654947630485883, 0.666976810858474, 0.678955290288568, 0.690872491319090,
            0.702717722868398, 0.714480195486403, 0.726149037073691, 0.737713309033535, 0.749162022824993, 0.760484156883590,
            0.771668673874526, 0.782704538241869, 0.793580734015767, 0.804286282838474, 0.814810262168729, 0.825141823623021,
            0.835270211411272, 0.845184780823671, 0.854875016724669, 0.864330552009586, 0.873541185978850, 0.882496902584595,
            0.891187888504184, 0.899604550995232, 0.907737535486830, 0.915577742861978, 0.923116346386636, 0.930344808241421,
            0.937254895612678, 0.943838696300543, 0.950088633802627, 0.955997481833100, 0.961558378238270, 0.966764838271153,
            0.971610767189123, 0.976090472140370, 0.980198673306755, 0.983930514272508, 0.987281571590291, 0.990247863518235,
            0.992825857903813, 0.995012479192682, 0.996805114543033, 0.998201619028437, 0.999200319914684, 0.999800019998667,
            1.000000000000000, 0.999800019998667, 0.999200319914684, 0.998201619028437, 0.996805114543033, 0.995012479192682,
            0.992825857903813, 0.990247863518235, 0.987281571590291, 0.983930514272508, 0.980198673306755, 0.976090472140370,
            0.971610767189123, 0.966764838271153, 0.961558378238269, 0.955997481833100, 0.950088633802627, 0.943838696300543,
            0.937254895612678, 0.930344808241421, 0.923116346386636, 0.915577742861978, 0.907737535486829, 0.899604550995231,
            0.891187888504184, 0.882496902584595, 0.873541185978850, 0.864330552009586, 0.854875016724669, 0.845184780823671,
            0.835270211411272, 0.825141823623021, 0.814810262168729, 0.804286282838474, 0.793580734015767, 0.782704538241868,
            0.771668673874526, 0.760484156883590, 0.749162022824993, 0.737713309033534, 0.726149037073690, 0.714480195486403,
            0.702717722868398, 0.690872491319090, 0.678955290288567, 0.666976810858474, 0.654947630485883, 0.642878198238472,
            0.630778820547428, 0.618659647502622, 0.606530659712633, 0.594401655750218, 0.582282240201813, 0.570181812337605,
            0.558109555416683, 0.546074426639709, 0.534085147759517, 0.522150196357980, 0.510277797795504, 0.498475917837466,
            0.486752255959972, 0.475114239335359, 0.463569017495998, 0.452123457673083, 0.440784140805324, 0.429557358210739,
            0.418449108913035, 0.407465097612528, 0.396610733289965, 0.385891128430219, 0.375311098851399, 0.364875164123679,
            0.354587548560899, 0.344452182766909, 0.334472705717564, 0.324652467358350, 0.314994531696763, 0.305501680367805,
            0.296176416650267, 0.287020969910917, 0.278037300453194, 0.269227104746599, 0.260591821012689, 0.252132635143300,
            0.243850486926524, 0.235746076555863, 0.227819871398009, 0.220072112994796, 0.212502824275022, 0.205111816952077,
            0.197898699083614, 0.190862882769880, 0.184003591967720, 0.177319870397796, 0.170810589523065, 0.164474456577155,
            0.158310022621939, 0.152315690614242, 0.146489723462366, 0.140830252053841, 0.135335283236613, 0.130002707736651,
            0.124830307995833, 0.119815765914756, 0.114956670486035, 0.110250525304485, 0.105694755941488, 0.101286717171718,
            0.097023700041287, 0.092902938767263, 0.088921617459386, 0.085076876655682, 0.081365819664525, 0.077785518706572,
            0.074333020850789, 0.071005353739637, 0.067799531099248, 0.064712558031227, 0.061741436083429, 0.058883168097810,
            0.056134762834134, 0.053493239368986, 0.050955631270194, 0.048518990547354, 0.046180391379735, 0.043936933623407,
            0.041785746099929, 0.039723989669419, 0.037748860091299, 0.035857590676414, 0.034047454734599, 0.032315767822159,
            0.030659889794007, 0.029077226665547, 0.027565232289593, 0.026121409853918, 0.024743313205154, 0.023428548005008,
            0.022174772724855, 0.020979699484926, 0.019841094744370, 0.018756779848581, 0.017724631440178, 0.016742581740099,
            0.015808618705242, 0.014920786069068, 0.014077183271575, 0.013275965284957, 0.012515342341224, 0.011793579567951);

    @Test
    public void testGaussianFunction() {
        GaussianPulse gaussianPulse = GaussianPulse.createGaussianPulseForExpectedValueAndStandardDeviation(3, 0.5);
        ArgumentRange arguments = ArgumentRange.createArgumentRangeForParameters(1.5, 4.5, 0.01);
        List<Double> values = gaussianPulse.getValuesForArgumentRange(arguments);

        assertTrue(roundValues(values).equals(roundValues(gaussianPulseReference)));
    }


}
