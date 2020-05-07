package model.list;

public class Media implements IMedia {

    // ATTRIBUTS

    /**
     * La durée du Media
     */
    private int duration;

    /**
     * Le nom du fichier du Media
     */
    private String name;

    /**
     * Le chemin du fichier du Media
     */
    private String path;

    // CONSTRUCTEUR

    public Media(int duration, String name) {
        if (duration < 0 || name == null) {
            throw new AssertionError("Paramètre invalide Media constructeur");
        }
        this.duration = duration;
        this.name = name;
    }

    public Media(String path) {
    	this.path = path;
        this.duration = 0;
        this.name = "new list";
    }

    public Media() {
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
    public String getPath() { return path; }

    @Override
    public String getInfos() {
        return "duration : " + getDuration() + " name : " + getName() + " path : " + getPath();
    }

    // COMMANDES

    @Override
    public void setDuration(int duration) {
        if (duration < 0) {
            throw new AssertionError("Paramètre invalide Media setDuration");
        }
        this.duration = duration;
    }

    @Override
    public void setName(String name) {
        if (name == null) {
            throw new AssertionError("Paramètre invalide Media setName");
        }
        this.name = name;
    }

    @Override
    public void setPath(String path) {
        if (path == null) {
            throw new AssertionError("Paramètre invalide Media setPath");
        }
        this.path = path;
    }
}