package BoringGame;

import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.io.*;

/**
 * Class which represents the Game.
 * This class is what will be called in the Driver class, and should hold all methods needed to 
 * load, spawn and play the game.
 */
public class Game 
{
    //Setting the environment variables
    private final int heigth = 900;
    private final int width = 900;
    private final int speed = 10;

    private Vector2f scale = new Vector2f((float)1.5, (float)1.5);
    private Vector2f fieldSize = new Vector2f(135, 135);
    private Vector2f windowSize = new Vector2f(width, height);

    private int fieldSizeInt = (int)fieldSize.x;

    private int lowerBound = height/2 + 3 * fieldSizeInt/2;
    private int upperBound = height/2 - 3 * fieldSizeInt/2;
    private int leftBound = width/2 - 3 * fieldSizeInt/2;
    private int rightBound = width/2 + 3 * fieldSizeInt/2;

    private int middleLowerBound = height/2 + fieldSizeInt/2;
    private int middleUpperBound = height/2 - fieldSizeInt/2;
    private int middleLeftBound = width/2 - fieldSizeInt/2;
    private int middleRightBound = width/2 + fieldSizeInt/2;

    //The border coordinates of the totalitty of the fields drawn.
    private int topBorder = height/2 - 5 * fieldSizeInt/2;
    private int bottomBorder = height/2 + 5 * fieldSizeInt/2;
    private int leftBorder = width/2 - 5 * fieldSizeInt/2;
    private int rightBorder = width/2 + 5 * fieldSizeInt/2;

    //The window which will display the game
    private RenderWindow window = new RenderWindow();
    
    //The texture and RectangleShape objects needed to draw a field on a rectangle
    private Texture backroundTexture = new Texture();
    private RectangleShape backround = new RectangleShape();

    //The texture and Sprite objects needed to draw a field on a rectangle
    private Texture farmerTexture = new Texture();
    private Sprite farmerSprite = new Sprite();

    //The texture and RectangleShape objects needed to draw a house/shop on a rectangle
    private Texture houseTexture = new Texture();
    private RectangleShape house = new RectangleShape(fieldSize);

    //The texture and RectangleShape objects arrays needed to draw all the fields around the shop
    private Texture [] fieldsTextures = new Texture[24];
    private RectangleShape [] fieldsRectangles = new RectangleShape[24];

    /**
     * Constructor for the game. Loads the window, adds all needed 
     * objects such as sprites and rectangles and sets their initial positions.
     */
    public Game()
    {
        //Create the window
        window.create(new VideoMode(width, height), "Escape from CommieLand!");

        //Limit the framerate
        window.setFramerateLimit(60);

        loadPathToSprite("BoringGame", "Man_Neutral.png", farmerSprite, farmerTexture);
        loadPathToRectangle("BoringGame", "Forest.png", backround, backroundTexture);
        loadPathToRectangle("BoringGame", "Shop.png", house, houseTexture);

        int x = 0;
        int y = 0;
        for(int i = 0; i < 25; i++) {
            
            //Initiates all the fields
            fieldsRectangles[i] = new RectangleShape();
            fieldsTextures[i] = new Texture();
            
            //Loads the textures
            loadPathToRectangle("BoringGame", "EmptyField.png", fieldsRectangles[i], fieldsTextures[i]);

            //Sets the size of each field
            fieldsRectangles[i].setSize(fieldSize);
            
            //Sets the position of each field
            fieldsRectangles[i].setPosition(width/2 + (((fieldSizeInt)*(x-2))) - fieldSizeInt/2,
                                            heigth/2 + ((fieldSizeInt)*(y-2)) - fieldSizeInt/2);

            //Calculates the x and y position on a 2 dimentional matrix
            if(x == 4) {
                x = 0;
                y++;
            } else {
                x++;
            }
        }

        farmerSprite.setPosition(100, 100);
        farmerSprite.setScale(scale);
        farmerSprite.scale(scale);

        house.setPosition(width/2 - fieldSizeInt/2, height/2 - fieldSizeInt/2);
        house.setSize(fieldSize);

        backround.setPosition(0, 0);
        backround.setSize(windowSize);
    }

    /**
     * Calculates the movement of the character.
     * Ensures the farner doesn't collide with the shop,
     * and doesn't walk out of the window.
     */
    public void movement()
    {
        if(Keyboard.isKeyPressed(Keyboard.Key.D)) {
            if(farmerSprite.getGlobalBounds().left + farmerSprite.getGlobalBounds().width + speed > middleLeftBound && 
                farmerSprite.getGlobalBounds().left + farmerSprite.getGlobalBounds().width + speed < middleRightBound && ((
                farmerSprite.getGlobalBounds().top + farmerSprite.getGlobalBounds().height < middleLowerBound && 
                farmerSprite.getGlobalBounds().top + farmerSprite.getGlobalBounds().height > middleUpperBound) || (
                farmerSprite.getGlobalBounds().top < middleLowerBound && 
                farmerSprite.getGlobalBounds().top > middleUpperBound)))
            {
                farmerSprite.move((Math.abs(middleLeftBound - (farmerSprite.getGlobalBounds().left + farmerSprite.getGlobalBounds().width))), 0);
            } else {
                farmerSprite.move(speed,0);
            }
        } else if(Keyboard.isKeyPressed(Keyboard.Key.A)) {
            if(farmerSprite.getGlobalBounds().left - speed < middleRightBound && 
                farmerSprite.getGlobalBounds().left - speed > middleLeftBound && ((
                farmerSprite.getGlobalBounds().top + farmerSprite.getGlobalBounds().height < middleLowerBound && 
                farmerSprite.getGlobalBounds().top + farmerSprite.getGlobalBounds().height > middleUpperBound) || (
                farmerSprite.getGlobalBounds().top < middleLowerBound && 
                farmerSprite.getGlobalBounds().top > middleUpperBound)))
            {
                farmerSprite.move(-(Math.abs(middleRightBound - farmerSprite.getGlobalBounds().left)), 0);
            } else {
                farmerSprite.move(-speed,0);
            }
        } else if(Keyboard.isKeyPressed(Keyboard.Key.S)) {
            if(farmerSprite.getGlobalBounds().top + farmerSprite.getGlobalBounds().height + speed > middleUpperBound && 
                farmerSprite.getGlobalBounds().top + farmerSprite.getGlobalBounds().height + speed < middleLowerBound && ((
                farmerSprite.getGlobalBounds().left + farmerSprite.getGlobalBounds().width < middleRightBound && 
                farmerSprite.getGlobalBounds().left + farmerSprite.getGlobalBounds().width > middleLeftBound) || (
                farmerSprite.getGlobalBounds().left < middleRightBound && 
                farmerSprite.getGlobalBounds().left > middleLeftBound)))
            {
                farmerSprite.move(0,(Math.abs(middleUpperBound - (farmerSprite.getGlobalBounds().top + farmerSprite.getGlobalBounds().height))));
            } else {
                farmerSprite.move(0,speed);
            }
        } else if(Keyboard.isKeyPressed(Keyboard.Key.W)) {
            if(farmerSprite.getGlobalBounds().top - speed < middleLowerBound && 
                farmerSprite.getGlobalBounds().top - speed > middleUpperBound && ((
                farmerSprite.getGlobalBounds().left + farmerSprite.getGlobalBounds().width < middleRightBound && 
                farmerSprite.getGlobalBounds().left + farmerSprite.getGlobalBounds().width > middleLeftBound) || (
                farmerSprite.getGlobalBounds().left < middleRightBound && 
                farmerSprite.getGlobalBounds().left > middleLeftBound)))
            {
                farmerSprite.move(0,-Math.abs(middleLowerBound - farmerSprite.getGlobalBounds().top));
            } else {
                farmerSprite.move(0,-speed);
            }
        }
    }

    /**
     * drawObjects method which is responsible for drawing all the objects,
     * this includes sprites, rectangles and the backround.
     */
    public void drawObjects()
    {
        window.draw(backround);

        for(int i = 0; i < 24; i++)
        {
            window.draw(fieldsRectangles[i]);
        }

        window.draw(house);
        window.draw(farmerSprite);
        window.display();
    }

    /**
     * Method which contains the while loop and calls all the other methods responsible for running the game (i.e movement, drawObjects, etc...)
     */
    public void playGame()
    {
        while(window.isOpen()) 
        {
            //Fill the window with red
            window.clear(Color.RED);

            this.movement();

            /*
            Following code will be useful for idenfying when the player is clicking on a certain crop or on the house etc...
            Right now doesnt do much but still a good starting point. 
            */
            if(Mouse.isButtonPressed(Mouse.Button.LEFT))
            {
                if(Mouse.getPosition(window).x >= house.getPosition().x && Mouse.getPosition(window).x <= house.getPosition().x + house.getSize().x && Mouse.getPosition(window).y >= house.getPosition().y && Mouse.getPosition(window).y <= house.getPosition().y + house.getSize().y)
                {
                    System.out.println("Test successful!");
                }
            }

            this.drawObjects();

            //Handle events
            for(Event event : window.pollEvents()) 
            {
                if(event.type == Event.Type.CLOSED) 
                {
                //The user pressed the close button
                window.close();
                }
            }
        }
    }

    /**
     * Method which allows to loadn in .PNG files into sprites
     * @param directory the directory of the file you wish to load
     * @param file the file you wish to load
     * @param sprite the sprite you wish to have this file drawn on
     * @param texture a needed texture for the sprite, making it drawable
     */
    public void loadPathToSprite(String directory, String file, Sprite sprite, Texture texture)
    {
        Path path = FileSystems.getDefault().getPath(directory, file);
        
        try 
        {
            BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);

        } catch (Exception e) {}

        try 
        {
            texture.loadFromFile(path);
        } 
        catch (Exception e) {}

        sprite.setTexture(texture);
    }

    /**
     * Method which allows to loadn in .PNG files into sprites
     * @param directory the directory of the file you wish to load
     * @param file the file you wish to load
     * @param rectangle the rectangle you wish to have this file drawn on
     * @param texture a needed texture for the rectangle, making it drawable
     */
    public void loadPathToRectangle(String directory, String file, RectangleShape rectangle, Texture texture)
    {
        Path path = FileSystems.getDefault().getPath(directory, file);
        
        try 
        {
            BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);

        } catch (Exception e) {}

        try 
        {
            texture.loadFromFile(path);
        } 
        catch (Exception e) {}

        rectangle.setTexture(texture);
    }
}
