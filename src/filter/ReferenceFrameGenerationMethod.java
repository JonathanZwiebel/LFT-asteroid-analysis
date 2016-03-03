package filter;

/**
 * Different ways to generate the reference frame
 *
 * TODO: More than binary locator
 * @author Jonathan Zwiebel
 * @version March 2 2016
 */
public enum ReferenceFrameGenerationMethod {
    BINARY_LOCATOR_ABSOLUTE,
    BINARY_LOCATOR_MEAN,
    BINARY_LOCATOR_MEAN_SHIFTED,
    BINARY_LOCATOR_MEAN_SCALED
}
