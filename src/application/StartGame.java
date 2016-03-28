package application;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class StartGame extends Application {
  private final int SCREEN_WIDTH = 1280;
  private final int SCREEN_HEIGHT = 720;
  private final int HERO_SIZE = 32;
  private final int STEP = 2;
  private final int SHOOT_START = 10;
  private final int CREATE_BOT = 5;
  private final int DOWN = 0;
  private final int LEFT = 1;
  private final int RIGHT = 2;
  private final int UP = 3;
  private final int EASY_LEVEL = 4;
  private final int NORMAL_LEVEL = 3;
  private final int HARD_LEVEL = 2;
  private final int STRONG_LEVEL = 1;
  private final int DESERT_MAP = 1;
  private final int FIELD_MAP = 2;
  private final int SANDY_ROAD_MAP = 3;
  private final int RANDOM = 20;
  private int gameLevel = 0;
  private int createShoot = 0;
  private int direction = 0;
  private HashMap<KeyCode, Boolean> keys = new HashMap<>();
  public static ArrayList<Bots> bonuses = new ArrayList<>();

  // creating main hero
  Image mainHeroImage = new Image(getClass().
    getResourceAsStream("resorses/models/mainHero.png"));
  ImageView mainHeroView = new ImageView(mainHeroImage);
  Character player = new Character(mainHeroView);

  public void bonus(Pane root) {
    // creating bots with random coordinates
    int random = (int) Math.floor(Math.random() * (RANDOM * gameLevel));
    int x = (int) Math.floor(Math.random() * SCREEN_WIDTH);
    int y = (int) Math.floor(Math.random() * SCREEN_HEIGHT);
    if (random == CREATE_BOT) {
      Image botsImage = new Image(getClass().
        getResourceAsStream("resorses/models/Bots.png"));
      ImageView botsView = new ImageView(botsImage);
      Bots bot = new Bots(botsView);
      bot.setTranslateX(x);
      bot.setTranslateY(y);
      bonuses.add(bot);
      root.getChildren().addAll(bot);
    }
  }

  public void update(Text score, Text health, Scene scene, Pane root) {
    // update score and health on screen
    score.setText("Score: " + player.Score());
    health.setText("Health: " + player.Health());

    // moving bots
    bonuses.forEach((bot) -> {
      bot.moveToHero(player.getBoundsInParent().getMaxX() - HERO_SIZE / 2,
        player.getBoundsInParent().getMaxY() - HERO_SIZE / 2);
    });

    // moving main hero
    if (isPressed(KeyCode.UP) && player.getTranslateY() >= 0) {
      direction = 2; // up direction
      player.animation.play();
      player.animation.setOffsetY(HERO_SIZE * UP);
      player.moveY(-STEP, root);
    } else if (isPressed(KeyCode.DOWN) &&
      player.getTranslateY() <= SCREEN_HEIGHT - HERO_SIZE) {
      direction = 0; // down direction
      player.animation.play();
      player.animation.setOffsetY(HERO_SIZE * DOWN);
      player.moveY(STEP, root);
    } else if (isPressed(KeyCode.RIGHT) &&
      player.getTranslateX() <= SCREEN_WIDTH - HERO_SIZE) {
      direction = 3; // right direction
      player.animation.play();
      player.animation.setOffsetY(HERO_SIZE * RIGHT);
      player.moveX(STEP, root);
    } else if (isPressed(KeyCode.LEFT) && player.getTranslateX() >= 0) {
      direction = 1; // left direction
      player.animation.play();
      player.animation.setOffsetY(HERO_SIZE * LEFT);
      player.moveX(-STEP, root);
    } else {
      player.animation.stop();
    }
    player.isBonuseEat(root);

    // creating shoots
    if (isPressed(KeyCode.SPACE)){
    	createShoot++;
      if (createShoot == SHOOT_START){
        Shoot shoot = new Shoot();
        root.getChildren().addAll(shoot.shootMake(scene, player.
          getTranslateX(), player.getTranslateY(), direction, root));
        createShoot = 0;
        player.ScoreAdd(shoot.Score());
        shoot.ScoreClear();
      }
    }
  }

  public boolean isPressed(KeyCode key) {
    // is pressed or not
    return keys.getOrDefault(key, false);
  }

  public void start(Stage primaryStage, int type, int map) {
    Pane root = new Pane();

    // load map
    if (map == DESERT_MAP) { // desert map = 1
      Image gameMap = new Image(getClass().
        getResourceAsStream("resorses/maps/desert.png"));
      ImageView mapView = new ImageView(gameMap);
      mapView.setFitHeight(SCREEN_HEIGHT);
      mapView.setFitWidth(SCREEN_WIDTH);
      root.getChildren().add(mapView);
    } else if (map == FIELD_MAP) { // field map = 2
      Image gameMap = new Image(getClass().
        getResourceAsStream("resorses/maps/field.png"));
      ImageView mapView = new ImageView(gameMap);
      mapView.setFitHeight(SCREEN_HEIGHT);
      mapView.setFitWidth(SCREEN_WIDTH);
      root.getChildren().add(mapView);
    } else if (map == SANDY_ROAD_MAP) { // sandy road map = 3
      Image gameMap = new Image(getClass().
        getResourceAsStream("resorses/maps/sandyRoad.png"));
      ImageView mapView = new ImageView(gameMap);
      mapView.setFitHeight(SCREEN_HEIGHT);
      mapView.setFitWidth(SCREEN_WIDTH);
      root.getChildren().add(mapView);
    }

    // load difficulty
    if (type == EASY_LEVEL)
      gameLevel = EASY_LEVEL;
    else if (type == NORMAL_LEVEL)
      gameLevel = NORMAL_LEVEL;
    else if (type == HARD_LEVEL)
      gameLevel = HARD_LEVEL;
    else if (type == STRONG_LEVEL)
      gameLevel = STRONG_LEVEL;

    // write score on screen
    Text score = new Text("Score: " + player.Score());
    score.setFont(Font.loadFont(getClass().
      getResourceAsStream("resorses/font/Chiller-Regular.ttf"), 50));
    score.setFill(Color.RED);
    root.getChildren().add(score);
    score.setTranslateX(1050);
    score.setTranslateY(40);
    score.setVisible(true);

    // write health on screen
    Text health = new Text("Health: " + player.Health());
    health.setFont(Font.loadFont(getClass().
      getResourceAsStream("resorses/font/Chiller-Regular.ttf"), 50));
    health.setFill(Color.RED);
    root.getChildren().add(health);
    health.setTranslateX(1080);
    health.setTranslateY(90);
    health.setVisible(true);

    // inset main hero
    player.setTranslateX(SCREEN_WIDTH / 2 - HERO_SIZE / 2);
    player.setTranslateY(SCREEN_HEIGHT / 2 - HERO_SIZE / 2);
    root.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT);
    root.getChildren().addAll(player);

    // analysis pressed key
    Scene scene = new Scene(root);
    scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
    scene.setOnKeyReleased(event -> {
      keys.put(event.getCode(), false);
    });

    AnimationTimer timer = new AnimationTimer() {
      @Override
      public void handle(long now) {
        // update process
        update(score, health, scene, root);
        bonus(root);

        // exit if escape
        if (isPressed(KeyCode.ESCAPE)) {
          stop();
          bonuses.clear();
          RushIt backToMainMenu = new RushIt();
          backToMainMenu.start(primaryStage);
        }

        // exit if game over
        if (player.Health() <= 0) {
          health.setText("Health: " + player.Health());
          stop();
          bonuses.clear();

          // write game over
          Text gameOver = new Text("Game Over");
          gameOver.setFont(Font.loadFont(getClass().
            getResourceAsStream("resorses/font/Chiller-Regular.ttf"), 300));
          gameOver.setFill(Color.RED);
          root.getChildren().add(gameOver);
          gameOver.setTranslateX((SCREEN_WIDTH - gameOver.getBoundsInParent()
            .getMaxX() - gameOver.getBoundsInParent().getMinX()) / 2);
          gameOver.setTranslateY((SCREEN_HEIGHT - gameOver.getBoundsInParent()
            .getMaxY() - gameOver.getBoundsInParent().getMinY()) / 2);
          gameOver.setVisible(true);

          // wait escape
          scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
              switch (event.getCode()) {
              case ESCAPE:
                RushIt backToMainMenu = new RushIt();
                backToMainMenu.start(primaryStage);
                break;
              default:
                break;
              }
            }
          });
        }
      }
    };

    // start game
    timer.start();

    primaryStage.setTitle("Rush It");
    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.show();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    //use for tests
    start(primaryStage, 4, 1);
  }
}