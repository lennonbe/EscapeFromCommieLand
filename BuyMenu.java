package BoringGame;

import org.jsfml.window.*;

import BoringGame.Loader;

//import org.w3c.dom.Text;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.io.*;

public class BuyMenu extends RectangleShape
{
    private float exitButtonSize = 15;
    private Vector2f vegIconsSize = new Vector2f(40, 40);
    private Vector2f upgradeIconsSize = new Vector2f(40, 40);
    private Vector2f passportSize = new Vector2f(40, 40);
    private float gap = 24;
    private float xLocation, yLocation;
    private float xCauliflower, xTomato, xSweetCorn, xCarrot, yCauliflower, yTomato, ySweetCorn, yCarrot;
    protected RectangleShape [] vegIcons = new RectangleShape[4];
    protected Texture [] vegTextures = new Texture[4];
    protected RectangleShape [] upgradeIcons = new RectangleShape[4];
    protected Texture [] upgradeTextures = new Texture[4];
    protected Text[] counterText;
    protected Text passportText;
    protected PassportBuy passport;
    private int textSize = 12;
    private RectangleShape exitButton  = new RectangleShape();
    private Texture exitButtonTexture  = new Texture();
    private Texture menuTexture = new Texture();
    private float yPositionVeg, yPositionUpgrades;
    protected boolean menuOpen = false;

    private final int numberOfIcons = 7;
    private Font font = new Font();
    
    /**
     * Constructor for BuyMenu class.
     * @param size the size of the menu
     * @param window the window to insert the BuyMenu in
     */
    public BuyMenu(Vector2f size, RenderWindow window)
    {
        
        super(size);
        exitButton.setSize(new Vector2f(exitButtonSize,exitButtonSize)); 
        Loader.loadPathToRectangle("BoringGame/AllResources", "PurchaseMenuV2.png", this, menuTexture);
        
        this.setPosition(window.getSize().x/2 - size.x/2, window.getSize().y/2 - size.y/2);
        exitButton.setPosition(this.getPosition().x + this.getSize().x - exitButtonSize * 2 + 2, this.getPosition().y + exitButtonSize - 4);
        
        this.setFillColor(new Color(128,128,128));
        exitButton.setFillColor(new Color(255,0,0));
        Loader.loadPathToRectangle("BoringGame/AllResources", "ExitButton.png", exitButton, exitButtonTexture);
        
        yPositionVeg = this.getPosition().y + this.getSize().y/4 - vegIconsSize.x/2 + 53;
        yPositionUpgrades = this.getPosition().y + this.getSize().y - this.getSize().y/4 - vegIconsSize.x/2 - 1;
        
        int temp = (int)this.getPosition().x + 32;
        for(int i = 0; i < vegIcons.length; i++) 
        {
            vegIcons[i] = new RectangleShape(vegIconsSize);
            vegTextures[i] = new Texture();
            
            if(i == 0)
            {
                vegIcons[i].setPosition(new Vector2f(temp + 5, yPositionVeg));
            }
            else
            {
                vegIcons[i].setPosition(new Vector2f(temp, yPositionVeg));
            }
            
            temp += (vegIconsSize.x + gap);
            
            if(i == 0)
            {
                Loader.loadPathToRectangle("BoringGame/AllResources/Closeup Vegetables", "resource" + i + ".png", vegIcons[i], vegTextures[i]);
            }
            else
            {
                Loader.loadPathToRectangle("BoringGame/AllResources/SilhouetteVeg", "resource" + i + "_sil.png", vegIcons[i], vegTextures[i]);
            }
            
        }
        
        temp = (int)this.getPosition().x + 32;
        for(int i = 0; i < upgradeIcons.length; i++) 
        {
            upgradeIcons[i] = new RectangleShape(upgradeIconsSize);
            upgradeTextures[i] = new Texture();
            
            if(i == 0)
            {
                upgradeIcons[i].setPosition(new Vector2f(temp + 5, yPositionUpgrades));
            }
            else
            {
                upgradeIcons[i].setPosition(new Vector2f(temp, yPositionUpgrades));
            }
            
            temp += (upgradeIconsSize.x + gap);
            
            Loader.loadPathToRectangle("BoringGame/AllResources/Silhouette", "upgrade" + i + "_sil.png", upgradeIcons[i], upgradeTextures[i]);
        }
        
        Loader.loadPathToFont(font, "BoringGame/AllResources/Russian.ttf");
        counterText = new Text[8];
        for(int i = 0; i < counterText.length; i++)
        {        
            int j = i - 4;    
            if(i == 0)
            {
                counterText[i] = new Text("1", font, textSize);
                counterText[i].setPosition(new Vector2f((float)(vegIcons[i].getPosition().x + (int)vegIcons[i].getSize().x - 10), (float)(vegIcons[i].getPosition().y + (int)vegIcons[i].getSize().y - 10)));
            }
            else if(i == 1)
            {
                counterText[i] = new Text("5", font, textSize);
                counterText[i].setPosition(new Vector2f((float)(vegIcons[i].getPosition().x + (int)vegIcons[i].getSize().x - 10), (float)(vegIcons[i].getPosition().y + (int)vegIcons[i].getSize().y - 10)));
            }
            else if(i == 2)
            {
                counterText[i] = new Text("10", font, textSize);   
                counterText[i].setPosition(new Vector2f((float)(vegIcons[i].getPosition().x + (int)vegIcons[i].getSize().x - 10), (float)(vegIcons[i].getPosition().y + (int)vegIcons[i].getSize().y - 10)));     
            }
            else if(i == 3)
            {
                counterText[i] = new Text("20", font, textSize);
                counterText[i].setPosition(new Vector2f((float)(vegIcons[i].getPosition().x + (int)vegIcons[i].getSize().x - 10), (float)(vegIcons[i].getPosition().y + (int)vegIcons[i].getSize().y - 10)));
            }
            else if(i == 4)
            {
                counterText[i] = new Text("5", font, textSize);
                counterText[i].setPosition(new Vector2f((float)((int)upgradeIcons[j].getPosition().x + (int)upgradeIcons[j].getSize().x - 10), (float)((int)upgradeIcons[j].getPosition().y + (int)upgradeIcons[j].getSize().y - 10)));
            }
            else if(i == 5)
            {
                counterText[i] = new Text("10", font, textSize);
                counterText[i].setPosition(new Vector2f((float)((int)upgradeIcons[j].getPosition().x + (int)upgradeIcons[j].getSize().x - 10), (float)((int)upgradeIcons[j].getPosition().y + (int)upgradeIcons[j].getSize().y - 10)));
            }
            else if(i == 6)
            {
                counterText[i] = new Text("15", font, textSize);
                counterText[i].setPosition(new Vector2f((float)((int)upgradeIcons[j].getPosition().x + (int)upgradeIcons[j].getSize().x - 10), (float)((int)upgradeIcons[j].getPosition().y + (int)upgradeIcons[j].getSize().y - 10)));
            }
            else if(i == 7)
            {
                counterText[i] = new Text("20", font, textSize);
                counterText[i].setPosition(new Vector2f((float)((int)upgradeIcons[j].getPosition().x + (int)upgradeIcons[j].getSize().x - 10), (float)((int)upgradeIcons[j].getPosition().y + (int)upgradeIcons[j].getSize().y - 10)));
            }
        }

        textSize = 8;
        passport = new PassportBuy(passportSize, (int)this.getPosition().x + 32, yPositionVeg - 65);
        passportText = new Text("1500", font, textSize);
        passportText.setPosition(new Vector2f((float)((int)passport.getPosition().x + (int)passport.getSize().x - 8), (float)((int)passport.getPosition().y + (int)passport.getSize().y - 5)));
    }

    /**
     * allows to check if the exit button was clicked, returning true if so.
     * @param mouseX mouse X position on window
     * @param mouseY mouse Y position on window
     * @return boolean (true if exit button is clicked false otherwise)
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