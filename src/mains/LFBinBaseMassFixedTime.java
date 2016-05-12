package mains;

import brightbodies.BrightBodyList;
import filter.MobilityFilter;
import filter.BaselineFrameGenerationMethod;
import filter.BaselineMobilityFilter;
import locate.BinaryLocator;
import locate.BinaryLocatorThresholdType;
import locate.Locator;
import nom.tam.fits.Fits;
import preprocess.K2Preprocessor;
import preprocess.Preprocessor;

import java.io.File;

/**
 * A top level runnable type that will perform locate-track within a range of parameters on a given file and output
 * information for a fixed timestamp comparing the different parameters. This works by performing single locates, and
 * then all filters over that given location data.
 *
 * @author Jonathan Zwiebel
 * @version February 26th, 2016
 */
public final class LFBinBaseMassFixedTime {
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
        assert args[0].equals("LF_MASS_FIXED_TIME");

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

        BaselineFrameMassType refFrameMassType = null;
        BaselineFrameGenerationMethod refFrameMassTypeSingleType = null; // this one is only filled in the SINGLE case
        float[] refFrameMassTypeArgs = null;
        String refFrameMassTypeString = args[current_arg];
        current_arg++;
        switch(refFrameMassTypeString) {
            case "SINGLE":
                refFrameMassType = BaselineFrameMassType.SINGLE;
                String refFrameMassTypeSingleTypeString = args[current_arg];
                current_arg++;
                switch(refFrameMassTypeSingleTypeString) {
                    case "BINARY_LOCATOR_ABSOLUTE":
                        refFrameMassTypeSingleType = BaselineFrameGenerationMethod.BINARY_LOCATOR_ABSOLUTE;
                        refFrameMassTypeArgs = new float[]{Float.parseFloat(args[current_arg])};
                        current_arg++;
                        break;
                    case "BINARY_LOCATOR_MEAN":
                        refFrameMassTypeSingleType = BaselineFrameGenerationMethod.BINARY_LOCATOR_MEAN;
                        refFrameMassTypeArgs = new float[]{};
                        break;
                    case "BINARY_LOCATOR_MEAN_SHIFTED":
                        refFrameMassTypeSingleType = BaselineFrameGenerationMethod.BINARY_LOCATOR_MEAN_SHIFTED;
                        refFrameMassTypeArgs = new float[]{Float.parseFloat(args[current_arg])};
                        current_arg++;
                        break;
                    case "BINARY_LOCATOR_MEAN_SCALED":
                        refFrameMassTypeSingleType = BaselineFrameGenerationMethod.BINARY_LOCATOR_MEAN_SCALED;
                        refFrameMassTypeArgs = new float[]{Float.parseFloat(args[current_arg])};
                        current_arg++;
                        break;
                    default:
                        System.err.println("Illegal binaryLocatorMassTypeSingleType: " + refFrameMassTypeSingleTypeString);
                        System.exit(1);
                }
                break;
            case "GIVEN_RANGE":
                refFrameMassType = BaselineFrameMassType.GIVEN_RANGE;
                refFrameMassTypeArgs = new float[]{Float.parseFloat(args[current_arg]), Float.parseFloat(args[current_arg + 1]), Float.parseFloat(args[current_arg + 2])};
                current_arg += 3;
                break;
            case "MEAN_SHIFTED_RANGE":
                refFrameMassType = BaselineFrameMassType.MEAN_SHIFTED_RANGE;
                refFrameMassTypeArgs = new float[]{Float.parseFloat(args[current_arg]), Float.parseFloat(args[current_arg + 1]), Float.parseFloat(args[current_arg + 2])};
                current_arg += 3;
                break;
            case "MEAN_SCALED_RANGE":
                refFrameMassType = BaselineFrameMassType.MEAN_SCALED_RANGE;
                refFrameMassTypeArgs = new float[]{Float.parseFloat(args[current_arg]), Float.parseFloat(args[current_arg + 1]), Float.parseFloat(args[current_arg + 2])};
                current_arg += 3;
                break;
            default:
                System.err.println("Illegal RefFrameMassType: " + binaryLocatorMassTypeString);
                System.exit(1);
        }

        int timestamp = Integer.parseInt(args[current_arg]);
        current_arg++;

        try {
            Preprocessor preprocessor = new K2Preprocessor(new Fits(new File(filename)));
            float data[][][] = preprocessor.read();
            BinaryLocatorSupplier loc_supplier = new BinaryLocatorSupplier(data, binaryLocatorMassType, binaryLocatorMassTypeArgs);
            while(!loc_supplier.empty()) {
                Locator locator = loc_supplier.popLocator();
                locator.initialize();
                BrightBodyList[] bodies = locator.locate();
            }
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
            System.out.println("BLS Construction Started");
            supplied_ = 0;
            mass_type_ = mass_type;
            mass_args_ = mass_args;
            data_ = data;
            switch(mass_type) {
                case SINGLE:
                    System.out.println("BLS Single Case");
                    count_= 1;
                    break;
                case GIVEN_RANGE:
                    System.out.println("BLS Given Range Case");
                    count_ = (int) ((mass_args_[2] - mass_args_[1]) / mass_args_[0]) + 1;
                    break;
                case MEAN_SCALED_RANGE:
                    System.out.println("BLS Scaled Range Not Shifted Range Case");
                case MEAN_SHIFTED_RANGE:
                    System.out.println("BLS Shifted Range Case");
                    count_ = (int) mass_args_[2] - (int) mass_args_[1] + 1;
                    break;
                default:
                    count_ = 0;
                    System.err.println("Illegal mass type given to binary locator supplier: " + mass_type_);
                    System.exit(1);
            }
        }

        public boolean empty() {
            System.out.println("Empty polling with supplied_ of " + supplied_ + " and count_ of " + count_);
            return supplied_ >= count_;
        }

        public Locator popLocator() {
            System.out.println("Popping locator");
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
                case MEAN_SHIFTED_RANGE:
                    locator = new BinaryLocator(data_, BinaryLocatorThresholdType.MEAN_SCALED, 1.0f + mass_args_[0] * (mass_args_[1] + supplied_));
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

        public BaselineFrameFilterSupplier(BrightBodyList[] bodies, float[][][] data, float similiarty_threshold, BaselineFrameMassType mass_type, float ... mass_args) {
            supplied_ = 0;
            bodies_ = bodies;
            data_ = data;
            similiarty_threshold_ = similiarty_threshold;
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