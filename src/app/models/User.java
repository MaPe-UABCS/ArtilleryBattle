package app.models;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class User {
  private int id;
  private String name;
  private String passwordHash;

  public User(int id, String name, String passwordHash) {
    this.id = id;
    this.name = name;
    this.passwordHash = passwordHash;
  }

  public User(String name, String passwordHash) {
    this.id = -1;
    this.name = name;
    this.passwordHash = passwordHash;
  }

  @Override
  public String toString() {
    return "ID:" + id + ",Name:" + name;
  }

  public boolean save() {
    if (id == -1) {
      // insert
      String query = "insert into users(name,password) values (?,?)";
      int created = 0;
      try (Connection con = MySQLConnection.connect();
          PreparedStatement pst = con.prepareStatement(query);) {
        pst.setString(1, getName());
        pst.setString(2, getPasswordHash());
        created = pst.executeUpdate();
      } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
      }
      return created > 0;
    }
    return false;
  }

  public static User find(int userID) {
    return null;
  }

  public static User find(String nameQuery) {
    try (
        Connection con = MySQLConnection.connect();
        Statement st = (Statement) con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);) {

      String query = "select * from users where name = \"" + nameQuery + "\"";
      ResultSet rs = st.executeQuery(query);

      while (rs.next()) {
        int idDB = rs.getInt("id");
        String name = rs.getString("name");
        String passwordHash = rs.getString("password");
        return new User(idDB, name, passwordHash);
      }

      return null;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    return null;
  }

  public static ArrayList<User> top3() {

    return null;
  }

  public static ArrayList<User> all() {
    return null;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPasswordHash() {
    return passwordHash;
  }
}
