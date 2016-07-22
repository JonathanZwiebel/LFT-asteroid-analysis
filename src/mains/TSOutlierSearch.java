package mains;

import core.preprocess.K2Preprocessor;
import core.preprocess.Preprocessor;
import nom.tam.fits.Fits;

import java.io.File;
import java.io.FileWriter;

/**
 * A top level runnable type for generating a directory with .csvs of all of the Bonferroni outliers in the time series
 * for each pixel over a given range.
 *
 * @author Jonathan Zwiebel
 * @version 22 July 2016
 *
 * TODO: x and y-position reflections may only be valid in the case of square images
 */
public class TSOutlierSearch {
    public static void run(String[] args) {
        assert args[0].equals("TS_OUTLIER_SEARCH");

        int current_arg = 1;

        String file_in = args[current_arg];
        current_arg++;

        int first_index = Integer.parseInt(args[current_arg]);
        current_arg++;

        int last_index = Integer.parseInt(args[current_arg]);
        current_arg++;

        String rscript = args[current_arg];
        current_arg++;

        String directory_out = args[current_arg];
        current_arg++;

        try {
            Fits fits = new Fits(new File(file_in));

            Preprocessor preprocessor = new K2Preprocessor(fits);
            float[][][] data = preprocessor.read();


            for(int x = 0; x < data[0].length; x++) {
                for(int y = 0; y < data[0][0].length; y++){
                    FileWriter writer = new FileWriter(new File(directory_out + "/" + x + "_" + y + ".csv"));
                    int x_final = data[0][0].length - 1 - y;
                    int y_final = x;
                    for(int i = 0; i < data.length; i++) {
                        writer.write(i + "," + data[i][x_final][y_final] + "\n");
                    }
                    writer.close();
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
