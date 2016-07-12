package stats;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class contains a method to find the median value of a floating point two-dimensional array.
 *
 * @author Jonathan Zwiebel
 * @version 12 July 2016
 *
 * TODO: Don't sort the array!
 */
public class MedianValue {
    public static float medianValue(float[][] data) {
        ArrayList<Float> single_array_data = new ArrayList<>();

        int index = 0;
        for(float[] array : data) {
            for(float point : array) {
                single_array_data.add(point);
                index++;
            }
        }

        Collections.sort(single_array_data);
        if(single_array_data.size() % 1 == 1) {
            return single_array_data.get(single_array_data.size() / 2);
        }
        else {
            return (single_array_data.get(single_array_data.size() / 2) + single_array_data.get(single_array_data.size() / 2 - 1)) / 2;
        }
    }
}
