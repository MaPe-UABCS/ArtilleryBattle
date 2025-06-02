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
    setSize(800, 800);
    setLocationRelativeTo(null);
    setTitle("ArtilleryBattle");
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    // adding componetns
    ArtilleryMap anim = new ArtilleryMap(60);
    anim.setBounds(0, 0, 800, 800);
    body.add(anim);

    // set setVisible
    add(body);
    setVisible(true);
    anim.setMapActive(true);
    anim.setActionListener(this);
    anim.startAnimation();
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
