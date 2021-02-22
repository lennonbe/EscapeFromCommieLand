package BoringGame;

import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.concurrent.TimeUnit;


public class Farmer extends Sprite
{
    private Texture farmerTexture = new Texture();
    private Vector2f scale = new Vector2f((float)1.5, (float)1.5);
    private String currentSprite;

    /**
     * Constructor for the Farmer object.
     */
    public Farmer()
    {
        super();

        this.setPosition(200, 200);
        this.setScale(scale);
        this.scale(scale);
        this.setSprite("BoringGame/AllResources", "Man_Neutral.png");
    }

    /**
     * Method which allows to set the farmer sprite image.
     * @param directory directory in which the image is stored
     * @param file file of the image
     */
    public void setSprite(String directory, String file)
    {
        Path path = FileSystems.getDefault().getPath(directory, file);
        currentSprite = file;
        
        try {
            BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);

        } catch (Exception e) {}

        try {
            farmerTexture.loadFromFile(path);
        } 
        catch (Exception e) {
            System.out.println("Error occured while loading image");
        }

        this.setTexture(farmerTexture);
    }

    /**
     * Allows to get the current Sprite image being used. This value is returned as a string.
     * @return current Sprite image being used
     */
    public String getCurrentSprite()
    {
        return currentSprite;
    }
}

