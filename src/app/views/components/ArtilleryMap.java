package app.views.components;

import app.AssetManager;
import app.Style;
import app.views.Animation;
import app.views.AnimationThread;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ArtilleryMap extends JPanel {

  Point cursorPosition;
  Point center;
  ArrayList<JLabel> layers;
  JLabel coordinatesLabel;
  GraphicsDevice graphicsDevice;
  int screenWidth;
  int screenHeight;
  int minScreenDimention;
  // AIM
  CursorAimLine horizontalAim;
  CursorAimLine verticalAim;
  Animation parallaxAnimation;

  // PauseScreen
  JPanel buttonsGrid;
  JLabel pauseScreen;

  GridButton buttonsMatrix[][];

  ArrayList<AbstractButton> buttonsWithActionListener;

  private int size = 440;

  public ArtilleryMap() {

    // swing things
    setLayout(null);
    setBackground(Style.getColor(Style.background));
    buttonsWithActionListener = new ArrayList<AbstractButton>();

    // coordinatres
    coordinatesLabel = new JLabel();
    coordinatesLabel.setForeground(Color.white);
    coordinatesLabel.setFont(AssetManager.getFont("light:10:plain"));
    add(coordinatesLabel);

    // pause screen
    pauseScreen = new JLabel("Waiting for Command autorization...");
    pauseScreen.setOpaque(true);
    pauseScreen.setBounds(0, 0, size, size);
    pauseScreen.setBackground(Color.black);
    pauseScreen.setForeground(Color.green);

    // cursor
    horizontalAim = new CursorAimLine(CursorAimLine.AimAxis.horizontal);
    verticalAim = new CursorAimLine(CursorAimLine.AimAxis.vertial);
    add(horizontalAim);
    add(verticalAim);

    // layers
    layers = new ArrayList<JLabel>();
    addLayer("GridM2.png");
    addLayer("MapL1.png");
    addLayer("MapL2.png");
    addLayer("MapL3.png");
    addLayer("MapL4.png");
    addLayer("MapL5.png");
    addLayer("MapL6.png");

    // buttons
    buttonsMatrix = new GridButton[10][10];
    GridLayout gridLayout = new GridLayout(10, 10);
    gridLayout.setHgap(0);
    gridLayout.setVgap(0);
    buttonsGrid = new JPanel(gridLayout);
    for (int r = 0; r < 10; r++) {
      for (int c = 0; c < 10; c++) {
        GridButton gridButton = new GridButton(c, r);
        buttonsMatrix[c][r] = gridButton;
        buttonsGrid.add(gridButton);
        buttonsWithActionListener.add(gridButton);
      }
    }
    buttonsGrid.setBounds(0, 0, size, size);
    add(buttonsGrid);

    // parallaxAnimation
    parallaxAnimation = parallaxAnimation();
    AnimationThread.registerAnimation(parallaxAnimation);

    // always at the end
    setMapActive(false);
  }

  private void addLayer(String imageName) {
    JLabel layer = new JLabel(AssetManager.getImageIcon(imageName));
    layer.setBounds(0, 0, size, size);
    layers.add(layer);
    this.add(layer);
  }

  private Animation parallaxAnimation() {
    return new Animation() {
      @Override
      public void update() {
        if (!this.isRunning()) return;

        cursorPosition = MouseInfo.getPointerInfo().getLocation();
        center.move(
            (int) getLocationOnScreen().getX() + getWidth() / 2,
            (int) getLocationOnScreen().getY() + getHeight() / 2);

        // Aim
        int actualCursorX = (int) (cursorPosition.getX() - center.getX() + getWidth() / 2);
        int actualCursorY = (int) (cursorPosition.getY() - center.getY() + getHeight() / 2);

        horizontalAim.setPositionInAxis(actualCursorX);
        verticalAim.setPositionInAxis(actualCursorY);

        coordinatesLabel.setBounds(actualCursorX + 10, actualCursorY, 200, 20);
        coordinatesLabel.setText("[ " + cursorPosition.getX() + "," + cursorPosition.getY() + "]");

        // Layers parallax efect
        for (int i = 0; i < layers.size(); i++) {
          // TODO: calcular una sola vez
          double layerWeigth = (i) * 10;
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
          layer.setBounds(x, y, size, size);
        }
      }
    };
  }

  public enum CellStatuses {
    hit,
    blank,
  }

  public void setCellStatus(int column, int row, CellStatuses status) {
    // TODO: aparecer un flash de blanco y rojo animacion disparo parpadeo, independientemente del
    // resultado, sonido asi de click disparo explocion bit moderno aestetic
    if (status == CellStatuses.hit) {
      buttonsMatrix[column][row].setAsHit();
    } else if (status == CellStatuses.blank) {
      buttonsMatrix[column][row].setAsBlank();
    }
  }

  public void setUpAnimation() {
    graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    screenWidth = graphicsDevice.getDisplayMode().getWidth();
    screenHeight = graphicsDevice.getDisplayMode().getHeight();
    minScreenDimention = screenWidth > screenHeight ? screenHeight : screenWidth;
    center = new Point();
  }

  public void setMapActive(boolean active) {
    if (active) {
      parallaxAnimation.play();
      remove(pauseScreen);
      for (JLabel layer : layers) {
        add(layer);
      }
      add(buttonsGrid);
    } else {
      parallaxAnimation.pause();
      remove(buttonsGrid);
      for (JLabel layer : layers) {
        remove(layer);
      }
      add(pauseScreen);
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

  private class GridButton extends JButton {
    public GridButton(int x, int y) {
      setActionCommand("map:" + x + "," + y);
      // setOpaque(true);
      // setContentAreaFilled(false);
      setBorderPainted(false);
      setFocusPainted(false);
      setBackground(Color.black);
      // setBackground(Color.red);
    }

    public void setAsBlank() {
      // TODO: llamar al asset manager y colocar algun tipo circulo o punto, blanco o gris
      setIcon(AssetManager.getImageIcon("blank.png"));
      // setBackground(Color.white);
    }

    public void setAsHit() {
      // TODO: llamar al asset manager y colocar un icono de hit una cruz o algo de color rojo
      setBackground(Color.red);
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
        this.setBounds(positionInAxis, 0, CursorAimLine.stroke, size);
      } else {
        this.setBounds(0, positionInAxis, size, CursorAimLine.stroke);
      }
    }
  }

  public void setActionListener(ActionListener listener) {
    for (AbstractButton button : buttonsWithActionListener) {
      button.addActionListener(listener);
    }
  }
}
