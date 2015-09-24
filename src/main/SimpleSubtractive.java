package main;

import java.io.IOException;

import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;
import nom.tam.fits.TableHDU;

public class SimpleSubtractive {
	// index from 1 to match fits file
	public static final int BASE_IMAGE_INDEX = 1;
	public static final int TOP_IMAGE_INDEX = 11; 
	public static final String COLUMN = "FLUX";
	public static final String FILENAME = "C:\\Users\\user\\Desktop\\ktwo200000905-c00_lpd-targ.fits";

	public static void main(String[] args) {
		try {
			Fits fits = readFile(FILENAME);
			TableHDU<?> thdu = extractTable(fits, 1);
			
			float[][][] data_cube_mat = (float[][][]) thdu.getColumn(COLUMN);
			
			float[][] base_image_mat = extractDataSlice(data_cube_mat, BASE_IMAGE_INDEX);
			float[][] top_image_mat = extractDataSlice(data_cube_mat, TOP_IMAGE_INDEX);

			float[][] subtracted_image_mat = subtractImages(top_image_mat, base_image_mat);

			System.out.println(base_image_mat[10][20]);
			System.out.println(top_image_mat[10][20]);
			System.out.println(subtracted_image_mat[10][20]);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// converts from string filename to fits object
	public static Fits readFile(String filename) throws FitsException {
		return new Fits(filename);
	}

	// converts from fits object to 3d float matrix
	public static TableHDU<?> extractTable(Fits fits, int HDU_index) throws FitsException, IOException {
		return (TableHDU<?>) fits.getHDU(HDU_index);
	}

	// extracts a 2d float matrix from a 3d one (represents a slice in time)
	public static float[][] extractDataSlice(float[][][] data_cube_mat, int index) {
		return data_cube_mat[index - 1];
	}

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
