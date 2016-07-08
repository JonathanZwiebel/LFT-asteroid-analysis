package core.track;

import brightbodies.BrightBody;
import brightbodies.BrightBodyEvenSpaceTripleLinkedSet;
import brightbodies.BrightBodyList;
import brightbodies.Coordinate;

import java.util.ArrayList;

/**
 * This class extends the general tracker classes. This searches though all combinations of bright bodies in adjacent
 * sets of three timestamps. The sets of three begin on stepped, overlapping values and are spaced by 1. A fitness value
 * is calculated for all combinations of MobileBrightBodies between those three frames and used to determine asteroid
 * likelihood.
 *
 * @author Jonathan Zwiebel
 * @version 8 July 2016
 */
public class EvenSpaceTripleTracker extends Tracker {
    /**
     * Constructs an EvenSpaceTripleTracker given the initial array of BrightBodyLists containing the MobileBrightBodies
     * at each frame.
     *
     * @param mobile_bodies the set of MobileBrightBodies to check
     */
    public EvenSpaceTripleTracker(BrightBodyList[] mobile_bodies) {
        super(mobile_bodies);
    }

    /**
     * Performs the tracking step by iterating through all combinations of MobileBrightBodies in adjacent sets of 3 frames
     * and checking their fitness vlaue.
     *
     * @return an array of all believed asteroid paths
     *
     * TODO: Create an iterative empty-checker that does not triple check all timestamps
     * TODO: Remove double calculation of intermediate values within fitness calculation due to overlapping iteration
     */
    public BrightBodyEvenSpaceTripleLinkedSet[] track() {
        // A list of all likely sets found
        @SuppressWarnings("unchecked") ArrayList<BrightBodyEvenSpaceTripleLinkedSet> linked_sets_list = new ArrayList();

            // Iterates through all timestamps to contain every set of 3 adjacent frames
            for(int first_timestamp = 0; first_timestamp <= mobile_bodies_.length - 3; first_timestamp++) {

                // Ignores a set of three timestamps if one of the three does not contain any MobileBrightBodies
                if(mobile_bodies_[first_timestamp].isEmpty() || mobile_bodies_[first_timestamp + 1].isEmpty() || mobile_bodies_[first_timestamp + 2].isEmpty()) {
                    continue;
                }

                // Iterates through all combinations of MobileBrightBodies for the three timestamps
                for(int first_index = 0; first_index < mobile_bodies_[first_timestamp].size(); first_index++) {
                    for(int second_index = 0; second_index < mobile_bodies_[first_timestamp + 1].size(); second_index++) {
                        for(int third_index = 0; third_index < mobile_bodies_[first_timestamp + 2].size(); third_index++) {
                            float fitness = fitness(mobile_bodies_, first_timestamp, first_index, second_index, third_index);
                            if(fitness >  1) {
                                BrightBody[] bodies = {mobile_bodies_[first_timestamp].get(first_index), mobile_bodies_[first_timestamp + 1].get(second_index), mobile_bodies_[first_timestamp + 2].get(third_index)};
                                int[] timestamps = {first_timestamp, first_timestamp + 1, first_timestamp + 2};
                                linked_sets_list.add(new BrightBodyEvenSpaceTripleLinkedSet(bodies, timestamps));
                            }
                        }
                    }
                }
            }

        // Returns the completed array of likely asteroids
        BrightBodyEvenSpaceTripleLinkedSet[] linked_sets = linked_sets_list.toArray(new BrightBodyEvenSpaceTripleLinkedSet[linked_sets_list.size()]);
        return linked_sets;
    }

    /**
     * Given three MobileBrightBodies, each from a different frame, calculates the likelihood that they are from the same
     * moving celestial object. The three frames are adjacent to each other ranging from first_timestamp to first_timestamp + 2
     * The index of the MobileBrightBody to check in each of the frames are given by the index parameters. A fitness value
     * above 1 for the linkage indicates a likely asteroid while lower values indicate false MobileBrightBodies.
     *
     * The fitness calculation used is as follows:
     *  d = total distance between first and third point
     *
     *  x_travel[] = x distance between first and second point AND y distance between second and third point
     *  y_travel[] = x distance between first and second point AND y distance between second and third point
     *
     *  x_ratio = ratio between smaller x travel and larger x travel
     *  y_ratio = ratio between smaller y travel and larger y travel
     *  space_ratio = x_ratio * y_ratio
     *
     *  u_s = mean size of the MobileBrightBodies being checked for linkage
     *  u_d = d / 2
     *  sd_ratio = ratio between the smaller value of u_s and u_d and the larger value
     *
     *  fitness = sd_ratio^2 * space_ratio^4 * log(d)
     *
     * @param mobile_bodies an array of the set of BrightBodyLists with the MobileBrightBodies from each frame
     * @param first_timestamp the first timestamp of three to get the bodies from
     * @param first_index the index of the BrightBody to check from timestamp first_timestamp
     * @param second_index the index of the BrightBody to check from timestamp first_timestamp + 1
     * @param third_index the index of the BrightBody to check from timestamp first_timestamp + 2
     * @return fitness of the linkage
     *
     * TODO: Allow for short-circuiting of calculation once one value is found to be too low
     */
    private static float fitness(BrightBodyList[] mobile_bodies, int first_timestamp, int first_index, int second_index, int third_index) {
        // Obtains the coordinates of the three MobileBrightBodies to
        Coordinate[] coordinates = {mobile_bodies[first_timestamp].get(first_index).centroid, mobile_bodies[first_timestamp + 1].get(second_index).centroid,mobile_bodies[first_timestamp + 2].get(third_index).centroid};

        // Calculates the x_travel[] and y_travel[] values while maintaining signage
        double ab_x_travel_distance = coordinates[1].x - coordinates[0].x;
        double bc_x_travel_distance = coordinates[2].x - coordinates[1].x;
        double ab_y_travel_distance = coordinates[1].y - coordinates[0].y;
        double bc_y_travel_distance = coordinates[2].y - coordinates[1].y;

        // Calculates the total travel distance using the distance formula
        double total_travel_distance = Math.sqrt(Math.pow(coordinates[0].x - coordinates[2].x, 2) + Math.pow(coordinates[0].y - coordinates[2].y, 2));

        // Calculates the travel distance ratio for both x_travel and y_travel and then their product, the spacing ratio
        double x_distance_ratio = Math.min(Math.abs(ab_x_travel_distance), Math.abs(bc_x_travel_distance)) / Math.max(Math.abs(ab_x_travel_distance), Math.abs(bc_x_travel_distance));
        double y_distance_ratio = Math.min(Math.abs(ab_y_travel_distance), Math.abs(bc_y_travel_distance)) / Math.max(Math.abs(ab_y_travel_distance), Math.abs(bc_y_travel_distance));
        float spacing_ratio = (float) (x_distance_ratio * y_distance_ratio);

        // Calculates the mean object size, mean travel distance over 1 timestamp, and the <1 ratio between them
        float mean_size = (mobile_bodies[first_timestamp].get(first_index).body.length + mobile_bodies[first_timestamp + 1].get(second_index).body.length + mobile_bodies[first_timestamp + 2].get(third_index).body.length) / 3;
        float mean_travel_distance = (float) total_travel_distance / 2;
        float size_travel_distance_ratio = Math.min(mean_size, mean_travel_distance) / Math.max(mean_size, mean_travel_distance);

        // Calculates the computed fitness value
        float fitness =  (float) (Math.pow(size_travel_distance_ratio, 2) * Math.pow(spacing_ratio, 4) *  Math.log(total_travel_distance));

        // Kills fitness if the object showed zig-zag behavior with movements in both the positive and negative x or y directions
        if(ab_x_travel_distance * bc_x_travel_distance < 0 || ab_y_travel_distance * bc_y_travel_distance < 0) {
            fitness = 0;
        }

        // Returns the fitness value
        return fitness;
    }
}
