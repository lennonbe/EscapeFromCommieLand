package BoringGame;

import org.jsfml.system.Vector2f;

import BoringGame.Loader;

import org.jsfml.graphics.*;

/**
 * This class represents the How To Play section of the main menu
 * This screen has one button to go back to the main menu
 */
public class HowToPlayScreen extends RectangleShape {
    
    private Vector2f buttonSize = new Vector2f(100, 50);
    private RectangleShape backButton;
    private RectangleShape prevButton;
    private RectangleShape nextButton;
    private Texture texture;
    private Texture prevTexture;
    private Texture nextTexture;
    private Texture buttonTexture;
    private int instructionSlide = 1;
    private Boolean isOpen;
    private Font font;
    private Text buttonText;
    private Text howToText;
    private String howToTextString;

    public HowToPlayScreen(float width, float height) {
        //Setting variables of this, which is the rectangle that is the background of the main menu
        super(new Vector2f(width, height));

        this.setPosition(0, 0);

        texture = new Texture();
        Loader.loadPathToRectangle("BoringGame/AllResources/Instructions", "Instructions" + instructionSlide + ".png", this, texture);

        isOpen = false;

        //Initializing the back button
        backButton = new RectangleShape(new Vector2f(100, 50));
        backButton.setPosition(20, height - buttonSize.y - 20);
        buttonTexture = new Texture();

        prevButton = new RectangleShape(new Vector2f(40, 50));
        prevButton.setPosition(width/2 - 40, 192);
        prevTexture = new Texture();

        nextButton = new RectangleShape(new Vector2f(40, 50));
        nextButton.setPosition(width/2 + 10, 192);
        nextTexture = new Texture();

        font = new Font();
        Loader.loadPathToFont(font, "BoringGame/AllResources/Chernobyl.otf");

        buttonText = new Text("BACK", font);
        buttonText.setPosition(backButton.getPosition().x + 20, backButton.getPosition().y);

        //Text explaining how to play the game
        howToTextString = "   - Click on the top menu to select seeds.\n\n   - Walk up to a field and left click to\n     plant selected seed.\n\n   - Once grown, walk up to a field and left\n     click to collect.\n\n   - Unlock new seeds and upgrades in the\n     shop menu.\n\n   - Win the game buy purchasing the\n     pasports for your family!";
        howToText = new Text(howToTextString, font);
        howToText.setPosition(70, 300);

        Loader.loadPathToRectangle("BoringGame/AllResources/MainMenu", "MainMenuButton.png", backButton, buttonTexture);
        Loader.loadPathToRectangle("BoringGame/AllResources/Instructions", "DirectionButtons2.png", prevButton, prevTexture);
        Loader.loadPathToRectangle("BoringGame/AllResources/Instructions", "DirectionButtons1.png", nextButton, nextTexture);
    }

    /**
     * This method is called when this menu is open.
     * It checks if the back button has been pressed,
     * if yes, return to main menu
     * 
     * @param mouseX float - x coordinate of the mouse when it was clicked
     * @param mouseY float - y coordinate of the mouse when it was clicked
     */
    public void isClicked(float mouseX, float mouseY) {
        if(mouseX > backButton.getPosition().x && mouseX < backButton.getPosition().x + buttonSize.x &&
           mouseY > backButton.getPosition().y && mouseY < backButton.getPosition().y + buttonSize.y) 
        {
            isOpen = false;
        }

        if(mouseX > prevButton.getPosition().x && mouseX < prevButton.getPosition().x + 40 &&
           mouseY > prevButton.getPosition().y && mouseY < prevButton.getPosition().y + 50) 
        {
            if(instructionSlide > 1) {
                instructionSlide--;
                Loader.loadPathToRectangle("BoringGame/AllResources/Instructions", "Instructions" + instructionSlide + ".png", this, texture);
                if(instructionSlide == 1)
                    howToText.setString(howToTextString);
            }
        }

        if(mouseX > nextButton.getPosition().x && mouseX < nextButton.getPosition().x + 40 &&
           mouseY > nextButton.getPosition().y && mouseY < nextButton.getPosition().y + 50)
        {
            if(instructionSlide < 4) {
                howToText.setString("");
                instructionSlide++;
                Loader.loadPathToRectangle("BoringGame/AllResources/Instructions", "Instructions" + instructionSlide + ".png", this, texture);
            }
        }
    }

    /**
     * Returns the texts that make up this menu 
     * 
     * @return Text[] - All the text that are in this menu 
     */
    public Text[] getText() {
        return new Text[] {buttonText, howToText};
    }

    /**
     * Returns the backButton
     * 
     * @return RectangleShape - backButton
     */
    public RectangleShape[] getButtons() {
        return new RectangleShape[] {this, backButton, prevButton, nextButton};
    }

    /**
     * Returns the boolean that represents if this menu is open or not
     * 
     * @return Boolean - Representing if the menu is open 
     */
    public Boolean getIsOpen() {
        return isOpen;
    }

    /**
     * Sets the value of isOpen
     * 
     * @param b - Boolean that represents the new state of the isOpen variable
     */
    public void setIsOpen(Boolean b) {
        isOpen = b;
    }
}
