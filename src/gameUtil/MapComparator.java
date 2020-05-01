package gameUtil;

import model.ModelPlayer;
import java.util.Comparator;

public class MapComparator implements Comparator<ModelPlayer> {
    @Override
    public int compare(ModelPlayer player1, ModelPlayer player2) {
        if (player1.getScore() > player2.getScore())
            return -1;
        if (player1.getScore() < player2.getScore())
            return 1;
        return 0;
    }
}