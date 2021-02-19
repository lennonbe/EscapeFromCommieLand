package BoringGame;

import java.util.Observable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 * This class is used to perform periodic actions.
 * The constructor parameter represents the amout of time in milliseconds
 * per each call.
 */
public class Clock extends Observable implements ActionListener
{
    protected Timer time;

    /**
     * Creates an instance of a Clock that calls upgrade every set amount of milliseconds.
     * 
     * @param milliseconds int - milliseconds
     */
    public Clock(int milliseconds) 
    {
        time = new Timer(milliseconds, this);
        time.start();
    }

    /**
     * Stops the clock.
     */
    public void stopClock() {
        time.stop();
    }

    /**
     * This function is called every set amount of milliseconds.
     * Notifys the Obeservers of this class.
     */
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        this.setChanged();
        this.notifyObservers();
    }
}
