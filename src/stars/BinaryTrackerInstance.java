package stars;

import java.io.*;

/**
 * Stores the bright bodies within a single timestamp of a BinaryTracker based on a passed binary mask. Adjacent
 * positives will be grouped into single bright bodies and extractable as a sorted BrightBodyList.
 * TODO: Consider making this a private class
 * TODO[Major]: Make a tracker instance abstract class
 */
public class BinaryTrackerInstance extends TrackerInstance{
    private int index;
    private BrightBodyList bright_bodies_;

    /**
     * Constructs a binary tracker instance given the original cube, binary mask cube and index cube
     * @param original_image original bright body field
     * @param binary_filter_image masked image containing positives at regions to search
     * @param index index of the cube to check
     * TODO: Move all of these procedures out of the constructor
     */
    public BinaryTrackerInstance(float[][] original_image, int[][] binary_filter_image, int index) {
        this.index = index;
        bright_bodies_ = BrightBodyLocator.binaryLocate(original_image, binary_filter_image);
        bright_bodies_.sortByArea();
    }

    /**
     * Constructs a binary tracker instance given the original slice and the binary filter slice not
     * in the context of a larger cube
     * @param original_image original bright body image
     * @param binary_filter_image masked image containing positives at regions to search
     */
    public BinaryTrackerInstance(float[][] original_image, int[][] binary_filter_image) {
        this(original_image, binary_filter_image, -1);
    }

    /**
     * Generates a serialization of the sorted bright bodies
     * @param filename .ser file location
     * @throws IOException
     */
    public void serialize(String filename) throws IOException {
        FileOutputStream file_out = new FileOutputStream(filename);
        ObjectOutputStream object_out = new ObjectOutputStream(file_out);
        object_out.writeObject(bright_bodies_);
        object_out.close();
        file_out.close();
    }

    /**
     * Generates a text output of the sorted bright bodies
     * @param filename .txt file location
     * @throws IOException
     */
    public void toTextFile(String filename) throws IOException {
        File f = new File(filename);
        if(!f.exists()) {
            f.createNewFile();
        }
        FileWriter file_writer = new FileWriter(f.getAbsoluteFile());
        BufferedWriter writer = new BufferedWriter(file_writer);
        writer.write("Index: " + index + " | Bright Bodies: " + bright_bodies_.size());
        writer.newLine();
        writer.newLine();
        for(BrightBody b : bright_bodies_) {
            writer.write(b.toString());
            writer.newLine();
        }
        writer.close();
        file_writer.close();
    }

    /**
     * Generates a CSV output of the sorted bright bodies with each row corresponding to a BrightBody, the first
     * column being area and the second column being centroid
     * @param filename .csv file location
     * @throws IOException
     */
    public void toCSV(String filename) throws IOException {
        File f = new File(filename);
        if(!f.exists()) {
            f.createNewFile();
        }
        FileWriter file_writer_ = new FileWriter(f.getAbsoluteFile());
        BufferedWriter writer = new BufferedWriter(file_writer_);
        writer.write("\"Area\",\"Centroid X\",\"Centroid Y\"");
        writer.newLine();
        for(BrightBody bright_body : bright_bodies_) {
            writer.write("\"" + bright_body.area + "\",\"" + bright_body.centroid.x + "\",\"" + bright_body.centroid.y + "\"");
            writer.newLine();
        }
        writer.close();
        file_writer_.close();
    }

    /**
     * Adds to a two dimensional matrix of rank ordered bright bodies the brightest bodies of this instance
     * @param rank_ordered_bright_bodies two dimensional matrix of rank ordered bright bodies
     */
    public void addToRankOrderedBrightBodies(float[][] rank_ordered_bright_bodies) {
        for(int i = 0; i < rank_ordered_bright_bodies.length; i++) {
            rank_ordered_bright_bodies[i][index] = bright_bodies_.get(i).area;
        }
    }
 }
