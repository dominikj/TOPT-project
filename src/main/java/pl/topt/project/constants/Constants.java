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
        Double MIN = 0D;
        Double MAX = 10D;
        Double STEP = 0.1;
    }
}
