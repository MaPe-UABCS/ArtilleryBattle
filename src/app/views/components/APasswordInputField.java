package app.views.components;

import app.AssetManager;
import java.awt.Graphics;
import javax.swing.JPasswordField;

public class APasswordInputField extends JPasswordField {

  public APasswordInputField(int fontSize) {
    setFont(AssetManager.getFont("light:" + fontSize + ":bold"));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
  }
}
