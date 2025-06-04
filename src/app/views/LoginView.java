package app.views;

import app.AssetManager;
import app.Style;
import app.views.components.AButton;
import app.views.components.ALabel;
import app.views.components.APasswordInputField;
import app.views.components.AppInputField;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

public class LoginView extends View {

  AppInputField userNameField;
  JPasswordField passwordField;

  public LoginView() {
    super("Login");
    setOpaque(false);

    GridBagConstraints gbc = new GridBagConstraints();
    setLayout(new GridBagLayout());

    gbc.insets = new Insets(5, 0, 5, 0);
    gbc.gridy = 1;
    JLabel decorLabelTop =
        new JLabel("< Artillery Battle > /////////////////////", SwingConstants.CENTER);
    decorLabelTop.setFont(AssetManager.getFont("light:26:bold"));
    decorLabelTop.setForeground(Style.getColor(Style.foreground));
    add(decorLabelTop, gbc);

    // FORM
    {
      GridBagConstraints formGBC = new GridBagConstraints();
      JPanel form = new JPanel(new GridBagLayout());
      form.setBackground(Style.getColor(Style.foreground));
      form.setPreferredSize(new Dimension(600, 125));

      // FORM content
      {
        formGBC.gridy = 0;
        formGBC.insets = new Insets(5, 10, 0, 10);

        formGBC.gridx = 0;
        ALabel userLabel = new ALabel("User    ", "thin:26:bold");
        form.add(userLabel, formGBC);

        formGBC.gridx = 1;
        userNameField = new AppInputField(24);
        userNameField.setPreferredSize(new Dimension(200, 30));
        form.add(userNameField, formGBC);

        formGBC.gridy = 1;

        formGBC.gridx = 0;
        ALabel passwordLabel = new ALabel("Password", "thin:26:bold");
        form.add(passwordLabel, formGBC);

        formGBC.gridx = 1;
        passwordField = new APasswordInputField(24);
        passwordField.setPreferredSize(new Dimension(200, 30));
        form.add(passwordField, formGBC);

        formGBC.gridy = 2;

        formGBC.gridx = 0;
        AButton skipButton = new AButton("Skip", 20, AButton.lightSecondary);
        skipButton.setPreferredSize(new Dimension(112, 30));
        registerButton(skipButton);
        form.add(skipButton, formGBC);

        formGBC.gridx = 1;
        AButton loginButton = new AButton("Continue", 24, AButton.darkPrimary);
        loginButton.setPreferredSize(new Dimension(200, 30));
        registerButton(loginButton);
        form.add(loginButton, formGBC);
      }

      gbc.gridy = 2;
      add(form, gbc);
    }

    gbc.gridy = 3;
    JLabel decorLabelBottom =
        new JLabel("//////////////////////////////////////////", SwingConstants.CENTER);
    decorLabelBottom.setFont(AssetManager.getFont("light:26:bold"));
    decorLabelBottom.setForeground(Style.getColor(Style.foreground));
    add(decorLabelBottom, gbc);
  }

  public void before() {
    resetForm();
    revalidate();
    repaint();
  }

  @Override
  public void after() {
      
  }

  public void resetForm() {
    userNameField.setText("");
    passwordField.setText("");
  }

  public String[] getFormData() {
    String password = "";
    for (char c : passwordField.getPassword()) {
      password += c;
    }
    return new String[] {userNameField.getText(), password};
  }

  @Override
  protected void paintComponent(Graphics g) {
    g.drawImage(AssetManager.getImageIcon("CrossGrid.png").getImage(), 0, 0, this);
    super.paintComponent(g);
  }
}
