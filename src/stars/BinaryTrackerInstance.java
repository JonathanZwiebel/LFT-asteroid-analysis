package stars;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;

public class BinaryTrackerInstance {
    public int index;
    public float[][] image;
    public int[][] binary_image;
    BrightBodyList bright_bodies;

    public BinaryTrackerInstance(float[][][] original_cube, int[][][] binary_cube, int index) {
        this.index = index;
        image = original_cube[index];
        binary_image = binary_cube[index];
        bright_bodies = BrightBodyLocator.binaryLocate(image, binary_image);
        sortByArea();
    }

    public void print() {
        System.out.println("Index: " + index + " | Bright Bodies: " + bright_bodies.size());
        for(BrightBody b : bright_bodies) {
            System.out.println(b);
        }
    }

    private void sortByArea() {
        Collections.sort(bright_bodies, Collections.reverseOrder());
    }

    public void toTextFile(String filename)throws IOException {
        File f = new File(filename);
        if(!f.exists()) {
            f.createNewFile();
        }
        FileWriter fwriter = new FileWriter(f.getAbsoluteFile());
        BufferedWriter writer = new BufferedWriter(fwriter);
        writer.write("Index: " + index + " | Bright Bodies: " + bright_bodies.size());
        writer.newLine();
        writer.newLine();
        for(BrightBody b : bright_bodies) {
            writer.write(b.toString());
            writer.newLine();
        }
        writer.close();
    }
}
