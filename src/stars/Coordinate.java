package stars;

import java.io.Serializable;

public class Coordinate implements Serializable{
    public float x, y;

    public Coordinate(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
