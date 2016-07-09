package helper;

/**
 * This class contains a static method to convert a boolean image into a binary image with values 0 and 1
 *
 * @author Jonathan Zwiebel
 * @version 8 July 2016
 */
public final class BooleanToFloat {

    /**
     * Converts a 2D boolean array to a 2D float array
     *
     * @param boolean_image the boolean array
     * @return the float array
     */
    public static float[][] booleanToFloat(boolean[][] boolean_image) {
        float[][] float_image = new float[boolean_image.length][boolean_image[0].length];
        for(int i = 0; i < boolean_image.length; i++) {
            for(int j = 0; j < boolean_image[0].length; j++) {
                float_image[i][j] = 0;
                if(boolean_image[i][j]) {
                    float_image[i][j] = 1;
                }
            }
        }
        return float_image;
    }
}
