package BoringGame;

import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.io.*;

public class Game 
{
    int length = 640;
    int width = 480;
    int speed = 15; 
    int speed2 = 10;

    RenderWindow window = new RenderWindow();
    
    CircleShape circle = new CircleShape(15);
    Texture farmer = new Texture();

    Image farmerImage = new Image();
    Texture farmerTexture = new Texture();

    Path path = FileSystems.getDefault().getPath("BoringGame", "Man_Neutral.png");

    Vector2f scale = new Vector2f((float)1.5, (float)1.5);

    Sprite farmerSprite = new Sprite();

    public Game()
    {
        //Create the window
        window.create(new VideoMode(640, 480), "Hello JSFML!");

        //Limit the framerate
        window.setFramerateLimit(60);

        //BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        //farmerTexture.loadFromFile(path);

        try 
        {
            BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        } 
        catch (Exception e) 
        {
            //TODO: handle exception
        }

        try 
        {
            farmerTexture.loadFromFile(path);
        } 
        catch (Exception e) 
        {
            //TODO: handle exception
        }

        farmerSprite.setTexture(farmerTexture);

        //farmerImage.loadFromFile("Man_Neutral.png");
        //farmer.loadFromFile(path);

        //System.out.println(farmerSprite.getScale());
        farmerSprite.setPosition(length/2, width/2);
        farmerSprite.setScale(scale);
        farmerSprite.scale(scale);
        //System.out.println(farmerSprite.getScale()+ "2");
    }

    public void playGame()
    {
        while(window.isOpen()) 
        {
            //Fill the window with red
            window.clear(Color.RED);

            if(Keyboard.isKeyPressed(Keyboard.Key.D))
            {
                if(farmerSprite.getPosition().x /*+ circle.getRadius()*/ >= 640 - circle.getRadius()*2)
                {
                //circle.move(0, 0);
                farmerSprite.move(0,0);
                }
                else
                {
                //circle.move(speed, 0);
                farmerSprite.move(speed,0);
                }
            }
            else if(Keyboard.isKeyPressed(Keyboard.Key.A))
            {
                if(farmerSprite.getPosition().x /*- circle.getRadius()*/ <= 0)
                {
                //circle.move(0, 0);
                farmerSprite.move(0,0);
                }
                else
                {
                //circle.move(-speed, 0);
                farmerSprite.move(-speed,0);
                }
            }
            else if(Keyboard.isKeyPressed(Keyboard.Key.S))
            {
                if(farmerSprite.getPosition().y /*+ circle.getRadius()*/ >= 480 - circle.getRadius()*2)
                {
                //circle.move(0, 0);
                farmerSprite.move(0,0);
                }
                else
                {
                //circle.move(0, speed);
                farmerSprite.move(0,speed);
                }
                //circle.move(0, speed);
            }
            else if(Keyboard.isKeyPressed(Keyboard.Key.W))
            {
                if(farmerSprite.getPosition().y /*- circle.getRadius()*/ <= 0)
                {
                //circle.move(0, speed - circle.getPosition().y);
                //circle.move(0, 0);
                farmerSprite.move(0,0);
                }
                else
                {
                //circle.move(0, -speed);
                farmerSprite.move(0,-speed);
                }
                //circle.move(0, -speed);
            }

            //window.draw(circle);
            //farmerSprite.scale(scale);
            window.draw(farmerSprite);
            window.display();

            //Handle events
            for(Event event : window.pollEvents()) 
            {
                if(event.type == Event.Type.CLOSED) 
                {
                //The user pressed the close button
                window.close();
                }
            }
        }
    

        /*
        public loadPathToSprite(String directory, String file)
        {
            Path path = FileSystems.getDefault().getPath(directory, file);
            
            try 
            {
                BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);

            } catch (Exception e) {
                //TODO: handle exception
            }

            try 
            {
                farmerTexture.loadFromFile(path);
            } 
            catch (Exception e) 
            {
                //TODO: handle exception
            }
        }*/

    }
}
