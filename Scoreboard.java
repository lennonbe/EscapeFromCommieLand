package BoringGame;

import org.jsfml.system.Vector2f;
import org.jsfml.graphics.*;

/**
 * This class represents the endSlide that is the first thing that the user will see once the game is booted
 * There is one button:
 *      Exit        - Exits the game 
 */
public class Scoreboard extends RectangleShape 
{
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

    public Scoreboard(float width, float height) 
    {
        super(new Vector2f(width, height));
    }
}