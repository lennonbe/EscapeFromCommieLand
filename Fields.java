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
    protected static boolean isWinter = false;
    
    //Will now call the update method every 2000 seconds allowing us to grow the plants
    protected int growthTime = 0; //in this case growth time is 10000 which means the update method will get called every 2000 seconds
    protected static Clock seasonsSwitchClock;
    protected static FieldClock [] clockArr = new FieldClock[4];
    protected boolean growing = false;
    protected boolean readyToCollect = false;
    protected String vegType = "";
    protected int growthStatus = -1;
    protected ResourceMenu resourceMenu;

    /**
     * Constructor for Fields.
     * @param size the size of the field
     */
    public Fields(Vector2f size, ResourceMenu input)
    {
        super(size);
        resourceMenu = input;
        this.loadPathToRectangle("BoringGame", "DirtWet.png");

        for(int i = 0; i < 4; i++)
        {
            if(i == 0)
            {
                clockArr[i] = new FieldClock(250);
            }
            else if(i == 1)
            {
                clockArr[i] = new FieldClock(1000);
            }
            else if(i == 2)
            {
                clockArr[i] = new FieldClock(2500);
            }
            else if(i == 3)
            {
                clockArr[i] = new FieldClock(5000);
            }
        }
    }

    public String getVegType()
    {
        return vegType;
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
        if(i == 0 && resourceMenu.getIndexVal(i) > 0)
        {
            vegType = "Chilli";
            this.clockArr[i].addObserver(this);
            resourceMenu.decrement(i);
        }
        else if(i == 1 && resourceMenu.getIndexVal(i) > 0)
        {
            vegType = "Carrot";
            this.clockArr[i].addObserver(this);
            resourceMenu.decrement(i);
        }
        else if(i == 2 && resourceMenu.getIndexVal(i) > 0)
        {
            vegType = "Hemp";
            this.clockArr[i].addObserver(this);
            resourceMenu.decrement(i);
        }
        else if(i == 3 && resourceMenu.getIndexVal(i) > 0)
        {
            vegType = "Cauliflower";
            this.clockArr[i].addObserver(this);
            resourceMenu.decrement(i);    
        }

        if(vegType != "" && growing == false)
        {
            this.growthStatus = 1;
            this.growing = true;
        } 
    }

    public void setVegType(String str)
    {
        vegType = str;
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
            this.loadPathToRectangle("BoringGame/Sprites/FruitVeg/" + this.vegType, this.vegType + growthStatus + ".png");
    
            if(growthStatus < 7)
            {
                this.growthStatus++;
            }
            else
            {
                this.growing = false;
                this.readyToCollect = true;
                this.growthStatus = -1;
                clock.deleteObservers();
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


    /**
     * Sets the growing variable, which decided if the plant is animated to grow every x seconds.
     * @param bool
     */
    public void setGrowing(boolean bool)
    {
        growing = bool;
    }
}