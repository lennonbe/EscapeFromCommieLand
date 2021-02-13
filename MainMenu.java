package BoringGame;

import org.jsfml.system.Vector2f;
import org.jsfml.graphics.*;

/**
 * This class represents the main menu that is the first thing that the user will see once the game is booted
 * There are three buttons :
 *      Start game  - Starts the game
 *      How To Play - A section describing the rule and controls of the game
 *      Exit        - Exits the game 
 */
public class MainMenu extends RectangleShape {

    private final Vector2f buttonSize = new Vector2f(400, 100);
    private HowToPlayScreen htps;
    private RectangleShape startGame;
    private RectangleShape exit;
    private RectangleShape howToPlay;
    private Boolean isHowToPlayOpen;
    private Boolean isOpen;
    private Text startGameText;
    private Text exitText;
    private Text howToPlayText;
    private long startTime;
    private Font font;

    public MainMenu(float width, float height) {
        //Setting variables of this, which is the rectangle that is the background of the main menu
        super(new Vector2f(width, height));

        this.setPosition(0, 0);
        this.setFillColor(new Color(0, 0, 0));

        htps = new HowToPlayScreen(width, height);

        isOpen = true;
        isHowToPlayOpen = htps.getIsOpen();

        //Initializing the buttons
        startGame = new RectangleShape(buttonSize);
        startGame.setPosition(width/2 - buttonSize.x/2, height/2 - buttonSize.y/2 - 200);
        startGame.setFillColor(new Color(128, 128, 128));

        howToPlay = new RectangleShape(buttonSize);
        howToPlay.setPosition(width/2 - buttonSize.x/2, height/2 - buttonSize.y/2);
        howToPlay.setFillColor(new Color(128, 128, 128));

        exit = new RectangleShape(buttonSize);
        exit.setPosition(width/2 - buttonSize.x/2, height/2 - buttonSize.y/2 + 200);
        exit.setFillColor(new Color(128, 128, 128));

        font = new Font();
        Loader.loadPathToFont(font, "BoringGame/pixelated.ttf");

        startGameText = new Text("START GAME", font);
        startGameText.setScale(2, 2);

        howToPlayText = new Text("HOW TO PLAY", font);
        howToPlayText.setScale(2, 2);

        exitText = new Text("EXIT GAME", font);
        exitText.setScale(2, 2);

        startGameText.setPosition(startGame.getPosition().x + 50, startGame.getPosition().y);
        howToPlayText.setPosition(howToPlay.getPosition().x + 50, howToPlay.getPosition().y);
        exitText.setPosition(exit.getPosition().x + 50, exit.getPosition().y);
    }

    /**
     * This function checks if any of the buttons have been pressed,
     * If yes, act accordingly
     */
    public void isClicked(float mouseX, float mouseY) {
        if(isHowToPlayOpen) {
            htps.isClicked(mouseX, mouseY);
            isHowToPlayOpen = htps.getIsOpen();
        } else {
            //Check if the start game button is clicked
            if(mouseX > startGame.getPosition().x && mouseX < startGame.getPosition().x + buttonSize.x &&
               mouseY > startGame.getPosition().y && mouseY < startGame.getPosition().y + buttonSize.y) 
            {
                //Closes this menu
                isOpen = false;
                startTime = System.currentTimeMillis();
            }
    
            //Checks if the howToPlay button has been clicked
            if(mouseX > howToPlay.getPosition().x && mouseX < howToPlay.getPosition().x + buttonSize.x &&
               mouseY > howToPlay.getPosition().y && mouseY < howToPlay.getPosition().y + buttonSize.y) 
            {
                isHowToPlayOpen = true;
                htps.setIsOpen(true);
            } 
    
            //Checks if the exit button has been clicked
            if(mouseX > exit.getPosition().x && mouseX < exit.getPosition().x + buttonSize.x &&
               mouseY > exit.getPosition().y && mouseY < exit.getPosition().y + buttonSize.y) 
            {
                //Exits the game
                System.exit(0);
            }
        }
    }

    /**
     * Returns a Boolean representing if the main menu is open or not
     * 
     * @return Boolean - true if menu is open, false otherwise
     */
    public Boolean getIsOpen() {
        return isOpen;
    }
    
    /**
     * Void method to set IsOpen variable
     * @param bool boolean value we wish to set isOpen to
     */
    public void setIsOpen(boolean bool)
    {
        isOpen = bool;
    }

    /**
     * Returns all the rectangles that make up the menu
     * 
     * @return RectangleShape[] - All RectangleShape in the menu
     */
    public RectangleShape[] getButtons() {
        if(isHowToPlayOpen) {
            return new RectangleShape[] {htps, htps.getButton()};
        } else {
            return new RectangleShape[] {startGame, exit, howToPlay};
        }
    }

    /**
     * Returns all the texts that make up the menu
     * 
     * @return Text[] - All Text in the menu
     */
    public Text[] getButtonText() {
        if(isHowToPlayOpen) {
            return htps.getText();
        } else {
            return new Text[] {startGameText, exitText, howToPlayText};
        }
    }

    /**
     * Used for calculating how long the player has been playing for
     * 
     * @return long - The time at which the player has started the game 
     */
    public long getStartTime() {
        return startTime;
    }
}