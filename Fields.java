package BoringGame;

import java.util.Observable;
import java.util.Observer;
import org.jsfml.window.*;
import org.jsfml.window.event.*;

import BoringGame.Clock;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.concurrent.TimeUnit;
import java.util.Observable;
import java.util.Observer;



public class Fields extends RectangleShape implements Observer
{
    private Texture fieldTexture = new Texture();
    protected static boolean clickFlag = false;
    protected static Fields selectedField = null;
    
    //Will now call the update method every 2000 seconds allowing us to grow the plants
    protected int growthTime = 0; //in this case growth time is 10000 which means the update method will get called every 2000 seconds
    private Clock clock = Clock.getInstance(2000);;
    protected boolean growing = false;
    protected boolean readyToCollect = false;
    private String vegType = "";
    private int growthStatus = -1;

    /**
     * Constructor for Fields.
     * @param size the size of the field
     */
    public Fields(Vector2f size)
    {
        super(size);
        this.loadPathToRectangle("BoringGame", "DirtWet.png");
    }

    public String getVegType()
    {
        return vegType;
    }

    public void setVegType(String input)
    {
        vegType = input;
    }
    
    public Fields getSelectedField()
    {
        return selectedField;
    }
    
    public void setSelectedField(Fields input)
    {
        selectedField = input;
    }
    
    /**
     * Set the vegType based on the files.
     * @param type type of veg
     */
    public void setVegType(int i)
    {
        if(i == 0)
        {
            vegType = "Chilli";
            if(clock != null)
            {
                System.out.println("here4");
                //this.clock = Clock.getInstance(2000);
                this.clock.setClockDelay(2000);
            }
        }
        else if(i == 1)
        {
            vegType = "Carrot";
            if(clock != null)
            {
                System.out.println("here2");
                //this.clock = Clock.getInstance(5000);
                this.clock.setClockDelay(5000);
            }
        }
        else if(i == 2)
        {
            vegType = "Hemp";
            if(clock != null)
            {
                System.out.println("here3");
                //this.clock = Clock.getInstance(10000);
                this.clock.setClockDelay(10000);
            }
        }
        else if(i == 3)
        {
            vegType = "Cauliflower";
            if(clock != null)
            {
                System.out.println("here4");
                //this.clock = Clock.getInstance(100);
                this.clock.setClockDelay(100);
            }
        }

        clock.addObserver(this);

        if(vegType != "" && growing == false)
        {
            this.growthStatus = 1;
            this.growing = true;
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

    /**
     * This is the method which will be called to update the field every growth cycle
     * @param clock
     * @param o
     */
    public void update(Observable clock, Object o)
    {
        if(this.growing == true)
        {
            System.out.println("hi, im happening" + growthStatus + this.selectedField);
            this.loadPathToRectangle("BoringGame/Sprites/FruitVeg/" + this.vegType, this.vegType + growthStatus + ".png");
    
            if(growthStatus < 7)
            {
                growthStatus++;
            }
            else
            {
                //growthStatus = 1;
                this.growing = false;
                this.readyToCollect = true;
                this.clock = null;
            }

        }
    }

    /**
     * Sets the growing variable, which decided if the plant is animated to grow every x seconds.
     * @param bool
     */
    public void setGrowing(boolean bool)
    {
        growing = bool;
    }
}