package app;

import java.awt.Font;
import java.util.HashMap;

public class Style {

  private static HashMap<Float, Font> loadedFonts;

  public static Font getFont(float size) {
    Font font = loadedFonts.get(size);
    if (font == null) {
      font = AssetManager.getFont().deriveFont(size);
      loadedFonts.put(size, font);
    }

    return font;
  }
}
