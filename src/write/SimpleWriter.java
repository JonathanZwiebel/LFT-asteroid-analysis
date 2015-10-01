package write;

import helper.FitsHelper;
import helper.K2ValidificationHelper;
import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;
import nom.tam.fits.TableHDU;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This class will scan through a Fits BinTable and generate a series of floating point images
 * that can be played in an animation
 */
public class SimpleWriter {
    public static void write(Fits fits, String output_filename, String column_name) throws IOException, FitsException {
        TableHDU<?> table = (TableHDU<?>) fits.getHDU(1);
        float[][][] unfiltered_data = (float[][][]) table.getColumn(column_name);

        ArrayList<Boolean> valid_indexes = new ArrayList<>(unfiltered_data.length);
        int hits = K2ValidificationHelper.validify(fits, valid_indexes);
        float[][][] data = new float[hits][][];

        int current_data_index = 0;
        for(int i = 0; i < unfiltered_data.length; i++) {
            if (valid_indexes.get(i)) {
                data[current_data_index] = unfiltered_data[i].clone();
                current_data_index++;
            }
        }
        FitsHelper.writeDataCube(data, output_filename);
    }
}
