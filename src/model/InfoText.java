package model;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * InfoText is a Text class for this game.
 * 
 * @author Nanthakarn Limkool
 */
public class InfoText extends Text {

    public InfoText(String txt) {
        setText(txt);
        setFill(Color.rgb(255, 205, 0));
        setFont(Font.font(("Verdana"), 23));
    }
}
