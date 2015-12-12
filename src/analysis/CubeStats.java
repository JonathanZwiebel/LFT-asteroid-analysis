package analysis;

/**
 * @author Jonathan Zwiebel
 * @version December 11th, 2015
 */
public class CubeStats {
    /**
     * The cube that this class performs statistics on
     */
    float[][][] cube;

    public float mean;

    public CubeStats(float[][][] cube) {
        this.cube = cube;
    }

    public static float mean(float[][][] cube) {
        float sum = 0.0f;
        for(float[][] slice : cube) {
            sum += ImageStats.mean(slice);
        }
        return sum / cube.length;
    }
}
