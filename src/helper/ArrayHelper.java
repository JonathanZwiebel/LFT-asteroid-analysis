package helper;

public class ArrayHelper {
    public static float[][] intToFloat(int[][] image) {
        float[][] imagef = new float[image.length][image[0].length];
        for(int i = 0; i < image.length; i++) {
            for(int j = 0; j < image[0].length; j++) {
                imagef[i][j] = image[i][j];
            }
        }
        return imagef;
    }

    public static float[][][] intToFloat(int[][][] cube) {
        float[][][] cubef = new float[cube.length][cube[0].length][cube[0][0].length];
        for(int i = 0; i < cube.length; i++) {
            cubef[i] = intToFloat(cube[i]);
        }
        return cubef;
    }
}
