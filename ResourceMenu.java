package BoringGame;

import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.io.*;

public class ResourceMenu extends RectangleShape {
    
    //Inherit attributes of the ResoureMenu are final
    private final static int width = 550;
    private final static int height = 90;
    private final Font font = new Font();
    private final int numberOfIcons = 5;
    private final int iconWidth = 50;
    private final int iconHeight = 50;
    private final int gap = 20;

    private Vector2f seedIconSize;
    private RectangleShape resuourceMenuRectange;
    private Texture resuourceMenuTexture;
    private RectangleShape[] seedIcons;
    private Texture[] seedIconsTexture;
    private RectangleShape[] numberDisplayBackground;
    private int[] resourceCounter;
    private Text[] counterText;

    //Initializes the resource menu rectangle 
    public ResourceMenu() {
        //Sets up this rectangle, so that it can be drawn with the right parameter
        super(new Vector2f(width, height));

        this.setPosition(0, 0);
        this.setFillColor(new Color(128,128,128));

        //Loads the font used for the counter
        try {
            font.loadFromFile(Paths.get("BoringGame/AmericanCaptain.ttf"));
        } catch(Exception e) { System.out.println("Error loading font"); }

        //Sets the size of each icon
        seedIconSize = new Vector2f(50, 50);

        //Initializes the arrays for the menu 
        seedIcons = new RectangleShape[numberOfIcons];
        seedIconsTexture = new Texture[numberOfIcons];
        numberDisplayBackground = new RectangleShape[numberOfIcons];
        counterText = new Text[numberOfIcons];
        
        //Sets up all images and the texts
        for(int i = 0; i < numberOfIcons; i++) {
            int iconXPosition = gap + (iconWidth*2) * i; 

            numberDisplayBackground[i] = new RectangleShape(new Vector2f(iconWidth, iconHeight));
            numberDisplayBackground[i].setPosition(iconXPosition + iconWidth, 20);
            numberDisplayBackground[i].setFillColor(new Color(0, 0, 0));
            
            seedIcons[i] = new RectangleShape();
            seedIconsTexture[i] = new Texture();
            
            seedIcons[i].setPosition(new Vector2f(iconXPosition, 20));
            seedIcons[i].setSize(seedIconSize);

            counterText[i] = new Text("0", font, 50);
            counterText[i].setPosition(iconXPosition + iconWidth, 20);
            
        }
            

        //Temporary solution ---- It'll be changes once we have the seed icons made
        loadPathToRectangle("BoringGame/Sprites/FruitVeg/Temp_seeds", "coffee.png", seedIcons[0], seedIconsTexture[0]);
        loadPathToRectangle("BoringGame/Sprites/FruitVeg/Temp_seeds", "seeds.png", seedIcons[1], seedIconsTexture[1]);
        loadPathToRectangle("BoringGame/Sprites/FruitVeg/Temp_seeds", "sesame.png", seedIcons[2], seedIconsTexture[2]);
        loadPathToRectangle("BoringGame/Sprites/FruitVeg/Temp_seeds", "soy.png", seedIcons[3], seedIconsTexture[3]);
        loadPathToRectangle("BoringGame/Sprites/FruitVeg/Temp_seeds", "dollar.png", seedIcons[4], seedIconsTexture[4]);
    }

    public RectangleShape[] getRectangleArray() {
        RectangleShape[] result = new RectangleShape[numberOfIcons*2];

        System.arraycopy(seedIcons, 0, result, 0, numberOfIcons);
        System.arraycopy(numberDisplayBackground, 0, result, numberOfIcons, numberOfIcons);

        return result;
    }

    public Text[] getCounter() {
        return counterText;
    }

    //THIS WILL BE USED ONCE THE RESOURCE SPRITE IS DONE
    public void loadPathToRectangle(String directory, String file, RectangleShape rectangle, Texture texture)
    {
        Path path = FileSystems.getDefault().getPath(directory, file);
        
        try {
            BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);

        } catch (Exception e) {}

        try {
            texture.loadFromFile(path);
        } 
        catch (Exception e) {}

        rectangle.setTexture(texture);
    }

    //Increments the resource of the given index 
    public void increment(int index) {
        int currentVal = Integer.parseInt(counterText[index].getString());
        counterText[index].setString(String.valueOf(currentVal + 1));
    }
}