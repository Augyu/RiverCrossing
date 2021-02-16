package river;

import java.awt.*;

public class GameObject {

    protected String label;
    protected Location location;
    protected Color color;

    public GameObject(String label, Color color) {
        this.label = label;
        this.location = Location.START;
        this.color = color;
    }

    public String getLabel() {
        return label;
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
