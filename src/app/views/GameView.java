package app.views;

import app.AssetManager;
import app.Style;
import app.views.components.ALabel;
import app.views.components.ArtilleryMap;
import java.awt.BorderLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameView extends View implements ActionListener {

  // History
  JPanel historyContainer;
  int historySize = 27;
  Queue<ALabel> historyMoves;

  // drag&drop
  boolean boatsReady;
  JPanel boatsContainer;
  JLabel selectedBoat;
  Animation dragAndDropAnimation;
  Point viewPositionOnScren;

  ArtilleryMap leftMap, rightMap;

  public GameView() {
    super("Game");
    historyMoves = new LinkedList<ALabel>();
    setBackground(Style.getColor(Style.background));
    setBounds(0, 0, 1300, 600);

    // LateralPanel
    JPanel lateralPanel = new JPanel(new BorderLayout());
    {
      ALabel historyTitleLabel = new ALabel("History", 20, Style.foreground);
      lateralPanel.add(historyTitleLabel, BorderLayout.NORTH);

      historyContainer = new JPanel();
      historyContainer.setOpaque(false);
      historyContainer.setLayout(new BoxLayout(historyContainer, BoxLayout.Y_AXIS));
      lateralPanel.add(historyContainer);
    }
    lateralPanel.setOpaque(false);
    lateralPanel.setBounds(5, 5, 97, 563);
    add(lateralPanel);

    dragAndDropAnimation = boatsCursorFollowingAnimation();
    AnimationThread.registerAnimation(dragAndDropAnimation);
    // Boats
    boatsContainer = new JPanel(null);
    {
      selectedBoat = new JLabel(AssetManager.getImageIcon("Boat3V.png"));
      selectedBoat.setBounds(0, 0, 43, 43 * 3);
      selectedBoat.setBackground(Style.getColor(Style.foreground));
      selectedBoat.setOpaque(true);
      boatsContainer.add(selectedBoat);

      JButton boat5Button = new JButton(AssetManager.getImageIcon("Boat5H.png"));
      boat5Button.setActionCommand("Boat5H");
      boat5Button.setOpaque(false);
      boat5Button.setBorderPainted(false);
      boat5Button.setFocusPainted(false);
      boat5Button.setContentAreaFilled(false);
      boat5Button.setBounds(130, 36, 43 * 5, 43);
      boat5Button.addActionListener(this);
      boatsContainer.add(boat5Button);
    }
    boatsContainer.setOpaque(true);
    boatsContainer.setBackground(Style.getColor(Style.primary));
    boatsContainer.setBounds(0, 0, 600, 600);
    add(boatsContainer);

    // Map
    {
      leftMap = new ArtilleryMap(this);
      leftMap.setBounds(129, 130, 430, 430);
      add(leftMap);

      // rightMap = new ArtilleryMap();
      // rightMap.setBounds(500, 130, 430, 430);
      // add(rightMap);
    }
  }

  public void addMove2HistoryDisplay(String move) {
    ALabel moveLabel = new ALabel(move, 15, Style.foreground);
    historyMoves.add(moveLabel);
    if (historyMoves.size() > historySize) {
      ALabel topMove = historyMoves.poll();
      historyContainer.remove(topMove);
    }
    historyContainer.add(moveLabel);
    // historyContainer.add(Box.createRigidArea(new Dimension(0, 5)));
  }

  @Override
  public void before() {
    leftMap.setUpAnimation();
    leftMap.setMapActive(true);

    // rightMap.setUpAnimation();
    // rightMap.setMapActive(true);
  }

  @Override
  public void after() {
    leftMap.setMapActive(false);
    rightMap.setMapActive(false);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    System.out.println(e.getActionCommand());
    // if the user clicked on a button in the boats seupt thing, then get the boat skin and set the
    // cursor following active

    if (dragAndDropAnimation.isRunning()) {
      dragAndDropAnimation.pause();
    } else {
      dragAndDropAnimation.play();
    }
  }

  private Animation boatsCursorFollowingAnimation() {
    return new Animation() {
      @Override
      public void update() {
        if (!this.isRunning()) return;

        if (viewPositionOnScren == null) {
          viewPositionOnScren = new Point();
        }
        try {
          viewPositionOnScren.move(
              (int) getLocationOnScreen().getX(), (int) getLocationOnScreen().getY());
        } catch (Exception e) {
          System.out.println("a");
          return;
        }
        Point cursorPosition = MouseInfo.getPointerInfo().getLocation();

        int x = (int) (cursorPosition.getX() - viewPositionOnScren.getX());
        int y = (int) (cursorPosition.getY() - viewPositionOnScren.getY());
        selectedBoat.setLocation(x - selectedBoat.getWidth() / 2, y - selectedBoat.getHeight() / 2);
      }
    };
  }

  private void setCurrentBoatSkin(String boat) {}

  // BOATS!
  public String getBoatsLocation() {
    return "player1:x:y";
  }
}
