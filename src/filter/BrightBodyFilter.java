package filter;

import helper.FitsHelper;
import nom.tam.fits.Fits;


/**
 * This class filters a FITS image into just the stars
 */
public class BrightBodyFilter {
    public static final String INPUT_FILENAME = "C:\\Users\\admin\\Desktop\\K2\\raw\\ktwo200000905-c00_lpd-targ.fits";
    public static final String OUTPUT_HEAD = "C:\\Users\\admin\\Desktop\\K2\\filtered\\ktwo200000908-c00";
    public static final String COLUMN = "FLUX";

    public static final int BINARY_THRESHOLD = 1000;
    public static final int BLUR_SIZE = 0;

    public static final String BINARY_EXTENSION = "-binfilmean";
    public static final String BLUR_EXTENSION = "-blurfill" + BLUR_SIZE;

    public static void main(String[] args) {
        try {
            Fits f = FitsHelper.readFile(INPUT_FILENAME);
            float[][][] column = FitsHelper.extractFilteredColumn(f, COLUMN);
            int[][][] binary_filtered = BinaryFilter.meanFilter(column);
            int[][][] blur_filtered = BlurFilter.filter(binary_filtered, BLUR_SIZE, BlurFilter.Blur_Filter_Type.NEGATIVE);

            FitsHelper.writeDataCube(binary_filtered, OUTPUT_HEAD + BINARY_EXTENSION + ".fits");
            FitsHelper.writeDataCube(blur_filtered, OUTPUT_HEAD + BINARY_EXTENSION + BLUR_EXTENSION + ".fits");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // for now instead of writing out to a fits and then having the tracker pick it up
    // this method will pass the data to the tracker
    // CHANGE THIS
    // TODO: CHANGE THIS
    public static int[][][] getFilteredCube() {
        try {
            Fits f = FitsHelper.readFile(INPUT_FILENAME);
            float[][][] column = FitsHelper.extractFilteredColumn(f, COLUMN);
            int[][][] binary_filtered = BinaryFilter.meanFilter(column);
            int[][][] blur_filtered = BlurFilter.filter(binary_filtered, BLUR_SIZE, BlurFilter.Blur_Filter_Type.NEGATIVE);

            return blur_filtered;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
