package BoringGame;

import org.jsfml.system.Vector2f;

import BoringGame.InitialCutscene;
import BoringGame.Loader;

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
    private InitialCutscene initialScene;
    private HowToPlayScreen htps;
    private RectangleShape startGame;
    private RectangleShape exit;
    private RectangleShape howToPlay;
    private Texture startTexture;
    private Texture tutorialTexture;
    private Texture exitTexture;
    private Boolean isHowToPlayOpen;
    private Boolean isOpen;
    private Texture texture;
    private Text startGameText;
    private Text exitText;
    private Text howToPlayText;
    private long startTime;
    private Font font;

    public MainMenu(float width, float height) {
        //Setting variables of this, which is the rectangle that is the background of the main menu
        super(new Vector2f(width, height));

        this.setPosition(0, 0);

        texture = new Texture();
        Loader.loadPathToRectangle("BoringGame/AllResources/MainMenu", "MainMenu.png", this, texture);

        initialScene = new InitialCutscene(width, height);
        htps = new HowToPlayScreen(width, height);

        isOpen = true;
        isHowToPlayOpen = htps.getIsOpen();

        //Initializing the buttons
        startGame = new RectangleShape(buttonSize);
        startGame.setPosition(width/2 - buttonSize.x/2, height/2 - buttonSize.y/2 - 200);
        startTexture = new Texture();

        howToPlay = new RectangleShape(buttonSize);
        howToPlay.setPosition(width/2 - buttonSize.x/2, height/2 - buttonSize.y/2);
        tutorialTexture = new Texture();

        exit = new RectangleShape(buttonSize);
        exit.setPosition(width/2 - buttonSize.x/2, height/2 - buttonSize.y/2 + 200);
        exitTexture = new Texture();

        font = new Font();
        Loader.loadPathToFont(font, "BoringGame/AllResources/pixelated.ttf");

        startGameText = new Text("START GAME", font);
        startGameText.setScale(2, 2);

        howToPlayText = new Text("HOW TO PLAY", font);
        howToPlayText.setScale(2, 2);

        exitText = new Text("EXIT GAME", font);
        exitText.setScale(2, 2);

        startGameText.setPosition(startGame.getPosition().x + 50, startGame.getPosition().y + 10);
        howToPlayText.setPosition(howToPlay.getPosition().x + 50, howToPlay.getPosition().y + 10);
        exitText.setPosition(exit.getPosition().x + 50, exit.getPosition().y + 10);

        Loader.loadPathToRectangle("BoringGame/AllResources/MainMenu", "MainMenuButton.png", startGame, startTexture);
        Loader.loadPathToRectangle("BoringGame/AllResources/MainMenu", "MainMenuButton.png", howToPlay, tutorialTexture);
        Loader.loadPathToRectangle("BoringGame/AllResources/MainMenu", "MainMenuButton.png", exit, exitTexture);
    }

    /**
     * This function checks if any of the buttons have been pressed,
     * If yes, act accordingly
     */
    public void isClicked(float mouseX, float mouseY) {
        if(initialScene.getSceneIsOver()) {
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
        } else {
            initialScene.isClicked(mouseX, mouseY);
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
     * Returns all the rectangles that make up the menu.
     * This function checks which screen is has to be shown currently,
     * and returns those rectangles accordingly.
     * 
     * @return RectangleShape[] - All RectangleShape in the menu
     */
    public RectangleShape[] getButtons() {
        if(isHowToPlayOpen) {
            return htps.getButtons();
            // System.arraycopy(htps.getButton(), 0, result, 0, length);
        } else if(!initialScene.getSceneIsOver()){
            return initialScene.getRectangles();
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
        } else if(!initialScene.getSceneIsOver()) {
            return initialScene.getText();
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