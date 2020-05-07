package model;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

/**
 * ModelSubscene is a SubScene class to slide this game menu.
 * 
 * @author Nanthakarn Limkool
 */
public class ModelSubscene extends SubScene {

    // string represent data file path
    private final String BACKGROUD_IMAGE = "model/resources/yellow_panel.png";

    /** true when this Subscene is Hidden from the main Scene */
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

    /** Slide this Scene to show/hide */
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

    /**
     * Return AnchorPane of this root
     * 
     * @return AnchorPane of this root
     */
    public AnchorPane getPane() {
        return (AnchorPane) this.getRoot();
    }
}
