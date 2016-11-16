package mains;

import fits.AnimationGenerator;
import fits.FitsIO;
import fits.K2AnimationGenerator;
import nom.tam.fits.Fits;
/**
 * A top level runnable type that will create an animation from a K2 FITS file for human viewing
 *
 * @author Jonathan Zwiebel
 * @version 8 July 2016
 */
public final class GenerateK2Animation {
    public static void run(String[] args) throws Exception {
        assert args[0] == "GENERATE_K2_ANIMATION";

        Fits f = FitsIO.readFile(args[1]);
        AnimationGenerator animationGenerator = new K2AnimationGenerator(f, args[2]);
        animationGenerator.generateAnimation();
    }
}
