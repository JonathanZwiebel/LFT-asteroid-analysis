package track;

import brightbodies.BrightBodyEvenSpaceTripleLinkedSet;
import brightbodies.BrightBodyList;
import brightbodies.Coordinate;
import helper.Correlation;

import java.util.ArrayList;

/**
 * This class extends the general tracker classes. This searches though all combinations of bright bodies in evenly
 * spaced sets of three timestamps. The sets of three begin on stepped, overlapping values and are spaced by values
 * that are a power of 3. The coefficient of determination is calculated and must exceed a given threshold value.
 *
 * @author Jonathan Zwiebel
 * @version 16 June 2016
 */
public class EvenSpaceTripleTracker extends Tracker {
    /**
     * Constructs an EvenSpaceTripleTracker given the initial array of BrightBodyLists containing the MobileBrightBodies
     * at each frame
     *
     * @param mobile_bodies the set of MobileBrightBodies to check
     */
    public EvenSpaceTripleTracker(BrightBodyList[] mobile_bodies) {
        super(mobile_bodies);
    }

    /**
     * Performs the tracking step using the algorithm described in the class javadocs use comment
     * @return an array of all believed asteroid paths
     */
    public BrightBodyEvenSpaceTripleLinkedSet[] track() {
        int checks = 0;
        @SuppressWarnings("unchecked") ArrayList<BrightBodyEvenSpaceTripleLinkedSet> linked_sets = new ArrayList();
            for(int firstTimestamp = 0; firstTimestamp <= mobile_bodies_.length - 3; firstTimestamp++) {
                // TODO: Create an iterative empty-checker that does not triple check all timestamps
                if(mobile_bodies_[firstTimestamp].isEmpty() || mobile_bodies_[firstTimestamp + 1].isEmpty() || mobile_bodies_[firstTimestamp + 2].isEmpty()) {
                    //System.out.println("Skipping " + firstTimestamp + " AND " + (firstTimestamp + 1) + " AND " + (firstTimestamp + 2) + " DUE TO LACK OF STAR");
                    continue; // Skips this iteration if there are no BrightBodies
                }
                for(int firstIndex = 0; firstIndex < mobile_bodies_[firstTimestamp].size(); firstIndex++) {
                    for(int secondIndex = 0; secondIndex < mobile_bodies_[firstTimestamp + 1].size(); secondIndex++) {
                        for(int thirdIndex = 0; thirdIndex < mobile_bodies_[firstTimestamp + 2].size(); thirdIndex++) {
                            Coordinate[] coords = {mobile_bodies_[firstTimestamp].get(firstIndex).centroid,mobile_bodies_[firstTimestamp + 1].get(secondIndex).centroid,mobile_bodies_[firstTimestamp + 2].get(thirdIndex).centroid};
                            double ac = Math.sqrt(Math.pow(coords[0].x - coords[2].x, 2) + Math.pow(coords[0].y - coords[2].y, 2));
                            double ab = Math.sqrt(Math.pow(coords[0].x - coords[1].x, 2) + Math.pow(coords[0].y - coords[1].y, 2));
                            double bc = Math.sqrt(Math.pow(coords[1].x - coords[2].x, 2) + Math.pow(coords[1].y - coords[2].y, 2));

                            checks++;
                            //System.out.println(coords[0] + " AND " + coords[1] + " AND " + coords[2] + " GIVES " + ac * Math.log(ac) / ((ab + bc) * (ab - bc) * (ab - bc)));
                        }
                    }
                }
            }
        System.out.println("Checks: " + checks);
        return null;
    }
}
