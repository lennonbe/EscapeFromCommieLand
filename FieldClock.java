package BoringGame;

import java.util.Observable;
import java.util.Observer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 * This clock class implements a Singleton design pattern.
 * To instantiate this clock class you MUST use the 'getInstance()' mothod.
 * Observable is deprecated, however, it's useful for this application.
 */
public class FieldClock extends Clock
{
    private boolean isWinter = false;


    public FieldClock(int milliseconds) 
    {
        super(milliseconds);
    }

    public void setWinter()
    {
        if(isWinter == false)
        {
            time.setDelay(time.getInitialDelay());
        }
        else
        {
            time.setDelay(time.getInitialDelay() * 2);
        }
    }
}