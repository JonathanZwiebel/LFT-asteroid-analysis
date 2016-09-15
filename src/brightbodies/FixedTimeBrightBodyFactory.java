package brightbodies;

import java.util.ArrayList;

/**
 * Represents a BrightBody in a single frame that is created by adding pixels
 * one at a time for a fixed and known timestamp. This is to be used with 
 * TSOutlierLocation to assist in storing temporary bodies. FTBBFs are compared
 * by the number of elements in them to assist with locating outlier blobs
 * with the highest likelihood.
 *
 * @author Jonathan Zwiebel
 * @version 14 September 2016
 */
public class FixedTimeBrightBodyFactory implements Comparable<FixedTimeBrightBodyFactory> {
    private final int timestamp_;
    private final ArrayList<CartesianPoint> pixels;

    public FixedTimeBrightBodyFactory(int timestamp) {
        timestamp_ = timestamp;
        pixels = new ArrayList();
    }
    
    /**
     * Adds a pixel with known location to the BrightBody. Ignores repeats. 
     * 
     * @param i Rightward x-value of the pixel. 0 indexed.
     * @param j Downward y-value of the pixel. 0 indexed.
     * TODO: Figure out what i and j correspond to within the image
     */
    public void addPixel(int i, int j) {
        boolean contains = false;
        CartesianPoint newPixel = new CartesianPoint(i, j);

        for(CartesianPoint pixel : pixels) {
            if(pixel.equals(newPixel)) { 
                contains = true;
            }
        }

        if(!contains) {
            pixels.add(newPixel);
        }
    }

    /**
     * If this FTBBF has a higher number of pixel values as other returns a positive value
     * If this FTBBF has the same number of pixel values as other returns zero
     * If this FTBBF has a lower number of pixel values as other returns a negative value
     * @param other FixedTimeBrightBodyFactory to compare to
     * @return comparison result
     */
    public int compareTo(FixedTimeBrightBodyFactory other) {
        return pixels.size() - other.pixels.size();
    }

    @Override
    public String toString() {
        String buf = "";
        buf += "FixedTimeBrightBodyFactory at timestamp " + timestamp_;
        for(CartesianPoint p : pixels) {
            buf += "\n" + p;
        }
        buf += "\n";
        return buf;
    }

    public int getTimestamp() {
        return timestamp_;
    }
}
