package app.views;

import app.Style;
import app.views.components.ALabel;

public class MainMenuView extends View {
  public MainMenuView() {
    super("MainMenu");
    ALabel test = new ALabel("HOLAAA", 20);
    test.setForeground(Style.getColor(Style.primary));
    test.setBounds(100, 100, 400, 400);
    add(test);
  }

  @Override
  public void before() {
    System.out.println(this.viewName);
  }
}
