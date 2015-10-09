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
            float[][][] flux_column = FitsHelper.extractFilteredColumn(f, "FLUX");
            PixelPoint[] test_star_body = {new PixelPoint(49, 49)};
            Star test_star = new Star(flux_column[0], test_star_body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
