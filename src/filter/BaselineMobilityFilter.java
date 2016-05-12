package filter;

import brightbodies.BrightBody;
import brightbodies.BrightBodyList;
import brightbodies.CartesianPoint;
import helper.DataMathematicsHelper;
import locate.BinaryLocator;
import locate.BinaryLocatorThresholdType;

/**
 * @author Jonathan Zwiebel
 * @version January 12th, 2016
 *
 * Filters the bright bodies by generating a baseline frame that is indicative of the cube as a whole. Locates the
 * bright bodies within that cube and then compares the bright bodies in each frame to that of the baseline cube
 * through area overlap
 */
public class BaselineMobilityFilter extends MobilityFilter {
    private final float[][][] processed_data_;
    private final float similarity_threshold_; // the percent similarity that two bright bodies must share to be considered the same
    private final BaselineFrameGenerationMethod base_gen_method_;
    private final float[] args_;

    /**
     * Constructs a BaselineMobilityFilter object with the bright bodies from a locator and the data from a processor
     * @param bright_body_lists the set of bright bodies in each frame
     * @param processed_data the floating point data of brightness in each frame
     * @param similarity_threshold percent similarity that searched body must share with existing body to be immobile
     * @param base_gen_method how the baseline frame will be generated
     * @param args varargs for baseline generation method arguments
     */
    public BaselineMobilityFilter(BrightBodyList[] bright_body_lists, float[][][] processed_data, float similarity_threshold, BaselineFrameGenerationMethod base_gen_method, float... args) {
        super(bright_body_lists);
        processed_data_ = processed_data;
        similarity_threshold_ = similarity_threshold;
        base_gen_method_ = base_gen_method;
        args_ = args;
    }



    /**
     * Filters the bright bodies using baseline frame filtration into mobile and immobile bright bodies
     * @return data cube with first slice as immobile bodies and second slice as mobile bodies
     */
    public BrightBodyList[][] filter() {
        float[][] baseline_frame = generateBaselineFrame();
        BrightBodyList baseline_bodies = generateBaselineFrameBodies(baseline_frame);

        BrightBodyList[][] filtered_bodies = new BrightBodyList[3][bright_body_lists_.length];
        // TODO: Consider if this for loop is better replaced by a single method
        for(int index = 0; index < bright_body_lists_.length; index++) {
            BrightBodyList[] filtered_bodies_instance = mobilitySeparation(bright_body_lists_[index], baseline_bodies, similarity_threshold_);
            assert filtered_bodies_instance.length == 3;
            filtered_bodies[IBB_INDEX][index] = filtered_bodies_instance[IBB_INDEX];
            filtered_bodies[MBB_INDEX][index] = filtered_bodies_instance[MBB_INDEX];
            filtered_bodies[NOISE_INDEX][index] = filtered_bodies_instance[NOISE_INDEX];
        }

        return filtered_bodies;
    }

    /**
     * Generates the baseline frame that will be used as a comparison to other frames
     * Currently only uses the mean image
     *
     * @return baseline frame
     * TODO[Major]: Allow for more types of baseline frames than just mean
     */
    private float[][] generateBaselineFrame() {
        return DataMathematicsHelper.meanImage(processed_data_);
    }

    /**
     * Locates the bright bodies in the baseline frame
     * Currently only does this through a simple mean binary locator
     *
     * @param baseline_frame floating point baseline frame
     * @return a list of all of the bright bodies in the baseline frame
     * TODO[Minor]: Allow for parameters when doing BinaryLocating on baseline frame
     * TODO[Major]: Allow for more location methods
     */
    private BrightBodyList generateBaselineFrameBodies(float[][] baseline_frame) {
        float[][][] baseline_frame_cube = {baseline_frame};
        BinaryLocator baseline_frame_locator = null;

        switch(base_gen_method_) {
            case BINARY_LOCATOR_ABSOLUTE:
                baseline_frame_locator = new BinaryLocator(baseline_frame_cube, BinaryLocatorThresholdType.ABSOLUTE, args_[0]);
                break;
            case BINARY_LOCATOR_MEAN:
                baseline_frame_locator = new BinaryLocator(baseline_frame_cube, BinaryLocatorThresholdType.MEAN);
                break;
            case BINARY_LOCATOR_MEAN_SHIFTED:
                baseline_frame_locator = new BinaryLocator(baseline_frame_cube, BinaryLocatorThresholdType.MEAN_SHIFTED, args_[0]);
                break;
            case BINARY_LOCATOR_MEAN_SCALED:
                baseline_frame_locator = new BinaryLocator(baseline_frame_cube, BinaryLocatorThresholdType.MEAN_SCALED, args_[0]);
                break;
            default:
                System.err.println("Illegal baseline frame generation method");
                System.exit(1);
        }

        baseline_frame_locator.initialize();
        return baseline_frame_locator.locate()[0];
    }

    /**
     * Separates the pre-located bright bodies in input_bodies into two BrightBodyLists by comparing similarity to
     * baseline_bodies. If they match by the threshold value
     * TODO: Allow for simple circle checks on centroid and body size as well as full check
     *
     * @param input_bodies bright bodies in the slice being searched
     * @param baseline_bodies bright bodies in the baseline slice
     * @return data cube with first slice as immobile and second slice as mobile bright bodies
     *
     * TODO: Consider if it is better to returns a special data type for sorted BrightBodyList
     * TODO: Consider if a deep copy is better than an indexed boolean array
     * Likely to be very memory intensive
     */
    private BrightBodyList[] mobilitySeparation(BrightBodyList input_bodies, BrightBodyList baseline_bodies, float similarity_threshold_) {
        BrightBodyList[] sorted_bodies = new BrightBodyList[3];
        sorted_bodies[IBB_INDEX] = new BrightBodyList();
        sorted_bodies[MBB_INDEX] = new BrightBodyList();
        sorted_bodies[NOISE_INDEX] = new BrightBodyList();

        // TODO: Check that input_bodies are actually sorted
        baseline_bodies.sortByArea();
        boolean[] baseline_bodies_used = new boolean[baseline_bodies.size()];

        for(BrightBody body : input_bodies) {
            boolean matched = false;
            for(int base_index = 0; base_index < baseline_bodies.size(); base_index++) {
                if(baseline_bodies_used[base_index]) {
                    continue;
                }
                float overlap = overlap(body, baseline_bodies.get(base_index));
                float percent_overlap = overlap / body.area;
                if(percent_overlap > similarity_threshold_) {
                    baseline_bodies_used[base_index] = true;
                    matched = true;
                    break;
                }
            }
            if(matched) {
                sorted_bodies[IBB_INDEX].add(body);
            }
            else {
                sorted_bodies[MBB_INDEX].add(body);
            }
        }
        return sorted_bodies;
    }

    /**
     * Returns the sum of the values on the original bright body coordinates that also exist in the baseline bright
     * body
     * @return overlap value
     *
     * TODO?: Different kinds of overlap
     */
    private float overlap(BrightBody original, BrightBody baseline) {
        float sum = 0;
        for(CartesianPoint c : original.body) {
            if(baseline.contains(c)) {
                // TODO[IMPORTANT]: MAKE SURE THIS IS THE CORRECT COORDINATE POINT
                sum += original.source[original.source.length - c.y - 1][c.x];
            }
        }
        return sum;
    }
}
