package mains;

import brightbodies.BrightBody;
import brightbodies.BrightBodyList;
import core.locate.binary.BinaryLocator;
import core.locate.binary.BinaryLocatorThresholdType;
import core.preprocess.K2Preprocessor;
import nom.tam.fits.Fits;

import java.io.File;
import java.io.FileWriter;

/**
 * A top level runnable type for generating text files to be submitted to Astrometry.net for spatial evaluation. The
 * files contain the positions of stars at a single timestamp in individual rows with comma separated x and y values
 * corresponding to their centroids. The brightest objects are listed first.
 *
 * @author Jonathan Zwiebel
 * @version 15 July 2015
 */
public class GenerateAstrometryTextFile {
    public static void run(String[] args) {
        assert args[0].equals("GENERATE_ASTROMETRY_TEXT_FILE");

        int current_arg = 1;

        String file_in = args[current_arg];
        current_arg++;

        int frame = Integer.parseInt(args[current_arg]);
        current_arg++;

        int max_object_count = Integer.parseInt(args[current_arg]);
        current_arg++;

        float scalar = Float.parseFloat(args[current_arg]);
        current_arg++;

        String file_out = args[current_arg];
        current_arg++;

        try {
            Fits fits = new Fits(new File(file_in));

            K2Preprocessor preprocessor = new K2Preprocessor(fits);
            float[][][] data = preprocessor.read();

            float[] locator_args = {scalar};
            BinaryLocator locator = new BinaryLocator(data, BinaryLocatorThresholdType.MEAN_SCALED, locator_args);
            locator.initialize();
            BrightBodyList[] bodies = locator.locate();

            BrightBodyList frame_bodies = bodies[frame];
            frame_bodies.sortByArea();

            FileWriter writer = new FileWriter(new File(file_out));

            int i = 0;
            for(BrightBody body : frame_bodies) {
                if(i == max_object_count) {
                    break;
                }

                writer.write(body.centroid.x + "," + body.centroid.y + "\n");
                i++;
            }
            writer.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
