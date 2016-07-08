package stats;

import brightbodies.CartesianPoint;
import brightbodies.Coordinate;

/**
 * This class contains static methods that help with finding the moment, area, and centroid of a BrightBody within
 * a given image.
 *
 * @author Jonathan Zwiebel
 * @version 8 July 2016
 * TODO(Major): Fix reversed y-axis bug
 */
public final class MomentStats {
    /**
     * Calculates the moment of an image with degree i for x and j fo ry
     *
     * @param image the input image
     * @param i x degree
     * @param j y degree
     * @return M_ij
     */
    public static float moment(float[][] image, int i, int j) {
        float moment = 0;
        for(int x = 0; x < image.length; x++) {
            for(int y = 0; y < image[0].length; y++) {
                moment += image[image.length - y - 1][x] * Math.pow(x, i) * Math.pow(y, j);
            }
        }
        return moment;
    }

    /**
     * Calculates the moment of a subset of CartesianPoints within an image with degree i
     * for x and j for y
     *
     * @param image the input image
     * @param bright_body subset of CartesianPoints
     * @param i x degree
     * @param j y degree
     * @return M_ij
     */
    public static float moment(float[][] image, CartesianPoint[] bright_body, int i, int j) {
        float moment = 0;
        for(CartesianPoint p : bright_body) {
            moment += image[image.length - p.y - 1][p.x] * Math.pow(p.x, i) * Math.pow(p.y, j);
        }
        return moment;
    }

    /**
     * Calculates the total area of the image, the sum of all of the values
     *
     * @param image the input image
     * @return area
     */
    public static float area(float[][] image) {
        return moment(image, 0, 0);
    }

    /**
     * Calculates the area of an array of CartesianPoints
     *
     * @param image the input image
     * @param bright_body the CartesianPoints that make up the BrightBody
     * @return the area of the star
     */
    public static float area(float[][] image, CartesianPoint[] bright_body) {
        return moment(image, bright_body, 0, 0);
    }

    /**
     * Calculates the centroid of an array of CartesianPoints
     *
     * @param image the input image
     * @param bright_body the CartesianPoints that make up the BrightBody
     * @return The centroid of the star as a a coordinate
     */
    public static Coordinate centroid(float[][] image, CartesianPoint[] bright_body) {
        float x = moment(image, bright_body, 1, 0) / area(image, bright_body);
        float y = moment(image, bright_body, 0, 1) / area(image, bright_body);
        return new Coordinate(x, y);
    }
}
