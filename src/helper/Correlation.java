package helper;

import brightbodies.Coordinate;

/**
 * This class contains some basic mathematical functions to calculate the r^2 value of a set of points
 *
 * @author Jonathan Zwiebel
 * @version 17 June 2016
 */
public final class Correlation {
    public static float coefficientOfDetermination(Coordinate[] data) {
        float sum_x = 0, sum_y = 0, sum_xy = 0, sum_xx = 0, sum_yy = 0;
        for(Coordinate c : data) {
            sum_x += c.x;
            sum_y += c.y;
            sum_xy += c.x * c.y;
            sum_xx += c.x * c.x;
            sum_yy += c.y * c.y;
        }
        int n = data.length;
        float r = (float) ((n * sum_xy - sum_x * sum_y) / Math.pow((n * sum_xx - sum_x * sum_x) * (n * sum_yy - sum_y * sum_y), 0.5f));
        return r * r;
    }
}
