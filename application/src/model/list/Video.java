package model.list;

public class Video implements List {

    // ATTRIBUTS

    private int duration;
    private String name;
    private String resolution;

    // CONSTRUCTEUR

    public Video(int duration, String name, String resolution) {
        if (duration < 0 || name == null || resolution == null) {
            throw new AssertionError("Paramètre invalide Video constructeur");
        }
        this.duration = duration;
        this.name = name;
        this.resolution = resolution;
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

    public String getResolution() {
        return resolution;
    }

    // COMMANDES

    @Override
    public void setDuration(int duration) {
        if (duration < 0) {
            throw new AssertionError("Paramètre invalide Video setDuration");
        }
        this.duration = duration;
    }

    @Override
    public void setName(String name) {
        if (name == null) {
            throw new AssertionError("Paramètre invalide Video setName");
        }
        this.name = name;
    }

    public void setResolution(String resolution) {
        if (resolution == null) {
            throw new AssertionError("Paramètre invalide Video setResolution");
        }
        this.resolution = resolution;
    }
}
