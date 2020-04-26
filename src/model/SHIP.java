package model;

public enum SHIP {
    BLUE("view/resources/shipchooser/playerShip_blue.png"),
    RED("view/resources/shipchooser/playerShip_red.png"),
    ORANGE("view/resources/shipchooser/playerShip_orange.png"),
    GREEN("view/resources/shipchooser/playerShip_green.png");

    private String urlShip;

    private SHIP(String urlShip) {
        this.urlShip = urlShip;
    }

    public String getUrlShip() {
        return urlShip;
    }
}
