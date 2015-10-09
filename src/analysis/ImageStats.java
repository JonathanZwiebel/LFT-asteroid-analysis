package analysis;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class ImageStats {
    float[][] image;
    ArrayList<Float> data_list;
    public float mean, std_dev, min, q1, med, q3, max;

    public ImageStats(float[][] image) {
        this.image = image;
        ArrayList<Float> data_list = new ArrayList<Float>();
        for(float[] arrayf : image) {
            for(float f : arrayf) {
                data_list.add(f);
                Collections.sort(data_list);
            }
        }
        analyze();
    }

    private void analyze() {
        findMean();
        findStdDev();
        findQuartiles();
    }

    private void findMean() {
        float sum = 0;
        for(float f : data_list) {
            sum += f;
        }
        mean =  sum / data_list.size();
    }

    private void findQuartiles() {
        int size = data_list.size();
        min = data_list.get(0);
        q1 = data_list.get(size / 4);
        med = data_list.get(size / 2);
        q3 = data_list.get(size * 3 / 4);
        max = data_list.get(size - 1);
    }

    private void findStdDev() {
        float std_dev_squared = 0;
        for(float f : data_list) {
            std_dev_squared += Math.pow(f - mean, 2);
        }
        std_dev_squared /= data_list.size();
        std_dev = (float) Math.sqrt(std_dev_squared);
    }

    public static float mean(float[][] image) {
        ArrayList<Float> data_list = new ArrayList();
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
