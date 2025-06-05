package app.controllers;

import app.Main;
import app.models.User;
import app.views.LoginView;
import app.views.MainMenuView;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import org.mindrot.jbcrypt.*;

public class UserController extends Controller {

  LoginView loginView;
  private boolean userAutenticated;

  public boolean isUserAutenticated() {
    return userAutenticated;
  }

  public UserController() {
    loginView = (LoginView) Main.getViewReference("Login");
    loginView.setActionListener(this);

    Main.addActionListener2View("MainMenu", this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String actionArray[] = e.getActionCommand().split(":");
    String view = actionArray[0];
    String command = actionArray[1];

    switch (command) {
      case "Skip":
        MainMenuView menuView = (MainMenuView) Main.getViewReference("MainMenu");
        menuView.setLoggedTag("Guess");
        Main.setCurrentUser(User.find("Guess"));
        Main.changeView("MainMenu");
        break;
      case "Continue":
        singUpOrLogin();
        break;
      case "logout":
        Main.setCurrentUser(null);
        Main.changeView("Login");
        break;
    }
  }

  public void singUpOrLogin() {
    String formData[] = loginView.getFormData();
    String userName = formData[0];
    String password = formData[1];

    if (!validFormData(userName, password)) {
      return;
    }

    User user = User.find(userName);
    if (user == null) {
      user = registerUser(userName, password);
    } else {
      boolean autenticated = loginUser(password, user);
      if (!autenticated) {
        JOptionPane.showMessageDialog(Main.getCurrentView(), "Incorret Password!!");
        return;
      }
    }

    MainMenuView menuView = (MainMenuView) Main.getViewReference("MainMenu");
    menuView.setLoggedTag(user.getName());
    Main.setCurrentUser(user);
    Main.changeView("MainMenu");
  }

  private boolean validFormData(String user, String password) {
    String fails = "";
    if (user.length() == 0) {
      fails += "User field is Required.";
    }
    if (password.length() == 0) {
      fails += "Password field is Required.";
    }
    if (user.length() > 20) {
      fails += "User Name to Long >:(.";
    }

    if (fails.length() > 0) {
      JOptionPane.showMessageDialog(Main.getCurrentView(), fails);
    }
    return fails.length() == 0;
  }

  private boolean loginUser(String password, User user) {
    return BCrypt.checkpw(password, user.getPasswordHash());
  }

  private User registerUser(String name, String password) {
    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
    User createdUser = new User(name, hashedPassword);
    createdUser.save();
    return createdUser;
  }
}
