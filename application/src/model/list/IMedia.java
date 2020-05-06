package model.list;

public interface IMedia {

    // METHODES

    int getDuration();
    String getName();
    String getPath();

    // COMMANDES

    void setDuration(int duration);
    void setName(String name);
    void setPath(String path);
}
