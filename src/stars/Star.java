package stars;

import analysis.StarMoment;

public class Star {
    public float[][] image;
    Coordinate centroid;
    float area;
    PixelPoint[] pre_filter_body;
    PixelPoint[] body;

    public Star(float[][] image, PixelPoint[] pre_filter_body) {
        this.pre_filter_body = pre_filter_body;
        body = pre_filter_body.clone(); // fow now

        centroid = StarMoment.centroid(image, body.clone());
        area = StarMoment.area(image, body.clone());

        System.out.println("Area: " + area);
        System.out.println("Centroid: " + "(" + centroid.x + " , " + centroid.y + ")");
    }
}
