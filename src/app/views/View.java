package app.views;

import app.Style;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.AbstractButton;
import javax.swing.JPanel;

public abstract class View extends JPanel {

  public final String viewName;
  protected final ArrayList<AbstractButton> buttons;

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
  public abstract void after();

  public void registerButton(AbstractButton button) {
    button.setActionCommand(viewName + ":" + button.getActionCommand());
    buttons.add(button);
  }

  public void registerButton(AbstractButton button, String command) {
    button.setActionCommand(viewName + ":" + command);
    buttons.add(button);
  }

  @Override
  public Component add(Component component) {
    if (component.getClass() == AbstractButton.class) {
      AbstractButton button = (AbstractButton) component;
      button.setActionCommand(viewName + ":command");
      buttons.add(button);
    }
    return super.add(component);
  }

  public void setActionListener(ActionListener listener) {
    for (AbstractButton button : buttons) {
      button.addActionListener(listener);
    }
  }
}
