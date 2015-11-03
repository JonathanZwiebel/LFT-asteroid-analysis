package filter;

import helper.ArrayHelper;
import helper.FitsHelper;
import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;

import java.io.IOException;


/**
 * This class filters a FITS image by doing a positive binary filter over the mean
 */
public class MeanBrightBodyFilter implements BrightBodyFilter {
    String input_filename_, output_head_, column_;

    public MeanBrightBodyFilter(String input_filename, String output_head, String column) {
        this.input_filename_ = input_filename;
        this.output_head_ = output_head;
        this.column_ = column;
    }

    public float[][][] filter() throws FitsException, IOException {
        Fits f = FitsHelper.readFile(input_filename_);
        float[][][] column = FitsHelper.extractFilteredColumn(f, column_);
        int[][][] filtered = BinaryFilter.meanFilter(column);
        return ArrayHelper.intToFloat(filtered);
    }

    public void writeFilter() throws FitsException, IOException {
        Fits f = FitsHelper.readFile(input_filename_);
        float[][][] column = FitsHelper.extractFilteredColumn(f, column_);
        int[][][] filtered = BinaryFilter.meanFilter(column);
        FitsHelper.writeDataCube(filtered, output_head_ + "-binfilmean.fits");
    }
}