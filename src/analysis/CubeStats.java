package analysis;

import java.util.Arrays;

/**
 * @author Jonathan Zwiebel
 * @version December 11th, 2015
 */
public class CubeStats {
    public static float mean(float[][][] cube) {
        float sum = 0.0f;
        for(float[][] slice : cube) {
            sum += ImageStats.mean(slice);
        }
        return sum / cube.length;
    }

    public static float percentile(float[][][] cube, float percentile) {
        float[] values = new float[cube.length * cube[0].length * cube[0][0].length];
        int index = 0;
        for(float[][] slice: cube) {
            for(float[] row : slice) {
                for(float value : row) {
                    values[index] = value;
                    index++;
                }
            }
        }
        Arrays.sort(values);
        return values[Math.round(values.length * percentile)];
    }
}
