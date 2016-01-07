package filtering;

import brightbodies.BrightBodyList;

/**
 * This class represents the third macro step and splits up the bright bodies into mobile bright bodies and
 * immobile bright bodies
 *
 * @author Jonathan Zwiebel
 * @version January 6th, 2016
 */
public abstract class MobilityFilter {
    protected BrightBodyList[] bright_body_list_;

    /**
     * Constructs a MobilityFilter object that will filter the immobile and mobile bright bodies
     * @param bright_body_list bright body list extracted from locator
     */
    public MobilityFilter(BrightBodyList[] bright_body_list) {
        bright_body_list_ = bright_body_list;
    }

    /**
     * Returns a two-wide data cube where the first index is the immobile bright bodies and the second index is the
     * mobile bright bodies
     * @return Separated mobile and immobile bright bodies
     */
    public abstract BrightBodyList[][] filter();
}
