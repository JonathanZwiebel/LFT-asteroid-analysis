package mains;

import brightbodies.BrightBodyList;
import filtering.MobilityFilter;
import filtering.ReferenceMobilityFilter;
import locating.BinaryLocator;
import locating.Locator;
import nom.tam.fits.Fits;
import preprocessing.K2Preprocessor;
import preprocessing.Preprocessor;
import locating.BinaryLocator.ThresholdType;
import filtering.ReferenceMobilityFilter.ReferenceBodyDetectionMethod;

import java.io.File;

/**
 * @author Jonathan Zwiebel
 * @version January 29th, 2016
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
            System.out.println("Preprocessing");
            Preprocessor preprocessor = new K2Preprocessor(new Fits(new File(args[0])));
            float[][][] data = preprocessor.read();



            float arg1 = Float.parseFloat(args[1]);
            Locator locator;
            if(arg1 == -1) {
                System.out.println("Locating with mean threshold");
                locator = new BinaryLocator(data, ThresholdType.MEAN);
            }
            else {
                System.out.println("Locating with given threshold: " + arg1);
                locator = new BinaryLocator(data, ThresholdType.GIVEN, arg1);
            }
            locator.initialize();
            BrightBodyList[] bodies = locator.locate();


            float arg2 = Float.parseFloat(args[2]);
            float arg3 = Float.parseFloat(args[3]);
            MobilityFilter filter;
            if(arg3 == -1) {
                System.out.println("Filtering with sim thresh of " + arg2 + " and mean threshold reference image");
                filter = new ReferenceMobilityFilter(bodies, data, arg2, ReferenceBodyDetectionMethod.MEAN);
            }
            else {
                System.out.println("Filtering with sim thresh of " + arg2 + " and given threshold reference image: " + arg3);
                filter = new ReferenceMobilityFilter(bodies, data, arg2, ReferenceBodyDetectionMethod.ABSOLUTE, arg3);
            }
            BrightBodyList[][] filtered_bodies = filter.filter();
            BrightBodyList[] immobile_bodies = filtered_bodies[0];
            BrightBodyList[] mobile_bodies = filtered_bodies[1];

            int arg4 = Integer.parseInt(args[4]);
            System.out.println("In frame " + args[4]);
            System.out.println("Immobile: " + immobile_bodies[arg4].size());
            System.out.println("Mobile: " + mobile_bodies[arg4].size());
            System.out.println(immobile_bodies.length);
        }
        catch(Exception e ) {
            e.printStackTrace();
        }
    }
}
