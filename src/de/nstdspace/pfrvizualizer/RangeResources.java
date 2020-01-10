package de.nstdspace.pfrvizualizer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;

import static de.nstdspace.pfrvizualizer.gui.Gui.RANGE_PREVIEW_SIZE;

public class RangeResources {

    public static final Image EMPTY_RANGE = loadImage("", "empty_range", "png");

    // for debugging purposes only
    public static HashMap<Image, String> imagesToNames;
    public static HashMap<String, Image> namesToImages;

    static {
        try {
            loadDirectory("/RFI", "png");
            loadDirectory("/VsOpen/VsOpenLimp", "png");
            loadDirectory("/VsOpen/Vs2BBOpen", "png");
            loadDirectory("/VsOpen/Vs2.5BBOpen", "png");
            loadDirectory("/VsOpen/Vs3BBOpen", "png");
            loadDirectory("/VsOpen/Vs4BBOpen", "png");
            System.out.println(namesToImages);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // filesEnding currently there for splitting off files "actual name", works only if all files have equal ending
    private static void loadDirectory(String directory, String filesEnding) throws IOException {
        InputStream stream = Main.class.getResourceAsStream(directory);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line;
        while((line = reader.readLine()) != null){
            loadImage(directory, line.split("\\." + filesEnding)[0], filesEnding);
        }
    }

    private static Image loadImage(String directory, String fileName, String fileEnding){
        if(imagesToNames == null){
            imagesToNames = new HashMap<>();
            namesToImages = new HashMap<>();
        }
        try {
            Image image = ImageIO.read(Main.class.getResourceAsStream(directory + "/" + fileName + "." + fileEnding))
                    .getScaledInstance(RANGE_PREVIEW_SIZE, RANGE_PREVIEW_SIZE, BufferedImage.SCALE_SMOOTH);
            imagesToNames.put(image, fileName);
            namesToImages.put(fileName, image);
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
