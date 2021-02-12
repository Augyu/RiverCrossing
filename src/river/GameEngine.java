package river;

import java.awt.*;
import java.util.HashMap;

public class GameEngine {

    public static final Item WOLF = Item.ITEM_2;
    public static final Item GOOSE = Item.ITEM_1;
    public static final Item BEANS = Item.ITEM_0;
    public static final Item FARMER = Item.ITEM_3;
    private Location boatLocation;
    final private HashMap<Item, GameObject> gameObjectByItem;
    private int itemsOnBoat;

//    private static final Item BEANS = Item.ITEM_0;
//    private static final Item GOOSE = Item.ITEM_1;
//    private static final Item WOLF = Item.ITEM_2;
//    private static final Item FARMER = Item.ITEM_3;

    public GameEngine() {
        boatLocation = Location.START;
        itemsOnBoat = 0;
        gameObjectByItem = new HashMap<>();
        GameObject wolf = new GameObject("Wolf", Color.CYAN);
        GameObject goose = new GameObject("Goose", Color.CYAN);
        GameObject beans = new GameObject("Beans", Color.CYAN);
        GameObject farmer = new GameObject("Farmer", Color.MAGENTA);

        gameObjectByItem.put(WOLF, wolf);
        gameObjectByItem.put(GOOSE, goose);
        gameObjectByItem.put(BEANS, beans);
        gameObjectByItem.put(FARMER, farmer);
    }

    public String getItemName(Item id) {
        return gameObjectByItem.get(id).name;
    }

    public Location getItemLocation(Item id) {
        return gameObjectByItem.get(id).getLocation();
    }

    public Color getItemSound(Item id) {
        return gameObjectByItem.get(id).getColor();
    }

    public Location getBoatLocation() {
        return boatLocation;
    }

    public void loadBoat(Item id) {
        GameObject object = gameObjectByItem.get(id);
        if (itemsOnBoat < 2 && object.getLocation() == boatLocation) {
            object.setLocation(Location.BOAT);
            itemsOnBoat++;
        }
    }

    public void unloadBoat(Item id) {
        GameObject object = gameObjectByItem.get(id);
        if (object.getLocation() == Location.BOAT) {
            object.setLocation(boatLocation);
            itemsOnBoat--;
        }
    }

    public void rowBoat() {
        assert (boatLocation != Location.BOAT);
        if (boatLocation == Location.START) {
            boatLocation = Location.FINISH;
        } else {
            boatLocation = Location.START;
        }
    }

    public boolean gameIsWon() {
        for (GameObject object : gameObjectByItem.values()) {
            if (object.getLocation() != Location.FINISH) {
                return false;
            }
        }
        return true;
    }

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

    public void resetGame() {
        for (GameObject object : gameObjectByItem.values()) {
            object.setLocation(Location.START);
        }
        boatLocation = Location.START;
    }
}
