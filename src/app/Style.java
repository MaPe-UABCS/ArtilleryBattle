package app;

import java.awt.Color;
import java.util.Map;

public class Style {

  public static final int background = 0;
  public static final int foreground = 1;
  public static final int primary = 2;
  public static final int secondary = 3;
  public static final int danger = 4;

  private static Map<Integer, Color> Colors =
      Map.of(
          background, Color.decode("#000000"),
          foreground, Color.decode("#ffffff"),
          primary, Color.decode("#1faf30"),
          secondary, Color.decode("#000000"),
          danger, Color.decode("#000000"));

  public static Color getColor(int color) {
    return Colors.get(color);
  }
}
