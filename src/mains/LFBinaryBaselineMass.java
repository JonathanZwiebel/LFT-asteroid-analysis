package mains;

import brightbodies.BrightBodyList;
import filter.MobilityFilter;
import filter.BaselineFrameGenerationMethod;
import filter.BaselineMobilityFilter;
import helper.ContainsBody;
import locate.BinaryLocator;
import locate.BinaryLocatorThresholdType;
import locate.Locator;
import nom.tam.fits.Fits;
import preprocess.K2Preprocessor;
import preprocess.Preprocessor;

import java.io.*;

/**
 * A top level runnable type that will perform locate-track within a range of parameters on a given file and output
 * information for a fixed timestamp comparing the different parameters. This works by performing single locates, and
 * then all filters over that given location data.
 *
 * @author Jonathan Zwiebel
 * @version February 26th, 2016
 */
public final class LFBinaryBaselineMass {
    // TODO: Move enums out
    public enum BinaryLocatorMassType {
        SINGLE, // specify type and arguments that go with it
        GIVEN_RANGE, // specify increment, min, and max
        MEAN_SHIFTED_RANGE, // specify increment, low, and high count
        MEAN_SCALED_RANGE // specify scalar increment, low, and high count
    }

    public enum BaselineFrameMassType {
        SINGLE, // specify type and arguments that go with it
        GIVEN_RANGE, // specify increment, min, and max
        MEAN_SHIFTED_RANGE, // specify increment, low, and high count
        MEAN_SCALED_RANGE // specify scalar increment, low, and high count
    }

    public static void run(String[] args) {
        assert args[0].equals("LF_BINARY_BASELINE_MASS");

        // TODO: Consider making this only accessible by method so every time it is checked, the number actually increments
        int current_arg = 1;

        String filename = args[current_arg];
        current_arg++;

        BinaryLocatorMassType binaryLocatorMassType = null;
        // TODO: Cleanup binaryLocatorMassTypeSingleType variable that is only used when binaryLocatorMassType is SINGLE
        BinaryLocatorThresholdType binaryLocatorMassTypeSingleType = null; // this one is only filled in the SINGLE case
        float[] binaryLocatorMassTypeArgs = null;
        String binaryLocatorMassTypeString = args[current_arg];
        current_arg++;
        switch(binaryLocatorMassTypeString) {
            case "SINGLE":
                binaryLocatorMassType = BinaryLocatorMassType.SINGLE;
                String binaryLocatorMassTypeSingleTypeString = args[current_arg];
                current_arg++;
                switch(binaryLocatorMassTypeSingleTypeString) {
                    case "ABSOLUTE":
                        binaryLocatorMassTypeSingleType = BinaryLocatorThresholdType.ABSOLUTE;
                        binaryLocatorMassTypeArgs = new float[]{Float.parseFloat(args[current_arg])};
                        current_arg++;
                        break;
                    case "MEAN":
                        binaryLocatorMassTypeSingleType = BinaryLocatorThresholdType.MEAN;
                        binaryLocatorMassTypeArgs = new float[]{};
                        break;
                    case "MEAN_SHIFTED":
                        binaryLocatorMassTypeSingleType = BinaryLocatorThresholdType.MEAN_SHIFTED;
                        binaryLocatorMassTypeArgs = new float[]{Float.parseFloat(args[current_arg])};
                        current_arg++;
                        break;
                    case "MEAN_SCALED":
                        binaryLocatorMassTypeSingleType = BinaryLocatorThresholdType.MEAN_SCALED;
                        binaryLocatorMassTypeArgs = new float[]{Float.parseFloat(args[current_arg])};
                        current_arg++;
                        break;
                    default:
                        System.err.println("Illegal binaryLocatorMassTypeSingleType: " + binaryLocatorMassTypeSingleTypeString);
                        System.exit(1);
                }
                break;
            case "GIVEN_RANGE":
                binaryLocatorMassType = BinaryLocatorMassType.GIVEN_RANGE;
                binaryLocatorMassTypeArgs = new float[]{Float.parseFloat(args[current_arg]), Float.parseFloat(args[current_arg + 1]), Float.parseFloat(args[current_arg + 2])};
                current_arg += 3;
                break;
            case "MEAN_SHIFTED_RANGE":
                binaryLocatorMassType = BinaryLocatorMassType.MEAN_SHIFTED_RANGE;
                binaryLocatorMassTypeArgs = new float[]{Float.parseFloat(args[current_arg]), Float.parseFloat(args[current_arg + 1]), Float.parseFloat(args[current_arg + 2])};
                current_arg += 3;
                break;
            case "MEAN_SCALED_RANGE":
                binaryLocatorMassType = BinaryLocatorMassType.MEAN_SCALED_RANGE;
                binaryLocatorMassTypeArgs = new float[]{Float.parseFloat(args[current_arg]), Float.parseFloat(args[current_arg + 1]), Float.parseFloat(args[current_arg + 2])};
                current_arg += 3;
                break;
            default:
                System.err.println("Illegal BinaryLocatorMassType: " + binaryLocatorMassTypeString);
                System.exit(1);
        }

        float similarity_threshold = Float.parseFloat(args[current_arg]);
        current_arg++;

        BaselineFrameMassType baselineMassType = null;
        BaselineFrameGenerationMethod baselineMassTypeSingleType = null; // this one is only filled in the SINGLE case
        float[] baselineMassTypeArgs = null;
        String baselineMassTypeString = args[current_arg];
        current_arg++;
        switch(baselineMassTypeString) {
            case "SINGLE":
                baselineMassType = BaselineFrameMassType.SINGLE;
                String baselineMassTypeSingleTypeString = args[current_arg];
                current_arg++;
                switch(baselineMassTypeSingleTypeString) {
                    case "BINARY_LOCATOR_ABSOLUTE":
                        baselineMassTypeSingleType = BaselineFrameGenerationMethod.BINARY_LOCATOR_ABSOLUTE;
                        baselineMassTypeArgs = new float[]{Float.parseFloat(args[current_arg])};
                        current_arg++;
                        break;
                    case "BINARY_LOCATOR_MEAN":
                        baselineMassTypeSingleType = BaselineFrameGenerationMethod.BINARY_LOCATOR_MEAN;
                        baselineMassTypeArgs = new float[]{};
                        break;
                    case "BINARY_LOCATOR_MEAN_SHIFTED":
                        baselineMassTypeSingleType = BaselineFrameGenerationMethod.BINARY_LOCATOR_MEAN_SHIFTED;
                        baselineMassTypeArgs = new float[]{Float.parseFloat(args[current_arg])};
                        current_arg++;
                        break;
                    case "BINARY_LOCATOR_MEAN_SCALED":
                        baselineMassTypeSingleType = BaselineFrameGenerationMethod.BINARY_LOCATOR_MEAN_SCALED;
                        baselineMassTypeArgs = new float[]{Float.parseFloat(args[current_arg])};
                        current_arg++;
                        break;
                    default:
                        System.err.println("Illegal binaryLocatorMassTypeSingleType: " + baselineMassTypeSingleTypeString);
                        System.exit(1);
                }
                break;
            case "GIVEN_RANGE":
                baselineMassType = BaselineFrameMassType.GIVEN_RANGE;
                baselineMassTypeArgs = new float[]{Float.parseFloat(args[current_arg]), Float.parseFloat(args[current_arg + 1]), Float.parseFloat(args[current_arg + 2])};
                current_arg += 3;
                break;
            case "MEAN_SHIFTED_RANGE":
                baselineMassType = BaselineFrameMassType.MEAN_SHIFTED_RANGE;
                baselineMassTypeArgs = new float[]{Float.parseFloat(args[current_arg]), Float.parseFloat(args[current_arg + 1]), Float.parseFloat(args[current_arg + 2])};
                current_arg += 3;
                break;
            case "MEAN_SCALED_RANGE":
                baselineMassType = BaselineFrameMassType.MEAN_SCALED_RANGE;
                baselineMassTypeArgs = new float[]{Float.parseFloat(args[current_arg]), Float.parseFloat(args[current_arg + 1]), Float.parseFloat(args[current_arg + 2])};
                current_arg += 3;
                break;
            default:
                System.err.println("Illegal RefFrameMassType: " + binaryLocatorMassTypeString);
                System.exit(1);
        }


        String data_location = args[current_arg];
        current_arg++;

        try {
            File dir = new File(data_location);
            if(!dir.exists()) {
                assert dir.mkdirs();
            }

            File mobile = new File(data_location + "/mobile.csv");
            Writer mobile_writer = new FileWriter(mobile);
            BufferedWriter mobile_buffered_writer = new BufferedWriter(mobile_writer);

            File immobile = new File(data_location + "/immobile.csv");
            Writer immobile_writer = new FileWriter(immobile);
            BufferedWriter immobile_buffered_writer = new BufferedWriter(immobile_writer);

            File noise = new File(data_location + "/noise.csv");
            Writer noise_writer = new FileWriter(noise);
            BufferedWriter noise_buffered_writer = new BufferedWriter(noise_writer);

            File contains = new File(data_location + "/contains.csv");
            Writer contains_writer = new FileWriter(contains);
            BufferedWriter contains_buffered_writer = new BufferedWriter(contains_writer);

            File success = new File(data_location + "/success.csv");
            Writer success_writer = new FileWriter(success);
            BufferedWriter success_buffered_writer = new BufferedWriter(success_writer);

            File information = new File(data_location + "/information.txt");
            Writer information_writer = new FileWriter(information);
            BufferedWriter information_buffered_writer = new BufferedWriter(information_writer);

            int timestamp = 1430;
            int x_pos = 9;
            int y_pos = 20;
            int min_size = 2;
            int max_size = 20;

            information_buffered_writer.write("This folder contains data from a locate-filter test on a single frame " +
                            "from " + filename + ". A range of scalars were used to generate target and baseline " +
                            "threshold values relative to the target and baseline mean brightness values. " +
                            "The target threshold value is used to find the " +
                            "bright bodies in each frame while the baseline threshold value is used to find the " +
                            "the bright bodies that can be found over the 'mean image'. " +
                    "Objects that are found in single frames but not the mean image " +
                            "are classified as mobile as they were only present for small portions of time and have " +
                            "had their brightness 'averaged' out over the entire dataset. Objects found in both the " +
                            "single and mean images are classified as immobile. Objects that are smaller than a given" +
                            "size are classified as noise. The count of each kind of object for the different target" +
                            "and baseline threshold values are found in their respective .csv files.");
            information_buffered_writer.newLine();
            information_buffered_writer.write("The data that this set was run over was known to contain an asteroid " +
                            "at position (" + x_pos + ", " + y_pos + ") in frame " + (timestamp - 1) + ". The " +
                    "asteroid was known to be between " + min_size + " and " + max_size + " pixels large. If the test " +
                    "classified the object at this position as mobile it was given a success rate equal to the inverse " +
                    "of the number of total objects classified as mobile. If the asteroid was classified as immobile, " +
                            "classified as noise, or not found the success rate was given as 0. These values can be found " +
                            "in success.csv. A formatted file containing the data from success.csv with " +
                            "two-way excel graphs is included as graphs.xlsx.");
            information_buffered_writer.close();

            Preprocessor preprocessor = new K2Preprocessor(new Fits(new File(filename)));
            float data[][][] = preprocessor.read();
            BinaryLocatorSupplier locator_supplier = new BinaryLocatorSupplier(data, binaryLocatorMassType, binaryLocatorMassTypeArgs);
            while(!locator_supplier.empty()) {
                Locator locator = locator_supplier.popLocator();
                locator.initialize();
                BrightBodyList[] bodies = locator.locate();
                BaselineFrameFilterSupplier filter_supplier = new BaselineFrameFilterSupplier(bodies, data, similarity_threshold, baselineMassType, baselineMassTypeArgs);
                while(!filter_supplier.empty()) {
                    MobilityFilter filter = filter_supplier.popFilter();
                    BrightBodyList[][] sorted_bodies = filter.filter();
                    immobile_buffered_writer.write(sorted_bodies[MobilityFilter.IBB_INDEX][timestamp - 1].size() + ",");
                    noise_buffered_writer.write(sorted_bodies[MobilityFilter.NOISE_INDEX][timestamp - 1].size() + ",");
                    mobile_buffered_writer.write(sorted_bodies[MobilityFilter.MBB_INDEX][timestamp - 1].size() + ",");
                    boolean contain = ContainsBody.containsBody(sorted_bodies[MobilityFilter.MBB_INDEX][timestamp - 1], 9, 20, 2, 20);
                    contains_buffered_writer.write(contain + ",");
                    if(contain) {
                        success_buffered_writer.write(1 / (float) sorted_bodies[MobilityFilter.MBB_INDEX][timestamp - 1].size() + ",");
                    }
                    else {
                        success_buffered_writer.write(0 + ",");
                    }
                }
                mobile_buffered_writer.newLine();
                contains_buffered_writer.newLine();
                immobile_buffered_writer.newLine();
                noise_buffered_writer.newLine();
                success_buffered_writer.newLine();
            }
            mobile_buffered_writer.close();
            contains_buffered_writer.close();
            immobile_buffered_writer.close();
            noise_buffered_writer.close();
            success_buffered_writer.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static final class BinaryLocatorSupplier {
        private final BinaryLocatorMassType mass_type_;
        private final float[][][] data_;
        private final float[] mass_args_;
        private final int count_;
        private int supplied_;

        public BinaryLocatorSupplier(float[][][] data, BinaryLocatorMassType mass_type, float ... mass_args) {
            supplied_ = 0;
            mass_type_ = mass_type;
            mass_args_ = mass_args;
            data_ = data;
            switch(mass_type) {
                case SINGLE:
                    count_= 1;
                    break;
                case GIVEN_RANGE:
                    count_ = (int) ((mass_args_[2] - mass_args_[1]) / mass_args_[0]) + 1;
                    break;
                case MEAN_SCALED_RANGE:
                case MEAN_SHIFTED_RANGE:
                    count_ = (int) mass_args_[2] - (int) mass_args_[1] + 1;
                    break;
                default:
                    count_ = 0;
                    System.err.println("Illegal mass type given to binary locator supplier: " + mass_type_);
                    System.exit(1);
            }
        }

        public boolean empty() {
            return supplied_ >= count_;
        }

        public Locator popLocator() {
            if (empty()) {
                System.err.println("No more locators to pop: " + supplied_);
                return null;
            }
            Locator locator;
            switch(mass_type_) {
                case SINGLE:
                    locator = new BinaryLocator(data_, BinaryLocatorThresholdType.ABSOLUTE, mass_args_[0]);
                    break;
                case GIVEN_RANGE:
                    locator = new BinaryLocator(data_, BinaryLocatorThresholdType.ABSOLUTE, mass_args_[1] + mass_args_[0] * supplied_);
                    break;
                case MEAN_SCALED_RANGE:
                    locator = new BinaryLocator(data_, BinaryLocatorThresholdType.MEAN_SCALED, 1.0f + mass_args_[0] * (mass_args_[1] + supplied_));
                    break;
                case MEAN_SHIFTED_RANGE:
                    locator = new BinaryLocator(data_, BinaryLocatorThresholdType.MEAN_SHIFTED, mass_args_[0] * (mass_args_[1] + supplied_));
                    break;
                default:
                    locator =  null;
            }
            supplied_++;
            return locator;
        }
    }

    public static final class BaselineFrameFilterSupplier {
        private final BaselineFrameMassType mass_type_;
        private final BrightBodyList[] bodies_;
        private final float[][][] data_;
        private final float similiarty_threshold_;
        private final float[] mass_args_;
        private final int count_;
        private int supplied_;

        public BaselineFrameFilterSupplier(BrightBodyList[] bodies, float[][][] data, float similarity_threshold, BaselineFrameMassType mass_type, float ... mass_args) {
            supplied_ = 0;
            bodies_ = bodies;
            data_ = data;
            similiarty_threshold_ = similarity_threshold;
            mass_type_ = mass_type;
            mass_args_ = mass_args;
            switch(mass_type) {
                case SINGLE:
                    count_= 1;
                    break;
                case GIVEN_RANGE:
                    count_ = (int) ((mass_args_[2] - mass_args_[1]) / mass_args_[0]) + 1;
                    break;
                case MEAN_SCALED_RANGE:
                case MEAN_SHIFTED_RANGE:
                    count_ = (int) mass_args_[2] - (int) mass_args_[1] + 1;
                    break;
                default:
                    count_ = 0;
                    System.err.println("Illegal mass type given to Baseline frame supplier: " + mass_type_);
                    System.exit(1);
            }
        }

        public boolean empty() {
            return supplied_ >= count_;
        }

        public MobilityFilter popFilter() {
            if (empty()) {
                System.err.println("No more filters to pop: " + supplied_);
                return null;
            }
            MobilityFilter filter;
            switch(mass_type_) {
                case SINGLE:
                    filter = new BaselineMobilityFilter(bodies_, data_, similiarty_threshold_, BaselineFrameGenerationMethod.BINARY_LOCATOR_ABSOLUTE, mass_args_[0]);
                    break;
                case GIVEN_RANGE:
                    filter = new BaselineMobilityFilter(bodies_, data_, similiarty_threshold_, BaselineFrameGenerationMethod.BINARY_LOCATOR_ABSOLUTE, mass_args_[1] + mass_args_[0] * supplied_);
                    break;
                case MEAN_SCALED_RANGE:
                    filter = new BaselineMobilityFilter(bodies_, data_, similiarty_threshold_, BaselineFrameGenerationMethod.BINARY_LOCATOR_MEAN_SCALED, 1.0f + mass_args_[0] * (mass_args_[1] + supplied_));
                    break;
                case MEAN_SHIFTED_RANGE:
                    filter = new BaselineMobilityFilter(bodies_, data_, similiarty_threshold_, BaselineFrameGenerationMethod.BINARY_LOCATOR_MEAN_SHIFTED, 1.0f + mass_args_[0] * (mass_args_[1] + supplied_));
                    break;
                default:
                    filter =  null;
            }
            supplied_++;
            return filter;
        }
    }
}