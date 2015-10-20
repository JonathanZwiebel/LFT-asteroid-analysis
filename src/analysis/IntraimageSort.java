package analysis;

import stars.PixelPoint;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Sorts through an image and returns a PixelPoint array of the coordinates brightness
 * TODO: SERIALIZE AND OPTIMIZE SORING
 */
public class IntraimageSort {
    public static PixelPoint[] sortedArray(float[][] image) {
        // mess with load factor
        HashMap<Float, PixelPoint> hashmap = new HashMap<>(2500, 0.75f);
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                addToHashMap(hashmap, image[i][j], new PixelPoint(i, j));
            }
        }
        TreeMap treemap = new TreeMap(hashmap);
        PixelPoint[] sortedPixelPoints = new PixelPoint[image.length * image[0].length];
        for(int i = 0; i < sortedPixelPoints.length; i++) {
            sortedPixelPoints[i] = (PixelPoint) treemap.pollLastEntry().getValue();
        }
        return sortedPixelPoints;
    }

    // deals with repetitions by slightly modifying values (sketchy)
    public static void addToHashMap(HashMap hashmap, float value, PixelPoint point) {
        while(hashmap.containsKey(value)){
            System.out.println("Duplicate keys of " + value + " at i = " + point.x + " j = " + point.y);
            value += 0.0001f;
        }
        hashmap.put(value, point);
    }
}
