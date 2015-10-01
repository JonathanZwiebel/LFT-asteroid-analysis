package helper;

import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;
import nom.tam.fits.TableHDU;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A helper class meant for the K2 Data to determine if a time stamp has valid data
 */
public class K2ValidificationHelper {

    /**
     * Fills the passed array with the set of valid indexes and returns the count
     * @param fits the input fits file, will check the 1st HDU
     * @param valid_indexes the array to be filled with the set of valid indexes
     * @return the count of valid indexes
     * @throws IOException
     * @throws FitsException
     */
    public static int validify(Fits fits, ArrayList<Boolean> valid_indexes) throws IOException, FitsException {
        TableHDU<?> thdu = (TableHDU<?>) fits.getHDU(1);
        int[] quality_column = (int[]) thdu.getColumn("QUALITY");
        int valid_count = 0;
        for(int i = 0; i < quality_column.length; i++) {
            if (quality_column[i] < 16384) {
                valid_indexes.add(true);
                valid_count++;
            }
            else {
                valid_indexes.add(false);
            }
        }
        return valid_count;
    }
}