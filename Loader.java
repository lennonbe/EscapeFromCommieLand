package BoringGame;

import org.jsfml.graphics.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.io.*;

public abstract class Loader {
    
    public static void loadPathToRectangle(String directory, String file, RectangleShape rectangle, Texture texture) {
        Path path = FileSystems.getDefault().getPath(directory, file);
        
        try {
            BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("Error while loading the buffer");
        }

        try {
            texture.loadFromFile(path);
        } catch (Exception e) {
            System.out.println("Error while loading the texture");
        }

        rectangle.setTexture(texture);
    }

    public static void loadPathToCircle(String directory, String file, CircleShape circle, Texture texture) {
        Path path = FileSystems.getDefault().getPath(directory, file);
        
        try {
            BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("Error while loading the buffer");
        }

        try {
            texture.loadFromFile(path);
        } catch (Exception e) {
            System.out.println("Error while loading the texture");
        }

        circle.setTexture(texture);
    }

    /**
     * Function used to load font from files
     * @param font Font variable in which the font will be stored
     * @param filePath path to the font file 
     */
    public static void loadPathToFont(Font font, String filePath) {
        try {
            font.loadFromFile(Paths.get(filePath));
        } catch(Exception e) { 
            System.out.println("Error loading font"); 
        }
    }
}
