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
    public ReferenceFrameLinkerInstance[] instances;

    /**
     * Constructs a reference frame linker object that
     * @param tracker binary tracker of all of the data
     * @param cleaned_data data cube of cleaned raw data
     */
    public ReferenceFrameLinker(BinaryTracker tracker, float[][][] cleaned_data) {
        assert tracker.instances.length == cleaned_data.length : "Cleaned data length does not match number of instances";
        tracker_ = tracker;
        cleaned_data_ = cleaned_data;
        //TODO[Major]: Not necessarily a mean composite image
        float[][] ref_frame = SubtractiveHelper.meanImage(cleaned_data_);
        //TODO[MAJOR]: Not necessarily a mean image filter
        ref_frame_tracker_instance_ = new BinaryTrackerInstance(ref_frame, BinaryFilter.meanFilter(ref_frame));
    }

    /**
     * Links the bright bodies within each frame to the bright bodies in the reference frame
     */
    public void link() {
        instances = new ReferenceFrameLinkerInstance[cleaned_data_.length];
        for(int i = 0; i < tracker_.instances.length; i++) {
            instances[i] = new ReferenceFrameLinkerInstance(ref_frame_tracker_instance_, tracker_.instances[i]);
        }
    }
}
