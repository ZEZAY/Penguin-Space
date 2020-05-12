package model;

import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * ModelButton is a Button class for this game.
 * 
 * @author Nanthakarn Limkool
 */
public class ModelButton extends Button {

    // string represent data file path
    private static final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url"
            + "('model/resources/yellow_button_pressed.png')";
    final static String BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background-image: url"
            + "('model/resources/yellow_button.png')";

    /**
     * Button.
     * 
     * @param txt to display on Button
     */
    public ModelButton(String txt) {
        setText(txt);
        setButtonFont();

        setPrefWidth(190);
        setPrefHeight(49);
        setStyle(BUTTON_FREE_STYLE);

        buttonListeners();
    }

    /** Set Font. */
    private void setButtonFont() {
        try {
            setFont(Font.loadFont(new FileInputStream("src/model/resources/kenvector_future_thin.ttf"), 23));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana, 23"));
        }

    }

    /** Set button style when pressed. */
    private void setButtonStylePressed() {
        setStyle(BUTTON_PRESSED_STYLE);
        setPrefHeight(45);
        setLayoutY(getLayoutY() + 4);
    }

    /** Set button style when free. */
    private void setButtonStyleFree() {
        setStyle(BUTTON_FREE_STYLE);
        setPrefHeight(49);
        setLayoutY(getLayoutY() - 4);
    }

    /** Set button listeners. */
    private void buttonListeners() {
        // set on Pressed
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonStylePressed();
                }
            }
        });
        // set on Released
        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonStyleFree();
                }
            }
        });
        // set on Hover
        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setEffect(new DropShadow());
            }
        });
        // set on not Hover
        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setEffect(null);
            }
        });
    }
}
