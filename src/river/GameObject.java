package river;

import java.awt.*;

public class GameObject {

    protected String name;
    protected Location location;
    protected Color color;

    public GameObject() {

    }

    public GameObject(String label, Color color) {
        this.name = label;
        this.location = Location.START;
        this.color = color;
    }


    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location loc) {
        this.location = loc;
    }

    public Color getColor() {
        return color;
    }

}
