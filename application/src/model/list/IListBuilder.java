package model.list;

import model.playlist.Playlist;

public interface IListBuilder {

    // METHODES

    /**
     * Renvoie la Playlist associé au IListBuilder
     * @return this.playlist
     */
    Playlist getPlaylist();

    // COMMANDES

    /**
     * Commence une nouvelle Sublist dans la liste courrante
     */
    void startSublist();

    /**
     * Termine une Sublist dans la liste courrante
     */
    void stopSublist();

    /**
     * Commence un nouvel Audio dans la liste courrante
     */
    void startAudio();

    /**
     * Termine un Audio dans la liste courrante
     */
    void stopAudio();

    /**
     * Commence une nouvelle Video dans la liste courrante
     */
    void startVideo();

    /**
     * Termine une Video dans la liste courrante
     */
    void stopVideo();

    /**
     * Ajoute un nom à une Sublist
     * @param name le nom à donner à la Sublist
     */
    void addName(String name);

    /**
     * Ajoute le chemin à un IMedia qui n'est pas une Sublist
     * @param path le chemin à donner au IMedia
     */
    void addPath(String path);
}
