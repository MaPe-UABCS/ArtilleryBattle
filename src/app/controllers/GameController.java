package app.controllers;

import app.Main;
import app.views.GameView;
import app.views.View;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JButton;

public class GameController extends Controller {

  GameView gameView;

  // Boat Placing in UI logic
  Boolean placingBoat;
  int selectedBoatSize;

  // gameMode
  boolean singlePlayer;
  int turnCount;
  boolean leftPlayerTurn;
  String moveHistory;

  // GAME MAP
  int leftGameMap[][];
  int rightGameMap[][];
  // left
  boolean leftMapboatsReady;
  int leftMapAliveBoatsCells;
  ArrayList<GameBoat> leftBoats;
  // Both
  JButton lastJButtonSelectionBoat;
  // right
  boolean rightMapboatsReady;
  int rightMapAliveBoatsCells;
  ArrayList<GameBoat> rightBoats;

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
    turnCount = 0;
    leftPlayerTurn = true;

    for (int x = 0; x < 10; x++) {
      for (int y = 0; y < 10; y++) {
        leftGameMap[x][y] = CellsStatus.water;
      }
    }
  }

  public void gameOver(boolean leftWon) {

    // TODO register the following data:
    //  clac points based on an inverse relation with the tourn count
    //  save the winner
    //  save the looser
    //  save the move history
    //  save the number of left boats of each player, not cells boats
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
    switch (command) {
      case "Single Player":
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
      // TODO: open Lan Menu and get ready UDT and TPC sockets
      default:
        break;
    }
  }

  private void actionPerformedInGameView(ActionEvent e) {
    String commandArray[] = e.getActionCommand().split(":");
    String side = commandArray[0];
    String command = commandArray[1];

    // Place here the logic for  buttons to regurn to main menu, and play again

    if ((leftMapboatsReady && rightMapboatsReady)
        && (leftMapAliveBoatsCells == 0 || rightMapAliveBoatsCells == 0)) {

      return;
    }

    int currentGameMap[][] = side.equals("L") ? leftGameMap : rightGameMap;

    JButton gridButton = (JButton) e.getSource();

    // calculate the logical coordinates of the click
    int mapX = (int) gridButton.getLocation().getX() / gridButton.getWidth();
    int mapY = (int) gridButton.getLocation().getY() / gridButton.getHeight();

    if (leftMapboatsReady && rightMapboatsReady) {
      int cell2BombValue = currentGameMap[mapX][mapY];
      if (cell2BombValue == CellsStatus.hit || cell2BombValue == CellsStatus.blank) {
        return;
      }

      if (cell2BombValue == CellsStatus.boat) {
        gameView.markHitInMap(side, mapX, mapY);
        cell2BombValue = CellsStatus.hit;
        // add to history
      } else if (cell2BombValue == CellsStatus.water) {
        gameView.markBlankInMap(side, mapX, mapY);
        cell2BombValue = CellsStatus.blank;
        // add to history
      }

      boolean leftMove = side.equals("R");
      if (leftMove) {
        rightGameMap[mapX][mapY] = cell2BombValue;
        gameView.setMapActive("L", true);
        gameView.setMapActive("R", false);
      } else {
        leftGameMap[mapX][mapY] = cell2BombValue;
        gameView.setMapActive("R", true);
        gameView.setMapActive("L", false);
      }

      String move = leftMove ? Main.getCurrentUser().getName() : "seocnd";
      String shootType = "blank";
      if (cell2BombValue == CellsStatus.hit) {
        if (leftMove) {
          rightMapAliveBoatsCells--;
        } else {
          leftMapAliveBoatsCells--;
        }
        shootType = "hit";
      }
      move += ":" + (char) ((int) ('A') + mapX) + "," + mapY + ":" + shootType;
      gameView.addMove2HistoryDisplay(move);

      // Check game over
      if (leftMapAliveBoatsCells == 0) {
        gameOver(false);
      } else if (rightMapAliveBoatsCells == 0) {
        gameOver(true);
      }

      turnCount++;
      return;
    }

    // Boat placement ====================================================

    if (command.equals("Ready")) {

      if (leftMapboatsReady == false && leftPlayerTurn) {
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
      } else if (rightMapboatsReady == false && !leftPlayerTurn) {
        rightMapboatsReady = rightMapAliveBoatsCells == 17;
        if (rightMapboatsReady) {
          if (!singlePlayer) {
            gameView.hideBoats(false);
            gameView.hideBoatSelectionContainer("R");
          } else {
            // lave to later
          }
        }
      }

      leftPlayerTurn = !leftPlayerTurn;
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
    boolean alive;

    public GameBoat(int x, int y, int size) {
      this.x = x;
      this.y = y;
      this.size = size;
      alive = true;
    }
  }
}
