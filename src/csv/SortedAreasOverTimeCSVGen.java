package csv;

import locating.BinaryTracker;

import java.io.*;

/**
 * Generates a CSV from a BinaryTracker object where each column is a valid timestamp and
 * each row is the area of a Track, sorted started from the greatest area
 */
public class SortedAreasOverTimeCSVGen implements CSVGenerator{

    /**
     * Generates a CSV from a BinaryTracker object
     * @param data the BinaryTracker object
     * @param filename location to store the CSV
     * @throws CSVGeneratorDataExcpetion
     */
    public void generateCSV(Object data, String filename) throws CSVGeneratorDataExcpetion, IOException {
        if(!(data instanceof BinaryTracker)) {
            throw new CSVGeneratorDataExcpetion();
        }
        BinaryTracker tracker_ = (BinaryTracker) data;
        FileWriter writer_ = new FileWriter(filename);
    }
}
