package stars;

import static filter.BrightBodyFilter.getFilteredCube;
import static helper.FitsHelper.*;
import nom.tam.fits.Fits;

public class BrightBodyTracker {
    public static final String DATA_FILENAME = "C:\\Users\\user\\Desktop\\K2\\raw\\ktwo200000908-c00_lpd-targ.fits";
    public static final String SECONDARY_FILENAME = "C:\\Users\\user\\Desktop\\K2\\filtered\\ktwo200000908-c00-binfilmean.fits";
    public static final String TEXT_FILENAME_BASE = "C:\\Users\\user\\Desktop\\K2\\text\\ktwo200000908-c00_lpd-targ";
    public static final String COLUMN = "FLUX";

    public static final int INDEX = 499;

    public static void main(String[] args) {
        try {
            Fits f = readFile(DATA_FILENAME);
            float[][][] flux_col = extractFilteredColumn(f, COLUMN);

            int[][][] binary_flux_col = getFilteredCube();
            writeDataCube(binary_flux_col, SECONDARY_FILENAME);

            BinaryTrackerInstance t = new BinaryTrackerInstance(flux_col, binary_flux_col, INDEX);
            t.print();
            t.toTextFile(TEXT_FILENAME_BASE + INDEX + ".txt");
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
