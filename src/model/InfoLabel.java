package model;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class InfoLabel extends Label {
    // public??
    public final String FONT_PATH = "src/model/resources/kenvector_future_thin.ttf";

    public InfoLabel(String txt) {
        setPrefWidth(600);
        setPrefHeight(400);
        setPadding(new Insets(40,40,40,40));
        setText(txt);
        setWrapText(true);
        setLabelFont();
    }

    private void setLabelFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)),23));
        } catch (FileNotFoundException e){
            setFont(Font.font(("Verdana"),23));
        }

    }

}
