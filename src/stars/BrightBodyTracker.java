package stars;

import static filter.BrightBodyFilter.getFilteredCube;
import static helper.FitsHelper.*;
import nom.tam.fits.Fits;

public class BrightBodyTracker {
    public static void main(String[] args) {
        try {
            Fits f = readFile("C:\\Users\\user\\Desktop\\K2\\raw\\ktwo200000908-c00_lpd-targ.fits");
            float[][][] flux_col = extractFilteredColumn(f, "FLUX");

            int[][][] binary_flux_col = getFilteredCube();
            writeDataCube(binary_flux_col, "C:\\Users\\user\\Desktop\\K2\\filtered\\ktwo200000908-c00-binfilmean.fits");

            BinaryTrackerInstance t = new BinaryTrackerInstance(flux_col, binary_flux_col, 0);
            t.print();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
