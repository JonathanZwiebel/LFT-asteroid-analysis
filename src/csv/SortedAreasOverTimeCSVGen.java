package csv;

import stars.BrightBodyTracker;

/**
 * Generates a CSV from a BrightBodyTracker object where each column is a valid timestamp and
 * each row is the area of a BrightBody, sorted started from the greatest area
 */
public class SortedAreasOverTimeCSVGen implements CSVGenerator{

    /**
     * Generates a CSV from a BrightBodyTracker object
     * @param data the BrightBodyTracker object
     * @param filename location to store the CSV
     * @throws CSVGeneratorDataExcpetion
     */
    public void generateCSV(Object data, String filename) throws CSVGeneratorDataExcpetion {
        if(!(data instanceof BrightBodyTracker)) {
            throw new CSVGeneratorDataExcpetion();
        }
    }
}
