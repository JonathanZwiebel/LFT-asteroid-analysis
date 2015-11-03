package filter;

import nom.tam.fits.FitsException;

import java.io.IOException;

/**
 * Interface for filtering a TrackBrightBody
 */
public interface BrightBodyFilter {
    /**
     * Generates and writes the data cube
     * @throws FitsException
     * @throws IOException
     */
    void writeFilter() throws FitsException, IOException;

    /**
     * Generates and returns the filtered data cube
     * @return the filtered data cube
     * @throws FitsException
     * @throws IOException
     */
    int[][][] filter() throws FitsException, IOException;
}
