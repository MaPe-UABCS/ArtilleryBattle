package app.views.components;

import app.AssetManager;
import app.Style;
import javax.swing.JLabel;

public class ALabel extends JLabel {
  public ALabel(String text, int size) {
    setFont(AssetManager.getFont("light:" + size + ":plain"));
    setText(text);
    setForeground(Style.getColor(Style.background));
  }

  public ALabel(String text, int size, int color) {
    setFont(AssetManager.getFont("light:" + size + ":plain"));
    setText(text);
    setForeground(Style.getColor(color));
  }

  public ALabel(String text, String fontCommand) {
    setText(text);
    setFont(AssetManager.getFont(fontCommand));
  }

  public ALabel(String text, String fontCommand, int color) {
    setText(text);
    setFont(AssetManager.getFont(fontCommand));
    setForeground(Style.getColor(Style.foreground));
  }
}
