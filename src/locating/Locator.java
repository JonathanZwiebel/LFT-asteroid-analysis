package locating;

/**
 * @author Jonathan Zwiebel
 * @version November 30th, 2015
 */
public abstract class Locator {
    protected float[][][] data_;

    public Locator(float[][][] data) {
        data_ = data;
    }

    public abstract TrackerInstance[] locate();

}
