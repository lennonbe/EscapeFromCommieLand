import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
   
public enum SoundEffect {
   BGM("AudioFile/Bad_Mood.wav"),               // Bad Mood BGM
   BADWEATHER("AudioFile/Heavy_Rain_Bad_Weather.wav"), // Bad Weather
   CROPHARVEST("AudioFile/Crop_Harvest.wav"),          // Harvest The Crop
   CROPPLANT("AudioFile/Crop_Plant.wav"),              // Plant The Crop
   OPENINVENTORY("AudioFile/Open_Inventory.wav"),      // Open/Close The Inventory/Store
   PURCHASEITEM("AudioFile/Open_Store.wav"),           // Purchase The Item
   UNLOCKITEM("AudioFile/Unlock_New_Land_Option_1.wav"),        // Unlock New Item
   FAILGUNSHOT("AudioFile/Gun_Shot_Fail.wav"),                  // Fail Condition - Gun Shot
   FAILPRISON("AudioFile/GameFail2.wav"),                       // Fail Condition - Sent To Prison
   FAILOTHER("AudioFile/GameFail.wav"),                         // Fail Condition - Other Condition
   WIN("AudioFile/Steam_Boat.wav");                             // Win Condition

   public static enum Volume {
      MUTE, LOW, MEDIUM, HIGH
   }    //Volume Setting
   
   public static Volume volume = Volume.LOW;
   
   // Each sound effect has its own clip, loaded with its own sound file.
   private Clip clip;
   
   // Constructor to construct each element of the enum with its own sound file.
   SoundEffect(String soundFileName) {
      try {
         
         URL url = this.getClass().getClassLoader().getResource(soundFileName);
         // Set up an audio input stream piped from the sound file.
         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
         // Get a clip resource.
         clip = AudioSystem.getClip();
         // Open audio clip and load samples from the audio input stream.
         clip.open(audioInputStream);
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
   }
   
   // Play or Re-play the sound effect from the beginning, by rewinding.
   public void play() {
      if (volume != Volume.MUTE) {
         if (clip.isRunning())
            clip.stop();   // Stop the player if it is still running
         clip.setFramePosition(0); // rewind to the beginning
         clip.start();     // Start playing
      }
   }
   
   // Optional static method to pre-load all the sound files.
   static void init() {
      values(); // calls the constructor for all the elements
   }
