package stars;

import java.io.Serializable;

public class PixelPoint implements Serializable {
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

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
