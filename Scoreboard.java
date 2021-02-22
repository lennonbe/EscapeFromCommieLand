package BoringGame;

import org.jsfml.system.Vector2f;
import org.jsfml.graphics.*;
import java.util.Set;
import java.util.TreeSet;
import java.util.*;
import java.io.*;
import java.util.*;

/**
 * This class represents the endSlide that is the first thing that the user will see once the game is booted
 * There is one button:
 *      Exit        - Exits the game 
 */
public class Scoreboard extends RectangleShape 
{
    private final Vector2f buttonSize = new Vector2f(400, 100);
    protected RectangleShape exit;
    protected RectangleShape back;
    protected Text exitText;
    protected Text backText;
    protected Boolean isOpen, backToEndSlide = false;
    private Font font;
    protected int playerScore;
    private String top5String = "";
    protected Text top5Score;
    private int gap = 60;

    public Scoreboard(float width, float height) 
    {
        super(new Vector2f(width, height));

        this.setPosition(0, 0);
        this.setFillColor(new Color(0, 0, 0));

        //isOpen = true;
        font = new Font();
        Loader.loadPathToFont(font, "BoringGame/pixelated.ttf");

        //Initializing the exit button
        exit = new RectangleShape(buttonSize);
        exit.setPosition(width/2 - buttonSize.x/2, height - buttonSize.y * 3);
        exit.setFillColor(new Color(128, 128, 128));

        //Initializing the exit button text
        exitText = new Text("EXIT GAME", font);
        exitText.setScale(2, 2);
        exitText.setPosition(exit.getPosition().x + buttonSize.x/2 - exitText.getGlobalBounds().width/2, exit.getPosition().y);

        //Initializing the exit button
        back = new RectangleShape(buttonSize);
        back.setPosition(width/2 - buttonSize.x/2, exit.getPosition().y - buttonSize.y * 2);
        back.setFillColor(new Color(128, 128, 128));

        //Initializing back button text
        backText = new Text("BACK", font);
        backText.setScale(2, 2);
        backText.setPosition(back.getPosition().x + buttonSize.x/2 - backText.getGlobalBounds().width/2, back.getPosition().y);

        //Initializing top 5 score
        //top5Score = Loader.readFile("Scoreboard.txt");

        try 
        {
            TreeSet<Integer> numbers=new TreeSet<>();
            FileReader fr=new FileReader("Scoreboard.txt");
            BufferedReader br=new BufferedReader(fr);
            String line;
    
            while((line=br.readLine())!=null)
            {
                //System.out.println(line);
                if(line != "" && line != "\n")
                {
                    numbers.add(Integer.parseInt(line));
                }
            }
            
            br.close();
            Iterator<Integer> it=numbers.iterator();
    
            int count = 0;
            while(it.hasNext() && count < 5)
            {
                top5String += it.next() + "\n";
                count++;
            }
    
            System.out.println(top5String);            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }

    }
    
    /**
     * This function checks if any of the buttons have been pressed,
     * If yes, act accordingly
     */
    public boolean isExitClicked(float mouseX, float mouseY) 
    {
        //Checks if the exit button has been clicked
        if(mouseX > exit.getPosition().x && mouseX < exit.getPosition().x + buttonSize.x && mouseY > exit.getPosition().y && mouseY < exit.getPosition().y + buttonSize.y) 
        {
            return true;
        }
        else
        {
            return false;
        }
    
    }

    /**
     * This function checks if the back of the buttons have been pressed,
     * If yes, act accordingly
     */
    public boolean isBackClicked(float mouseX, float mouseY) 
    {
        //Checks if the exit button has been clicked
        if(mouseX > back.getPosition().x && mouseX < back.getPosition().x + buttonSize.x && mouseY > back.getPosition().y && mouseY < back.getPosition().y + buttonSize.y) 
        {
            return true;
        }
        else
        {
            return false;
        }
    
    }
}