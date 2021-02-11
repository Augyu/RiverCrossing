package river;

import java.util.HashMap;

public class GameEngine {

    public enum Item {
        WOLF, GOOSE, BEANS, FARMER;
    }

    private Location boatLocation;
    final private HashMap<Item, GameObject> gameObjectByItem;
    private int itemsOnBoat;

    public GameEngine() {
        boatLocation = Location.START;
        itemsOnBoat = 0;
        gameObjectByItem = new HashMap<>();
        GameObject wolf = new GameObject("Wolf", "Howl");
        GameObject goose = new GameObject("Goose", "Honk");
        GameObject beans = new GameObject("Beans", "");
        GameObject farmer = new GameObject("Farmer", "");

        gameObjectByItem.put(Item.WOLF, wolf);
        gameObjectByItem.put(Item.GOOSE, goose);
        gameObjectByItem.put(Item.BEANS, beans);
        gameObjectByItem.put(Item.FARMER, farmer);
    }

    public String getItemName(Item id) {
        return gameObjectByItem.get(id).name;
    }

    public Location getItemLocation(Item id) {
        return gameObjectByItem.get(id).getLocation();
    }

    public String getSound(Item id) {
        return gameObjectByItem.get(id).getSound();
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
        Location gooseLocation = gameObjectByItem.get(Item.GOOSE).getLocation();
        Location farmerLocation = gameObjectByItem.get(Item.FARMER).getLocation();
        Location wolfLocation = gameObjectByItem.get(Item.WOLF).getLocation();
        Location beansLocation = gameObjectByItem.get(Item.BEANS).getLocation();

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
