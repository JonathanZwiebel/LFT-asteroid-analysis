package analysis;


import helper.FitsHelper;
import nom.tam.fits.Fits;
import nom.tam.fits.TableHDU;

public class MomentTester {
    public static void main(String[] args) {
        try {
            Fits f = FitsHelper.readFile("C:\\Users\\user\\Desktop\\K2\\raw\\ktwo200000905-c00_lpd-targ.fits");
            TableHDU<?> thdu = (TableHDU<?>) FitsHelper.extractTable(f, 1);
            float[][][] flux_col = (float[][][]) thdu.getColumn("FLUX");
            float[][] flux_values_t_1 = flux_col[1];
            System.out.println("Area: " + ImageMoment.area(flux_values_t_1));
            System.out.println("Centroid: (" + ImageMoment.centroid_x(flux_values_t_1) + " , " + ImageMoment.centroid_y(flux_values_t_1) + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
