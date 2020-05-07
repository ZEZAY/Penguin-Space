package view;

import gameutil.LoadDataFile;
import gameutil.MapComparator;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import model.*;

import java.util.*;

/**
 * Create new Stage (getMainStage()) for a game menu. 
 * And manege all data for the game.
 * 
 * @author Nanthakarn Limkool
 */
public class ViewManager {

    // string represent Scene size
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;
    // string represent menu notton layout
    private static final int MENU_BUTTONS_START_X = 96;
    private static final int MENU_BUTTONS_START_Y = 150;

    /** main's Pane (AnchorPane class) */
    private AnchorPane mainPane;
    /** main's Scene */
    private Scene mainScene;
    /** main's main Stage */
    private Stage mainStage;

    /** List of menu buttons */
    private List<ModelButton> menuButtons;

    // Subscene
    /** Subscene of menu "START" */
    private ModelSubscene startSubScene;
    /** Subscene of menu "SCORES" */
    private ModelSubscene scoresSubScene;
    /** Subscene of menu "HELP" */
    private ModelSubscene helpSubScene;
    /** Subscene of menu "CREDITS" */
    private ModelSubscene creditsSubScene;
    /** Subscene of last opening menu */
    private ModelSubscene sceneToHide;

    /** List of ShipPicker that player can choose */
    private List<ShipPicker> ships;
    /** player's current chosen ship */
    private SHIP chosenShip;

    /** Map of player ranking (sort by score) */
    private Map<ModelPlayer, String> scoreBoard = new TreeMap<>(new MapComparator());
    /** (VBox) Pane for score ranking */
    private VBox vRankingBox = new VBox();

    /** main's GameViewManager */
    private GameViewManager gm = new GameViewManager();
    /** main's LoadDataFile */
    private LoadDataFile loadDataFile = new LoadDataFile();

    /** Current player's name */
    private String playerName;
    /** Textfield for player's name */
    private InfoTextfield playerNameField;

    public ViewManager() {
        menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);

        createBackground();
        createLogo();
        createMenuButtons();
        createSubscenes();
    }

    // Background
    /** Create Background */
    private void createBackground() {
        Image BackgroundImage = new Image("view/resources/background_black.png", 256, 256, false, true);
        BackgroundImage Background = new BackgroundImage(BackgroundImage, BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(Background));
    }

    // Logo
    /** Create Logo */
    private void createLogo() {
        ImageView logo = new ImageView("view/resources/logo.png");
        logo.setLayoutX(400);
        logo.setLayoutY(50);
        logo.setOnMouseEntered(mouseEvent -> {
            DropShadow dropShadow = new DropShadow();
            dropShadow.setColor(Color.rgb(255, 205, 0));
            logo.setEffect(dropShadow);
        });
        logo.setOnMouseExited(mouseEvent -> logo.setEffect(null));
        mainPane.getChildren().add(logo);
    }

    // Menu Buttons
    /** Create menu all buttons */
    private void createMenuButtons() {
        createMenuButton("START");
        createMenuButton("SCORES");
        createMenuButton("HELP");
        createMenuButton("CREDITS");
        createMenuButton("EXIT");
    }

    /**
     * Create a button, set button text to "txt", 
     * and add EventHandler for the botton.
     * 
     * @param txt button's display text
     */
    private void createMenuButton(String txt) {
        ModelButton btn = new ModelButton(txt);
        addMenuButton(btn);
        btn.setOnAction(actionEvent -> {
            switch (txt) {
                case "START":
                    showSubScene(startSubScene);
                    break;
                case "SCORES":
                    updateRanking();
                    showSubScene(scoresSubScene);
                    break;
                case "HELP":
                    showSubScene(helpSubScene);
                    break;
                case "CREDITS":
                    showSubScene(creditsSubScene);
                    break;
                case "EXIT":
                    updateRanking();
                    mainStage.close();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + txt);
            }
        });
    }

    /**
     * Add a ModelButton (btn) to menuButtons and Display on the mainPane.
     * 
     * @param btn (new button) that want to add
     */
    private void addMenuButton(ModelButton btn) {
        btn.setLayoutX(MENU_BUTTONS_START_X);
        btn.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size() * 100);
        menuButtons.add(btn);
        mainPane.getChildren().add(btn);
    }

    /**
     * Show new subScene and Hide current subScene.
     * 
     * @param subScene that want to show up
     */
    private void showSubScene(ModelSubscene subScene) {
        if (sceneToHide != null)
            sceneToHide.moveScene();
        subScene.moveScene();
        sceneToHide = subScene;
    }

    // create Subscenes
    /* Create all Subscenes and Add to the mainPane (in hide position). */
    private void createSubscenes() {
        createShipChooserScene(); // startSubScene
        createPlayerRankingScene(); // scoresSubScene
        createHelpScene(); // helpSubScene
        createCreditsScene(); // creditsSubScene

        mainPane.getChildren().addAll(startSubScene, scoresSubScene, helpSubScene, creditsSubScene);
    }

    // startSubScene
    /** Create startSubScene use for player to choose a ship (to play) */
    private void createShipChooserScene() {
        startSubScene = new ModelSubscene();
        // Scene title
        InfoLabel shipLabel = new InfoLabel("CHOOSE YOUR SHIP");
        shipLabel.setLayoutX(110);
        shipLabel.setLayoutY(25);
        startSubScene.getPane().getChildren().add(shipLabel);
        // Textfield to get player's name
        playerNameField = new InfoTextfield("Enter your name");
        playerNameField.setLayoutX(10);
        playerNameField.setLayoutY(300);
        startSubScene.getPane().getChildren().add(playerNameField);
        // ShipToChooser (HBox) Pane
        startSubScene.getPane().getChildren().add(createShipToChooser());
        // "PLAY" button
        startSubScene.getPane().getChildren().add(createButtonToPlay());
    }

    /**
     * Create Pane(HBox) contain ShipPicker(which is VBox) for all ships that player
     * can choose. Add EventHandler to ShipPicker and get chosenShip (only one).
     * Return mainPane(HBox).
     * 
     * @return Pane(HBox) for display ships chooser
     */
    private HBox createShipToChooser() {
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        ships = new ArrayList<>();
        for (SHIP ship : SHIP.values()) {
            ShipPicker shipToPicker = new ShipPicker(ship);
            ships.add(shipToPicker);
            hBox.getChildren().add(shipToPicker);

            shipToPicker.setOnMouseClicked(mouseEvent -> {
                for (ShipPicker sp : ships)
                    sp.setIsCircleChoosen(false);
                shipToPicker.setIsCircleChoosen(true);
                chosenShip = shipToPicker.getShip();
            });
        }
        hBox.setLayoutX(300 - (118 * 2));
        hBox.setLayoutY(100);
        return hBox;
    }

    /**
     * Create a "PLAY" button add position x-350, y-300. 
     * Add EventHandler to start a game, when chosenShip is not null.
     * 
     * @return PLAY button
     */
    private ModelButton createButtonToPlay() {
        ModelButton btn = new ModelButton("PLAY");
        btn.setLayoutX(350);
        btn.setLayoutY(300);

        btn.setOnAction(actionEvent -> {
            playerName = playerNameField.getText().trim();
            if (playerName.isEmpty())
                playerName = "unknow";
            if (chosenShip != null) {
                updateRanking();
                gm.createGame(mainStage, chosenShip);
            }
        });
        return btn;
    }

    // scoresSubScene
    /**
     * Create scoresSubScene to display player ranking. 
     * And read player data file to initialize scoreBoard.
     */
    private void createPlayerRankingScene() {
        scoresSubScene = new ModelSubscene();
        // Scene title
        InfoLabel rankLabel = new InfoLabel("RANKING");
        rankLabel.setLayoutX(110);
        rankLabel.setLayoutY(25);
        scoresSubScene.getPane().getChildren().add(rankLabel);
        // display vRankingBox
        vRankingBox.setLayoutX(110);
        vRankingBox.setLayoutY(100);
        scoresSubScene.getPane().getChildren().add(vRankingBox);
        // initialize scoreBoard
        scoreBoard.putAll(loadDataFile.loadMap());
    }

    /** Update player ranking and player data file */
    private void updateRanking() {
        // put new player/score to scoreBoard
        if (gm.getScore() > 0) {
            int scoreToSet = gm.getScore();
            ModelPlayer player = new ModelPlayer(playerName, scoreToSet);
            for (Map.Entry<ModelPlayer, String> entey : scoreBoard.entrySet()) {
                if (player.equals(entey.getKey())) {
                    scoreBoard.remove(entey.getKey());
                    player.setScore(Math.max(scoreToSet, (entey.getKey()).getScore()));
                    break;
                }
            }
            scoreBoard.put(player, player.getName());
            gm.setScore(0);
        }
        // clear vRankingBox
        vRankingBox.getChildren().clear();
        // add new scoreBoard to vRankingBox
        int n = 0;
        for (Map.Entry<ModelPlayer, String> entey : scoreBoard.entrySet()) {
            n++;
            InfoLabel rankLabel = new InfoLabel(entey.getKey().toString());
            rankLabel.setLayoutX(110);
            rankLabel.setLayoutY(50 * n);
            vRankingBox.getChildren().add(rankLabel);
        }
        // update player data file
        loadDataFile.updateDataFile(scoreBoard);
    }

    // helpSubScene
    /** Create help scene */
    private void createHelpScene() {
        helpSubScene = new ModelSubscene();
        // Scene title
        InfoLabel helpLabel = new InfoLabel("HELP");
        helpLabel.setLayoutX(110);
        helpLabel.setLayoutY(25);
        helpSubScene.getPane().getChildren().add(helpLabel);
        // display an image
        ImageView helpImg = new ImageView("view/resources/how_to_play.png");
        helpImg.setLayoutX(0);
        helpImg.setLayoutY(0);
        helpSubScene.getPane().getChildren().add(helpImg);
        // button to slide the image
        ModelButton buttonToSlide = new ModelButton("NEXT");
        buttonToSlide.setLayoutX(50);
        buttonToSlide.setLayoutY(350);
        buttonToSlide.setOnAction(actionEvent -> {
            if (helpImg.getLayoutX() > -233) {
                helpImg.setLayoutX(helpImg.getLayoutX() - 233);
            } else {
                helpImg.setLayoutX(0);
            }
        });
        helpSubScene.getPane().getChildren().add(buttonToSlide);
    }

    // creditsSubScene
    /** Create credits scene */
    private void createCreditsScene() {
        creditsSubScene = new ModelSubscene();
        // Scene title
        InfoLabel creditsLabel = new InfoLabel("CREDITS");
        creditsLabel.setLayoutX(110);
        creditsLabel.setLayoutY(25);
        creditsSubScene.getPane().getChildren().add(creditsLabel);
        // display penguin image
        ImageView penguin = new ImageView("view/resources/penguin.png");
        penguin.setLayoutX(0);
        penguin.setLayoutY(70);
        creditsSubScene.getPane().getChildren().add(penguin);
        // display credits image
        ImageView creditsTag = new ImageView("view/resources/credits_tag.png");
        creditsTag.setLayoutX(300);
        creditsTag.setLayoutY(80);
        creditsSubScene.getPane().getChildren().add(creditsTag);
        // add animation for images
        AnimationTimer penguinRun = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (penguin.getLayoutX() > 800)
                    penguin.setLayoutX(-300);
                if (creditsTag.getLayoutX() > 600)
                    creditsTag.setLayoutX(-500);
                penguin.setLayoutX(penguin.getLayoutX() + 3);
                penguin.setRotate(penguin.getRotate() + 2);
                creditsTag.setLayoutX(creditsTag.getLayoutX() + 3);
            }
        };
        penguinRun.start();
    }

    /**
     * Return mainStage
     * 
     * @return mainStage
     */
    public Stage getMainStage() {
        return mainStage;
    }
}