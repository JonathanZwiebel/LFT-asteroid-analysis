package filter;

/**
 * This class filters an image by checking the pixels around it and setting the value to their median
 * TODO: Make non-static
 */
public class BlurFilter {
    public enum Blur_Filter_Type {
        STANDARD,
        POSITIVE,
        NEGATIVE
    }

    public static int[][] filter(int[][] image, int filter_size, Blur_Filter_Type filter_type) {
        int[][] filtered_image = new int[image.length][image[0].length];
        for (int i = 0; i < image[0].length; i++) {
            for (int j = 0; j < image.length; j++) {
                switch (filter_type) {
                    case STANDARD:
                        filtered_image[i][j] = stdFilter(image, filter_size, i, j);
                        break;
                    case POSITIVE:
                        filtered_image[i][j] = image[i][j] == 1 ? 1 : stdFilter(image, filter_size, i, j);
                        break;
                    default:
                        filtered_image[i][j] = image[i][j] == 0 ? 0 : stdFilter(image, filter_size, i, j);
                        break;
                }
            }
        }
        return filtered_image;
    }

    public static int[][][] filter(int[][][] image, int filter_size, Blur_Filter_Type filter_type) {
        int[][][] filtered_image = new int[image.length][image[0].length][image[0][0].length];
        for(int i = 0; i < image.length; i++) {
            filtered_image[i] = filter(image[i], filter_size, filter_type);
        }
        return filtered_image;
    }


    private static int stdFilter(int[][] image, int filter_size, int x, int y) {
        int hits = 0, points = 0;
        for(int i = x - filter_size; i <= x + filter_size; i++) {
            for(int j = y - filter_size; j <= y + filter_size; j++) {
                if(isPointValid(i, j, image.length, image[0].length)) {
                    points++;
                    if(image[i][j] == 1) {
                        hits++;
                    }
                }
            }
        }
        return (2 * hits - 1) / points;
    }

    private static boolean isPointValid(int x, int y, int length, int width) {
        if(x < 0 ||  length <= x) {
            return false;
        }
        if(y < 0 || width <= y) {
            return false;
        }
        return true;
    }
}
