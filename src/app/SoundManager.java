package app;

import javax.sound.sampled.Clip;

public class SoundManager {

  public static SoundManager sharedInstance;

  public SoundManager() {
    if (sharedInstance == null) {
      sharedInstance = this;
    }
  }

  public static void playSound(String soundName) {
    Clip audioClip = AssetManager.getAudio(soundName);
    audioClip.start();
  }
}
