import java.util.Observable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 * This clock class implements a Singleton design pattern.
 * To instantiate this clock class you MUST use the 'getInstance()' mothod.
 * Observable is deprecated, however, it's useful for this application.
 */
public class Clock extends Observable implements ActionListener {
    
    private static Clock instance = null;
    private Timer time;

    //This implements a Singleton design pattern
    public static Clock getInstance() {
        if(instance == null) 
            instance = new Clock();

        return instance;        
    }

    //Private constructor on purpose, DO NOT CHANGE!
    private Clock() {
        time = new Timer(1000, this);
        time.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.setChanged();
        this.notifyObservers();
    }
}