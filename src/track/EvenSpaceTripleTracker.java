package track;

import brightbodies.BrightBodyEvenSpaceTripleLinkedSet;
import brightbodies.BrightBodyList;

/**
 * This class extends the general tracker classes. This searches though all combinations of bright bodies in evenly
 * spaced sets of three timestamps. The sets of three begin on stepped, overlapping values and are spaced by values
 * that are a power of 3. The coefficient of determination is calculated and must exceed a given threshold value.
 *
 * @author Jonathan Zwiebel
 * @version 16 June 2016
 */
public class EvenSpaceTripleTracker extends Tracker {
    /**
     * Constructs an EvenSpaceTripleTracker given the initial array of BrightBodyLists containing the MobileBrightBodies
     * at each frame
     *
     * @param mobile_bodies the set of MobileBrightBodies to check
     */
    public EvenSpaceTripleTracker(BrightBodyList[] mobile_bodies) {
        super(mobile_bodies);
    }

    /**
     * Performs the tracking step using the algorithm described in the class javadocs use comment
     * @return an array of all believed asteroid paths
     */
    public BrightBodyEvenSpaceTripleLinkedSet[] track() {
        return null;
    }
}
