package pl.topt.project.constants;

/**
 * Created by dominik on 29.10.17.
 */
public class Constants {
    public enum PulseType {
        GAUSSIAN,
        LORENTZIAN,
        RAISED_COSINE;
    }

    public interface PulseArgument {
        double STEP = 0.2;

        interface CleanPulse {
            double MIN = 0D;
            double MAX = 10D;
        }

        interface InterferedPulse {
            double ISI_RATE = 6;
            double MIN = CleanPulse.MIN;
            double MAX = CleanPulse.MAX * ISI_RATE;
        }
    }

    public interface WebConstants {
        int BITS_TO_SHOW = 5;
    }
}
