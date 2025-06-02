package app.views;

import app.Style;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.AbstractButton;
import javax.swing.JPanel;

public abstract class View extends JPanel {

  public final String viewName;
  private final ArrayList<AbstractButton> buttons;

  public View(String name) {
    setBackground(Style.getColor(Style.background));
    setLayout(null);
    setBounds(0, 0, 600, 600);
    buttons = new ArrayList<AbstractButton>();
    viewName = name;
  }

  /**
   * Method called once just before this view is render, use it to setup or configure things at
   * start
   */
  public abstract void before();

  @Override
  public Component add(Component component) {
    if (component.getClass() == AbstractButton.class) {
      buttons.add((AbstractButton) component);
    }
    return super.add(component);
  }

  public void setActionListener(ActionListener listener) {
    for (AbstractButton button : buttons) {
      button.addActionListener(listener);
    }
  }
}
