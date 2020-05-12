package model;

import gameutil.PropertyManager;
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

    /** ImageView of radio button. */
    private final ImageView circleImage;
    /** ImageView of ship. */
    private final ImageView shipImage;

    private final PropertyManager property = PropertyManager.getInstance();

    /** ship to choose. */
    private final SHIP ship;

    /** true when this ship is Choosen. */
    private boolean isCircleChoosen;

    public ShipPicker(SHIP ship) {
        circleImage = new ImageView(property.getproperty("model.circle_not_choosen"));
        shipImage = new ImageView(ship.getUrlShip());
        this.ship = ship;
        isCircleChoosen = false;

        setAlignment(Pos.CENTER);
        setSpacing(20);
        getChildren().addAll(circleImage, shipImage);
    }

    /**
     * Return ship to choose.
     * 
     * @return ship
     */
    public SHIP getShip() {
        return ship;
    }

    /**
     * Set isCircleChoosen.
     * 
     * @param isCircleChoosen true/false
     */
    public void setIsCircleChoosen(boolean isCircleChoosen) {
        this.isCircleChoosen = isCircleChoosen;
        String imageToSet = (this.isCircleChoosen) ? property.getproperty("model.circle_choosen") : property.getproperty("model.circle_not_choosen");
        circleImage.setImage(new Image(imageToSet));
    }
}