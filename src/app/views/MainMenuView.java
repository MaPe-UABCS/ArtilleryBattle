package app.views;

import app.AssetManager;
import app.Style;
import app.views.components.AButton;
import app.views.components.ALabel;
import java.awt.Component;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainMenuView extends View {
  Point cursorPosition;
  Point center;
  ArrayList<Component> layers;
  GraphicsDevice graphicsDevice;
  int screenWidth;
  int screenHeight;
  int minScreenDimention;
  Animation parallaxAnimation;

  // Reactive Elements
  ALabel userSessionLabel;

  public MainMenuView() {
    super("MainMenu");
    setLayout(null);

    ALabel gameTitle = new ALabel("< Artillery Battle >", "light:30:bold", Style.foreground);
    gameTitle.setHorizontalAlignment(SwingConstants.CENTER);
    gameTitle.setBounds(0, 0, 600, 100);
    add(gameTitle);

    // UI
    JPanel uiContainer = new JPanel();
    uiContainer.setLayout(null);
    {
      JLabel frameForButtons =
          new JLabel(AssetManager.getImageIcon("MainMenuButtonsLayoutThing.png"));
      frameForButtons.setBounds(0, 0, 600, 600);
      uiContainer.add(frameForButtons);

      String buttonsFontCommand = "light:18:plain";

      AButton playButton = new AButton("Single Player", buttonsFontCommand, AButton.lightPrimary);
      playButton.setHorizontalAlignment(SwingConstants.CENTER);
      playButton.setBounds(430, 134, 150, 30);
      registerButton(playButton);
      uiContainer.add(playButton);

      AButton twoPlayerButton = new AButton("2 Players", buttonsFontCommand, AButton.lightPrimary);
      twoPlayerButton.setHorizontalAlignment(SwingConstants.CENTER);
      twoPlayerButton.setBounds(24, 241, 150, 30);
      registerButton(twoPlayerButton);
      uiContainer.add(twoPlayerButton);

      AButton lanButton = new AButton("LAN Multiplayer", buttonsFontCommand, AButton.lightPrimary);
      lanButton.setHorizontalAlignment(SwingConstants.CENTER);
      lanButton.setBounds(24, 280, 150, 30);
      registerButton(lanButton);
      uiContainer.add(lanButton);

      AButton aboutButton = new AButton("About", buttonsFontCommand, AButton.lightPrimary);
      aboutButton.setHorizontalAlignment(SwingConstants.CENTER);
      aboutButton.setBounds(24, 337, 150, 30);
      registerButton(aboutButton);
      uiContainer.add(aboutButton);

      userSessionLabel = new ALabel("Playing as a guest", buttonsFontCommand);
      userSessionLabel.setOpaque(true);
      userSessionLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      userSessionLabel.setBackground(Style.getColor(Style.background));
      userSessionLabel.setForeground(Style.getColor(Style.foreground));
      userSessionLabel.setBounds(24, 536, 200, 30);
      uiContainer.add(userSessionLabel);

      AButton logoutButton = new AButton(AButton.lightPrimary);
      logoutButton.setIcon(AssetManager.getImageIcon("offIcon.png"));
      logoutButton.setBounds(230, 536, 28, 28);
      registerButton(logoutButton, "logout");
      uiContainer.add(logoutButton);
    }

    uiContainer.setOpaque(false);
    uiContainer.setBounds(0, 0, 600, 600);
    add(uiContainer);

    // animation setup
    center = new Point();
    layers = new ArrayList<Component>();
    // AnimatedLayers
    layers.add(uiContainer);

    addLayer("MenuCanon.png");
    addLayer("MenuGround.png");

    JLabel sky = new JLabel(AssetManager.getImageIcon("MenuSky.png"));
    sky.setBounds(2960 / 2, 0, 2960, 620);
    add(sky);
    layers.add(sky);

    this.parallaxAnimation = parallaxAnimation();
    AnimationThread.registerAnimation(this.parallaxAnimation);
  }

  @Override
  public void before() {
    setUpAnimation();
    parallaxAnimation.play();
  }

  public void setUpAnimation() {
    graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    screenWidth = graphicsDevice.getDisplayMode().getWidth();
    screenHeight = graphicsDevice.getDisplayMode().getHeight();
    minScreenDimention = screenWidth > screenHeight ? screenHeight : screenWidth;
  }

  private void addLayer(String imageName) {
    JLabel layer = new JLabel(AssetManager.getImageIcon(imageName));
    layer.setBounds(0, 0, 600, 600);
    layers.add(layer);
    this.add(layer);
  }

  private Animation parallaxAnimation() {
    return new Animation() {
      @Override
      public void update() {
        if (!this.isRunning()) return;
        try {
          center.move(
              (int) getLocationOnScreen().getX() + getWidth() / 2,
              (int) getLocationOnScreen().getY() + getHeight() / 2);
        } catch (Exception e) {
          System.out.println("wtf");
          return;
        }

        cursorPosition = MouseInfo.getPointerInfo().getLocation();

        // Layers parallax efect
        for (int i = 0; i < layers.size(); i++) {
          double layerWeigth = (i + 1) * 7;
          double cursorXMin = center.getX() - minScreenDimention / 2;
          double cursorXMax = center.getX() + minScreenDimention / 2;
          double cursorX = clamp((cursorPosition.getX()), cursorXMin, cursorXMax);
          double xWeigth = 2 * (cursorX - cursorXMin) / (cursorXMax - cursorXMin) - 1;
          int x = (int) (0 + (layerWeigth * xWeigth));

          double cursorYMin = center.getY() - minScreenDimention / 2;
          double cursorYMax = center.getY() + minScreenDimention / 2;
          double cursorY = clamp((cursorPosition.getY()), cursorYMin, cursorYMax);
          double yWeigth = 2 * (cursorY - cursorYMin) / (cursorYMax - cursorYMin) - 1;
          int y = (int) (0 + (layerWeigth * yWeigth));

          // System.out.println(" x: " + x + " , y: " + y);
          Component layer = layers.get(i);

          if (i == 3) {
            // Capa 3, el Cielo
            x += -2400 / 2;
            y -= 50;
          }

          layer.setLocation(x, y);
        }
      }
    };
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
