package app;

import app.views.LoginView;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
    body.setBackground(Style.getColor(Style.background));
    body.setPreferredSize(new Dimension(600, 600));

    // frame config
    setLayout(new BorderLayout());
    // setSize(600 + getInsets().left + getInsets().right, 600 + getInsets().top +
    setLocationRelativeTo(null);
    setResizable(false);
    setTitle("ArtilleryBattle");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    // --------------

    // TODO: check the prefs an see if the user has logged in before
    // TEST delete later
    LoginView loginView = new LoginView("login");
    body.add(loginView);
    // --------------

    // set setVisible
    add(body);
    this.pack();
    setVisible(true);
    // --------------
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    SoundManager.playSound("Alert sound 2.wav");
  }

  public static void main(String[] args) {
    new Main();
  }
}
