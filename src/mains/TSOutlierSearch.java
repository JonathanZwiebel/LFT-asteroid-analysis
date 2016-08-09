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


            for(int x = 0; x < data[0].length - 46; x++) {
                for(int y = 0; y < data[0][0].length - 46; y++){
                    String raw_file = directory_out + "/" + x + "_" + y + ".csv";
                    FileWriter writer = new FileWriter(new File(raw_file));
                    int x_final = data[0][0].length - 1 - y;
                    int y_final = x;
                    for(int i = 0; i < data.length; i++) {
                        writer.write(i + "," + data[i][x_final][y_final] + "\n");
                    }
                    writer.close();

                    String file_out = directory_out + "/outliers_" + x + "_" + y + ".csv";
                    // Included so that console provides feedback
                    System.out.println("Running rscript for " + x + ", " + y);

                    // Calls the rscript with parameters for the input csv, starting index, ending index, and output file 
                    Runtime.getRuntime().exec("rscript.exe " + rscript + " " + raw_file + " " + first_index + " " + last_index + " " + file_out);

                    // Sleep for 4 seconds to allow r script to execute
                    Thread.sleep(4000);
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
