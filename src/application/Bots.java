package application;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Bots extends Pane implements Constants {
  private final int rand = 2;
  private final int step = 1;
  private ImageView botsView;
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
    animation.play();
    if (botPosX > heroPosX) {
      if (botPosY > heroPosY) {
        int random = (int) Math.floor(Math.random() * rand);
        if (random == step) {
          animation.setOffsetY(HERO_SIZE * Sides.LEFT.value);
          moveX(-step);
        }
        else {
          animation.setOffsetY(HERO_SIZE * Sides.UP.value);
          moveY(-step);
        }
      }
      if (botPosY < heroPosY) {
        int random = (int) Math.floor(Math.random() * rand);
        if (random == step) {
          animation.setOffsetY(HERO_SIZE * Sides.LEFT.value);
          moveX(-step);
        }
        else {
          animation.setOffsetY(HERO_SIZE * Sides.DOWN.value);
          moveY(step);
        }
      }
      if (botPosY == heroPosY) {
        animation.setOffsetY(HERO_SIZE * Sides.LEFT.value);
        moveX(-step);
      }
    }
    if (botPosX < heroPosX) {
      if (botPosY > heroPosY) {
        int random = (int) Math.floor(Math.random() * rand);
        if (random == step) {
          animation.setOffsetY(HERO_SIZE * Sides.RIGHT.value);
          moveX(step);
        }
        else {
          animation.setOffsetY(HERO_SIZE * Sides.UP.value);
          moveY(-step);
        }
      }
      if (botPosY < heroPosY) {
        int random = (int) Math.floor(Math.random() * rand);
        if (random == step) {
          animation.setOffsetY(HERO_SIZE * Sides.RIGHT.value);
          moveX(step);
        }
        else {
          animation.setOffsetY(HERO_SIZE * Sides.DOWN.value);
          moveY(step);
        }
      }
      if (botPosY == heroPosY) {
        animation.setOffsetY(HERO_SIZE * Sides.RIGHT.value);
        moveX(step);
      }
    }
    if (botPosX == heroPosX) {
      if (botPosY > heroPosY) {
        animation.setOffsetY(HERO_SIZE * Sides.UP.value);
        moveY(-step);
      }
      if (botPosY < heroPosY) {
        animation.setOffsetY(HERO_SIZE * Sides.DOWN.value);
        moveY(step);
      }
    }
  }

  public void moveX(int posX) {
    // moving bots by X coordinate
    boolean right = posX > 0 ? true : false;
    for (int i = 0; i < Math.abs(posX); i++) {
      if (right) {
        this.setTranslateX(this.getTranslateX() + 1);
      } else {
        this.setTranslateX(this.getTranslateX() - 1);
      }
    }
  }

  public void moveY(int posY) {
    // moving bots by Y coordinate
    boolean down = posY > 0 ? true : false;
    for (int i = 0; i < Math.abs(posY); i++) {
      if (down) {
        this.setTranslateY(this.getTranslateY() + 1);
      } else {
        this.setTranslateY(this.getTranslateY() - 1);
      }
    }
  }
}