package mains;

import stars.BinaryTracker;

/**
 * Created by Jonathan Zwiebel on 11/2/15.
 *
 */
public class TrackBrightBody {
    public static final String DATA_FILENAME =          "C:\\Users\\admin\\Desktop\\K2\\raw\\ktwo200000908-c00_lpd-targ.fits";
    public static final String TEXT_DIRECTORY =         "C:\\Users\\admin\\Desktop\\K2\\text\\ktwo200000908-c00";
    public static final String SERIALIZED_DIRECTORY =   "C:\\Users\\admin\\Desktop\\K2\\serialized\\ktwo200000908-c00";
    public static final String CSV_DIRECTORY =          "C:\\Users\\admin\\Desktop\\K2\\csv\\ktwo200000908-c00";
    public static final String MASS_CSV_FILENAME =      "C:\\Users\\admin\\Desktop\\K2\\csv\\908MassAreaSorted.csv";
    public static final String COLUMN =                 "FLUX";

    public static void main(String[] args) {
        try {
            BinaryTracker bbt = new BinaryTracker(DATA_FILENAME, COLUMN);
            bbt.track();
            bbt.toMassAreaSortedCSV(MASS_CSV_FILENAME, 5);

        }
        catch(Exception e ) {
            e.printStackTrace();
        }
    }
}
