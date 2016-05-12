package locate;

import analysis.ImageStats;
import brightbodies.BrightBody;
import brightbodies.BrightBodyList;
import brightbodies.CartesianPoint;
import mask.BinaryImageMask;
import mask.ImageMask;

import java.util.ArrayList;

/**
 * @author Jonathan Zwiebel
 * @version December 3rd, 2015
 */
public class BinaryLocatorInstance extends LocatorInstance {

    /**
     * Constructs a BinaryLocatorInstance that belongs to a BinaryLocator object
     * @param data one slice of the data in the BinaryLocatorInstance
     */
    public BinaryLocatorInstance(float[][] data) {
        super(data);
    }

    /**
     * Finds all of the pixels above the threshold value
     * @return List of bright bodies, joined pixels above threshold value
     * TODO[Fix]: Throws a bug when threshold is too low
     * TODO: Mask is thrown away but may want to be accessed
     */
    public BrightBodyList locate(Locator parent) {
        ImageMask mask = new BinaryImageMask(data_);

        float target_threshold_ = 0.0f;
        boolean[][] masked;
        switch(((BinaryLocator) parent).threshold_type_) {
            case ABSOLUTE:
                target_threshold_ = ((BinaryLocator) parent).threshold_arg_;
                break;
            case MEAN:
                target_threshold_ = ImageStats.mean(data_);
                break;
            case MEAN_SHIFTED:
                target_threshold_ = ImageStats.mean(data_) + ((BinaryLocator) parent).threshold_arg_;
                break;
            case MEAN_SCALED:
                target_threshold_ = ImageStats.mean(data_ ) * ((BinaryLocator) parent).threshold_arg_;
                break;
            default:
                System.out.println("Illegal target threshold method");
                System.exit(1);
        }
        masked = mask.mask(target_threshold_);

        int[][] blob_labels = extractBlobLabels(masked);
        int blob_count = getMaxValue(blob_labels);

        ArrayList[] bright_bodies = new ArrayList[blob_count];
        for(int i = 0; i < bright_bodies.length; i++) {
            bright_bodies[i] = new ArrayList<CartesianPoint>();
        }
        for(int i = 0; i < data_.length; i++) {
            for(int j = 0; j < data_[0].length; j++) {
                if(blob_labels[i][j] != -1) {
                    // TODO: Fix bug where the position is being echoed
                    //noinspection unchecked
                    bright_bodies[blob_labels[i][j] - 1].add(new CartesianPoint(j, blob_labels.length - 1 - i));
                }
            }
        }

        BrightBodyList bodies = new BrightBodyList();
        for(ArrayList bright_body : bright_bodies) {
            bodies.add(new BrightBody(data_, (CartesianPoint[]) bright_body.toArray(new CartesianPoint[bright_body.size()])));
        }

        bodies.sortByArea();
        return bodies;
    }

    /**
     * Extracts a matrix of numerical labels for each coordinate that identifies which bright body it belongs to
     * @param binary_image original binary image
     * @return integer array of labels
     */
    private static int[][] extractBlobLabels(boolean[][] binary_image) {
        // Creates a label matrix and initializes all values to 0
        // Within the label matrix: -1 is dark, 0 is not searched, +n is unique label n
        int[][] label_matrix = new int[binary_image.length][binary_image[0].length];
        for(int i = 0; i < label_matrix.length; i++) {
            for(int j = 0; j < label_matrix[0].length; j++) {
                label_matrix[i][j] = 0;
            }
        }

        int blobs = 1;
        for(int i = 0; i < binary_image.length; i++) {
            for(int j = 0; j < binary_image[0].length; j++) {
                if(addToLabelSet(-1, i, j, label_matrix, binary_image, blobs)){
                    blobs++;
                }
            }
        }
        return label_matrix;
    }

    /**
     * Adds a particular coordinate location to the label set, attaching it to an existing label if possible
     * @param attached_label label that parent coordinate location that called this method had, will be -1 if from extract
     * @param i the i value of the point to add
     * @param j the j value of the point to add
     * @param label_matrix the matrix of current labels that is being filled with this call
     * @param binary_image the nonmutable binary mask
     * @param blob_count the current number of blobs in the set
     * @return if there was a new blob by this call
     */
    private static boolean addToLabelSet(int attached_label, int i, int j, int[][] label_matrix, boolean[][] binary_image, int blob_count) {
        // Already been searched
        if(label_matrix[i][j] != 0) {
            return false;
        }

        // Has not been searched but has negative on the mask
        if(!binary_image[i][j]) {
            label_matrix[i][j] = -1;
            return false;
        }

        assert binary_image[i][j] && label_matrix[i][j] == 0; // light and not searched

        int new_label;
        // This is a new label set, begin new labelling system
        if(attached_label == -1) {
            new_label = blob_count;
        }
        // This is not a new label set, give this the same label as the coordiante that called this
        else {
            new_label = attached_label;
        }
        assert new_label != -1;
        label_matrix[i][j] = new_label;
        addNeighbors(new_label, i, j, label_matrix, binary_image, blob_count);
        return true;
    }

    /**
     * Adds the 4 neighbors of a particular address to the data set with the working label of the parent location
     * @param parent_label the label of the location that called this method
     * @param i the i value of the calling location
     * @param j the j value of the calling location
     * @param label_matrix the matrix of current labels that is being filled with this call
     * @param binary_image the nonmutable binary mastk
     * @param blob_count the current number of blods in the set
     * TODO: Don't call addToLabelSet if already searched
     */
    private static void addNeighbors(int parent_label, int i, int j, int[][] label_matrix, boolean[][] binary_image, int blob_count) {
        if(j != label_matrix[0].length - 1) {
            addToLabelSet(parent_label, i, j + 1, label_matrix, binary_image, blob_count);
        }
        if(i != label_matrix.length - 1) {
            addToLabelSet(parent_label, i + 1, j, label_matrix, binary_image, blob_count);
        }

        if(j != 0) {
            addToLabelSet(parent_label, i, j - 1, label_matrix, binary_image, blob_count);
        }
        if(i != 0) {
            addToLabelSet(parent_label, i - 1, j, label_matrix, binary_image, blob_count);
        }
    }

    /**
     * Returns the maximum value in an integer matrix
     * @param matrix matrix to be searched
     * @return maximum value
     */
    private static int getMaxValue(int[][] matrix) {
        int max = Integer.MIN_VALUE;
        for(int[] array : matrix) {
            for(int i : array) {
                if(i > max) {
                    max = i;
                }
            }
        }
        return max;
    }
}
