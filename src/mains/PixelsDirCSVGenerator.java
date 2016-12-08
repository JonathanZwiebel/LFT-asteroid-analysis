package mains;

import core.preprocess.K2Preprocessor;
import core.preprocess.Preprocessor;
import nom.tam.fits.Fits;

import java.io.File;
import java.io.FileWriter;

/**
 * A top level runnable type that extracts a directory of  pixel .csvs from a K2 FITS file
 * A seperate .csv is generate for each pixel
 *
 * @author Jonathan Zwiebel
 * @version 8 December 2016
 */
public class PixelsDirCSVGenerator {
    public static void run(String[] args) throws Exception {
        assert args[0].equals("PIXELS_DIR_CSV_GENERATOR");

        int current_arg = 1;

        String file_in = args[current_arg];
        current_arg++;
        String dir_out = args[current_arg];
        current_arg++;

        Fits fits = new Fits(new File(file_in));
        Preprocessor preprocessor = new K2Preprocessor(fits);
        float[][][] data = preprocessor.read();

        for(int i = 0; i < data.length; i++) {
            for(int j = 0; j < data[0].length; j++) {
                String filename_out = dir_out + "/" + i + "_" + j + ".csv";
                File out = new File(filename_out);
                FileWriter writer = new FileWriter(out);
                int x_reflected = data[0].length - 1 - j;
                int y_reflected = i;

                for(int t = 0; t < data.length; t++) {
                    writer.write(t + "," + data[t][x_reflected][y_reflected] + "\n");
                }
                writer.close();
            }
        }
    }
}
