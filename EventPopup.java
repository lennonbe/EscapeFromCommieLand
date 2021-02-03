package BoringGame;

import org.jsfml.graphics.*;
import java.util.Observable;
import java.util.Observer;

public class EventPopup extends CircleShape implements Observer{

    private Boolean isActive = false;
    private int timeToCancel = 3; //(seconds)
    private int timeLeft = timeToCancel;
    private FamilyMenu main;
    private Thread thread;
    private Clock clock;
    private int index;
    private Font font;
    private Text text;

    public EventPopup(FamilyMenu main, int index) {
        super(25);

        this.setPosition(20, 300);
        this.setFillColor(new Color(0, 0, 0));

        this.index = index;

        clock = new Clock(1 * 1000);
        clock.addObserver(this);

        this.main = main;

        font = new Font();
        Loader.loadPathToFont(font, "BoringGame/Russian.ttf");
        text = new Text(String.valueOf(timeToCancel), font);
        text.setPosition(30, 310);

        main.setText(text);
    }    

    public void eventResponded() {
        clock.stopClock();
    }

    public void update(Observable obs, Object obj) {
        if(timeLeft != 0) {
            timeLeft--;
            text.setString(String.valueOf(timeLeft));
            main.setText(text);
        } else {
            //Moves the text out of bounds
            text.setPosition(-50, -50);
            clock.stopClock();
            main.killPerson(index);
        }
    }
}
