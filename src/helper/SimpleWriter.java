package helper;

import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;

import java.io.IOException;

/**
 * This class will scan through a Fits BinTable and generate a series of floating point images
 * that can be played in an animation
 * TODO[Major]: Make animation framework because K2 is used always in this case
 */
public final class SimpleWriter {
    public static void write(Fits fits, String output_filename, String column_name) throws IOException, FitsException {
        float[][][] column = FitsHelper.extractFilteredColumn(fits, column_name);
        FitsHelper.writeDataCube(column, output_filename);
    }
}
