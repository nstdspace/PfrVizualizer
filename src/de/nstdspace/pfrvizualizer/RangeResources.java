package de.nstdspace.pfrvizualizer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.Collections;
import java.util.HashMap;

import static de.nstdspace.pfrvizualizer.gui.Gui.RANGE_PREVIEW_SIZE;

public class RangeResources {

    private static HashMap<String, InputStream> imageNamesToResourceStreams;
    private static HashMap<Image, String> imagesToNames;
    private static HashMap<String, Image> namesToImages;

    public static void load(){
        imageNamesToResourceStreams = new HashMap<>();
        imagesToNames = new HashMap<>();
        namesToImages = new HashMap<>();
        try {
            loadResourceStream("", "empty_range", "png");
            loadDirectoryStreams("/RFI", "png");
            loadDirectoryStreams("/VsOpen/VsOpenLimp", "png");
            loadDirectoryStreams("/VsOpen/Vs2BBOpen", "png");
            loadDirectoryStreams("/VsOpen/Vs2.5BBOpen", "png");
            loadDirectoryStreams("/VsOpen/Vs3BBOpen", "png");
            loadDirectoryStreams("/VsOpen/Vs4BBOpen", "png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!Main.ondemandMode){
            for(String key : imageNamesToResourceStreams.keySet()){
                loadAndMapImage(key);
            }
        }
    }

    private static Path getFolderPath(String folder) throws URISyntaxException, IOException {
        URI uri = Main.class.getClassLoader().getResource(folder).toURI();
        if(uri.getScheme().equals("jar")){
            FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap(), null);
            return fileSystem.getPath(folder);
        } else {
            return Paths.get(uri);
        }
    }

    public static String getImageName(Image currentRangePreviewImage) {
        return imagesToNames.get(currentRangePreviewImage);
    }

    public static Image getImage(String imageName){
        if(!namesToImages.containsKey(imageName)){
            loadAndMapImage(imageName);
        }
        return namesToImages.get(imageName);
    }

    private static Image loadAndMapImage(String imageName) {
        if(!imageNamesToResourceStreams.containsKey(imageName)){
            throw new IllegalStateException("Image with name \"" + imageName + "\" not mapped!");
        }
        InputStream stream = imageNamesToResourceStreams.get(imageName);
        Image image = loadImage(stream);
        imagesToNames.put(image, imageName);
        namesToImages.put(imageName, image);
        return image;
    }

    // filesEnding currently there for splitting off files "actual name", works only if all files have equal ending
    private static void loadDirectoryStreams(String directory, String filesEnding) throws IOException {
        InputStream stream = Main.class.getResourceAsStream(directory);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line;
        while((line = reader.readLine()) != null){
            String actualFileName = line.split("\\." + filesEnding)[0];
            loadResourceStream(directory, actualFileName, filesEnding);
        }
    }

    private static void loadResourceStream(String directory, String actualFileName, String fileEnding) {
        imageNamesToResourceStreams.put(actualFileName,
                Main.class.getResourceAsStream(getResourceString(directory, actualFileName, fileEnding)));
    }

    private static String getResourceString(String directory, String fileName, String fileEnding){
        return directory + "/" + fileName + "." + fileEnding;
    }

    private static Image loadImage(InputStream inputStream){
        try {
            Image image = ImageIO.read(inputStream).getScaledInstance(RANGE_PREVIEW_SIZE, RANGE_PREVIEW_SIZE, BufferedImage.SCALE_SMOOTH);
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Image getEmptyRange() {
        return getImage("empty_range");
    }
}
