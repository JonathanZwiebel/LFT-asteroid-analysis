package filter;

import helper.FitsHelper;
import helper.K2ValidificationHelper;
import nom.tam.fits.Fits;
import nom.tam.fits.TableHDU;
import write.SimpleWriter;

import java.util.ArrayList;

/**
 * This class filters a FITS image into just the stars
 */
public class StarFilter {
    public static final String INPUT_FILENAME = "C:\\Users\\user\\Desktop\\K2\\raw\\ktwo200000908-c00_lpd-targ.fits";
    public static final String OUTPUT_HEAD = "C:\\Users\\user\\Desktop\\K2\\filtered\\ktwo200000908-c00";
    public static final String COLUMN = "FLUX";

    public static final int BINARY_THRESHOLD = 1000;
    public static final int BLUR_SIZE = 2;

    public static final String BINARY_EXTENSION = "-binfil" + BINARY_THRESHOLD;
    public static final String BLUR_EXTENSION = "-blurfill" + BLUR_SIZE;

    public static void main(String[] args) {
        try {
            Fits f = FitsHelper.readFile(INPUT_FILENAME);
            float[][][] column = FitsHelper.extractFilteredColumn(f, COLUMN);
            int[][][] binary_filtered = BinaryFilter.filter(column, BINARY_THRESHOLD);
            int[][][] blur_filtered = BlurFilter.filter(binary_filtered, BLUR_SIZE, BlurFilter.Blur_Filter_Type.NEGATIVE);

            FitsHelper.writeDataCube(binary_filtered, OUTPUT_HEAD + BINARY_EXTENSION + ".fits");
            FitsHelper.writeDataCube(blur_filtered, OUTPUT_HEAD + BINARY_EXTENSION + BLUR_EXTENSION + ".fits");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
