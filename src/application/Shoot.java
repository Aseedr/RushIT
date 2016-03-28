package application;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Shoot extends Pane {
  private static final Duration TRANSLATE_DURATION = Duration.seconds(0.3);
  private static int Hero_Center = 16;
  private static int Screen_Width = 1280;
  private static int Screen_Height = 720;
  private static double TranslateX;
  private static double TranslateY;
  private static int Deraction;
  private static int Shoot_Radius = 4;
  private static int score = 0;
  private Bots removeBot = null;
	
  public Circle shootMake(Scene scene, double playerTranslateX,
    double playerTranslateY, int playerDeraction, Pane root) {
    // create shoot
    TranslateX = playerTranslateX;
    TranslateY =playerTranslateY;
    Deraction = playerDeraction;
    final Circle shoot = new Circle(TranslateX + Hero_Center,
      TranslateY + Hero_Center, Shoot_Radius, Color.RED);
    TranslateTransition transition = createTranslateTransition(shoot);
    moveShoot(scene, shoot, transition, root);
    return shoot;
  }

  private TranslateTransition createTranslateTransition(final Circle shoot) {
    // moving shoot
    final TranslateTransition transition =
      new TranslateTransition(TRANSLATE_DURATION, shoot);
    transition.setOnFinished(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent t) {
        shoot.setCenterX(shoot.getTranslateX() + shoot.getCenterX());
        shoot.setCenterY(shoot.getTranslateY() + shoot.getCenterY());
      }
    });
    return transition;
  }
	
  private void moveShoot(Scene scene, final Circle shoot,
    final TranslateTransition transition, Pane root) {
    // moving shoot by deraction
    if (Deraction == 0) { // down deraction
      transition.setToX(TranslateX + Hero_Center - shoot.getCenterX());
      transition.setToY(Screen_Height + Hero_Center - shoot.getCenterY());
      transition.playFromStart();
      isBonuseEatDown(shoot, root);
    }
    if (Deraction == 1) { // left deraction
      transition.setToX(- Hero_Center - shoot.getCenterX());
      transition.setToY(TranslateY + Hero_Center - shoot.getCenterY());
      transition.playFromStart();
      isBonuseEatLeft(shoot, root);
    }
    if (Deraction == 2) { // up deraction
      transition.setToX(TranslateX + Hero_Center - shoot.getCenterX());
      transition.setToY(- Hero_Center - shoot.getCenterY());
      transition.playFromStart();
      isBonuseEatUp(shoot, root);
    }
    if (Deraction == 3) { // right deraction
      transition.setToX(Screen_Width + Hero_Center - shoot.getCenterX());
      transition.setToY(TranslateY + Hero_Center - shoot.getCenterY());
      transition.playFromStart();
      isBonuseEatRight(shoot, root);
    }
  }
	
  public void isBonuseEatRight(final Circle shoot, Pane root) {
    // shoot into right bots
    StartGame.bonuses.forEach((bot) -> {
      if (bot.getBoundsInParent().getMaxY() -
        shoot.getBoundsInParent().getMaxY() > - Shoot_Radius * 2 &&
        shoot.getBoundsInParent().getMinY() -
        bot.getBoundsInParent().getMinY() > - Shoot_Radius * 2 &&
        bot.getBoundsInParent().getMinX() >
        shoot.getBoundsInParent().getMinX()) {
        removeBot = bot;
        score = 1;
      }
    });
    StartGame.bonuses.remove(removeBot);
    root.getChildren().remove(removeBot);
  }

  public void isBonuseEatLeft(final Circle shoot, Pane root) {
    // shoot into left bots
    StartGame.bonuses.forEach((Bot) -> {
      if (Bot.getBoundsInParent().getMaxY() -
        shoot.getBoundsInParent().getMaxY() > - Shoot_Radius * 2 &&
        shoot.getBoundsInParent().getMinY() -
        Bot.getBoundsInParent().getMinY() > - Shoot_Radius * 2 &&
        shoot.getBoundsInParent().getMaxX() >
        Bot.getBoundsInParent().getMaxX()) {
        removeBot = Bot;
        score = 1;
      }
    });
    StartGame.bonuses.remove(removeBot);
    root.getChildren().remove(removeBot);
  }
	
  public void isBonuseEatUp(final Circle shoot, Pane root) {
    // shoot into up bots
    StartGame.bonuses.forEach((Bot) -> {
      if (Bot.getBoundsInParent().getMaxX() -
        shoot.getBoundsInParent().getMaxX() > - Shoot_Radius * 2 &&
        shoot.getBoundsInParent().getMinX() -
        Bot.getBoundsInParent().getMinX() > - Shoot_Radius * 2 &&
        shoot.getBoundsInParent().getMaxY() >
        Bot.getBoundsInParent().getMaxY()) {
        removeBot = Bot;
        score = 1;
      }
    });
    StartGame.bonuses.remove(removeBot);
    root.getChildren().remove(removeBot);
  }
	
  public void isBonuseEatDown(final Circle shoot, Pane root) {
    // shoot into down bots
    StartGame.bonuses.forEach((Bot) -> {
      if (Bot.getBoundsInParent().getMaxX() -
        shoot.getBoundsInParent().getMaxX() > - Shoot_Radius * 2 &&
        shoot.getBoundsInParent().getMinX() -
        Bot.getBoundsInParent().getMinX() > - Shoot_Radius * 2 &&
        Bot.getBoundsInParent().getMinY() >
        shoot.getBoundsInParent().getMinY()) {
        removeBot = Bot;
        score = 1;
      }
    });
    StartGame.bonuses.remove(removeBot);
    root.getChildren().remove(removeBot);
  }
	
  public int Score() {
    // look score
    return score;
  }
  
  public void ScoreClear() {
    // clear score
    score = 0;
  }
}