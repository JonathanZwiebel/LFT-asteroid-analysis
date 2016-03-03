package brightbodies;

import java.io.Serializable;

/**
 * Serializable coordinate position
 */
public class Coordinate implements Serializable{
    public final float x;
    public final float y;

    /**
     * Constructs a coordinate position
     * @param x the x-value
     * @param y the y-value
     */
    public Coordinate(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
