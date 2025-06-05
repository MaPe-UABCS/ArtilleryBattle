package app.controllers;

import app.Main;
import app.views.GameView;
import app.views.View;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class GameController extends Controller {

  GameView gameView;

  // Boat Placing in UI logic
  Boolean placingBoat;
  int selectedBoatSize;

  // gameMode
  boolean singlePlayer;

  // GAME MAP
  int leftGameMap[][];
  int rightGameMap[][];
  // left
  boolean leftMapboatsReady;
  int leftMapAliveBoatsCells;
  // Both
  JButton lastJButtonSelectionBoat;
  // right
  boolean rightMapboatsReady;
  int rightMapAliveBoatsCells;

  public GameController() {
    // Main menu setup
    View menuView = Main.getViewReference("MainMenu");
    menuView.setActionListener(this);

    gameView = (GameView) Main.getViewReference("Game");
    gameView.setActionListener(this);
    leftGameMap = new int[10][10];
    rightGameMap = new int[10][10];
    startNewGame();
    placingBoat = false;
  }

  public void startNewGame() {
    leftMapAliveBoatsCells = 0;
    leftMapboatsReady = false;
    rightMapboatsReady = false;
    rightMapAliveBoatsCells = 0;

    // gameView.setMapActive("L", true);
    // gameView.leftMap.setMapActive(true);
    // gameView.setMapActive("R", false);

    for (int x = 0; x < 10; x++) {
      for (int y = 0; y < 10; y++) {
        leftGameMap[x][y] = CellsStatus.water;
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (Main.getCurrentView() == Main.getViewReference("MainMenu")) {
      actionPerformedInMainMenu(e);
    } else {
      actionPerformedInGameView(e);
    }
  }

  private void actionPerformedInMainMenu(ActionEvent e) {
    String command = e.getActionCommand().split(":")[1];
    System.out.println(command);
    switch (command) {
      case "Single Player":
        System.out.println("here");
        Main.changeView("Game");
        singlePlayer = true;
        leftMapboatsReady = false;
        gameView.hideBoatSelectionContainer("R");
        break;
      case "2 Players":
        Main.changeView("Game");
        singlePlayer = false;
        // gameView.hideBoatSelectionContainer("R");
        break;
      case "LAN":
      // TODO: open Lan Menu
      default:
        break;
    }
  }

  private void actionPerformedInGameView(ActionEvent e) {
    String commandArray[] = e.getActionCommand().split(":");
    String side = commandArray[0];
    String command = commandArray[1];

    if (leftMapboatsReady && rightMapboatsReady) {
      // TODO: handle shooting

      return;
    }

    // And not in game
    // if (side.equals("L") && leftMapboatsReady) {
    //   return;
    // }

    if (leftMapboatsReady == false || rightMapboatsReady == false) {

      if (command.equals("Ready")) {

        if (leftMapboatsReady == false && side.equals("L")) {
          leftMapboatsReady = leftMapAliveBoatsCells == 17;
          if (leftMapboatsReady) {
            // hide left and enable right

            if (!singlePlayer) {
              gameView.hideBoats(true);
            }
            gameView.hideBoatSelectionContainer("L");
            gameView.setMapActive("L", false);
            gameView.setMapActive("R", true);
          }
        } else if (rightMapboatsReady == false && side.equals("R")) {
          rightMapboatsReady = rightMapAliveBoatsCells == 17;
          if (rightMapboatsReady) {
            if (!singlePlayer) {
              gameView.hideBoats(false);
              gameView.hideBoatSelectionContainer("R");
              System.out.println("oculatndo barcos");
            } else {
              // lave to later
            }
          }
        }
      }

      if (!placingBoat && command.charAt(0) == 'B') {
        if (side.equals("R") && !leftMapboatsReady) {
          return;
        }

        selectedBoatSize = Integer.parseInt(command.charAt(4) + "");
        gameView.setSelectedBoat(command);
        placingBoat = true;
        lastJButtonSelectionBoat = (JButton) e.getSource();
        gameView.hideBoatSelectionButton(lastJButtonSelectionBoat, side);

      } else if (placingBoat && command.charAt(0) == 'm') {

        placingBoat = false;

        JButton gridButton = (JButton) e.getSource();
        // calculate the logical coordinates of the boat
        int mapX = (int) gridButton.getLocation().getX() / gridButton.getWidth();
        int mapY = (int) gridButton.getLocation().getY() / gridButton.getHeight();

        int gameMap[][] = side.equals("L") ? leftGameMap : rightGameMap;
        boolean legal = placeBoatInMap(mapX, mapY, selectedBoatSize, gameMap);

        selectedBoatSize = -99;

        if (legal) {
          // ok
          gameView.placeSelectedBoatInMap(gridButton.getLocation(), side.equals("L"));
        } else {
          // undo the hide boat button
          gameView.showBoatSelectionButton(lastJButtonSelectionBoat, side);
          lastJButtonSelectionBoat = null;
        }
        gameView.unselectBoat();
      }
    }
  }

  public boolean placeBoatInMap(int x, int y, int size, int map[][]) {
    for (int w = 0; w < size; w++) {
      if (x + w > 9 || x < 0) return false;
      int mapCellValue = map[x + w][y];
      if (mapCellValue == CellsStatus.boat) {
        return false;
      }
    }

    // all right make the changes
    GameBoat boat = new GameBoat(x, y, size);
    for (int w = 0; w < size; w++) {
      map[x + w][y] = CellsStatus.boat;
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
