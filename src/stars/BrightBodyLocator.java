package stars;

import java.util.ArrayList;

// needs to check for no collisions
public class BrightBodyLocator {
    public static ArrayList<BrightBody> testLocate(float[][] image) {
        PixelPoint[] star1 = {new PixelPoint(41, 11), new PixelPoint(42, 11), new PixelPoint(41, 10), new PixelPoint(42, 10)};
        BrightBody test1 = new BrightBody(image, star1);

        PixelPoint[] star2 = {new PixelPoint(28, 24), new PixelPoint(28, 23), new PixelPoint(29, 24), new PixelPoint(29, 23)};
        BrightBody test2 = new BrightBody(image, star2);

        ArrayList<BrightBody> ret = new ArrayList();
        ret.add(test1);
        ret.add(test2);
        return ret;
    }

    public static ArrayList<BrightBody> binaryLocate(float[][] binaryImage) {
        ArrayList<BrightBody> bright_bodies = new ArrayList();

        for(int i = 0; i < binaryImage.length; i++) {
            for(int j = 0; j < binaryImage[0].length; j++) {

            }
        }
    }
}
