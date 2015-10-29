package analysis;

/**
 * Calculates the moment of a grayscale image passed as a floating point array
 *
 */
public class ImageMoment {

    /**
     * Find the moment of the image with x degree of i and y degree of j
     * @param image the input image
     * @param i x degree
     * @param j y degree
     * @return M_ij
     */
    public static float moment(float[][] image, int i, int j) {
        float moment = 0;
        for(int x = 0; x < image.length; x++) {
            for(int y = 0; y < image[0].length; y++) {
                moment += image[x][y] * Math.pow(x, i) * Math.pow(y, j);
            }
        }
        return moment;
    }

    /**
     * Returns the total area of the image, the sum of all of hte values
     * @param image the input image
     * @return area
     */
    public static float area(float[][] image) {
        return moment(image, 0, 0);
    }

    /**
     * Returns the x value of the centroid of the image
     * @param image the input image
     * @return the x value of the centroid of the image
     */
    public static float centroid_x(float[][] image) {
        return moment(image, 1, 0) / moment(image, 0, 0);
    }

    /**
     * Returns the y value of the centroid of the image
     * @param image the input image
     * @return the y value of the centroid of the image
     */
    public static float centroid_y(float[][] image) {
        return moment(image, 0, 1) / moment(image, 0, 0);
    }
}
