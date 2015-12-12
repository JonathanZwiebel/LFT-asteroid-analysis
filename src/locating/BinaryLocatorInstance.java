package locating;

import brightbodies.BrightBodyList;
import filter.BinaryFilter;

/**
 * @author Jonathan Zwiebel
 * @version December 3rd, 2015
 */
public class BinaryLocatorInstance extends LocatorInstance {

    public BinaryLocatorInstance(float[][] data) {
        super(data);
    }

    /**
     * Finds all of the pixels above the threshold value
     * @return List of bright bodies, joined pixels above threshold value
     */
    public BrightBodyList locate(Locator parent) {
        int[][] filtered = BinaryFilter.filter(data_, ((BinaryLocator) parent).threshold_);
        BrightBodyList bodies = BrightBodyLocator.binaryLocate(data_.clone(), filtered.clone());
        bodies.sortByArea();
        return bodies;
    }
}
