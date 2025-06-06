package app.models;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class MySQLConnection {

  private static String driver = "com.mysql.cj.jdbc.Driver";
  private static String database = "sql3780248";
  private static String hostname = "sql3.freesqldatabase.com";
  private static String port = "3306";
  private static String username = "sql3780248";
  private static String password = "bbT7XLg9mW";

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
      JOptionPane.showMessageDialog(null, "Error trying to Connect to the database");
      ex.printStackTrace();
    }

    return conn;
  }
}
