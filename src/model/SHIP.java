package model;

public enum SHIP {
    BLUE("view/resources/shipchooser/playerShip_blue.png", "view/resources/playerLife_blue.png"),
    RED("view/resources/shipchooser/playerShip_red.png", "view/resources/playerLife_red.png"),
    ORANGE("view/resources/shipchooser/playerShip_orange.png", "view/resources/playerLife_orange.png"),
    GREEN("view/resources/shipchooser/playerShip_green.png", "view/resources/playerLife_green.png");

    private String urlShip;
    private String urlPlayerLifeShip;

    private SHIP(String urlShip, String urlPlayerLifeShip) {
        this.urlShip = urlShip;
        this.urlPlayerLifeShip = urlPlayerLifeShip;
    }

    public String getUrlShip() {
        return urlShip;
    }

    public String getUrlPlayerLifeShip() {
        return urlPlayerLifeShip;
    }
}
