package application;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Character extends Pane implements Constants {
  private int score = 0;
  private int health = 100;
  private Bots removeBot = null;
  private ImageView mainHeroView;
  public SpriteAnimation animation;

  public Character(ImageView modelView) {
    // creature main hero
    this.mainHeroView = modelView;
    this.mainHeroView.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
    animation = new SpriteAnimation(modelView, Duration.millis(200),
        count, columns, offsetX, offsetY, width, height);
    getChildren().addAll(modelView);
  }

  public void moveX(int posX, Pane root) {
    // moving main hero by X coordinate
    boolean right = posX > 0 ? true : false;
    for (int i = 0; i < Math.abs(posX); i++) {
      if (right) {
        this.setTranslateX(this.getTranslateX() + 1);
      } else {
        this.setTranslateX(this.getTranslateX() - 1);
      }
      isBotEat(root);
    }
  }

  public void moveY(int posY, Pane root) {
    // moving main hero by Y coordinate
    boolean down = posY > 0 ? true : false;
    for (int i = 0; i < Math.abs(posY); i++) {
      if (down) {
        this.setTranslateY(this.getTranslateY() + 1);
      } else {
        this.setTranslateY(this.getTranslateY() - 1);
      }
      isBotEat(root);
    }
  }

  public void isBotEat(Pane root) {
    // bot reached main hero
    StartGame.bots.forEach((bot) -> {
      if (this.getBoundsInParent().intersects(bot.getBoundsInParent())) {
        removeBot = bot;
        health--;
      }
    });
    StartGame.bots.remove(removeBot);
    root.getChildren().remove(removeBot);
  }

  public void addScore(int number) {
    // add to score
    score += number;
  }

  public int getScore() {
    // to look score
    return score;
  }

  public int getHealth(){
    // to look health
    return health;
  }
}