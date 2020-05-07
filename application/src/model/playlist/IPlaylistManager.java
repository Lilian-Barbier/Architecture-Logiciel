package model.playlist;

import model.list.ListBuilder;

import java.io.File;

public interface IPlaylistManager {

    // METHODES
    Playlist getPlaylist();
    ListBuilder getBuilder();

    // COMMANDES
    void load(File f);
    void save();
    //void setPlaylist(Playlist playlist);
    void setBuilder(ListBuilder builder);
}
