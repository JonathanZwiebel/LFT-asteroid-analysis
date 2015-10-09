package analysis;


import helper.FitsHelper;
import nom.tam.fits.Fits;
import nom.tam.fits.TableHDU;
import stars.PixelPoint;
import stars.Star;

public class MomentTester {
    public static void main(String[] args) {
        try {
            Fits f = FitsHelper.readFile("C:\\Users\\user\\Desktop\\K2\\raw\\ktwo200000908-c00_lpd-targ.fits");
            TableHDU<?> thdu = (TableHDU<?>) FitsHelper.extractTable(f, 1);
            float[][][] flux_col = (float[][][]) thdu.getColumn("FLUX");
            float[][] flux_values_t_1 = flux_col[0];
            System.out.println("Area: " + ImageMoment.area(flux_values_t_1));
            System.out.println("Centroid: (" + ImageMoment.centroid_x(flux_values_t_1) + " , " + ImageMoment.centroid_y(flux_values_t_1) + ")");

            PixelPoint[] test_star_body = {new PixelPoint(38, 41), new PixelPoint(39, 41), new PixelPoint(38, 42), new PixelPoint(39, 42)};
            System.out.println("Test: " + flux_values_t_1[41][38]);
            Star test_star = new Star(flux_values_t_1, test_star_body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
