package core.locate;

import brightbodies.BrightBodyList;

/**
 * This abstract class will computationally compute the location of BrightBodies for a single frame of a data cube. These
 * are to be used only in arrays within Locator objects.
 *
 * @author Jonathan Zwiebel
 * @version 11 July 2016
 */
public abstract class LocatorInstance {
    /**
     * The slice from the data cube that this LocatorInstance is working over
     */
    protected final float[][] data_;

    /**
     * Constructs a LocatorInstance object given a single slice of a data cube. This is to be called only by the
     * initialize() method of a Locator object.
     *
     * @param data A single slice of a data cube
     */
    protected LocatorInstance(float[][] data) {
        data_ = data.clone();
    }

    /**
     * The computational method of the LocatorInstance that finds the BrightBodies in the frame. This method is to be
     * called only by the locate() method of a Locator object.
     *
     * @return BrightBodyList containing all BrightBodies in this frame
     */
    protected abstract BrightBodyList locate(Locator parent);
}
