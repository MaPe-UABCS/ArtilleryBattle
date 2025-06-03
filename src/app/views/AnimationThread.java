package app.views;

import java.util.ArrayList;

public class AnimationThread extends Thread {

  private static AnimationThread sharedInstace;
  int fps;
  Thread animationThread;

  ArrayList<Animation> animatedElements;

  public AnimationThread(int fps) {
    if (sharedInstace == null) {
      sharedInstace = this;
    }
    animatedElements = new ArrayList<Animation>();
    this.fps = fps;
  }

  public static void registerAnimation(Animation animation) {
    sharedInstace.animatedElements.add(animation);
  }

  @Override
  public void run() {
    while (this.isAlive()) {
      for (Animation animaton : animatedElements) {
        if (animaton.isRunning()) {
          animaton.update();
        }
      }
      try {
        Thread.sleep((long) (1f / fps * 1000));
      } catch (Exception e) {
        System.out.println(e.getMessage());
        break;
      }
    }
  }
}
