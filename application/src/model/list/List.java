package model.list;

public class List implements IList {

    // ATTRIBUTS

    private int duration;
    private String name;
    private String path;

    // CONSTRUCTEUR

    public List(int duration, String name) {
        if (duration < 0 || name == null) {
            throw new AssertionError("Paramètre invalide List constructeur");
        }
        this.duration = duration;
        this.name = name;
    }

    public List() {
        this.duration = 0;
        this.name = "new list";
    }

    // METHODES

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPath() { return null; }

    // COMMANDES

    @Override
    public void setDuration(int duration) {
        if (duration < 0) {
            throw new AssertionError("Paramètre invalide List setDuration");
        }
        this.duration = duration;
    }

    @Override
    public void setName(String name) {
        if (name == null) {
            throw new AssertionError("Paramètre invalide List setName");
        }
        this.name = name;
    }

    @Override
    public void setPath(String path) {
        if (path == null) {
            throw new AssertionError("Paramètre invalide List setPath");
        }
        this.path = path;
    }
}