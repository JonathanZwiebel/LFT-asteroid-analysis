package brightbodies;

/**
 * This class stores references to three MobileBrightBodies believed to be linked. The BrightBodies will be in an
 * adjacent set with a timestamp delta that is a power of three. This data type is to be used by the
 * AdjacentCollinearTripleLinker class.
 *
 * @author Jonathan Zwiebel
 * @version 16 June 2016
 */
public class LinkedBrightBodyTriple {
    public BrightBody[] bodies;
    public int initial_time;
    public int delta_time;
    public int co_determination;

    /**
     * Constructs a LinkedB
     * @param bodies the set of three linked bright bodies
     * @param initial_time the timestamp of the first asteroid
     * @param delta_time the difference in time between the asteroids
     * @param co_determination the r^2 value of the three centroids
     */
    public LinkedBrightBodyTriple(BrightBody[] bodies, int initial_time, int delta_time, int co_determination) {
        assert bodies.length == 3;
        this.bodies = bodies;
        this.initial_time = initial_time;
        this.delta_time = delta_time;
        this.co_determination = co_determination;
    }

    /**
     * Calculates the distance between body 1 and body 2
     *
     * @return the distance between the centroids of the first and second bright bodies
     */
    public double firstDistance() {
        return Math.sqrt(Math.pow(bodies[1].centroid.x - bodies[0].centroid.x, 2) + Math.pow(bodies[1].centroid.y - bodies[0].centroid.y, 2));
    }

    /**
     * Calculates the distance between body 2 and body 3
     *
     * @return the distance between the centroids of the second and third bright bodies
     */
    public double secondDistance() {
        return Math.sqrt(Math.pow(bodies[1].centroid.x - bodies[2].centroid.x, 2) + Math.pow(bodies[1].centroid.y - bodies[2].centroid.y, 2));
    }
}
