package analysis;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class finds various statistics of an image by treating each pixel value as a homogenous data point
 * TODO: Safely remove this class or make it non-instantiatable
 */
class ImageStats {

    /**
     * An ArrayList of the data in the iamge
     */
    private ArrayList<Float> data_list;

    /**
     * The mean of the data
     */
    private float mean;

    /**
     * Constructs an instance of ImageStats with a given image
     * @param image the input image
     */
    public ImageStats(float[][] image) {
        /*
      The image that this class performs statistics on
     */
        for(float[] arrayf : image) {
            for(float f : arrayf) {
                data_list.add(f);
                Collections.sort(data_list);
            }
        }
        analyze();
    }

    /**
     * Abstraction to calculate the desired values
     */
    private void analyze() {
        findMean();
        findStdDev();
        findQuartiles();
    }

    /**
     * Calculates and sets the mean of the data
     */
    private void findMean() {
        float sum = 0;
        for(float f : data_list) {
            sum += f;
        }
        mean =  sum / data_list.size();
    }

    /**
     * Finds and sets min, q1, med, q3, and max
     */
    private void findQuartiles() {
        int size = data_list.size();
        /*
      The minimum value of the data
     */
        float min = data_list.get(0);
        /*
      The first quartile of the data
     */
        float q1 = data_list.get(size / 4);
        /*
      The median of the data
     */
        float med = data_list.get(size / 2);
        /*
      The third quartile of the data
     */
        float q3 = data_list.get(size * 3 / 4);
        /*
      The maximum value of the data
     */
        float max = data_list.get(size - 1);
    }

    /**
     * Calculates and sets the standard deviation of the data
     * The mean must be calculated before
     */
    private void findStdDev() {
        float std_dev_squared = 0;
        for(float f : data_list) {
            std_dev_squared += Math.pow(f - mean, 2);
        }
        std_dev_squared /= data_list.size();
        /*
      The standard deviation of the data
     */
        float std_dev = (float) Math.sqrt(std_dev_squared);
    }

    /**
     * TODO: Make non-static or move
     * Calculates the mean value of an image
     * @param image the input image
     * @return the mean value
     */
    public static float mean(float[][] image) {
        ArrayList<Float> data_list = new ArrayList<>();
        for(float[] arrayf : image) {
            for(float f : arrayf) {
                data_list.add(f);
            }
        }
        float sum = 0;
        for(float f : data_list) {
            sum += f;
        }
        return sum / data_list.size();
    }
}
