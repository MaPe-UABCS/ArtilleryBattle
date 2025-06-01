package app;

import javax.sound.sampled.Clip;

public class SoundManager {

  public static SoundManager sharedInstance;

  public SoundManager() {
    if (sharedInstance == null) {
      sharedInstance = this;
    }
  }

  public void playSound(String soundName) {
//    Clip audioClip = AssetManager.getAudio(soundName);
//    audioClip.start();
//    Thread stopThread =
//        new Thread() {
//          public void start() {
//            while (!audioClip.isRunning()) {
//              audioClip.stop();
//              System.out.println("stoping sound");
//              break;
//            }
//          }
//          ;
//        };
//    stopThread.start();
  }
}
