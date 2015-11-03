package filter;

import nom.tam.fits.FitsException;

import java.io.IOException;

/**
 * Interface for filtering a TrackBrightBody
 */
public interface BrightBodyFilter {
    void writeFilter() throws FitsException, IOException;
    float[][][] filter() throws FitsException, IOException;
}
