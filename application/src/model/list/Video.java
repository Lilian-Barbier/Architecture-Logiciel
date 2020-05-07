package model.list;

public class Video extends Media {

    // ATTRIBUTS

    private String resolution;

    // CONSTRUCTEUR

    public Video(int duration, String name, String resolution) {
        super(duration, name);
        if (resolution == null) {
            throw new AssertionError("Paramètre invalide Video constructeur");
        }
        this.resolution = resolution;
    }

    public Video(String path) {
    	super(path);
	}
    
    // METHODES

	public String getResolution() {
        return resolution;
    }

    // COMMANDES

    public void setResolution(String resolution) {
        if (resolution == null) {
            throw new AssertionError("Paramètre invalide Video setResolution");
        }
        this.resolution = resolution;
    }
    
	@Override
	public String getInfos() {
        return " Video duration : " + getDuration() +
                "; Video name : " + getName() +
                "; Video Artist : " + getResolution();
    }
}
