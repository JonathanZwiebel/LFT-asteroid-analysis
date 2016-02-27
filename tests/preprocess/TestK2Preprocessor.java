package preprocess;

import org.junit.Test;

import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;


/**
 * @author Jonathan Zwiebel
 * @version 1/29/16
 *
 * Experimental Unit tests to cover the K2Preprocessor
 */
public class TestK2Preprocessor {
    @Test
    public void testGoodRawDataDoesNotThrowFitsException() {
        boolean caught = false;
        try{
            new K2Preprocessor(new Fits(new File("data\\905.fits")));
        }
        catch(FitsException e) {
            caught = true;
        }
        Assert.assertFalse("K2Preprocessor through FitsException on good data", caught);
    }

    @Test
    public void testBadRawDataThrowsException() {
        boolean caught = false;
        try{
            new K2Preprocessor(new Fits(new File("data\\fake.fits")));
        }
        catch(FitsException e) {
            caught = true;
        }
        Assert.assertTrue("K2Preprocessor did not through Exception on reading bad data", caught);
    }

    @Test
    public void testArrayReadNotNull() throws FitsException, IOException {
        Preprocessor test_preprocessor = new K2Preprocessor(new Fits(new File("data\\905.fits")));
        Assert.assertNotNull("K2Preprocessor read returned null", test_preprocessor.read());
    }

    @Test
    public void testDataReadCorrectly() {
        Assert.fail("not yet implemented");
    }
}
