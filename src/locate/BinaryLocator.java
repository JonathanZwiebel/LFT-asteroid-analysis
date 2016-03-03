package locate;

import analysis.CubeStats;

/**
 * @author Jonathan Zwiebel
 * @version January 26th, 2016
 */
public class BinaryLocator extends Locator {
    public float threshold_;
    private final BinaryLocatorThresholdType threshold_type_;
    private final float[] args_;

    /**
     * Constructs a BinaryLocator object using floating point data extracted from a preprocessor
     * the data extracted from the preprocessor
     * @param data data extracted from the preprocessor
     * @param threshold_type how the binary filtering will be done
     * @param args argument that may be passed to threshold
     */
    public BinaryLocator(float[][][] data, BinaryLocatorThresholdType threshold_type, float ... args) {
        super(data);

        threshold_type_ = threshold_type;
        args_ = args;
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
            case ABSOLUTE:
                threshold_ = args_[0];
                break;
            case MEAN:
                threshold_ = CubeStats.mean(data_);
                break;
            case MEAN_SHIFTED:
                threshold_ = CubeStats.mean(data_) + args_[0];
                break;
            case MEAN_SCALED:
                threshold_ = CubeStats.mean(data_) * args_[0];
                break;
            default:
                System.out.println("Error: Unrecognized threshold type");
                System.exit(1);
                break;
        }
    }
}
