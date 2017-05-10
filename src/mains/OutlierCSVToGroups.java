package mains;

import brightbodies.FixedTimeBrightBodyFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A top level runnable type that takes in a directory of outlier .csv files and outputs a series of FTBBF objects of
 * given a p-cutoff value and minimum size constraint
 *
 * Input File Format:
 *  dir/x_y_blockname.csv
 * 
 * Argument Order: 
 *  0 - "OUTLIER_CSV_TO_GROUPS"
 *  1 - Directory containing outlier .csv files for individual pixels
 *  2 - Block name for outlier .csv files
 *  3 - Maximum allowed p-value for individual pixels to be considered outliers
 *  4 - Minimum allowed size for adjacent group of pixels to be considered single-frame candidate
 *  
 * TODO: COPIED FROM PIXELS_TO_ITERATIVE_OUTLIERS
 *
 * @author Jonathan Zwiebel
 * @version 9 May 2017
 */
public class OutlierCSVToGroups {

    public static ArrayList<FixedTimeBrightBodyFactory> run(String[] args) throws Exception {
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

        // The p-value to use in the Bonferroni-corrected time series outlier test
        float p_cutoff = Float.parseFloat(args[current_arg]);
        current_arg++;

        // The minimum size to use as a cutoff for when an adjacent group of outliers can be considered a candidate
        float size_cutoff = Float.parseFloat(args[current_arg]);
        current_arg++;

        // The maximum number of candidates that can appear in a timestamp before it is thrown out
        float time_candidate_limit = Float.parseFloat(args[current_arg]);
        current_arg++;

        // ***************
        // END INPUT STAGE
        // ***************

        // ************************
        // BEGIN DATA LOADING STAGE
        // ************************

        // An array that contains all pixels in a frame (STC) which contained an outlier

        ArrayList hits = new ArrayList<SpaceTimeCoordinate>();

        // The maximum j-value that contains valid targets
        int jlimit = -1;

        // The coordinates of the location to check
        int i = 0, j = 0;

        System.out.println("Starting while loop to iterate through values");
        // TODO: Maybe not a while loop
        while(true) {
            String filename_in = dir_in + "/" + i + "_" + j + "_" + block + ".csv";

            // Sets up the i and j values to always get a valid target file
            File file_in = new File(filename_in);
            if(!file_in.exists()) {
                if(i == 0 && j == 0) {
                    System.err.println("Illegal block name passed to OutlierCSVSurvey");
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
            String line;
            while((line = reader.readLine()) != null) {
                int comma_index = line.indexOf(',');
                int timestamp = Integer.parseInt(line.substring(0, comma_index));
                float adjusted_p_value = Float.parseFloat(line.substring(comma_index + 1));
                // Only objects with p-values less than the p-cutoff will be used
                if(adjusted_p_value < p_cutoff) {
                    hits.add(new SpaceTimeCoordinate(i, j, timestamp));
                }
            }
            ++j;
          System.out.println("Completed outliers extraction from file at " + i + ", " + j);
        }

        // **********************
        // END DATA LOADING STAGE
        // **********************

        // **********************
        // BEGIN PROCESSING STAGE
        // **********************

        // The array list of factories which contain candidates made up of adjacent outliers in one frame
        ArrayList<FixedTimeBrightBodyFactory> factory_list = new ArrayList<>();

        System.out.println("Starting while loop to create factories from " + hits.size() + " STCs at cutoff of " + p_cutoff);
        int count = 1;
        while(hits.size() > 0) {
            SpaceTimeCoordinate stc = (SpaceTimeCoordinate) hits.get(0);
            FixedTimeBrightBodyFactory factory = new FixedTimeBrightBodyFactory(stc.t);
            factory.addPixel(stc.x, stc.y);
            hits.remove(stc);

             // aAPtFTF function takes the factory created, the set of all outliers, and values for the newest outlier
            addAdjacentPixelsToFixedTimeFactory(factory, hits, stc);
            factory_list.add(factory);
            ++count;
        }
        System.out.println("Complete construction of " + count + " factories");

        Collections.sort(factory_list);
        Collections.reverse(factory_list);

        ArrayList<FixedTimeBrightBodyFactory> size_filtered_factory_list = new ArrayList<>();
        HashMap<Integer, Integer> times_to_count = new HashMap<>();

        for(FixedTimeBrightBodyFactory factory : factory_list) {
            if(factory.size() >= size_cutoff) {
                size_filtered_factory_list.add(factory);
                if(times_to_count.containsKey(factory.getTimestamp())) {
                    times_to_count.put(factory.getTimestamp(), (int) times_to_count.get(factory.getTimestamp()) + 1);
                }
                else {
                    times_to_count.put(factory.getTimestamp(), 1);
                }
            }
        }

        for(Map.Entry<Integer, Integer> entry : times_to_count.entrySet()) {
            if(entry.getValue() > time_candidate_limit) {
                System.out.println("Throwing out " + entry.getKey() + " with " + entry.getValue() + " candidates");
            }
        }

        ArrayList<FixedTimeBrightBodyFactory> filtered_factory_list = new ArrayList<>();

        for(FixedTimeBrightBodyFactory factory : size_filtered_factory_list) {
            if(times_to_count.get(factory.getTimestamp()) <= time_candidate_limit) {
                filtered_factory_list.add(factory);
            }
        }

        for(FixedTimeBrightBodyFactory factory : filtered_factory_list) {
            System.out.println("Time: " + factory.getTimestamp() + " | Size: " + factory.size());
        }

        return filtered_factory_list;
        // End output is filtered_factory_list

        // ********************
        // END PROCESSING STAGE
        // ********************
    }

    /**
     * Recursively adds adjacent pixels with matching timestamps to the
     * @param factory FTBBF to add a found pixel
     * @param STCs Dirty set of SpaceTimeCoordinate values
     * @param source_STC SpaceTimeCoordinate to adjacency of
     * TODO[SPEED]: Very inefficient. Performs 9 checks that cycle through the entire STC list on each call.
     */
    private static void addAdjacentPixelsToFixedTimeFactory(FixedTimeBrightBodyFactory factory, ArrayList<SpaceTimeCoordinate> STCs, SpaceTimeCoordinate source_STC) {
        // Iterate through the coordinates in the square of size 3 around source_STC
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
            return this.x == x && this.y == y && this.t == t;
        }

        /**
         * Compares two STCs by their x, y, and t values
         * @param stc SpaceTimeCoordinate object to compare to
         * @return true if equal, false if unequal
         */
        public boolean equals(SpaceTimeCoordinate stc) {
            return x == stc.x && y == stc.y && t == stc.t;
        }
    }
}