package mains;

import core.preprocess.K2Preprocessor;
import core.preprocess.Preprocessor;
import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A top level runnable type that is used to perform analysis on the FLUX value of a single pixel over time.
 *
 * @author Jonathan Zwiebel
 * @version 12 July 2016
 */
public class PixelAnalysis {
    public static void run(String[] args) {
        try {
            assert args[0].equals("PIXEL_ANALYSIS");

            int current_arg = 1;

            String file_in = args[current_arg];
            current_arg++;
            int x_position = Integer.parseInt(args[current_arg]);
            current_arg++;
            int y_position = Integer.parseInt(args[current_arg]);
            current_arg++;
            String file_out = args[current_arg];
            current_arg++;

            Fits fits = new Fits(new File(file_in));
            Preprocessor preprocessor = new K2Preprocessor(fits);

            float[][][] data = preprocessor.read();

            File out = new File(file_out);
            FileWriter writer = new FileWriter(out);

            int x_reflected = data[0].length - 1 - y_position;
            int y_reflected = x_position;

            for(int i = 0; i < data.length; i++) {
                writer.write(i + "," + data[i][x_reflected][y_reflected] + "\n");
            }
            writer.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
