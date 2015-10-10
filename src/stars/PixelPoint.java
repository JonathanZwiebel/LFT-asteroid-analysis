package stars;

public class PixelPoint {
    public int x, y;

    public PixelPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals (PixelPoint p) {
        if (x == p.x && y == p.y) {
            return true;
        }
        return false;
    }
}
