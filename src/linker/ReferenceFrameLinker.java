package linker;

import stars.BinaryTracker;

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
    }

    private float[][] generateReferenceFrame() {
    }


}
