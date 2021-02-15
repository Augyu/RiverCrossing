package river;

import java.awt.*;
import java.util.HashMap;

public class FarmerGameEngine implements GameEngine {

    public static final Item WOLF = Item.ITEM_2;
    public static final Item GOOSE = Item.ITEM_1;
    public static final Item BEANS = Item.ITEM_0;
    public static final Item FARMER = Item.ITEM_3;
    private Location boatLocation;
    final private HashMap<Item, GameObject> gameObjectByItem;

    public FarmerGameEngine() {
        boatLocation = Location.START;
        gameObjectByItem = new HashMap<>();
        GameObject wolf = new GameObject("W", Color.CYAN);
        GameObject goose = new GameObject("G", Color.CYAN);
        GameObject beans = new GameObject("B", Color.CYAN);
        GameObject farmer = new GameObject("", Color.MAGENTA);

        gameObjectByItem.put(WOLF, wolf);
        gameObjectByItem.put(GOOSE, goose);
        gameObjectByItem.put(BEANS, beans);
        gameObjectByItem.put(FARMER, farmer);
    }

    @Override
    public String getItemLabel(Item id) {
        return gameObjectByItem.get(id).name;
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
        if (getNumOfItemOnBoat() < 2 && object.getLocation() == boatLocation) {
            object.setLocation(Location.BOAT);
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
        Location gooseLocation = gameObjectByItem.get(GOOSE).getLocation();
        Location farmerLocation = gameObjectByItem.get(FARMER).getLocation();
        Location wolfLocation = gameObjectByItem.get(WOLF).getLocation();
        Location beansLocation = gameObjectByItem.get(BEANS).getLocation();

        if (gooseLocation == Location.BOAT ||
                gooseLocation == farmerLocation ||
                gooseLocation == boatLocation) {
            return false;
        }
        if (gooseLocation == wolfLocation || gooseLocation == beansLocation) {
            return true;
        }
        return false;
    }

    @Override
    public void resetGame() {
        for (GameObject object : gameObjectByItem.values()) {
            object.setLocation(Location.START);
        }
        boatLocation = Location.START;
    }

    private int getNumOfItemOnBoat() {
        int numOfItem = 0;
        for (Item item: gameObjectByItem.keySet()) {
            if (getItemLocation(item) == Location.BOAT) {
                numOfItem++;
            }
        }
        return numOfItem;
    }
}
