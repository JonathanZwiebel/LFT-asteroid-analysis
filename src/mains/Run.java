package mains;

import brightbodies.BrightBodyList;
import filtering.MobilityFilter;
import filtering.ReferenceMobilityFilter;
import locating.BinaryLocator;
import locating.Locator;
import nom.tam.fits.Fits;
import preprocessing.K2Preprocessor;
import preprocessing.Preprocessor;

import java.io.File;

/**
 * @author Jonathan Zwiebel
 * @version January 6th, 2015
 *
 * This class will track a cube of bright bodies in standard FITS format and the link them between individual frames
 * to get references to individual bright bodies over time.
 * TODO[MAJOR]: Split procedure into four macro steps called through instantiated classes
 * TODO: 1. Clean the raw data file
 * TODO: 2. Identify bright bodies in individual frames
 *  TODO: 2A Binary Locator
 *   TODO: 2A.1 MobilityFilter the bodies into hit or miss spots to create a mask (different filters)
 *   TODO: 2A.2 Combine the mask and original image to get tracked bright bodies (different combination techniques)
 * TODO: 3. Link immobile bright bodies between frames
 *  TODO: 3A Reference Linking
 *   TODO: 3A.1 Generate a reference frame (different generation techniques)
 *   TODO: 3A.2 Connect bright bodies in each frame to ones in reference (should be different ways)
 * TODO: 4. Track mobile bright bodies between frames
 */
public class Run {

    public static void main(String[] args) {
        try {
            Preprocessor preprocessor = new K2Preprocessor(new Fits(new File(args[0])));
            float[][][] data = preprocessor.read();

            Locator locator = new BinaryLocator(data, BinaryLocator.ThresholdType.MEAN, -1);
            locator.initialize();
            BrightBodyList[] bodies = locator.locate();

            MobilityFilter filter = new ReferenceMobilityFilter(bodies, data);
            BrightBodyList[][] filtered_bodies = filter.filter();
            BrightBodyList[] immobile_bodies = filtered_bodies[0];
            BrightBodyList[] mobile_bodies = filtered_bodies[1];

            System.out.println(bodies[3]);
        }
        catch(Exception e ) {
            e.printStackTrace();
        }
    }
}
