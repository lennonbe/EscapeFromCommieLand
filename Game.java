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

/**
 * Class which represents the Game.
 * This class is what will be called in the Driver class, and should hold all methods needed to 
 * load, spawn and play the game.
 */
public class Game {

    //Setting the environment variables
    private final int height = 864;//900; this is multiplied by 3
    private final int width = 768;//900; this is multiplied by 3
    private final int speed = 10;

    private final Vector2f scale = new Vector2f((float)1.5, (float)1.5);
    private final Vector2f fieldSize = new Vector2f(96, 96); // prev value was 135
    private final Vector2f windowSize = new Vector2f(width, height);

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

    //The Farmer objects needed to draw a Farmer on a the Window
    private Farmer farmer = new Farmer();

    //The texture and RectangleShape objects needed to draw a house/shop on a rectangle
    private Shop shop = new Shop(fieldSize);

    //The Fields object array needed to draw all the fields around the shop
    private Fields [] farmFields = new Fields[25];

    //Variables for keeping track of the menu status
    private boolean menuOpen = false;
    private BuyMenu menu;

    //ResourceMenu testing::::
    private ResourceMenu resourceMenu;

    //Variables to keep track of quantity of seeds of each type
    private int [] numSeeds = new int []{0, 0, 0, 0}; 

    /**
     * Constructor for the game. Loads the window, adds all needed 
     * objects such as sprites and rectangles and sets their initial positions.
     */
    public Game()
    {
        //Create the window
        window.create(new VideoMode(width, height), "Escape from CommieLand!");
        window.setSize(new Vector2i(width, height));
       

        //Creates the BuyMenu based on window size.
        menu = new BuyMenu(new Vector2f(300,120), window);

        resourceMenu = new ResourceMenu();

        //Limit the framerate
        window.setFramerateLimit(60);

        loadPathToRectangle("BoringGame", "PlayAreaSquare3t.png", backround, backroundTexture);

        int x = 0;
        int y = 0;
        for(int i = 0; i < farmFields.length; i++) 
        {
            farmFields[i] = new Fields(fieldSize);
            farmFields[i].setPosition(width/2 + (((fieldSizeInt)*(x-2))) - fieldSizeInt/2, height/2 + ((fieldSizeInt)*(y-2)) - fieldSizeInt/2);

            //Calculates the x and y position on a 2 dimentional matrix
            if(x == 4) 
            {
                x = 0;
                y++;
            } 
            else 
            {
                x++;
            }
        }

        shop.setPosition(width/2 - fieldSizeInt/2, height/2 - fieldSizeInt/2);

        backround.setPosition(0, 0);
        backround.setSize(windowSize);
        SoundEffect.init();
        SoundEffect.volume = SoundEffect.Volume.LOW;  // un-mute
        SoundEffect.BGM.play();  //testing the audio play for 1 time
    }

    /**
     * Calculates the movement of the character.
     * Ensures the farmer doesn't collide with the shop,
     * and doesn't walk out of the window.
     */
    public void movement()
    {
        if(Keyboard.isKeyPressed(Keyboard.Key.D))
        {
            if(farmer.getCurrentSprite() == "Right-L.png")
            {
                farmer.setSprite("BoringGame/Sprites/Man/Animations", "Right-R.png");
            }
            else if(farmer.getCurrentSprite() == "Right-R.png")
            {
                farmer.setSprite("BoringGame/Sprites/Man/Animations", "Right-L.png");
            }
            else
            {
                farmer.setSprite("BoringGame/Sprites/Man/Animations", "Right-R.png");
            }
            
            if(farmer.getGlobalBounds().left + farmer.getGlobalBounds().width >= rightBorder)
            {
                farmer.move(0,0);
            }
            else if(farmer.getGlobalBounds().left + farmer.getGlobalBounds().width >= middleLeftBound && farmer.getGlobalBounds().left + farmer.getGlobalBounds().width <= middleRightBound && ((farmer.getGlobalBounds().top + farmer.getGlobalBounds().height < middleLowerBound && farmer.getGlobalBounds().top + farmer.getGlobalBounds().height > middleUpperBound) || (farmer.getGlobalBounds().top < middleLowerBound && farmer.getGlobalBounds().top > middleUpperBound)))
            {
                farmer.move(0,0);
            }
            else
            {
                if(farmer.getGlobalBounds().left + farmer.getGlobalBounds().width + speed > middleLeftBound && farmer.getGlobalBounds().left + farmer.getGlobalBounds().width + speed < middleRightBound &&((farmer.getGlobalBounds().top + farmer.getGlobalBounds().height < middleLowerBound && farmer.getGlobalBounds().top + farmer.getGlobalBounds().height > middleUpperBound) || (farmer.getGlobalBounds().top < middleLowerBound && farmer.getGlobalBounds().top > middleUpperBound)))
                {
                    farmer.move((Math.abs(middleLeftBound - (farmer.getGlobalBounds().left + farmer.getGlobalBounds().width))), 0);
                }
                else
                {
                    farmer.move(speed,0);
                }  
            }
        }
        else if(Keyboard.isKeyPressed(Keyboard.Key.A))
        {
            if(farmer.getCurrentSprite() == "Left-L.png")
            {
                farmer.setSprite("BoringGame/Sprites/Man/Animations", "Left-R.png");
            }
            else if(farmer.getCurrentSprite() == "Left-R.png")
            {
                farmer.setSprite("BoringGame/Sprites/Man/Animations", "Left-L.png");
            }
            else
            {
                farmer.setSprite("BoringGame/Sprites/Man/Animations", "Left-R.png");
            }

            if(farmer.getGlobalBounds().left <= leftBorder)
            {
                farmer.move(0,0);
            }
            else if(farmer.getGlobalBounds().left <= middleRightBound && farmer.getGlobalBounds().left >= middleLeftBound && ((farmer.getGlobalBounds().top + farmer.getGlobalBounds().height < middleLowerBound && farmer.getGlobalBounds().top + farmer.getGlobalBounds().height > middleUpperBound) || (farmer.getGlobalBounds().top < middleLowerBound && farmer.getGlobalBounds().top > middleUpperBound)))
            {
                farmer.move(0,0);
            }
            else
            {
                
                if(farmer.getGlobalBounds().left - speed < middleRightBound && farmer.getGlobalBounds().left - speed > middleLeftBound && ((farmer.getGlobalBounds().top + farmer.getGlobalBounds().height < middleLowerBound && farmer.getGlobalBounds().top + farmer.getGlobalBounds().height > middleUpperBound) || (farmer.getGlobalBounds().top < middleLowerBound && farmer.getGlobalBounds().top > middleUpperBound)))
                {
                    farmer.move(-(Math.abs(middleRightBound - farmer.getGlobalBounds().left)), 0);
                }
                else
                {
                    farmer.move(-speed,0);
                }
            }
        }
        else if(Keyboard.isKeyPressed(Keyboard.Key.S))
        {
            if(farmer.getCurrentSprite() == "Forward-L.png")
            {
                farmer.setSprite("BoringGame/Sprites/Man/Animations", "Forward-R.png");
            }
            else if(farmer.getCurrentSprite() == "Forward-R.png")
            {
                farmer.setSprite("BoringGame/Sprites/Man/Animations", "Forward-L.png");
            }
            else
            {
                farmer.setSprite("BoringGame/Sprites/Man/Animations", "Forward-R.png");
            }

            if(farmer.getGlobalBounds().top + farmer.getGlobalBounds().height >= bottomBorder)
            {
                farmer.move(0,0);
            }
            else if(farmer.getGlobalBounds().top + farmer.getGlobalBounds().height <= middleLowerBound && farmer.getGlobalBounds().top + farmer.getGlobalBounds().height >= middleUpperBound && ((farmer.getGlobalBounds().left + farmer.getGlobalBounds().width < middleRightBound && farmer.getGlobalBounds().left + farmer.getGlobalBounds().width > middleLeftBound) || (farmer.getGlobalBounds().left < middleRightBound && farmer.getGlobalBounds().left > middleLeftBound)))
            {
                farmer.move(0,0);
            }
            else
            {
                if(farmer.getGlobalBounds().top + farmer.getGlobalBounds().height + speed > middleUpperBound && farmer.getGlobalBounds().top + farmer.getGlobalBounds().height + speed < middleLowerBound && ((farmer.getGlobalBounds().left + farmer.getGlobalBounds().width < middleRightBound && farmer.getGlobalBounds().left + farmer.getGlobalBounds().width > middleLeftBound) || (farmer.getGlobalBounds().left < middleRightBound && farmer.getGlobalBounds().left > middleLeftBound)))
                {
                    farmer.move(0,(Math.abs(middleUpperBound - (farmer.getGlobalBounds().top + farmer.getGlobalBounds().height))));
                }
                else if(farmer.getGlobalBounds().top + farmer.getGlobalBounds().height + speed > bottomBorder)
                {
                    farmer.move(0,(Math.abs(bottomBorder - (farmer.getGlobalBounds().top + farmer.getGlobalBounds().height))));
                }
                else
                {
                    farmer.move(0,speed);
                }
            }
        }
        else if(Keyboard.isKeyPressed(Keyboard.Key.W))
        {
            if(farmer.getCurrentSprite() == "Back-L.png")
            {
                farmer.setSprite("BoringGame/Sprites/Man/Animations", "Back-R.png");
            }
            else if(farmer.getCurrentSprite() == "Back-R.png")
            {
                farmer.setSprite("BoringGame/Sprites/Man/Animations", "Back-L.png");
            }
            else
            {
                farmer.setSprite("BoringGame/Sprites/Man/Animations", "Back-R.png");
            }

            if(farmer.getGlobalBounds().top <= topBorder)
            {
                farmer.move(0,0);
            }
            else if(farmer.getGlobalBounds().top >= middleUpperBound && farmer.getGlobalBounds().top <= middleLowerBound && ((farmer.getGlobalBounds().left + farmer.getGlobalBounds().width < middleRightBound && farmer.getGlobalBounds().left + farmer.getGlobalBounds().width > middleLeftBound) || (farmer.getGlobalBounds().left < middleRightBound && farmer.getGlobalBounds().left > middleLeftBound)))
            {
                farmer.move(0,0);
            }
            else
            {
                if(farmer.getGlobalBounds().top - speed < middleLowerBound && farmer.getGlobalBounds().top - speed > middleUpperBound && ((farmer.getGlobalBounds().left + farmer.getGlobalBounds().width < middleRightBound && farmer.getGlobalBounds().left + farmer.getGlobalBounds().width > middleLeftBound) || (farmer.getGlobalBounds().left < middleRightBound && farmer.getGlobalBounds().left > middleLeftBound)))
                {
                    farmer.move(0,-Math.abs(middleLowerBound - farmer.getGlobalBounds().top));
                }
                else
                {
                    farmer.move(0,-speed);
                }
            }
        }
    }

    /**
     * Detects the clicks on the specific fields.
     */
    public void detectClicks()
    {
        if(menuOpen == false)
        {
            for(int i = 0; i < farmFields.length; i++)
            {
                if(farmFields[i].isClicked(window) && i != 12) // cause field 12 is behind the shop TODO: Remove field12 safely
                {
                    System.out.println("Field " + i + "is clicked");
                    System.out.println("Current field selected is " + farmFields[i].selectedField + " and the clickFlag is " + farmFields[i].clickFlag);
                    pause();
                }
            }
        }
    }

    /**
     * drawObjects method which is responsible for drawing all the objects,
     * this includes sprites, rectangles and the backround.
     */
    public void drawObjects()
    {
        //window.draw(backround);

        for(int i = 0; i < farmFields.length; i++)
        {
            window.draw(farmFields[i]);
        }

        window.draw(shop);
        window.draw(farmer);
        window.draw(backround);
        window.draw(resourceMenu);

        //Draws the resource icons
        RectangleShape[] arrayOfRectangles = resourceMenu.getRectangleArray();

        for(RectangleShape i : arrayOfRectangles) {
            window.draw(i);
        }

        //Draws the counters for each resource
        Text[] arrayOfText = resourceMenu.getCounter();

        for(Text t : arrayOfText) {
            window.draw(t);
        }

        /*
        Following code will be useful for idenfying when the player is clicking on a certain crop or on the house etc...
        Right now doesnt do much but still a good starting point. 
        */
        if(shop.isClicked(window) == true)
        {
            menuOpen = true;
            pause();
        }

        if(menuOpen == true)
        {
            window.draw(menu);

            for(int i = 0; i < 4; i++)
            {
                window.draw(menu.getRectangleArray()[i]);
            }

            window.draw(menu.getExitButton());

            if(menu.isExitClicked(window) == true)
            {
                menuOpen = false;
                pause();
            }
        }

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
            window.clear(new Color(50,20,20));

            this.movement();
            this.detectClicks();
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

            if(menu.buyVeg(window) != -1 && menuOpen == true)
            {
                int temp = menu.buyVeg(window);

                if(temp != -1)
                {
                    numSeeds[temp]++;

                    pause();

                    System.out.println("incrementing numSeeds " + temp + " value is " + numSeeds[temp]);
                    resourceMenu.increment(temp);
                }
            }
        }
    }

    /**
     * Slows down the game by sleeping for 1/4 of a second
     */
    public void pause()
    {
        try 
        {
            TimeUnit.MILLISECONDS.sleep(250); 
        } 
        catch (Exception e) 
        {
            //TODO: handle exception
        }
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
        
        try {
            BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);

        } catch (Exception e) {}

        try {
            texture.loadFromFile(path);
        } 
        catch (Exception e) {}

        rectangle.setTexture(texture);
    }
}
