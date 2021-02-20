package BoringGame;

import org.jsfml.system.Vector2f;

import BoringGame.Loader;

import java.util.Observable;
import java.util.Observer;
import org.jsfml.graphics.*;

public class InitialCutscene extends RectangleShape implements Observer {
    
    private RectangleShape scene;
    private Boolean sceneIsOver;
    private Texture texture;
    private int sceneNumber;
    private Clock clock;

    public InitialCutscene(float width, float height) {
        super(new Vector2f(width, height));
        this.setPosition(0, 0);
        this.setFillColor(new Color(0, 0, 0));

        sceneIsOver = false;

        clock = new Clock(5 * 1000);
        texture = new Texture();
        sceneNumber = 1;

        scene = new RectangleShape(new Vector2f(384, 432));
        scene.setPosition(0, 300);
    }

    public RectangleShape[] getRectangles() {
        RectangleShape[] result = new RectangleShape[] {this, scene};

        return result;
    }

    public Boolean getSceneIsOver() {
        return sceneIsOver;
    }

    //Changes the cut scene every set amout of seconds
    public void update(Observable clock, Object o) {
        Loader.loadPathToRectangle("BoringGame/AllResources/IntroSlides", "Slides" + sceneNumber + ".png", scene, texture);

        if(sceneNumber == 7)
            sceneIsOver = true;
    }
}
