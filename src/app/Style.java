package app;

import java.awt.Color;
import java.util.Map;

public class Style {

  public static final int background = 0;
  public static final int foreground = 1;
  public static final int primary = 2;
  public static final int secondary = 3;
  public static final int danger = 4;
  public static final int gray = 5;

  private static Map<Integer, Color> Colors =
      Map.of(
          background,
          Color.decode("#060606"),
          foreground,
          Color.decode("#e9e9e9"),
          primary,
          Color.decode("#1faf30"),
          secondary,
          Color.decode("#000000"),
          gray,
          Color.decode("#808080"),
          danger,
          Color.decode("#000000"));

  public static Color getColor(int color) {
    return Colors.get(color);
  }
}
