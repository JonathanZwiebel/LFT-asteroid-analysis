package mains;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A top level runnable type for locating asteroids given .csv files with time
 * series outliers over individual pixels. The CSV files should be in the same
 * directory and named outliers_x_y.csv. This class is to be used in
 * conjunction with PixelTSOutliers.java
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
        // Test 1: Adjacency of outliers within the same timestamp
        HashMap[][] outliers = new HashMap[size][size];
        
        // Load a matrix of HashMaps with index, bonf.p values 
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                outliers[i][j] = new HashMap();
                String location = directory + "/outliers_" + i + "_" + j + ".csv";
                File file = new File(location);
                BufferedReader reader = new BufferedReader(new FileReader(file));
               
                String line = null;
                while((line = reader.readLine()) != null) {
                    int comma_index = line.indexOf(',');
                    int timestamp = Integer.parseInt(line.substring(0, comma_index));
                    float bonfp = Float.parseFloat(line.substring(comma_index + 1));
                    outliers[i][j].put(timestamp, bonfp);
                }
            }
        }

        // Iterate through the matrix checking 
        // TODO: Don't double check interior edges in the matrix
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if(j + 1 < size) {
                    outlierAdjacency(outliers[i][j], outliers[i][j + 1]);
                }
                if(i + 1 < size) {
                    outlierAdjacency(outliers[i][j], outliers[i + 1][j]);
                }
                if(j + 1 < size && i + 1 < size) {
                    outlierAdjacency(outliers[i][j], outliers[i + 1][j + 1]);
                }      
            }
        
        }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    private static void outlierAdjacency(HashMap first, HashMap second) {
        Iterator iterator = first.entrySet().iterator();
        while(iterator.hasNext()) {
            int key = (int) ((Map.Entry) iterator.next()).getKey();
            if(second.containsKey(key)) {
                System.out.println("key: " + key);
            }
        }
    }
}
