package app;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

public class Style {

  public enum AppColorsDefinitions {
    background,
    primary,
    secondary,
    danger,
  }

  public static Map<AppColorsDefinitions, Color> Colors =
      Map.of(
          AppColorsDefinitions.background, Color.decode("#000000"),
          AppColorsDefinitions.primary, Color.decode("#000000"),
          AppColorsDefinitions.secondary, Color.decode("#000000"),
          AppColorsDefinitions.danger, Color.decode("#000000"));

  private static HashMap<Float, Font> loadedFonts = new HashMap<Float, Font>();

  public static Font getFont(float size) {
    Font font = loadedFonts.get(size);
    if (font == null) {
      font = AssetManager.getFont().deriveFont(size);
      loadedFonts.put(size, font);
    }

    return font;
  }
}
