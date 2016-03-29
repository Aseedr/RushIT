package application;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Shoot extends Pane implements Constants {
  private static final Duration TRANSLATE_DURATION = Duration.seconds(0.3);
  private final int SHOOT_RADIUS = 4;
  private static double translateX;
  private static double translateY;
  private static Direction direction;
  private static int score = 0;
  private Bots removeBot = null;

  public Circle shootMake(Scene scene, double playerTranslateX,
      double playerTranslateY, Direction playerDeraction, Pane root) {
    // create shoot
    translateX = playerTranslateX;
    translateY = playerTranslateY;
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
    if (direction == Direction.DIRECTION_DOWN) { // down direction
      transition.setToX(translateX + HERO_CENTER - shoot.getCenterX());
      transition.setToY(SCREEN_HEIGHT + HERO_CENTER - shoot.getCenterY());
      transition.playFromStart();
      isBotEatDown(shoot, root);
    }
    if (direction == Direction.DIRECTION_LEFT) { // left direction
      transition.setToX(- HERO_CENTER - shoot.getCenterX());
      transition.setToY(translateY + HERO_CENTER - shoot.getCenterY());
      transition.playFromStart();
      isBotEatLeft(shoot, root);
    }
    if (direction == Direction.DIRECTION_UP) { // up direction
      transition.setToX(translateX + HERO_CENTER - shoot.getCenterX());
      transition.setToY(- HERO_CENTER - shoot.getCenterY());
      transition.playFromStart();
      isBotEatUp(shoot, root);
    }
    if (direction == Direction.DIRECTION_RIGHT) { // right direction
      transition.setToX(SCREEN_WIDTH + HERO_CENTER - shoot.getCenterX());
      transition.setToY(translateY + HERO_CENTER - shoot.getCenterY());
      transition.playFromStart();
      isBotEatRight(shoot, root);
    }
  }

  public void isBotEatRight(final Circle shoot, Pane root) {
    // shoot into right bots
    StartGame.bots.forEach((bot) -> {
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
    StartGame.bots.remove(removeBot);
    root.getChildren().remove(removeBot);
  }

  public void isBotEatLeft(final Circle shoot, Pane root) {
    // shoot into left bots
    StartGame.bots.forEach((Bot) -> {
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
    StartGame.bots.remove(removeBot);
    root.getChildren().remove(removeBot);
  }

  public void isBotEatUp(final Circle shoot, Pane root) {
    // shoot into up bots
    StartGame.bots.forEach((Bot) -> {
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
    StartGame.bots.remove(removeBot);
    root.getChildren().remove(removeBot);
  }

  public void isBotEatDown(final Circle shoot, Pane root) {
    // shoot into down bots
    StartGame.bots.forEach((Bot) -> {
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
    StartGame.bots.remove(removeBot);
    root.getChildren().remove(removeBot);
  }

  public int getScore() {
    // look score
    return score;
  }

  public void clearScore() {
    // clear score
    score = 0;
  }
}