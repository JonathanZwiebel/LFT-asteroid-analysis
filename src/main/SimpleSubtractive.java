package main;

public class SimpleSubtractive {
	// index from 1 to match fits file
	public static final int BASE-IMAGE-INDEX = 1;
	public static final int TOP-IMAGE-INDEX = 11; 
	public static final String FILESYSTEM-LOC = "C:\\Users\\user\\Desktop"
	public static final String FILENAME = "ktwo200000905-c00_lpd-targ.fits"

	public static void main(String[] args) {
		try {
			Fits fits = readFile(FILESYSTEM-LOC + "\\" + FILENAME);
			float[][][] fits-data-cube-mat = extractDataCube(fits);

			float[][] base-image-mat = extractDataSliceFromFmat(fits-data-cube-fmat, BASE-IMAGE-INDEX);
			float[][] top-image-mat = extractDataSliceFromFmat(fits-data-cube-fmat, TOP-IMAGE-INDEX);

			float[][] subtracted-image-mat = subtractImages(top-image-mat, base-image-mat);

			System.out.println(base-image-mat[10][20]);
			System.out.println(top-image-mat[10][20]);
			System.out.println(subtracted-image-mat[10][20]);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// converts from string filename to fits object
	public static Fits readFile(String filename) {
		return new Fits(filename);
	}

	// converts from fits object to 3d float matrix
	public static float[][][] extractDataCube(Fits fits) {
		return (float[][][]) fits.getHDU(1).getData().getKernel()
	}

	// extracts a 2d float matrix from a 3d one (represets a slice in time)
	public static float[][] extractDataSliceFromFmat(float[][][] data-cube-mat, int image-index) {
		return cube[image-index - 1];
	}

	// subtracts two 2d slices from eacther top-base and outputs result
	public static float[][] subtractImages(float[][] top-image-mat, float[][] base-image-mat) {
		int row-count = top-image-mat.length;
		int column-count = top-image-mat[0].length;
		float[][] subtracted-image-mat = new float[row-count][column-count];

		for(int row = 0; row < row-count; row++) {
			for(int column = 0; column-count; column++) {
				subtracted-image-mat[row][column] = top-image-mat[row][column] - base-image-mat[row][column];
			}
		}

		return subtracted-image-mat;
	}
}
