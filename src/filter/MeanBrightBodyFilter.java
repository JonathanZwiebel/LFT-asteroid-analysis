package filter;

import helper.FitsHelper;
import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;

import java.io.IOException;


/**
 * This class filters a FITS image by doing a positive binary filter over the mean value of the image
 */
public class MeanBrightBodyFilter implements BrightBodyFilter {
    private String output_head_, column_;
    private float[][][] data_;

    /**
     * Constructs a MeanBrightBodyFilter object
     * @param data Preprocessed data
     * @param output_head Filtered output file
     * @param column Column to filter
     */
    public MeanBrightBodyFilter(float[][][] data, String output_head, String column) {
        data_ = data;
        output_head_ = output_head;
        column_ = column;
    }

    /**
     * Generates and returns the filtered data cube
     * @return the filtered data cube
     * @throws FitsException
     * @throws IOException
     */
    public int[][][] filter() throws FitsException, IOException {
        BinaryFilter binaryFilter = new BinaryFilter(data_);
        int[][][] filtered = binaryFilter.meanFilter();
        return filtered;
    }

    /**
     * Generates and writes the filtered data cube
     * @throws FitsException
     * @throws IOException
     */
    public void writeFilter() throws FitsException, IOException {
        BinaryFilter binaryFilter = new BinaryFilter(data_);
        int[][][] filtered = binaryFilter.meanFilter();
        FitsHelper.writeDataCube(filtered, output_head_ + "-binfilmean.fits");
    }
}