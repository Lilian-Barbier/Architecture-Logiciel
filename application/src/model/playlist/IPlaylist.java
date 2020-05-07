package model.playlist;

import model.list.IMedia;

import java.util.List;

public interface IPlaylist {

    // METHODES
    /**
     * Renvoie l'ensemble des IMedia présent dans cette Playlist
     * @return this.playlist
     */
    List<IMedia> getPlaylist();

    /**
     * Renvoie le nom de la Playlist
     * @return this.name
     */
    String getName();

    // COMMANDES
    /**
     * Modifie l'attribut playlist avec celui passé en paramètre
     * @param playlist la nouvelle Playlist souhaitée
     */
    void setPlaylist(List<IMedia> playlist);

    /**
     * Modifie l'attribut name avec celui passé en paramètre
     * @param name la nouvelle Playlist souhaitée
     */
    void setName(String name);
}
