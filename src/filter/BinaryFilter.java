package filter;

import analysis.ImageStats;

/**
 * This class applies a binary filter to a data set, setting all values below the filter to 0 and all above to 1
 */
public class BinaryFilter {
    public static int[][] meanFilter(float[][] image) {
        return filter(image, ImageStats.mean(image));
    }

    public static int[][][] meanFilter(float[][][] cube) {
        int[][][] output_cube = new int[cube.length][cube[0].length][cube[0][0].length];
        for(int i = 0; i < cube.length; i++) {
            output_cube[i] = meanFilter(cube[i]);
        }
        return output_cube;
    }

    public static int[][] filter(float[][] image, double threshold) {
        int[][] return_binary_image = new int[image.length][image[0].length];
        for(int i = 0; i < image.length; i++) {
            for(int j = 0; j < image[0].length; j++) {
                if(image[i][j] > threshold) {
                    return_binary_image[i][j] = 1;
                }
                else {
                    return_binary_image[i][j] = 0;
                }
            }
        }
        return return_binary_image;
    }

    public static int[][][] filter(float[][][] cube, double threshold) {
        int[][][] output_cube = new int[cube.length][cube[0].length][cube[0][0].length];
        for(int i = 0; i < cube.length; i++) {
            output_cube[i] = filter(cube[i], threshold);
        }
        return output_cube;
    }
}
