package model;

public class ModelPlayer {

    private String name;
    private int score;

    public ModelPlayer(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

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
}
