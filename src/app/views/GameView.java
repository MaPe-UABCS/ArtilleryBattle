package app.views;

import app.AssetManager;
import app.Style;
import app.views.components.AButton;
import app.views.components.ALabel;
import app.views.components.AnIconButton;
import app.views.components.ArtilleryMap;
import app.views.components.MapHeader;
import java.awt.BorderLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameView extends View {

  // History
  JPanel historyContainer;
  int historySize = 30;
  Queue<ALabel> historyMoves;

  // drag&drop
  JPanel indicatorsLayer;
  ArrayList<JLabel> leftBoatsIndicators, rigthBoatsIndicators;
  JPanel leftBoatSelectionContainer;
  JPanel rightBoatSelectionContainer;
  JLabel selectedBoat;
  int selectedBoatSize;
  Animation dragAndDropAnimation;
  Point viewPositionOnScren;

  public MapHeader leftStatusContainer;
  public MapHeader rigthStatusContiner;

  private ArtilleryMap leftMap, rightMap;

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

    AButton exitButton = new AButton("Exit", 12, AButton.lightPrimary);
    exitButton.setBounds(615, 10, 40, 20);
    add(exitButton);

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
    lateralPanel.setBounds(5, 5, 150, 600);
    add(lateralPanel);

    dragAndDropAnimation = boatsCursorFollowingAnimation();
    AnimationThread.registerAnimation(dragAndDropAnimation);

    // boatSelected in the drag and dorp thing
    selectedBoat = new JLabel();
    selectedBoat.setBounds(0, 0, 43 * 5, 43);
    selectedBoat.setOpaque(false);
    selectedBoat.setHorizontalAlignment(JLabel.CENTER);
    add(selectedBoat);

    // Boats selection
    leftBoatSelectionContainer = new JPanel(null);
    leftBoatSelectionContainer.setOpaque(false);
    leftBoatSelectionContainer.setBackground(Style.getColor(Style.background));
    leftBoatSelectionContainer.setBounds(170, 10, 440, 43 * 2);
    leftBoatSelectionContainer.setBorder(
        BorderFactory.createLineBorder(Style.getColor(Style.foreground)));

    generateBoatSelectionButtons(leftBoatSelectionContainer, "L");
    add(leftBoatSelectionContainer);

    rightBoatSelectionContainer = new JPanel(null);
    rightBoatSelectionContainer.setBorder(
        BorderFactory.createLineBorder(Style.getColor(Style.foreground)));
    rightBoatSelectionContainer.setBounds(660, 10, 440, 43 * 2);
    rightBoatSelectionContainer.setOpaque(false);
    generateBoatSelectionButtons(rightBoatSelectionContainer, "R");
    add(rightBoatSelectionContainer);
    // Map
    {
      JLabel topRuleLeft = new JLabel(AssetManager.getImageIcon("TopRule.png"));
      topRuleLeft.setBounds(170, 90, 440, 50);
      add(topRuleLeft);
      JLabel sideRuleLeft = new JLabel(AssetManager.getImageIcon("SideRule.png"));
      sideRuleLeft.setBounds(138, 130, 40, 440);
      add(sideRuleLeft);

      leftMap = new ArtilleryMap(this);
      leftMap.setBounds(170, 130, 440, 440);
      leftMap.setSide("L");
      for (AbstractButton mapButtons : leftMap.getButtonsReference()) {
        this.buttons.add(mapButtons);
      }
      add(leftMap);

      JLabel topRuleR = new JLabel(AssetManager.getImageIcon("TopRule.png"));
      topRuleR.setBounds(660, 90, 440, 50);
      add(topRuleR);
      JLabel sideRuleR = new JLabel(AssetManager.getImageIcon("SideRule.png"));
      sideRuleR.setBounds(660 - (170 - 138), 130, 40, 440);
      add(sideRuleR);

      rightMap = new ArtilleryMap(this);
      rightMap.setSide("R");
      rightMap.setBounds(660, 130, 440, 440);
      for (AbstractButton mapButtons : rightMap.getButtonsReference()) {
        this.buttons.add(mapButtons);
      }
      add(rightMap);
    }

    // player status things
    leftStatusContainer = new MapHeader(false);
    leftStatusContainer.setBounds(170, 10, 440, 43 * 2);
    add(leftStatusContainer);

    rigthStatusContiner = new MapHeader(true);
    rigthStatusContiner.setBounds(660, 10, 440, 43 * 2);
    add(rigthStatusContiner);

    // side decoration
    JLabel crossGridDecoration = new JLabel(AssetManager.getImageIcon("CrossGridBeam.png"));
    crossGridDecoration.setBounds(1110, -14, 190, 600);
    add(crossGridDecoration);
  }

  private void generateBoatSelectionButtons(JPanel container, String side) {
    JButton boat5Button = new AnIconButton("Boat5H");
    boat5Button.setActionCommand(side + ":Boat5H");
    boat5Button.setBounds(0, 0, 43 * 5, 43);
    this.buttons.add(boat5Button);
    container.add(boat5Button);

    JButton boat3Button1 = new AnIconButton("Boat3H");
    boat3Button1.setActionCommand(side + ":Boat3H");
    boat3Button1.setBounds(43 * 5, 0, 43 * 3, 43);
    this.buttons.add(boat3Button1);
    container.add(boat3Button1);

    JButton boat2Button = new AnIconButton("Boat2H");
    boat2Button.setActionCommand(side + ":Boat2H");
    boat2Button.setBounds(43 * 8, 43, 43 * 2, 43);
    this.buttons.add(boat2Button);
    container.add(boat2Button);

    JButton boat4Button = new AnIconButton("Boat4H");
    boat4Button.setActionCommand(side + ":Boat4H");
    boat4Button.setBounds(0, 43, 43 * 4, 43);
    this.buttons.add(boat4Button);
    container.add(boat4Button);

    JButton boat3Button2 = new AnIconButton("Boat3H");
    boat3Button2.setActionCommand(side + ":Boat3H");
    boat3Button2.setBounds(43 * 5, 43, 43 * 3, 43);
    this.buttons.add(boat3Button2);
    container.add(boat3Button2);

    AButton boatsReadyButton = new AButton("Ready", 20, AButton.lightPrimary);
    boatsReadyButton.setActionCommand(side + ":Ready");
    boatsReadyButton.setBounds(43 * 8, 0, 43 * 2 + 10, 43);
    this.buttons.add(boatsReadyButton);
    container.add(boatsReadyButton);
  }

  public void addMove2HistoryDisplay(String move) {
    ALabel moveLabel = new ALabel(move, 14, Style.foreground);
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

    this.rigthBoatsIndicators = new ArrayList<JLabel>();
    rightMap.setUpAnimation();
    rightMap.setMapActive(false);
  }

  @Override
  public void after() {
    leftMap.setMapActive(false);
    rightMap.setMapActive(false);
  }

  public void setMapActive(String side, boolean status) {
    ArtilleryMap map = side.equals("L") ? leftMap : rightMap;
    map.setMapActive(status);
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

  public void placeSelectedBoatInMap(Point gridButtonLocation, boolean left) {

    ArtilleryMap map = left ? leftMap : rightMap;

    Icon indicatorIcon = selectedBoat.getIcon();
    JLabel boatIndicator = new JLabel(indicatorIcon);

    int indicatorX = map.getX() + (int) gridButtonLocation.getX();
    int indicatorY = map.getY() + (int) gridButtonLocation.getY();

    boatIndicator.setBounds(indicatorX, indicatorY, indicatorIcon.getIconWidth(), 43);
    indicatorsLayer.add(boatIndicator);
    if (left) {
      leftBoatsIndicators.add(boatIndicator);
    } else {
      rigthBoatsIndicators.add(boatIndicator);
    }

    dragAndDropAnimation.pause();
    selectedBoat.setIcon(null);
  }

  public void hideBoats(boolean left) {
    ArrayList<JLabel> boatsIndicator = left ? leftBoatsIndicators : rigthBoatsIndicators;

    for (JLabel boatIndicator : boatsIndicator) {
      indicatorsLayer.remove(boatIndicator);
    }
  }

  public void showBoats(boolean left) {
    ArrayList<JLabel> boatsIndicator = left ? leftBoatsIndicators : rigthBoatsIndicators;

    for (JLabel boatIndicator : boatsIndicator) {
      indicatorsLayer.add(boatIndicator);
    }
  }

  public void hideBoatSelectionButton(JButton button, String side) {
    if (side.equals("L")) {
      leftBoatSelectionContainer.remove(button);
    } else {
      rightBoatSelectionContainer.remove(button);
    }
  }

  public void showBoatSelectionButton(JButton button, String side) {
    if (side.equals("L")) {
      leftBoatSelectionContainer.add(button);
    } else {
      rightBoatSelectionContainer.add(button);
    }
  }

  public void hideBoatSelectionContainer(String side) {
    JPanel container = side.equals("L") ? leftBoatSelectionContainer : rightBoatSelectionContainer;
    remove(container);
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

  public void markHitInMap(String side, int x, int y) {
    // seee para este punto deveria tener un variable que guarde el mapa aactivo
    ArtilleryMap map = side.equals("L") ? leftMap : rightMap;
    map.setCellStatus(x, y, ArtilleryMap.CellStatuses.hit);
    map.repaint();
  }

  // deberia ser el mismo metodo pero ya no quiero pasar el enum hasta el controller
  public void markBlankInMap(String side, int x, int y) {
    // seee para este punto deveria tener un variable que guarde el mapa aactivo
    ArtilleryMap map = side.equals("L") ? leftMap : rightMap;
    map.setCellStatus(x, y, ArtilleryMap.CellStatuses.blank);
    map.repaint();
  }
}
