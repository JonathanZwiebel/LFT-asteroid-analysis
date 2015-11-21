package mains;

import linker.ReferenceFrameLinker;
import stars.BinaryTracker;

/**
 * @author Joanthan Zwiebel
 * @version November 20th, 2015
 *
 * This class will track a cube of bright bodies in standard FITS format and the link them between individual frames
 * to get references to individual bright bodies over time.
 */
public class TrackAndLink {
    public static final String DATA_FILENAME =          "C:\\Users\\admin\\Desktop\\K2\\raw\\ktwo200000905-c00_lpd-targ.fits";
    public static final String COLUMN =                 "FLUX";

    public static void main(String[] args) {
        try {
            BinaryTracker tracker = new BinaryTracker(DATA_FILENAME, COLUMN);
            float[][][] cleaned_data = tracker.track();
            ReferenceFrameLinker linker = new ReferenceFrameLinker(tracker, cleaned_data);
        }
        catch(Exception e ) {
            e.printStackTrace();
        }
    }
}
