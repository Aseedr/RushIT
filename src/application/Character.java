package application;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Character extends Pane {
  private ImageView mainHeroView;
  private static int count = 3;
  private static int columns = 3;
  private static int offsetX = 0;
  private static int offsetY = 0;
  private static int width = 32;
  private static int height = 32;
  private int score = 0;
  private int health = 100;
  private Bots removeBot = null;
  public SpriteAnimation animation;

  public Character(ImageView modelView) {
    // creature main hero
    this.mainHeroView = modelView;
    this.mainHeroView.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
    animation = new SpriteAnimation(modelView, Duration.millis(200),
      count, columns, offsetX, offsetY, width, height);
    getChildren().addAll(modelView);
  }

  public void moveX(int x, Pane root) {
    // moving main hero by X coordinate
    boolean right = x > 0 ? true : false;
    for (int i = 0; i < Math.abs(x); i++) {
      if (right)
        this.setTranslateX(this.getTranslateX() + 1);
      else
        this.setTranslateX(this.getTranslateX() - 1);
      isBonuseEat(root);
    }
  }

  public void moveY(int y, Pane root) {
	// moving main hero by Y coordinate
    boolean down = y > 0 ? true : false;
    for (int i = 0; i < Math.abs(y); i++) {
      if (down)
        this.setTranslateY(this.getTranslateY() + 1);
      else
        this.setTranslateY(this.getTranslateY() - 1);
      isBonuseEat(root);
    }
  }

  public void isBonuseEat(Pane root) {
    // bot reached main hero
    StartGame.bonuses.forEach((bot) -> {
      if (this.getBoundsInParent().intersects(bot.getBoundsInParent())) {
        removeBot = bot;
        health--;
      }
    });
    StartGame.bonuses.remove(removeBot);
    root.getChildren().remove(removeBot);
  }
	
  public void ScoreAdd(int number) {
    // add to score
    score += number;
  }
	
  public int Score() {
    // to look score
    return score;
  }
	
  public int Health(){
    // to look health
    return health;
  }
}