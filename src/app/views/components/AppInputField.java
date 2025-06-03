package app.views.components;

import app.AssetManager;
import app.Style;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class AppInputField extends JTextField {

  public AppInputField(int size) {
    setFont(AssetManager.getFont("thin:" + size + ":bold"));
    setBackground(Style.getColor(Style.foreground));
    setForeground(Style.getColor(Style.background));
    setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D) g;
    g2.setPaint(Style.getColor(Style.gray));
    Area leftBorder = new Area(new Rectangle2D.Double(0, 0, getHeight() - 1, getHeight() - 1));
    leftBorder.subtract(new Area(new Rectangle2D.Double(1, 1, getHeight() - 2, getHeight() - 3)));
    leftBorder.subtract(
        new Area(new Rectangle2D.Double(getHeight() / 4, 0, getHeight(), getHeight())));
    g2.fill(leftBorder);
    int rx = getWidth() - getHeight();
    Area rightBorder = new Area(new Rectangle2D.Double(rx, 0, getHeight() - 1, getHeight() - 1));
    rightBorder.subtract(new Area(new Rectangle2D.Double(rx, 1, getHeight() - 2, getHeight() - 3)));
    rightBorder.subtract(
        new Area(new Rectangle2D.Double(rx - getHeight() / 4, 0, getHeight(), getHeight())));
    g2.fill(rightBorder);
  }
}
