package analysis;

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
}
