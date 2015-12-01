package analysis;

import brightbodies.CartesianPoint;
import brightbodies.Coordinate;

/**
 * This class holds static methods that help with finding the moment, area, and centroid of a bright_body within
 * a given image.
 * TODO: Fix reversed y-axis bug
 */
public class BrightBodyMoment {

    public static final int IMAGE_SIZE = 49;

    /**
     * Calculates the moment of a subset of <code>CartesianPoint</code> within an image with degree i
     * for x and j for y
     * @param image the input image
     * @param bright_body the <code>CartesianPoint</code> that make up the bright body
     * @param i the x moment weighting
     * @param j the y moment weighting
     * @return the moment M_ij
     */
    public static float moment(float[][] image, CartesianPoint[] bright_body, int i, int j) {
        float moment = 0;
        for(CartesianPoint p : bright_body) {
            moment += image[IMAGE_SIZE - p.y][p.x] * Math.pow(p.x, i) * Math.pow(p.y, j);
        }
        return moment;
    }

    /**
     * Returns the area of a Track
     * @param image the input image
     * @param bright_body the <code>CartesianPoint</code> that make up the bright body
     * @return the area of the star
     */
    public static float area(float[][] image, CartesianPoint[] bright_body) {
        return moment(image, bright_body, 0, 0);
    }

    /**
     * Returns the centroid of a Track
     * @param image the input image
     * @param bright_body the <code>CartesianPoint</code> that make up the bright body
     * @return The centroid of the star as a a coordinate
     */
    public static Coordinate centroid(float[][] image, CartesianPoint[] bright_body) {
        float x = moment(image, bright_body, 1, 0) / area(image, bright_body);
        float y = moment(image, bright_body, 0, 1) / area(image, bright_body);
        return new Coordinate(x, y);
    }
}
