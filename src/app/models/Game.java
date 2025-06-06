package app.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Game {
  int id;
  String moves;
  int winnerId;

  int player1Id;
  int player1Hits;
  int player1Blanks;
  int player1MoveCount;
  int player1Score;

  int player2Id;
  int player2Hits;
  int player2Blanks;
  int player2MoveCount;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getMoves() {
    return moves;
  }

  public void setMoves(String moves) {
    this.moves = moves;
  }

  public int getWinnerId() {
    return winnerId;
  }

  public void setWinnerId(int winnerId) {
    this.winnerId = winnerId;
  }

  public int getPlayer1Id() {
    return player1Id;
  }

  public void setPlayer1Id(int player1Id) {
    this.player1Id = player1Id;
  }

  public int getPlayer1Hits() {
    return player1Hits;
  }

  public void setPlayer1Hits(int player1Hits) {
    this.player1Hits = player1Hits;
  }

  public int getPlayer1Blanks() {
    return player1Blanks;
  }

  public void setPlayer1Blanks(int player1Blanks) {
    this.player1Blanks = player1Blanks;
  }

  public int getPlayer1MoveCount() {
    return player1MoveCount;
  }

  public void setPlayer1MoveCount(int player1MoveCount) {
    this.player1MoveCount = player1MoveCount;
  }

  public int getPlayer1Score() {
    return player1Score;
  }

  public void setPlayer1Score(int player1Score) {
    this.player1Score = player1Score;
  }

  public int getPlayer2Id() {
    return player2Id;
  }

  public void setPlayer2Id(int player2Id) {
    this.player2Id = player2Id;
  }

  public int getPlayer2Hits() {
    return player2Hits;
  }

  public void setPlayer2Hits(int player2Hits) {
    this.player2Hits = player2Hits;
  }

  public int getPlayer2Blanks() {
    return player2Blanks;
  }

  public void setPlayer2Blanks(int player2Blanks) {
    this.player2Blanks = player2Blanks;
  }

  public int getPlayer2MoveCount() {
    return player2MoveCount;
  }

  public void setPlayer2MoveCount(int player2MoveCount) {
    this.player2MoveCount = player2MoveCount;
  }

  public int getPlayer2Score() {
    return player2Score;
  }

  public void setPlayer2Score(int player2Score) {
    this.player2Score = player2Score;
  }

  int player2Score;

  public Game(
      int player2Score,
      int id,
      String moves,
      int winnerId,
      int player1Id,
      int player1Hits,
      int player1Blanks,
      int player1MoveCount,
      int player1Score,
      int player2Id,
      int player2Hits,
      int player2Blanks,
      int player2MoveCount) {
    this.player2Score = player2Score;
    this.id = id;
    this.moves = moves;
    this.winnerId = winnerId;
    this.player1Id = player1Id;
    this.player1Hits = player1Hits;
    this.player1Blanks = player1Blanks;
    this.player1MoveCount = player1MoveCount;
    this.player1Score = player1Score;
    this.player2Id = player2Id;
    this.player2Hits = player2Hits;
    this.player2Blanks = player2Blanks;
    this.player2MoveCount = player2MoveCount;
  }

  public Game(
      int player2Score,
      String moves,
      int winnerId,
      int player1Id,
      int player1Hits,
      int player1Blanks,
      int player1MoveCount,
      int player1Score,
      int player2Id,
      int player2Hits,
      int player2Blanks,
      int player2MoveCount) {
    this.player2Score = player2Score;
    this.moves = moves;
    this.winnerId = winnerId;
    this.player1Id = player1Id;
    this.player1Hits = player1Hits;
    this.player1Blanks = player1Blanks;
    this.player1MoveCount = player1MoveCount;
    this.player1Score = player1Score;
    this.player2Id = player2Id;
    this.player2Hits = player2Hits;
    this.player2Blanks = player2Blanks;
    this.player2MoveCount = player2MoveCount;
  }

  public int save() {
    // insert
    String query =
        "insert into games (moves, player1_id, player1_hits, player1_blanks, player1_move_count,"
            + " player2_id, player2_hits, player2_blanks, player2_move_count,"
            + " player1_score, player2_score, winner_id)  values (?,?,?,?,?,?,?,?,?,?,?,?)";
    int created = 0;
    try (Connection con = MySQLConnection.connect();
        PreparedStatement pst =
            con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS); ) {
      pst.setString(1, moves);

      pst.setInt(2, player1Id);
      pst.setInt(3, player1Hits);
      pst.setInt(4, player1Blanks);
      pst.setInt(5, player1MoveCount);

      pst.setInt(6, player2Id);
      pst.setInt(7, player2Hits);
      pst.setInt(8, player2Blanks);
      pst.setInt(9, player2MoveCount);

      pst.setInt(10, player1Score);
      pst.setInt(11, player2Score);
      pst.setInt(12, winnerId);

      created = pst.executeUpdate();

      if (created > 0) {
        ResultSet generatedKeys = pst.getGeneratedKeys();

        if (generatedKeys.next()) {
          this.id = generatedKeys.getInt(1);
          return generatedKeys.getInt(1);
        }
      }

    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
    return -1;
  }

  public static ArrayList<Game> all() {
    ArrayList<Game> games = new ArrayList<Game>();
    String query = "select * from games order by id ASC";
    try (Connection con = MySQLConnection.connect(); ) {
      Statement st =
          (Statement)
              con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      ResultSet rs = st.executeQuery(query);

      while (rs.next()) {
        String moves = rs.getString("moves");

        int player1Id = rs.getInt("player1_id");
        int player1Hits = rs.getInt("player1_hits");
        int player1Blanks = rs.getInt("player1_blanks");
        int player1MoveCount = rs.getInt("player1_move_count");
        int player1Score = rs.getInt("player1_score");

        int player2Id = rs.getInt("player2_id");
        int player2Hits = rs.getInt("player2_hits");
        int player2Blanks = rs.getInt("player2_blanks");
        int player2MoveCount = rs.getInt("player2_move_count");
        int player2Score = rs.getInt("player2_score");

        int winnerId = rs.getInt("winner_id");

        Game game =
            new Game(
                player2Score,
                moves,
                winnerId,
                player1Id,
                player1Hits,
                player1Blanks,
                player1MoveCount,
                player1Score,
                player2Id,
                player2Hits,
                player2Blanks,
                player2MoveCount);
        games.add(game);
      }

      return games;

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return null;
  }
}
