package analysis;

import stars.Coordinate;
import stars.PixelPoint;

public class BrightBodyMoment {

    /**
     * Finds the M_ij of the star
     * @param i x-weight
     * @param j j-weight
     */
    public static float moment(float[][] image, PixelPoint[] star, int i, int j) {
        float moment = 0;
        for(PixelPoint p : star) {
            moment += image[p.y][p.x] * Math.pow(p.x, i) * Math.pow(p.y, j);
        }
        return moment;
    }

    public static float area(float[][] image, PixelPoint[] star) {
        return moment(image, star, 0, 0);
    }

    public static Coordinate centroid(float[][] image, PixelPoint[] star) {
        float x = moment(image, star, 1, 0) / area(image, star);
        float y = moment(image, star, 0, 1) / area(image, star);
        return new Coordinate(x, y);
    }
}
