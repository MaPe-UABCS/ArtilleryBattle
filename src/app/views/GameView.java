package app.views;

import app.Style;
import app.views.components.ALabel;
import app.views.components.ArtilleryMap;
import java.awt.BorderLayout;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class GameView extends View {

  JPanel historyContainer;
  int historySize = 27;
  Queue<ALabel> historyMoves;

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

    // Map
    {
      leftMap = new ArtilleryMap();
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
}
