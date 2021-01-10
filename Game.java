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
    int heigth = 900;
    int width = 900;
    int speed = 10; 
    int speed2 = 10;

    Vector2f scale = new Vector2f((float)1.5, (float)1.5);
    Vector2f fieldSize = new Vector2f(135, 135);
    Vector2f windowSize = new Vector2f(width, heigth);

    int fieldSizeInt = (int)fieldSize.x;

    int lowerBound = heigth/2 + 3 * fieldSizeInt/2;
    int upperBound = heigth/2 - 3 * fieldSizeInt/2;
    int leftBound = width/2 - 3 * fieldSizeInt/2;
    int rightBound = width/2 + 3 * fieldSizeInt/2;

    int middleLowerBound = heigth/2 + fieldSizeInt/2;
    int middleUpperBound = heigth/2 - fieldSizeInt/2;
    int middleLeftBound = width/2 - fieldSizeInt/2;
    int middleRightBound = width/2 + fieldSizeInt/2;

    RenderWindow window = new RenderWindow();
    
    Texture backroundTexture = new Texture();
    Texture farmerTexture = new Texture();
    Texture houseTexture = new Texture();

    Texture [] fieldsTextures = new Texture[24];
    RectangleShape [] fieldsRectangles = new RectangleShape[24];

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
        this.loadPathToRectangle("BoringGame", "Shop.png", house, houseTexture);

        for(int i = 0; i < 24; i++)
        {
            fieldsRectangles[i] = new RectangleShape();
            fieldsTextures[i] = new Texture();

            this.loadPathToRectangle("BoringGame", "EmptyField.png", fieldsRectangles[i], fieldsTextures[i]);
            fieldsRectangles[i].setSize(fieldSize);
        }

        //Inner left top, middle and bottom (respectivelly)
        fieldsRectangles[0].setPosition(width/2 - 3*fieldSizeInt/2, heigth/2 - 3*fieldSizeInt/2);
        fieldsRectangles[1].setPosition(width/2 - 3*fieldSizeInt/2, heigth/2 - fieldSizeInt/2);
        fieldsRectangles[2].setPosition(width/2 - 3*fieldSizeInt/2, heigth/2 + fieldSizeInt/2);

        //Inner right top, middle and bottom (respectivelly) 
        fieldsRectangles[3].setPosition(width/2 + fieldSizeInt/2, heigth/2 - 3*fieldSizeInt/2);
        fieldsRectangles[4].setPosition(width/2 + fieldSizeInt/2, heigth/2 - fieldSizeInt/2);
        fieldsRectangles[5].setPosition(width/2 + fieldSizeInt/2, heigth/2 + fieldSizeInt/2);

        //Middle top and bottom (respectivelly)
        fieldsRectangles[6].setPosition(width/2 - fieldSizeInt/2, heigth/2 - 3*fieldSizeInt/2);
        fieldsRectangles[7].setPosition(width/2 - fieldSizeInt/2, heigth/2 + fieldSizeInt/2);

        //Right top, middle and bottom (respectivelly)
        fieldsRectangles[8].setPosition(width/2 + 3*fieldSizeInt/2, heigth/2 - 3*fieldSizeInt/2);
        fieldsRectangles[9].setPosition(width/2 + 3*fieldSizeInt/2, heigth/2 - fieldSizeInt/2);
        fieldsRectangles[10].setPosition(width/2 + 3*fieldSizeInt/2, heigth/2 + fieldSizeInt/2);

        //Left top, middle and bottom (respectivelly)
        fieldsRectangles[11].setPosition(width/2 - 5*fieldSizeInt/2, heigth/2 - 3*fieldSizeInt/2);
        fieldsRectangles[12].setPosition(width/2 - 5*fieldSizeInt/2, heigth/2 - fieldSizeInt/2);
        fieldsRectangles[13].setPosition(width/2 - 5*fieldSizeInt/2, heigth/2 + fieldSizeInt/2);

        //Top mid left, middle and right (respectivelly)
        fieldsRectangles[14].setPosition(width/2 - 3*fieldSizeInt/2, heigth/2 - 5*fieldSizeInt/2);
        fieldsRectangles[15].setPosition(width/2 - fieldSizeInt/2, heigth/2 - 5*fieldSizeInt/2);
        fieldsRectangles[16].setPosition(width/2 + fieldSizeInt/2, heigth/2 - 5*fieldSizeInt/2);

        //Bottom mid left, middle and right (respectivelly)
        fieldsRectangles[17].setPosition(width/2 - 3*fieldSizeInt/2, heigth/2 + 3*fieldSizeInt/2);
        fieldsRectangles[18].setPosition(width/2 - fieldSizeInt/2, heigth/2 + 3*fieldSizeInt/2);
        fieldsRectangles[19].setPosition(width/2 + fieldSizeInt/2, heigth/2 + 3*fieldSizeInt/2);

        //Top left and right corners (respectivelly)
        fieldsRectangles[20].setPosition(width/2 - 5*fieldSizeInt/2, heigth/2 - 5*fieldSizeInt/2);
        fieldsRectangles[21].setPosition(width/2 + 3*fieldSizeInt/2, heigth/2 - 5*fieldSizeInt/2);

        //Bottom left and right corners (respectivelly)
        fieldsRectangles[22].setPosition(width/2 - 5*fieldSizeInt/2, heigth/2 + 3*fieldSizeInt/2);
        fieldsRectangles[23].setPosition(width/2 + 3*fieldSizeInt/2, heigth/2 + 3*fieldSizeInt/2);

        farmerSprite.setPosition(100, 100);
        farmerSprite.setScale(scale);
        farmerSprite.scale(scale);

        house.setPosition(width/2 - fieldSizeInt/2, heigth/2 - fieldSizeInt/2);
        house.setSize(fieldSize);
        
        backround.setPosition(0, 0);
        backround.setSize(windowSize);
    }

    public void movement()
    {
        if(Keyboard.isKeyPressed(Keyboard.Key.D))
        {
            if(farmerSprite.getGlobalBounds().left + farmerSprite.getGlobalBounds().width >= width)
            {
                farmerSprite.move(0,0);
            }
            else if(farmerSprite.getGlobalBounds().left + farmerSprite.getGlobalBounds().width >= middleLeftBound && farmerSprite.getGlobalBounds().left + farmerSprite.getGlobalBounds().width <= middleRightBound && ((farmerSprite.getGlobalBounds().top + farmerSprite.getGlobalBounds().height < middleLowerBound && farmerSprite.getGlobalBounds().top + farmerSprite.getGlobalBounds().height > middleUpperBound) || (farmerSprite.getGlobalBounds().top < middleLowerBound && farmerSprite.getGlobalBounds().top > middleUpperBound)))
            {
                farmerSprite.move(0,0);
            }
            else
            {
                if(farmerSprite.getGlobalBounds().left + farmerSprite.getGlobalBounds().width + speed > middleLeftBound && farmerSprite.getGlobalBounds().left + farmerSprite.getGlobalBounds().width + speed < middleRightBound &&((farmerSprite.getGlobalBounds().top + farmerSprite.getGlobalBounds().height < middleLowerBound && farmerSprite.getGlobalBounds().top + farmerSprite.getGlobalBounds().height > middleUpperBound) || (farmerSprite.getGlobalBounds().top < middleLowerBound && farmerSprite.getGlobalBounds().top > middleUpperBound)))
                {
                    farmerSprite.move((Math.abs(middleLeftBound - (farmerSprite.getGlobalBounds().left + farmerSprite.getGlobalBounds().width))), 0);
                }
                else
                {
                    farmerSprite.move(speed,0);
                }
                
            }
        }
        else if(Keyboard.isKeyPressed(Keyboard.Key.A))
        {
            if(farmerSprite.getGlobalBounds().left <= 0)
            {
                farmerSprite.move(0,0);
            }
            else if(farmerSprite.getGlobalBounds().left <= middleRightBound && farmerSprite.getGlobalBounds().left >= middleLeftBound && ((farmerSprite.getGlobalBounds().top + farmerSprite.getGlobalBounds().height < middleLowerBound && farmerSprite.getGlobalBounds().top + farmerSprite.getGlobalBounds().height > middleUpperBound) || (farmerSprite.getGlobalBounds().top < middleLowerBound && farmerSprite.getGlobalBounds().top > middleUpperBound)))
            {
                farmerSprite.move(0,0);
            }
            else
            {
                
                if(farmerSprite.getGlobalBounds().left - speed < middleRightBound && farmerSprite.getGlobalBounds().left - speed > middleLeftBound && ((farmerSprite.getGlobalBounds().top + farmerSprite.getGlobalBounds().height < middleLowerBound && farmerSprite.getGlobalBounds().top + farmerSprite.getGlobalBounds().height > middleUpperBound) || (farmerSprite.getGlobalBounds().top < middleLowerBound && farmerSprite.getGlobalBounds().top > middleUpperBound)))
                {
                    farmerSprite.move(-(Math.abs(middleRightBound - farmerSprite.getGlobalBounds().left)), 0);
                }
                else
                {
                    farmerSprite.move(-speed,0);
                }
                
            }
        }
        else if(Keyboard.isKeyPressed(Keyboard.Key.S))
        {
            if(farmerSprite.getGlobalBounds().top + farmerSprite.getGlobalBounds().height >= heigth)
            {
                farmerSprite.move(0,0);
            }
            else if(farmerSprite.getGlobalBounds().top + farmerSprite.getGlobalBounds().height <= middleLowerBound && farmerSprite.getGlobalBounds().top + farmerSprite.getGlobalBounds().height >= middleUpperBound && ((farmerSprite.getGlobalBounds().left + farmerSprite.getGlobalBounds().width < middleRightBound && farmerSprite.getGlobalBounds().left + farmerSprite.getGlobalBounds().width > middleLeftBound) || (farmerSprite.getGlobalBounds().left < middleRightBound && farmerSprite.getGlobalBounds().left > middleLeftBound)))
            {
                farmerSprite.move(0,0);
            }
            else
            {
                if(farmerSprite.getGlobalBounds().top + farmerSprite.getGlobalBounds().height + speed > middleUpperBound && farmerSprite.getGlobalBounds().top + farmerSprite.getGlobalBounds().height + speed < middleLowerBound && ((farmerSprite.getGlobalBounds().left + farmerSprite.getGlobalBounds().width < middleRightBound && farmerSprite.getGlobalBounds().left + farmerSprite.getGlobalBounds().width > middleLeftBound) || (farmerSprite.getGlobalBounds().left < middleRightBound && farmerSprite.getGlobalBounds().left > middleLeftBound)))
                {
                    farmerSprite.move(0,(Math.abs(middleUpperBound - (farmerSprite.getGlobalBounds().top + farmerSprite.getGlobalBounds().height))));
                }
                else
                {
                    farmerSprite.move(0,speed);
                }
            }
        }
        else if(Keyboard.isKeyPressed(Keyboard.Key.W))
        {
            if(farmerSprite.getGlobalBounds().top <= 0)
            {
                farmerSprite.move(0,0);
            }
            else if(farmerSprite.getGlobalBounds().top >= middleUpperBound && farmerSprite.getGlobalBounds().top <= middleLowerBound && ((farmerSprite.getGlobalBounds().left + farmerSprite.getGlobalBounds().width < middleRightBound && farmerSprite.getGlobalBounds().left + farmerSprite.getGlobalBounds().width > middleLeftBound) || (farmerSprite.getGlobalBounds().left < middleRightBound && farmerSprite.getGlobalBounds().left > middleLeftBound)))
            {
                farmerSprite.move(0,0);
            }
            else
            {
                if(farmerSprite.getGlobalBounds().top - speed < middleLowerBound && farmerSprite.getGlobalBounds().top - speed > middleUpperBound && ((farmerSprite.getGlobalBounds().left + farmerSprite.getGlobalBounds().width < middleRightBound && farmerSprite.getGlobalBounds().left + farmerSprite.getGlobalBounds().width > middleLeftBound) || (farmerSprite.getGlobalBounds().left < middleRightBound && farmerSprite.getGlobalBounds().left > middleLeftBound)))
                {
                    farmerSprite.move(0,-Math.abs(middleLowerBound - farmerSprite.getGlobalBounds().top));
                }
                else
                {
                    farmerSprite.move(0,-speed);
                }
            }
        }
    }

    public void drawObjects()
    {
        window.draw(backround);

        for(int i = 0; i < 24; i++)
        {
            window.draw(fieldsRectangles[i]);
        }

        window.draw(house);
        window.draw(farmerSprite);
        window.display();
    }

    public void playGame()
    {
        while(window.isOpen()) 
        {
            //Fill the window with red
            window.clear(Color.RED);

            this.movement();

            /*
            Following code will be useful for idenfying when the player is clicking on a certain crop or on the house etc...
            Right now doesnt do much but still a good starting point. 
            */
            if(Mouse.isButtonPressed(Mouse.Button.LEFT))
            {
                if(Mouse.getPosition(window).x >= house.getPosition().x && Mouse.getPosition(window).x <= house.getPosition().x + house.getSize().x && Mouse.getPosition(window).y >= house.getPosition().y && Mouse.getPosition(window).y <= house.getPosition().y + house.getSize().y)
                {
                    System.out.println("Test successful!");
                }
            }

            this.drawObjects();

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
