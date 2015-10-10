package stars;

import helper.FitsHelper;
import nom.tam.fits.Fits;

public class BrightBodyTracker {
    public static void main(String[] args) {
        try {
            Fits f = FitsHelper.readFile("C:\\Users\\user\\Desktop\\K2\\raw\\ktwo200000908-c00_lpd-targ.fits");
            float[][][] flux_col = FitsHelper.extractFilteredColumn(f, "FLUX");
            TrackerInstance t = new TrackerInstance(flux_col, 0);
            t.print();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
