package BoringGame;

import org.jsfml.system.Vector2f;
import java.util.Observer;
import java.util.Observable;
import BoringGame.Loader;
import org.jsfml.graphics.*;

public class InitialCutscene extends RectangleShape implements Observer {
    
    private RectangleShape scene;
    private Boolean sceneIsOver;
    private Texture texture;
    private int sceneNumber;
    private Clock clock;
    private Font font;
    private Text text;
    private String[] sceneStrings;

    public InitialCutscene(float width, float height) {
        super(new Vector2f(width, height));
        this.setPosition(0, 0);
        this.setFillColor(new Color(128, 128, 128));

        sceneIsOver = false;

        texture = new Texture();
        sceneNumber = 0;
        
        scene = new RectangleShape(new Vector2f(384 * 2,432 * 2));
        scene.setPosition(0, 0);

        font = new Font();
        text = new Text("", font);
        text.setPosition(100, 630);
        
        clock = new Clock(5 * 1000);
        clock.addObserver(this);

        sceneStrings = new String[] {
            "Welcome to mother Russia!",
            "Where the days are harsh,\nmany will perish.",
            "You must farm to protect \nyourself and your family.",
            "From desease and famin",
            "You are their only hope.",
            "...",
            "Good luck Comrade."
        };

        Loader.loadPathToRectangle("BoringGame/AllResources/IntroSlides", "Slides0.png", scene, texture);
        Loader.loadPathToFont(font, "BoringGame/AllResources/SovietProgram.ttf");
    }

    public RectangleShape[] getRectangles() {
        return new RectangleShape[] {this, scene};
    }

    public Text getText() {
        text.setString(sceneStrings[sceneNumber]);
        return text;
    }

    public Boolean getSceneIsOver() {
        return sceneIsOver;
    }

    //Changes the cut scene every set amout of seconds
    public void update(Observable c, Object o) {
        sceneNumber++;

        if(sceneNumber == 7) {
            sceneIsOver = true;    
            clock.stopClock();
            return;
        }

        Loader.loadPathToRectangle("BoringGame/AllResources/IntroSlides", "Slides" + sceneNumber + ".png", scene, texture);
    }
}
