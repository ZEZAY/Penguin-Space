package model;

/**
 * ModelPlayer is Player infomation use to ranking player score
 * 
 * @author Nanthakarn Limkool
 */
public class ModelPlayer implements Comparable<ModelPlayer> {

    /** Player's name */
    private String name;
    /** Player's best score */
    private int score;

    public ModelPlayer(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Reurn Player's name
     * 
     * @return Player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Return Player's best score
     * 
     * @return Player's best score
     */
    public int getScore() {
        return score;
    }

    /**
     * Set new Player's best score
     * 
     * @param score that wnat to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object anotherPlayer) {
        if (this == anotherPlayer)
            return true;
        if (anotherPlayer == null || getClass() != anotherPlayer.getClass())
            return false;
        ModelPlayer thatPlayer = (ModelPlayer) anotherPlayer;
        return name.equalsIgnoreCase(thatPlayer.name);
    }

    public String toString() {
        return getName() + " " + getScore();
    }

    @Override
    public int compareTo(ModelPlayer anotherPlayer) {
        return getName().compareTo(anotherPlayer.getName());
    }
}
