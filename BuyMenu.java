package BoringGame;

import org.jsfml.window.*;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

public class BuyMenu extends RectangleShape
{
    private float exitButtonSize = 15;
    private Vector2f vegIconsSize = new Vector2f(50, 50);
    private Vector2f upgradeIconsSize = new Vector2f(50, 50);
    private float gap = 20;
    private float xLocation, yLocation;
    private float xCauliflower, xTomato, xSweetCorn, xCarrot, yCauliflower, yTomato, ySweetCorn, yCarrot;
    protected RectangleShape [] vegIcons = new RectangleShape[4];
    private Texture [] vegTextures = new Texture[4];
    protected RectangleShape [] upgradeIcons = new RectangleShape[3];
    private Texture [] upgradeTextures = new Texture[3];
    private RectangleShape exitButton  = new RectangleShape();
    private Texture menuTexture;
    private float yPositionVeg, yPositionUpgrades;
    protected boolean menuOpen = false;

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

        yPositionVeg = this.getPosition().y + this.getSize().y/4 - vegIconsSize.x/2;
        yPositionUpgrades = this.getPosition().y + this.getSize().y - this.getSize().y/4 - vegIconsSize.x/2;
        int temp = (int)this.getPosition().x + 20;

        for(int i = 0; i < vegIcons.length; i++) 
        {
            vegIcons[i] = new RectangleShape(vegIconsSize);
            vegTextures[i] = new Texture();

            vegIcons[i].setPosition(new Vector2f(temp, yPositionVeg));
            
            temp += (vegIconsSize.x + gap);

            Loader.loadPathToRectangle("BoringGame/Sprites/FruitVeg/Temp_seeds", "resource" + i + ".png", vegIcons[i], vegTextures[i]);
        }

        temp = (int)this.getPosition().x + 20;
        
        for(int i = 0; i < upgradeIcons.length; i++) 
        {
            upgradeIcons[i] = new RectangleShape(upgradeIconsSize);
            upgradeTextures[i] = new Texture();

            upgradeIcons[i].setPosition(new Vector2f(temp, yPositionUpgrades));
            
            temp += (upgradeIconsSize.x + gap);

            Loader.loadPathToRectangle("BoringGame/Sprites/FruitVeg/Temp_seeds", "resource" + i + ".png", upgradeIcons[i], upgradeTextures[i]);
        }

    }

    /**
     * Detects if the exitButton is clicked
     * @param window current window the menu is in
     * @return returns boolean value if the exit is clicked
     */
    public boolean isExitClicked(float mouseX, float mouseY)
    {
        boolean flag = false;

        if(Mouse.isButtonPressed(Mouse.Button.LEFT))
        {
            if(mouseX >= exitButton.getPosition().x && mouseX <= exitButton.getPosition().x + exitButton.getSize().x && mouseY >= exitButton.getPosition().y && mouseY <= exitButton.getPosition().y + exitButton.getSize().y)
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
    public RectangleShape[] getVegArray()
    {
        return vegIcons;
    }

    /**
     * returns the array of rectangles which serve as buttons
     * @return array of rectangles which serve as buttons
     */
    public RectangleShape[] getUpgradeArray()
    {
        return upgradeIcons;
    }

    /**
     * returns the exit button
     * @return the exitButton object
     */
    public RectangleShape getExitButton()
    {
        return exitButton;
    }
}