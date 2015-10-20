package stars;


import static analysis.BrightBodyMoment.centroid;
import static analysis.BrightBodyMoment.area;

public class BrightBody implements Comparable<BrightBody>{
    public float[][] image;
    Coordinate centroid;
    float area;
    PixelPoint[] pre_filter_body;
    PixelPoint[] body;

    public BrightBody(float[][] image, PixelPoint[] pre_filter_body) {
        this.pre_filter_body = pre_filter_body;
        body = pre_filter_body.clone(); // for now

        centroid = centroid(image, body.clone());
        area = area(image, body.clone());
    }

    public boolean contains(PixelPoint point) {
        for(PixelPoint p : pre_filter_body) {
            if(point.x == p.x && point.y == p.y) {
                return true;
            }
        }
        return false;
    }

    public int compareTo(BrightBody b) {
        return (int) this.area - (int) b.area;
    }

    public String toString() {
        return("Size: " + body.length + " | Area: " + area + " | Centroid: " + "(" + centroid.x + " , " + centroid.y + ")");
    }
}
