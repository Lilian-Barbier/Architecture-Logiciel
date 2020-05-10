package model.list;

public class Audio extends Media {

    // ATTRIBUTS
    /**
     * L'artiste du fichier Audio
     */
    private String artist;

    // CONSTRUCTEUR

    public Audio(String path) {
        super(path);
    }
    
    public Audio(int duration, String name, String artist) {
        super(duration, name);
        if (artist == null) {
            throw new AssertionError("Paramètre invalide Audio constructeur");
        }
        this.artist = artist;
    }

    // METHODES
    /**
     * Renvoie l'attribut artist
     * @return this.artist
     */
    public String getArtist() {
        return artist;
    }

    // COMMANDES
    /**
     * Modifie l'attribut artiste avec celui passé en paramètre
     * @param artist le nouvel artiste souhaité
     */
    public void setArtist(String artist) {
        if (artist == null) {
            throw new AssertionError("Paramètre invalide Audio setArtist");
        }
        this.artist = artist;
    }

	@Override
	public String getInfos() {
        return " Audio duration : " + getDuration() +
                "; Audio name : " + getName() +
                "; Audio Artist : " + getArtist();
    }
	
}
