import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;
import org.junit.Assert;
import org.junit.Test;
import preprocessing.K2Preprocessor;
import preprocessing.Preprocessor;

import java.io.File;


/**
 * @author Jonathan Zwiebel
 * @version 1/25/16
 * Experimental JUnit test
 */
public class TestK2Preprocessor {
    @Test
    public void testGoodRawDataDoesNotThrowFitsException() {
        boolean caught = false;
        try{
            Preprocessor test_preprocessor = new K2Preprocessor(new Fits(new File("data\\905.fits")));
        }
        catch(FitsException e) {
            caught = true;
        }
        Assert.assertFalse(caught);
    }



    @Test
    public void testBadRawDataThrowsException() {
        boolean caught = false;
        try{
            Preprocessor test_preprocessor = new K2Preprocessor(new Fits(new File("data\\fake.fits")));
        }
        catch(FitsException e) {
            caught = true;
        }
        Assert.assertTrue(caught);
    }

    @Test
    public void testArrayReadNotNull() throws FitsException,  {
        Preprocessor test_preprocessor = new K2Preprocessor(new Fits(new File("data\\905.fits")));
        test_preprocessor.read();

    }
}
