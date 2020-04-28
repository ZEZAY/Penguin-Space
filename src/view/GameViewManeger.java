package view;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.InfoGameLabel;
import model.SHIP;

import java.util.Random;

public class GameViewManeger {

    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;

    private static final int GAME_WIDTH = 600;
    private static final int GAME_HEIGHT = 800;

    private Stage menuStage;
    private ImageView playerShip;

    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private boolean isUpKeyPressed;
    private boolean isDownKeyPressed;
    private int angle;
    private AnimationTimer gameTimer;
    private boolean isGameRunning;

    private GridPane gridPane1;
    private GridPane gridPane2;
    private final String BACKGROUD_IMAGE = "view/resources/background_black.png";

    private final String METEOR_GREY_IMAGE = "view/resources/meteorGrey_med1.png";
    private final String METEOR_BROWN_IMAGE = "view/resources/meteorBrown_small2.png";

    private ImageView[] greyMeteors;
    private ImageView[] brownMeteors;

    Random randomPositionGenerators;

    private ImageView star;
    private InfoGameLabel gameLabel;
    private ImageView[] playerLifes;
    private int playerLife;
    private int score;
    private final static String STAR_IMAGE = "view/resources/star_gold.png";

    public GameViewManeger() {
        initializeStage();
        createKeyListeners();
        randomPositionGenerators = new Random();
    }

    private void createKeyListeners() {

        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                KeyCode code = keyEvent.getCode();
                switch (code) {
                    case LEFT:
                        isLeftKeyPressed = true;
                        break;
                    case RIGHT:
                        isRightKeyPressed = true;
                        break;
                    case UP:
                        isUpKeyPressed = true;
                        break;
                    case DOWN:
                        isDownKeyPressed = true;
                        break;
                    case ESCAPE:
//                        if (isGameRunning) gameTimer.stop();
//                        else gameTimer.start();
                        gameStage.close();
                        break;
                }
            }
        });

        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                KeyCode code = keyEvent.getCode();
                switch (code) {
                    case LEFT:
                        isLeftKeyPressed = false;
                        break;
                    case RIGHT:
                        isRightKeyPressed = false;
                        break;
                    case UP:
                        isUpKeyPressed = false;
                        break;
                    case DOWN:
                        isDownKeyPressed = false;
                        break;
                }
            }
        });
    }

    private void initializeStage() {
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
    }

    public void createGame(Stage menuStage, SHIP choosenShip) {
        this.menuStage = menuStage;
        this.menuStage.hide();

        createBackground();
        createPlayerShip(choosenShip);
        createGameElements(choosenShip);

        createGameLoop();
        gameStage.show();
    }

    private void createGameElements(SHIP choosenShip) {
        playerLife = 2;
        star = new ImageView(STAR_IMAGE);
        setGameElementNewPosition(star);
        gamePane.getChildren().add(star);

        gameLabel = new InfoGameLabel("POINTS: 00");
        gameLabel.setLayoutX(460);
        gameLabel.setLayoutY(20);
        gamePane.getChildren().add(gameLabel);

        playerLifes = new ImageView[3];
        for (int i = 0; i < playerLifes.length; i++) {
            ImageView item = playerLifes[i];
            item = new ImageView(choosenShip.getUrlPlayerLifeShip());
            item.setLayoutX(455 + (i*50));
            item.setLayoutY(80);
            gamePane.getChildren().add(item);
        }

        greyMeteors = new ImageView[3];
        for (int i = 0; i < greyMeteors.length; i++) {
            greyMeteors[i] = new ImageView(METEOR_GREY_IMAGE);
            setGameElementNewPosition(greyMeteors[i]);
            gamePane.getChildren().add(greyMeteors[i]);
        }

        brownMeteors = new ImageView[3];
        for (int i = 0; i < brownMeteors.length; i++) {
            brownMeteors[i] = new ImageView(METEOR_BROWN_IMAGE);
            setGameElementNewPosition(brownMeteors[i]);
            gamePane.getChildren().add(brownMeteors[i]);
        }
    }

    private void moveGameElement() {

        star.setLayoutY(star.getLayoutY() + 5);
        if (star.getLayoutY()>900) setGameElementNewPosition(star);

        for (int i = 0; i < brownMeteors.length ; i++) {
            ImageView item = brownMeteors[i];
            item.setLayoutY(item.getLayoutY()+7);
            item.setRotate(item.getRotate()+4);
            if (item.getLayoutY()>900) setGameElementNewPosition(item);
        }
        for (int i = 0; i < greyMeteors.length ; i++) {
            ImageView item = greyMeteors[i];
            item.setLayoutY(item.getLayoutY()+7);
            item.setRotate(item.getRotate()+4);
            if (item.getLayoutY()>900) setGameElementNewPosition(item);
        }
    }

    private void setGameElementNewPosition(ImageView img) {
        img.setLayoutX(randomPositionGenerators.nextInt(370));
        img.setLayoutY(-(randomPositionGenerators.nextInt(3200)+600));
    }

    private void createPlayerShip(SHIP choosenShip) {
        playerShip = new ImageView(choosenShip.getUrlShip());
        playerShip.setLayoutX(GAME_WIDTH/2);
        playerShip.setLayoutY(GAME_HEIGHT - 90);
        gamePane.getChildren().add(playerShip);
    }

    private void createGameLoop() {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                moveBackground();
                moveGameElement();
                moveShip();
            }
        };
        isGameRunning = true;
        gameTimer.start();
    }

    private void moveShip() {
        if (isLeftKeyPressed) {
            if (angle > -30) angle -= 5;
            playerShip.setRotate(angle);
            if (playerShip.getLayoutX() > -20)
                playerShip.setLayoutX(playerShip.getLayoutX() - 3);
        }
        if (isRightKeyPressed) {
            if (angle < 30) angle += 5;
            playerShip.setRotate(angle);
            if (playerShip.getLayoutX() < 522)
                playerShip.setLayoutX(playerShip.getLayoutX() + 3);
        }
        if (!isRightKeyPressed && !isLeftKeyPressed) {
            if (angle < 0) angle += 5;
            if (angle > 0) angle -= 5;
            playerShip.setRotate(angle);
        }
        if (isUpKeyPressed) {
            System.out.println(playerShip.getLayoutY());
            if (playerShip.getLayoutY() > 0)
                playerShip.setLayoutY(playerShip.getLayoutY() - 3);
        }
        if (isDownKeyPressed) {
            System.out.println(playerShip.getLayoutY());
            if (playerShip.getLayoutY() < GAME_HEIGHT-90)
                playerShip.setLayoutY(playerShip.getLayoutY() + 3);
        }
    }

    private void createBackground() {
        gridPane1 = new GridPane();
        gridPane2 = new GridPane();
        for (int i = 0; i < 12 ; i++) {
            ImageView bg1 = new ImageView(BACKGROUD_IMAGE);
            ImageView bg2 = new ImageView(BACKGROUD_IMAGE);
            GridPane.setConstraints(bg1, i%3, i/3);
            GridPane.setConstraints(bg2, i%3, i/3);
            gridPane1.getChildren().add(bg1);
            gridPane2.getChildren().add(bg2);
        }
        gridPane1.setLayoutY(-1024);
        gamePane.getChildren().addAll(gridPane1, gridPane2);
    }

    private void moveBackground() {
        gridPane1.setLayoutY(gridPane1.getLayoutY() + 0.5);
        gridPane2.setLayoutY(gridPane2.getLayoutY() + 0.5);
        if (gridPane1.getLayoutY()>=1024)
            gridPane1.setLayoutY(-1024);
        if (gridPane2.getLayoutY()>=1024)
            gridPane2.setLayoutY(-1024);
    }
}
