package BoringGame;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.io.*;

/**
 * Class representing the resource menu in the top left of the screen.
 * This class implements the functionality for selecting resources,
 * and shows changes in the resources when new ones are aquired or lost.
 */
public class ResourceMenu extends RectangleShape 
{
    
    //Inherit attributes of the ResoureMenu are final
    private final static int width = 550;
    private final static int height = 90;
    private final int numberOfIcons = 5;
    private final int iconWidth = 50;
    private final int iconHeight = 50;
    private final int gap = 20;
    
    //More attributes about the resource menu
    private Font font;
    protected static int selectedIndex = -1;
    private Vector2f seedIconSize;
    private RectangleShape resuourceMenuRectange;
    private Texture resuourceMenuTexture;
    private RectangleShape[] seedIcons;
    private Texture[] seedIconsTexture;
    private RectangleShape[] numberDisplayBackground;
    private int[] resourceCounter;
    private Text[] counterText;
    private boolean isSelected = false;
    private Texture resourceMenuTexture;

    /**
     * Initializes the resource menu attributes
     */
    public ResourceMenu(BuyMenu buyMenu) 
    {
        //Sets up this rectangle, so that it can be drawn with the right parameter
        super(new Vector2f(width, height));

        this.setPosition(0, 0);
        this.setFillColor(new Color(128,128,128));

        /*resourceMenuTexture = new Texture();
        loadPathToRectangle("BoringGame/AllResources", "TopMenu.png");*/

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
        numberDisplayBackground = new RectangleShape[numberOfIcons];
        counterText = new Text[numberOfIcons];
        
        /**
         * This for loop positions the icons equidistant from eachother,
         * with the 'display' (black rectangles) inbetween.
         * Then loads in the icons and sets their attributes.
         */
        for(int i = 0; i < numberOfIcons; i++) 
        {
            int iconXPosition = gap + (iconWidth*2) * i; 

            numberDisplayBackground[i] = new RectangleShape(new Vector2f(iconWidth, iconHeight));
            numberDisplayBackground[i].setPosition(iconXPosition + iconWidth, 20);
            numberDisplayBackground[i].setFillColor(new Color(0, 0, 0));
            
            seedIcons[i] = new RectangleShape();
            seedIconsTexture[i] = new Texture();
            
            seedIcons[i].setPosition(new Vector2f(iconXPosition, 20));
            seedIcons[i].setSize(seedIconSize);
            
            if(i == 4)//initial money val is 5
            {
                counterText[i] = new Text("5", font, 50);
            }
            else
            {
                counterText[i] = new Text("0", font, 50);
            }

            counterText[i].setPosition(iconXPosition + iconWidth, 20);

            Loader.loadPathToRectangle("BoringGame/AllResources/Closeup Vegetables", "resource" + i + ".png", seedIcons[i], seedIconsTexture[i]);
        }
    }

    /**
     * This function adds all the JSFML Rectangles used to draw the resource menu into one array and returns it.
     * This is useful in the class that implements this menu, so that the rectangles can be drawn onto the screen
     * 
     * @return The array of rectangles that make up the menu
     */
    public RectangleShape[] getRectangleArray() 
    {
        RectangleShape[] result = new RectangleShape[numberOfIcons*2];

        System.arraycopy(seedIcons, 0, result, 0, numberOfIcons);
        System.arraycopy(numberDisplayBackground, 0, result, numberOfIcons, numberOfIcons);

        return result;
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
            int iconXPosition = gap + (iconWidth*2) * i; 
            int iconYPosition = gap;
            
            if(mouseX > iconXPosition && mouseX < iconXPosition + iconWidth && mouseY > iconYPosition && mouseY < iconYPosition + iconHeight) 
            {
                index = i;
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
            resourceMenuTexture.loadFromFile(path);
        } 
        catch (Exception e) {}

        this.setTexture(resourceMenuTexture);
    }
}