package model.playlist;

import model.list.ListBuilder;
import model.list.ListBuilderStd;

public abstract class PlaylistManager implements IPlaylistManager {

    // ATTRIBUTS
    private ListBuilder builder;

    // CONSTRUCTEUR

    public PlaylistManager(String absolutePath) {
        builder = new ListBuilderStd(absolutePath);
    }

    public PlaylistManager(String name, String absolutePath) {
        builder = new ListBuilderStd(absolutePath);
    }

    // METHODES

    @Override
    public Playlist getPlaylist() {
        return builder.getPlaylist();
    }

    @Override
    public ListBuilder getBuilder() {
        return builder;
    }

    // COMMANDES

    /*@Override
    public void setPlaylist(Playlist playlist) {
        if (playlist == null) {
            throw new AssertionError("Paramètre invalide PlaylistManager setPlaylist");
        }
        builder. TODO RAJOUTER UN SETPLAYLIS
    }*/

    @Override
    public void setBuilder(ListBuilder builder) {
        if (builder == null) {
            throw new AssertionError("Paramètre invalide PlaylistManager setBuilder");
        }
        this.builder = builder;
    }
}
