package stars;

import java.util.ArrayList;

public class BrightBodyLocator {
    public static ArrayList<BrightBody> binaryLocate(float[][] original_image, int[][] binary_image) {
        ArrayList<BrightBody> true_bright_bodies = new ArrayList();
        int[][] blob_labels = extractBlobLabels(binary_image);
        int blob_count = getMaxValue(blob_labels);
        ArrayList<PixelPoint>[] bright_bodies = new ArrayList[blob_count];
        for(int i = 0; i < original_image.length; i++) {
            for(int j = 0; i < original_image[0].length; i++) {
                if(blob_labels[i][j] != -1) {
                    bright_bodies[blob_labels[i][j]].add(new PixelPoint(i, j));
                }
            }
        }
        for(ArrayList bright_body : bright_bodies) {
            true_bright_bodies.add(new BrightBody(original_image, (PixelPoint[]) bright_body.toArray(new PixelPoint[bright_body.size()])));
        }
        return true_bright_bodies;
    }

    public static int[][] extractBlobLabels(int[][] binary_image) {
        int[][] label_array = new int[binary_image.length][binary_image[0].length];
        for(int i = 0; i < label_array.length; i++) {
            for(int j = 0; j < label_array[0].length; j++) {
                label_array[i][j] = 0;
            }
        }
        // label_array definitions:
        // -1: Dark
        //  0: Not searched
        // +n: Label n
        int blobs = 0;
        for(int i = 0; i < binary_image.length; i++) {
            for(int j = 0; j < binary_image[0].length; j++) {
                if(addToLabelSet(-1, i, j, label_array, binary_image, blobs)) blobs++;
            }
        }
        return label_array;
    }

    private static boolean addToLabelSet(int attached_label, int i, int j, int[][] label_matrix, int[][] binary_image, int blob_count) {
        if(label_matrix[i][j] != 0) {    // already searched
            return false;
        }
        if(binary_image[i][j] == 0) {   // dark
            label_matrix[i][j] = -1;
            return false;
        }
        assert binary_image[i][j] == 1 && label_matrix[i][j] == 0; // light and not searched

        int new_label;
        if(attached_label == -1) {
            new_label = blob_count;
        }
        else {
            new_label = attached_label;
        }
        label_matrix[i][j] = new_label;
        addNeighbors(new_label, i, j, label_matrix, binary_image, blob_count);
        return true;
    }

    private static void addNeighbors(int parent_label, int i, int j, int[][] label_matrix, int[][] binary_image, int blob_count) {
        if(j != label_matrix[0].length - 1) {
            addToLabelSet(parent_label, i, j + 1, label_matrix, binary_image, blob_count);
        }
        if(i != label_matrix.length - 1) {
            addToLabelSet(parent_label, i + 1, j, label_matrix, binary_image, blob_count);
        }
    }

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
