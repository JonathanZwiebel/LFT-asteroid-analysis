package track;

import brightbodies.BrightBodyList;
import filter.BaselineFrameGenerationMethod;
import filter.BaselineMobilityFilter;
import filter.MobilityFilter;
import locate.BinaryLocator;
import locate.BinaryLocatorThresholdType;
import locate.Locator;
import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;
import org.junit.BeforeClass;
import org.junit.Test;
import preprocess.K2Preprocessor;
import preprocess.Preprocessor;

import java.io.File;
import java.io.IOException;

/**
 * This class contains a series of unit tests for the EvenSpaceTripleTracker class. Assumes a BinaryTracker locate step
 * and a BaselineFrameBinaryTracker mobility filtration step.
 *
 * @author Jonathan Zwiebel
 * @version 16 June 2016
 */
public class TestEvenSpaceTripleTracker {
    private static final String FILE = "data\\905.fits";
    private static final float TARGET_SCLAER = 0.95f;
    private static final float SIMILARITY_THRESHOLD = 0.4f;
    private static final float BASELINE_SCALER = 0.95f;

    private static BrightBodyList[] mobile_bodies_;

    @BeforeClass
    public static void beforeOnce() throws FitsException, IOException{
        Fits fits = new Fits(new File(FILE));

        Preprocessor preprocessor = new K2Preprocessor(fits);
        float[][][] data = preprocessor.read();

        Locator locator = new BinaryLocator(data, BinaryLocatorThresholdType.MEAN_SCALED, TARGET_SCLAER);
        locator.initialize();
        BrightBodyList[] bodies = locator.locate();

        MobilityFilter filter = new BaselineMobilityFilter(bodies, data, SIMILARITY_THRESHOLD, BaselineFrameGenerationMethod.BINARY_LOCATOR_MEAN_SCALED, BASELINE_SCALER);
        BrightBodyList[][] sorted_bodies = filter.filter();

        mobile_bodies_ = sorted_bodies[BaselineMobilityFilter.MBB_INDEX];
    }
}
