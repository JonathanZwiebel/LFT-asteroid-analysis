package locate;

import brightbodies.BrightBodyList;

/**
 * @author Jonathan Zwiebel
 * @version November 30th, 2015
 */
public abstract class LocatorInstance {
    final float[][] data_;

    /**
     * Constructs a LocatorInstance given an image array from the data cube, to be called by a Locator object
     * @param data one index of a data cube
     */
    LocatorInstance(float[][] data) {
        data_ = data.clone();
    }

    /**
     * Locates all of the BrightBodies in the data array
     * @return BrightBodyList showing locations of all of the bright bodies
     */
    public abstract BrightBodyList locate(Locator parent);
}
