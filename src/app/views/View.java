package app.views;

import javax.swing.JPanel;

public abstract class View extends JPanel {

  public final String viewName = "xd";

  /**
   * Method called once just before this view is render, use it to setup or configure things at
   * start
   */
  public abstract void before();

 // public View(String name) {
    //setBackground(App.BackgroundColor);
    //setLayout(null);
    //App.sharedInstance.registerView(this, name);
    //viewName = name;
//  }
}
