package brightbodies;

import java.util.ArrayList;

/**
 * Represents a BrightBody in a single frame that is created by adding pixels
 * one at a time for a fixed and known timestamp. This is to be used with 
 * TSOutlierLocation to assist in storing temporary bodies.
 *
 * @author Jonathan Zwiebel
 * @version 10 August 2016
 */
public class FixedTimeBrightBodyBuilder {
    private final int timestamp_;
    private ArrayList<CartesianPoint> pixels;

    public FixedTimeBrightBodyBuilder(int timestamp) {
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

    public int getTimestamp() {
        return timestamp_;
    }
}
