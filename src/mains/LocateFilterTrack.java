package mains;


/**
 * @author by Jonathan Zwiebel
 * @version February 26 2016
 */
public class LocateFilterTrack {
    public static void main(String[] args) {
        switch(args[0]) {
            case "HELP":
                Help.run(args);
                break;
            case "GENERATE_ANIMATION":
                GenerateAnimation.run(args);
                break;
            case "LF_SINGLE_FIXED_TIME":
                LFSingleFixedTime.run(args);
                break;
            case "LF_MASS_FIXED_TIME":
                LFMassFixedTime.run(args);
                break;
            default:
                System.err.println("Wrong run type");
                System.exit(1);
        }
    }
}
