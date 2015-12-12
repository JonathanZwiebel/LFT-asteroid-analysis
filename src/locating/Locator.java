package locating;

import brightbodies.BrightBodyList;

/**
 * Abstract class to locate the BrightBodies in individual indices of a data cube and return them as an array of
 * BrightBodyLists
 *
 * @author Jonathan Zwiebel
 * @version November 30th, 2015
 * TODO: Include logging functionality
 */
public abstract class Locator {
    protected float[][][] data_;
    protected LocatorInstance[] instances_;

    /**
     * Constructs a Locator object given a cleaned data cube from a preprocessor
     * @param data floating point data cube from a preprocessor
     */
    public Locator(float[][][] data) {
        data_ = data;
    }


    /**
     * Initializes the instances_ variable to unique LocatorInstances for each timestamp
     * TODO: Put this in the constructor, will require subclass to have reference to subLocatorInstance class
     */
    public abstract void initialize();

    /**
     * Locates the BrightBodies in each instance by delegating the task to the LocatorInstances
     * @return array of BrightBodyLists corresponding to BrightBodies at each index
     * TODO: Consider not using BrightBodyList
     */
    public BrightBodyList[] locate() {
        BrightBodyList[] bright_body_lists = new BrightBodyList[data_.length];

        for(int index = 0; index < data_.length; index++) {
            bright_body_lists[index]  = instances_[index].locate(this);
        }

        return bright_body_lists;
    }
}
