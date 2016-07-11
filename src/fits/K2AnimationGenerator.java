package fits;

import core.preprocess.K2Preprocessor;
import core.preprocess.Preprocessor;
import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;

import java.io.IOException;

/**
 * This class extends AnimationGenerator and generates an animation from a FITS file from the K2 data set. This uses the
 * existing K2Preprocessor class to filter out 'bad' frames.
 *
 * @author Jonathan Zwiebel
 * @version 8 July 2016
 */
public final class K2AnimationGenerator extends AnimationGenerator {

    /**
     * Constructs a K2AnimationGeneratorObject
     * @param in the FITS file to read
     * @param out location of output FITS file
     */
    public K2AnimationGenerator(Fits in, String out) {
        super(in, out);
    }

    /**
     * Generates the animation using a K2Preprocessor
     *
     * @throws IOException
     * @throws FitsException
     */
    public void generateAnimation() throws IOException, FitsException {
        Preprocessor preprocessor = new K2Preprocessor(in_);
        float[][][] data = preprocessor.read();
        FitsIO.writeDataCube(data, out_);
    }
}
