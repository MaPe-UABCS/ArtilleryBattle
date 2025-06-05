package app.controllers;

import java.awt.event.ActionEvent;

import app.Main;
import app.controllers.Controller;;
import app.views.GameView;

public class GameController extends Controller {

  GameView gameView;

  public GameController() {
    gameView = Main.getViewReference("Game");
    gameView.setActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    
      
  }
}
