package core.track;

import brightbodies.BrightBodyLinkedSet;
import brightbodies.BrightBodyList;

/**
 * This abstract class will take in the set of MobileBrightBodies from the MobilityFilter and determine which are
 * asteroids. It is assumed that the false positive rate is <80% while the false negative rate is <1%.
 *
 * @author Jonathan Zwiebel
 * @version 16 June 2016
 */
public abstract class Tracker {
    BrightBodyList[] mobile_bodies_;

    /**
     * Creates a generic tracker object
     *
     * @param mobile_bodies MBBs from mobility filtration
     */
    Tracker(BrightBodyList[] mobile_bodies) {
        mobile_bodies_ = mobile_bodies;
    }

    /**
     * Links the MobileBrightBodies that show asteroid-like motion
     * @return BrightBodyLinkedSets that each store multiple locations of a believed asteroid
     */
    public abstract BrightBodyLinkedSet[] track();
}
