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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameView extends View {

  // History
  JPanel historyContainer;
  int historySize = 27;
  Queue<ALabel> historyMoves;

  // drag&drop
  JPanel indicatorsLayer;
  ArrayList<JLabel> leftBoatsIndicators, rigthBoatsIndicators;
  JPanel boatsContainer;
  JLabel selectedBoat;
  int selectedBoatSize;
  Animation dragAndDropAnimation;
  Point viewPositionOnScren;

  ArtilleryMap leftMap, rightMap;

  public GameView() {
    super("Game");
    historyMoves = new LinkedList<ALabel>();
    setBackground(Style.getColor(Style.background));
    setBounds(0, 0, 1300, 600);
    indicatorsLayer = new JPanel();
    indicatorsLayer.setLayout(null);
    indicatorsLayer.setOpaque(false);
    indicatorsLayer.setBounds(0, 0, 1300, 600);

    add(indicatorsLayer);
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
    selectedBoat = new JLabel();
    selectedBoat.setBounds(0, 0, 43 * 5, 43);
    selectedBoat.setOpaque(false);
    selectedBoat.setHorizontalAlignment(JLabel.CENTER);
    add(selectedBoat);

    // leftBoatsIndicators = new ArrayList<JLabel>();
    // TODO: also for right

    // Boats selection
    boatsContainer = new JPanel(null);
    {
      JButton boat5Button = new AnIconButton("Boat5H");
      boat5Button.setBounds(0, 0, 43 * 5, 43);
      this.buttons.add(boat5Button);
      boatsContainer.add(boat5Button);

      JButton boat3Button1 = new AnIconButton("Boat3H");
      boat3Button1.setBounds(43 * 5, 0, 43 * 3, 43);
      this.buttons.add(boat3Button1);
      boatsContainer.add(boat3Button1);

      JButton boat2Button = new AnIconButton("Boat2H");
      boat2Button.setBounds(43 * 8, 43, 43 * 2, 43);
      this.buttons.add(boat2Button);
      boatsContainer.add(boat2Button);

      JButton boat4Button = new AnIconButton("Boat4H");
      boat4Button.setBounds(0, 43, 43 * 4, 43);
      this.buttons.add(boat4Button);
      boatsContainer.add(boat4Button);

      JButton boat3Button2 = new AnIconButton("Boat3H");
      boat3Button2.setBounds(43 * 5, 43, 43 * 3, 43);
      this.buttons.add(boat3Button2);
      boatsContainer.add(boat3Button2);

      AButton boatsReadyButton = new AButton("Ready", 20, AButton.lightPrimary);
      boatsReadyButton.setBounds(43 * 8, 0, 43 * 2, 43);
      this.buttons.add(boatsReadyButton);
      boatsContainer.add(boatsReadyButton);
    }
    boatsContainer.setBounds(130, 30, 430, 43 * 2);
    boatsContainer.setOpaque(false);
    add(boatsContainer);

    // Map
    {
      leftMap = new ArtilleryMap(this);
      leftMap.setBounds(129, 130, 440, 440);
      // leftMap.setActionListener(this);
      for (AbstractButton mapButtons : leftMap.getButtonsReference()) {
        this.buttons.add(mapButtons);
      }
      add(leftMap);

      // rightMap = new ArtilleryMap();
      // rightMap.setBounds(500, 130, 430, 430);
      // add(rightMap);
    }
    // TODO: do the same with rigth map

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
    this.leftBoatsIndicators = new ArrayList<JLabel>();
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

  public void setSelectedBoat(String boatName) {
    int boatSize = Integer.parseInt(boatName.charAt(4) + "");
    selectedBoatSize = boatSize;
    selectedBoat.setBounds(0, 0, 43 * boatSize, 43);
    selectedBoat.setIcon(AssetManager.getImageIcon(boatName + ".png"));
    dragAndDropAnimation.play();
  }

  public void unselectBoat() {
    selectedBoat.setIcon(null);
    dragAndDropAnimation.pause();
  }

  public void placeSelectedBoatInMap(Point gridButtonLocation) {
    // JButton targetButton = (JButton) e.getSource();
    // targetButton.getLocation();

    // Preguntar al artilleryBattle si es legal la ubicaion

    // TODO: crea un array list de JLaels que guardan los botes left y otro para rigth
    // ademas de 2 metodos para ocultarl left y rigth
    Icon indicatorIcon = selectedBoat.getIcon();
    JLabel boatIndicator = new JLabel(indicatorIcon);

    int indicatorX = leftMap.getX() + (int) gridButtonLocation.getX();
    int indicatorY = leftMap.getY() + (int) gridButtonLocation.getY();

    boatIndicator.setBounds(indicatorX, indicatorY, indicatorIcon.getIconWidth(), 43);
    indicatorsLayer.add(boatIndicator);

    dragAndDropAnimation.pause();
    selectedBoat.setIcon(null);
  }

  public void hideBoats(boolean left) {
    ArrayList<JLabel> boatsIndicator = left ? leftBoatsIndicators : rigthBoatsIndicators;

    for (JLabel boatIndicator : boatsIndicator) {
      boatsIndicator.remove(boatIndicator);
    }
  }

  public void hideBoatSelectionButton(JButton button) {
    boatsContainer.remove(button);
  }

  public void showBoatSelectionButton(JButton button) {
    boatsContainer.add(button);
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
          System.out.println("aiuda");
          return;
        }
        Point cursorPosition = MouseInfo.getPointerInfo().getLocation();

        int x = (int) (cursorPosition.getX() - viewPositionOnScren.getX());
        int y = (int) (cursorPosition.getY() - viewPositionOnScren.getY());
        selectedBoat.setLocation(x, y - selectedBoat.getHeight() / 2);
      }
    };
  }

  // BOATS!
  public String getBoatsLocation() {
    return "player1:x:y";
  }
}
