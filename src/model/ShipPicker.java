package model;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * ShipPicker is a VBox class use to create ShipPickerScene.
 * 
 * @author Nanthakarn Limkool
 */
public class ShipPicker extends VBox {

    /** ImageView of radio button */
    private ImageView circleImage;
    /** ImageView of ship */
    private ImageView shipImage;

    // string represent data file path
    private String CIRCLE_NOT_CHOOSEN = "view/resources/shipchooser/grey_circle.png";
    private String CIRCLE_CHOOSEN = "view/resources/shipchooser/yellow_boxTick.png";

    /** ship to choose */
    private SHIP ship;

    /** true when this ship is Choosen */
    private boolean isCircleChoosen;

    public ShipPicker(SHIP ship) {
        circleImage = new ImageView(CIRCLE_NOT_CHOOSEN);
        shipImage = new ImageView(ship.getUrlShip());
        this.ship = ship;
        isCircleChoosen = false;

        setAlignment(Pos.CENTER);
        setSpacing(20);
        getChildren().addAll(circleImage, shipImage);
    }

    /**
     * Return ship to choose
     * 
     * @return ship
     */
    public SHIP getShip() {
        return ship;
    }

    /**
     * Return true when this ship is Choosen
     * 
     * @return isCircleChoosen
     */
    public boolean getIsCircleChoosen() {
        return isCircleChoosen;
    }

    /**
     * Set isCircleChoosen
     * 
     * @param isCircleChoosen true/false
     */
    public void setIsCircleChoosen(boolean isCircleChoosen) {
        this.isCircleChoosen = isCircleChoosen;
        String imageToSet = (this.isCircleChoosen) ? CIRCLE_CHOOSEN : CIRCLE_NOT_CHOOSEN;
        circleImage.setImage(new Image(imageToSet));
    }
}