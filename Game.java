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
    int heigth = 600;
    int width = 800;
    int speed = 10; 
    int speed2 = 10;

    Vector2f scale = new Vector2f((float)1.5, (float)1.5);
    Vector2f fieldSize = new Vector2f(135, 135);
    Vector2f windowSize = new Vector2f(width, heigth);

    int fieldSizeInt = (int)fieldSize.x;

    RenderWindow window = new RenderWindow();
    
    Texture backroundTexture = new Texture();
    Texture farmerTexture = new Texture();
    Texture houseTexture = new Texture();

    Texture [] fieldsTextures = new Texture[8];
    RectangleShape [] fieldsRectangles = new RectangleShape[8];

    RectangleShape house = new RectangleShape(fieldSize);

    Sprite farmerSprite = new Sprite();
    RectangleShape backround = new RectangleShape();

    public Game()
    {
        //Create the window
        window.create(new VideoMode(width, heigth), "Escape from CommieLand!");

        //Limit the framerate
        window.setFramerateLimit(60);

        this.loadPathToSprite("BoringGame", "Man_Neutral.png", farmerSprite, farmerTexture);
        this.loadPathToRectangle("BoringGame", "Forest.png", backround, backroundTexture);
        this.loadPathToRectangle("BoringGame", "House.jpg", house, houseTexture);

        for(int i = 0; i < 8; i++)
        {
            fieldsRectangles[i] = new RectangleShape();
            fieldsTextures[i] = new Texture();

            this.loadPathToRectangle("BoringGame", "EmptyField.png", fieldsRectangles[i], fieldsTextures[i]);
            fieldsRectangles[i].setSize(fieldSize);
        }

        fieldsRectangles[0].setPosition(width/2 - 3*fieldSizeInt/2, heigth/2 - 3*fieldSizeInt/2);
        fieldsRectangles[1].setPosition(width/2 - 3*fieldSizeInt/2, heigth/2 - fieldSizeInt/2);
        fieldsRectangles[2].setPosition(width/2 - 3*fieldSizeInt/2, heigth/2 + fieldSizeInt/2);
        fieldsRectangles[3].setPosition(width/2 + fieldSizeInt/2, heigth/2 - 3*fieldSizeInt/2);
        fieldsRectangles[4].setPosition(width/2 + fieldSizeInt/2, heigth/2 - fieldSizeInt/2);
        fieldsRectangles[5].setPosition(width/2 + fieldSizeInt/2, heigth/2 + fieldSizeInt/2);
        fieldsRectangles[6].setPosition(width/2 - fieldSizeInt/2, heigth/2 - 3*fieldSizeInt/2);
        fieldsRectangles[7].setPosition(width/2 - fieldSizeInt/2, heigth/2 + fieldSizeInt/2);

        farmerSprite.setPosition(width/2, heigth/2);
        farmerSprite.setScale(scale);
        farmerSprite.scale(scale);

        house.setPosition(width/2 - fieldSizeInt/2, heigth/2 - fieldSizeInt/2);
        backround.setPosition(0, 0);
        backround.setSize(windowSize);
        house.setSize(fieldSize);
    }

    public void movement()
    {
        if(Keyboard.isKeyPressed(Keyboard.Key.D))
        {
            if(farmerSprite.getGlobalBounds().left + farmerSprite.getGlobalBounds().width >= width)
            {
            //System.out.println("I AM HERE " + farmerSprite.getPosition().x);
            farmerSprite.move(0,0);
            }
            else
            {
            farmerSprite.move(speed,0);
            }
        }
        else if(Keyboard.isKeyPressed(Keyboard.Key.A))
        {
            if(farmerSprite.getGlobalBounds().left <= 0)
            {
            //System.out.println("I AM HERE " + farmerSprite.getPosition().x);
            farmerSprite.move(0,0);
            }
            else
            {
            farmerSprite.move(-speed,0);
            }
        }
        else if(Keyboard.isKeyPressed(Keyboard.Key.S))
        {
            if(farmerSprite.getGlobalBounds().top + farmerSprite.getGlobalBounds().height >= heigth)
            {
            farmerSprite.move(0,0);
            }
            else
            {
            //System.out.println("I AM HERE " + farmerSprite.getGlobalBounds().top + "&" + farmerSprite.getGlobalBounds().height);
            farmerSprite.move(0,speed);
            }
        }
        else if(Keyboard.isKeyPressed(Keyboard.Key.W))
        {
            if(farmerSprite.getGlobalBounds().top <= 0)
            {
            farmerSprite.move(0,0);
            }
            else
            {
            farmerSprite.move(0,-speed);
            }
        }
    }

    public void playGame()
    {
        //System.out.println(farmerSprite.getScale() + "Hello there!");
        while(window.isOpen()) 
        {
            //Fill the window with red
            window.clear(Color.RED);

            /*
            if(Keyboard.isKeyPressed(Keyboard.Key.D))
            {
                if(farmerSprite.getGlobalBounds().left + farmerSprite.getGlobalBounds().width >= width)
                {
                //System.out.println("I AM HERE " + farmerSprite.getPosition().x);
                farmerSprite.move(0,0);
                }
                else
                {
                farmerSprite.move(speed,0);
                }
            }
            else if(Keyboard.isKeyPressed(Keyboard.Key.A))
            {
                if(farmerSprite.getGlobalBounds().left <= 0)
                {
                //System.out.println("I AM HERE " + farmerSprite.getPosition().x);
                farmerSprite.move(0,0);
                }
                else
                {
                farmerSprite.move(-speed,0);
                }
            }
            else if(Keyboard.isKeyPressed(Keyboard.Key.S))
            {
                if(farmerSprite.getGlobalBounds().top + farmerSprite.getGlobalBounds().height >= heigth)
                {
                farmerSprite.move(0,0);
                }
                else
                {
                //System.out.println("I AM HERE " + farmerSprite.getGlobalBounds().top + "&" + farmerSprite.getGlobalBounds().height);
                farmerSprite.move(0,speed);
                }
            }
            else if(Keyboard.isKeyPressed(Keyboard.Key.W))
            {
                if(farmerSprite.getGlobalBounds().top <= 0)
                {
                farmerSprite.move(0,0);
                }
                else
                {
                farmerSprite.move(0,-speed);
                }
            }
            */

            this.movement();

            /*
            Following code will be useful for idenfying when the player is clicking on a certain crop or on the house etc...
            Right now doesnt do much but still a good starting point. 
            */
            if(Mouse.isButtonPressed(Mouse.Button.LEFT))
            {
                if(Mouse.getPosition(window).x >= width/3 && Mouse.getPosition(window).x <= 2 * width/3 && Mouse.getPosition().y >= heigth/3 && Mouse.getPosition().y <= 2 * heigth/3)
                {
                    System.out.println("Test successful!");
                }
            }

            window.draw(backround);

            for(int i = 0; i < 8; i++)
            {
                window.draw(fieldsRectangles[i]);
            }

            window.draw(house);
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
    }

    public void loadPathToSprite(String directory, String file, Sprite sprite, Texture texture)
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
            texture.loadFromFile(path);
        } 
        catch (Exception e) 
        {
            //TODO: handle exception
        }

        sprite.setTexture(texture);
    }

    public void loadPathToRectangle(String directory, String file, RectangleShape rectangle, Texture texture)
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
            texture.loadFromFile(path);
        } 
        catch (Exception e) 
        {
            //TODO: handle exception
        }

        rectangle.setTexture(texture);
    }
}
