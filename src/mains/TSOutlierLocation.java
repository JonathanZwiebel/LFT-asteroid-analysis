package mains;

import brightbodies.FixedTimeBrightBodyFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A top level runnable type for locating asteroids given .csv files with time
 * series outliers over individual pixels. The CSV files should be in the same
 * directory and named outliers_x_y.csv. This class is to be used in
 * conjunction with PixelTSOutliers.java
 *
 * @author Jonathan Zwiebel
 * @version 14 September 2016
 */
public class TSOutlierLocation {
    public static void run(String[] args) {
        assert args[0].equals("TS_OUTLIER_LOCATION");

        int current_arg = 1;

        //TODO: More than just squares
        int size = Integer.parseInt(args[current_arg]);
        current_arg++;

        String directory = args[current_arg];
        current_arg++;

        try {
            ArrayList<SpaceTimeCoordinate> hits = new ArrayList<>();

            for(int i = 0; i < size; i++) {
                for(int j = 0; j < size; j++) {
                    String location = directory + "/outliers_" + i + "_" + j + ".csv";
                    File file = new File(location);
                    BufferedReader reader = new BufferedReader(new FileReader(file));

                    String line = null;
                    while((line = reader.readLine()) != null) {
                        int comma_index = line.indexOf(',');
                        int timestamp = Integer.parseInt(line.substring(0, comma_index));
                        hits.add(new SpaceTimeCoordinate(i, j, timestamp));
                    }
                }
            }
            ArrayList<FixedTimeBrightBodyFactory> factory_list = new ArrayList<>();

            // On termination of this loop body_list contains a list of FTBBFs that contain all pixels and group
            // adjacent ones with matching times
            for(int stc_index = 0; stc_index < hits.size(); stc_index++) {
                SpaceTimeCoordinate stc = hits.get(stc_index);
                FixedTimeBrightBodyFactory factory = new FixedTimeBrightBodyFactory(stc.t);
                factory.addPixel(stc.x, stc.y);
                hits.remove(stc);
                stc_index--;
                addAdjacentPixelsToFixedTimeFactory(factory, hits, stc);
                factory_list.add(factory);
            }

            Collections.sort(factory_list);

            File out = new File("data\\FTBBFs.txt");
            FileWriter file_writer = new FileWriter(out);
            BufferedWriter buffered_writer = new BufferedWriter(file_writer);
            for(FixedTimeBrightBodyFactory factory : factory_list) {
                buffered_writer.write(factory.toString());
            }
            buffered_writer.close();
            file_writer.close();

        }
        catch(Exception e) {
            e.printStackTrace();
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
        for(int i = source_STC.x - 1; i <= source_STC.x + 1; i++) {
            for(int j = source_STC.y - 1; j <= source_STC.y + 1; j++) {
                for(int stc_index = 0; stc_index < STCs.size(); stc_index++) {
                    SpaceTimeCoordinate stc = STCs.get(stc_index);
                    if(stc.equals(i, j, source_STC.t)) {
                        factory.addPixel(i, j);
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
