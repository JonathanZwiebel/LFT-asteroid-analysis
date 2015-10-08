package filter;

/**
 * This class applies a binary filter to a data set, setting all values below the filter to 0 and all above to 1
 */
public class BinaryFilter {
    public static float[][] filter(float[][] image, double threshold) {
        float[][] return_binary_image = new float[image.length][image[0].length];
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
}
