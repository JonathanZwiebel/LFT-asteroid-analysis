package IO;


import helper.BooleanToFloat;
import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;
import nom.tam.fits.FitsFactory;
import nom.tam.util.BufferedFile;

import java.io.File;
import java.io.IOException;

/**
 * This class contains static methods to help with generation of FITS files and extraction of data from them. This class
 * mainly serves as a wrapper for existing methods in the nom.tam.fits library.
 *
 * @author Jonathan Zwiebel
 * @version July 8 2016
 */
public final class FitsIO {
	/**
	 * Reads a FITS file
	 *
	 * @param in the filename
	 * @return the fits file
	 * @throws FitsException
	 */
	public static Fits readFile(String in) throws FitsException {
		return new Fits(in);
	}

    /**
     * Writes a 2D image of floating points to a new FITS file
	 *
     * @param image the input image
     * @param out filename
     * @throws FitsException
     * @throws IOException
	 * TODO: Include a header in the FITS file to explain its content
     */
	public static void write2DImage(float[][] image, String out) throws FitsException, IOException {
		Fits subtracted_fits = new Fits();
		subtracted_fits.addHDU(FitsFactory.hduFactory(image));
		File output_file = new File(out);
		BufferedFile buffered_file = new BufferedFile(output_file, "rw");
		subtracted_fits.write(buffered_file);
		buffered_file.close();
		subtracted_fits.close();
	}

    /**
     * Writes a 3D cube of floating points to a new FITS file
	 *
     * @param cube the input cube
     * @param out filename
     * @throws FitsException
     * @throws IOException
     */
	public static void writeDataCube(float[][][] cube, String out) throws FitsException, IOException {
		Fits subtracted_fits = new Fits();
		subtracted_fits.addHDU(FitsFactory.hduFactory(cube));
		File output_file = new File(out);
		BufferedFile buffered_file = new BufferedFile(output_file, "rw");
		subtracted_fits.write(buffered_file);
		buffered_file.close();
		subtracted_fits.close();
	}

	/**
	 * Writes a 2D image of booleans to a new FITS file
	 *
	 * @param image the input image
	 * @param out filename
	 * @throws FitsException
	 * @throws IOException
	 */
	public static void write2DImage(boolean[][] image, String out) throws FitsException, IOException{
		write2DImage(BooleanToFloat.booleanToFloat(image), out);
	}

}