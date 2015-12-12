package mains;

import static helper.FitsHelper.*;
import static helper.K2ValidificationHelper.*;
import static helper.SubtractiveHelper.*;
import nom.tam.fits.Fits;
import nom.tam.fits.TableHDU;

import java.util.ArrayList;

public class MeanSubtractive {
	public static final int INITIAL_BASE = 1;
	public static final int DELTA_TIME = 15;
	public static final int TIME_SPACING = 15;
    public static final String COLUMN = "FLUX";
	public static final String OUTPUT_FOLDERNAME = "C:\\Users\\user\\Desktop\\K2\\subtractive\\mean";
    public static final String INPUT_FILENAME = "C:\\Users\\user\\Desktop\\K2\\raw\\ktwo200000908-c00_lpd-targ.fits";

	public static void main(String[] args) {
        try {
            Fits fits = readFile(INPUT_FILENAME);
            TableHDU<?> table = extractTable(fits, 1);
            float[][][] raw_data = (float[][][]) table.getColumn(COLUMN);

            ArrayList<Boolean> valid_indices = new ArrayList<>();
            validify(fits, valid_indices);
            for(int i = INITIAL_BASE; i + DELTA_TIME <= valid_indices.size(); i += TIME_SPACING) {
                boolean valid = true;

                for(int j = i; j <= i + DELTA_TIME; j++) {
                    if(!valid_indices.get(j)) {
                        valid = false;
                        break;
                    }
                }

                if(valid) {
                    float[][] base = extractDataSlice(raw_data, i);
                    float[][] top = extractDataSlice(raw_data, i + DELTA_TIME);
                    float[][] delta = subtractImages(top, base);
                    String output_filename = OUTPUT_FOLDERNAME + "\\delta" + i + "to" + (i + DELTA_TIME) + ".fits";
                    float[][] mean = meanImage(base, top);
                    float[][] divided = divideImage(delta, mean);
                    write2DImage(divided, output_filename);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
	}

}