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
  private final int HERO_CENTER = 16;
  private final int SCREEN_WIDTH = 1280;
  private final int SCREEN_HEIGHT = 720;
  private final int SHOOT_RADIUS = 4;
  private final int DOWN = 0;
  private final int LEFT = 1;
  private final int UP = 2;
  private final int RIGHT = 3;
  private static double translateX;
  private static double translateY;
  private static int direction;
  private static int score = 0;
  private Bots removeBot = null;

  public Circle shootMake(Scene scene, double playerTranslateX,
    double playerTranslateY, int playerDeraction, Pane root) {
    // create shoot
    translateX = playerTranslateX;
    translateY =playerTranslateY;
    direction = playerDeraction;
    final Circle shoot = new Circle(translateX + HERO_CENTER,
      translateY + HERO_CENTER, SHOOT_RADIUS, Color.RED);
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
    // moving shoot by direction
    if (direction == DOWN) { // down direction
      transition.setToX(translateX + HERO_CENTER - shoot.getCenterX());
      transition.setToY(SCREEN_HEIGHT + HERO_CENTER - shoot.getCenterY());
      transition.playFromStart();
      isBonuseEatDown(shoot, root);
    }
    if (direction == LEFT) { // left direction
      transition.setToX(- HERO_CENTER - shoot.getCenterX());
      transition.setToY(translateY + HERO_CENTER - shoot.getCenterY());
      transition.playFromStart();
      isBonuseEatLeft(shoot, root);
    }
    if (direction == UP) { // up direction
      transition.setToX(translateX + HERO_CENTER - shoot.getCenterX());
      transition.setToY(- HERO_CENTER - shoot.getCenterY());
      transition.playFromStart();
      isBonuseEatUp(shoot, root);
    }
    if (direction == RIGHT) { // right direction
      transition.setToX(SCREEN_WIDTH + HERO_CENTER - shoot.getCenterX());
      transition.setToY(translateY + HERO_CENTER - shoot.getCenterY());
      transition.playFromStart();
      isBonuseEatRight(shoot, root);
    }
  }

  public void isBonuseEatRight(final Circle shoot, Pane root) {
    // shoot into right bots
    StartGame.bonuses.forEach((bot) -> {
      if (bot.getBoundsInParent().getMaxY() -
        shoot.getBoundsInParent().getMaxY() > - SHOOT_RADIUS * 2 &&
        shoot.getBoundsInParent().getMinY() -
        bot.getBoundsInParent().getMinY() > - SHOOT_RADIUS * 2 &&
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
        shoot.getBoundsInParent().getMaxY() > - SHOOT_RADIUS * 2 &&
        shoot.getBoundsInParent().getMinY() -
        Bot.getBoundsInParent().getMinY() > - SHOOT_RADIUS * 2 &&
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
        shoot.getBoundsInParent().getMaxX() > - SHOOT_RADIUS * 2 &&
        shoot.getBoundsInParent().getMinX() -
        Bot.getBoundsInParent().getMinX() > - SHOOT_RADIUS * 2 &&
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
        shoot.getBoundsInParent().getMaxX() > - SHOOT_RADIUS * 2 &&
        shoot.getBoundsInParent().getMinX() -
        Bot.getBoundsInParent().getMinX() > - SHOOT_RADIUS * 2 &&
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