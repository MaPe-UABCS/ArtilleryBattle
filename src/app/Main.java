package app;

import app.views.components.ArtilleryMap;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame implements ActionListener {

  private JPanel body;

  public Main() {
    // singletons
    new SoundManager();
    new AssetManager();
    // ----------------------

    body = new JPanel();
    body.setLayout(null);

    // frame config
    setLayout(new BorderLayout());
    setSize(1600, 800);
    setLocationRelativeTo(null);
    setTitle("ArtilleryBattle");
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    // adding componetns
    ArtilleryMap anim = new ArtilleryMap(60);
    anim.setBounds(0, 0, 800, 800);
    body.add(anim);

    ArtilleryMap map2 = new ArtilleryMap(60);
    map2.setBounds(800, 0, 800, 800);
    body.add(map2);
    map2.setMapActive(true);

    // set setVisible
    add(body);
    setVisible(true);
    anim.startAnimation();
    map2.startAnimation();

    map2.setCellStatus(0, 5, ArtilleryMap.CellStatuses.hit);
    map2.setCellStatus(9, 0, ArtilleryMap.CellStatuses.blank);

    map2.setActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    SoundManager.playSound("Alert sound 2.wav");
  }

  private void readMousePos() {}

  public static void main(String[] args) {
    new Main();
  }
}
