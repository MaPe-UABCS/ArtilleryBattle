package app;

import app.controllers.Controller;
import app.controllers.UserController;
import app.models.MySQLConnection;
import app.models.User;
import app.views.AnimationThread;
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
      setTitle("ArtilleryBattle");
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
      currentView = null;
    }

    // Contorllers
    {
      controllers = new HashMap<String, Controller>();
      controllers.put("User", new UserController());
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
    sharedInstance.body.add(sharedInstance.currentView);
    sharedInstance.currentView.before();
  }

  public static void main(String[] args) {
    new Main();
  }
}
