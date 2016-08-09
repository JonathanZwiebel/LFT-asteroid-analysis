package mains;

/**
 * A top level runnable type that uses TSOutlierSearch to generate .csvs with time
 * series outliers and then attempts to sort between asteroids, noise, and 
 * fluctuations of the entire dataset
 *
 * @author Jonathan Zwiebel
 * @version 9 August 2016
 */
public class TSOutlierTest {
    public static void run(String[] args) {
        assert args[0].equals("TS_OUTLIER_TEST");
        
        // Calls search with the following arguments:
        //  - Fixed Runnable ID
        //  - Location of raw .fits
        //  - First index to search over
        //  - Last index to search over
        //  - Location of .r script
        //  - Directory to output .csv files
        //  - Size of square to search for outliers
        //  - Time allocated for each call to the rscript
        String[] search_args = {"TS_OUTLIER_SEARCH", args[1], args[2], args[3], args[4], args[5], args[6], args[7]};

        TSOutlierSearch.run(search_args);
        }
}
