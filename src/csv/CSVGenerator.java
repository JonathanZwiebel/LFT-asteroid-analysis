package csv;

/**
 * This interface is for classes that produce CSVs
 */
public interface CSVGenerator {
    void generateCSV(Object data, String filename) throws CSVGeneratorDataExcpetion;
}
