package stars;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A serializable and sortable ArrayList of bright bodies
 * */
public class BrightBodyList extends ArrayList<BrightBody> implements Serializable {

    public String toString() {
        String ret = "";
        for(BrightBody b : this) {
            ret += b + "\n";
        }
        return ret;
    }

    /**
     * Sorts all of the bright bodies in descending order (largest area first)
     */
    public void sortByArea() {
        Collections.sort(this, Collections.reverseOrder());
    }
}
