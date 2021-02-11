package BoringGame;

import org.jsfml.system.Vector2f;

import BoringGame.Loader;

import org.jsfml.graphics.*;

public class MainMenu extends RectangleShape {

    private final Vector2f buttonSize = new Vector2f(400, 100);
    private RectangleShape startGame;
    private RectangleShape exit;
    private Boolean isOpen = true;
    private long startTime;
    private Font font;
    private Text startGameText;
    private Text exitText;

    public MainMenu(float width, float height) {
        super(new Vector2f(width, height));

        this.setPosition(0, 0);
        this.setFillColor(new Color(0, 0, 0));

        startGame = new RectangleShape(buttonSize);
        startGame.setPosition(width/2 - buttonSize.x/2, height/2 - buttonSize.y/2 - 100);
        startGame.setFillColor(new Color(128, 128, 128));

        exit = new RectangleShape(buttonSize);
        exit.setPosition(width/2 - buttonSize.x/2, height/2 - buttonSize.y/2 + 100);
        exit.setFillColor(new Color(128, 128, 128));

        font = new Font();
        Loader.loadPathToFont(font, "BoringGame/pixelated.ttf");

        startGameText = new Text("START GAME", font);
        startGameText.setScale(2, 2);
        exitText = new Text("EXIT GAME", font);
        exitText.setScale(2, 2);

        startGameText.setPosition(startGame.getPosition().x + 50, startGame.getPosition().y);
        exitText.setPosition(exit.getPosition().x + 50, exit.getPosition().y);

    }

    /**
     * This function checks if any of the buttons have been pressed,
     * If yes, act accordingly
     */
    public void isClicked(float mouseX, float mouseY) {
        if(mouseX > startGame.getPosition().x && mouseX < startGame.getPosition().x + buttonSize.x &&
           mouseY > startGame.getPosition().y && mouseY < startGame.getPosition().y + buttonSize.y) 
        {
            //Closes this menu
            isOpen = false;
            startTime = System.currentTimeMillis();
        }

        if(mouseX > exit.getPosition().x && mouseX < exit.getPosition().x + buttonSize.x &&
           mouseY > exit.getPosition().y && mouseY < exit.getPosition().y + buttonSize.y) 
        {
            //Exits the game
            System.exit(0);
        }
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public RectangleShape[] getButtons() {
        return new RectangleShape[] {startGame, exit};
    }

    public Text[] getButtonText() {
        return new Text[] {startGameText, exitText};
    }

    public long getStartTime() {
        return startTime;
    }
}