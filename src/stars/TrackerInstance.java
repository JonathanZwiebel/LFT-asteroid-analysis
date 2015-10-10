package stars;

import java.util.ArrayList;

public class TrackerInstance {
    public int index;
    public float[][] image;
    ArrayList<BrightBody> bright_bodies;

    public TrackerInstance(float[][][] cube, int index) {
        this.index = index;
        image = cube[index];
        bright_bodies = BrightBodyLocator.testLocate(image);
    }

    public void print() {
        System.out.println("Index: " + index + " | Bright Bodies: " + bright_bodies.size());
        for(BrightBody b : bright_bodies) {
            System.out.println(b);
        }
    }
}
