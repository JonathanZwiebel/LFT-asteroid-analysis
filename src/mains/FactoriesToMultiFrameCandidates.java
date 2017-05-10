package mains;

import brightbodies.FixedTimeBrightBodyFactory;

import java.util.ArrayList;

/**
 * This top level runnable take a list of fixed time frame bright body factories from and OutlierCSVToGroups call
 * and converts them into multi-frame candidate paths. The paths are created when single-frame candidates are found
 * in three adjacent time stamps.
 *
 * Argument Order:
 *  0 - "FACTORIES_TO_MULTI_FRAME_CANDIDATES"
 *  1 - Directory containing outlier .csv files for individual pixels
 *  2 - Block name for outlier .csv files
 *  3 - Maximum allowed p-value for individual pixels to be considered outliers
 *  4 - Minimum allowed size for adjacent group of pixels to be considered single-frame candidate
 *
 * TODO: Rename this class or rework the way it interacts with OutlierCSVToGroups
 * TODO: Initial linking
 * TODO: Linking with incomplete adjacency
 * TODO: Linking of paths of length greater than 3
 *
 * @author Jonathan Zwiebel
 * @version 9 May 2017
 */
public class FactoriesToMultiFrameCandidates {
    public static void run(String[] args) throws Exception{
        // *****************
        // BEGIN INPUT STAGE
        // *****************
        assert args[0].equals("FACTORIES_TO_MULTI_FRAME_CANDIDATES");

        String[] outlier_csv_to_groups_args = new String[6];
        outlier_csv_to_groups_args[0] = "OUTLIER_CSV_TO_GROUPS";

        for(int i = 1; i <= 5; i++) {
            outlier_csv_to_groups_args[i] = args[i];
        }

        ArrayList<FixedTimeBrightBodyFactory> factories = OutlierCSVToGroups.run(outlier_csv_to_groups_args);

        HashMap<Integer, ArrayList> factories_by_timestamp = new HashMap();
        
    }
}
