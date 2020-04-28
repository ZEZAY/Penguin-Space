package model;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class InfoText extends Text {
    public InfoText(String txt) {
        setText(txt);
        setFill(Color.rgb(255,205,0));
        setFont(Font.font(("Verdana"),23));
    }
}


