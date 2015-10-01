package main;

import helper.FitsHelper;
import nom.tam.fits.Fits;
import write.SimpleWriter;

/**
 * This class will generate an animation with a given column and data set
 */
public class AnimationGenerator {
    public static final String INPUT_FILENAME = "C:\\Users\\user\\Desktop\\K2\\raw\\ktwo200000905-c00_lpd-targ.fits";
    public static final String OUTPUT_FILENAME = "C:\\Users\\user\\Desktop\\K2\\animation.fits";


    public static void main(String[] args) {
        try {
            Fits f = FitsHelper.readFile(INPUT_FILENAME);
            SimpleWriter.write(f, OUTPUT_FILENAME, "FLUX");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
