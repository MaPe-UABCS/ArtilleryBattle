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
  boolean animationRuning;

  // AIM
  CursorAimLine horizontalAim;
  CursorAimLine verticalAim;

  public ArtilleryMap(int fps) {
    // engine
    this.fps = fps;
    animationRuning = false;

    // swing things
    setLayout(null);
    setBackground(Color.black);

    horizontalAim = new CursorAimLine(CursorAimLine.AimAxis.horizontal);
    verticalAim = new CursorAimLine(CursorAimLine.AimAxis.vertial);
    add(horizontalAim);
    add(verticalAim);

    // layers
    layers = new ArrayList<JLabel>();
    addLayer("GridM2");
    addLayer("MapL1");
    addLayer("MapL2");
    addLayer("MapL3");
    addLayer("MapL4");
    addLayer("MapL5");
    addLayer("MapL6");
    animationThread = new Thread(this);
  }

  private void addLayer(String imageName) {
    JLabel layer = new JLabel(AssetManager.getImageIcon(imageName));
    layer.setBounds(0, 0, 800, 800);
    layers.add(layer);
    this.add(layer);
  }

  @Override
  public void run() {
    float delay = (1f / fps) * 1000;
    if (center == null) {
      center =
          new Point(
              (int) getLocationOnScreen().getX() + getWidth() / 2,
              (int) getLocationOnScreen().getY() + getHeight() / 2);
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
    graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    screenWidth = graphicsDevice.getDisplayMode().getWidth();
    screenHeight = graphicsDevice.getDisplayMode().getHeight();
    minScreenDimention = screenWidth > screenHeight ? screenHeight : screenWidth;
    resumeAnimation();
    animationThread.start();
  }

  public void pauseAnimation() {
    animationRuning = false;
  }

  public void resumeAnimation() {
    animationRuning = true;
  }

  // call every frame
  public void update() {
    if (!animationRuning) return;
    // TODO: Solo ejecuta el resto del metodo si es visible en pantalla, o si un try get screen
    // location falla retornar

    cursorPosition = MouseInfo.getPointerInfo().getLocation();
    center.move(
        (int) getLocationOnScreen().getX() + getWidth() / 2,
        (int) getLocationOnScreen().getY() + getHeight() / 2);

    // Aim
    horizontalAim.setPositionInAxis((int) (cursorPosition.getX() - center.getX() + getWidth() / 2));
    verticalAim.setPositionInAxis((int) (cursorPosition.getY() - center.getY() + getHeight() / 2));

    // Layers parallax efect
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
      layer.setBounds(x, y, 800, 800);
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

  private class CursorAimLine extends JPanel {

    public enum AimAxis {
      horizontal,
      vertial
    }

    AimAxis axis;
    private static int stroke = 1;

    public CursorAimLine(AimAxis axis) {
      this.axis = axis;
      setOpaque(true);
      setBackground(Color.gray);
    }

    public void setPositionInAxis(int positionInAxis) {
      if (this.axis == AimAxis.horizontal) {
        this.setBounds(positionInAxis, 0, CursorAimLine.stroke, 800);
      } else {
        this.setBounds(0, positionInAxis, 800, CursorAimLine.stroke);
      }
    }
  }
}
