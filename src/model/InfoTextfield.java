package model;

import gameutil.PropertyManager;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
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
 * InfoTextfield is a TextField class for this game.
 * 
 * @author Nanthakarn Limkool
 */
public class InfoTextfield extends TextField {

    private final PropertyManager property = PropertyManager.getInstance();

    /**
     * TextField for get and displaying txt.
     * 
     * @param txt to display
     */
    public InfoTextfield(String txt) {
        setPrefWidth(380);
        setPrefHeight(49);
        setAlignment(Pos.CENTER);
        setPromptText(txt);
        setTextfieldFont();

        BackgroundImage bg = new BackgroundImage(new Image(property.getproperty("model.textfield_background"), 380,
                49, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        setBackground(new Background(bg));
    }

    /** Set Font. */
    private void setTextfieldFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(new File(property.getproperty("model.font"))), 23));
        } catch (FileNotFoundException e) {
            setFont(Font.font(("Verdana"), 23));
        }
    }
}
