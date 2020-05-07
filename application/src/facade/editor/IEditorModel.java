package facade.editor;

import java.io.File;
import java.io.IOException;


@SuppressWarnings("unused")
public interface IEditorModel {

    // METHODES

    /**
     * Affiche les informations associés à la Playlist
     * @return les informations sous forme de String
     */
    String getInfos();

    // COMMANDES

    /**
     * Ajoute le nom name à la Playlist en cours de création
     * @param name le nom de la Playlist
     */
    void create(String name);

    /**
     * Charge le fichier f s'il s'agit d'un fichier dans un format compatible avec Playlist
     * @param f le fichier à charger
     */
    void load(File f);

    /**
     * Enregistre la Playlist dans un fichier en utilisant le nom de cette dernière
     */
    void save();

    /**
     * Ajoute le fichier de chemin path à la Playlist en cours de création
     * @param path le chemin du fichier à ajouter
     * @throws IOException exception levée en cas d'erreur lors de la lecture dans le fichier
     */
    void addFile(String path) throws IOException;

    /**
     * Ajoute tous les fichier du dossier de chemin path à la Playlist en cours de création
     * @param path le chemin du dossier à ajouter
     * @throws IOException exception levée en cas d'erreur lors de la lecture dans un des fichiers
     */
    void addFilesFromFolder(String path) throws IOException;

    /**
     * Ajoute une Playlist déjà existante dans la Playlist en cours de création
     * @param path le chemin de la playlist à ajouter
     * @throws IOException exception levée en cas d'erreur lors de la lecture du fichier
     */
    void addList(String path) throws IOException;
}
