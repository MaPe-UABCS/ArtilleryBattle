package app;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

public class AssetManager {
  public static AssetManager sharedInstance;

  private HashMap<String, ImageIcon> imageIcons;
  private HashMap<String, URL> audioURls;
  private HashMap<String, Font> derivedFonts;
  private Font font;

  public AssetManager() {
    if (sharedInstance == null) {
      sharedInstance = this;
    }
    imageIcons = new HashMap<String, ImageIcon>();
    audioURls = new HashMap<String, URL>();
    derivedFonts = new HashMap<String, Font>();

    try {
      font =
          Font.createFont(
              Font.TRUETYPE_FONT,
              getClass().getResourceAsStream("/fonts/IosevkaTermNerdFontPropo-Thin.ttf"));
    } catch (IOException | FontFormatException e) {
      System.out.println(e.getMessage());
    }
  }

  public static Clip getAudio(String name) {
    URL audioURl = sharedInstance.audioURls.get(name);

    if (audioURl == null) {
      audioURl = sharedInstance.getClass().getResource("/sounds/" + name);
      sharedInstance.audioURls.put(name, audioURl);
    }

    try {
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioURl);
      Clip clip = AudioSystem.getClip();
      clip.open(audioInputStream);
      return clip;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  public static ImageIcon getImageIcon(String name) {
    if (sharedInstance.imageIcons == null) {}

    ImageIcon imageIcon = sharedInstance.imageIcons.get(name);
    if (imageIcon == null) {
      try {
        BufferedImage bufferedImage =
            ImageIO.read(sharedInstance.getClass().getResourceAsStream("/img/" + name));
        imageIcon = new ImageIcon(bufferedImage);
        sharedInstance.imageIcons.put(name, imageIcon);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
    return imageIcon;
  }

  /* getFont (String fontCommand)
   *
   * examples:
   *    "20:Font.PLAIN"
   *    "14:Font.BOLD"
   */
  public static Font getFont(String fontCommand) {
    Font font = sharedInstance.derivedFonts.get(fontCommand);
    if (font == null) {
      int style = Font.PLAIN;
      String fontType = fontCommand.split(":")[1].toLowerCase();
      switch (fontType) {
        case "bold":
          style = Font.BOLD;
          break;
        case "plain":
          style = Font.PLAIN;
          break;
        case "italic":
          style = Font.ITALIC;
          break;
        default:
          throw new RuntimeException("Font type: " + fontType + " is not valid");
      }
      int size = Integer.parseInt(fontCommand.split(":")[0]);
      font = sharedInstance.font.deriveFont(style, size);
      sharedInstance.derivedFonts.put(fontCommand, font);
    }
    return font;
  }
}
