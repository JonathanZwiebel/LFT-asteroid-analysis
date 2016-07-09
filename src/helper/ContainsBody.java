package helper;

import brightbodies.BrightBody;
import brightbodies.BrightBodyList;
import brightbodies.CartesianPoint;

/**
 * @author Jonathan Zwiebel
 * @version 18 May 2016
 *
 * This class contains static method to determine if a certain BrightBodyList contains a given asteroid
 */
public final class ContainsBody {

    /**
     * Determines if bodies contains a BrightBody with point(x, y) and size between min_size and max_size inclusive
     * @param bodies the list of bodies to search
     * @param x_peak x coordinate of known asteroid center
     * @param y_peak y coordinate of known asteroid center
     * @param min_size minimum size to return positive
     * @param max_size maximum size to return positive
     * @return does the BrightBodyList contain the asteroid?
     */
    public static boolean containsBody(BrightBodyList bodies, int x_peak, int y_peak, int min_size, int max_size) {
        for(BrightBody body : bodies) {
            if(!body.contains(new CartesianPoint(x_peak, y_peak))) {
                continue;
            }
            if(bodies.size() > max_size || bodies.size() < min_size) {
                continue;
            }
            return true;
        }
        return false;
    }
}
