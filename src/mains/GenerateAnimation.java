package mains;

import helper.FitsHelper;
import nom.tam.fits.Fits;
import analysis.SimpleWriter;

/**
 * This class will generate an animation with a given column and data set
 */
class GenerateAnimation {
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
