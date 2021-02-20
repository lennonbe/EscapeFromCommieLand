package BoringGame;

import java.util.Observable;
import java.util.Observer;
import org.jsfml.window.*;
import org.jsfml.window.event.*;

import BoringGame.Clock;

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

public class BackroundTrees extends RectangleShape implements Observer
{
    //The texture and RectangleShape objects needed to draw a field on a rectangle
    private Texture backroundTexture = new Texture();
    private int counter = 1;
    protected boolean seasonChange = false;
    protected boolean winter = false;
    protected Clock clock = new Clock(3000);

    public BackroundTrees()
    {
        Loader.loadPathToRectangle("BoringGame/AllResources/PlayAreaVariations", "PlayArea1.png", this, backroundTexture);
        this.clock.addObserver(this);
    }

    public void update(Observable clock, Object o)
    {
        if(seasonChange == true && winter == true)
        {
            if(counter == 1)
            {
                Loader.loadPathToRectangle("BoringGame/AllResources/PlayAreaVariations", "PlayArea3.png", this, backroundTexture);
                counter++;
            }
            else if(counter == 2)
            {
                Loader.loadPathToRectangle("BoringGame/AllResources/PlayAreaVariations", "PlayArea2.png", this, backroundTexture);
                counter++;
            }
            else if(counter == 3)
            {
                Loader.loadPathToRectangle("BoringGame/AllResources/PlayAreaVariations", "PlayArea1.png", this, backroundTexture);
                counter = 1;
                winter = false;
                seasonChange = false;
            }
        }
        else if(seasonChange == true && winter == false)
        {
            if(counter == 1)
            {
                Loader.loadPathToRectangle("BoringGame/AllResources/PlayAreaVariations", "PlayArea2.png", this, backroundTexture);
                counter++;
            }
            else if(counter == 2)
            {
                Loader.loadPathToRectangle("BoringGame/AllResources/PlayAreaVariations", "PlayArea3.png", this, backroundTexture);
                counter++;
            }
            else if(counter == 3)
            {
                Loader.loadPathToRectangle("BoringGame/AllResources/PlayAreaVariations", "PlayArea4.png", this, backroundTexture);
                counter = 1;
                winter = true;
                seasonChange = false;
            }
        }
    }
}