package mains;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A top level runnable type that uses PixelTSOutliers to generate .csvs with time
 * series outliers and then attempts to sort between asteroids, noise, and 
 * fluctuations of the entire dataset
 *
 * @author Jonathan Zwiebel
 * @version 9 August 2016
 * @deprecated
 */
public class TSOutlierTest {
    public static void run(String[] args) {
        assert args[0].equals("TS_OUTLIER_TEST");
        
        // Calls search with the following arguments:
        //  - 0 Fixed Runnable ID
        //  - 1 Location of raw .fits
        //  - 2 First index to search over
        //  - 3 Last index to search over
        //  - 4 Location of .r script
        //  - 5 Directory to output .csv files
        //  - 6 Size of square to search for outliers
        //  - 7 Time allocated for each call to the rscript
        String[] search_args = {"TS_OUTLIER_SEARCH", args[1], args[2], args[3], args[4], args[5], args[6], args[7]};

        //TODO: Modify to work with new outlier methods
        //PixelTSOutliers.run(search_args);
        
        try {

        // Test 1: Adjacency of outliers within the same timestamp
        int size = Integer.parseInt(args[6]);
        HashMap[][] outliers = new HashMap[size][size];
        
        // Load a matrix of HashMaps with index, bonf.p values 
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                outliers[i][j] = new HashMap();
                String location = args[5] + "/outliers_" + i + "_" + j + ".csv";
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
