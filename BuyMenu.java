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

public class BuyMenu extends RectangleShape
{
    private float exitButtonSize = 15;
    private Vector2f vegIconsSize = new Vector2f(50, 50);
    private float gap = 20;
    private float xLocation, yLocation;
    private float xCauliflower, xTomato, xSweetCorn, xCarrot, yCauliflower, yTomato, ySweetCorn, yCarrot;
    private RectangleShape [] vegIcons = new RectangleShape[4];
    private Texture [] vegTextures = new Texture[4];
    private RectangleShape exitButton  = new RectangleShape();
    private Texture menuTexture;
    private float yPosition;

    /**
     * Constructor for BuyMenu class.
     * @param size the size of the menu
     * @param window the window to insert the BuyMenu in
     */
    public BuyMenu(Vector2f size, RenderWindow window)
    {
        
        super(size);
        exitButton.setSize(new Vector2f(exitButtonSize,exitButtonSize)); 

        this.setPosition(window.getSize().x/2 - size.x/2, window.getSize().y/2 - size.y/2);
        exitButton.setPosition(this.getPosition().x + this.getSize().x - exitButtonSize, this.getPosition().y);

        this.setFillColor(new Color(128,128,128));
        exitButton.setFillColor(new Color(255,0,0));

        yPosition = this.getPosition().y + this.getSize().y/2 - vegIconsSize.x/2;
        int temp = (int)this.getPosition().x + 20;

        for(int i = 0; i < 4; i++) {
            vegIcons[i] = new RectangleShape(vegIconsSize);
            vegTextures[i] = new Texture();

            vegIcons[i].setPosition(new Vector2f(temp, yPosition));
            
            temp += (vegIconsSize.x + gap);
        }

        //Temporary solution ---- It'll be changes once we have the seed icons made
        loadPathToRectangle("BoringGame/Sprites/FruitVeg/Temp_seeds", "coffee.png", vegIcons[0], vegTextures[0]);
        loadPathToRectangle("BoringGame/Sprites/FruitVeg/Temp_seeds", "seeds.png", vegIcons[1], vegTextures[1]);
        loadPathToRectangle("BoringGame/Sprites/FruitVeg/Temp_seeds", "sesame.png", vegIcons[2], vegTextures[2]);
        loadPathToRectangle("BoringGame/Sprites/FruitVeg/Temp_seeds", "soy.png", vegIcons[3], vegTextures[3]);
    }

    /**
     * Chooses what vegetable to buy.
     * @param window the current game window for prespective
     * @return the array index of the vegetables
     */
    public int buyVeg(Window window)
    {
        int returnValue = -1;

        if(Mouse.isButtonPressed(Mouse.Button.LEFT))
        {
            if(Mouse.getPosition(window).x >= vegIcons[0].getPosition().x && Mouse.getPosition(window).x <= vegIcons[0].getPosition().x + vegIcons[0].getSize().x && Mouse.getPosition(window).y >= vegIcons[0].getPosition().y && Mouse.getPosition(window).y <= vegIcons[0].getPosition().y + vegIcons[0].getSize().y)
            {
                returnValue = 0;
            }
            else if(Mouse.getPosition(window).x >= vegIcons[1].getPosition().x && Mouse.getPosition(window).x <= vegIcons[1].getPosition().x + vegIcons[1].getSize().x && Mouse.getPosition(window).y >= vegIcons[1].getPosition().y && Mouse.getPosition(window).y <= vegIcons[1].getPosition().y + vegIcons[1].getSize().y)
            {
                returnValue = 1;
            }
            else if(Mouse.getPosition(window).x >= vegIcons[2].getPosition().x && Mouse.getPosition(window).x <= vegIcons[2].getPosition().x + vegIcons[2].getSize().x && Mouse.getPosition(window).y >= vegIcons[2].getPosition().y && Mouse.getPosition(window).y <= vegIcons[2].getPosition().y + vegIcons[2].getSize().y)
            {
                returnValue = 2;
            }
            else if(Mouse.getPosition(window).x >= vegIcons[3].getPosition().x && Mouse.getPosition(window).x <= vegIcons[3].getPosition().x + vegIcons[3].getSize().x && Mouse.getPosition(window).y >= vegIcons[3].getPosition().y && Mouse.getPosition(window).y <= vegIcons[3].getPosition().y + vegIcons[3].getSize().y)
            {
                returnValue = 3;
            }
        }

        return returnValue;
    }

    /**
     * Detects if the exitButton is clicked
     * @param window current window the menu is in
     * @return returns boolean value if the exit is clicked
     */
    public boolean isExitClicked(Window window)
    {
        boolean flag = false;

        if(Mouse.isButtonPressed(Mouse.Button.LEFT))
        {
            if(Mouse.getPosition(window).x >= exitButton.getPosition().x && Mouse.getPosition(window).x <= exitButton.getPosition().x + exitButton.getSize().x && Mouse.getPosition(window).y >= exitButton.getPosition().y && Mouse.getPosition(window).y <= exitButton.getPosition().y + exitButton.getSize().y)
            {
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
     * returns the array of rectangles which serve as buttons
     * @return array of rectangles which serve as buttons
     */
    public RectangleShape[] getRectangleArray()
    {
        return vegIcons;
    }

    /**
     * returns the exit button
     * @return the exitButton object
     */
    public RectangleShape getExitButton()
    {
        return exitButton;
    }

    /**
     * Loads path in to the recatangles.
     * @param directory
     * @param file
     * @param rectangle
     * @param texture
     */
    public void loadPathToRectangle(String directory, String file, RectangleShape rectangle, Texture texture)
    {
        Path path = FileSystems.getDefault().getPath(directory, file);
        
        try {
            BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);

        } catch (Exception e) {}

        try {
            texture.loadFromFile(path);
        } 
        catch (Exception e) {}

        rectangle.setTexture(texture);
    }
}