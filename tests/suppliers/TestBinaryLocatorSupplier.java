package suppliers;

import mains.LFBinBaseMassFixedTime.BinaryLocatorSupplier;
import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import preprocess.K2Preprocessor;
import preprocess.Preprocessor;
import mains.LFBinBaseMassFixedTime.BinaryLocatorMassType;

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
    public void testMeanScaledSuppliesCorrectNumber() {
        BinaryLocatorSupplier supplier = new BinaryLocatorSupplier(data, DEFAULT_MASS_TYPE, DEFAULT_MASS_ARGS);
        int count = 0;
        while(!supplier.empty()) {
            count++;
            supplier.popLocator();
        }
        Assert.assertEquals(7 , count);
    }
}
