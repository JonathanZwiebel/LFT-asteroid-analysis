package helper;

import nom.tam.fits.*;
import java.io.*;
import java.util.ArrayList;

import nom.tam.util.*;

/**
 * Static methods to help with Data extraction and insertion
 */
public class FitsHelper {
	/**
	 * Reads a FITS file
	 * @param filename the filename
	 * @return the fits file
	 * @throws FitsException
	 */
	public static Fits readFile(String filename) throws FitsException {
		return new Fits(filename);
	}

    /**
     * Extracts a TableHDU from a FITS file with a given index
     * @param fits the FITS file
     * @param HDU_index the index
     * @return the TableHDU at that index
     * @throws FitsException
     * @throws IOException
     */
	public static TableHDU<?> extractTable(Fits fits, int HDU_index) throws FitsException, IOException {
		return (TableHDU<?>) fits.getHDU(HDU_index);
	}

    /**
     * TODO: Safely remove this
     * Extracts a 2d index-offset data slice?
     * @param data_cube_mat the 3d data cube
     * @param index the index to be sliced
     * @return the slice
     */
	public static float[][] extractDataSlice(float[][][] data_cube_mat, int index) {
		return data_cube_mat[index - 1];
	}

    /**
     * TODO: Add header and more documentation information on FITS file
     * Writes a 2d image of floating points to a new FITS file
     * @param image the input image
     * @param filename filename
     * @throws FitsException
     * @throws IOException
     */
	public static void write2DImage(float[][] image, String filename) throws FitsException, IOException {
		Fits subtracted_fits = new Fits();
		subtracted_fits.addHDU(FitsFactory.hduFactory(image));
		File output_file = new File(filename);
		BufferedFile bf = new BufferedFile(output_file, "rw");
		subtracted_fits.write(bf);
		bf.close();
		subtracted_fits.close();
	}

    /**
     * Writes a 3d cube of floating points to a new FITS file
     * @param cube the input cube
     * @param filename filename
     * @throws FitsException
     * @throws IOException
     */
	public static void writeDataCube(float[][][] cube, String filename) throws FitsException, IOException {
		Fits subtracted_fits = new Fits();
		subtracted_fits.addHDU(FitsFactory.hduFactory(cube));
		File output_file = new File(filename);
		BufferedFile bf = new BufferedFile(output_file, "rw");
		subtracted_fits.write(bf);
		bf.close();
		subtracted_fits.close();
	}

    /**
     * Writes a 2d image of ints to a new FITS file
     * @param image the input image
     * @param filename filename
     * @throws FitsException
     * @throws IOException
     */
	public static void write2DImage(int[][] image, String filename) throws FitsException, IOException{
		write2DImage(ArrayHelper.intToFloat(image), filename);
	}

    /**
     * Writes a 3d cube of ints to a new FITS file
     * @param cube the inout cube
     * @param filename filename
     * @throws FitsException
     * @throws IOException
     */
	public static void writeDataCube(int[][][] cube, String filename) throws FitsException, IOException{
		writeDataCube(ArrayHelper.intToFloat(cube), filename);
	}

	/**
	 * Writes a 2d image of booleans to a new FITS file
	 * @param image the input image
	 * @param filename filename
	 * @throws FitsException
	 * @throws IOException
	 */
	public static void write2DImage(boolean[][] image, String filename) throws FitsException, IOException{
		write2DImage(ArrayHelper.booleanToFloat(image), filename);
	}

	/**
	 * Writes a 3d cube of booleans to a new FITS file
	 * @param cube the inout cube
	 * @param filename filename
	 * @throws FitsException
	 * @throws IOException
	 */
	public static void writeDataCube(boolean[][][] cube, String filename) throws FitsException, IOException{
		writeDataCube(ArrayHelper.booleanToFloat(cube), filename);
	}

    /**
     * Extracts a column from a FITS File filtered through the K2 filtering method as a cube
     * @param fits the FITS file
     * @param column_name name of the column to be extracted
     * @return extracted column has a floating point cube
     * @throws FitsException
     * @throws IOException
     */
	public static float[][][] extractFilteredColumn(Fits fits, String column_name) throws FitsException, IOException{
		TableHDU<?> table = (TableHDU<?>) fits.getHDU(1);
		ArrayList<Boolean> valid_indices = new ArrayList();
		int valid_index_count = K2ValidificationHelper.validify(fits, valid_indices);

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