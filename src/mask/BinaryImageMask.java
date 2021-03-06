package mask;


/**
 * This class applies a binary mask to a data set, setting all values below the mask to false and all values
 * above the threshold to true
 */

public class BinaryImageMask implements ImageMask {
    private final float[][] data_;

    public BinaryImageMask(float[][] data) {
        data_ = data;
    }

    public boolean[][] mask(float ... args) {
        boolean[][] return_binary_image = new boolean[data_.length][data_[0].length];
        for (int i = 0; i < data_.length; i++) {
            for (int j = 0; j < data_[0].length; j++) {
                return_binary_image[i][j] = data_[i][j] > args[0];
            }
        }
        return return_binary_image;
    }
}
