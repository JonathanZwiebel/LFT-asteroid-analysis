package analysis;

/**
 * Calculates the moment of a greyscale image passed as a floating point array
 */
public class ImageMoment {

    /**
     * Finds the M_ij of the image
     * @param i x-weight
     * @param j j-weight
     */
    public static double moment(float[][] image, int i, int j) {
        double moment = 0;
        for(int x = 0; x < image.length; x++) {
            for(int y = 0; y < image[0].length; y++) {
                moment += image[x][y] * Math.pow(x, i) * Math.pow(y, j);
            }
        }
        return moment;
    }

    /**
     * Returns the summed greyscale area of the image
     * @param image the image as a floating point array
     * @return the area
     */
    public static double area(float[][] image) {
        return moment(image, 0, 0);
    }

    public static double centroid_x(float[][] image) {
        return moment(image, 1, 0) / moment(image, 0, 0);
    }

    public static double centroid_y(float[][] image) {
        return moment(image, 0, 1) / moment(image, 0, 0);
    }
}
