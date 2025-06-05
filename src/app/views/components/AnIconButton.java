package app.views.components;

import app.AssetManager;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class AnIconButton extends JButton {

  public AnIconButton(String iconName) {
    setIcon(AssetManager.getImageIcon(iconName + ".png"));
    setActionCommand(iconName);
    setOpaque(false);
    setBorderPainted(false);
    setFocusPainted(false);
    setContentAreaFilled(false);
  }

  public AnIconButton(String iconName, ActionListener listener) {
    setIcon(AssetManager.getImageIcon(iconName + ".png"));
    setActionCommand(iconName);
    setOpaque(false);
    setBorderPainted(false);
    setFocusPainted(false);
    setContentAreaFilled(false);
    addActionListener(listener);
  }
}
