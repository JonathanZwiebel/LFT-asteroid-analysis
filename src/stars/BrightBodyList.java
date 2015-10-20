package stars;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A serializable AL of BrightBodies
 * */
public class BrightBodyList extends ArrayList<BrightBody> implements Serializable {

    public String toString() {
        String ret = "";
        for(BrightBody b : this) {
            ret += b;
        }
        return ret;
    }

    public void sortByArea() {
        Collections.sort(this, Collections.reverseOrder());
    }
}
