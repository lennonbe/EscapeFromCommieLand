package BoringGame;

import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
//import org.jsfml.system.Clock;
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
    private MainMenu startingMenu;
    
    //The texture and RectangleShape objects needed to draw a field on a rectangle
    private BackroundTrees backround = new BackroundTrees();

    //The Farmer objects needed to draw a Farmer on a the Window
    protected Farmer farmer = new Farmer();

    //The texture and RectangleShape objects needed to draw a house/shop on a rectangle
    private Shop shop = new Shop(fieldSize);

    //New passport buying button
    protected int passportValue = 3;

    //Boolean to set if the game is finished
    private boolean victory = false;
    private boolean scoreRecorded = false;
    private boolean scoreboardOpen = false;
    private boolean endSlideBool = false;

    //EndSlide for when game is beaten
    private EndSlide endSlide;

    //Scoreboard slide
    private Scoreboard scoreboard;

    //Fields used for growing
    protected Fields [][] farmFields = new Fields[5][5];

    //Variables for keeping track of the menu status
    private BuyMenu menu;

    //ResourceMenu testing::::
    private ResourceMenu resourceMenu;
    private FamilyMenu familyMenu;
    private DistanceMarker distanceMarker;

    //BuyCycle class testing:
    private BuyCycle buyCycle;

    //Clock formswitching seasons every x seconds
    private Clock seasonClock = new Clock(60000);

    //Time increasing values for seasons
    private double winter = 1.33;
    private double summer = 0.75;

    //Text for timer
    private Text elapsedTime = new Text();
    private Font font = new Font();
    private long startTime;
    private long currentTime;
    private long displayTime = 0;

    private long totalSeconds;
    private long currentSecond;
    private long totalMinutes;

    /**
     * Constructor for the game. Loads the window, adds all needed 
     * objects such as sprites and rectangles and sets their initial positions.
     */
    public Game()
    {
        //Create the window
        window.create(new VideoMode(width, height), "Escape from CommieLand!");
        window.setSize(new Vector2i(width, height));

        startingMenu = new MainMenu(width, height);
        endSlide = new EndSlide(width, height);
        scoreboard = new Scoreboard(width, height);
       
        seasonClock.addObserver(this);

        //Creates the BuyMenu based on window size.
        menu = new BuyMenu(new Vector2f(300,240), window);

        resourceMenu = new ResourceMenu(menu);
        familyMenu = new FamilyMenu();
        distanceMarker = new DistanceMarker(window, this);

        //Coding the elapsedTime clock
        Loader.loadPathToFont(font, "BoringGame/Russian.ttf");
        elapsedTime.setFont(font);
        elapsedTime.setString("00:00");
        elapsedTime.setPosition(new Vector2f(660, 25));

        //Limit the framerate
        window.setFramerateLimit(60);
        
        //Loader.loadPathToRectangle("BoringGame", "PlayAreaSquare3t.png", backround, backroundTexture);
        
        int positionX = width/2 + (fieldSizeInt*(-2)) - fieldSizeInt/2;
        for(int i = 0; i < 5; i++)
        {
            int positionY = height/2 + ((fieldSizeInt)*(-2)) - fieldSizeInt/2;
            for(int j = 0; j < 5; j++)
            {
                farmFields[i][j] = new Fields(fieldSize, resourceMenu);
                farmFields[i][j].setPosition(positionX, positionY);
                
                if(i == 0 || i == 4 || j == 0 || j == 4)
                {
                    farmFields[i][j].loadPathToRectangle("BoringGame/AllResources/PurchaseTiles", "Purchase1.png");
                    farmFields[i][j].unlocked = false;
                }
                
                positionY += fieldSizeInt;
            }
            
            positionX += fieldSizeInt;
        }
        
        buyCycle = new BuyCycle(farmFields, resourceMenu, menu, familyMenu, window, this);
        
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
     * 
     * Also includes the animations of the movement, with the farmer facing different directions as he moves.
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
        distanceMarker.drawBounds();
    }
    
    /**
     * drawObjects method which is responsible for drawing all the objects,
     * this includes sprites, rectangles and the backround.
     */
    public void drawObjects()
    {
        if(!startingMenu.getIsOpen()) {

            //Draws the fields
            for(int i = 0; i < 5; i++)
            {
                for(int j = 0; j < 5; j++)
                {
                    window.draw(farmFields[i][j]);
                }
            }
            
            //Drawing all the essenetial elements of the game (farmer, shop, backround, etc.)
            window.draw(shop);
            window.draw(farmer);
            window.draw(backround);
            window.draw(resourceMenu);
            window.draw(familyMenu);
            
            try 
            {
                for(CircleShape c : distanceMarker.getPointers()) 
                {
                    window.draw(c);
                }
                window.draw(familyMenu.getPopup());
            } 
            catch (Exception e) 
            {

            }
            
            //Draws the resource icons
            for(RectangleShape i : resourceMenu.getRectangleArray(1)) {
                //simply change getRectangle Array by not giving it an int and it does milosz's version
                window.draw(i);
            }
        
            //Draws the counters for each resource
            for(Text t : resourceMenu.getCounter()) {
                window.draw(t);
            }
        
            //Draw the season icon
            window.draw(resourceMenu.seasonIcon);
            
            //Drawing the entire family menu
            for(RectangleShape r : familyMenu.getRectangleArray()) 
            {
                window.draw(r);
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
            
            //When the menu is open display all of the menus items e.g seeds (unlocked and blacked out), upgrades and the buy menu itself
            if(menu.menuOpen == true)
            {
                window.draw(menu);
                
                //Seeds array drawing
                for(int i = 0; i < menu.getVegArray().length; i++)
                {
                    window.draw(menu.getVegArray()[i]);
                }
                
                //Upgrades drawing
                for(int i = 0; i < menu.getUpgradeArray().length; i++)
                {
                    window.draw(menu.getUpgradeArray()[i]);
                }
                
                //Drawing the text displaying the prices
                for(int i = 0; i < menu.counterText.length; i++)
                {
                    window.draw(menu.counterText[i]);
                }
                
                //Drawing the passport and its pricing
                window.draw(menu.passport);
                window.draw(menu.passportText);

                //Exit button drawing
                window.draw(menu.getExitButton());
                
                //Checking if user wishes to close the buy menu
                if(menu.isExitClicked(Mouse.getPosition(window).x, Mouse.getPosition(window).y) == true)
                {
                    SoundEffect.OPENINVENTORY.play();
                    menu.menuOpen = false;
                    pause();
                }
            }

            window.draw(elapsedTime);

            if(victory == true)
            {
                SoundEffect.WIN.play();
                
                if(scoreboardOpen == false)
                {
                    if(Mouse.isButtonPressed(Mouse.Button.LEFT))
                    {
                        if(endSlide.isScoreboardClicked(Mouse.getPosition(window).x, Mouse.getPosition(window).y))
                        {
                            scoreboardOpen = true;
                        }
                    }

                    //Score calculated as a decreasing value along time
                    if(scoreRecorded == false)
                    {
                        scoreRecorded = true;
                        
                        int score = (int)((9400 - (totalMinutes * 60 + currentSecond) * 10) - (400*familyMenu.deadIndex.size()));
                        String temp = "YOU SCORED:" + (int)((9400 - (totalMinutes * 60 + currentSecond) * 10) - (400*familyMenu.deadIndex.size()));
                        endSlide.scoreText.setString(temp);
                        temp += "\n";
                        Loader.addToFile(score + "\n", "Scoreboard.txt");
                    }

                    window.draw(endSlide);
                    window.draw(endSlide.exit);
                    window.draw(endSlide.exitText);
                    window.draw(endSlide.youWin);
                    window.draw(endSlide.scoreboard);
                    window.draw(endSlide.scoreboardText);
                    window.draw(endSlide.scoreText);
                    
                }
                else
                {
                    if(Mouse.isButtonPressed(Mouse.Button.LEFT))
                    {
                        if(scoreboard.isBackClicked(Mouse.getPosition(window).x, Mouse.getPosition(window).y))
                        {
                            scoreboardOpen = false;
                        }
                    }

                    window.draw(scoreboard);
                    window.draw(scoreboard.exit);
                    window.draw(scoreboard.exitText);
                    window.draw(scoreboard.back);
                    window.draw(scoreboard.backText);
                }

            }

            if(familyMenu.isAllDead()) {
                SoundEffect.BGM2.stop();
                window.draw(endSlide);
                window.draw(endSlide.exit);
                window.draw(endSlide.exitText);
                window.draw(endSlide.youLost);
            }
        } 
        else 
        {
            window.draw(startingMenu);

            for(RectangleShape r : startingMenu.getButtons())
                window.draw(r);

            for(Text t : startingMenu.getButtonText()) 
                window.draw(t);
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
            
            if(!startingMenu.getIsOpen() && victory != true) 
            {
                startTime = startingMenu.getStartTime();
                currentTime = System.currentTimeMillis();
                totalSeconds = (currentTime - startTime)/1000;
                currentSecond = totalSeconds % 60;
                totalMinutes =  totalSeconds/60;
                String str = totalMinutes + ":" + currentSecond;
                elapsedTime.setString(totalMinutes + "m" + currentSecond + "s");
            }

            this.movement();
            this.drawObjects();
            
            if(farmFields[0][0].selectedField == farmFields[2][2]) // TODO: Handle issue regarding field 12, ideally remove it from the class totally
            {
                farmFields[0][0].selectedField = null;
            }
            
            //Handle events
            for(Event event : window.pollEvents()) 
            {
                if(event.type == Event.Type.CLOSED) 
                {
                    //The user pressed the close button
                    window.close();
                    System.exit(0);
                }
                
                if(Mouse.isButtonPressed(Mouse.Button.LEFT)) 
                {
                    float mouseX = Mouse.getPosition(window).x;
                    float mouseY = Mouse.getPosition(window).y;

                    if(!startingMenu.getIsOpen()) 
                    {
                        resourceMenu.selectIcon(mouseX, mouseY);
                        buyCycle.buyVeg(mouseX, mouseY);
                        buyCycle.buyUpgrade(mouseX, mouseY);
                        buyCycle.eventClicked(mouseX, mouseY);
                        buyCycle.collectVeg();
                        buyCycle.selectVegToGrowOnField();
                        buyCycle.unlockField();
                    } 
                    else 
                    {
                        startingMenu.isClicked(mouseX, mouseY);
                    }
                    
                    if(menu.passport.isClicked(window) && resourceMenu.getIndexVal(4) >= passportValue)
                    {
                        resourceMenu.decrement(4, passportValue);
                        victory = true;
                    }

                    if(victory == true)
                    {
                        if(endSlide.isExitClicked(mouseX, mouseY) && scoreboardOpen == false)
                        {
                            System.exit(0);
                        }
                        else if(scoreboard.isExitClicked(mouseX, mouseY) && scoreboardOpen == true)
                        {
                            System.exit(0);
                        }
                    }

                    if(familyMenu.isAllDead()) 
                    {
                        SoundEffect.FAILOTHER.play();  //testing the audio play for 1 time
                        /*if(endSlide.isExitClicked(mouseX, mouseY))
                        {
                            System.exit(0);
                        }*/
                    }
                }
            }
        }
    }
    
    /**
     * Pauses the game for x milliseconds.
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
        if(farmFields[0][0].isWinter == true)
        {
            for(int j = 0; j < 5; j++)
            {
                for(int z = 0; z < 5; z++)
                {
                    for(int i = 0; i < farmFields[j][z].clockArr.length; i++)
                    {
                        int temp = farmFields[j][z].clockArr[i].time.getDelay();
                        farmFields[j][z].clockArr[i].time.setDelay((int)(temp * summer));
                    }
        
                    farmFields[j][z].isWinter = false;
                }
            }
            resourceMenu.changeSeasonIcon(2);
            backround.seasonChange = true;
        }
        else
        {
            for(int j = 0; j < 5; j++)
            {
                for(int z = 0; z < 5; z++)
                {
                    for(int i = 0; i < farmFields[j][z].clockArr.length; i++)
                    {
                        int temp = farmFields[j][z].clockArr[i].time.getDelay();
                        farmFields[j][z].clockArr[i].time.setDelay((int)(temp * summer));
                    }
        
                    farmFields[j][z].isWinter = true;
                }
            }

            resourceMenu.changeSeasonIcon(1);
            backround.seasonChange = true;
        }
        
        SoundEffect.FAILPRISON.play();
        System.out.println("SEASON HAS CHANGED, WINTER IS " + farmFields[0][0].isWinter);
    }
}
