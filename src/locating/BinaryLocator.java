package locating;

/**
 * @author Jonathan Zwiebel
 * @version December 3rd, 2015
 */
public class BinaryLocator extends Locator {

    public BinaryLocator(float[][][] data) {
        super(data);
    }

    public void generateInstances() {
        instances_ = new BinaryLocatorInstance[data_.length];

        for(int index = 0; index < data_.length; index++) {
            instances_[index] = new BinaryLocatorInstance(data_[index]);
        }
    }
}
