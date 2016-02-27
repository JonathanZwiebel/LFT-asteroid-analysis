package mains;


/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Jonathan Zwiebel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *
 * The highest level class that will run the runnable type classes. To use LFT-asteroid-tracking, this class must be
 * run with the first parameter being the run type and subsequent parameters being parameters for the runnable typeit l
 *
 * @author by Jonathan Zwiebel
 * @version February 26 2016 - t0.1.0 Testing initial suite addition
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
