package filtering;

import brightbodies.BrightBody;
import brightbodies.BrightBodyList;
import brightbodies.CartesianPoint;
import helper.DataMathematicsHelper;
import locating.BinaryLocator;

/**
 * @author Jonathan Zwiebel
 * @version January 12th, 2016
 *
 * Filters the bright bodies by generating a reference frame that is indicative of the cube as a whole. Locates the
 * bright bodies within that cube and then compares the bright bodies in each frame to that of the reference cube
 * through area overlap
 */
public class ReferenceMobilityFilter extends MobilityFilter {
    private final float[][][] processed_data_;
    private final float similarity_threshold_; // the percent similarity that two bright bodies must share to be considered the same
    private final ReferenceBodyDetectionMethod ref_gen_method_;
    private final float[] args_;

    // TODO[Major] Make this not a special enum but instead an enum from the standard locator set
    public enum ReferenceBodyDetectionMethod {
        ABSOLUTE,
        MEAN,
        MEAN_SHIFTED,
        MEAN_SCALED
    }


    /**
     * Constructs a ReferenceMobilityFilter object with the bright bodies from a locator and the data from a processor
     * @param bright_body_lists the set of bright bodies in each frame
     * @param processed_data the floating point data of brightness in each frame
     * @param similarity_threshold percent similarity that searched body must share with existing body to be immobile
     * @param ref_gen_method how the reference frame will be generated
     * @param args varargs for reference generation method arguments
     */
    public ReferenceMobilityFilter(BrightBodyList[] bright_body_lists, float[][][] processed_data, float similarity_threshold, ReferenceBodyDetectionMethod ref_gen_method, float... args) {
        super(bright_body_lists);
        processed_data_ = processed_data;
        similarity_threshold_ = similarity_threshold;
        ref_gen_method_ = ref_gen_method;
        args_ = args;
    }



    /**
     * Filters the bright bodies using reference frame filtration into mobile and immobile bright bodies
     * @return data cube with first slice as immobile bodies and second slice as mobile bodies
     */
    public BrightBodyList[][] filter() {
        float[][] reference_frame = generateReferenceFrame();
        BrightBodyList reference_bodies = generateReferenceFrameBodies(reference_frame);

        BrightBodyList[][] filtered_bodies = new BrightBodyList[3][bright_body_lists_.length];
        // TODO: Consider if this for loop is better replaced by a single method
        for(int index = 0; index < bright_body_lists_.length; index++) {
            BrightBodyList[] filtered_bodies_instance = mobilitySeparation(bright_body_lists_[index], reference_bodies, similarity_threshold_);
            assert filtered_bodies_instance.length == 3;
            filtered_bodies[IBB_INDEX][index] = filtered_bodies_instance[IBB_INDEX];
            filtered_bodies[MBB_INDEX][index] = filtered_bodies_instance[MBB_INDEX];
            filtered_bodies[NOISE_INDEX][index] = filtered_bodies_instance[NOISE_INDEX];
        }

        return filtered_bodies;
    }

    /**
     * Generates the reference frame that will be used as a comparison to other frames
     * Currently only uses the mean image
     *
     * @return reference frame
     * TODO[Major]: Allow for more types of reference frames than just mean
     */
    private float[][] generateReferenceFrame() {
        return DataMathematicsHelper.meanImage(processed_data_);
    }

    /**
     * Locates the bright bodies in the reference frame
     * Currently only does this through a simple mean binary locator
     *
     * @param reference_frame floating point reference frame
     * @return a list of all of the bright bodies in the reference frame
     * TODO[Minor]: Allow for parameters when doing BinaryLocating on reference frame
     * TODO[Major]: Allow for more location methods
     */
    private BrightBodyList generateReferenceFrameBodies(float[][] reference_frame) {
        float[][][] reference_frame_cube = {reference_frame};
        BinaryLocator reference_frame_locator = null;

        switch(ref_gen_method_) {
            case ABSOLUTE:
                reference_frame_locator = new BinaryLocator(reference_frame_cube, BinaryLocator.ThresholdType.ABSOLUTE, args_[0]);
                break;
            case MEAN:
                reference_frame_locator = new BinaryLocator(reference_frame_cube, BinaryLocator.ThresholdType.MEAN);
                break;
            case MEAN_SHIFTED:
                reference_frame_locator = new BinaryLocator(reference_frame_cube, BinaryLocator.ThresholdType.MEAN_SHIFTED, args_[0]);
                break;
            case MEAN_SCALED:
                reference_frame_locator = new BinaryLocator(reference_frame_cube, BinaryLocator.ThresholdType.MEAN_SCALED, args_[0]);
                break;
            default:
                System.err.println("Illegal reference frame generation method");
                System.exit(1);
        }

        reference_frame_locator.initialize();
        return reference_frame_locator.locate()[0];
    }

    /**
     * Separates the pre-located bright bodies in input_bodies into two BrightBodyLists by comparing similarity to
     * reference_bodies. If they match by the threshold value
     * TODO: Allow for simple circle checks on centroid and body size as well as full check
     *
     * @param input_bodies bright bodies in the slice being searched
     * @param reference_bodies bright bodies in the reference slice
     * @return data cube with first slice as immobile and second slice as mobile bright bodies
     *
     * TODO: Consider if it is better to returns a special data type for sorted BrightBodyList
     * TODO: Consider if a deep copy is better than an indexed boolean array
     * Likely to be very memory intensive
     */
    private BrightBodyList[] mobilitySeparation(BrightBodyList input_bodies, BrightBodyList reference_bodies, float similarity_threshold_) {
        BrightBodyList[] sorted_bodies = new BrightBodyList[3];
        sorted_bodies[IBB_INDEX] = new BrightBodyList();
        sorted_bodies[MBB_INDEX] = new BrightBodyList();
        sorted_bodies[NOISE_INDEX] = new BrightBodyList();

        // TODO: Check that input_bodies are actually sorted
        reference_bodies.sortByArea();
        boolean[] reference_bodies_used = new boolean[reference_bodies.size()];

        for(BrightBody body : input_bodies) {
            boolean matched = false;
            for(int ref_index = 0; ref_index < reference_bodies.size(); ref_index++) {
                if(reference_bodies_used[ref_index]) {
                    continue;
                }
                float overlap = overlap(body, reference_bodies.get(ref_index));
                float percent_overlap = overlap / body.area;
                if(percent_overlap > similarity_threshold_) {
                    reference_bodies_used[ref_index] = true;
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
     * Returns the sum of the values on the original bright body coordinates that also exist in the reference bright
     * body
     * @return overlap value
     *
     * TODO?: Different kinds of overlap
     */
    private float overlap(BrightBody original, BrightBody reference) {
        float sum = 0;
        for(CartesianPoint c : original.body) {
            if(reference.contains(c)) {
                // TODO[IMPORTANT]: MAKE SURE THIS IS THE CORRECT COORDINATE POINT
                sum += original.source[original.source.length - c.y - 1][c.x];
            }
        }
        return sum;
    }
}
