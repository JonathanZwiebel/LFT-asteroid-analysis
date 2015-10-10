package stars;

import java.util.ArrayList;

public class BinaryTrackerInstance {
    public int index;
    public float[][] image;
    public int[][] binary_image;
    ArrayList<BrightBody> bright_bodies;

    public BinaryTrackerInstance(float[][][] original_cube, int[][][] binary_cube, int index) {
        this.index = index;
        image = original_cube[index];
        binary_image = binary_cube[index];
        bright_bodies = BrightBodyLocator.binaryLocate(image, binary_image);
    }

    public void print() {
        System.out.println("Index: " + index + " | Bright Bodies: " + bright_bodies.size());
        for(BrightBody b : bright_bodies) {
            System.out.println(b);
        }
    }
}
