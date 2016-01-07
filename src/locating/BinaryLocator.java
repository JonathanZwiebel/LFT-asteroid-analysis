package locating;

import analysis.CubeStats;

/**
 * @author Jonathan Zwiebel
 * @version December 3rd, 2015
 */
public class BinaryLocator extends Locator {
    public float threshold_;
    private ThresholdType threshold_type_;
    private float argument_;

    //Todo: Add more and include blurs
    public enum ThresholdType {
        GIVEN,
        MEAN
    }

    /**
     * Constructs a BinaryLocator object using in floating point data extracted from a preprocessor
     * the data extracted from the preprocessor
     * @param data data extracted from the preprocessor
     * @param threshold_type how the binary filtering will be done
     * @param argument argument that may be passed to threshold
     */
    public BinaryLocator(float[][][] data, ThresholdType threshold_type, float argument) {
        super(data);

        threshold_type_ = threshold_type;
        if(threshold_type_ == ThresholdType.GIVEN) {
            argument_ = argument;
        }
    }

    /**
     * Constructs a BinaryLocator object with no additionally argument
     * @param data data extracted from the preprocessor
     * @param threshold_type data extracted from the preprocessor
     */
    public BinaryLocator(float[][][] data, ThresholdType threshold_type) {
        this(data, threshold_type, -1.0f);
    }

    /**
     * Initializes the BinaryLocator by filling the instances_ variable with BinaryLocatorInstances and sets the
     * threshold value depending on the threshold type
     */
    public void initialize() {
        instances_ = new BinaryLocatorInstance[data_.length];

        for(int index = 0; index < data_.length; index++) {
            instances_[index] = new BinaryLocatorInstance(data_[index]);
        }

        switch(threshold_type_) {
            case GIVEN:
                threshold_ = argument_;
                break;
            case MEAN:
                threshold_ = CubeStats.mean(data_);
                break;
            default:
                System.out.println("Error: Unrecognized threshold type");
                System.exit(0);
                break;
        }
    }
}
