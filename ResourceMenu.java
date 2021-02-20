package BoringGame;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.Observable;
import java.util.Observer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.lang.model.util.ElementScanner6;
import javax.swing.Timer;

/**
 * Class representing the resource menu in the top left of the screen.
 * This class implements the functionality for selecting resources,
 * and shows changes in the resources when new ones are aquired or lost.
 */
public class ResourceMenu extends RectangleShape implements Observer
{
    
    //Inherit attributes of the ResoureMenu are final
    private final static int width = 768;
    private final static int height = 90;
    private final int numberOfIcons = 5;
    private final int iconWidth = 50;
    private final int iconHeight = 50;
    private final int gap = 42;

    
    //More attributes about the resource menu
    private Font font;
    protected static int selectedIndex = -1;
    private Vector2f seedIconSize;
    private RectangleShape resuourceMenuRectange;
    private Texture resuourceMenuTexture;
    protected RectangleShape[] seedIcons;
    protected Texture[] seedIconsTexture;
    private int[] resourceCounter;
    private Text[] counterText;
    private boolean isSelected = false;
    private Texture resourceMenuTexture;
    private int textYPosition = 60;
    private Texture seasonIconTexture = new Texture();
    protected RectangleShape seasonIcon;

    //Attributes regarding the veg and it being or not locked
    protected boolean carrotLocked = true, hempLocked = true, cauliflowerLocked = true;

    //Clock for passive income every 30 seconds
    private Clock passiveIncomeClock = new Clock(5000);
    protected boolean autoIncrement = false;
    protected int autoIncrementVal = 1;

    /**
     * Initializes the resource menu attributes
     */
    public ResourceMenu(BuyMenu buyMenu) 
    {
        //Sets up this rectangle, so that it can be drawn with the right parameter
        super(new Vector2f(width, height));
        passiveIncomeClock.addObserver(this);

        this.setPosition(0, 0);
        this.setFillColor(new Color(128,128,128));

        resourceMenuTexture = new Texture();
        Loader.loadPathToRectangle("BoringGame/AllResources", "TopMenuNew.png", this, resourceMenuTexture);

        seasonIcon = new RectangleShape(new Vector2f(105, 62));
        seasonIcon.setPosition(495, 14);
        Loader.loadPathToRectangle("BoringGame/AllResources", "Season2.png", seasonIcon, seasonIconTexture);

        //Loads in the font
        font = new Font();
        Loader.loadPathToFont(font, "BoringGame/Russian.ttf");
        
        //Sets the size of each icon
        seedIconSize = new Vector2f(50, 50);
        
        //Initially no resource is selected, therefore index set to an invalid value
        selectedIndex = -1;
        
        //Initializes the arrays for the menu 
        seedIcons = new RectangleShape[numberOfIcons];
        seedIconsTexture = new Texture[numberOfIcons];
        counterText = new Text[numberOfIcons];
        
        /**
         * This for loop positions the icons equidistant from eachother,
         * with the 'display' (black rectangles) inbetween.
         * Then loads in the icons and sets their attributes.
         */
        int iconXPosition = gap - 10;  
        for(int i = 0; i < numberOfIcons; i++) 
        {
            seedIcons[i] = new RectangleShape();
            seedIconsTexture[i] = new Texture();
            
            seedIcons[i].setPosition(new Vector2f(iconXPosition, 20));
            seedIcons[i].setSize(seedIconSize);
            
            if(i == 4) {//initial coin no. value is 5
                counterText[i] = new Text("3", font, 15);
            } else {
                counterText[i] = new Text("0", font, 15);
            }
            
            
            if(i == 0  || i == 4) {
                Loader.loadPathToRectangle("BoringGame/AllResources/Closeup Vegetables", "resource" + i + ".png", seedIcons[i], seedIconsTexture[i]);                
            }
            else {
                Loader.loadPathToRectangle("BoringGame/AllResources/SilhouetteVeg", "resource" + i + "_sil.png", seedIcons[i], seedIconsTexture[i]);
            }
            
            if(i == 0) {
                counterText[i].setPosition(iconXPosition + iconWidth - 10, textYPosition);
                iconXPosition += (iconWidth + gap + 2);
            } else if(i == 1) {
                counterText[i].setPosition(iconXPosition + iconWidth - 5, textYPosition);
                iconXPosition += (iconWidth + gap - 2);
            } else if(i == 2) {
                counterText[i].setPosition(iconXPosition + iconWidth - 10, textYPosition);
                iconXPosition += (iconWidth + gap - 10);
            } else {
                counterText[i].setPosition(iconXPosition + iconWidth - 10, textYPosition);
                iconXPosition += (iconWidth + gap);
            }
            
        }

    }

    /**
     * The resource menu contains the icon that represents the current season.
     * This function is used to change the seasons icon
     * @param i
     */
    public void changeSeasonIcon(int i)
    {
        if(i == 1)
        {
            Loader.loadPathToRectangle("BoringGame/AllResources", "Season1.png", seasonIcon, seasonIconTexture);
        }
        else if(i == 2)
        {
            Loader.loadPathToRectangle("BoringGame/AllResources", "Season2.png", seasonIcon, seasonIconTexture);
        }
    }
    
    /**
     * This function adds all the JSFML Rectangles used to draw the resource menu into one array and returns it.
     * This is useful in the class that implements this menu, so that the rectangles can be drawn onto the screen
     * 
     * @return The array of rectangles that make up the menu
     */
    public RectangleShape[] getRectangleArray() {
        return seedIcons;
    }

    /**
     * This function adds all the JSFML Rectangles used to draw the resource menu into one array and returns it.
     * This is useful in the class that implements this menu, so that the rectangles can be drawn onto the screen
     * 
     * @return The array of rectangles that make up the menu
     */
    public RectangleShape[] getRectangleArray(int i) 
    {
        return seedIcons;
    }

    /**
     * This function returns the array of JSFML Text, these are used to display the number of resources in the menu.
     * @return Array of Text
     */
    public Text[] getCounter() 
    {
        return counterText;
    }
    
    
    /**
     * Function called everytime a resource was bought in the shop.
     * Used to increment the text counter.
     * @param index The index of the item bought
     */
    public void increment(int index) 
    {
        int currentVal = Integer.parseInt(counterText[index].getString());
        counterText[index].setString(String.valueOf(currentVal + 1));
    }

    /**
     * Function called everytime a resource is used to plant.
     * Used to decrement the text counter.
     * @param index The index of the item used
     */
    public void decrement(int index)
    {
        int currentVal = Integer.parseInt(counterText[index].getString());
        counterText[index].setString(String.valueOf(currentVal - 1));
    }

    /**
     * Function called everytime a resource is used to plant.
     * Used to decrement the text counter, this time by a certain value.
     * @param index The index of the item used
     * @param value value to decrement by
     */
    public void decrement(int index, int value)
    {
        int currentVal = Integer.parseInt(counterText[index].getString());
        counterText[index].setString(String.valueOf(currentVal - value));
    }

    /**
     * Gets the text value of an item in a certain index in the reource menu.
     * @param index index of the item
     * @return value of the text counter at this index
     */
    public int getIndexVal(int index)
    {
        if(index < 0)
            return -1;

        return Integer.parseInt(counterText[index].getString());
    }

    /**
     * Function called everytime a resource was bought in the shop.
     * Used to increment the text counter.
     * @param index The index of the item bought
     * @param value The value to increment by
     */
    public void increment(int index, int value) 
    {
        int currentVal = Integer.parseInt(counterText[index].getString());
        counterText[index].setString(String.valueOf(currentVal + value));
    }
    
    /**
     * Called everytime a mouse click occurs.
     * Checks if any of the Icon in the resource menu have been clicked,
     * if yes, then the corresponding one is marked as selected and it's icon is changed.
     * If there already was one selected, it is marked as unselected and it's icon is changed.
     */
    public void selectIcon(float mouseX, float mouseY) 
    {
        int index = -1;
        
        for(int i = 0; i < seedIcons.length; i++)
        {
            /*int iconXPosition = gap + (iconWidth*2) * i; 
            int iconYPosition = gap;*/

            int iconXPosition = (int)seedIcons[i].getPosition().x; 
            int iconYPosition = (int)seedIcons[i].getPosition().y;
            
            if(mouseX > iconXPosition && mouseX < iconXPosition + iconWidth && mouseY > iconYPosition && mouseY < iconYPosition + iconHeight) 
            {
                if(i == 1 && carrotLocked == true)
                {
                    index = -1;
                }
                else if(i == 2 && hempLocked == true)
                {
                    index = -1;
                }
                else if(i == 3 && cauliflowerLocked == true)
                {
                    index = -1;
                }
                else
                {
                    index = i;
                }

                System.out.println("Index = " + i);
            }
        }
        
        //If the user click on a valid resource in the menu, this selects it and changes the icon to selected
        if(index != -1) 
        {
            //If there is an already selected icon
            if(selectedIndex != -1) 
            {
                if(selectedIndex == index)
                {
                    Loader.loadPathToRectangle("BoringGame/AllResources/Closeup Vegetables", "resource" + selectedIndex + ".png", seedIcons[selectedIndex], seedIconsTexture[selectedIndex]);   
                    selectedIndex = -1;  
                    System.out.println("Selected = " + selectedIndex);           
                }
                else
                {
                    Loader.loadPathToRectangle("BoringGame/AllResources/Closeup Vegetables", "resource" + selectedIndex + ".png", seedIcons[selectedIndex], seedIconsTexture[selectedIndex]);
                    //System.out.println("hello general kenobi");
                    selectedIndex = index;
                    System.out.println("Selected = " + selectedIndex);
                    Loader.loadPathToRectangle("BoringGame/AllResources/Closeup Vegetables", "resource" + index + "_selected.png", seedIcons[index], seedIconsTexture[index]);
                }
            }
            else //i.e there is no selected icon
            {
                selectedIndex = index;
                System.out.println("Selected = " + selectedIndex);
                Loader.loadPathToRectangle("BoringGame/AllResources/Closeup Vegetables", "resource" + index + "_selected.png", seedIcons[index], seedIconsTexture[index]);
            }
        }

    }

    /**
     * Returns the index of the resource that is currently selected
     * @return index of the resource that is currently selected
     */
    public int getSelectedIndex() 
    {
        return selectedIndex;
    }

    /**
     * This function is called by the Clock object (Every 5 seconds), once the player has bought the final upgrade.
     * The final upgrade gives the player 1 coin per 5 seconds
     */
    public void update(Observable clock, Object o)
    {
        if(autoIncrement == true)
        {
            this.increment(4, autoIncrementVal);
            System.out.println("AUTOMATIC INCREMENT HAS OCCURED");
        }
        
    }

    /**
     * Function used for deselecting.
     * This is used for example when the users plans seeds untill they run out, then the current seed is deselected automatically
     */
    public void deselect() 
    {
        Loader.loadPathToRectangle("BoringGame/AllResources/Closeup Vegetables", "resource" + selectedIndex + ".png", seedIcons[selectedIndex], seedIconsTexture[selectedIndex]);
        selectedIndex = -1;
    }
}