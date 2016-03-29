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

public class StartGame extends Application implements Constants {
  private final int CREATE_BOT = 5;
  private final int SHOOT_START = 10;
  private final int RANDOM = 20;
  private final int STEP = 2;
  private int gameLevel = 0;
  private int createShoot = 0;
  private Direction direction = Direction.DIRECTION_DOWN;
  private HashMap<KeyCode, Boolean> keys = new HashMap<>();
  public static ArrayList<Bots> bots = new ArrayList<>();

  // creating main hero
  Image mainHeroImage = new Image(getClass().
      getResourceAsStream("resorses/models/mainHero.png"));
  ImageView mainHeroView = new ImageView(mainHeroImage);
  Character player = new Character(mainHeroView);

  public void createBots(Pane root) {
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
      bots.add(bot);
      root.getChildren().addAll(bot);
    }
  }

  public void update(Text score, Text health, Scene scene, Pane root) {
    // update score and health on screen
    score.setText("Score: " + player.getScore());
    health.setText("Health: " + player.getHealth());

    // moving bots
    bots.forEach((bot) -> {
      bot.moveToHero(player.getBoundsInParent().getMaxX() - HERO_SIZE / 2,
          player.getBoundsInParent().getMaxY() - HERO_SIZE / 2);
    });

    // moving main hero
    if (isPressed(KeyCode.UP) && player.getTranslateY() >= 0) {
      direction = Direction.DIRECTION_UP;
      player.animation.play();
      player.animation.setOffsetY(HERO_SIZE * Sides.UP.value);
      player.moveY(-STEP, root);
    } else if (isPressed(KeyCode.DOWN) &&
        player.getTranslateY() <= SCREEN_HEIGHT - HERO_SIZE) {
      direction = Direction.DIRECTION_DOWN;
      player.animation.play();
      player.animation.setOffsetY(HERO_SIZE * Sides.DOWN.value);
      player.moveY(STEP, root);
    } else if (isPressed(KeyCode.RIGHT) &&
        player.getTranslateX() <= SCREEN_WIDTH - HERO_SIZE) {
      direction = Direction.DIRECTION_RIGHT;
      player.animation.play();
      player.animation.setOffsetY(HERO_SIZE * Sides.RIGHT.value);
      player.moveX(STEP, root);
    } else if (isPressed(KeyCode.LEFT) && player.getTranslateX() >= 0) {
      direction = Direction.DIRECTION_LEFT;
      player.animation.play();
      player.animation.setOffsetY(HERO_SIZE * Sides.LEFT.value);
      player.moveX(-STEP, root);
    } else {
      player.animation.stop();
    }
    player.isBotEat(root);

    // creating shoots
    if (isPressed(KeyCode.SPACE)) {
      createShoot++;
      if (createShoot == SHOOT_START) {
        Shoot shoot = new Shoot();
        root.getChildren().addAll(shoot.shootMake(scene, player.
            getTranslateX(), player.getTranslateY(), direction, root));
        createShoot = 0;
        player.addScore(shoot.getScore());
        shoot.clearScore();
      }
    }
  }

  public boolean isPressed(KeyCode key) {
    // is pressed or not
    return keys.getOrDefault(key, false);
  }

  public void start(Stage primaryStage, Levels type, Maps map) {
    Pane root = new Pane();

    // load map
    if (map == Maps.DESERT_MAP) { // desert map = 1
      Image gameMap = new Image(getClass().
          getResourceAsStream("resorses/maps/desert.png"));
      ImageView mapView = new ImageView(gameMap);
      mapView.setFitHeight(SCREEN_HEIGHT);
      mapView.setFitWidth(SCREEN_WIDTH);
      root.getChildren().add(mapView);
    } else if (map == Maps.FIELD_MAP) { // field map = 2
      Image gameMap = new Image(getClass().
          getResourceAsStream("resorses/maps/field.png"));
      ImageView mapView = new ImageView(gameMap);
      mapView.setFitHeight(SCREEN_HEIGHT);
      mapView.setFitWidth(SCREEN_WIDTH);
      root.getChildren().add(mapView);
    } else if (map == Maps.SANDY_ROAD_MAP) { // sandy road map = 3
      Image gameMap = new Image(getClass().
          getResourceAsStream("resorses/maps/sandyRoad.png"));
      ImageView mapView = new ImageView(gameMap);
      mapView.setFitHeight(SCREEN_HEIGHT);
      mapView.setFitWidth(SCREEN_WIDTH);
      root.getChildren().add(mapView);
    }

    // load difficulty
    if (type == Levels.EASY_LEVEL)
      gameLevel = Levels.EASY_LEVEL.value;
    else if (type == Levels.NORMAL_LEVEL)
      gameLevel = Levels.NORMAL_LEVEL.value;
    else if (type == Levels.HARD_LEVEL)
      gameLevel = Levels.HARD_LEVEL.value;
    else if (type == Levels.STRONG_LEVEL)
      gameLevel = Levels.STRONG_LEVEL.value;

    // write score on screen
    Text score = new Text("Score: " + player.getScore());
    score.setFont(Font.loadFont(getClass().
        getResourceAsStream("resorses/font/Chiller-Regular.ttf"), 50));
    score.setFill(Color.RED);
    root.getChildren().add(score);
    score.setTranslateX(1050);
    score.setTranslateY(40);
    score.setVisible(true);

    // write health on screen
    Text health = new Text("Health: " + player.getHealth());
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
        createBots(root);

        // exit if escape
        if (isPressed(KeyCode.ESCAPE)) {
          stop();
          bots.clear();
          RushIt backToMainMenu = new RushIt();
          backToMainMenu.start(primaryStage);
        }

        // exit if game over
        if (player.getHealth() <= 0) {
          health.setText("Health: " + player.getHealth());
          stop();
          bots.clear();

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
              case ESCAPE: {
                RushIt backToMainMenu = new RushIt();
                backToMainMenu.start(primaryStage);
                break;
              }
              default: {
                break;
              }
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
    //it's not part of program, it's use for test and debugging
    start(primaryStage, null, null);
  }
}