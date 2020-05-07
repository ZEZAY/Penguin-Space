package model;

/**
 * Ship that player can choose
 * 
 * @author Nanthakarn Limkool
 */
public enum SHIP {

    /** blue color ship */
    BLUE("view/resources/shipchooser/playerShip_blue.png", "view/resources/playerLife_blue.png"),
    /** red color ship */
    RED("view/resources/shipchooser/playerShip_red.png", "view/resources/playerLife_red.png"),
    /** orange color ship */
    ORANGE("view/resources/shipchooser/playerShip_orange.png", "view/resources/playerLife_orange.png"),
    /** green color ship */
    GREEN("view/resources/shipchooser/playerShip_green.png", "view/resources/playerLife_green.png");

    /** url for a playing Ship (bigger) */
    private String urlShip;
    /** url for a player life (smaller) */
    private String urlPlayerLifeShip;

    SHIP(String urlShip, String urlPlayerLifeShip) {
        this.urlShip = urlShip;
        this.urlPlayerLifeShip = urlPlayerLifeShip;
    }

    /**
     * Return playing ship url
     * 
     * @return urlShip
     */
    public String getUrlShip() {
        return urlShip;
    }

    /**
     * Return player life url
     * 
     * @return urlPlayerLifeShip
     */
    public String getUrlPlayerLifeShip() {
        return urlPlayerLifeShip;
    }
}
