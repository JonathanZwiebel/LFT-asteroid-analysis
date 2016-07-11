package mask;

import fits.FitsIO;
import nom.tam.fits.FitsException;

import java.io.IOException;

/**
 * This class contains static methods to output masks, boolean or image, to FITS files
 *
 * @author Jonathan Zwiebel
 * @version February 26 2016
 */
public final class MaskOut {
    /**
     * Writes a quickly generated BinaryImageMask to a fits file to make analysis of detected patterns easier
     *
     * @param location location in the filepath to generateK2Animation to
     * @param data floating point astro-data
     * @param threshold threshold for the BinaryImageMask
     * @throws FitsException
     * @throws IOException
     *
     * TODO[Minor]: Make this work with mean
     */
    public static void writeBinaryImageMask(String location, float[][] data, float threshold) throws FitsException, IOException {
        BinaryImageMask mask_generator = new BinaryImageMask(data);
        boolean[][] mask = mask_generator.mask(threshold);
        FitsIO.write2DImage(mask, location);
    }
}
