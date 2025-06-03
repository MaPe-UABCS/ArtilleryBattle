package app.views;

public abstract class Animation {

  boolean running;

  public void play() {
    running = true;
  }

  public void pause() {
    running = false;
  }

  public boolean isRunning() {
    return running;
  }

  /** Update runs like 60 times per second */
  public abstract void update();
}
