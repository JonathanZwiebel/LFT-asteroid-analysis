package mains;

import brightbodies.FixedTimeBrightBodyFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A top level runnable type that takes in a directory of outlier .csv files and outputs a FTBBF file which contains
 * groups of adjacent pixels found in the outlier files.
 *
 *
 * Input File Format:
 *  dir/x_y_blockname.csv
 * 
 * Argument Order: 
 *  0 - "OUTLIER_CSV_TO_GROUPS"
 *  1 - Directory containing outlier .csv files for individual pixels
 *  2 - Block name for outlier .csv files
 *  3 - Filename for output location of .txt file with all FTBBFs (Do not include ".txt")
 *  4 - Filename for output location of .csv file with FTBBF counts by size (Do not include ".csv")
 *  
 * @author Jonathan Zwiebel
 * @version 26 January 2017
 */
public class OutlierCSVToGroups {
    public static final float[] CUTOFF_VALUES = {0.0001f, 0.00033f, 0.001f, 0.0033f, 0.01f, 0.033f, 0.1f, 0.33f, 1f};

    public static void run(String[] args) throws Exception {
        // *****************
        // BEGIN INPUT STAGE
        // *****************

        assert args[0].equals("OUTLIER_CSV_TO_GROUPS");

        int current_arg = 1;

        // The directory of the outlier csv files
        String dir_in = args[current_arg];
        current_arg++;

        // The block of outlier csvs to check
        String block = args[current_arg];
        current_arg++;

        // Location to dump txt of sorted FTBBF data
        String file_out = args[current_arg];
        current_arg++;

        // Location to dump csv of counted FTBBF size frequencies
        String size_file_out  = args[current_arg];
        current_arg++;

        // ***************
        // END INPUT STAGE
        // ***************

        // ************************
        // BEGIN DATA LOADING STAGE
        // ************************

        // An array that contains all pixels in a frame (STC) which contained an outlier
        ArrayList<SpaceTimeCoordinate> hits[] = new ArrayList[CUTOFF_VALUES.length];
        for(int i = 0; i < CUTOFF_VALUES.length; i++) {
            hits[i] = new ArrayList<>();
        }

        // The maximum j-value that contains valid targets
        int jlimit = -1;

        // The coordiantes of the location to check
        int i = 0, j = 0;

        System.out.println("Starting while loop to iterate through values");
        // TODO: Maybe not a while loop
        while(true) {
            String filename_in = dir_in + "/" + i + "_" + j + "_" + block + ".csv";

            // Sets up the i and j values to always get a valid target file
            File file_in = new File(filename_in);
            if(!file_in.exists()) {
                if(i == 0 && j == 0) {
                    System.err.println("Illegal block name passed to OutlierCSVToGroups");
                    System.exit(1);
                }
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

            System.out.println("Reading file at " + i + ", " + j);
            File file = new File(filename_in);
            BufferedReader reader = new BufferedReader(new FileReader(file));

            // Adds all of the outliers from a given outlier file to the STC array list
            // Checks the p-value against all cutoff values and adds the coordinates depending on if it below a cutoff value
            String line = null;
            while((line = reader.readLine()) != null) {
                int comma_index = line.indexOf(',');
                int timestamp = Integer.parseInt(line.substring(0, comma_index));
                float adjusted_p_value = Float.parseFloat(line.substring(comma_index + 1));
                for(int cutoff_index = 0; cutoff_index < CUTOFF_VALUES.length; cutoff_index++) {
                    if(adjusted_p_value < CUTOFF_VALUES[cutoff_index]) {
                        hits[cutoff_index].add(new SpaceTimeCoordinate(i, j, timestamp));
                    }
                }
            }

            ++j;
          System.out.println("Completed outliers extraction from file at " + i + ", " + j);
        }

        // **********************
        // END DATA LOADING STAGE
        // **********************

        for(int cutoff_index = 0; cutoff_index < CUTOFF_VALUES.length; cutoff_index++) {
            // **********************
            // BEGIN PROCESSING STAGE
            // **********************

            // The array list of factories which contain candidates made up of adjacent outliers in one frame
            ArrayList<FixedTimeBrightBodyFactory> factory_list = new ArrayList<>();

            System.out.println("Starting while loop to create factories from " + hits[cutoff_index].size() + " STCs at cutoff of " + CUTOFF_VALUES[cutoff_index]);
            int count = 1;
            while(hits[cutoff_index].size() > 0) {
                SpaceTimeCoordinate stc = hits[cutoff_index].get(0);
                FixedTimeBrightBodyFactory factory = new FixedTimeBrightBodyFactory(stc.t);
                factory.addPixel(stc.x, stc.y);
                hits[cutoff_index].remove(stc);

                 // aAPtFTF function takes the factory created, the set of all outliers, and values for the newest outlier
                addAdjacentPixelsToFixedTimeFactory(factory, hits[cutoff_index], stc);
                factory_list.add(factory);
                ++count;
            }
            System.out.println("Complete construction of " + count + " factories");

            Collections.sort(factory_list);
            Collections.reverse(factory_list);

            // ********************
            // END PROCESSING STAGE
            // ********************

            // ******************
            // BEGIN OUTPUT STAGE
            // ******************

            File out = new File(file_out + "_" + CUTOFF_VALUES[cutoff_index] + ".txt");
            FileWriter file_writer = new FileWriter(out);
            BufferedWriter buffered_writer = new BufferedWriter(file_writer);
            buffered_writer.write("This file groups adjacent outliers in single frames into targets.\n");
            buffered_writer.write("Data from this file is taken from " + dir_in + " | Block: " + block + "\n");
            buffered_writer.write("A value of " + CUTOFF_VALUES[cutoff_index] + " was used as adjusted p-value cutoff.\n\n");

            // Assumed that the default value is 0
            // 0 to 124 is sizes 1 to 125, 125 is size larger than 125

            int MAX_CHECKED_SIZE = 125;

            int[] count_at_each_size = new int[MAX_CHECKED_SIZE + 1];
            int[] count_at_size_or_larger = new int[MAX_CHECKED_SIZE + 1];
            ArrayList<Integer> times = new ArrayList();

            for(FixedTimeBrightBodyFactory factory : factory_list) {
                buffered_writer.write(factory.toString());
                if(!times.contains(factory.getTimestamp())) {
                    times.add(factory.getTimestamp());
                }
                if(factory.size() > MAX_CHECKED_SIZE) {
                    ++count_at_each_size[MAX_CHECKED_SIZE];
                    for(int cum_index = 0; cum_index < MAX_CHECKED_SIZE + 1; cum_index++) {
                        ++count_at_size_or_larger[cum_index];
                    }
                }
                else {
                    ++count_at_each_size[factory.size() - 1];
                    for(int cum_index = 0; cum_index <= factory.size() - 1; cum_index++) {
                        ++count_at_size_or_larger[cum_index];
                    }
                }
            }

            buffered_writer.write("There were " + times.size() + " unique timestamps with asteroids.\n");
            System.out.println("Factories found at " + times.size() + " unique time stamps.");

            File size_out = new File(size_file_out + "_" + CUTOFF_VALUES[cutoff_index] + ".csv");
            FileWriter size_file_writer = new FileWriter(size_out);
            BufferedWriter size_buffered_writer = new BufferedWriter(size_file_writer);

            for(int size_check = 0; size_check < MAX_CHECKED_SIZE; size_check++) {
                size_buffered_writer.write(size_check + "," + count_at_each_size[size_check] + "," + count_at_size_or_larger[size_check] + "\n");
            }
            size_buffered_writer.write((MAX_CHECKED_SIZE + 1) + "+, " + count_at_each_size[MAX_CHECKED_SIZE]);

            size_buffered_writer.close();
            buffered_writer.close();
            file_writer.close();

            // ****************
            // END OUTPUT STAGE
            // ****************
        }
    }

    /**
     * Recursively adds adjacent pixels with matching timestamps to the
     * @param factory FTBBF to add a found pixel
     * @param STCs Dirty set of SpaceTimeCoordinate values
     * @param source_STC SpaceTimeCoordinate to adjacency of
     * TODO[SPEED]: Very inefficient. Performs 9 checks that cycle through the entire STC list on each call.
     */
    private static void addAdjacentPixelsToFixedTimeFactory(FixedTimeBrightBodyFactory factory, ArrayList<SpaceTimeCoordinate> STCs, SpaceTimeCoordinate source_STC) {
        // Iterate through the coordiantes in the square of size 3 around source_STC
        for(int i = source_STC.x - 1; i <= source_STC.x + 1; i++) {
            for(int j = source_STC.y - 1; j <= source_STC.y + 1; j++) {

                // Iterate through all hits still remaining
                for(int stc_index = 0; stc_index < STCs.size(); stc_index++) {
                    SpaceTimeCoordinate stc = STCs.get(stc_index);

                    // If the hit being checked matches the specifications (coordinate and time) then add it to the factory and work recursively
                    if(stc.equals(i, j, source_STC.t)) {
                        factory.addPixel(i, j);

                        // Remove the hit that was just added
                        STCs.remove(stc);
                        stc_index--;
                        addAdjacentPixelsToFixedTimeFactory(factory, STCs, stc);
                    }
                }
            }
        }
    }

    /**
     * This inner class stores the location of a coordinate with an assigned time.
     * TODO: Searching through an array of unordered STCs is not efficient. Improve efficiency by ordering.
     */
    private static class SpaceTimeCoordinate {
        public final int x;
        public final int y;
        public final int t;

        /**
         * Constructs a SpaceTimeCoordinate value based on its spatial position and timestamp. This is similar to a 3D
         * Coordinate object. The primary difference in the immutability of the values.
         *
         * @param x x spatial value
         * @param y y spatial
         * @param t timestamp
         */
        public SpaceTimeCoordinate(int x, int y, int t) {
            this.x = x;
            this.y = y;
            this.t = t;
        }

        /**
         * Compares the x, y, and t values of this object to generic integer values
         * @param x x value
         * @param y y value
         * @param t timestamp
         * @return true if equal, false if unequal
         */
        public boolean equals(int x, int y, int t) {
            if(this.x == x && this.y == y && this.t == t) {
                return true;
            }
            return false;
        }

        /**
         * Compares two STCs by their x, y, and t values
         * @param stc SpaceTimeCoordinate object to compare to
         * @return true if equal, false if unequal
         */
        public boolean equals(SpaceTimeCoordinate stc) {
            if(this.x == stc.x && this.y == stc.y && this.t == stc.t) {
                return true;
            }
            return false;
        }
    }
}