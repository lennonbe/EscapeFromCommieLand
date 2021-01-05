package Movement;

import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;

class Movement
{
   public static void main(String[] args)
   {
      //Create the window
      RenderWindow window = new RenderWindow();
      window.create(new VideoMode(640, 480), "Hello JSFML!");

      //Limit the framerate
      window.setFramerateLimit(30);

      //Main loop
      while(window.isOpen()) {
         //Fill the window with red
         window.clear(Color.RED);

         //Display what was drawn (... the red color!)
         window.display();

         //Handle events
         for(Event event : window.pollEvents()) {
            if(event.type == Event.Type.CLOSED) {
               //The user pressed the close button
               window.close();
            }
         }
      }
   }
}
