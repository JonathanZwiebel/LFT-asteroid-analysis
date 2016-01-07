package filtering;

import brightbodies.BrightBodyList;

/**
 * @author Jonathan Zwiebel
 * @version January 6th, 2016
 */
public class ReferenceMobilityFilter extends MobilityFilter {

    public ReferenceMobilityFilter(BrightBodyList[] bright_body_list) {
        super(bright_body_list);
    }

    // TODO: Implement
    public BrightBodyList[][] filter() {
        return new BrightBodyList[0][0];
    }
}
