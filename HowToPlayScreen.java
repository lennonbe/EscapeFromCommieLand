package BoringGame;

import org.jsfml.system.Vector2f;

import BoringGame.Loader;

import org.jsfml.graphics.*;

public class HowToPlayScreen extends RectangleShape {
    
    private Vector2f buttonSize = new Vector2f(100, 50);
    private RectangleShape backButton;
    private Boolean isOpen;
    private Font font;
    private Text buttonText;
    private Text howToText;

    public HowToPlayScreen(float width, float height) {
        super(new Vector2f(width, height));

        this.setPosition(0, 0);
        this.setFillColor(new Color(0, 0, 0));

        isOpen = false;

        backButton = new RectangleShape(new Vector2f(100, 50));
        backButton.setPosition(100, height - buttonSize.y - 100);
        backButton.setFillColor(new Color(128, 128, 128));

        font = new Font();
        Loader.loadPathToFont(font, "BoringGame/pixelated.ttf");

        buttonText = new Text("BACK", font);
        buttonText.setPosition(backButton.getPosition().x + 20, backButton.getPosition().y);

        howToText = new Text("LOREM IPSUM", font);
        howToText.setPosition(50, 50);
    }

    public void isClicked(float mouseX, float mouseY) {
        if(mouseX > backButton.getPosition().x && mouseX < backButton.getPosition().x + buttonSize.x &&
           mouseY > backButton.getPosition().y && mouseY < backButton.getPosition().y + buttonSize.y) 
        {
            isOpen = false;
        } 
    }

    public Text[] getText() {
        return new Text[] {buttonText, howToText};
    }

    public RectangleShape getButton() {
        return backButton;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean b) {
        isOpen = b;
    }
}
