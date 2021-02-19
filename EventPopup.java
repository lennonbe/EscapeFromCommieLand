package BoringGame;

import org.jsfml.graphics.*;
import java.util.Observable;
import java.util.Observer;

/**
 * This class represents the timer pop-up that happens randomly during the game.
 * It extends CircleShape because the pop-up itself is circle icon.
 */
public class EventPopup extends CircleShape implements Observer{

    private Boolean isActive = false;
    private int timeToCancel = 10; //(seconds)
    private int timeLeft = timeToCancel;
    private FamilyMenu main;
    private Clock clock;
    private int index;
    private Texture timerTexture;

    /**
     * Constructor that creates the pop-up.
     * When a pop-up is made the player has exactly 10 seconds to react.
     * 
     * @param main FamilyMenu - The FamilyMenu class that this pop-up was made in
     * @param index int - the index of the 
     */
    public EventPopup(FamilyMenu main, int index) {
        super(25);

        timerTexture = new Texture();
        Loader.loadPathToCircle("BoringGame/Sprites/Countdown", "Countdown10.png", this, timerTexture);

        this.setPosition(20, 300);
        
        this.index = index;

        clock = new Clock(1 * 1000);
        clock.addObserver(this);

        this.main = main;
    }    

    /**
     * Called when the player has reacted to the event,
     * the clock has to be stopped.
     */
    public void eventResponded() {
        clock.stopClock();
    }

    /**
     * Called automatically by the Clock every second.
     * This function changes the image of this class to the next stage,
     * this makes it look like it's a clock or a timer.
     * Once the timer runs out, a function in the FamilyMenu class is called
     */
    public void update(Observable obs, Object obj) {
        if(timeLeft != 0) {
            timeLeft--;
            Loader.loadPathToCircle("BoringGame/Sprites/Countdown", "Countdown" + timeLeft + ".png", this, timerTexture);
        } else {
            clock.stopClock();
            main.killPerson(index);
        }
    }
}
