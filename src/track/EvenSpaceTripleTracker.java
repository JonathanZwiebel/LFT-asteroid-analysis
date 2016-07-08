package track;

import brightbodies.BrightBodyEvenSpaceTripleLinkedSet;
import brightbodies.BrightBodyList;
import brightbodies.Coordinate;
import helper.Correlation;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        ArrayList<Float> fitnessValues = new ArrayList();
            for(int firstTimestamp = 0; firstTimestamp <= mobile_bodies_.length - 3; firstTimestamp++) {
                // TODO: Create an iterative empty-checker that does not triple check all timestamps
                if(mobile_bodies_[firstTimestamp].isEmpty() || mobile_bodies_[firstTimestamp + 1].isEmpty() || mobile_bodies_[firstTimestamp + 2].isEmpty()) {
                    continue; // Skips this iteration if there are no BrightBodies
                }
                // TODO: Don't double calculate a lot of these distances when being used a second time
                for(int firstIndex = 0; firstIndex < mobile_bodies_[firstTimestamp].size(); firstIndex++) {
                    for(int secondIndex = 0; secondIndex < mobile_bodies_[firstTimestamp + 1].size(); secondIndex++) {
                        for(int thirdIndex = 0; thirdIndex < mobile_bodies_[firstTimestamp + 2].size(); thirdIndex++) {
                            Coordinate[] coords = {mobile_bodies_[firstTimestamp].get(firstIndex).centroid,mobile_bodies_[firstTimestamp + 1].get(secondIndex).centroid,mobile_bodies_[firstTimestamp + 2].get(thirdIndex).centroid};

                            double ab_x = coords[1].x - coords[0].x;
                            double bc_x = coords[2].x - coords[1].x;
                            double ab_y = coords[1].y - coords[0].y;
                            double bc_y = coords[2].y - coords[1].y;

                            double ac = Math.sqrt(Math.pow(coords[0].x - coords[2].x, 2) + Math.pow(coords[0].y - coords[2].y, 2));
                            double ab = Math.sqrt(ab_x * ab_x + ab_y * ab_y);
                            double bc = Math.sqrt(bc_x * bc_x + bc_y * bc_y);

                            // TODO: Create a ratio check that doesn't use both min and max
                            double x_ratio = Math.min(Math.abs(ab_x), Math.abs(bc_x)) / Math.max(Math.abs(ab_x), Math.abs(bc_x));
                            double y_ratio = Math.min(Math.abs(ab_y), Math.abs(bc_y)) / Math.max(Math.abs(ab_y), Math.abs(bc_y));

                            // TODO: Any sign flipping method that isn't this
                            if(Math.signum(ab_x) != Math.signum(bc_x)) {
                                x_ratio *= -1;
                            }
                            if(Math.signum(ab_y) != Math.signum(bc_y)) {
                                y_ratio *= -1;
                            }

                            float meanSize = (mobile_bodies_[firstTimestamp].get(firstIndex).body.length + mobile_bodies_[firstTimestamp + 1].get(secondIndex).body.length + mobile_bodies_[firstTimestamp + 2].get(thirdIndex).body.length) / 3;
                            float meanTravelDistance = (float) ac / 2;
                            float sizeTravelRatio = Math.min(meanSize, meanTravelDistance) / Math.max(meanSize, meanTravelDistance);

                            float linkFitness =  (float) (Math.pow(sizeTravelRatio, 2) * Math.pow(x_ratio * y_ratio, 4) *  Math.log(ac));

                            if(linkFitness > 1) {
                                System.out.println("Fitness of " + linkFitness + " found at " + firstTimestamp + " with " + coords[0] + " and " + coords[1] + " and " + coords[2]);
                                System.out.println("Above has sizeTravelRatio of " + sizeTravelRatio);
                            }

                            fitnessValues.add(linkFitness);
                            checks++;
                        }
                    }
                }
            }
        System.out.println("Checks: " + checks);


        return null;
    }
}
