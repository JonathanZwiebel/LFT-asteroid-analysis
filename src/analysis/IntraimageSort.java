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
                hashmap.put(image[i][j], new PixelPoint(i, j));
            }
        }
        TreeMap treemap = new TreeMap(hashmap);
        PixelPoint[] sortedPixelPoints = new PixelPoint[image.length * image[0].length];
        for(int i = 0; i < sortedPixelPoints.length; i++) {
            System.out.println("Filling spot: " + i);
            sortedPixelPoints[i] = (PixelPoint) treemap.pollLastEntry().getValue();
        }
        return sortedPixelPoints;
    }
}
