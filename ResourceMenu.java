package BoringGame;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

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
    private static int selectedIndex = -1;
    private Vector2f seedIconSize;
    private RectangleShape resuourceMenuRectange;
    private Texture resuourceMenuTexture;
    private RectangleShape[] seedIcons;
    private Texture[] seedIconsTexture;
    private RectangleShape[] numberDisplayBackground;
    private int[] resourceCounter;
    private Text[] counterText;
    private boolean isSelected = false;

    /**
     * Initializes the resource menu attributes
     */
    public ResourceMenu() 
    {
        //Sets up this rectangle, so that it can be drawn with the right parameter
        super(new Vector2f(width, height));

        this.setPosition(0, 0);
        this.setFillColor(new Color(128,128,128));

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

            counterText[i] = new Text("0", font, 50);
            counterText[i].setPosition(iconXPosition + iconWidth, 20);

            Loader.loadPathToRectangle("BoringGame/Sprites/FruitVeg/Closeup Vegetables", "resource" + i + ".png", seedIcons[i], seedIconsTexture[i]);
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
                    Loader.loadPathToRectangle("BoringGame/Sprites/FruitVeg/Closeup Vegetables", "resource" + selectedIndex + ".png", seedIcons[selectedIndex], seedIconsTexture[selectedIndex]);   
                    selectedIndex = -1;  
                    System.out.println("Selected = " + selectedIndex);           
                }
                else
                {
                    Loader.loadPathToRectangle("BoringGame/Sprites/FruitVeg/Temp_seeds", "resource" + selectedIndex + ".png", seedIcons[selectedIndex], seedIconsTexture[selectedIndex]);
                    System.out.println("hello general kenobi");
                    selectedIndex = index;
                    System.out.println("Selected = " + selectedIndex);
                    Loader.loadPathToRectangle("BoringGame/Sprites/FruitVeg/Temp_seeds", "resource" + index + "_selected.png", seedIcons[index], seedIconsTexture[index]);
                }
            }
            else //i.e there are no selected icon
            {
                selectedIndex = index;
                System.out.println("Selected = " + selectedIndex);
                Loader.loadPathToRectangle("BoringGame/Sprites/FruitVeg/Temp_seeds", "resource" + index + "_selected.png", seedIcons[index], seedIconsTexture[index]);
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
}