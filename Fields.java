package BoringGame;

import java.util.Observable;
import java.util.Observer;
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


public class Fields extends RectangleShape
{
    public final int[] growthPhases = new int[]{0,1,2,3,4,5,6};
    protected static boolean clickFlag = false;
    protected static Fields selectedField = null;

    public enum VegType 
    {
        Carrots,
        Cauliflower,
        Tomatos,
        Sweetcorn
    }

    private VegType currentVegType = null;
    private int growthStatus = -1;
    private Texture fieldTexture = new Texture();

    /**
     * Constructor for Fields.
     * @param size the size of the field
     */
    public Fields(Vector2f size)
    {
        super(size);

        this.loadPathToRectangle("BoringGame", "DirtWet.png");
    }

    /**
     * Set the vegType based on the files.
     * @param type type of veg
     */
    public void setVegType(VegType type)
    {
        currentVegType = type;
        growthStatus = 0;

        if(currentVegType != null)
        {
            this.loadPathToRectangle("BoringGame/Sprites/FruitVeg/" + currentVegType.name(), currentVegType.name() + "1");
        } 
    }

    /**
     * 
     * @param newStatus
     */
    public void setStatus(int newStatus)
    {
        if(newStatus > 6 || newStatus < 0)
        {
            System.out.println("ERROR - INVALID newStatus variable");
        }
        else
        {
            if(currentVegType != null && growthStatus != -1)
            {
                this.loadPathToRectangle("BoringGame/Sprites/FruitVeg/" + currentVegType.name(), currentVegType.name() + growthStatus);
            }            
        }
    }

    /**
     * Method which allows to loadn in .PNG files into sprites
     * @param directory the directory of the file you wish to load
     * @param file the file you wish to load
     * @param rectangle the rectangle you wish to have this file drawn on
     * @param texture a needed texture for the rectangle, making it drawable
     */
    public void loadPathToRectangle(String directory, String file)
    {
        Path path = FileSystems.getDefault().getPath(directory, file);
        
        try {
            BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);

        } catch (Exception e) {}

        try {
            fieldTexture.loadFromFile(path);
        } 
        catch (Exception e) {}

        this.setTexture(fieldTexture);
    }

    /**
     * Checks if a square/rectangle has been clicked.
     * @param rectangle the rectangle in question
     * @return a boolean which is true if its clicked and false otherwise
     */
    public boolean isClicked(Window window)
    {
        boolean flag = false;

        if(Mouse.isButtonPressed(Mouse.Button.LEFT))
        {
            if(Mouse.getPosition(window).x >= this.getPosition().x && Mouse.getPosition(window).x <= this.getPosition().x + this.getSize().x && Mouse.getPosition(window).y >= this.getPosition().y && Mouse.getPosition(window).y <= this.getPosition().y + this.getSize().y)
            {
                if(selectedField == this && clickFlag == true)
                {
                    selectedField = null;
                    clickFlag = false;
                }
                else
                {
                    selectedField = this;
                    clickFlag = true;
                }

                flag = true;
            }
            else
            {
                flag = false;
            }
        }
        else
        {
            flag = false;
        }

        return flag;
    }
}