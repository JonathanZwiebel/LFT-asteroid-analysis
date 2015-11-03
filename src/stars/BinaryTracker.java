package stars;

import static filter.BrightBodyFilter.getFilteredCube;
import static helper.FitsHelper.readFile;
import static helper.FitsHelper.extractFilteredColumn;
import static helper.FitsHelper.writeDataCube;
import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;

import java.io.IOException;

/**
 * Stores BinaryTrackerInstances over a single FITs file. Will filter a given image based on a passed binary filter and
 * then group adjacent units into BrightBodies. BrightBodies will be tracked by BinaryTrackerInstances
 */
public class BinaryTracker {
    String data_filename_, secondary_filename_, text_directory_, serialized_directory_, column_;

    /**
     * Constructs a BinaryTracker object
     * @param data_filename the data file with the field of bodies
     * @param secondary_filename the binary filter file
     * @param text_directory directory to dump generated .txt files
     * @param serialized_directory directory to dump generated .ser files
     * @param column column name
     */
    public BinaryTracker(String data_filename, String secondary_filename, String text_directory, String serialized_directory, String column) {
        this.data_filename_ = data_filename;
        this.secondary_filename_ = secondary_filename;
        this.text_directory_ = text_directory;
        this.serialized_directory_ = serialized_directory;
        this.column_ = column;
    }

    /**
     * Sets up BinaryTrackerInstances for each valid index
     * @throws FitsException
     * @throws IOException
     */
    public void track() throws FitsException, IOException {
        Fits fits = readFile(data_filename_);
        float[][][] col_ = extractFilteredColumn(fits, column_);

        int[][][] filtered_col_ = getFilteredCube();
        writeDataCube(filtered_col_, secondary_filename_);


        for(int i = 0; i < col_.length; i++) {
            BinaryTrackerInstance t = new BinaryTrackerInstance(col_, filtered_col_, i);
            t.toTextFile(text_directory_ + "\\index" + i + ".txt");
            t.serialize(serialized_directory_ + "\\index" + i + ".ser");
        }
    }
}
