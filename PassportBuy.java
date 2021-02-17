package BoringGame;

import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.concurrent.TimeUnit;
import java.util.Observable;
import java.util.Observer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class PassportBuy extends RectangleShape
{
    private Texture texture = new Texture();

    /**
     * Constructor for passportBuy object, an icon which allows the player to buy a passport which wins the game
     * @param size size of the passport
     * @param xPosition passport X position on window
     * @param yPosition passport Y position on window
     */
    public PassportBuy(Vector2f size, float xPosition, float yPosition)
    {
        super(size);

        this.setPosition(xPosition, yPosition);
        
        Loader.loadPathToRectangle("BoringGame/AllResources", "Passport.png", this, texture);
    }

    /**
     * Checks if the mouse is currently clicked or not.
     * @param window current window we sish to check for clicks on
     * @return boolean which represents the click (true if clicked false otherwise)
     */
    public boolean isClicked(Window window)
    {
        boolean flag = false;

        if(Mouse.isButtonPressed(Mouse.Button.LEFT))
        {
            if(Mouse.getPosition(window).x >= this.getPosition().x && Mouse.getPosition(window).x <= this.getPosition().x + this.getSize().x && Mouse.getPosition(window).y >= this.getPosition().y && Mouse.getPosition(window).y <= this.getPosition().y + this.getSize().y)
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
}