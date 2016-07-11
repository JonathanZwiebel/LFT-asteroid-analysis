package core.locate.binary;

/**
 * Different threshold calculation methods for a BinaryLocator
 *
 * @author Jonathan Zwiebel
 * @version 11 July 2016
 */
public enum BinaryLocatorThresholdType {
    /**
     * Threshold value is given as a fixed floating point value
     */
    ABSOLUTE,
    /**
     * Threshold value for each frame is the mean brightness value of each frame
     */
    MEAN,
    /**
     * Threshold value for each frame is the mean brightness value of each frame shifted by a fixed floating point value
     */
    MEAN_SHIFTED,
    /**
     * Threshold value for each frame is the mean brightness value of each frame scaled by a fixed floating point value
     */
    MEAN_SCALED
}
