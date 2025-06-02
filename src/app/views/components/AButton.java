package app.views.components;

import app.AssetManager;
import app.Style;
import javax.swing.BorderFactory;
import javax.swing.JButton;

public class AButton extends JButton {

  public static final int lightPrimary = 0;
  public static final int darkPrimary = 1;

  public static final int lightSecondary = 2;
  public static final int darkSecondary = 3;

  public AButton(String text, int fontSize, int style) {
    setText(text);
    setFont(AssetManager.getFont("thin:" + fontSize + ":bold"));

    applyStyle(style);
  }

  private void applyStyle(int style) {
    setFocusPainted(false);

    setBorder(BorderFactory.createEmptyBorder());
    switch (style) {
      case lightPrimary:
        setBackground(Style.getColor(Style.foreground));
        setForeground(Style.getColor(Style.background));
        break;
      case darkPrimary:
        setBackground(Style.getColor(Style.background));
        setForeground(Style.getColor(Style.foreground));
        break;
      case lightSecondary:
        setBackground(Style.getColor(Style.foreground));
        setForeground(Style.getColor(Style.background));
        setBorder(BorderFactory.createLineBorder(Style.getColor(Style.background)));
        break;
      case darkSecondary:
        setBackground(Style.getColor(Style.foreground));
        setForeground(Style.getColor(Style.background));
        setBorder(BorderFactory.createLineBorder(Style.getColor(Style.foreground)));
        break;
      default:
        throw new RuntimeException("Color Style for AButton does not exist >:|");
    }
  }
}
