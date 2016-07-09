package IO;

import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;

import java.io.IOException;

/**
 * An abstract class to generate an animation from a raw FITS file. To be used when the FITS file is not in a
 * standard data cube format. The animation will be a FITS file with a single data header unit containing a data cube
 * with floating point data. This class is to be extended by concrete classes, each for a different data format.
 *
 * @author Jonathan Zwiebel
 * @version 8 July 2016
 */
public abstract class AnimationGenerator {
    Fits in_;
    String out_;

    /**
     * Constructs an AnimationGenerator object
     *
     * @param in the FITS file to read
     * @param out location of output FITS file
     */
    public AnimationGenerator(Fits in, String out) {
        in_ = in;
        out_ = out;
    }

    /**
     * Generates the animation
     *
     * @throws IOException
     * @throws FitsException
     */
    public abstract void generateAnimation() throws IOException, FitsException;
}
