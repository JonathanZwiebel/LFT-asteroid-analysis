package stars;

import static helper.FitsHelper.readFile;
import static helper.FitsHelper.extractFilteredColumn;

import filter.MeanBrightBodyFilter;
import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Stores BinaryTrackerInstances over a single FITs file. Will filter a given image based on a passed binary filter and
 * then group adjacent units into BrightBodies. BrightBodies will be tracked by BinaryTrackerInstances.
 */
public class BinaryTracker {
    private String data_filename_, column_;
    public BinaryTrackerInstance[] instances;
    /**
     * Constructs a BinaryTracker object
     * @param data_filename the data file with the field of bodies
     * @param column column name
     */
    public BinaryTracker(String data_filename, String column) {
        this.data_filename_ = data_filename;
        this.column_ = column;
    }

    /**
     * Sets up BinaryTrackerInstances for each valid index
     * @throws FitsException
     * @throws IOException
     */
    public void track() throws FitsException, IOException {
        Fits fits = readFile(data_filename_);
        float[][][] col = extractFilteredColumn(fits, column_);

        MeanBrightBodyFilter meanBrightBodyFilter = new MeanBrightBodyFilter(data_filename_, "Null", "FLUX");
        int[][][] filtered_col = meanBrightBodyFilter.filter();

        instances = new BinaryTrackerInstance[col.length];
        for(int i = 0; i < col.length; i++) {
            instances[i] = new BinaryTrackerInstance(col, filtered_col, i);
        }
    }

    /**
     * Generates text files for each instance in the tracker
     * @param text_directory directory to place the .txt files
     * @throws IOException
     */
    public void toTextFiles(String text_directory) throws IOException {
        int count = 0;
        for(BinaryTrackerInstance instance : instances) {
            instance.toTextFile(text_directory + "\\index" + count + ".txt");
            count++;
        }
    }

    /**
     * Generates serialized BinaryTrackerInstances as .ser files for all instances within the tracker
     * @param serialized_directory directory to place the .ser files
     * @throws IOException
     */
    public void toSerializedFiles(String serialized_directory) throws IOException{
        int count = 0;
        for(BinaryTrackerInstance instance : instances) {
            instance.serialize(serialized_directory + "\\index" + count + ".txt");
            count++;
        }
    }

    /**
     * Generates CSVs for all instances within the tracker
     * @param csv_directory directory to place the .csv files
     * @throws IOException
     */
    public void toCSVs(String csv_directory) throws IOException{
        int count = 0;
        for(BinaryTrackerInstance instance : instances) {
            instance.toCSV(csv_directory + "\\index" + count + ".csv");
            count++;
        }
    }

    /**
     * Generates a massive CSV that includes the brightness over time for the n brightest bright bodies
     * @param filename .csv file location
     * @param body_count number of bright bodies to include
     * @throws IOException
     */
    public void toMassAreaSortedCSV(String filename, int body_count) throws IOException {
        float[][] rank_ordered_bright_bodies = new float[body_count][instances.length];
        for(BinaryTrackerInstance instance : instances) {
            instance.addToRankOrderedBrightBodies(rank_ordered_bright_bodies);
        }

        File f = new File(filename);
        if(!f.exists()) {
            f.createNewFile();
        }
        FileWriter file_writer = new FileWriter(f.getAbsoluteFile());
        BufferedWriter writer = new BufferedWriter(file_writer);
        writer.write("Index");
        for(int i = 1; i <= body_count; i++) {
            writer.write(",Body of Size Rank " + i + " Area");
        }
        writer.newLine();

        for(int index = 0; index < instances.length; index++) {
            writer.write(Integer.toString(index));
            for(int bright_body_rank = 1; bright_body_rank <= body_count; bright_body_rank++ ) {
                writer.write("," + Float.toString(rank_ordered_bright_bodies[bright_body_rank - 1][index]));
            }
            writer.newLine();
        }
        writer.close();
        file_writer.close();
    }
}
