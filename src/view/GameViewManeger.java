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
    private static int score;
    private final static String STAR_IMAGE = "view/resources/star_gold.png";

    private final int SHIP_RADIUS = 45;
    private final int STAR_RADIUS = 15;
    private final int METEOR_GREY_RADIUS = 25;
    private final int METEOR_BROWN_RADIUS = 15;

    ImageView laserR = new ImageView("view/resources/laser.png");
    ImageView laserL = new ImageView("view/resources/laser.png");
    ImageView laserB = new ImageView("view/resources/laser.png");
    ImageView laserT = new ImageView("view/resources/laser.png");

    ImageView laserRI = new ImageView("view/resources/laser.png");
    ImageView laserLI = new ImageView("view/resources/laser.png");
    ImageView laserBI = new ImageView("view/resources/laser.png");
    ImageView laserTI = new ImageView("view/resources/laser.png");

    ImageView laserR2 = new ImageView("view/resources/laser.png");
    ImageView laserL2 = new ImageView("view/resources/laser.png");
    ImageView laserB2 = new ImageView("view/resources/laser.png");
    ImageView laserT2 = new ImageView("view/resources/laser.png");

    ImageView laserRI2 = new ImageView("view/resources/laser.png");
    ImageView laserLI2 = new ImageView("view/resources/laser.png");
    ImageView laserBI2 = new ImageView("view/resources/laser.png");
    ImageView laserTI2 = new ImageView("view/resources/laser.png");

    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }

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

//        createLaser();

        createGameLoop();
        gameStage.show();
    }

    private void createGameLoop() {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                moveBackground();
                moveGameElement();

//                moveLaser();

                checkIfElementsCollide();
                moveShip();
            }
        };
        isGameRunning = true;
        gameTimer.start();
    }

    private void createGameElements(SHIP choosenShip) {
        playerLife = 2;
        star = new ImageView(STAR_IMAGE);
        setGameElementNewPosition(star);
        gamePane.getChildren().add(star);

        gameLabel = new InfoGameLabel("POINTS: ");
        gameLabel.setLayoutX(460);
        gameLabel.setLayoutY(20);
        gamePane.getChildren().add(gameLabel);

        playerLifes = new ImageView[3];
        for (int i = 0; i < playerLifes.length; i++) {
            playerLifes[i] = new ImageView(choosenShip.getUrlPlayerLifeShip());
            playerLifes[i].setLayoutX(455 + (i*50));
            playerLifes[i].setLayoutY(80);
            gamePane.getChildren().add(playerLifes[i]);
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
            if (playerShip.getLayoutY() > 0)
                playerShip.setLayoutY(playerShip.getLayoutY() - 3);
        }
        if (isDownKeyPressed) {
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

    private void checkIfElementsCollide() {
        if (STAR_RADIUS + SHIP_RADIUS > calculateDistance(playerShip, star, 10,8)) {
            setGameElementNewPosition(star);
            score++;
            gameLabel.setText("POINT: "+score);
        }

        for (int i = 0; i < greyMeteors.length ; i++) {
            if (METEOR_GREY_RADIUS + SHIP_RADIUS > calculateDistance(playerShip, greyMeteors[i], 18,15)) {
                setGameElementNewPosition(greyMeteors[i]);
                removePlayerLife();
            }
        }

        for (int i = 0; i < brownMeteors.length ; i++) {
            if (METEOR_BROWN_RADIUS + SHIP_RADIUS > calculateDistance(playerShip, brownMeteors[i], 10, 8)) {
                setGameElementNewPosition(brownMeteors[i]);
                removePlayerLife();
            }
        }
    }

    private void removePlayerLife() {
        gamePane.getChildren().remove(playerLifes[playerLife]);
        playerLife--;
        if (playerLife < 0) {
            gameStage.close();
            gameTimer.stop();
            menuStage.show();
        }
    }

    private double calculateDistance(ImageView ship, ImageView item, double needX, double needY) {
        double x1 = ship.getLayoutX() + 40;
        double x2 = item.getLayoutX() + needX;
        double y1 = ship.getLayoutY() + 30;
        double y2 = item.getLayoutY() + needY;
        return Math.sqrt(Math.pow( x1 - x2, 2) + Math.pow( y1 - y2, 2));
    }

    private void createLaser(){
        gamePane.getChildren().addAll(laserRI, laserBI, laserLI, laserTI);
        gamePane.getChildren().addAll(laserR, laserB, laserL, laserT);
        gamePane.getChildren().addAll(laserRI2, laserBI2, laserLI2, laserTI2);
        gamePane.getChildren().addAll(laserR2, laserB2, laserL2, laserT2);
    }

    private void moveLaser(){
        subMoveLaser(playerShip, SHIP_RADIUS, 40, 30, laserRI, laserBI, laserLI, laserTI);
        subMoveLaser(star, STAR_RADIUS, 10, 8, laserR, laserB, laserL, laserT);
        subMoveLaser(brownMeteors[0], METEOR_BROWN_RADIUS, 10, 8, laserRI2, laserBI2, laserLI2, laserTI2);
        subMoveLaser(greyMeteors[0], METEOR_GREY_RADIUS, 18, 15, laserR2, laserB2, laserL2, laserT2);
    }

    private void subMoveLaser(ImageView item, int raduis, double needx, double needy, ImageView lR, ImageView lB,
                              ImageView lL, ImageView lT){
        double x = item.getLayoutX() + needx;
        double y = item.getLayoutY() + needy;
        lR.setLayoutX(x + (raduis));
        lR.setLayoutY(y);
        lL.setLayoutX(x - (raduis));
        lL.setLayoutY(y);
        lB.setLayoutY(y + (raduis));
        lB.setLayoutX(x);
        lT.setLayoutY(y - (raduis));
        lT.setLayoutX(x);
    }
}
