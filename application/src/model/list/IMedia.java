package model.list;

public interface IMedia {

    // METHODES

    /**
     * Renvoie la durée du IMedia
     * @return this.duration
     */
    int getDuration();

    /**
     * Renvoie le nom du fichier du IMedia
     * @return this.name
     */
    String getName();

    /**
     * Renvoie le chemin du fichier du IMedia
     * @return this.path
     */
    String getPath();

    /**
     * Renvoie l'ensemble des informations du IMedia
     * @return une String contenant les valeurs des attributs
     */
    String getInfos();

    // COMMANDES

    /**
     * Modifie l'attribut duration avec celui passé en paramètre
     * @param duration la nouvelle durée souhaitée
     */
    void setDuration(int duration);

    /**
     * Modifie l'attribut name avec celui passé en paramètre
     * @param name le nouveau nom du fichier souhaité
     */
    void setName(String name);

    /**
     * Modifie l'attribut path avec celui passé en paramètre
     * @param path le nouveau chemin souhaité
     */
    void setPath(String path);
}
