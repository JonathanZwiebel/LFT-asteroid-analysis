package mains;

import stars.BinaryTracker;

/**
 * Created by admin on 11/2/15.
 */
public class BrightBody {
    public static final String DATA_FILENAME = "C:\\Users\\user\\Desktop\\K2\\raw\\ktwo200000908-c00_lpd-targ.fits";
    public static final String SECONDARY_FILENAME = "C:\\Users\\user\\Desktop\\K2\\filtered\\ktwo200000908-c00-binfilmean.fits";
    public static final String TEXT_DIRECTORY = "C:\\Users\\user\\Desktop\\K2\\text\\ktwo200000908-c00";
    public static final String SERIALIZED_DIRECTORY = "C:\\Users\\user\\Desktop\\K2\\serialized\\ktwo200000908-c00";
    public static final String COLUMN = "FLUX";

    public static void main(String[] args) {
        try {
            BinaryTracker bbt = new BinaryTracker(DATA_FILENAME, SECONDARY_FILENAME, TEXT_DIRECTORY, SERIALIZED_DIRECTORY, COLUMN);
            bbt.track();
        }
        catch(Exception e ) {
            e.printStackTrace();
        }
    }
}
