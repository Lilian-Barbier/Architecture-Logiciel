package model.list;

@SuppressWarnings("unused")
public class Video extends Media {

    // ATTRIBUTS
    /**
     * La résolution du fichier Video
     */
    private String resolution;

    // CONSTRUCTEUR

    public Video(String path) {
        super(path);
    }

    public Video(int duration, String name, String resolution) {
        super(duration, name);
        if (resolution == null) {
            throw new AssertionError("Paramètre invalide Video constructeur");
        }
        this.resolution = resolution;
    }

    // METHODES
    /**
     * Renvoie l'attribut resolution
     * @return this.resolution
     */
	public String getResolution() {
        return resolution;
    }

    // COMMANDES
    /**
     * Modifie l'attributrésolution avec celui passé en paramètre
     * @param resolution le nouvel artiste souhaité
     */
    public void setResolution(String resolution) {
        if (resolution == null) {
            throw new AssertionError("Paramètre invalide Video setResolution");
        }
        this.resolution = resolution;
    }
}
