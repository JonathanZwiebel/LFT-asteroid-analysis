package stars;

import java.io.*;

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
        bright_bodies.sortByArea();
    }

    public void print() {
        System.out.println("Index: " + index + " | Bright Bodies: " + bright_bodies.size());
        System.out.print(bright_bodies);
    }

    // TODO: GENERIC
    public void serialize() throws FileNotFoundException, IOException {
        FileOutputStream file_out = new FileOutputStream("C:\\Users\\user\\Desktop\\K2\\serialized\\ktwo200000908-c00.ser");
        ObjectOutputStream object_out = new ObjectOutputStream(file_out);
        object_out.writeObject(bright_bodies);
        object_out.close();
        file_out.close();
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
