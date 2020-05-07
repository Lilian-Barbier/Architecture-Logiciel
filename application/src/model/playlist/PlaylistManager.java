package model.playlist;

import model.list.IListBuilder;
import model.list.StdListBuilder;

@SuppressWarnings("unused")
public abstract class PlaylistManager implements IPlaylistManager {

    // ATTRIBUTS
    /**
     * La playlist qui est managée
     */
    private Playlist playlist;

    /**
     * Le builder construisant la playlist
     */
    private IListBuilder builder;

    // CONSTRUCTEUR

    public PlaylistManager() {
        playlist = new Playlist();
        builder = new StdListBuilder();
    }

    public PlaylistManager(String name) {
        playlist = new Playlist(name);
        builder = new StdListBuilder();
    }

    // METHODES

    @Override
    public Playlist getPlaylist() {
        return builder.getPlaylist();
    }

    @Override
    public IListBuilder getBuilder() {
        return builder;
    }

    // COMMANDES

    @Override
    public void setPlaylist(Playlist playlist) {
        if (playlist == null) {
            throw new AssertionError("Paramètre invalide PlaylistManager setPlaylist");
        }
        this.playlist = playlist;
    }

    @Override
    public void setBuilder(IListBuilder builder) {
        if (builder == null) {
            throw new AssertionError("Paramètre invalide PlaylistManager setBuilder");
        }
        this.builder = builder;
    }
}
