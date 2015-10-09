package analysis;


import helper.FitsHelper;
import nom.tam.fits.Fits;
import stars.PixelPoint;
import stars.BrightBody;

public class MomentTester {
    public static void main(String[] args) {
        try {
            Fits f = FitsHelper.readFile("C:\\Users\\user\\Desktop\\K2\\raw\\ktwo200000908-c00_lpd-targ.fits");
            float[][][] flux_column = FitsHelper.extractFilteredColumn(f, "FLUX");
            PixelPoint[] test_star_body = {new PixelPoint(41, 10), new PixelPoint(41, 11), new PixelPoint(42, 10), new PixelPoint(42, 11)};
            BrightBody test_bright_body = new BrightBody(flux_column[0], test_star_body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
