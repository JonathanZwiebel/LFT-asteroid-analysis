package mains;

import helper.FitsHelper;
import nom.tam.fits.Fits;
import helper.SimpleWriter;

/**
 * A top level runnable type that will create an animation from a FITS file for human viewing
 *
 * @author Jonathan Zwiebel
 * @version February 26 2016
 *
 * TODO: Add functionality for files in non-kepler format
 */
public final class GenerateAnimation {
    public static void run(String[] args) {
        try {
            Fits f = FitsHelper.readFile(args[1]);
            SimpleWriter.write(f, args[2], "FLUX");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
