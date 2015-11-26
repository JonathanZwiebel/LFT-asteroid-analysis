package preprocessing;

import java.io.File;

/**
 * @author Jonathan Zwiebel
 * @version November 25th, 2015
 *
 * This abstract class takes in a file of astronomical static field data and coverts it to a data cube where the first
 * index correspond, the other two correspond to location (RA and D) and the floating point value at each cell is the
 * brightness as detected by a CCD array.
 * TODO[Major]: Make this work with non-rectangular data sets
 */
public abstract class Preprocessor {
    public abstract float[][][] read(File f);
}
