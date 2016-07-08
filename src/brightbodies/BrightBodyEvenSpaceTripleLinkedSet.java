package brightbodies;

/**
 * This class stores references to three MobileBrightBodies believed to be linked. The BrightBodies will be in an
 * adjacent set with a timestamp delta that is a power of three. This data type is to be used by the
 * AdjacentCollinearTripleLinker class.
 *
 * @author Jonathan Zwiebel
 * @version 16 June 2016
 */
public class BrightBodyEvenSpaceTripleLinkedSet extends BrightBodyLinkedSet {
    /**
     * Constructs a LinkedSet with three BrightBodies in adjacent frames using the EvenSpacedTripleTracker method
     * @param mobile_bodies the set of three linked bright bodies
     */
    public BrightBodyEvenSpaceTripleLinkedSet(BrightBody[] mobile_bodies, int[] timestamps) {
        super(mobile_bodies, timestamps);
        assert mobile_bodies.length == 3;
    }
}
