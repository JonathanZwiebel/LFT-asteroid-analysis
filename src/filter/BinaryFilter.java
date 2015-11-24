package filter;

import analysis.ImageStats;

/**
 * This class applies a binary filter to a data set, setting all values below the filter to 0 and all above to 1
 */
//TODO: Half of these methods are public and half are private when they should all be the same

public class BinaryFilter {
    float[][][] cube_;

    /**
     * Constructs the BinaryFilter
     * @param cube data input
     */
    public BinaryFilter(float[][][] cube) {
        this.cube_ = cube;
    }

    /**
     * Filters the data with a given threshold, setting values greater than or equal to 1 and less than to 0
     * @param threshold the threshold
     * @return the filtered cube
     */
    public int[][][] filter(double threshold) {
        int[][][] output_cube = new int[cube_.length][cube_[0].length][cube_[0][0].length];
        for(int i = 0; i < cube_.length; i++) {
            output_cube[i] = filter(cube_[i], threshold);
        }
        return output_cube;
    }

    public static int[][] filter(float[][] image, double threshold) {
        int[][] return_binary_image = new int[image.length][image[0].length];
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                if (image[i][j] > threshold) {
                    return_binary_image[i][j] = 1;
                } else {
                    return_binary_image[i][j] = 0;
                }
            }
        }
        return return_binary_image;
    }

    /**
     * Filters the data using the mean value as the threshold
     * @return the filtered cube
     */
    public int[][][] meanFilter() {
        int[][][] output_cube = new int[cube_.length][cube_[0].length][cube_[0][0].length];
        for(int i = 0; i < cube_.length; i++) {
            output_cube[i] = meanFilter(cube_[i]);
        }
        return output_cube;
    }

    public static int[][] meanFilter(float[][] image) {
        return filter(image, ImageStats.mean(image));
    }
}
