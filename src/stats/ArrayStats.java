package stats;


import java.util.Arrays;

/**
 * Performs statistics on a given static array and then stores the values
 * TODO: Add more one variable stats
 */
public class ArrayStats {
    public float[] array, sorted_array;
    public int n;
    public float min, q1, med, q3, max, iqr, range;
    public float fd_width;
    public int fd_col_count;

    /**
     * Constructs an ArrayStats object to track an array
     * @param array the array to track
     */
    public ArrayStats(float[] array) {
        this.array = array;
        sorted_array = array.clone();
        Arrays.sort(sorted_array);
        n = array.length;
        computeQuartiles();
        iqr = q3 - q1;
        range = max - min;
        fd_width = 2 * iqr * (float) Math.pow(n, -(float) 1 / 3);
        fd_col_count  = (int) (range / fd_width) + 1;
    }

    /**
     * Computes min, q1, med, q3, and max
     */
    public void computeQuartiles() {
        min = sorted_array[0];
        max = sorted_array[n - 1];

        int p = n % 2;
        if(p == 0) {
            med = (sorted_array[p - 1] + sorted_array[p]) / 2;
        }
        else {
            med = sorted_array[p];
        }

        int q = n % 4;
        if(q == 0 || q == 1) {
            q1 = (sorted_array[q - 1] + sorted_array[q]) / 2;
            q3 = (sorted_array[n - q - 1] + sorted_array[n - q]);
        }
        else {
            q1 = sorted_array[q];
            q3 = sorted_array[n - q - 1];
        }
    }
}
