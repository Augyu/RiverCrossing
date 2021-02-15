package river;

import java.awt.*;

public interface GameEngine {
    String getItemLabel(Item id);

    Location getItemLocation(Item id);

    Color getItemColor(Item id);

    Location getBoatLocation();

    void loadBoat(Item id);

    void unloadBoat(Item id);

    void rowBoat();

    boolean gameIsWon();

    boolean gameIsLost();

    void resetGame();
}
