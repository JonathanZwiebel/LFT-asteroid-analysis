package main;

import java.io.File;
import java.io.IOException;

import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;
import nom.tam.fits.FitsFactory;
import nom.tam.fits.TableHDU;
import nom.tam.util.BufferedFile;

public class SimpleSubtractive {
	// index from 1 to match fits file
	public static final int BASE_IMAGE_INDEX = 1;
	public static final int TOP_IMAGE_INDEX = 101; 
	public static final String COLUMN = "FLUX";
	public static final String INPUT_FILENAME = "C:\\Users\\user\\Desktop\\K2\\raw\\ktwo200000905-c00_lpd-targ.fits";
	public static final String OUTPUT_FILENAME = "C:\\Users\\user\\Desktop\\K2\\subtractive\\simple\\subtractive-output.fits";

	public static void main(String[] args) {
		try {
			Fits fits = readFile(INPUT_FILENAME);
			TableHDU<?> thdu = extractTable(fits, 1);
			
			float[][][] data_cube_mat = (float[][][]) thdu.getColumn(COLUMN);
			
			float[][] base_image_mat = extractDataSlice(data_cube_mat, BASE_IMAGE_INDEX);
			float[][] top_image_mat = extractDataSlice(data_cube_mat, TOP_IMAGE_INDEX);

			float[][] subtracted_image_mat = subtractImages(top_image_mat, base_image_mat);
			
			writeImage(subtracted_image_mat, OUTPUT_FILENAME);
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

	public static void writeImage(float[][] image, String filename) throws FitsException, IOException {
			Fits subtracted_fits = new Fits();
			subtracted_fits.addHDU(FitsFactory.hduFactory(image));
			File output_file = new File(filename);
			BufferedFile bf = new BufferedFile(output_file, "rw");
			subtracted_fits.write(bf);
			bf.close();
			subtracted_fits.close();
	}
}
