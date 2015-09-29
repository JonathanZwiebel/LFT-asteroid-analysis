package main;

public class SubtractiveHelper {
	// subtracts two 2d slices from eacother top-base and outputs result
	public static float[][] subtractImages(float[][] top_image_mat, float[][] base_image_mat) {
		int row_count = top_image_mat.length;
		int column_count = top_image_mat[0].length;
		float[][] subtracted_image_mat = new float[row_count][column_count];

		for(int row = 0; row < row_count; row++) {
			for(int column = 0; column < column_count; column++) {
				subtracted_image_mat[row][column] = top_image_mat[row][column] - base_image_mat[row][column];
			}
		}

		return subtracted_image_mat;
	}
}