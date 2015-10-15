package analysis;

import stars.PixelPoint;

import java.util.HashMap;
import static java.util.Map.Entry.comparingByValue;

/**
 * Sorts through an image and returns a PixelPoint array of the coordinates brightness
 * TODO: SERIALIZE AND OPTIMIZE SORING
 */
public class IntraimageSort {
    public PixelPoint[] sortedArray(float[][] image) {
        // mess with load factor
        HashMap<Integer, PixelPoint> hashmap = new HashMap(2500, 0.75);
    }
}
