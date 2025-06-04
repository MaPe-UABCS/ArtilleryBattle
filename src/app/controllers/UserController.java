package app.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import app.Main;
import app.models.User;
import app.views.LoginView;

public class UserController extends Controller implements ActionListener {

  LoginView loginView;
  private boolean userAutenticated;

  public boolean isUserAutenticated() {
    return userAutenticated;
  }

  public UserController() {
    loginView = (LoginView) Main.getViewReference("Login");
    System.out.println(loginView);
    loginView.setActionListener(this);

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String actionArray[] = e.getActionCommand().split(":");
    String view = actionArray[0];
    String command = actionArray[1];

    switch (command) {
      case "Skip":
        // TODO: login as guest user
        break;
      case "Continue":
        singUpOrLogin();
        break;
      default:
        throw new RuntimeException(command + " command not found ");
    }
  }

  public void singUpOrLogin() {
    String formData[] = loginView.getFormData();
    String userName = formData[0];
    String password = formData[1];

    System.out.println(userName);
    // User possibleRegisteredUser = User.find(userName);

    // System.out.println(possibleRegisteredUser);


    // if (user == null) {
    // System.out.println("registrando usuairo");
    // registerUser(userName, password);
    // } else {
    // System.out.println("intentando hacer login");
    // loginUser(userName, password);
    // }
  }

  public boolean loginUser(String name, String password) {

    return false;
  }

  public boolean registerUser(String name, String password) {

    User createdUser = new User(name, password);
    createdUser.save();
    return true;
  }
}
