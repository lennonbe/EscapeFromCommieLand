package BoringGame;

import org.jsfml.system.Vector2f;
import java.util.Observer;
import java.util.Observable;
import BoringGame.Loader;
import org.jsfml.graphics.*;

public class InitialCutscene extends RectangleShape implements Observer {
    
    private RectangleShape skipButton;
    private RectangleShape scene;
    private Boolean sceneIsOver;
    private Texture sceneTexture;
    private Texture buttonTexture;
    private int sceneNumber;
    private Clock clock;
    private Font sceneFont;
    private Text sceneText;
    private Text skipText;
    private Font skipFont;
    private String[] sceneStrings;

    public InitialCutscene(float width, float height) {
        super(new Vector2f(width, height));
        this.setPosition(0, 0);

        sceneIsOver = false;
        sceneTexture = new Texture();
        sceneNumber = 0;

        sceneFont = new Font();
        sceneText = new Text("", sceneFont);
        sceneText.setPosition(100, 630);
        
        clock = new Clock(5 * 1000);
        clock.addObserver(this);

        buttonTexture = new Texture();
        skipButton = new RectangleShape(new Vector2f(160, 82));
        skipButton.setPosition(width - 180, height - 102);
        skipButton.setFillColor(new Color(255, 255, 255));

        skipFont = new Font();
        skipText = new Text("SKIP", skipFont);
        skipText.setPosition(skipButton.getPosition().x + 15, skipButton.getPosition().y);
        skipText.setScale(2, 2);

        sceneStrings = new String[] {
            "Welcome to mother Russia!",
            "Where the days are harsh,\nmany will perish.",
            "You must farm to protect \nyourself and your family.",
            "From desease and famin",
            "You are their only hope.",
            "...",
            "Good luck Comrade."
        };

        Loader.loadPathToRectangle("BoringGame/AllResources/IntroSlides", "Slides0.png", this, sceneTexture);
        Loader.loadPathToRectangle("BoringGame/AllResources/MainMenu", "MainMenuButton.png", skipButton, buttonTexture);
        Loader.loadPathToFont(sceneFont, "BoringGame/AllResources/SovietProgram.ttf");
        Loader.loadPathToFont(skipFont, "BoringGame/AllResources/pixelated.ttf");

        skipText.setString("SKIP");
    }

    public void isClicked(float mouseX, float mouseY) {
        if(mouseX > skipButton.getPosition().x && mouseX < skipButton.getPosition().x + 160 &&
           mouseY > skipButton.getPosition().y && mouseY < skipButton.getPosition().y + 82) 
        {
            sceneIsOver = true;
            clock.stopClock();
        }
    }

    public RectangleShape[] getRectangles() {
        return new RectangleShape[] {this, skipButton};
    }

    public Text[] getText() {
        sceneText.setString(sceneStrings[sceneNumber]);
        return new Text[] {sceneText, skipText};
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

        Loader.loadPathToRectangle("BoringGame/AllResources/IntroSlides", "Slides" + sceneNumber + ".png", this, sceneTexture);
    }
}
