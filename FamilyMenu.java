package BoringGame;

import org.jsfml.system.Vector2f;

import BoringGame.EventPopup;

import org.jsfml.graphics.*;
import java.util.Observable;
import java.util.Observer;
import java.lang.Math;

public class FamilyMenu extends RectangleShape implements Observer{
    
    //Inherit attributes of the FamilyMenu are final
    private final static int numberOfIcons = 2;
    private final static Vector2f iconSize = new Vector2f(50, 50);
    private final static int width = (int) iconSize.x * numberOfIcons;
    private final static int height = (int) iconSize.y + 40;
    private final Clock clock = new Clock(1000);
    private final double chanceOfEvent = 0.1;  //0.5 for testing --- change this later 
    private final int menuXPosition = 0;
    private final int menuYPosition = 864 - height;
    private final int iconWidth = 50;
    private final int iconHeight = 50;
    private final int gap = 20;
    
    private CircleShape[] deadDot; //DELETE THIS LATER, THIS IS JUST A TEMPORARY THING TO SHOW A FAMILY MEMBER IS DEAD 
    private Texture[] familyIconsTexture;
    private RectangleShape[] familyIcons;
    private EventPopup popup;
    private Text text;

    public FamilyMenu() {
        super(new Vector2f(width, height));

        this.setPosition(menuXPosition, menuYPosition);
        this.setFillColor(new Color(128,128,128));

        clock.addObserver(this);

        familyIconsTexture = new Texture[numberOfIcons];
        familyIcons = new RectangleShape[numberOfIcons];

        deadDot = new CircleShape[numberOfIcons];

        text = new Text();

        for(int i = 0; i < numberOfIcons; i++) {
            int iconXPosition = (menuXPosition) + (iconWidth * i);
            int iconYPosition = (menuYPosition + height) - (gap + iconHeight);

            familyIconsTexture[i] = new Texture();
            familyIcons[i] = new RectangleShape();
            
            //deadDot is a red circle that will appear on top of a persons icon if that person is dead, 
            // This will be deleted later because it's ugly :)
            deadDot[i] = new CircleShape(18);

            //It's position is intentionally placed out of bounds, because they shouldn't be visable, unless a person dies 
            deadDot[i].setPosition(-50, -50);
            deadDot[i].setFillColor(new Color(255, 0, 0));

            familyIcons[i].setPosition(iconXPosition, iconYPosition);
            familyIcons[i].setSize(iconSize);

            Loader.loadPathToRectangle("BoringGame/Sprites/Family", i + ".png", familyIcons[i], familyIconsTexture[i]);
        }
    }

    /**
     * Returns an array of RectangleShape so that they can be drawn.
     * @return RectangleShape array
     */
    public RectangleShape[] getRectangleArray() {
        return familyIcons;
    }

    public CircleShape[] getCircleShapeArray() {
        return deadDot;
    }

    public CircleShape getPopup() {
        return popup;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text; 
    }

    /**
     * This function is called when the mouse is clicked.
     * If an event is active, check if the user has clicked on the event popup,
     * If yes, remove it.
     * 
     * @param mouseX x coordinate of the mouse when it was pressed
     * @param mouseY y coordinate of the mouse when it was pressed
     */
    public void eventClicked(float mouseX, float mouseY) {
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
            }
        }
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
            
            //Once an event fires, another random number decides which family member will be affected
            int personIndex = (int)(Math.random()*(numberOfIcons));

            popup = new EventPopup(this, personIndex);
        }
    }

    public void killPerson(int index) {
        RectangleShape person = familyIcons[index];
        deadDot[index].setPosition(person.getPosition().x, person.getPosition().y);
        popup = null;
    }
}
