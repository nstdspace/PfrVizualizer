package de.nstdspace.pfrvizualizer.gui;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * this helper class considers only <i>rounded</i> colors. There are too many
 * different ones because of the image downscaling.
 */
public class RangeColorTooltipHelper {

    static Set<Color> raiseColors = new HashSet<>();
    static Set<Color> raiseOftenCallColors = new HashSet<>();
    static Set<Color> raiseSometimesCallColors = new HashSet<>();
    static Set<Color> callColors = new HashSet<>();
    static Set<Color> raiseSometimesFoldColors = new HashSet<>();
    static Set<Color> callFoldColors = new HashSet<>();
    static Set<Color> raiseCallFoldColors = new HashSet<>();
    static Set<Color> foldColors = new HashSet<>();

    static {
        raiseColors.add(new Color(250, 0, 0));
        raiseColors.add(new Color(240, 0, 0));
        raiseColors.add(new Color(255, 0, 0));

        raiseOftenCallColors.add(new Color(250, 130, 0));
        raiseOftenCallColors.add(new Color(240, 120, 0));
        raiseOftenCallColors.add(new Color(250, 120, 0));

        raiseSometimesCallColors.add(new Color(240, 240, 0));
        raiseSometimesCallColors.add(new Color(250, 250, 0));
        raiseSometimesCallColors.add(new Color(255, 255, 0));

        callColors.add(new Color(0, 240, 0));
        callColors.add(new Color(0, 230, 0));
        callColors.add(new Color(0, 250, 0));
        callColors.add(new Color(0, 255, 0));

        raiseSometimesFoldColors.add(new Color(250, 130, 250));
        raiseSometimesFoldColors.add(new Color(240, 120, 240));
        raiseSometimesFoldColors.add(new Color(250, 120, 250));

        callFoldColors.add(new Color(240, 240, 240));
        callFoldColors.add(new Color(250, 250, 250));

        raiseCallFoldColors.add(new Color(0, 250, 250));
        raiseCallFoldColors.add(new Color(0, 240, 240));

//        foldColors.add(new Color())
    }

    public static Color getRoundedColor(Color color){
        int redRounded = (int) (Math.round(color.getRed() * 0.1) * 10);
        int greenRounded = (int) (Math.round(color.getGreen() * 0.1) * 10);
        int blueRounded = (int) (Math.round(color.getBlue() * 0.1) * 10);
        redRounded = Math.min(255, redRounded);
        greenRounded = Math.min(255, greenRounded);
        blueRounded = Math.min(255, blueRounded);
        Color rounded = new Color(redRounded, greenRounded, blueRounded);
        return rounded;
    }


    public static String getTooltip(Color color){
        Color roundedColor = getRoundedColor(color);
        if(raiseColors.contains(roundedColor)){
            return "always raise";
        } else if(raiseOftenCallColors.contains(roundedColor)){
            return "often raise, otherwise call";
        } else if(raiseSometimesCallColors.contains(roundedColor)){
            return "sometimes raise, otherwise call";
        } else if(callColors.contains(roundedColor)){
            return "always call";
        } else if(raiseSometimesFoldColors.contains(roundedColor)){
            return "sometimes raise, otherwise fold";
        } else if(callFoldColors.contains(roundedColor)){
            return "call or fold";
        } else if(raiseCallFoldColors.contains(roundedColor)){
            return "raise, call or fold";
        } else if(foldColors.contains(roundedColor)){
            return "always fold";
        }
        return "";
    }
}
