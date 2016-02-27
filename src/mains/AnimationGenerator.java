package mains;

import helper.FitsHelper;
import nom.tam.fits.Fits;
import analysis.SimpleWriter;

/**
 * This class will generate an animation with a given column and data set
 */
class AnimationGenerator {
    public static void main(String[] args) {
        try {
            Fits f = FitsHelper.readFile(args[0]);
            SimpleWriter.write(f, args[1], "FLUX");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
