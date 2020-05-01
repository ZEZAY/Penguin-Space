package view;

import gameUtil.LoadDataFile;
import gameUtil.MapComparator;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import model.*;

import java.util.*;

public class ViewManeger {

    private static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;

    private final static int MENU_BUTTONS_START_X = 96;
    private final static int MENU_BUTTONS_START_Y = 150;
    List<ModelButton> menuButtons;

    private ModelSubscene startSubScene;
    private ModelSubscene scoresSubScene;
    private ModelSubscene helpSubScene;
    private ModelSubscene creditsSubScene;
    private ModelSubscene sceneToHide;

    GameViewManeger gm = new GameViewManeger();
    LoadDataFile loadDataFile = new LoadDataFile();

    List<ShipPicker> ships;
    private SHIP choosenShip;

    Map<ModelPlayer, String> scoreBoard = new TreeMap<>(new MapComparator());
    private String playerName;
    private InfoTextfield playerNameField;

    private VBox vRankingBox = new VBox();

    public ViewManeger() {
        menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);

        createSubscene();
        createMenuButtons();
        createBackground();
        createLogo();
    }

    public Stage getMainStage() {
        return mainStage;
    }

    private void showSubScene(ModelSubscene subScene) {
        if (sceneToHide != null) {
            sceneToHide.moveScene();
        }
        subScene.moveScene();
        sceneToHide = subScene;
    }

    private void createSubscene() {
        createShipChooserScene(); // startSubScene
        createPlayerRankingScene(); // scoresSubScene
        createHelpScene(); // helpSubScene
        createCreditsScene(); // creditsSubScene

        mainPane.getChildren().addAll(startSubScene, scoresSubScene, helpSubScene, creditsSubScene);
    }

    private void createShipChooserScene() {
        startSubScene = new ModelSubscene();

        InfoLabel shipLabel = new InfoLabel("CHOOSE YOUR SHIP");
        shipLabel.setLayoutX(110);
        shipLabel.setLayoutY(25);
        startSubScene.getPane().getChildren().add(shipLabel);

        playerNameField = new InfoTextfield("Enter your name");
        playerNameField.setLayoutX(10);
        playerNameField.setLayoutY(300);
        startSubScene.getPane().getChildren().add(playerNameField);

        startSubScene.getPane().getChildren().add(createShipToChooser());
        startSubScene.getPane().getChildren().add(createButtonToPlay());
    }

    private HBox createShipToChooser() {
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        ships = new ArrayList<>();
        for (SHIP ship : SHIP.values()) {
            ShipPicker shipToPicker = new ShipPicker(ship);
            ships.add(shipToPicker);
            hBox.getChildren().add(shipToPicker);

            shipToPicker.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    for (ShipPicker sp : ships)
                        sp.setIsCircleChoosen(false);
                    shipToPicker.setIsCircleChoosen(true);
                    choosenShip = shipToPicker.getShip();
                }
            });
        }
        hBox.setLayoutX(300 - (118 * 2));
        hBox.setLayoutY(100);
        return hBox;
    }

    private ModelButton createButtonToPlay() {
        ModelButton btn = new ModelButton("PLAY");
        btn.setLayoutX(350);
        btn.setLayoutY(300);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                playerName = playerNameField.getText().trim();
                if (playerName.isEmpty())
                    playerName = "unknow";
                if (choosenShip != null) {
                    updateRanking();
                    gm.createGame(mainStage, choosenShip);
                }
            }
        });
        return btn;
    }

    private void createPlayerRankingScene() {
        scoresSubScene = new ModelSubscene();
        InfoLabel rankLabel = new InfoLabel("RANKING");
        rankLabel.setLayoutX(110);
        rankLabel.setLayoutY(25);
        scoresSubScene.getPane().getChildren().add(rankLabel);
        vRankingBox.setLayoutX(110);
        vRankingBox.setLayoutY(100);
        scoresSubScene.getPane().getChildren().add(vRankingBox);
        scoreBoard.putAll(loadDataFile.loadMap());
    }

    private void updateRanking() {
        if (gm.getScore() > 0) {
            int scoreToSet = gm.getScore();
            ModelPlayer player = new ModelPlayer(playerName, scoreToSet);
            for (Map.Entry entey : scoreBoard.entrySet()) {
                if (player.equals(entey.getKey())) {
                    scoreBoard.remove(entey.getKey());
                    player.setScore(Math.max(scoreToSet, ((ModelPlayer) entey.getKey()).getScore()));
                    break;
                }
            }
            scoreBoard.put(player, player.getName());
            gm.setScore(0);
        }
        vRankingBox.getChildren().clear();
        int n = 0;
        for (Map.Entry entey : scoreBoard.entrySet()) {
            n++;
            InfoLabel rankLabel = new InfoLabel(((ModelPlayer) entey.getKey()).getScore() + " " + entey.getValue());
            rankLabel.setLayoutX(110);
            rankLabel.setLayoutY(50 * n);
            vRankingBox.getChildren().add(rankLabel);
        }
        loadDataFile.updateDataFile(scoreBoard);
    }

    private void createCreditsScene() {
        creditsSubScene = new ModelSubscene();
        InfoLabel creditsLabel = new InfoLabel("CREDITS");
        creditsLabel.setLayoutX(110);
        creditsLabel.setLayoutY(25);
        creditsSubScene.getPane().getChildren().add(creditsLabel);

        ImageView penguin = new ImageView("view/resources/penguin.png");
        penguin.setLayoutX(0);
        penguin.setLayoutY(70);
        creditsSubScene.getPane().getChildren().add(penguin);

        ImageView creditsTag = new ImageView("view/resources/credits_tag.png");
        creditsTag.setLayoutX(300);
        creditsTag.setLayoutY(80);
        creditsSubScene.getPane().getChildren().add(creditsTag);

        AnimationTimer penguinRun = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (penguin.getLayoutX() > 800) {
                    penguin.setLayoutX(-300);
                }
                if (creditsTag.getLayoutX() > 600) {
                    creditsTag.setLayoutX(-500);
                }
                penguin.setLayoutX(penguin.getLayoutX() + 3);
                penguin.setRotate(penguin.getRotate() + 2);
                creditsTag.setLayoutX(creditsTag.getLayoutX() + 3);
            }
        };
        penguinRun.start();
    }

    private void createHelpScene() {
        helpSubScene = new ModelSubscene();
        InfoLabel helpLabel = new InfoLabel("HELP");
        helpLabel.setLayoutX(110);
        helpLabel.setLayoutY(25);
        helpSubScene.getPane().getChildren().add(helpLabel);

        ImageView helpImg = new ImageView("view/resources/how_to_play.png");
        helpImg.setLayoutX(0);
        helpImg.setLayoutY(0);
        helpSubScene.getPane().getChildren().add(helpImg);

        ModelButton buttonToSlide = new ModelButton("NEXT");
        buttonToSlide.setLayoutX(50);
        buttonToSlide.setLayoutY(350);
        buttonToSlide.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (helpImg.getLayoutX() > -233) {
                    helpImg.setLayoutX(helpImg.getLayoutX() - 233);
                } else {
                    helpImg.setLayoutX(0);
                }
            }
        });
        helpSubScene.getPane().getChildren().add(buttonToSlide);
    }

    private void addMenuButton(ModelButton btn) {
        btn.setLayoutX(MENU_BUTTONS_START_X);
        btn.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size() * 100);
        menuButtons.add(btn);
        mainPane.getChildren().add(btn);
    }

    private void createMenuButton(String txt) {
        ModelButton btn = new ModelButton(txt);
        addMenuButton(btn);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
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
            }
        });
    }

    private void createMenuButtons() {
        createMenuButton("START");
        createMenuButton("SCORES");
        createMenuButton("HELP");
        createMenuButton("CREDITS");
        createMenuButton("EXIT");
    }

    private void createBackground() {
        Image BackgroundImage = new Image("view/resources/background_black.png", 256, 256, false, true);
        BackgroundImage Background = new BackgroundImage(BackgroundImage, BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(Background));
    }

    private void createLogo() {
        ImageView logo = new ImageView("view/resources/logo.png");
        logo.setLayoutX(400);
        logo.setLayoutY(50);

        logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                logo.setEffect(new DropShadow());
            }
        });

        logo.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                logo.setEffect(null);
            }
        });

        mainPane.getChildren().add(logo);
    }
}