package brightbodies;

import java.io.Serializable;

/**
 * A cartesian coordinate point
 */
public class CartesianPoint implements Serializable {
    public final int x;
    public final int y;

    /**
     * Constructs a CartesianPoint
     * @param x the x-value
     * @param y the y-value
     */
    public CartesianPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals (CartesianPoint p) {
        return x == p.x && y == p.y;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
