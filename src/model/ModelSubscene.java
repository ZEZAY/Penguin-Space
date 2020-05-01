package model;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class ModelSubscene extends SubScene {

    private final String BACKGROUD_IMAGE = "model/resources/yellow_panel.png";
    private boolean isHidden;

    public ModelSubscene() {
        super(new AnchorPane(), 600, 400);
        prefWidth(600);
        prefHeight(400);

        BackgroundImage image = new BackgroundImage(new Image(BACKGROUD_IMAGE, 600, 400, false, true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);

        AnchorPane root2 = (AnchorPane) this.getRoot();
        root2.setBackground(new Background(image));

        isHidden = true;
        setLayoutX(1024);
        setLayoutY(180);
    }

    public void moveScene() {
        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.3), this);

        if (isHidden) {
            tt.setToX(-676);
            isHidden = false;
        } else {
            tt.setToX(0);
            isHidden = true;
        }
        tt.play();
    }

    public AnchorPane getPane() {
        return (AnchorPane) this.getRoot();
    }
}
