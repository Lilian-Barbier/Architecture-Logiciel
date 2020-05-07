package model.playlist;

import model.list.IListBuilder;
import model.list.StdListBuilder;

@SuppressWarnings("unused")
public abstract class PlaylistManager implements IPlaylistManager {

    // ATTRIBUTS

	/**
	 * Le builder construisant la playlist
	 */
	private IListBuilder builder;

    // CONSTRUCTEUR

    public PlaylistManager(String absolutePath) {
        builder = new StdListBuilder(absolutePath);
    }

    public PlaylistManager(String name, String absolutePath) {
        builder = new StdListBuilder(absolutePath);
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

    /*@Override
    public void setPlaylist(Playlist playlist) {
        if (playlist == null) {
            throw new AssertionError("Paramètre invalide PlaylistManager setPlaylist");
        }
        builder. TODO RAJOUTER UN SETPLAYLIS
    }*/

    @Override
    public void setBuilder(IListBuilder builder) {
        if (builder == null) {
            throw new AssertionError("Paramètre invalide PlaylistManager setBuilder");
        }
        this.builder = builder;
    }
}
