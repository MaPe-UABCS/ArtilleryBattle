package app.views.components;

import app.Style;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MapHeader extends JPanel {

  ALabel title;
  ALabel takenHitsLabel;

  public MapHeader(boolean art) {

    JLabel corner = new JLabel(!art ? "    :)" : "      >:|");
    corner.setForeground(Style.getColor(Style.background));
    corner.setOpaque(true);
    corner.setBackground(Style.getColor(Style.foreground));
    corner.setBounds(43 * 8, 0, 43 * 2 + 10, 43);
    add(corner);

    setOpaque(false);
    setLayout(null);
    setBorder(BorderFactory.createLineBorder(Style.getColor(Style.foreground)));
    title = new ALabel("< void's Map >", 24, Style.foreground);
    title.setBounds(0, 0, 440, 20);
    add(title);

    takenHitsLabel = new ALabel("Hits took: 0", 18, Style.foreground);
    takenHitsLabel.setBounds(0, 25, 440, 20);
    add(takenHitsLabel);

    String faces = "            (^_^) [o_o] (^.^)  (\".\") ($.$) ";
    if (art) {
      faces = "            █║▌│ █│║▌ ║││█║▌ │║║█║ │║║█║";
    }
    ALabel decoration = new ALabel(faces, 18, Style.foreground);
    decoration.setBounds(0, 60, 440, 20);
    add(decoration);
  }

  public void setUserName(String user) {
    title.setText("< " + user + "'s Map >");
  }

  public void setHitsTookCount(int hits) {
    takenHitsLabel.setText("Hits took: " + hits);
  }
}
