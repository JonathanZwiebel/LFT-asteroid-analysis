package mains;

import helper.FitsHelper;
import helper.SubtractiveHelper;
import nom.tam.fits.*;

public class SimpleSubtractive {
	// index from 1 to match fits file
	public static final int BASE_IMAGE_INDEX = 1;
	public static final int TOP_IMAGE_INDEX = 101; 
	public static final String COLUMN = "FLUX";
	public static final String INPUT_FILENAME = "C:\\Users\\user\\Desktop\\K2\\raw\\ktwo200000905-c00_lpd-targ.fits";
	public static final String OUTPUT_FILENAME = "C:\\Users\\user\\Desktop\\K2\\subtractive\\single\\subtractive-output.fits";

	public static void main(String[] args) {
		try {
			Fits fits = FitsHelper.readFile(INPUT_FILENAME);
			TableHDU<?> thdu = FitsHelper.extractTable(fits, 1);
			float[][][] data_cube_mat = (float[][][]) thdu.getColumn(COLUMN);
			
			float[][] base_image_mat = FitsHelper.extractDataSlice(data_cube_mat, BASE_IMAGE_INDEX);
			float[][] top_image_mat = FitsHelper.extractDataSlice(data_cube_mat, TOP_IMAGE_INDEX);

			float[][] subtracted_image_mat = SubtractiveHelper.subtractImages(top_image_mat, base_image_mat);
			
			FitsHelper.write2DImage(subtracted_image_mat, OUTPUT_FILENAME);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
