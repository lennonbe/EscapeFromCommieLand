package BoringGame;

import org.jsfml.system.Vector2f;
import org.jsfml.graphics.*;

/**
 * This class represents the endSlide that is the first thing that the user will see once the game is booted
 * There is one button:
 *      Exit        - Exits the game 
 */
public class EndSlide extends RectangleShape {

    private final Vector2f buttonSize = new Vector2f(400, 100);
    protected RectangleShape exit, scoreboard;
    protected Text exitText, scoreboardText,scoreDigitsText;
    protected Text youWin;
    protected Text youLost;
    private Boolean isOpen;
    protected boolean scoreboardDisplay = false;
    private Font font;
    protected int score;
    protected Text scoreText;
    private int gap = 60;

    /**
     * Constructor method for the EndSLide object. Creates an EndSlide object displaying score and exit button.
     * @param width width of the EndSlide
     * @param height height of the EndSlide
     */
    public EndSlide(float width, float height) 
    {
        //Setting variables of this, which is the rectangle that is the background of the main menu

        super(new Vector2f(width, height));

        this.setPosition(0, 0);
        this.setFillColor(new Color(0, 0, 0));

        isOpen = true;

        //Initializing the exit button
        exit = new RectangleShape(buttonSize);
        exit.setPosition(width/2 - buttonSize.x/2, height - buttonSize.y * 4);
        exit.setFillColor(new Color(128, 128, 128));

        font = new Font();
        Loader.loadPathToFont(font, "BoringGame/pixelated.ttf");

        exitText = new Text("EXIT GAME", font);
        exitText.setScale(2, 2);

        //Initializing the scoreboard button
        scoreboard = new RectangleShape(buttonSize);
        scoreboard.setPosition(width/2 - buttonSize.x/2, exit.getPosition().y + buttonSize.y * 2);
        scoreboard.setFillColor(new Color(128, 128, 128));

        scoreboardText = new Text("SCOREBOARD", font);
        scoreboardText.setScale(2, 2);


        youLost = new Text("YOU LOST", font);
        youLost.setScale(2, 2);

        youWin = new Text("YOU WIN!", font);
        youWin.setScale(2, 2);

        scoreText = new Text("YOU SCORED:", font);
        scoreText.setScale(2, 2);

        scoreDigitsText = new Text("" + score, font);
        scoreDigitsText.setScale(2, 2);

        youWin.setPosition(width/2 - youWin.getGlobalBounds().width/2, 0 + buttonSize.y);
        youLost.setPosition(width/2 - youLost.getGlobalBounds().width/2, 0 + buttonSize.y);
        scoreText.setPosition(width/2 - scoreText.getGlobalBounds().width/2, youWin.getPosition().y + youWin.getGlobalBounds().height + buttonSize.y/2);
        scoreDigitsText.setPosition(width/2 - scoreDigitsText.getGlobalBounds().width/2, scoreText.getPosition().y + scoreText.getGlobalBounds().height + buttonSize.y/2);

        exitText.setPosition(exit.getPosition().x + buttonSize.x/2 - exitText.getGlobalBounds().width/2, exit.getPosition().y);
        scoreboardText.setPosition(scoreboard.getPosition().x + buttonSize.x/2 - scoreboardText.getGlobalBounds().width/2, scoreboard.getPosition().y);
    }

    /**
     * This function checks if exit button have been pressed,
     * If yes, act accordingly
     */
    public boolean isExitClicked(float mouseX, float mouseY) 
    {
        //Checks if the exit button has been clicked
        if(mouseX > exit.getPosition().x && mouseX < exit.getPosition().x + buttonSize.x && mouseY > exit.getPosition().y && mouseY < exit.getPosition().y + buttonSize.y) 
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * This function checks if scorevoard button has been pressed,
     * If yes, act accordingly
     */
    public boolean isScoreboardClicked(float mouseX, float mouseY) 
    {
        //Checks if the exit button has been clicked
        if(mouseX > scoreboard.getPosition().x && mouseX < scoreboard.getPosition().x + buttonSize.x && mouseY > scoreboard.getPosition().y && mouseY < scoreboard.getPosition().y + buttonSize.y) 
        {
            return true;
        }
        else
        {
            return false;
        }

    }
}