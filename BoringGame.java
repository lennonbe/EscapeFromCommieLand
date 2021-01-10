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


class BoringGame
{
   public static void main(String[] args)
   {
      //Bounds of the window
      final int width = 640;
      final int height = 480;

      //Create the window
      RenderWindow window = new RenderWindow();
      window.create(new VideoMode(width, height), "Escape From CommieLand!");

      //Limit the framerate
      window.setFramerateLimit(30);

      //Speed at which the player moves
      int speed = 15; 

      //Loading the farmer sprite
      Texture farmerTexture = new Texture();
      Path path = FileSystems.getDefault().getPath("BoringGame", "Man_Neutral.png");

      try {
         farmerTexture.loadFromFile(path);
      } catch (Exception e) {}

      Sprite farmerSprite = new Sprite(farmerTexture);

      //Formatting the sprite into the desired size
      Vector2f scale = new Vector2f((float)2.5, (float)2.5);
      farmerSprite.setScale(scale);

      //Set the initial position of the character
      farmerSprite.setPosition(320,240);

      //Main loop
      while(window.isOpen()) 
      {
         //Fill the window with red
         window.clear(Color.RED);

         /**
          * Works out the logic for the movement - W is up, A is left, etc...
          * Also enforced the bounds of the window, so that the character cannot move outside
          */
         if(Keyboard.isKeyPressed(Keyboard.Key.D ) && (farmerSprite.getPosition().x + farmerSprite.getGlobalBounds().width) < width ) {
            farmerSprite.move(speed, 0);
         } else if(Keyboard.isKeyPressed(Keyboard.Key.A) && farmerSprite.getPosition().x > 0) {
            farmerSprite.move(-speed, 0);
         } else if(Keyboard.isKeyPressed(Keyboard.Key.S) && (farmerSprite.getPosition().y + farmerSprite.getGlobalBounds().height) < height) {
            farmerSprite.move(0, speed);
         } else if(Keyboard.isKeyPressed(Keyboard.Key.W) && farmerSprite.getPosition().y > 0) {
            farmerSprite.move(0, -speed);
         }

         //Updates the window with the charactes new position
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