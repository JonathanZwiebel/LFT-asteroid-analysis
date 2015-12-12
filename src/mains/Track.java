package mains;

import brightbodies.BrightBodyList;
import locating.BinaryLocator;
import locating.Locator;
import nom.tam.fits.Fits;
import preprocessing.K2Preprocessor;
import preprocessing.Preprocessor;

import java.io.File;

/**
 * @author Jonathan Zwiebel
 * @version November 30th, 2015
 * TODO: Make command-line argument based
 */
public class Track {
    public static final String DATA_FILENAME =          "c:\\users\\admin\\desktop\\k2\\raw\\ktwo200000852-c00_lpd-targ.fits";
    public static final String TEXT_DIRECTORY =         "data\\text\\852";
    public static final String SERIALIZED_DIRECTORY =   "data\\ser\\852";
    public static final String CSV_DIRECTORY =          "data\\csv\\852";
    public static final String MASS_CSV_FILENAME =      "data\\csv\\852MassAreaSorted.csv";
    public static final String COLUMN =                 "FLUX";

    public static void main(String[] args) {
        try {
            Preprocessor preprocessor = new K2Preprocessor(new Fits(new File(DATA_FILENAME)));
            float[][][] data = preprocessor.read();

            Locator locator = new BinaryLocator(data);
            locator.initialize();
            BrightBodyList[] bodies = locator.locate();
        }
        catch(Exception e ) {
            e.printStackTrace();
        }
    }
}
