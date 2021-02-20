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
    protected RectangleShape exit;
    protected Text exitText;
    protected Text youWin;
    protected Text youLost;
    private Boolean isOpen;
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
        exit.setPosition(width/2 - buttonSize.x/2, height/2 - buttonSize.y/2);
        exit.setFillColor(new Color(128, 128, 128));

        font = new Font();
        Loader.loadPathToFont(font, "BoringGame/pixelated.ttf");

        exitText = new Text("EXIT GAME", font);
        exitText.setScale(2, 2);

        youLost = new Text("YOU LOST", font);
        youLost.setScale(2, 2);

        youWin = new Text("YOU WIN!", font);
        youWin.setScale(2, 2);

        scoreText = new Text("YOU SCORED:" + score, font);
        scoreText.setScale(2, 2);

        scoreText.setPosition(exit.getPosition().x + exit.getSize().x/2 - scoreText.getGlobalBounds().width/2, exit.getPosition().y - gap - youWin.getGlobalBounds().height/2 - scoreText.getGlobalBounds().height/2);
        youWin.setPosition(exit.getPosition().x + exit.getSize().x/2 - youWin.getGlobalBounds().width/2, exit.getPosition().y - gap*3 - youWin.getGlobalBounds().height/2);
        youLost.setPosition(exit.getPosition().x + exit.getSize().x/2 - youLost.getGlobalBounds().width/2, exit.getPosition().y - gap*3 - youLost.getGlobalBounds().height/2);
        exitText.setPosition(exit.getPosition().x + buttonSize.x/2 - exitText.getGlobalBounds().width/2, exit.getPosition().y);
    }

    /**
     * This function checks if any of the buttons have been pressed,
     * If yes, act accordingly
     */
    public boolean isClicked(float mouseX, float mouseY) 
    {
        //Checks if the exit button has been clicked
        if(mouseX > exit.getPosition().x && mouseX < exit.getPosition().x + buttonSize.x &&
            mouseY > exit.getPosition().y && mouseY < exit.getPosition().y + buttonSize.y) 
        {
            return true;
        }

        return false;
    }
}