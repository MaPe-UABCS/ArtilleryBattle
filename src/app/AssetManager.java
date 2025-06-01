package app;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

public class AssetManager {
  public static AssetManager sharedInstance;

  private HashMap<String, ImageIcon> imageIcons;
  private HashMap<String, AudioInputStream> audioInputStreams;
  private Font font;

  public AssetManager() {
    if (sharedInstance == null) {
      sharedInstance = this;
    }
  }

  public Clip getAudio(String name) {
    // try {
    //   AudioInputStream audioInputStream = audioInputStreams.get(name);
    //   Clip clip = AudioSystem.getClip();
    //   clip.open(audioInputStream);
    //   return clip;
    // } catch (Exception e) {
    //   System.out.println(e.getMessage());
    // }
    return null;
  }

  public static ImageIcon getImageIcon(String name) {
    if (sharedInstance.imageIcons == null) {
      sharedInstance.imageIcons = new HashMap<String, ImageIcon>();
    }

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

  public static Font getFont() {
    return sharedInstance.font;
  }
}
