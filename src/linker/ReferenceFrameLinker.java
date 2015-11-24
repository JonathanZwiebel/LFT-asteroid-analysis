package linker;

import filter.BinaryFilter;
import helper.SubtractiveHelper;
import stars.BinaryTracker;
import stars.BinaryTrackerInstance;

/**
 * @author Jonathan Zwiebel
 * @version November 20th, 2015
 *
 * This class takes a BinaryTracker object and will link the stars between them creating LinkedStarsMap by taking the
 * mean of the images to generate a reference frame which will be tracked with a binary track. Each individual frame
 * will then be compared individually and bright bodies with similar brightnesses and locations to match bright bodies
 * between the original data and the reference frame. Unlinked objects will then be sent to a free body tracker.
 */
public class ReferenceFrameLinker {
    private BinaryTracker tracker_;
    private float[][][] cleaned_data_;
    private BinaryTrackerInstance ref_frame_tracker_instance_;

    /**
     * Constructs a reference frame linker object that
     * @param tracker
     * @param cleaned_data
     */
    public ReferenceFrameLinker(BinaryTracker tracker, float[][][] cleaned_data) {
        tracker_ = tracker;
        cleaned_data_ = cleaned_data;
        //TODO[MAJOR]: Not necessarily a mean image filter
        float[][] ref_frame = SubtractiveHelper.meanImage(cleaned_data_);
        ref_frame_tracker_instance_ = new BinaryTrackerInstance(ref_frame, BinaryFilter.meanFilter(ref_frame));

    }

    /**
     * Links the objects within the
     */
    public void link() {

    }
}
