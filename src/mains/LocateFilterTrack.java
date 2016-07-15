package mains;

import java.awt.*;

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
 * run with the first parameter being the run type and subsequent parameters being parameters for the runnable type.
 *
 * @author Jonathan Zwiebel
 * @version 8 July 2016
 */
public final class LocateFilterTrack {
    /**
     * Project main method
     * @param args main method arguments
     */
    public static void main(String[] args) {
        switch(args[0]) {
            case "HELP":
                Help.run(args);
                break;
            case "GENERATE_ANIMATION":
                GenerateAnimation.run(args);
                break;
            case "LF_BIN_BASE_SINGLE_FIXED_TIME":
                LFBinBaseSingleFixedTime.run(args);
                break;
            case "LF_BINARY_BASELINE_MASS":
                LFBinaryBaselineMass.run(args);
                break;
            case "LFT_SINGLE_BASIC":
                LFTSingleBasic.run(args);
                break;
            case "PIXEL_ANALYSIS":
                PixelAnalysis.run(args);
                break;
            case "IMAGE_ANALYSIS":
                ImageAnalysis.run(args);
                break;
            case "PIXEL_DIFFERENCE_ANALYSIS":
                PixelDifferenceAnalysis.run(args);
                break;
            default:
                System.err.println("Wrong run type: " + args[0]);
                System.exit(1);
        }
    }
}
