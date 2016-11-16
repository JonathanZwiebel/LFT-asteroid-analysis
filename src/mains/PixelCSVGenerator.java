package mains;

import core.preprocess.K2Preprocessor;
import core.preprocess.Preprocessor;
import nom.tam.fits.Fits;

import java.io.File;
import java.io.FileWriter;

/**
 * A top level runnable type that extracts a given pixel .csv from a K2 FITS file
 *
 * @author Jonathan Zwiebel
 * @version 15 November 2016
 */
public class PixelCSVGenerator {
    public static void run(String[] args) throws Exception {
        assert args[0].equals("PIXEL_CSV_GENERATOR");

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
}
