package suppliers;

import brightbodies.BrightBodyList;
import locate.Locator;
import mains.LFBinaryBaselineMass.BinaryLocatorSupplier;
import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import preprocess.K2Preprocessor;
import preprocess.Preprocessor;
import mains.LFBinaryBaselineMass.BinaryLocatorMassType;

import java.io.File;
import java.io.IOException;

/**
 * This class tests the BinaryLocatorSupplier class by attempting to create various BinaryLocators
 *
 * @author Jonathan Zwiebel
 * @version 14 April 2016
 */
public class TestBinaryLocatorSupplier {
    private static final String DEFAULT_FILE_LOCATION = "data/905.fits";
    private static final BinaryLocatorMassType DEFAULT_MASS_TYPE = BinaryLocatorMassType.MEAN_SCALED_RANGE;
    private static final float[] DEFAULT_MASS_ARGS = {0.1f, -3.0f, 3.0f};
    private static float[][][] data; // TODO: Only calculate once

    @BeforeClass
    public static void beforeOnce() throws FitsException, IOException {
        Fits fits = new Fits(new File(DEFAULT_FILE_LOCATION));
        Preprocessor preprocessor = new K2Preprocessor(fits);
        data = preprocessor.read();
    }

    @Before
    public void before() {
    }

    @Test
    public void testBinaryLocatorSupplierConstructs() {
       BinaryLocatorSupplier supplier = new BinaryLocatorSupplier(data, DEFAULT_MASS_TYPE, DEFAULT_MASS_ARGS);
        Assert.assertNotNull(supplier);
    }

    @Test
    public void testMeanScaledSuppliesCorrectCount() {
        BinaryLocatorSupplier supplier = new BinaryLocatorSupplier(data, DEFAULT_MASS_TYPE, DEFAULT_MASS_ARGS);
        int count = 0;
        while(!supplier.empty()) {
            count++;
            supplier.popLocator();
        }
        Assert.assertEquals((int) (DEFAULT_MASS_ARGS[2] - DEFAULT_MASS_ARGS[1] + 1), count);
    }

    @Test
    public void testEmptyPollingMethod() {
        BinaryLocatorSupplier supplier = new BinaryLocatorSupplier(data, DEFAULT_MASS_TYPE, DEFAULT_MASS_ARGS);
        for(int i = 0; i < (int) (DEFAULT_MASS_ARGS[2] - DEFAULT_MASS_ARGS[1] + 1); i++) {
            Assert.assertFalse(supplier.empty());
            supplier.popLocator();
        }
        Assert.assertTrue(supplier.empty());
    }

    @Test
    public void testSupplierGivesNullWhenEmpty() {
        BinaryLocatorSupplier supplier = new BinaryLocatorSupplier(data, DEFAULT_MASS_TYPE, DEFAULT_MASS_ARGS);
        for(int i = 0; i < (int) (DEFAULT_MASS_ARGS[2] - DEFAULT_MASS_ARGS[1] + 1); i++) {
            supplier.popLocator();
        }
        Locator bad_locator = supplier.popLocator();
        Assert.assertNull(bad_locator);
    }

    @Test
    public void testMeanScaledSuppliesValidLocators() {
        BinaryLocatorSupplier supplier = new BinaryLocatorSupplier(data, DEFAULT_MASS_TYPE, DEFAULT_MASS_ARGS);
        while(!supplier.empty()) {
            Locator locator = supplier.popLocator();
            locator.initialize();
            BrightBodyList[] bodies = locator.locate();
            Assert.assertEquals(bodies.length, data.length); // checks length of the locators
            Assert.assertNotEquals(bodies[0].size(), 0); // tests that there is at least one body found
            Assert.assertNotEquals(bodies[data.length / 2].size(), 0); // tests that there is at least one body found
            Assert.assertNotEquals(bodies[data.length - 1].size(), 0); // tests that there is at least one body found
            Assert.assertTrue(bodies[data.length / 2].get(0).body.length > 5); // test that the first bright body is larger than 5
            Assert.assertTrue(bodies[data.length / 2].get(0).body.length < 1000); // test that the first bright body is smaller than 1000
            Assert.assertTrue(bodies[data.length / 2].get(0).area > 10);
            Assert.assertTrue(bodies[data.length / 2].get(0).area < Integer.MAX_VALUE);
        }
    }

    @Test
    public void testMeanScaledLocatorsHaveCorrectParameters() {
        BinaryLocatorSupplier supplier = new BinaryLocatorSupplier(data, DEFAULT_MASS_TYPE, DEFAULT_MASS_ARGS);
        Locator first_locator = supplier.popLocator();
        for(int i = 1; i < (int) (DEFAULT_MASS_ARGS[2] - DEFAULT_MASS_ARGS[1]); i++) {
            supplier.popLocator();
        }
        Locator second_locator = supplier.popLocator();

        first_locator.initialize();
        second_locator.initialize();
        BrightBodyList[] first_bodies = first_locator.locate();
        BrightBodyList[] second_bodies = second_locator.locate();

        Assert.assertTrue(first_bodies[0].size() > second_bodies[0].size());
        Assert.assertTrue(first_bodies[data.length / 2].size() > second_bodies[data.length / 2].size());
        Assert.assertTrue(first_bodies[data.length - 2].size() > second_bodies[data.length - 2].size());

        first_bodies[0].sortByArea();
        second_bodies[0].sortByArea();
        Assert.assertTrue(second_bodies[0].get(0).area / second_bodies[0].get(0).body.length > first_bodies[0].get(0).area / first_bodies[0].get(0).body.length);
    }

    @Test
    public void testSingleRun() throws NullPointerException {
        BinaryLocatorSupplier supplier = new BinaryLocatorSupplier(data, DEFAULT_MASS_TYPE, DEFAULT_MASS_ARGS);
        Locator first = supplier.popLocator();
        first.initialize();
        BrightBodyList[] bodies_first = first.locate();
        Assert.assertNotNull(bodies_first);

        for(int i = 1; i < 6; i++) {
            supplier.popLocator();
        }

        Locator second = supplier.popLocator();
        second.initialize();
        BrightBodyList[] bodies_second = second.locate();
        Assert.assertNotNull(bodies_second);
    }
}
