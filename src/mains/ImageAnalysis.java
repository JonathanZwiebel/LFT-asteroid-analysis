package mains;

import core.preprocess.K2Preprocessor;
import core.preprocess.Preprocessor;
import nom.tam.fits.Fits;
import stats.MedianValue;

import java.io.File;
import java.io.FileWriter;

/**
 * A top level runnable type for determining statistics such as median and mean FLUX of the image over time.
 *
 * @author Jonathan Zwiebel
 * @version 12 July 2016
 */
public class ImageAnalysis {
    public static void run(String[] args) {
        assert args[0].equals("IMAGE_ANALYSIS");

        int current_arg = 1;

        String file_in = args[current_arg];
        current_arg++;

        String file_out = args[current_arg];
        current_arg++;

        try {
            Fits fits = new Fits(new File(file_in));

            FileWriter writer = new FileWriter(new File(file_out));

            Preprocessor preprocessor = new K2Preprocessor(fits);
            float[][][] data = preprocessor.read();

            for(int i = 0; i < data.length; i++) {
                writer.write(i + "," + MedianValue.medianValue(data[i]) + "\n");
            }
            writer.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
