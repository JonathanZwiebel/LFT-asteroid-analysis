package helper;

/**
 * This class contains a static method to generate a mean image based on an array of floating point images
 *
 * @author Jonathan Zwiebel
 * @version 8 July 2016
 */
public final class MeanImage {

	/**
	 * Calculates a mean image given an array of image by calculating the mean value of each pixel over the entire
	 * data set.
	 *
	 * @param images array of images to take the mean of
	 * @return mean image
	 */
	public static float[][] meanImage(float[][] ... images) {
		int count = images.length;
		float[][] mean_image = new float[images[0].length][images[0][0].length];
		for(int i = 0; i < images[0].length; i++) {
			for(int j = 0; j < images[0][0].length; j++) {
				float sum = 0;
				for(float[][] image : images) {
					sum += image[i][j];
				}
				mean_image[i][j] = sum / count;
			}
		}

		return mean_image;
	}
}