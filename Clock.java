package BoringGame;

import java.util.Observable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 * This clock class implements a Singleton design pattern.
 * To instantiate this clock class you MUST use the 'getInstance()' mothod.
 * Observable is deprecated, however, it's useful for this application.
 */
public class Clock extends Observable implements ActionListener
{
    protected Timer time;

    public Clock(int milliseconds) 
    {
        time = new Timer(milliseconds, this);
        time.start();
    }

    public void stopClock() {
        time.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        this.setChanged();
        this.notifyObservers();
    }
}
