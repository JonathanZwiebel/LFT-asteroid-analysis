package helper;

/**
 * Static methods to help with arrays
 */
class ArrayHelper {

    /**
     * Converts a 2d int array to a 2d float array
     * @param image the int array
     * @return the float array
     */
    public static float[][] intToFloat(int[][] image) {
        float[][] imagef = new float[image.length][image[0].length];
        for(int i = 0; i < image.length; i++) {
            for(int j = 0; j < image[0].length; j++) {
                imagef[i][j] = image[i][j];
            }
        }
        return imagef;
    }

    /**
     * Converts a 3d int cube to a 3d float cube
     * @param cube the 3d int cube
     * @return the 3d float cube
     */
    public static float[][][] intToFloat(int[][][] cube) {
        float[][][] cubef = new float[cube.length][cube[0].length][cube[0][0].length];
        for(int i = 0; i < cube.length; i++) {
            cubef[i] = intToFloat(cube[i]);
        }
        return cubef;
    }

    /**
     * Converts a 2d boolean array to a 2d float array
     * @param image the boolean array
     * @return the float array
     */
    public static float[][] booleanToFloat(boolean[][] image) {
        float[][] imagef = new float[image.length][image[0].length];
        for(int i = 0; i < image.length; i++) {
            for(int j = 0; j < image[0].length; j++) {
                imagef[i][j] = 0;
                if(image[i][j]) {
                    imagef[i][j] = 1;
                }
            }
        }
        return imagef;
    }

    /**
     * Converts a 3d boolean cube to a 3d float cube
     * @param cube the 3d boolean cube
     * @return the 3d float cube
     */
    public static float[][][] booleanToFloat(boolean[][][] cube) {
        float[][][] cubef = new float[cube.length][cube[0].length][cube[0][0].length];
        for(int i = 0; i < cube.length; i++) {
            cubef[i] = booleanToFloat(cube[i]);
        }
        return cubef;
    }

    public static float[][] shift(float[][] data, float shift) {
        float[][] output = data.clone();
        for(int i = 0; i < output.length; i++) {
            for(int j = 0; j < output.length; j++) {
                output[i][j] += shift;
            }
        }
        return output;
    }
}
