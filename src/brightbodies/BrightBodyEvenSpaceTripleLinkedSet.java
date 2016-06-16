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
    public int co_determination;

    /**
     * Constructs a LinkedB
     * @param mobile_bodies the set of three linked bright bodies
     * @param co_determination the r^2 value of the three centroids
     */
    public BrightBodyEvenSpaceTripleLinkedSet(BrightBody[] mobile_bodies, int[] timetstamps, int co_determination) {
        super(mobile_bodies, timetstamps);
        assert mobile_bodies.length == 3;
        this.co_determination = co_determination;
    }

    /**
     * Calculates the distance between body 1 and body 2
     *
     * @return the distance between the centroids of the first and second bright bodies
     */
    public double firstDistance() {
        return Math.sqrt(Math.pow(mobile_bodies_[1].centroid.x - mobile_bodies_[0].centroid.x, 2) + Math.pow(mobile_bodies_[1].centroid.y - mobile_bodies_[0].centroid.y, 2));
    }

    /**
     * Calculates the distance between body 2 and body 3
     *
     * @return the distance between the centroids of the second and third bright bodies
     */
    public double secondDistance() {
        return Math.sqrt(Math.pow(mobile_bodies_[1].centroid.x - mobile_bodies_[2].centroid.x, 2) + Math.pow(mobile_bodies_[1].centroid.y - mobile_bodies_[2].centroid.y, 2));
    }
}
