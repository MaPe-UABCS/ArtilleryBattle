package app;

import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ArtilleryMap extends JPanel implements Runnable {

  int fps;
  Point cursorPosition;
  Point center;
  ArrayList<JLabel> layers;
  GraphicsDevice graphicsDevice;

  int screenWidth;
  int screenHeight;
  int minScreenDimention;

  Thread animationThread;

  public ArtilleryMap(int fps) {
    // engine
    this.fps = fps;
    // center = new Point(960, 540);

    // swing things
    setLayout(null);
    setBackground(Color.decode("#3179d4"));

    // layers
    layers = new ArrayList<JLabel>();

    addLayer("Map_Cross_Grid");
    addLayer("Map_Snow");
    addLayer("Map_Mountain2");
    addLayer("Map_Mountain1");
    addLayer("Map_Land2");
    addLayer("Map_Land1");
    addLayer("Map_Sand");
    animationThread = new Thread(this);
  }

  private void addLayer(String imageName) {
    JLabel layer = new JLabel(AssetManager.getImageIcon(imageName));
    layer.setBounds(0, 0, 500, 500);
    layers.add(layer);
    this.add(layer);
  }

  @Override
  public void run() {
    float delay = (1f / fps) * 1000;
    if (center == null) {
      center =
          new Point(
              (int) getLocationOnScreen().getX() + 250, (int) getLocationOnScreen().getY() + 250);
    }

    while (animationThread.isAlive()) {
      try {
        update();
        Thread.sleep((long) delay);
      } catch (Exception e) {
        System.out.println(e.getMessage());
        animationThread.interrupt();
        return;
      }
    }
  }

  public void startAnimation() {
    animationThread.start();
    graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    screenWidth = graphicsDevice.getDisplayMode().getWidth();
    screenHeight = graphicsDevice.getDisplayMode().getHeight();
    minScreenDimention = screenWidth > screenHeight ? screenHeight : screenWidth;
  }

  // call every frame
  public void update() {
    // TODO: Solo ejecuta el resto del metodo si es visible en pantalla, o si un try get screen
    // location falla retornar

    cursorPosition = MouseInfo.getPointerInfo().getLocation();
    center.move((int) getLocationOnScreen().getX() + 250, (int) getLocationOnScreen().getY() + 250);

    for (int i = 0; i < layers.size(); i++) {
      // TODO: calcular una sola vez
      double layerWeigth = (i + 1) * 10;
      double cursorXMin = center.getX() - minScreenDimention / 2;
      double cursorXMax = center.getX() + minScreenDimention / 2;
      // -----------------------------
      double cursorX = clamp((cursorPosition.getX()), cursorXMin, cursorXMax);
      double xWeigth = 2 * (cursorX - cursorXMin) / (cursorXMax - cursorXMin) - 1;
      int x = (int) (0 + (layerWeigth * xWeigth));

      // TODO: calcular una sola vez
      double cursorYMin = center.getY() - minScreenDimention / 2;
      double cursorYMax = center.getY() + minScreenDimention / 2;
      // -----------------------------
      double cursorY = clamp((cursorPosition.getY()), cursorYMin, cursorYMax);
      double yWeigth = 2 * (cursorY - cursorYMin) / (cursorYMax - cursorYMin) - 1;
      int y = (int) (0 + (layerWeigth * yWeigth));

      JLabel layer = layers.get(i);
      layer.setBounds(x, y, 500, 500);
    }
  }

  private double clamp(double value, double min, double max) {
    if (value < min) {
      return min;
    } else if (value > max) {
      return max;
    } else {
      return value;
    }
  }
}
