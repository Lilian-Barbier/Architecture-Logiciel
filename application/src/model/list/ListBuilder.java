package model.list;

public interface ListBuilder {

    // COMMANDES

    void startList();
    void stopList();
    void startAudio();
    void stopAudio();
    void startVideo();
    void stopVideo();
    void setName(String name);
    void setDuration(int duration);
}
