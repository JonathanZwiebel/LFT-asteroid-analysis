package filter;

import helper.ArrayHelper;
import helper.FitsHelper;
import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;

import java.io.IOException;


/**
 * This class filters a FITS image by doing a positive binary filter over the mean value of the image
 */
public class MeanBrightBodyFilter implements BrightBodyFilter {
    String input_filename_, output_head_, column_;

    /**
     * Constructs a MeanBrightBodyFilter object
     * @param input_filename BrightBody file
     * @param output_head Filtered output file
     * @param column Column to filter
     */
    public MeanBrightBodyFilter(String input_filename, String output_head, String column) {
        this.input_filename_ = input_filename;
        this.output_head_ = output_head;
        this.column_ = column;
    }

    /**
     * Generates and returns the filtered data cube
     * @return the filtered data cube
     * @throws FitsException
     * @throws IOException
     */
    public float[][][] filter() throws FitsException, IOException {
        Fits f = FitsHelper.readFile(input_filename_);
        float[][][] column = FitsHelper.extractFilteredColumn(f, column_);
        int[][][] filtered = BinaryFilter.meanFilter(column);
        return ArrayHelper.intToFloat(filtered);
    }

    /**
     * Generates and writes the filtered datat cube
     * @throws FitsException
     * @throws IOException
     */
    public void writeFilter() throws FitsException, IOException {
        Fits f = FitsHelper.readFile(input_filename_);
        float[][][] column = FitsHelper.extractFilteredColumn(f, column_);
        int[][][] filtered = BinaryFilter.meanFilter(column);
        FitsHelper.writeDataCube(filtered, output_head_ + "-binfilmean.fits");
    }
}