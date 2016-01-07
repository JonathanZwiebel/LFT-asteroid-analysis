package filtering;

import analysis.ImageStats;
import brightbodies.BrightBodyList;
import helper.SubtractiveHelper;

/**
 * @author Jonathan Zwiebel
 * @version January 6th, 2016
 */
public class ReferenceMobilityFilter extends MobilityFilter {
    float[][] reference_frame;
    float[][][] processed_data_;

    public ReferenceMobilityFilter(BrightBodyList[] bright_body_list, float[][][] processed_data) {
        super(bright_body_list);
        processed_data_ = processed_data;
    }

    // TODO: Implement
    public BrightBodyList[][] filter() {
        reference_frame = generateReferenceFrame();
        return new BrightBodyList[0][0];
    }

    /**
     * Generates the reference frame that will be used as a comparison to other frames
     * @return reference frame
     * TODO: Allow for more types of reference frames than just mean
     * TODO: Allow for shifts to the reference frame
     */
    private float[][] generateReferenceFrame() {
        return SubtractiveHelper.meanImage(processed_data_);
    }
}
