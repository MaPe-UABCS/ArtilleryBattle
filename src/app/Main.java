package app;

import app.controllers.Controller;
import app.controllers.GameController;
import app.controllers.UserController;
import app.models.Game;
import app.models.MySQLConnection;
import app.models.User;
import app.views.AnimationThread;
import app.views.GameView;
import app.views.LoginView;
import app.views.MainMenuView;
import app.views.View;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class Main extends JFrame {

  private static Main sharedInstance;

  private JPanel body;

  private HashMap<String, Controller> controllers;
  private HashMap<String, View> views;
  private View currentView;
  private User currentUser;

  public static User getSecondUser() {
    return sharedInstance.secondUser;
  }

  public static void setSecondUser(User secondUser) {
    sharedInstance.secondUser = secondUser;
  }

  private User secondUser;

  public static User getCurrentUser() {
    return sharedInstance.currentUser;
  }

  public static void setCurrentUser(User currentUser) {
    sharedInstance.currentUser = currentUser;
  }

  private AnimationThread animationThread;

  public Main() {
    if (sharedInstance == null) {
      sharedInstance = this;
    }

    MySQLConnection.connect();

    // singletons
    new SoundManager();
    new AssetManager();
    animationThread = new AnimationThread(60);

    UIManager.put("Button.select", Style.getColor(Style.gray));

    // Jframe config
    {
      setLayout(new BorderLayout());
      // setSize(600 + getInsets().left + getInsets().right, 600 + getInsets().top +
      setLocationRelativeTo(null);
      setResizable(false);
      setTitle("< Artillery Battle >");
      setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // Body container
    {
      body = new JPanel();
      body.setLayout(null);
      body.setBackground(Style.getColor(Style.background));
      body.setPreferredSize(new Dimension(600, 600));
    }

    // views
    {
      views = new HashMap<String, View>();
      views.put("Login", new LoginView());
      views.put("MainMenu", new MainMenuView());
      views.put("Game", new GameView());
      currentView = null;
    }

    // Contorllers
    {
      controllers = new HashMap<String, Controller>();
      controllers.put("UserController", new UserController());
      controllers.put("GameController", new GameController());
    }

    // set setVisible
    add(body);
    this.pack();
    setVisible(true);
    animationThread.start();
    // --------------

    // TODO: check the prefs an see if the user has logged in before, if so load
    // main Menu else
    // login screen
    changeView("Login");
    // TODO: eliminar esto aes para debugg
    // currentUser = User.find("manu");
  }

  public static View getViewReference(String viewName) {
    return sharedInstance.views.get(viewName);
  }

  public static View getCurrentView() {
    return sharedInstance.currentView;
  }

  public static void addActionListener2View(String viewName, ActionListener listener) {
    getViewReference(viewName).setActionListener(listener);
  }

  public static void changeView(String viewName) {
    if (sharedInstance.currentView != null) {
      sharedInstance.body.remove(sharedInstance.currentView);
      sharedInstance.currentView.after();
    }
    sharedInstance.currentView = sharedInstance.views.get(viewName);
    if (sharedInstance.currentView == null) {
      throw new RuntimeException(viewName + " does not exists");
    }

    if (viewName == "Game") {
      sharedInstance.setSize(1300, 600);
      sharedInstance.body.setPreferredSize(new Dimension(1300, 600));
      sharedInstance.body.setBounds(0, 0, 1300, 600);
    } else {
      sharedInstance.setSize(600, 600);
      sharedInstance.body.setPreferredSize(new Dimension(600, 600));
      // sharedInstance.body.setBounds(0, 0, 600, 600);
    }

    sharedInstance.body.add(sharedInstance.currentView);
    sharedInstance.currentView.before();
  }

  public static void main(String[] args) {
    new Main();
  }
}
