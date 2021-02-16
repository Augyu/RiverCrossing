package river;

import java.awt.*;
import java.util.HashMap;

public class RobotGameEngine implements GameEngine{

    public static final Item SMALLBOT_1 = Item.ITEM_0;
    public static final Item SMALLBOT_2 = Item.ITEM_1;
    public static final Item TALLBOT_1 = Item.ITEM_2;
    public static final Item TALLBOT_2 = Item.ITEM_3;
    private Location boatLocation;
    final private HashMap<Item, GameObject> gameObjectByItem;

    public RobotGameEngine() {
        boatLocation = Location.START;
        gameObjectByItem = new HashMap<>();
        GameObject smallBot_1 = new GameObject("S1", Color.CYAN);
        GameObject smallBot_2 = new GameObject("S2", Color.CYAN);
        GameObject tallBot_1 = new GameObject("T1", Color.MAGENTA);
        GameObject tallBot_2 = new GameObject("T2", Color.MAGENTA);

        gameObjectByItem.put(SMALLBOT_1, smallBot_1);
        gameObjectByItem.put(SMALLBOT_2, smallBot_2);
        gameObjectByItem.put(TALLBOT_1, tallBot_1);
        gameObjectByItem.put(TALLBOT_2, tallBot_2);
    }

    @Override
    public String getItemLabel(Item id) {
        return gameObjectByItem.get(id).getLabel();
    }

    @Override
    public Location getItemLocation(Item id) {
        return gameObjectByItem.get(id).getLocation();
    }

    @Override
    public Color getItemColor(Item id) {
        return gameObjectByItem.get(id).getColor();
    }

    @Override
    public Location getBoatLocation() {
        return boatLocation;
    }

    @Override
    public void loadBoat(Item id) {
        GameObject object = gameObjectByItem.get(id);
        if (object.getLocation() == boatLocation) {
            if ((id == Item.ITEM_2 || id == Item.ITEM_3) && getBoatCapacity() == 0) {
                object.setLocation(Location.BOAT);
            } else if ((id == Item.ITEM_0 || id == Item.ITEM_1) && getBoatCapacity() < 2){
                object.setLocation(Location.BOAT);
            }
        }
    }

    @Override
    public void unloadBoat(Item id) {
        GameObject object = gameObjectByItem.get(id);
        if (object.getLocation() == Location.BOAT) {
            object.setLocation(boatLocation);
        }
    }

    @Override
    public void rowBoat() {
        assert (boatLocation != Location.BOAT);
        if (boatLocation == Location.START) {
            boatLocation = Location.FINISH;
        } else {
            boatLocation = Location.START;
        }
    }

    @Override
    public boolean gameIsWon() {
        for (GameObject object : gameObjectByItem.values()) {
            if (object.getLocation() != Location.FINISH) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean gameIsLost() {
        return false;
    }

    @Override
    public void resetGame() {
        for (GameObject object : gameObjectByItem.values()) {
            object.setLocation(Location.START);
        }
        boatLocation = Location.START;
    }

    private int getBoatCapacity() {
        int capacity = 0;
        for (Item item: gameObjectByItem.keySet()) {
            if (getItemLocation(item) == Location.BOAT) {
                if (item == Item.ITEM_0 || item == Item.ITEM_1) capacity++;
                else capacity += 2;
            }
        }
        return capacity;
    }
}
