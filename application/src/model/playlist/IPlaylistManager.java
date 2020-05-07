package model.playlist;

import model.list.IListBuilder;

import java.io.File;

@SuppressWarnings("unused")
public interface IPlaylistManager {

    // METHODES

    /**
     * Renvoie la Playlist manipulée par le IPlaylistManager
     * @return this.playlist
     */
    Playlist getPlaylist();

    /**
     * Renvoie le IListBuilder fabriquant la Playlist
     * @return this.builder
     */
    IListBuilder getBuilder();

    // COMMANDES

    /**
     * Charge le contenu du fichier sous forme d'une Playlist
     * @param f le fichier à charger
     */
    void load(File f);

    /**
     * Enregistre la Playlist dans un nouveau fichier nommé avec le nom de Playlist
     */
    void save();

    /**
     * Modifie l'attribut playlist avec celui passé en paramètre
     * @param playlist la nouvelle Playlist souhaitée
     */
    void setPlaylist(Playlist playlist);

    /**
     * Modifie l'attribut builder avec celui passé en paramètre
     * @param builder le nouveau IListBuilder souhaité
     */
    void setBuilder(IListBuilder builder);
}
