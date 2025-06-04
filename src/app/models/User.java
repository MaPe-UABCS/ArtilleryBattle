package app.models;

import java.util.ArrayList;

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
    this.name = name;
    this.passwordHash = passwordHash;
  }

  @Override
  public String toString() {
    return "ID:" + id + ",Name:" + name;
  }

  public boolean save() {
    // si este usuario tiene id significa que ya existia de ante mano en la base de datos y ha sido
    // modificado, efectuar un updaat, de lo contrario insert y consulta el ID asignado en la base
    // de datos
    // TODO: al terminar de ejecutar el query establecer el id de esta instancia en caso de que se
    // pueda guardar
    return false;
  }

  public static User find(int userID) {
    return null;
  }

  public static User find(String name) {
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
