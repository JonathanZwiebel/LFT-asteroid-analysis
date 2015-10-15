package analysis;

import stars.PixelPoint;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Sorts through an image and returns a PixelPoint array of the coordinates brightness
 * TODO: SERIALIZE AND OPTIMIZE SORING
 */
public class IntraimageSort {
    public PixelPoint[] sortedArray(float[][] image) {
        // mess with load factor
        HashMap<PixelPoint, Float> hashmap = new HashMap<>(2500, 0.75f);
        for(int i = 0; i < image.length; i++) {
            for(int j = 0; j < image[0].length; j++) {
                hashmap.put(new PixelPoint(i, j), image[i][j]);
            }
        }
        sortByValue(hashmap);
        return (PixelPoint[]) hashmap.values().toArray();
    }

     <K, V extends Comparable<? super V>> List<Entry<K, V>> sortByValue(Map<K, V> map) {
        return map.entrySet().stream().sorted(Entry.comparingByValue()).collect(Collectors.toList());
    }
}
