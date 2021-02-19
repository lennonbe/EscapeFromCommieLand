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
    private Boolean isOpen;
    private Font font;
    private Text buttonText;
    private Text howToText;

    public HowToPlayScreen(float width, float height) {
        //Setting variables of this, which is the rectangle that is the background of the main menu
        super(new Vector2f(width, height));

        this.setPosition(0, 0);
        this.setFillColor(new Color(0, 0, 0));

        isOpen = false;

        //Initializing the back button
        backButton = new RectangleShape(new Vector2f(100, 50));
        backButton.setPosition(100, height - buttonSize.y - 100);
        backButton.setFillColor(new Color(128, 128, 128));

        font = new Font();
        Loader.loadPathToFont(font, "BoringGame/pixelated.ttf");

        buttonText = new Text("BACK", font);
        buttonText.setPosition(backButton.getPosition().x + 20, backButton.getPosition().y);

        //Text explaining how to play the game
        howToText = new Text(Loader.readFile("BoringGame/Instructions.txt"), font);
        howToText.setPosition(50, 50);
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
    public RectangleShape getButton() {
        return backButton;
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
