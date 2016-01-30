package helper;

/**
 * Static methods to help with image subtraction
 */
public class DataMathematicsHelper {
	/**
	 * Subtracts two images from each other
	 * @param top the top image
	 * @param base the base image
	 * @return top - base
	 */
	public static float[][] subtractImages(float[][] top, float[][] base) {
		int row_count = top.length;
		int column_count = top[0].length;
		float[][] subtracted_image_mat = new float[row_count][column_count];

		for(int row = 0; row < row_count; row++) {
			for(int column = 0; column < column_count; column++) {
				subtracted_image_mat[row][column] = top[row][column] - base[row][column];
			}
		}

		return subtracted_image_mat;
	}

	/**
	 * Returns an image where all of the values have been absolute valued
	 * @param image the input image
	 * @return all values absolute valued
	 */
	public static float[][] absolulteValue(float[][] image) {
		float[][] out_float_mat = new float[image.length][image[0].length];
		for(int i = 0; i < image.length; i++) {
			for(int j = 0; j < image[0].length; j++) {
				out_float_mat[i][j] = Math.abs(image[i][j]);
			}
		}
		return out_float_mat;
	}

	/**
	 * Truncates all values of an image below a specific value
	 * @param image the input image
	 * @param truncation_limit the value to truncate beneath
	 * @return an image where all values below the limit are 0
	 */
	public static float[][] limitTruncate(float[][] image, float truncation_limit) {
		float[][] truncated_float_mat = new float[image.length][image[0].length];
		for(int i = 0; i < image.length; i++) {
			for(int j = 0; j < image[0].length; j++) {
				if(image[i][j] >= truncation_limit) {
					truncated_float_mat[i][j] = image[i][j];
				}
				else {
					truncated_float_mat[i][j] = 0;
				}
			}
		}
		return truncated_float_mat;
	}

	/**
	 * Calculates the mean of multiple images through each pixel
	 * @param images array of images to take the mean of
	 * @return mean image
	 */
	public static float[][] meanImage(float[][] ... images) {
		int count = images.length;
		float[][] mean_float_mat = new float[images[0].length][images[0][0].length];
		for(int i = 0; i < images[0].length; i++) {
			for(int j = 0; j < images[0][0].length; j++) {
				float sum = 0;
				for(float[][] float_mat : images) {
					sum += float_mat[i][j];
				}
				mean_float_mat[i][j] = sum / count;
			}
		}

		return mean_float_mat;
	}

	/**
	 * Calculates the value of one image divided by another, pixel by pixel
	 * @param base the numerator image
	 * @param divisor the denominator image
	 * @return base / divisor
	 */
    public static float[][] divideImage(float[][] base, float[][] divisor) {
        float[][] output = new float[base.length][base[0].length];
        for(int i = 0; i < base.length; i++) {
            for(int j = 0; j < base[0].length; j++) {
                output[i][j] = base[i][j] / divisor[i][j];
            }
        }
        return output;
    }
}