package model.list;

public class Audio extends List {

    // ATTRIBUTS

    private String artist;

    // CONSTRUCTEUR

    public Audio(int duration, String name, String artist) {
        super(duration, name);
        if (artist == null) {
            throw new AssertionError("Paramètre invalide Audio constructeur");
        }
        this.artist = artist;
    }

    // METHODES

    public String getArtist() {
        return artist;
    }

    // COMMANDES

    public void setArtist(String artist) {
        if (artist == null) {
            throw new AssertionError("Paramètre invalide Audio setArtist");
        }
        this.artist = artist;
    }
}
