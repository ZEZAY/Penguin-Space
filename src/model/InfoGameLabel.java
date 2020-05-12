package model;

import gameutil.PropertyManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * InfoGameLabel is a Label class for this game.
 * 
 * @author Nanthakarn Limkool
 */
public class InfoGameLabel extends Label {

    private final PropertyManager property = PropertyManager.getInstance();

    /**
     * Label for displaying txt.
     * 
     * @param txt to display
     */
    public InfoGameLabel(String txt) {
        setPrefWidth(130);
        setPrefHeight(50);
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(10, 10, 10, 10));
        setText(txt);
        setLabelFont();

        BackgroundImage bg = new BackgroundImage(new Image(property.getproperty("model.label"), 130, 50, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        setBackground(new Background(bg));
    }

    /** Set Font. */
    private void setLabelFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(new File(property.getproperty("model.font"))), 16));
        } catch (FileNotFoundException e) {
            setFont(Font.font(("Verdana"), 16));
        }
    }
}
