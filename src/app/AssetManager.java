package app;

import java.awt.Font;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import javax.swing.ImageIcon;

public class AssetManager {
  private static HashMap<String, ImageIcon> imageIcons;
  private static Font font;

  public static void loadAssets(String assetsDirectoryPath) {
    imageIcons = new HashMap<String, ImageIcon>();

    File sourceFolder = new File(assetsDirectoryPath);
    if (sourceFolder.exists() && sourceFolder.isDirectory()) {
      try {
        File[] assetsFilesArray = sourceFolder.listFiles();

        if (assetsFilesArray != null) {
          for (File assetFile : assetsFilesArray) {
            String fileName = assetFile.getName();
            if (fileName.endsWith(".png")
                || fileName.endsWith(".gif")
                || fileName.endsWith(".jpg")) {
              // if (assetFile != null) {
              // Image spriteImage = ImageIO.read(assetFile);
              // System.out.println();
              // Image image = ImageIO.read(AssetManager.class.getResource(assetFile.getPath()));

              String imageRoute = assetFile.getPath();
              URL imageURl = Main.class.getResource(assetFile.getPath());
              ImageIcon image = new ImageIcon(imageRoute);

              String imageName = assetFile.getName().split("\\.")[0];
              imageIcons.put(imageName, image);
              // }
            } else if (fileName.endsWith(".ttf")) {
              font = Font.createFont(Font.TRUETYPE_FONT, assetFile).deriveFont(20f);
            }
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("what?");
      throw new RuntimeException(assetsDirectoryPath + " , is not a valid Directory.");
    }
  }

  public static ImageIcon getImageIcon(String name) {
    return imageIcons.get(name);
  }

  public static Font getFont() {
    return font;
  }
}
