package preprocessing;

import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;
import nom.tam.fits.TableHDU;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Joanthan Zwiebel
 * @version November 25th, 2015
 *
 * Extracts the functional data from the K2 mission's output files.
 */
public class K2Preprocessor extends Preprocessor {
    public static final String COLUMN = "FLUX";
    public static final String VALID_COLUMN = "QUALITY";
    public static final int HDU_INDEX = 1;

    /**
     * Reads a FITS file from the K2 mission and converts it into a data cube of floats. All blank indices or indices
     * where the quality value is >= 16384 are not included in the data cube
     *
     * @param fits input FITS file
     * @return cleaned data cube
     * @throws FitsException
     * @throws IOException
     */
    public float[][][] read(Fits fits) throws FitsException, IOException {
        TableHDU<?> table = (TableHDU<?>) fits.getHDU(HDU_INDEX);
        ArrayList<Boolean> valid_indices = new ArrayList();
        int valid_index_count = fillValidIndices(table, valid_indices);

        float[][][] unfiltered_column = (float[][][]) table.getColumn(COLUMN);
        float[][][] filtered_column = new float[valid_index_count][unfiltered_column[0].length][unfiltered_column[0][0].length];

        int insertion_index = 0;
        for(int index = 0; insertion_index < valid_index_count; index++) {
            if(valid_indices.get(index)) {
                filtered_column[insertion_index] = unfiltered_column[index];
                insertion_index++;
            }
        }
        return filtered_column;
    }


    /**
     * Private method to fill an array list with the index of the indices where the data is valid. Also counts the
     * number of valid indices so an array of the correct size can be instantiated
     *
     * @param table table header data unit with the columns
     * @param valid_indexes array list that will have the indices of the valid indices added to it
     * @return count of valid indices
     * @throws IOException
     * @throws FitsException
     */
    private static int fillValidIndices(TableHDU table, ArrayList<Boolean> valid_indexes) throws IOException, FitsException {
        int[] quality_column = (int[]) table.getColumn(VALID_COLUMN);

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
