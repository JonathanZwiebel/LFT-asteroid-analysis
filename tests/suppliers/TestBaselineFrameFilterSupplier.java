package suppliers;

import brightbodies.BrightBodyList;
import core.filter.MobilityFilter;
import org.junit.Assert;
import core.locate.BinaryLocator;
import core.locate.BinaryLocatorThresholdType;
import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;
import org.junit.BeforeClass;
import org.junit.Test;
import core.preprocess.K2Preprocessor;
import core.preprocess.Preprocessor;
import mains.LFBinaryBaselineMass.BaselineFrameFilterSupplier;
import mains.LFBinaryBaselineMass.BaselineFrameMassType;


import java.io.File;
import java.io.IOException;

/**
 * This class tests the BaselineFrameFilterSupplier class by attempting to create various BaselineFrameFilters
 *
 * @author Jonathan Zwiebel
 * @version 17 April 2016
 */
public class TestBaselineFrameFilterSupplier {
    public static final String DEFAULT_FILE_LOCATION = "data/905.fits";
    public static final BinaryLocatorThresholdType DEFAULT_LOCATOR_THRESHOLD_TYPE = BinaryLocatorThresholdType.MEAN;
    public static final float DEFAULT_SIMILARITY_THRESHOLD = 0.6f;
    public static final BaselineFrameMassType DEFAULT_BASELINE_FRAME_MASS_TYPE = BaselineFrameMassType.MEAN_SCALED_RANGE;
    public static final float[] DEFAULT_MASS_ARGS = {0.1f, -3.0f, 3.0f};

    public static float[][][] data;
    public static BrightBodyList[] bodies;
    public static BinaryLocator locator;

    @BeforeClass
    public static void beforeOnce() throws FitsException, IOException {
        Fits fits = new Fits(new File(DEFAULT_FILE_LOCATION));
        Preprocessor preprocessor = new K2Preprocessor(fits);
        data = preprocessor.read();
        locator = new BinaryLocator(data, DEFAULT_LOCATOR_THRESHOLD_TYPE);
        locator.initialize();
        bodies = locator.locate();
    }

    @Test
    public void testBaselineFrameFilterSupplierConstructs() {
        BaselineFrameFilterSupplier supplier = new BaselineFrameFilterSupplier(bodies, data, DEFAULT_SIMILARITY_THRESHOLD, DEFAULT_BASELINE_FRAME_MASS_TYPE, DEFAULT_MASS_ARGS);
        Assert.assertNotNull(supplier);
    }

    @Test
    public void testMeanScaledSuppliesCorrectCount() {
        BaselineFrameFilterSupplier supplier = new BaselineFrameFilterSupplier(bodies, data, DEFAULT_SIMILARITY_THRESHOLD, DEFAULT_BASELINE_FRAME_MASS_TYPE, DEFAULT_MASS_ARGS);
        int count = 0;
        while(!supplier.empty()) {
            count++;
            supplier.popFilter();
        }
        Assert.assertEquals((int) DEFAULT_MASS_ARGS[2] - (int) DEFAULT_MASS_ARGS[1] + 1, count);
    }

    @Test
    public void testEmptyPollingMethod() {
        BaselineFrameFilterSupplier supplier = new BaselineFrameFilterSupplier(bodies, data, DEFAULT_SIMILARITY_THRESHOLD, DEFAULT_BASELINE_FRAME_MASS_TYPE, DEFAULT_MASS_ARGS);
        for(int i = (int) DEFAULT_MASS_ARGS[1]; i <= DEFAULT_MASS_ARGS[2]; i++) {
            supplier.popFilter();
        }
        Assert.assertTrue(supplier.empty());
    }

    @Test
    public void testSupplierGivesNullWhenEmpty() {
        BaselineFrameFilterSupplier supplier = new BaselineFrameFilterSupplier(bodies, data, DEFAULT_SIMILARITY_THRESHOLD, DEFAULT_BASELINE_FRAME_MASS_TYPE, DEFAULT_MASS_ARGS);
        for(int i = (int) DEFAULT_MASS_ARGS[1]; i <= DEFAULT_MASS_ARGS[2]; i++) {
            supplier.popFilter();
        }
        MobilityFilter bad_filter = supplier.popFilter();
        Assert.assertNull(bad_filter);
    }

    @Test
    public void testMeanScaledSuppliesValidSupplier() {
        BaselineFrameFilterSupplier supplier = new BaselineFrameFilterSupplier(bodies, data, DEFAULT_SIMILARITY_THRESHOLD, DEFAULT_BASELINE_FRAME_MASS_TYPE, DEFAULT_MASS_ARGS);
        MobilityFilter filter = supplier.popFilter();
        BrightBodyList[][] sorted_bodies = filter.filter();

        Assert.assertNotNull(sorted_bodies); // a valid sorted bodies
        Assert.assertEquals(3, sorted_bodies.length); // there are 3 sets of bodies (immobile, mobile, and noise)
        Assert.assertEquals(data.length, sorted_bodies[0].length); // each set is an array of the correct length
        Assert.assertTrue(sorted_bodies[0][0].size() > 0); // there is at least 1 immobile in frame 0
        Assert.assertTrue(sorted_bodies[1][0].size() > 0); // there is at least 1 mobile in frame 0
        Assert.assertTrue(sorted_bodies[2][0].size() > 0); // there is at least 1 noise in frame 0
        Assert.assertEquals(bodies[0].size(), sorted_bodies[0][0].size() + sorted_bodies[1][0].size() + sorted_bodies[2][0].size()); // all of the bodies got sorted
        // TODO: Add a check to make sure each body has actually been sorted
    }

    @Test
    public void testMeanScaledBaselineFrameFiltersHaveCorrectParameters() {
        BaselineFrameFilterSupplier supplier = new BaselineFrameFilterSupplier(bodies, data, DEFAULT_SIMILARITY_THRESHOLD, DEFAULT_BASELINE_FRAME_MASS_TYPE, DEFAULT_MASS_ARGS);
        MobilityFilter first = supplier.popFilter();
        for(int i = (int) DEFAULT_MASS_ARGS[1] + 1; i < (int) DEFAULT_MASS_ARGS[2]; i++){
            supplier.popFilter();
        }
        MobilityFilter second = supplier.popFilter();
        Assert.assertTrue(supplier.empty());

        BrightBodyList[][] first_sorted_bodies = first.filter();
        BrightBodyList[][] second_sorted_bodies = second.filter();

        // if all baseline bodies are matched with an immobile then the filter with a lower threshold should
        // produce more immobiles
        Assert.assertTrue(first_sorted_bodies[0][0].size() > second_sorted_bodies[0][0].size());
    }
}
