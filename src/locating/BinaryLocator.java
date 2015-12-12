package locating;

import analysis.CubeStats;

/**
 * @author Jonathan Zwiebel
 * @version December 3rd, 2015
 */
public class BinaryLocator extends Locator {
    public float threshold_;


    public BinaryLocator(float[][][] data) {
        super(data);
    }

    public void initialize() {
        instances_ = new BinaryLocatorInstance[data_.length];

        for(int index = 0; index < data_.length; index++) {
            instances_[index] = new BinaryLocatorInstance(data_[index]);
        }

        threshold_ = CubeStats.mean(data_);
        // TODO[Fix]: Throws a bug when threshold is to low
    }
}
