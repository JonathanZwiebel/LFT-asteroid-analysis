package linker;

import filter.MeanBrightBodyFilter;
import helper.ArrayHelper;
import helper.FitsHelper;
import helper.SubtractiveHelper;
import stars.BinaryTracker;
import stars.BinaryTrackerInstance;

/**
 * @author Jonathan Zwiebel
 * @version November 20th, 2015
 *
 * TODO: Update class description
 * This class takes a BinaryTracker object and will link the stars between them creating LinkedStarsMap
 */
public class ReferenceFrameLinker {
    private BinaryTracker tracker_;
    private float[][][] cleaned_data_;

    public ReferenceFrameLinker(BinaryTracker tracker, float[][][] cleaned_data) {
        tracker_ = tracker;
        cleaned_data_ = cleaned_data;
        float[][] ref_frame = SubtractiveHelper.meanImage(cleaned_data_);
    }

    /**
     * Generates a reference frame by applying an SRS and the averaging on each pixel
     * @return reference frame
     */
    private float[][] generateSampledReferenceFrame() {
        int sample_count = (int) Math.sqrt(cleaned_data_.length);
        int sampled = 0;
        float[][][] samples = new float[sample_count][][];
        while (sampled < sample_count) {
            samples[sampled] = cleaned_data_[(int) (cleaned_data_.length * Math.random())];
            sampled++;
        }

        return SubtractiveHelper.meanImage(samples);
    }


}
