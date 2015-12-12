package mains;

import brightbodies.BrightBodyList;
import locating.BinaryLocator;
import locating.Locator;
import nom.tam.fits.Fits;
import preprocessing.K2Preprocessor;
import preprocessing.Preprocessor;

import java.io.File;

/**
 * @author Joanthan Zwiebel
 * @version November 20th, 2015
 *
 * This class will track a cube of bright bodies in standard FITS format and the link them between individual frames
 * to get references to individual bright bodies over time.
 * TODO[MAJOR]: Split procedure into four macro steps called through instantiated classes
 * TODO: 1. Clean the raw data file
 * TODO: 2. Identify bright bodies in individual frames
 *  TODO: 2A Binary Locator
 *   TODO: 2A.1 Filter the bodies into hit or miss spots to create a mask (different filters)
 *   TODO: 2A.2 Combine the mask and original image to get tracked bright bodies (different combination techniques)
 * TODO: 3. Link immobile bright bodies between frames
 *  TODO: 3A Reference Linking
 *   TODO: 3A.1 Generate a reference frame (different generation techniques)
 *   TODO: 3A.2 Connect bright bodies in each frame to ones in reference (should be different ways)
 * TODO: 4. Track mobile bright bodies between frames
 */
public class Run {
    public static final String DATA_FILENAME =          "C:\\Users\\admin\\Desktop\\K2\\raw\\ktwo200000905-c00_lpd-targ.fits";

    public static void main(String[] args) {
        try {
            Preprocessor preprocessor = new K2Preprocessor(new Fits(new File(DATA_FILENAME)));
            float[][][] data = preprocessor.read();

            Locator locator = new BinaryLocator(data, BinaryLocator.ThresholdType.GIVEN, 500);
            locator.initialize();
            BrightBodyList[] bodies = locator.locate();

            System.out.println(bodies[3]);
        }
        catch(Exception e ) {
            e.printStackTrace();
        }
    }
}
