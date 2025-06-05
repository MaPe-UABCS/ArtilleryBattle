package app.controllers;

import app.Main;
import app.views.GameView;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class GameController extends Controller {

  GameView gameView;

  // Boat Placing in UI logic
  Boolean placingBoat;
  int selectedBoatSize;

  // GAME MAP
  int gameMap[][];
  // left
  boolean leftMapboatsReady;
  int leftMapAliveBoatsCells;
  // Both
  JButton lastJButtonSelectionBoat;
  // right
  boolean rightMapboatsReady;
  int rightMapAliveBoatsCells;

  public GameController() {
    gameView = (GameView) Main.getViewReference("Game");
    gameView.setActionListener(this);
    gameMap = new int[10][10];

    startNewGame();
    placingBoat = false;
  }

  public void startNewGame() {
    leftMapAliveBoatsCells = 0;
    leftMapboatsReady = false;
    rightMapboatsReady = false;
    rightMapAliveBoatsCells = 0;

    for (int x = 0; x < 10; x++) {
      for (int y = 0; y < 10; y++) {
        gameMap[x][y] = CellsStatus.water;
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (leftMapboatsReady) {
      return;
    }

    String command = e.getActionCommand();

    if (leftMapboatsReady == false || rightMapboatsReady == false) {
      if (command == "Ready") {
        if (leftMapboatsReady == false) {
          leftMapboatsReady = leftMapAliveBoatsCells == 17;
        } else if (rightMapboatsReady == false) {
          rightMapboatsReady = rightMapAliveBoatsCells == 17;
        }
      }

      // TODO:  si el x del gridButton, o boatSelectionButton es mayor a la mitad del la vista
      // entonces es de la derecha

      if (!placingBoat && command.charAt(0) == 'B') {
        selectedBoatSize = Integer.parseInt(command.charAt(4) + "");
        gameView.setSelectedBoat(command);
        placingBoat = true;
        lastJButtonSelectionBoat = (JButton) e.getSource();
        gameView.hideBoatSelectionButton(lastJButtonSelectionBoat);
        // hide the boat button
      } else if (placingBoat && command.charAt(0) == 'm') {
        placingBoat = false;

        JButton gridButton = (JButton) e.getSource();

        // calculate the logical coordinates of the boat
        int mapX = (int) gridButton.getLocation().getX() / gridButton.getWidth();
        int mapY = (int) gridButton.getLocation().getY() / gridButton.getHeight();
        boolean legal = placeBoatInMap(mapX, mapY, selectedBoatSize);

        selectedBoatSize = -99;

        if (legal) {
          // ok
          gameView.placeSelectedBoatInMap(gridButton.getLocation());
        } else {
          // undo the hide boat button
          gameView.showBoatSelectionButton(lastJButtonSelectionBoat);
          lastJButtonSelectionBoat = null;
        }
        gameView.unselectBoat();
      }
    }
  }

  public boolean placeBoatInMap(int x, int y, int size) {
    for (int w = 0; w < size; w++) {
      if (x + w > 9 || x < 0) return false;
      int mapCellValue = gameMap[x + w][y];
      if (mapCellValue == CellsStatus.boat) {
        return false;
      }
    }

    // all right make the changes
    GameBoat boat = new GameBoat(x, y, size);
    for (int w = 0; w < size; w++) {
      gameMap[x + w][y] = CellsStatus.boat;
    }
    if (leftMapboatsReady == false) {
      leftMapAliveBoatsCells += size;
    } else {
      rightMapAliveBoatsCells += size;
    }
    return true;
  }

  class CellsStatus {
    // CELLS values
    public static final int water = 0;
    public static final int blank = 1;
    public static final int boat = 2;
    public static final int hit = 3;
  }

  class GameBoat {
    int x;
    int y;
    int size;

    public GameBoat(int x, int y, int size) {
      this.x = x;
      this.y = y;
      this.size = size;
    }
  }
}
