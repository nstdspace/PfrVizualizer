package de.nstdspace.pfrvizualizer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import static de.nstdspace.pfrvizualizer.gui.Gui.RANGE_PREVIEW_SIZE;

public class RangeResources {

    public static final Image EMPTY_RANGE = loadImage("/empty_range.png");

    public static final Image BU_RFI_RANGE = loadImage("/RFI/BU-RFI2bb.png");
    public static final Image SB_RFI_RANGE = loadImage("/RFI/SB-RFI3bb.png");
    public static final Image LJ_RFI_RANGE = loadImage("/RFI/LJ-RFI2bb.png");
    public static final Image HJ_RFI_RANGE = loadImage("/RFI/HJ-RFI2bb.png");
    public static final Image CO_RFI_RANGE = loadImage("/RFI/CO-RFI2bb.png");

    // for debugging purposes only
    public static HashMap<Image, String> imageNames;

    private static Image loadImage(String fileName){
        if(imageNames == null){
            imageNames = new HashMap<>();
        }
        try {
            Image image = ImageIO.read(Main.class.getResourceAsStream(fileName))
                    .getScaledInstance(RANGE_PREVIEW_SIZE, RANGE_PREVIEW_SIZE, BufferedImage.SCALE_SMOOTH);
            imageNames.put(image, fileName);
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
