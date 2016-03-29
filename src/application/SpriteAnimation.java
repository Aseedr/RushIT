package application;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SpriteAnimation extends Transition {
  private final ImageView imageView;
  private final int count;
  private final int columns;
  private int offsetX;
  private int offsetY;
  private final int width;
  private final int height;

  public SpriteAnimation(ImageView imageView, Duration duration, int count,
      int columns, int offsetX, int offsetY, int width, int height) {
    // creating sprite animation for main hero or bots
    this.imageView = imageView;
    this.count = count;
    this.columns = columns;
    this.offsetX = offsetX;
    this.offsetY = offsetY;
    this.width = width;
    this.height = height;
    setCycleDuration(duration);
    setCycleCount(Animation.INDEFINITE);
    setInterpolator(Interpolator.LINEAR);
    this.imageView.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
  }

  public void setOffsetX(int posX) {
    // change side for x
    this.offsetX = posX;
  }

  public void setOffsetY(int posY) {
    // change side for y
    this.offsetY = posY;
  }

  protected void interpolate(double frac) {
    // move animation for main hero or bots
    final int index = Math.min((int) Math.floor(count * frac), count - 1);
    final int x = (index % columns) * width + offsetX;
    final int y = (index / columns) * height + offsetY;
    imageView.setViewport(new Rectangle2D(x, y, width, height));
  }
}