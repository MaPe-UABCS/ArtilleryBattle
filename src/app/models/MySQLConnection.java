package app.models;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLConnection {

  private static String driver = "com.mysql.cj.jdbc.Driver";
  private static String database = "programacion3";
  private static String hostname = "localhost";
  private static String port = "3306";
  private static String username = "manu";
  private static String password = "password";

  private static String url =
      "jdbc:mysql://"
          + hostname
          + ":"
          + port
          + "/"
          + database
          + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

  public static Connection connect() {

    Connection conn = null;

    try {
      Class.forName(driver);
      conn = DriverManager.getConnection(url, username, password);
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return conn;
  }
}
