package mains;

import brightbodies.BrightBodyLinkedSet;
import brightbodies.BrightBodyList;
import core.filter.BaselineFrameGenerationMethod;
import core.filter.BaselineMobilityFilter;
import core.filter.MobilityFilter;
import core.locate.binary.BinaryLocator;
import core.locate.binary.BinaryLocatorThresholdType;
import core.locate.Locator;
import nom.tam.fits.Fits;
import core.preprocess.K2Preprocessor;
import core.preprocess.Preprocessor;
import core.track.EvenSpaceTripleTracker;
import core.track.Tracker;

import java.io.File;

/**
 * A top-level runnable type that will run locate-track-filter with a single set of parameters over a single data set
 * and produce a simple output with non-analytic path descriptions. Only runs with mean-scaled binary locators and
 * mean-scaled baseline binary mobility filters.
 *
 * @author Jonathan Zwiebel
 * @version June 15th, 2016
 */
public class LFTSingleBasic {
    /**
     * Main method to be run for program execution
     * @param args path, target threshold scaling value, similarity threshold, baseline threshold scaling value
     */
    public static void run(String[] args) {
        assert args[0].equals("LFT_SINGLE_BASIC");
        String path = args[1];
        float target_threshold_scaling_value = Float.parseFloat(args[2]);
        float similarity_threshold = Float.parseFloat(args[3]);
        float baseline_threshold_scaling_value = Float.parseFloat(args[4]);

        try {
            Fits fits = new Fits(new File(path));

            Preprocessor preprocessor = new K2Preprocessor(fits);
            float[][][] data = preprocessor.read();

            Locator locator = new BinaryLocator(data, BinaryLocatorThresholdType.MEAN_SCALED, target_threshold_scaling_value);
            locator.initialize();
            BrightBodyList[] bright_bodies = locator.locate();

            MobilityFilter filter = new BaselineMobilityFilter(bright_bodies, data, similarity_threshold, BaselineFrameGenerationMethod.BINARY_LOCATOR_MEAN_SHIFTED, baseline_threshold_scaling_value);
            BrightBodyList[][] sorted_bodies = filter.filter();

            Tracker tracker = new EvenSpaceTripleTracker(sorted_bodies[BaselineMobilityFilter.MBB_INDEX]);
            BrightBodyLinkedSet[] linked_sets = tracker.track();

            for(BrightBodyLinkedSet linked_set : linked_sets) {
                System.out.println(linked_set);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
