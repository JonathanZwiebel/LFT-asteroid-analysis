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

	public static float[][] absolulteValue(float[][] in_float_mat) {
		float[][] out_float_mat = new float[in_float_mat.length][in_float_mat[0].length];
		for(int i = 0; i < in_float_mat.length; i++) {
			for(int j = 0; j < in_float_mat[0].length; j++) {
				out_float_mat[i][j] = Math.abs(in_float_mat[i][j]);
			}
		}
		return out_float_mat;
	}

	public static float[][] limitTruncate(float[][] in_float_mat, float truncation_limit) {
		float[][] truncated_float_mat = new float[in_float_mat.length][in_float_mat[0].length];
		for(int i = 0; i < in_float_mat.length; i++) {
			for(int j = 0; j < in_float_mat[0].length; j++) {
				if(in_float_mat[i][j] >= truncation_limit) {
					truncated_float_mat[i][j] = in_float_mat[i][j];
				}
				else {
					truncated_float_mat[i][j] = 0;
				}
			}
		}
		return truncated_float_mat;
	}

	public static float[][] meanImage(float[][] ... float_mats) {
		int count = float_mats.length;
		float[][] mean_float_mat = new float[float_mats.length][float_mats[0].length];
		for(int i = 0; i < float_mats.length; i++) {
			for(int j = 0; j < float_mats[0].length; j++) {
				float sum = 0;
				for(float[][] float_mat : float_mats) {
					sum += float_mat[i][j];
				}
				mean_float_mat[i][j] = sum / count;
			}
		}

		return mean_float_mat;
	}
}