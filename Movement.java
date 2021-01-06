package Movement;

import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.io.*;


class BoringGame
{
   public static RenderWindow window = new RenderWindow();

   public static void main(String[] args)
   {
      final int width = 640;
      final int height = 480;

      //Create the window
      // RenderWindow window = new RenderWindow();
      window.create(new VideoMode(width, height), "Hello JSFML!");

      //Limit the framerate
      window.setFramerateLimit(30);

      //Speed at which the player moves
      int speed = 15; 

      //Loading the farmer sprite
      Texture farmerTexture = new Texture();
      Path path = FileSystems.getDefault().getPath("BoringGame", "Man_Neutral.png");

      try {
         farmerTexture.loadFromFile(path);
      } catch (Exception e) {
         //TODO: handle exception
      }

      Sprite farmerSprite = new Sprite(farmerTexture);

      //Formatting the sprite into the desired size
      Vector2f scale = new Vector2f((float)2.5, (float)2.5);

      farmerSprite.setPosition(320,240);
      farmerSprite.setScale(scale);
      System.out.println(farmerSprite.getScale()+ "2");

      //Testing --- Milosz
      System.out.println(farmerSprite.getLocalBounds());
      System.out.println(farmerSprite.getGlobalBounds());

      //End of Testing --- Milosz

      //Main loop
      while(window.isOpen()) 
      {
         //Fill the window with red
         window.clear(Color.RED);

         // System.out.println(Mouse.getPosition(window).x);

         if(Mouse.isButtonPressed(Mouse.Button.RIGHT)) {

            float speed2 = 50;

            float xMove = Mouse.getPosition(window).x - 
               (farmerSprite.getPosition().x + 
               (farmerSprite.getGlobalBounds().width / 2));

            float yMove = Mouse.getPosition(window).y - 
               (farmerSprite.getPosition().y + 
               (farmerSprite.getGlobalBounds().height / 2));

            float totalDistance = (float) Math.sqrt(Math.abs(xMove) + Math.abs(yMove));

            System.out.println("TotalDistance = " + totalDistance);
            System.out.println("xMove = " + xMove + ", yMove = " + yMove);

            for(int i = 0; i < (totalDistance); i++) {
               farmerSprite.move((xMove / totalDistance),
                                 (yMove / totalDistance));

               window.clear(Color.RED);
               window.draw(farmerSprite);
               window.display();

               try {
                  Thread.sleep(100);
               } catch(Exception e) {
                  // TODO: handle exception
               }
            }
         }
         
         // if(Keyboard.isKeyPressed(Keyboard.Key.D ) && (farmerSprite.getPosition().x + farmerSprite.getGlobalBounds().width) < width )
         // {
         //    farmerSprite.move(speed, 0);
         // }
         // else if(Keyboard.isKeyPressed(Keyboard.Key.A) && farmerSprite.getPosition().x > 0)
         // {
         //    farmerSprite.move(-speed, 0);
         // }
         // else if(Keyboard.isKeyPressed(Keyboard.Key.S) && (farmerSprite.getPosition().y + farmerSprite.getGlobalBounds().height) < height)
         // {
         //    farmerSprite.move(0, speed);
         // }
         // else if(Keyboard.isKeyPressed(Keyboard.Key.W) && farmerSprite.getPosition().y > 0)
         // {
         //    farmerSprite.move(0, -speed);
         // }

         // window.draw(circleHitbox);
         //farmerSprite.scale(scale);
         window.draw(farmerSprite);
         window.display();

         //Handle events
         for(Event event : window.pollEvents()) {
            if(event.type == Event.Type.CLOSED) 
            {
               //The user pressed the close button
               window.close();
            }
         }
      }

   }
}