package view;

import gameutil.PropertyManager;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Create new Stage (createGame()) for playing a game. 
 * And manege all work in the game.
 * 
 * @author Nanthakarn Limkool
 */
public class GameViewManager {

    private final PropertyManager property = PropertyManager.getInstance();

    /** game's Pane (AnchorPane class). */
    private AnchorPane gamePane;
    /** game's Scene. */
    private Scene gameScene;
    /** game's main Stage. */
    private Stage gameStage;
    /**
     * game's main Stage (need to hide before start new stage(gameStage)).
     */
    private Stage mainStage;

    // moving control key
    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private boolean isUpKeyPressed;
    private boolean isDownKeyPressed;
    /** Ship's angle while playing. */
    private int angle;

    /** Animation for game loop. */
    private AnimationTimer gameTimer;
    /** background Pane for game loop. */
    private GridPane gridPane1;
    /** background Pane for game loop. */
    private GridPane gridPane2;
    /** Random for items new position (for game loop). */
    private Random randomPositionGenerators;

    // game's view items
    /** game's Ship to play. */
    private ImageView playerShip;
    /** player's current score. */
    private int score;
    /** number of total player life. */
    private int playerLife;
    /** game' Score label to display current score. */
    private InfoGameLabel gameLabel;

    // game's score items
    /** game's Star to gain score. */
    private ImageView star;
    /** List of Text (words) to gain score. */
    private List<Text> wordList;
    /** game' word label to gain score. */
    private InfoLabel gameWordLabel;

    // game's item lists
    /** Array of grey meteors. */
    private ImageView[] greyMeteors;
    /** Array of brown meteors. */
    private ImageView[] brownMeteors;
    /** Array of player lifes. */
    private ImageView[] playerLifes;

    public GameViewManager() {
        initializeStage();
        createKeyListeners();
        // generate random positions
        randomPositionGenerators = new Random();
    }

    /** Initialize component of game layout. */
    private void initializeStage() {
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, Double.parseDouble(property.getproperty("game.width")), Double.parseDouble(property.getproperty("game.height")));
        gameStage = new Stage();
        gameStage.setScene(gameScene);
    }

    /** Handle events on key pressing. */
    private void createKeyListeners() {
        // KeyPressed
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
                    case ENTER:
                        checkWordLabel();
                        break;
                    case BACK_SPACE:
                        String have = gameWordLabel.getText();
                        gameWordLabel.setText(have.substring(0, have.length() - 1));
                        break;
                    case ESCAPE:
                        gameStage.close();
                        break;
                    default:
                        gameWordLabel.setText(gameWordLabel.getText() + code.getChar());
                        break;
                }
            }
        });
        // KeyReleased
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

    /**
     * Hide main stage. 
     * Create new Stage for playing a game. 
     * Create game items & Run animations
     * 
     * @param mainStage  from ViewManager
     * @param chosenShip Ship to play
     */
    public void createGame(Stage mainStage, SHIP chosenShip) {
        this.mainStage = mainStage;
        this.mainStage.hide();
        // create items
        createBackground();
        createGameWordLabel();
        createPlayerShip(chosenShip);
        createGameElements(chosenShip);
        // run animations
        createGameLoop();
        gameStage.show();
    }

    // Background
    /** Create Background. */
    private void createBackground() {
        gridPane1 = new GridPane();
        gridPane2 = new GridPane();
        for (int i = 0; i < 12; i++) {
            ImageView bg1 = new ImageView(property.getproperty("game.background"));
            ImageView bg2 = new ImageView(property.getproperty("game.background"));
            GridPane.setConstraints(bg1, i % 3, i / 3);
            GridPane.setConstraints(bg2, i % 3, i / 3);
            gridPane1.getChildren().add(bg1);
            gridPane2.getChildren().add(bg2);
        }
        gridPane1.setLayoutY(-1024);
        gamePane.getChildren().addAll(gridPane1, gridPane2);
    }

    // WordLabel
    /** Create GameWordLabel. */
    private void createGameWordLabel() {
        gameWordLabel = new InfoLabel("");
        gameWordLabel.setLayoutX(25);
        gameWordLabel.setLayoutY(90);
        gamePane.getChildren().add(gameWordLabel);
    }

    /**
     * Check word in GameWordLabel that it in the wordList or not. If true then get
     * some score, otherwise clear the gameWordLabel.
     */
    private void checkWordLabel() {
        String word = gameWordLabel.getText();
        // clear gameWordLabel
        gameWordLabel.setText("");
        for (Text text : wordList) {
            if (text.getText().equalsIgnoreCase(word)) {
                // remove correct word
                wordList.remove(text); // from list
                gamePane.getChildren().remove(text); // from game display
                // update score
                score += word.length();
                gameLabel.setText("POINT: " + score);
                break;
            }
        }
    }

    // PlayerShip
    /**
     * Create a ship.
     * 
     * @param chosenShip Ship to play
     */
    private void createPlayerShip(SHIP chosenShip) {
        playerShip = new ImageView(chosenShip.getUrlShip());
        playerShip.setLayoutX(290);
        playerShip.setLayoutY(Double.parseDouble(property.getproperty("game.height")) - 90);
        gamePane.getChildren().add(playerShip);
    }

    // GameElements
    /**
     * Create player's lifes, current score label, a star, word list, and meteors.
     * 
     * @param chosenShip Ship chosen to play
     */
    private void createGameElements(SHIP chosenShip) {
        // create player's lifes
        playerLife = 2;
        playerLifes = new ImageView[3];
        for (int i = 0; i < playerLifes.length; i++) {
            playerLifes[i] = new ImageView(chosenShip.getUrlPlayerLifeShip());
            playerLifes[i].setLayoutX(455 + (i * 50));
            playerLifes[i].setLayoutY(80);
            gamePane.getChildren().add(playerLifes[i]);
        }

        // create current score label
        gameLabel = new InfoGameLabel("POINTS: ");
        gameLabel.setLayoutX(460);
        gameLabel.setLayoutY(20);
        gamePane.getChildren().add(gameLabel);

        // create a star
        star = new ImageView(property.getproperty("game.star"));
        setGameElementNewPosition(star);
        gamePane.getChildren().add(star);

        // create word list
        generateRandomWords(10);
        for (Text word : wordList) {
            setWordNewPosition(word);
            gamePane.getChildren().add(word);
        }

        // create grey meteors
        greyMeteors = new ImageView[3];
        for (int i = 0; i < greyMeteors.length; i++) {
            greyMeteors[i] = new ImageView(property.getproperty("game.meteor_grey"));
            setGameElementNewPosition(greyMeteors[i]);
            gamePane.getChildren().add(greyMeteors[i]);
        }

        // create brown meteors
        brownMeteors = new ImageView[3];
        for (int i = 0; i < brownMeteors.length; i++) {
            brownMeteors[i] = new ImageView(property.getproperty("game.meteor_brown"));
            setGameElementNewPosition(brownMeteors[i]);
            gamePane.getChildren().add(brownMeteors[i]);
        }
    }

    /**
     * Generate some random text length between 3 to 11.
     * 
     * @param numberOfWords want to generate
     */
    private void generateRandomWords(int numberOfWords) {
        wordList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < numberOfWords; i++) {
            char[] word = new char[random.nextInt(8) + 3];
            for (int j = 0; j < word.length; j++)
                word[j] = (char) ('a' + random.nextInt(26));
            wordList.add(new InfoText(new String(word)));
        }
    }

    // GameLoop
    /**
     * Create animation that move Background, GameElement, Ship. 
     * And check if Ship get hit by some GameElement.
     */
    private void createGameLoop() {
        // create animation
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                moveBackground();
                moveGameElement();
                moveShip();

                checkIfElementsCollide();
            }
        };
        gameTimer.start();
    }

    /** Set movement for Background. */
    private void moveBackground() {
        gridPane1.setLayoutY(gridPane1.getLayoutY() + 0.5);
        gridPane2.setLayoutY(gridPane2.getLayoutY() + 0.5);
        if (gridPane1.getLayoutY() >= 1024)
            gridPane1.setLayoutY(-1024);
        if (gridPane2.getLayoutY() >= 1024)
            gridPane2.setLayoutY(-1024);
    }

    /** Set movement for GameElement.*/
    private void moveGameElement() {
        // move star
        star.setLayoutY(star.getLayoutY() + 5);
        if (star.getLayoutY() > 900)
            setGameElementNewPosition(star);

        // move words
        for (Text word : wordList) {
            word.setLayoutY(word.getLayoutY() + 2);
            if (word.getLayoutY() > 900)
                setWordNewPosition(word);
        }

        // move meteors
        for (ImageView meteor : greyMeteors) {
            meteor.setLayoutY(meteor.getLayoutY() + 7);
            meteor.setRotate(meteor.getRotate() + 4);
            if (meteor.getLayoutY() > 900)
                setGameElementNewPosition(meteor);
        }
        for (ImageView meteor : brownMeteors) {
            meteor.setLayoutY(meteor.getLayoutY() + 7);
            meteor.setRotate(meteor.getRotate() + 4);
            if (meteor.getLayoutY() > 900)
                setGameElementNewPosition(meteor);
        }
    }

    /** Set movement for Ship. */
    private void moveShip() {
        if (isLeftKeyPressed) {
            if (angle > -30)
                angle -= 5;
            playerShip.setRotate(angle);
            if (playerShip.getLayoutX() > -20)
                playerShip.setLayoutX(playerShip.getLayoutX() - 3);
        }
        if (isRightKeyPressed) {
            if (angle < 30)
                angle += 5;
            playerShip.setRotate(angle);
            if (playerShip.getLayoutX() < 522)
                playerShip.setLayoutX(playerShip.getLayoutX() + 3);
        }
        if (!isRightKeyPressed && !isLeftKeyPressed) {
            if (angle < 0)
                angle += 5;
            if (angle > 0)
                angle -= 5;
            playerShip.setRotate(angle);
        }
        if (isUpKeyPressed) {
            if (playerShip.getLayoutY() > 0)
                playerShip.setLayoutY(playerShip.getLayoutY() - 3);
        }
        if (isDownKeyPressed) {
            if (playerShip.getLayoutY() < Double.parseDouble(property.getproperty("game.height")) - 90)
                playerShip.setLayoutY(playerShip.getLayoutY() + 3);
        }
    }

    /** Check if Ship get hit by some GameElement. */
    private void checkIfElementsCollide() {
        // check hit star
        if (Double.parseDouble(property.getproperty("game.star_radius")) + Double.parseDouble(property.getproperty(
                "game.ship_radius")) > calculateDistance(playerShip, star, 10, 8)) {
            setGameElementNewPosition(star);
            score++;
            gameLabel.setText("POINT: " + score);
        }
        // check hit grey meteor
        for (ImageView greyMeteor : greyMeteors) {
            if (Double.parseDouble(property.getproperty("game.meteor_grey_radius")) + Double.parseDouble(property.getproperty("game.ship_radius")) > calculateDistance(playerShip, greyMeteor, 18, 15)) {
                setGameElementNewPosition(greyMeteor);
                removePlayerLife();
            }
        }
        // check hit brown meteor
        for (ImageView brownMeteor : brownMeteors) {
            if (Double.parseDouble(property.getproperty("game.meteor_brown_radius")) + Double.parseDouble(property.getproperty("game.ship_radius")) > calculateDistance(playerShip, brownMeteor, 10, 8)) {
                setGameElementNewPosition(brownMeteor);
                removePlayerLife();
            }
        }
    }

    // Tools
    /**
     * Set new random position for item (GameElement) 
     * when it get out of Scene by animation.
     * 
     * @param item that want to focus
     */
    private void setGameElementNewPosition(ImageView item) {
        item.setLayoutX(randomPositionGenerators.nextInt(370));
        item.setLayoutY(-(randomPositionGenerators.nextInt(3200) + 600));
    }

    /**
     * Set new random position for txt (word in wordList) 
     * when it get out of Scene by animation.
     * 
     * @param txt that want to focus
     */
    private void setWordNewPosition(Text txt) {
        txt.setLayoutX(randomPositionGenerators.nextInt(370));
        txt.setLayoutY(-(randomPositionGenerators.nextInt(3200) + 600));
    }

    /**
     * Remove a player's life from playerLifes and Scene. 
     * Check if playerLifes is empty, get out of the game.
     */
    private void removePlayerLife() {
        gamePane.getChildren().remove(playerLifes[playerLife]);
        playerLife--;
        if (playerLife < 0) {
            gameStage.close();
            gameTimer.stop();
            mainStage.show();
        }
    }

    /**
     * Calculate distance between ship and item (GameElement).
     * 
     * @param ship  player's ship
     * @param item  (GameElement) that want to focus
     * @param needX Error x-axis radius of item
     * @param needY Error y-axis radius of item
     * @return distance between ship and item
     */
    private double calculateDistance(ImageView ship, ImageView item, double needX, double needY) {
        double x1 = ship.getLayoutX() + 40;
        double x2 = item.getLayoutX() + needX;
        double y1 = ship.getLayoutY() + 30;
        double y2 = item.getLayoutY() + needY;
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    /**
     * Return score.
     * 
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * Set Score to input score.
     * 
     * @param score player's current score
     */
    public void setScore(int score) {
        this.score = score;
    }
}
