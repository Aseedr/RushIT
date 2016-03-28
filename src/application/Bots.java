package application;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Bots extends Pane {
  private ImageView botsView;
  private final int count = 3;
  private final int columns = 3;
  private final int offsetX = 0;
  private final int offsetY = 0;
  private final int width = 32;
  private final int height = 32;
  private final int HERO_CENTER = 16;
  private final int step = 1;
  private final int rand = 2;
  public SpriteAnimation animation;

  public Bots(ImageView imageView) {
    // add new bot
    this.botsView = imageView;
    this.botsView.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
    animation = new SpriteAnimation(imageView, Duration.millis(200),
      count, columns, offsetX, offsetY, width, height);
    getChildren().addAll(imageView);
  }

  public void moveToHero(double heroPosX, double heroPosY) {
    double botPosX = getBoundsInParent().getMaxX() - HERO_CENTER;
    double botPosY = getBoundsInParent().getMaxY() - HERO_CENTER;

    // moving bots by steps to main hero
    if (botPosX > heroPosX) {
      if (botPosY > heroPosY) {
        int random = (int) Math.floor(Math.random() * rand);
        if (random == step) {
          animation.play();
          animation.setOffsetY(32);
          moveX(-step);
        }
        else {
          animation.play();
          animation.setOffsetY(96);
          moveY(-step);
        }
      }
      if (botPosY < heroPosY) {
        int random = (int) Math.floor(Math.random() * rand);
        if (random == step) {
          animation.play();
          animation.setOffsetY(32);
          moveX(-step);
        }
        else {
          animation.play();
          animation.setOffsetY(0);
          moveY(step);
        }
      }
      if (botPosY == heroPosY) {
        animation.play();
        animation.setOffsetY(32);
        moveX(-step);
      }
    }
    if (botPosX < heroPosX) {
      if (botPosY > heroPosY) {
        int random = (int) Math.floor(Math.random() * rand);
        if (random == step) {
          animation.play();
          animation.setOffsetY(64);
          moveX(step);
        }
        else {
          animation.play();
          animation.setOffsetY(96);
          moveY(-step);
        }
      }
      if (botPosY < heroPosY) {
        int random = (int) Math.floor(Math.random() * rand);
        if (random == step) {
          animation.play();
          animation.setOffsetY(64);
          moveX(step);
        }
        else {
          animation.play();
          animation.setOffsetY(0);
          moveY(step);
        }
      }
      if (botPosY == heroPosY) {
        animation.play();
        animation.setOffsetY(64);
        moveX(step);
      }
    }
    if (botPosX == heroPosX) {
      if (botPosY > heroPosY) {
        animation.play();
        animation.setOffsetY(96);
        moveY(-step);
      }
      if (botPosY < heroPosY) {
        animation.play();
        animation.setOffsetY(0);
        moveY(step);
      }
    }
  }

  public void moveX(int x) {
    // moving bots by X coordinate
    boolean right = x > 0 ? true : false;
    for (int i = 0; i < Math.abs(x); i++) {
      if (right)
        this.setTranslateX(this.getTranslateX() + 1);
      else
        this.setTranslateX(this.getTranslateX() - 1);
    }
  }

  public void moveY(int y) {
    // moving bots by Y coordinate
    boolean down = y > 0 ? true : false;
    for (int i = 0; i < Math.abs(y); i++) {
      if (down)
        this.setTranslateY(this.getTranslateY() + 1);
      else
        this.setTranslateY(this.getTranslateY() - 1);
    }
  }
}