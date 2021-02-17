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


/**
 * Class for fields used in this game. The field is one of the most important objects of this game due to the game being resource collection based.
 */
public class Fields extends RectangleShape implements Observer
{
    private Texture fieldTexture = new Texture();
    protected static boolean clickFlag = false;
    protected static Fields selectedField = null;
    protected static boolean isWinter = false;
    
    //Will now call the update method every 2000 seconds allowing us to grow the plants
    protected int growthTime = 0; //in this case growth time is 10000 which means the update method will get called every 2000 seconds
    protected static Clock seasonsSwitchClock;
    protected Clock [] clockArr = new Clock[4];
    protected boolean growing = false;
    protected boolean readyToCollect = false;
    protected boolean unlocked = true;
    protected String vegType = "";
    protected int growthStatus = -1;
    protected ResourceMenu resourceMenu;

    //The following values are in regard to the different vegetables growth types. This time is the cycle time and is multiplied by 7 to get a total time of growth.
    private int chilliGrowthCycleTime = 1000;
    private int carrotGrowthCycleTime = 2000;
    private int hempGrowthCycleTime = 2500;
    private int cauliflowerGrowthCycleTime = 5000;

    /**
     * Constructor method for the fields.
     * @param size size of field
     * @param input resource menu used as input when constructing the field.
     */
    public Fields(Vector2f size, ResourceMenu input)
    {
        super(size);
        resourceMenu = input;
        this.loadPathToRectangle("BoringGame/AllResources", "WetDirt.png");

        for(int i = 0; i < 4; i++)
        {
            if(i == 0)
            {
                clockArr[i] = new Clock(chilliGrowthCycleTime);
            }
            else if(i == 1)
            {
                clockArr[i] = new Clock(carrotGrowthCycleTime);
            }
            else if(i == 2)
            {
                clockArr[i] = new Clock(hempGrowthCycleTime);
            }
            else if(i == 3)
            {
                clockArr[i] = new Clock(cauliflowerGrowthCycleTime);
            }
        }
    }

    /**
     * Getter method for the veg type
     * @return string which represents the vegType
     */
    public String getVegType()
    {
        return vegType;
    }
    
    /**
     * Getter for the selected field.
     * @return currently selected field, a static variable
     */
    public Fields getSelectedField()
    {
        return selectedField;
    }
    
    /**
     * Setter method for the selectedField, setting this variable
     * @param input value selectedField will be set to
     */
    public void setSelectedField(Fields input)
    {
        selectedField = input;
    }
    
    /**
     * Setter method for vegType.
     * @param i index value of vegType
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

    /**
     * Different setter method for vegType. Directly sets the vegType as a string.
     * @param str str which represents and will be set as vegType
     */
    public void setVegType(String str)
    {
        vegType = str;
    }
    
    /**
     * This is the method which will be called to update the field every growth cycle.
     * @param clock
     * @param o
     */
    public void update(Observable clock, Object o)
    {
        if(this.growing == true)
        {
            this.loadPathToRectangle("BoringGame/AllResources/FruitVeg/" + this.vegType, this.vegType + growthStatus + ".png");
    
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
     * Method used to load images to rectangles.
     * @param directory directory where you can find the image you wish to set.
     * @param file file regarding said image.
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
     * Checks if the mouse left button is clicked in the game window.
     * @param window current window
     * @return boolean representing if it is or not clicked (true if it is clicked, false otherwise)
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