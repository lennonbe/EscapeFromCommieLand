package BoringGame;

import org.jsfml.graphics.*;
import java.util.Observable;
import java.util.Observer;

public class EventPopup extends CircleShape implements Observer{

    private Boolean isActive = false;
    private int timeToCancel = 10; //(seconds)
    private int timeLeft = timeToCancel;
    private FamilyMenu main;
    private Clock clock;
    private int index;
    private Texture timerTexture;

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

    public void eventResponded() {
        clock.stopClock();
    }

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
