package app.views;

import app.AssetManager;
import app.Style;
import app.views.components.AButton;
import app.views.components.ALabel;
import app.views.components.AnIconButton;
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
  boolean leftMapboatsReady;
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

    // boatSelected in the drag and dorp thing
    selectedBoat = new JLabel(AssetManager.getImageIcon("Boat3V.png"));
    selectedBoat.setBounds(0, 0, 43, 43 * 3);
    selectedBoat.setOpaque(false);
    add(selectedBoat);

    // Boats selection
    boatsContainer = new JPanel(null);
    {
      JButton boat5Button = new AnIconButton("Boat5H", this);
      boat5Button.setBounds(0, 0, 43 * 5, 43);
      boatsContainer.add(boat5Button);

      JButton boat3Button1 = new AnIconButton("Boat3H", this);
      boat3Button1.setBounds(43 * 5, 0, 43 * 3, 43);
      boatsContainer.add(boat3Button1);

      JButton boat2Button = new AnIconButton("Boat2H", this);
      boat2Button.setBounds(43 * 8, 43, 43 * 2, 43);
      boatsContainer.add(boat2Button);

      JButton boat4Button = new AnIconButton("Boat4H", this);
      boat4Button.setBounds(0, 43, 43 * 4, 43);
      boatsContainer.add(boat4Button);

      JButton boat3Button2 = new AnIconButton("Boat3H", this);
      boat3Button2.setBounds(43 * 5, 43, 43 * 3, 43);
      boatsContainer.add(boat3Button2);

      AButton boatsReadyButton = new AButton("Ready", 20, AButton.lightPrimary);
      boatsReadyButton.setBounds(43 * 8, 0, 43 * 2, 43);
      boatsContainer.add(boatsReadyButton);
    }
    boatsContainer.setBounds(130, 30, 430, 43 * 2);
    boatsContainer.setOpaque(false);
    add(boatsContainer);

    // Map
    {
      leftMap = new ArtilleryMap(this);
      leftMap.setBounds(129, 130, 440, 440);
      leftMap.setActionListener(this);
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

    if (leftMapboatsReady) {
      // TODO: remover this de la lista de acctions lisener del mapa
      return;
    }
    ;

    if (selectedBoat.getIcon() == null) {}

    // if the user clicked on a button in the boats seupt thing, then get the boat skin and set the
    // cursor following active

    if (dragAndDropAnimation.isRunning()) {
      dragAndDropAnimation.pause();
      // remove the image in the selectedBoat
      selectedBoat.setIcon(null);
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
