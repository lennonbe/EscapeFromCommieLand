package BoringGame;

import java.util.Observable;
import java.util.Observer;
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


public class Shop extends RectangleShape
{
    private Texture fieldTexture = new Texture();

    /**
     * Constructor method for the shop.
     * @param size size of the shop
     */
    public Shop(Vector2f size)
    {
        super(size);

        Loader.loadPathToRectangle("BoringGame/AllResources", "NewShop.png", this, fieldTexture);
    }

    /**
     * Checks if a square/rectangle has been clicked.
     * @param rectangle the rectangle in question
     * @return a boolean which is true if its clicked and false otherwise
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