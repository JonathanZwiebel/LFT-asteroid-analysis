package mains;

import java.io.*;

/**
 * A top level runnable type that takes in a file of pixel csvs in the form x_y.csv and outputs iterative time series
 * outliers at an alpha = .05 level using the Bonfferoni FWER correction
 *
 * @author Jonathan Zwiebel
 * @version 15 November 2016
 * TODO: Allow for different set of pixels than rectangle that starts at 0, 0
 */
public class PixelsToIterativeOutliers {
    public static void run(String[] args) throws IOException, InterruptedException {
        assert args[0].equals("PIXEL_TO_ITERATIVE_OUTLIERS");

        int current_arg = 1;

        String rscript = args[current_arg];
        current_arg++;

        String dir_in = args[current_arg];
        current_arg++;

        int first_index = Integer.parseInt(args[current_arg]);
        current_arg++;

        int last_index = Integer.parseInt(args[current_arg]);
        current_arg++;

        String dir_out = args[current_arg];
        current_arg++;

        String block_signature = args[current_arg];
        current_arg++;

        int jlimit = -1;
        int i = 0, j = 0;

        // TODO: Don't use a while(true) loop
        while(true) {
            String filename_in = dir_in + "/" + i + "_" + j + ".csv";
            String filename_out = dir_out + "/" + i + "_" + j + "_" + block_signature + ".csv";
            String lockfilename = dir_out + "/" + i + "_" + j + "_" + block_signature + ".lftlock";

            File file_in = new File(filename_in);
            if(!file_in.exists()) {
                if(jlimit == -1) {
                    jlimit = j - 1;
                }
                if(j > jlimit) {
                    ++i;
                    j = 0;
                    continue;
                }
                break;
            }

            String command = "rscript.exe " + rscript + " " + filename_in + " " + first_index + " " + last_index + " "
                    + filename_out + " " + lockfilename;

            Runtime.getRuntime().exec(command);

            File lockfile = new File(lockfilename);
            do {
                Thread.sleep(100);
            }
            while(!lockfile.exists());
            lockfile.delete();

            ++j;
        }
    }
}
