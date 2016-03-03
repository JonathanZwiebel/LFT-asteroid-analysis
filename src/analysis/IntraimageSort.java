package analysis;

import brightbodies.CartesianPoint;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * Has a static method to through an image and return a CartesianPoint array of the coordinates brightness
 * TODO: Safely remove this
 */
public final class IntraimageSort {
    private static final float SHIFT_VALUE  = 0.0001f;

    /**
     * Returns the sorted array of PixelPoints by their value in the image
     * @param image the input image
     * @return the sorted array of PixelPoints
     */
    public static CartesianPoint[] sortedArray(float[][] image) {
        HashMap<Float, CartesianPoint> hashmap = new HashMap<>(2500, 0.75f);
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                addToHashMap(hashmap, image[i][j], new CartesianPoint(i, j));
            }
        }
        TreeMap treemap = new TreeMap<>(hashmap);
        CartesianPoint[] sortedPixelPoints = new CartesianPoint[image.length * image[0].length];
        for(int i = 0; i < sortedPixelPoints.length; i++) {
            sortedPixelPoints[i] = (CartesianPoint) treemap.pollLastEntry().getValue();
        }
        return sortedPixelPoints;
    }

    /**
     * Adds a specific value to a hashmap, modifying them slightly if needed
     * @param hashmap the hashmap to add to
     * @param value the value to add
     * @param point the corresponding PixelPoiint
     */
    private static void addToHashMap(HashMap hashmap, float value, CartesianPoint point) {
        while(hashmap.containsKey(value)){
            value += SHIFT_VALUE;
        }
        //noinspection unchecked
        hashmap.put(value, point);
    }
}
