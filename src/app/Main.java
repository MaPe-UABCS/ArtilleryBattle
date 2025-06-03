package app;

import app.views.AnimationThread;
import app.views.LoginView;
import app.views.MainMenuView;
import app.views.View;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame {

  private static Main sharedInstance;

  private JPanel body;

  private HashMap<String, View> programViews;
  private View currentView;
  private AnimationThread animationThread;

  public Main() {
    if (sharedInstance == null) {
      sharedInstance = this;
    }

    // singletons
    new SoundManager();
    new AssetManager();
    animationThread = new AnimationThread(60);

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
      programViews = new HashMap<String, View>();
      programViews.put("Login", new LoginView());
      programViews.put("MainMenu", new MainMenuView());
      currentView = null;
    }

    // set setVisible
    add(body);
    this.pack();
    setVisible(true);
    animationThread.start();
    // --------------

    // TODO: check the prefs an see if the user has logged in before, if so load main Menu else
    // login screen
    changeView("MainMenu");
  }

  public static void changeView(String viewName) {
    if (sharedInstance.currentView != null) {
      sharedInstance.body.remove(sharedInstance.currentView);
    }
    sharedInstance.currentView = sharedInstance.programViews.get(viewName);
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
