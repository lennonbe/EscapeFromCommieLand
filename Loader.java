package BoringGame;

import org.jsfml.graphics.*;
import jdk.jshell.spi.ExecutionControl.ExecutionControlException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.io.*;

/**
 * Abstract class used for file manipulation.
 */
public abstract class Loader {
    
    /**
     * Allows user to load an image innto a specific rectangle
     * @param directory directory in which the file can be found
     * @param file file to be used
     * @param rectangle rectangle to add file to
     * @param texture texture to allow drawing on that rectangle
     */
    public static void loadPathToRectangle(String directory, String file, RectangleShape rectangle, Texture texture) {
        Path path = FileSystems.getDefault().getPath(directory, file);
        
        try {
            BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("Error while loading the buffer");
            e.printStackTrace();
        }

        try {
            texture.loadFromFile(path);
        } catch (Exception e) {
            System.out.println("Error while loading the texture");
            e.printStackTrace();
        }

        rectangle.setTexture(texture);
    }

    /**
     * Allows user to load an image innto a specific circle
     * @param directory directory in which the file can be found
     * @param file file to be used
     * @param circle circle to add file to
     * @param texture texture to allow drawing on that rectangle
     */
    public static void loadPathToCircle(String directory, String file, CircleShape circle, Texture texture) {
        Path path = FileSystems.getDefault().getPath(directory, file);
        
        try {
            BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("Error while loading the buffer");
            e.printStackTrace();
        }

        try {
            texture.loadFromFile(path);
        } catch (Exception e) {
            System.out.println("Error while loading the texture");
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    /**
     * Function clears the content of the specified file.
     * @param fileName String - Path of the file to be cleared.
     */
    public static void clearFile(String fileName) {
        try {
            Path filePath = Path.of(fileName);
            Files.writeString(filePath, "");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Function used to add to a file.
     * @param text String - The text that is being added to the file.
     * @param fileName String - The path to the file that is being added to.
     */
    public static void addToFile(String text, String fileName) {
        try {
            Path filePath = Path.of(fileName);
            String currentString = Files.readString(filePath);
            Files.writeString(filePath, currentString + text);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Function used to read from a file.
     * @param fileName - Name of the file that is to be read
     * @return String - Contents of the file
     */
    public static String readFile(String fileName) {
        try {
            Path filePath = Path.of(fileName);
            String currentString = Files.readString(filePath);

            return currentString; 
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
