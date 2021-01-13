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

public class BuyMenu extends RectangleShape
{
    //private int size;
    private float xLocation, yLocation;
    private float xCauliflower, xTomato, xSweetCorn, xCarrot, yCauliflower, yTomato, ySweetCorn, yCarrot;
    private Texture menuTexture;

    public BuyMenu(Vector2f size, RenderWindow window)
    {
        super(size);
        this.setPosition(window.getSize().x/2 - size.x/2, window.getSize().y/2 - size.y/2);
        //System.out.println(window.getSize().x + " " + window.getSize().y);
        this.setFillColor(new Color(128,128,128));
    }
}

/*RectangleShape(Vector2f size)

public MyPlanet(double distance, double angle, double diameter, String col, MySun sun, double speed, int moons)
{
    super(distance, angle, diameter, col);
    sunDistance = sun.distance;
    sunAngle = sun.angle;
    rotationSpeed = speed;

    moonsArr = new MyMoon[moons];
    
}*/