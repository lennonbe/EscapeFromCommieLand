package BoringGame;

import org.jsfml.system.Vector2f;
import org.jsfml.graphics.*;
import java.util.*;
import BoringGame.Game;

public class DistanceMarker {
    
    private final int numberOfRectangles = 12;

    private Game game;
    private RectangleShape[] bounds;
    private RenderWindow window;
    private CircleShape[] pointers;
    private ArrayList<Fields> closeFields;

    public DistanceMarker(RenderWindow window, Game game) {
        this.window = window;
        this.game = game;
    }

    public CircleShape[] getPointers() {
        return pointers;
    }

    private double distance(float x1, float y1, float x2, float y2) {
        double xDiff = Math.pow(x1-x2, 2);
        double yDiff = Math.pow(y1-y2, 2);

        double result = Math.sqrt(xDiff + yDiff);

        return result;
    }

    public void drawBounds() {
        closeFields = new ArrayList<>();
        float fieldX = -50;
        float fieldY = -50;

        for(int i = 0; i < game.farmFields.length; i++) {
            for(int n = 0; n < game.farmFields[i].length; n++) {
                fieldX = game.farmFields[i][n].getPosition().x + (game.fieldSizeInt / 2) - 12;
                fieldY = game.farmFields[i][n].getPosition().y + (game.fieldSizeInt / 2);

                float farmerX = game.farmer.getGlobalBounds().left + (game.farmer.getGlobalBounds().width  / 2) - 12;
                float farmerY = game.farmer.getGlobalBounds().top  + (game.farmer.getGlobalBounds().height / 2);

                if(distance(fieldX, fieldY, farmerX, farmerY) < game.fieldSizeInt * 1.5) {
                    closeFields.add(game.farmFields[i][n]);
                }
            }
        }
        
        pointers = new CircleShape[closeFields.size()];
        
        for(int k = 0; k < closeFields.size(); k++) {
            pointers[k] = new CircleShape(10);
            pointers[k].setPosition(closeFields.get(k).getPosition().x + (game.fieldSizeInt / 2) - 12, closeFields.get(k).getPosition().y + (game.fieldSizeInt / 2));
        }
    }
}
