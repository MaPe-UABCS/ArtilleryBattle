package app;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame {

  private JPanel body;

  public Main() {
    body = new JPanel();
    body.setLayout(null);

    // frame config
    setLayout(new BorderLayout());
    setSize(500, 500);
    setLocationRelativeTo(null);
    setTitle("ArtilleryBattle");
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    // adding componetns
    ArtilleryMap anim = new ArtilleryMap(60);
    anim.setBounds(0, 0, 500, 500);
    body.add(anim);

    // set setVisible
    add(body);
    setVisible(true);

    anim.startAnimation();
  }

  private void readMousePos() {}

  public static void main(String[] args) {
    AssetManager.loadAssets("assets");
    new Main();
  }
}
