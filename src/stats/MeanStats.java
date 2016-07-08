package stats;

/**
 * This class contains helper methods for calculating the mean values of data images or cubes
 *
 * @author Jonathan Zwiebel
 * @version 8 July 2016
 */
public final class MeanStats {
    /**
     * Calculates the complete mean value of a cube of floats
     *
     * @param cube the cube
     * @return mean value
     */
    public static float mean(float[][][] cube) {
        float sum = 0.0f;
        for(float[][] slice : cube) {
            sum += mean(slice);
        }
        return sum / cube.length;
    }

    /**
     * Calculates the complete mean value of an image of floats
     *
     * @param image the image
     * @return mean value
     */
    public static float mean(float[][] image) {
        float sum = 0;
        for(float[] array : image) {
            for(float f : array) {
                sum += f;
            }
        }
        return sum / (image.length * image[0].length);
    }

}
