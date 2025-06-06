package app.controllers;

import app.Main;
import app.models.BoatPlacements;
import app.models.Game;
import app.views.GameView;
import app.views.View;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GameController extends Controller {

  GameView gameView;

  // Boat Placing in UI logic
  Boolean placingBoat;
  int selectedBoatSize;

  // gameMode
  boolean singlePlayer;
  // int turnCount;
  boolean leftPlayerTurn;
  int leftTurnCount;
  int rightTurnCount;
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
    // turnCount = 0;
    leftTurnCount = 0;
    rightTurnCount = 0;
    leftPlayerTurn = true;
    moveHistory = "";
    leftBoats = new ArrayList<GameBoat>();
    rightBoats = new ArrayList<GameBoat>();

    for (int x = 0; x < 10; x++) {
      for (int y = 0; y < 10; y++) {
        leftGameMap[x][y] = CellsStatus.water;
      }
    }
  }

  public void gameOver(boolean leftWon) {

    int player1Hits = 17 - rightMapAliveBoatsCells;
    int player1Blanks = leftTurnCount - player1Hits;
    int player1MoveCount = leftTurnCount;
    int player1Score = (999 * 17) / player1MoveCount;

    int player2Hits = 17 - leftMapAliveBoatsCells;
    int player2Blanks = rightTurnCount - player2Hits;
    int player2MoveCount = rightTurnCount;
    int player2Score = (999 * 17) / player2MoveCount;

    int winnerId = leftWon ? Main.getCurrentUser().getId() : Main.getSecondUser().getId();

    Game game =
        new Game(
            player2Score,
            moveHistory,
            winnerId,
            Main.getCurrentUser().getId(),
            player1Hits,
            player1Blanks,
            player1MoveCount,
            player1Score,
            Main.getSecondUser().getId(),
            player2Hits,
            player2Blanks,
            player2MoveCount);
    int gameId = game.save();
    // save boat placement
    String player1Placements = "";
    for (GameBoat boat : leftBoats) {
      player1Placements += "1:" + boat.size + ":" + boat.x + "," + boat.y + ".";
    }
    String player2Placements = "";
    for (GameBoat boat : rightBoats) {
      player2Placements += "2:" + boat.size + ":" + boat.x + "," + boat.y + ".";
    }
    BoatPlacements placements = new BoatPlacements(gameId, player1Placements, player2Placements);
    placements.save();

    gameView.showBoats(true);
    gameView.showBoats(false);
    gameView.setMapActive("L", true);
    gameView.setMapActive("R", true);
    JOptionPane.showMessageDialog(
        Main.getCurrentView(),
        (leftWon ? Main.getCurrentUser().getName() : Main.getSecondUser().getName()) + " Has Won!");
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
        Main.changeView("Login");
        singlePlayer = false;
        // gameView.hideBoatSelectionContainer("R");
        break;
      case "LAN":
      // TODO: open Lan Menu and get ready UDT and TPC sockets
      case "Report":
        export();
        break;
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
        leftTurnCount++;
        gameView.setMapActive("L", true);
        gameView.setMapActive("R", false);
      } else {
        rightTurnCount++;
        leftGameMap[mapX][mapY] = cell2BombValue;
        gameView.setMapActive("R", true);
        gameView.setMapActive("L", false);
      }

      String move = leftMove ? Main.getCurrentUser().getName() : Main.getSecondUser().getName();
      String shootType = "blank";
      if (cell2BombValue == CellsStatus.hit) {
        if (leftMove) {
          rightMapAliveBoatsCells--;
          gameView.rigthStatusContiner.setHitsTookCount(17 - rightMapAliveBoatsCells);
        } else {
          leftMapAliveBoatsCells--;
          gameView.leftStatusContainer.setHitsTookCount(17 - leftMapAliveBoatsCells);
        }
        shootType = "hit";
      }
      move += ":" + (char) ((int) ('A') + mapX) + "," + mapY + ":" + shootType;
      gameView.addMove2HistoryDisplay(move);
      moveHistory += move + ".";

      // Check game over
      if (leftMapAliveBoatsCells == 0) {
        gameOver(false);
      } else if (rightMapAliveBoatsCells == 0) {
        gameOver(true);
      }

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

      if (legal) {
        // ok
        gameView.placeSelectedBoatInMap(gridButton.getLocation(), side.equals("L"));
        if (side.equals("L")) {
          leftBoats.add(new GameBoat(mapX, mapY, selectedBoatSize));
        } else {
          rightBoats.add(new GameBoat(mapX, mapY, selectedBoatSize));
        }
      } else {
        // undo the hide boat button
        gameView.showBoatSelectionButton(lastJButtonSelectionBoat, side);
        lastJButtonSelectionBoat = null;
      }

      selectedBoatSize = -99;
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

  private void export() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    fileChooser.setAcceptAllFileFilterUsed(false);
    FileNameExtensionFilter pdfs = new FileNameExtensionFilter("Documentos PDF", "pdf");
    fileChooser.addChoosableFileFilter(pdfs);
    fileChooser.setFileFilter(pdfs);

    int option = fileChooser.showDialog(null, "generate PDF");
    if (option == JFileChooser.CANCEL_OPTION) {
      return;
    }

    try (PdfDocument pdfDoc = new PdfDocument(new PdfWriter(fileChooser.getSelectedFile()));
        Document doc = new Document(pdfDoc, PageSize.LETTER.rotate()); // Página en
        ) {
      doc.add(new Paragraph("Artillery Battle Data Report").setBold().setFontSize(22));
      doc.add(new Paragraph("").setMarginTop(30));

      float columnsWidth[] = new float[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
      Table gamesTable =
          new Table(UnitValue.createPercentArray(columnsWidth)).useAllAvailableWidth();

      Cell[] headerFooter =
          new Cell[] {
            // new ReportHeaderCell("Moves"),
            new ReportHeaderCell("Player 1"),
            new ReportHeaderCell("P1 hits"),
            new ReportHeaderCell("P1 blanks"),
            new ReportHeaderCell("P1 moves"),
            new ReportHeaderCell("P1 Score"),
            new ReportHeaderCell("Player 2"),
            new ReportHeaderCell("P2 hits"),
            new ReportHeaderCell("P2 blanks"),
            new ReportHeaderCell("P2 moves"),
            new ReportHeaderCell("P2 Score"),
            new ReportHeaderCell("Winer")
          };

      for (Cell cell : headerFooter) {
        gamesTable.addHeaderCell(cell);
      }

      for (Game game : Game.all()) {
        // gamesTable.addCell(new ReportCell(game.getMoves()));

        gamesTable.addCell(new ReportCell(game.getPlayer1Id()));
        gamesTable.addCell(new ReportCell(game.getPlayer1Hits()));
        gamesTable.addCell(new ReportCell(game.getPlayer1Blanks()));
        gamesTable.addCell(new ReportCell(game.getPlayer1MoveCount()));
        gamesTable.addCell(new ReportCell(game.getPlayer1Score()));

        gamesTable.addCell(new ReportCell(game.getPlayer2Id()));
        gamesTable.addCell(new ReportCell(game.getPlayer2Hits()));
        gamesTable.addCell(new ReportCell(game.getPlayer2Blanks()));
        gamesTable.addCell(new ReportCell(game.getPlayer2MoveCount()));
        gamesTable.addCell(new ReportCell(game.getPlayer2Score()));

        gamesTable.addCell(new ReportCell(game.getWinnerId()));
      }

      doc.add(gamesTable);
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
      ex.printStackTrace();
      JOptionPane.showMessageDialog(null, "Error exporting to PDF");
    }
  }

  private class ReportHeaderCell extends Cell {
    public ReportHeaderCell(String text) {
      setTextAlignment(TextAlignment.CENTER);
      setBorderTop(new SolidBorder(1f));
      setBackgroundColor(new DeviceRgb(200, 200, 200));
      add(new Paragraph(text));
    }
  }

  private class ReportCell extends Cell {
    public ReportCell(String text) {
      setTextAlignment(TextAlignment.CENTER);
      Paragraph paragraph = new Paragraph(text);
      add(paragraph);
    }

    public ReportCell(int value) {
      setTextAlignment(TextAlignment.CENTER);
      Paragraph paragraph = new Paragraph(value + "");
      add(paragraph);
    }
  }
}
