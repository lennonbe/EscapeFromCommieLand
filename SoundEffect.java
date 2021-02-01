package BoringGame;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import java.util.Observable;
import java.util.Observer;
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

/**
 * The Options Listed For Playing
 * Each Option Contains The URL path and the file name for user to choose to play
 */
public enum SoundEffect {
   BGM("AudioFile/Bad_Mood.wav"),                      // Bad Mood BGM
   BGM2("AudioFile/BGM_option2.wav"),                  // Option 2 BGM
   //BGM3("AudioFile/BGM3.wav"),                         // Option 3 BGM
   BADWEATHER("AudioFile/Heavy_Rain_Bad_Weather.wav"), // Bad Weather
   CROPHARVEST("AudioFile/Crop_Harvest.wav"),          // Harvest The Crop
   CROPPLANT("AudioFile/Crop_Plant.wav"),              // Plant The Crop
   OPENINVENTORY("AudioFile/Open_Inventory.wav"),      // Open/Close The Inventory/Store
   PURCHASEITEM("AudioFile/Open_Store.wav"),           // Purchase The Item
   UNLOCKITEM("AudioFile/Unlock_New_Land_Option_1.wav"),        // Unlock New Item
   UPGRADEITEM("AudioFile/Item_Upgrade.wav"),                   // Unlock New Item
   FAILGUNSHOT("AudioFile/Gun_Shot_Fail.wav"),                  // Fail Condition - Gun Shot
   FAILPRISON("AudioFile/GameFail2.wav"),                       // Fail Condition - Sent To Prison
   FAILOTHER("AudioFile/GameFail.wav"),                         // Fail Condition - Other Condition
   WIN("AudioFile/Steam_Boat.wav");                             // Win Condition

   public static enum Volume{
      MUTE, LOW, MEDIUM, HIGH
   } 
   public static Volume volume = Volume.LOW;
   private Clip clip;
   
   /**
    * The Function Used To Parse In The WAV File By Using Assigned URL
    * @param soundFileName URL And File Name
    */
   SoundEffect(String soundFileName){
      try {
         URL url = this.getClass().getClassLoader().getResource(soundFileName);
         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
         clip = AudioSystem.getClip();
         clip.open(audioInputStream);
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
   }
   
   /**
    * The Function Used To Play And Replay The Selected Sound Option
    */
   public void play() {
      if (volume != Volume.MUTE){
         if (clip.isRunning())
            clip.stop();           // Stop the player if it is still running
         clip.setFramePosition(0); // Back to beginning and replay
         clip.start();             // Start playing
      }
   }

   /**
    *  The Function Used To Stop The Selected Sound Option
    */
    public void stop(){
       clip.stop();
    }
   
   /**
    *  Optional static method to pre-load all the sound files.
    */
   static void init(){
      values();   // calls the constructor for all the elements
   }
   
   /**
    *  Play the music looply
    *  Usually used for BGM playing
    */
   public void loopPlay() {
      try{
         if(volume != Volume.MUTE){
            clip.setFramePosition(0); // Back to beginning and replay
            clip.start();             // Start playing
            clip.loop(Clip.LOOP_CONTINUOUSLY);
         }
         else{}
      }

      catch(Exception e){
         e.printStackTrace();
      }
   }
}