package app.controllers;

import app.Main;
import app.models.User;
import app.views.LoginView;

public class UserController {

  public UserController() {
    super();
  }

  public boolean registerUser() {
    LoginView loginView = (LoginView) Main.getViewReference("Login");
    String formData[] = loginView.getFormData();
    String userName = formData[0];
    String password = formData[1];

    // check if a user with that name exists
    User createdUser = User.find(userName);
    if (createdUser != null) {
      // TODO: mostar un dialogo de error, nombre de usuario ya regsitrado
      System.out.println("TRikitracatelas ese usuario ya existe");
      return false;
    }
    // TODO usar bcrypt para guardar el hash
    createdUser = new User(userName, password);
    createdUser.save();
    return true;
  }
}
