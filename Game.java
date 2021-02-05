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

/**
 * Class which represents the Game.
 * This class is what will be called in the Driver class, and should hold all methods needed to 
 * load, spawn and play the game.
 */
public class Game implements Observer
{

    //Setting the environment variables
    private final int height = 864;//900; this is multiplied by 3
    private final int width = 768;//900; this is multiplied by 3
    private final int speed = 10;

    private final Vector2f scale = new Vector2f((float)1.5, (float)1.5);
    private final Vector2f fieldSize = new Vector2f(96, 96); // prev value was 135
    private final Vector2f windowSize = new Vector2f(width, height);

    protected int fieldSizeInt = (int)fieldSize.x;

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
    protected Farmer farmer = new Farmer();

    //The texture and RectangleShape objects needed to draw a house/shop on a rectangle
    private Shop shop = new Shop(fieldSize);

    //The Fields object array needed to draw all the fields around the shop
    protected Fields [] farmFields = new Fields[25];

    //Variables for keeping track of the menu status
    //private boolean menuOpen = false;
    private BuyMenu menu;

    //ResourceMenu testing::::
    private ResourceMenu resourceMenu;
    private FamilyMenu familyMenu;

    //BuyCycle class testing:
    private BuyCycle buyCycle;

    //Clock formswitching seasons every x seconds
    private Clock seasonClock = new Clock(60000);

    //Time increasing values for seasons
    private double winter = 1.33;
    private double summer = 0.75;


    /**
     * Constructor for the game. Loads the window, adds all needed 
     * objects such as sprites and rectangles and sets their initial positions.
     */
    public Game()
    {
        //Create the window
        window.create(new VideoMode(width, height), "Escape from CommieLand!");
        window.setSize(new Vector2i(width, height));
       
        seasonClock.addObserver(this);

        //Creates the BuyMenu based on window size.
        menu = new BuyMenu(new Vector2f(300,240), window);

        resourceMenu = new ResourceMenu(menu);
        familyMenu = new FamilyMenu();

        //Limit the framerate
        window.setFramerateLimit(60);

        Loader.loadPathToRectangle("BoringGame", "PlayAreaSquare3t.png", backround, backroundTexture);

        int x = 0;
        int y = 0;
        for(int i = 0; i < farmFields.length; i++) 
        {
            farmFields[i] = new Fields(fieldSize, resourceMenu);
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

        buyCycle = new BuyCycle(farmFields, resourceMenu, menu, window, this);

        shop.setPosition(width/2 - fieldSizeInt/2, height/2 - fieldSizeInt/2);

        backround.setPosition(0, 0);
        backround.setSize(windowSize);
        SoundEffect.init();
        SoundEffect.volume = SoundEffect.Volume.LOW;  // un-mute
        SoundEffect.BGM2.loopPlay();  //testing the audio play for 1 time
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
                farmer.setSprite("BoringGame/AllResources/Man/Animations", "Right-R.png");
            }
            else if(farmer.getCurrentSprite() == "Right-R.png")
            {
                farmer.setSprite("BoringGame/AllResources/Man/Animations", "Right-L.png");
            }
            else
            {
                farmer.setSprite("BoringGame/AllResources/Man/Animations", "Right-R.png");
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
                farmer.setSprite("BoringGame/AllResources/Man/Animations", "Left-R.png");
            }
            else if(farmer.getCurrentSprite() == "Left-R.png")
            {
                farmer.setSprite("BoringGame/AllResources/Man/Animations", "Left-L.png");
            }
            else
            {
                farmer.setSprite("BoringGame/AllResources/Man/Animations", "Left-R.png");
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
                farmer.setSprite("BoringGame/AllResources/Man/Animations", "Forward-R.png");
            }
            else if(farmer.getCurrentSprite() == "Forward-R.png")
            {
                farmer.setSprite("BoringGame/AllResources/Man/Animations", "Forward-L.png");
            }
            else
            {
                farmer.setSprite("BoringGame/AllResources/Man/Animations", "Forward-R.png");
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
                farmer.setSprite("BoringGame/AllResources/Man/Animations", "Back-R.png");
            }
            else if(farmer.getCurrentSprite() == "Back-R.png")
            {
                farmer.setSprite("BoringGame/AllResources/Man/Animations", "Back-L.png");
            }
            else
            {
                farmer.setSprite("BoringGame/AllResources/Man/Animations", "Back-R.png");
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
     * drawObjects method which is responsible for drawing all the objects,
     * this includes sprites, rectangles and the backround.
     */
    public void drawObjects()
    {
        //Draws the fields
        for(int i = 0; i < farmFields.length; i++)
        {
            window.draw(farmFields[i]);
        }
        
        window.draw(shop);
        window.draw(farmer);
        window.draw(backround);
        window.draw(resourceMenu);
        window.draw(familyMenu);
        
        try {
            window.draw(familyMenu.getPopup());
        } catch (Exception e) {}
        window.draw(familyMenu.getText());
        
        //Draws the resource icons
        for(RectangleShape i : resourceMenu.getRectangleArray()) {
            window.draw(i);
        }
        
        //Draws the counters for each resource
        for(Text t : resourceMenu.getCounter()) {
            window.draw(t);
        }
        
        for(RectangleShape r : familyMenu.getRectangleArray()) {
            window.draw(r);
        }
        
        for(CircleShape c : familyMenu.getCircleShapeArray()) {
            window.draw(c);
        }
        
        /*
        Following code will be useful for idenfying when the player is clicking on a certain crop or on the house etc...
        Right now doesnt do much but still a good starting point. 
        */
        if(shop.isClicked(window) == true && menu.menuOpen == false)
        {
            SoundEffect.OPENINVENTORY.play();
            menu.menuOpen = true;
            pause();
        }
        
        if(menu.menuOpen == true)
        {
            window.draw(menu);
            
            for(int i = 0; i < 4; i++)
            {
                window.draw(menu.getVegArray()[i]);
            }

            for(int i = 0; i < 3; i++)
            {
                window.draw(menu.getUpgradeArray()[i]);
            }
            
            window.draw(menu.getExitButton());
            
            if(menu.isExitClicked(Mouse.getPosition(window).x, Mouse.getPosition(window).y) == true)
            {
                SoundEffect.OPENINVENTORY.play();
                menu.menuOpen = false;
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
            //Fill the window
            window.clear(new Color(50,20,20));
            
            this.movement();
            buyCycle.collectVeg();
            buyCycle.selectVegToGrowOnField();
            this.drawObjects();

            if(farmFields[0].selectedField == farmFields[12]) // TODO: Handle issue regarding field 12, ideally remove it from the class totally
            {
                farmFields[0].selectedField = null;
            }
            
            //Handle events
            for(Event event : window.pollEvents()) 
            {
                if(event.type == Event.Type.CLOSED) 
                {
                    //The user pressed the close button
                    window.close();
                }

                if(Mouse.isButtonPressed(Mouse.Button.LEFT))
                {
                    resourceMenu.selectIcon(Mouse.getPosition(window).x, Mouse.getPosition(window).y);
                    buyCycle.buyVeg(Mouse.getPosition(window).x, Mouse.getPosition(window).y);
                    buyCycle.buyUpgrade(Mouse.getPosition(window).x, Mouse.getPosition(window).y);
                }

                if(resourceMenu.getSelectedIndex() != -1 && farmFields[1].getSelectedField() != null)
                {
                    farmFields[1].getSelectedField().setGrowing(true);
                }
            }

            //System.out.println("Selected field is" + farmFields[0].selectedField);
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

        }
    }

    /**
     * Update method called every x seconds of the clock instance.
     * This changes the season, which speeds up or slows down the growth speed.
     */
    public void update(Observable clock, Object o)
    {
        if(farmFields[1].isWinter == true)
        {
            for(int j = 0; j < farmFields.length; j++)
            {
                for(int i = 0; i < farmFields[j].clockArr.length; i++)
                {
                    int temp = farmFields[j].clockArr[i].time.getDelay();
                    farmFields[j].clockArr[i].time.setDelay((int)(temp * summer));
                }
    
                farmFields[j].isWinter = false;
            }
        }
        else
        {
            for(int j = 0; j < farmFields.length; j++)
            {
                for(int i = 0; i < farmFields[j].clockArr.length; i++)
                {
                    int temp = farmFields[j].clockArr[i].time.getDelay();
                    farmFields[j].clockArr[i].time.setDelay((int)(temp * winter));
                }
                
                farmFields[j].isWinter = true;
            }
        }
        
        SoundEffect.FAILPRISON.play();
        System.out.println("SEASON HAS CHANGED, WINTER IS " + farmFields[1].isWinter);
    }
}
