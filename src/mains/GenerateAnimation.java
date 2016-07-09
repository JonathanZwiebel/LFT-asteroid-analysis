package mains;

import IO.AnimationGenerator;
import IO.FitsIO;
import IO.K2AnimationGenerator;
import nom.tam.fits.Fits;

/**
 * A top level runnable type that will create an animation from a FITS file for human viewing
 *
 * @author Jonathan Zwiebel
 * @version 8 July 2016
 */
public final class GenerateAnimation {
    public static void run(String[] args) {
        try {
            Fits f = FitsIO.readFile(args[1]);
            AnimationGenerator animationGenerator = new K2AnimationGenerator(f, args[2]);
            animationGenerator.generateAnimation();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
