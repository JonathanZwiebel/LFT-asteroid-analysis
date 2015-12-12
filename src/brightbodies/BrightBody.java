package brightbodies;


import java.io.Serializable;

import static analysis.BrightBodyMoment.centroid;
import static analysis.BrightBodyMoment.area;

/**
 * Represents a bright body with a given set of coordinate values. Can determine and store centroid and area. Comparable
 * by area and serializable.
 */
public class BrightBody implements Comparable<BrightBody>, Serializable{
    public Coordinate centroid;
    public float area;
    public CartesianPoint[] body;

    /**
     * Constructs a bright body given the source image and coordinates
     * @param image source image
     * @param body coordinates in the bright body
     */
    public BrightBody(float[][] image, CartesianPoint[] body) {
        this.body = body;
        centroid = centroid(image, body.clone());
        area = area(image, body.clone());
    }

    /**
     * Determines if this bright body contains a particular point
     * @param point
     * @return
     */
    public boolean contains(CartesianPoint point) {
        for(CartesianPoint p : body) {
            if(point.equals(p)) {
                return true;
            }
        }
        return false;
    }

    public int compareTo(BrightBody b) {
        return (int) (this.area - b.area);
    }

    public String toString() {
        return("Size: " + body.length + " | Area: " + area + " | Centroid: " + "(" + centroid.x + " , " + centroid.y + ")");
    }
}