package stars;

import static filter.BrightBodyFilter.getFilteredCube;
import static helper.FitsHelper.*;
import nom.tam.fits.Fits;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class BrightBodyTracker {
    public static final String DATA_FILENAME = "C:\\Users\\user\\Desktop\\K2\\raw\\ktwo200000908-c00_lpd-targ.fits";
    public static final String SECONDARY_FILENAME = "C:\\Users\\user\\Desktop\\K2\\filtered\\ktwo200000908-c00-binfilmean.fits";
    public static final String TEXT_DIRECTORY = "C:\\Users\\user\\Desktop\\K2\\text\\ktwo200000908-c00";
    public static final String SERIALIZED_DIRECTORY = "C:\\Users\\user\\Desktop\\K2\\serialized\\ktwo200000908-c00";
    public static final String COLUMN = "FLUX";

    public static void main(String[] args) {
        try {
            Fits f = readFile(DATA_FILENAME);
            float[][][] flux_col = extractFilteredColumn(f, COLUMN);

            int[][][] binary_flux_col = getFilteredCube();
            writeDataCube(binary_flux_col, SECONDARY_FILENAME);


            for(int i = 0; i < flux_col.length; i++) {
                BinaryTrackerInstance t = new BinaryTrackerInstance(flux_col, binary_flux_col, i);
                System.out.println("Finish with index " + i);
                t.toTextFile(TEXT_DIRECTORY + "\\index" + i + ".txt");
                t.serialize(SERIALIZED_DIRECTORY + "\\index" + i + ".ser");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
