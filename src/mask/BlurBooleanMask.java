package mask;

/**
 * This class filters an image by checking the pixels around it and setting the value to their median
 * TODO: Make non-static
 */
public class BlurBooleanMask implements BooleanMask {
    public enum BlurFilterType {
        STANDARD,
        POSITIVE,
        NEGATIVE
    }

    private boolean[][] data_;

    public BlurBooleanMask(boolean[][] data) {
        data_ = data;
    }

    public boolean[][] mask(float ... args) {
        boolean[][] masked_data = new boolean[data_.length][data_[0].length];
        int filter_size = (int) args[0];

        // TODO: Fix this sketch by taking objects or strings
        BlurFilterType filter_type;
        if((int) args[1] == 0) {
            filter_type = BlurFilterType.STANDARD;
        }
        else {
            filter_type = BlurFilterType.POSITIVE;
        }

        for (int i = 0; i < data_[0].length; i++) {
            for (int j = 0; j < data_.length; j++) {
                switch (filter_type) {
                    case STANDARD:
                        masked_data[i][j] = stdFilter(data_, filter_size, i, j);
                        break;
                    case POSITIVE:
                        masked_data[i][j] = data_[i][j] || stdFilter(data_, filter_size, i, j);
                        break;
                    case NEGATIVE:
                        masked_data[i][j] = !data_[i][j] && stdFilter(data_, filter_size, i, j);
                        break;
                    default:
                        masked_data[i][j] = stdFilter(data_, filter_size, i, j);
                }
            }
        }
        return masked_data;
    }
    private static boolean stdFilter(boolean[][] image, int filter_size, int x, int y) {
        int hits = 0, points = 0;
        for(int i = x - filter_size; i <= x + filter_size; i++) {
            for(int j = y - filter_size; j <= y + filter_size; j++) {
                if(isPointValid(i, j, image.length, image[0].length)) {
                    points++;
                    if(image[i][j]) {
                        hits++;
                    }
                }
            }
        }
        return ((2 * hits - 1) / points) > 0.5f;
    }

    private static boolean isPointValid(int x, int y, int length, int width) {
        if(x < 0 ||  length <= x) {
            return false;
        }
        return !(y < 0 || width <= y);
    }
}
