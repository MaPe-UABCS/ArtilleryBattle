package app.views.components;

import app.AssetManager;
import javax.swing.JTextField;

public class AppInputField extends JTextField {

  public AppInputField(int size) {
    setFont(AssetManager.getFont("thin:" + size + ":bold"));
  }
}
