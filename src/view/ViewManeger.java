package view;

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

import java.util.ArrayList;
import java.util.List;

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

    List<ShipPicker> ships;
    private  SHIP choosenShip;

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

    private void showSubScene(ModelSubscene subScene) {
        if (sceneToHide != null) {
            sceneToHide.moveScene();
        }
        subScene.moveScene();
        sceneToHide = subScene;
    }

    private void createSubscene() {
        createShipChooserScene(); // startSubScene
        scoresSubScene = new ModelSubscene();
        helpSubScene = new ModelSubscene();
        creditsSubScene = new ModelSubscene();

        mainPane.getChildren().addAll(startSubScene, scoresSubScene, helpSubScene, creditsSubScene);
    }

    private void createShipChooserScene() {
        startSubScene = new ModelSubscene();
        InfoLabel shipLabel = new InfoLabel("CHOOSE YOUR SHIP");
        shipLabel.setLayoutX(110);
        shipLabel.setLayoutY(25);
        startSubScene.getPane().getChildren().add(shipLabel);
        startSubScene.getPane().getChildren().add(createShipToChooser());
        startSubScene.getPane().getChildren().add(createButtonToPlay());
    }

    private HBox createShipToChooser() {
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        ships = new ArrayList<>();
        for (SHIP ship: SHIP.values()) {
            ShipPicker shipToPicker = new ShipPicker(ship);
            ships.add(shipToPicker);
            hBox.getChildren().add(shipToPicker);

            shipToPicker.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    for (ShipPicker sp: ships)
                        sp.setIsCircleChoosen(false);
                    shipToPicker.setIsCircleChoosen(true);
                    choosenShip = shipToPicker.getShip();
                }
            });
        }
        hBox.setLayoutX(300 - (118*2));
        hBox.setLayoutY(100);
        return hBox;
    }

    private ModelButton createButtonToPlay() {
        ModelButton btn = new ModelButton("PLAY");
        btn.setLayoutX(350);
        btn.setLayoutY(300);
        return btn;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    private void addMenuButton(ModelButton btn) {
        btn.setLayoutX(MENU_BUTTONS_START_X);
        btn.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size()*100);
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
                        showSubScene(scoresSubScene);
                        break;
                    case "HELP":
                        showSubScene(helpSubScene);
                        break;
                    case "CREDITS":
                        showSubScene(creditsSubScene);
                        break;
                    case "EXIT":
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
        BackgroundImage Background = new BackgroundImage(BackgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT, null);
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