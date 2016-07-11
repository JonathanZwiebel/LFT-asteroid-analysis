package core.locate.binary;

import core.locate.Locator;

/**
 * This subclass of Locator locates BrightBodies by perform binary filters over individual frames to create BinaryImages.
 * Adjacent positives in the BinaryImages are grouped into BrightBodies. This location method is lightweight but subject
 * to missing feint objects and combining large objects
 *
 * @author Jonathan Zwiebel
 * @version 11 July 2016
 */
public class BinaryLocator extends Locator {
    /**
     * An argument that is used in calculating the locating threshold
     */
    public float threshold_arg_;

    /**
     * The type of calculation used to find the threshold
     */
    protected final BinaryLocatorThresholdType threshold_type_;

    /**
     * Arguments passed in by the user to assist with threshold calculation
     */
    private final float[] args_;

    /**
     * Constructs a BinaryLocator object with given data, ThresholdType, and arguments
     *
     * @param data Data extracted from the preprocessor
     * @param threshold_type Threshold calculation method
     * @param args Additional arguments that may be used in threshold calculation
     */
    public BinaryLocator(float[][][] data, BinaryLocatorThresholdType threshold_type, float ... args) {
        super(data);

        threshold_type_ = threshold_type;
        args_ = args;
    }

    /**
     * Initializes the BinaryLocator by filling the instances_ array with BinaryLocatorInstances and setting the
     * threshold value depending on the threshold type. in the case of BinaryLocatorThresholdTypes of MEAN, MEAN_SHIFTED,
     * and MEAN_SCALED, the value must be computed by each instance.
     */
    public void initialize() {
        instances_ = new BinaryLocatorInstance[data_.length];

        for(int index = 0; index < data_.length; index++) {
            instances_[index] = new BinaryLocatorInstance(data_[index]);
        }

        switch(threshold_type_) {
            case ABSOLUTE:
                threshold_arg_ = args_[0];
                break;
            case MEAN:
                break;
            case MEAN_SHIFTED:
            case MEAN_SCALED:
                threshold_arg_ = args_[0];
                break;
            default:
                System.out.println("Error: Unrecognized threshold type");
                System.exit(1);
                break;
        }
    }
}
