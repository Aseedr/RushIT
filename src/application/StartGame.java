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
  private static int Screen_Width = 1280;
  private static int Screen_Height = 720;
  private static int Hero_Size = 32;
  private static int Step = 2;
  private static int Shoot_Start = 10;
  private static int Create_Bot = 5;
  private int Hard_Level = 0;
  private int Shoot = 0;
  private int Deraction = 0;
  private HashMap<KeyCode, Boolean> keys = new HashMap<>();
  public static ArrayList<Bots> bonuses = new ArrayList<>();

  // creating main hero
  Image mainHeroImage = new Image(getClass().
    getResourceAsStream("resorses/models/mainHero.png"));
  ImageView mainHeroView = new ImageView(mainHeroImage);
  Character player = new Character(mainHeroView);
	
  public void bonus(Pane root) {
    // creating bots with random coordinates
    int random = (int) Math.floor(Math.random() * Hard_Level);
    int x = (int) Math.floor(Math.random() * Screen_Width);
    int y = (int) Math.floor(Math.random() * Screen_Height);
    if (random == Create_Bot) {
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
      bot.moveToHero(player.getBoundsInParent().getMaxX() - Hero_Size / 2,
        player.getBoundsInParent().getMaxY() - Hero_Size / 2);
    });
	
    // moving main hero
    if (isPressed(KeyCode.UP) && player.getTranslateY() >= 0) {
      Deraction = 2; // up deraction
      player.animation.play();
      player.animation.setOffsetY(96);
      player.moveY(-Step, root);
    } else if (isPressed(KeyCode.DOWN) &&
      player.getTranslateY() <= Screen_Height - Hero_Size) {
      Deraction = 0; // down deraction
      player.animation.play();
      player.animation.setOffsetY(0);
      player.moveY(Step, root);
    } else if (isPressed(KeyCode.RIGHT) &&
      player.getTranslateX() <= Screen_Width - Hero_Size) {
      Deraction = 3; // right deraction
      player.animation.play();
      player.animation.setOffsetY(64);
      player.moveX(Step, root);
    } else if (isPressed(KeyCode.LEFT) && player.getTranslateX() >= 0) {
      Deraction = 1; // left deraction
      player.animation.play();
      player.animation.setOffsetY(32);
      player.moveX(-Step, root);
    } else {
      player.animation.stop();
    }
    player.isBonuseEat(root);
	
	
    // creating shoots
    if (isPressed(KeyCode.SPACE)){
      Shoot++;
      if (Shoot == Shoot_Start){
        Shoot shoot = new Shoot();
        root.getChildren().addAll(shoot.shootMake(scene, player.
          getTranslateX(), player.getTranslateY(), Deraction, root));
        Shoot = 0;
        player.ScoreAdd(shoot.Score());
        shoot.ScoreClear();
      }
    }
  }

  public boolean isPressed(KeyCode key) {
    // is pressed or not
    return keys.getOrDefault(key, false);
  }

  public void start(Stage primaryStage, int Type, int Map) {
    Pane root = new Pane();
	
    // load map
    if (Map == 1) { // desert map = 1
      Image map = new Image(getClass().
        getResourceAsStream("resorses/maps/desert.png"));
      ImageView mapView = new ImageView(map);
      mapView.setFitHeight(Screen_Height);
      mapView.setFitWidth(Screen_Width);
      root.getChildren().add(mapView);
    } else if (Map == 2) { // field map = 2
      Image map = new Image(getClass().
        getResourceAsStream("resorses/maps/field.png"));
      ImageView mapView = new ImageView(map);
      mapView.setFitHeight(Screen_Height);
      mapView.setFitWidth(Screen_Width);
      root.getChildren().add(mapView);
    } else { // sandy road map = 3
      Image map = new Image(getClass().
        getResourceAsStream("resorses/maps/sandyRoad.png"));
      ImageView mapView = new ImageView(map);
      mapView.setFitHeight(Screen_Height);
      mapView.setFitWidth(Screen_Width);
      root.getChildren().add(mapView);
    }
	
    // load difficulty
    if (Type == 1) // easy level = 1
      Hard_Level = 100; // easy = 100
    else if (Type == 2) // normal level = 2
      Hard_Level = 70; // normal = 70
    else if (Type == 3) // hard level = 3
      Hard_Level = 40; // hard = 40
    else // strong level = 4
      Hard_Level = 20; // strong = 20
	
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
    player.setTranslateX(Screen_Width / 2 - Hero_Size / 2);
    player.setTranslateY(Screen_Height / 2 - Hero_Size / 2);
    root.setPrefSize(Screen_Width, Screen_Height);
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
          gameOver.setTranslateX((Screen_Width - gameOver.getBoundsInParent()
            .getMaxX() - gameOver.getBoundsInParent().getMinX()) / 2);
          gameOver.setTranslateY((Screen_Height - gameOver.getBoundsInParent()
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
    start(primaryStage, 1, 1);
  }
}