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
        float[][][] column = FitsHelper.extractFilteredColumn(fits, column_name);
        FitsHelper.writeDataCube(column, output_filename);
    }
}
