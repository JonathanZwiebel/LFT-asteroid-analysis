package mains;

import helper.FitsHelper;
import nom.tam.fits.Fits;
import analysis.SimpleWriter;

/**
 * This class will generate an animation with a given column and data set
 */
public class AnimationGenerator {
    public static final String OUTPUT_DIRECTORY = "data\\ani";


    public static void main(String[] args) {
        try {
            Fits f = FitsHelper.readFile(args[0]);
            SimpleWriter.write(f, OUTPUT_DIRECTORY + "\\" + args[1] + ".fits", "FLUX");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
