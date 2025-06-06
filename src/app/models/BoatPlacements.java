package app.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BoatPlacements {
  int id;
  String player1Placements;
  String player2Placements;
  int gameId;

  public BoatPlacements(int gameId, String player1Placements, String player2Placements) {
    this.gameId = gameId;
    this.player1Placements = player1Placements;
    this.player2Placements = player2Placements;
  }

  public int save() {
    String query =
        "insert into boat_placements( game_id, player1_placements, player2_placements ) values (?,?,?)";
    int created = 0;
    try (Connection con = MySQLConnection.connect();
        PreparedStatement pst =
            con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS); ) {
      pst.setInt(1, gameId);
      pst.setString(2, player1Placements);
      pst.setString(3, player2Placements);

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
}
