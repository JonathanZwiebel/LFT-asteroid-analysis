package helper;

import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;
import nom.tam.fits.TableHDU;

import java.io.IOException;

/**
 * A helper class meant for the K2 Data to determine if a time stamp has valid data
 */
public class K2ValidificationHelper {

    public static boolean[] validify(Fits fits) throws IOException, FitsException {
        TableHDU<?> thdu = (TableHDU<?>) fits.getHDU(1);
        int[] quality_column = (int[]) thdu.getColumn("QUALITY");
        boolean[] valid_column = new boolean[quality_column.length];
        for(int i = 0; i < quality_column.length; i++) {
            if (quality_column[i] < 16384) {
                valid_column[i] = true;
            }
            else {
                valid_column[i] = false;
            }
        }
        return valid_column;
    }
}
