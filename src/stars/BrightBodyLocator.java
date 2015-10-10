package stars;

import java.util.ArrayList;

// needs to check for no collisions
public class BrightBodyLocator {
    public static ArrayList<BrightBody> binaryLocate(float[][] originalImage, int[][] binaryImage) {
        ArrayList<ArrayList<PixelPoint>> bright_bodies = new ArrayList();
        ArrayList<BrightBody> true_bright_bodies = new ArrayList();

        for(int i = 0; i < binaryImage.length; i++) {
            for(int j = 0; j < binaryImage[0].length; j++) {
                if(binaryImage[j][i] == 1) {
                    num++;
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
                    bright_body.add(new PixelPoint(i, j));
                    break;
                }
                if (p.x == i + 1 && p.y == j) {
                    caught = true;
                    bright_body.add(new PixelPoint(i, j));
                    break;
                }
                if(p.x == i && p.y == j + 1) {
                    caught = true;
                    bright_body.add(new PixelPoint(i, j));
                    break;
                }
                if(p.x == i && p.y == j - 1) {
                    caught = true;
                    bright_body.add(new PixelPoint(i, j));
                    break;
                }
            }
            if(caught) {
                break;
            }
        }
        if(!caught) {
            uncaught++;
            System.out.println("uncaught: " + uncaught + " | " + i + " , " + j);
            ArrayList<PixelPoint> new_bright_body = new ArrayList();
            new_bright_body.add(new PixelPoint(i, j));
            bright_bodies.add(new_bright_body);
        }
    }

    static int uncaught = 0, num = 0;
}
