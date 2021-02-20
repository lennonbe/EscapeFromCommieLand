package BoringGame;

import org.jsfml.system.Vector2f;
import BoringGame.EventPopup;
import org.jsfml.graphics.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.lang.Math;

/**
 * Family menu class. Used to create and manage the farmers family and random events associated to these.
 */
public class FamilyMenu extends RectangleShape implements Observer
{
    
    //Inherit attributes of the FamilyMenu are final
    private final static int numberOfIcons = 2;
    private final static Vector2f iconSize = new Vector2f(80, 110);
    private final static int width = (int) iconSize.x * numberOfIcons;
    private final static int height = (int) iconSize.y + 40;
    private final Clock clock = new Clock(120 * 1000);
    private final double chanceOfEvent = 0.2;
    private final int menuXPosition = 0;
    private final int menuYPosition = 864 - height;
    private final int gap = 20;
    
    protected ArrayList<Integer> deadIndex = new ArrayList<>();
    private Texture[] familyIconsTexture;
    private RectangleShape[] familyIcons;
    private EventPopup popup;
    private Boolean allDead;
    private Text text;

    /**
     * Constructor for the family menu object.
     */
    public FamilyMenu() 
    {
        super(new Vector2f(width, height));

        this.setPosition(menuXPosition, menuYPosition);
        this.setFillColor(new Color(128, 128, 128, 0));

        clock.addObserver(this);

        familyIconsTexture = new Texture[numberOfIcons];
        familyIcons = new RectangleShape[numberOfIcons];

        allDead = false;
        
        text = new Text();

        for(int i = 0; i < numberOfIcons; i++) {
            float iconXPosition = (menuXPosition) + (gap + (gap + iconSize.x) * i);
            float iconYPosition = (menuYPosition + height) - (gap + iconSize.y);

            familyIconsTexture[i] = new Texture();
            familyIcons[i] = new RectangleShape();

            familyIcons[i].setPosition(iconXPosition, iconYPosition);
            familyIcons[i].setSize(iconSize);

            Loader.loadPathToRectangle("BoringGame/AllResources/FamilyMembers", "FamilyMembers" + i + ".png", familyIcons[i], familyIconsTexture[i]);
        }
    }

    /**
     * Returns an array of RectangleShape so that they can be drawn.
     * @return RectangleShape array
     */
    public RectangleShape[] getRectangleArray() {
        return familyIcons;
    }

    public CircleShape getPopup() {
        return popup;
    }

    /**
     * This function is called when the mouse is clicked.
     * If an event is active, check if the user has clicked on the event popup,
     * If yes, remove it and return true.
     * Otherwise, return false
     * 
     * @param mouseX x coordinate of the mouse when it was pressed
     * @param mouseY y coordinate of the mouse when it was pressed
     */
    public boolean eventClicked(float mouseX, float mouseY) {
        //Checks for active popup
        if(popup != null) {
            float xCircle = popup.getPosition().x + popup.getRadius();
            float yCircle = popup.getPosition().y + popup.getRadius();
    
            double xDiff = Math.pow(xCircle - mouseX, 2);
            double yDiff = Math.pow(yCircle - mouseY, 2);
    
            double totalDistance = Math.sqrt(xDiff + yDiff);

            if(totalDistance <= popup.getRadius()) {
                popup.eventResponded();
                popup = null;
                return true;
            }
        }
        return false;
    }

    /**
     * This function is called every minute.
     * Every minute there's a chance (denoted by the chanceOfEvent vaiable) that an event fires,
     * the player has a set amout of seconds after an event fires to react, otherwise that family member gets sick, or dies.
     * To react to these events, the player will have to left click on the pop-up once it fires
     */
    @Override
    public void update(Observable obs, Object obj) {

        if(chanceOfEvent > Math.random() && popup == null) {
            SoundEffect.ALARM.play();
            //Once an event fires, another random number decides which family member will be affected
            int personIndex = (int)(Math.random()*(numberOfIcons));

            while(isDead(personIndex)) {
                personIndex = (int)(Math.random()*(numberOfIcons));
            }

            popup = new EventPopup(this, personIndex);
        }
    }

    public void killPerson(int index) {
        //If the player doesn't respont to the event, there's a 50% chance that a family member will die,
        //Otherwise do nothing
        if(Math.random() > 0.5) {
            SoundEffect.DIE.play();
            RectangleShape person = familyIcons[index];

            Loader.loadPathToRectangle("BoringGame/AllResources/FamilyMembers", "FamilyMembers" + index + "_dead.png", familyIcons[index], familyIconsTexture[index]);
            deadIndex.add(index);

            if(deadIndex.size() >= numberOfIcons) 
                allDead = true;
        }

        popup = null;
    }

    /**
     * Checks if all family members are dead.
     * @return allDead bool
     */
    public Boolean isAllDead() {
        return allDead;
    }

    /**
     * Checks if a member is dead.
     * @param index index you wish to check is dead
     * @return boolean represing if is dead (true if so, false otherwise)
     */
    private Boolean isDead(int index) {
        for(Integer i : deadIndex) {
            if(i == index) 
                return true;
        }

        return false;
    }
}
