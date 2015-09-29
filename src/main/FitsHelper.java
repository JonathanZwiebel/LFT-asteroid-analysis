public class FitsHelper {
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
}