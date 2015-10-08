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
    public static final String OUTPUT_FILENAME = "C:\\Users\\user\\Desktop\\K2\\filtered\\ktwo200000908-c00-filtered.fits";
    public static final String COLUMN = "FLUX";

    public static final int BINARY_THRESHOLD = 3500;

    public static void main(String[] args) {
        try {
            Fits f = FitsHelper.readFile(INPUT_FILENAME);
            float[][][] column = FitsHelper.extractFilteredColumn(f, COLUMN);
            float[][][] filtered = BinaryFilter.filter(column, BINARY_THRESHOLD);
            FitsHelper.writeDataCube(filtered, OUTPUT_FILENAME);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
