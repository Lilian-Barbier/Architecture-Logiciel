package model.list;

public class Audio implements List {

    // ATTRIBUTS

    private int duration;
    private String name;
    private String artist;

    // CONSTRUCTEUR

    public Audio(int duration, String name, String artist) {
        if (duration < 0 || name == null || artist == null) {
            throw new AssertionError("Paramètre invalide Audio constructeur");
        }
        this.duration = duration;
        this.name = name;
        this.artist = artist;
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

    public String getArtist() {
        return artist;
    }

    // COMMANDES

    @Override
    public void setDuration(int duration) {
        if (duration < 0) {
            throw new AssertionError("Paramètre invalide Audio setDuration");
        }
        this.duration = duration;
    }

    @Override
    public void setName(String name) {
        if (name == null) {
            throw new AssertionError("Paramètre invalide Audio setName");
        }
        this.name = name;
    }

    public void setArtist(String artist) {
        if (artist == null) {
            throw new AssertionError("Paramètre invalide Audio setArtist");
        }
        this.artist = artist;
    }
}
