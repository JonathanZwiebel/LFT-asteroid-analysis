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

    public static ArrayList<BrightBody> binaryLocate(float[][] originalImage, float[][] binaryImage) {
        ArrayList<ArrayList<PixelPoint>> bright_bodies = new ArrayList();
        ArrayList<BrightBody> true_bright_bodies = new ArrayList();

        for(int i = 0; i < binaryImage.length; i++) {
            for(int j = 0; j < binaryImage[0].length; j++) {
                if(binaryImage[i][j] == 1) {
                    addToBodyList(i, j, bright_bodies);
                }
            }
        }

        for(ArrayList<PixelPoint> bright_body : bright_bodies) {
            BrightBody true_bright_body = new BrightBody(originalImage, bright_body.toArray(new PixelPoint[0]));
            true_bright_bodies.add(true_bright_body);
        }
        return true_bright_bodies;
    }

    private static void addToBodyList(int i, int j, ArrayList<ArrayList<PixelPoint>> bright_bodies){
        boolean caught = false;
        for(ArrayList<PixelPoint> bright_body : bright_bodies) {
            for(PixelPoint p : bright_body) {
                if (p.x == i - 1 && p.y == j) {
                    caught = true;
                    bright_body.add(p);
                    break;
                }
                if(p.x == i && p.y == j - 1) {
                    caught = true;
                    bright_body.add(p);
                    break;
                }
            }
            if(caught) {
                break;
            }
        }
        if(!caught) {
            ArrayList<PixelPoint> new_bright_body = new ArrayList();
            new_bright_body.add(new PixelPoint(i, j));
            bright_bodies.add(new_bright_body);
        }
    }
}
