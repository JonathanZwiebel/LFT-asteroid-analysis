package analysis;

import helper.FitsHelper;
import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;

import java.io.IOException;

/**
 * This class will scan through a Fits BinTable and generate a series of floating point images
 * that can be played in an animation
 */
public class SimpleWriter {
    public static void write(Fits fits, String output_filename, String column_name) throws IOException, FitsException {
        float[][][] column = FitsHelper.extractFilteredColumn(fits, column_name);
        FitsHelper.writeDataCube(column, output_filename);
    }
}
