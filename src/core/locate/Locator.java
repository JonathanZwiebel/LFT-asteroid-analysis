package core.locate;

import brightbodies.BrightBodyList;

/**
 * This abstract class is for a generic object that performs the Locating step of LFT. Classes that extend this class
 * are constructed with data cubes extracted by a Preprocessor and output an array of BrightBodyList objects. Each
 * element of the array corresponds to a sequential timestamp and the BrightBodyList contain information on all BrightBodies
 * found in each frame. Locator objects interpret each frame individually and contain an array of LocatorInstance
 * objects which each correspond to a frame in the cube. The initialize method is required to create LocateInstance
 * objects of the correct type.
 *
 * @author Jonathan Zwiebel
 * @version 11 July 2016
 */
public abstract class Locator {
    /**
     * Floating point data cube taken from a Preprocessor
     */
    protected final float[][][] data_;

    /**
     * Array of objects that will each locate BrightBodies in a single frame
     */
    protected LocatorInstance[] instances_;

    /**
     * Constructs a Locator object given a floating point data cube from a Preprocessor object. No computation occurs in
     * this method.
     * @param data Data cube from Preprocessor
     */
    public Locator(float[][][] data) {
        instances_ = null;
        data_ = data;
    }


    /**
     * Initializes the array of LocatorInstance objects with LocatorInstances of the correct subclass. This method must
     * be called prior to the locate() method.
     * TODO: Transfer this method to the constructor; this will require the Locator subclass to have a reference to the correct LocatorInstance subclass
     */
    public abstract void initialize();

    /**
     * Iterates through the array of LocatorInstances to generate the computed BrightBodyList array.
     * @return The array of BrightBodyLists
     */
    public BrightBodyList[] locate() {
        BrightBodyList[] bright_body_lists = new BrightBodyList[data_.length];

        for(int index = 0; index < data_.length; index++) {
            bright_body_lists[index]  = instances_[index].locate(this);
        }

        return bright_body_lists;
    }
}
