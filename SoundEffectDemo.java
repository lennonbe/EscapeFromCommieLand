import java.awt.*;
import java.awt.event.*;

public class SoundEffectDemo{
   
   // Constructor
   public SoundEffectDemo() {
      SoundEffect.init();
      SoundEffect.volume = SoundEffect.Volume.LOW;  // un-mute
             
    }
     
   public static void main(String[] args) {
      SoundEffectDemo();
      SoundEffect.EXPLODE.play();
   }
}