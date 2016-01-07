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
    protected BrightBodyList[] bright_body_lists_;

    /**
     * Constructs a MobilityFilter object that will filter the immobile and mobile bright bodies
     * @param bright_body_lists bright body list extracted from locator
     */
    public MobilityFilter(BrightBodyList[] bright_body_lists) {
        bright_body_lists_ = bright_body_lists;
    }

    /**
     * Returns a two-wide data cube where the first index is the immobile bright bodies and the second index is the
     * mobile bright bodies
     * @return Separated mobile and immobile bright bodies
     */
    public abstract BrightBodyList[][] filter();
}
