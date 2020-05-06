package model.list;

import model.playlist.Playlist;

public interface ListBuilder {

    // COMMANDES

    void startSublist();
    void stopSublist();
    
    void startAudio();
    void stopAudio();
    
    void startVideo();
    void stopVideo();
    
    void addName(String name);
    void addPath(String path);
    
    public Playlist getPlaylist();
    
}
