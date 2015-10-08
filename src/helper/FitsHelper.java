package helper;

import nom.tam.fits.*;
import java.io.*;
import java.util.ArrayList;

import nom.tam.util.*;

/**
 * A helper class used for general purpose FITS data extraction
 */
public class FitsHelper {
	// converts from string filename to fits object
	public static Fits readFile(String filename) throws FitsException {
		return new Fits(filename);
	}

	// converts from fits object to 3d float matrix
	public static TableHDU<?> extractTable(Fits fits, int HDU_index) throws FitsException, IOException {
		return (TableHDU<?>) fits.getHDU(HDU_index);
	}

	// extracts a 2d float matrix from a 3d one (represents a slice in time) by timestamp
	public static float[][] extractDataSlice(float[][][] data_cube_mat, int index) {
		return data_cube_mat[index - 1];
	}

	// writes the floating point table to a 2d fits image
	public static void write2DImage(float[][] image, String filename) throws FitsException, IOException {
		Fits subtracted_fits = new Fits();
		subtracted_fits.addHDU(FitsFactory.hduFactory(image));
		File output_file = new File(filename);
		BufferedFile bf = new BufferedFile(output_file, "rw");
		subtracted_fits.write(bf);
		bf.close();
		subtracted_fits.close();
	}

	public static void writeDataCube(float[][][] image, String filename) throws FitsException, IOException {
		Fits subtracted_fits = new Fits();
		subtracted_fits.addHDU(FitsFactory.hduFactory(image));
		File output_file = new File(filename);
		BufferedFile bf = new BufferedFile(output_file, "rw");
		subtracted_fits.write(bf);
		bf.close();
		subtracted_fits.close();
	}

	public static float[][][] extractFilteredColumn(Fits f, String column_name) throws FitsException, IOException{
		TableHDU<?> table = (TableHDU<?>) f.getHDU(1);
		ArrayList<Boolean> valid_indices = new ArrayList();
		int valid_index_count = K2ValidificationHelper.validify(f, valid_indices);

		float[][][] unfiltered_column = (float[][][]) table.getColumn(column_name);
		float[][][] filtered_column = new float[valid_index_count][unfiltered_column[0].length][unfiltered_column[0][0].length];

		int insertion_index = 0;
		for(int index = 0; insertion_index < valid_index_count; index++) {
			if(valid_indices.get(index)) {
				filtered_column[insertion_index] = unfiltered_column[index];
				insertion_index++;
			}
		}
		return filtered_column;
	}
}