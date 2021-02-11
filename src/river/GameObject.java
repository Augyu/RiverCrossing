package river;

public class GameObject {

    protected String name;
    protected Location location;
    protected String sound;

    public GameObject() {

    }

    public GameObject(String name, String sound) {
        this.name = name;
        this.location = Location.START;
        this.sound = sound;
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

    public String getSound() {
        return sound;
    }

}
