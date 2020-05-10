package facade.editor;

import model.list.IListBuilder;
import model.list.IMedia;
import model.list.StdListBuilder;
import model.list.SubList;
import model.playlist.IPlaylist;
import model.playlist.Playlist;
import model.util.LoadAudio;
import model.util.LoadVideo;
import model.xml.XMLPlaylistLoader;
import model.xml.XMLPlaylistSaver;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class StdEditorModel implements IEditorModel {

    //  CONSTANTES
    private static final String MTA = "mta";
    private static final String MTV = "mtv";
    private static final String XPL = "xpl";
    private static final String POINT = "\\.";


    // ATTRIBUTS

    /**
     * L'objet Playlist est la racine de notre playlist.
     */
    private Playlist rootPlaylist;

    /**
     * On associe pour chaque profondeur parcourus un indice indiquant le media courant.
     */
    private final Map<Integer, Integer> headPositions;

    /**
     * Profondeur de la tête de lecture.
     */
    private int depth;

    /**
     * Le chemin courant de la Playlist
     */
    private final String currentPath;

    // CONSTRUCTEUR

    public StdEditorModel() {
        String urlCourante = XMLPlaylistLoader.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        urlCourante = urlCourante.substring(0, urlCourante.lastIndexOf("/"));
        if (!new File(urlCourante + "/saves/").exists()) {
            boolean b = new File(urlCourante + "/saves/").mkdirs();
            if (!b) {
                System.out.println("Erreur lors de la création du dossier saves");
            }
        }
        headPositions = new TreeMap<>();
        headPositions.put(0,0);
        depth = 0;
        rootPlaylist = new Playlist();
        currentPath = urlCourante;
    }

    // METHODES

    /**
     * Renvoie la Playlist générale en cours d'édition
     * @return this.rootPlaylist
     */
    IPlaylist getRootPlaylist() {
        return rootPlaylist;
    }

    /**
     * Renvoie la map gérant le parcours en profondeur.
     * @return this.headPositions
     */
    public Map<Integer, Integer> getHeadPositions() {
        return headPositions;
    }

    /**
     * Renvoie la profondeur à laquelle nous sommes en train d'éditer
     * @return this.depth
     */
    public int getDepth() { return depth; }

    /**
     * Renvoie la Playlist dans laquelle nous sommes en train d'éditer
     * @return this.rootPlaylist en suivant le chemin de headPositions
     */
    IMedia getCurrentPlaylist() {
        SubList cursor = (SubList) getRootPlaylist().getPlaylist();
        if (getHeadPositions().size() > 1) {
            for (int k = 0; k <= getDepth(); ++k) {
                IMedia media = cursor.getChild(getHeadPositions().get(k));
                if (media instanceof SubList) {
                    cursor = (SubList) cursor.getChild(getHeadPositions().get(k));
                }
            }
            return cursor;
        }
        return cursor;
    }

    @Override
    public String getInfos() {
        IMedia cursor = getCurrentPlaylist();
        StringBuilder result = new StringBuilder("Current list name = " + cursor.getName() + "\n");
        int k = 0;
        if (cursor instanceof  SubList) {
            SubList sublist = (SubList) cursor;
            for (IMedia m : sublist.getContains()) {
                result.append(k).append(" : ").append(m.getName()).append(" / ").append(m.getDuration()).append(" secondes\n");
                ++k;
            }
        } else {
            result.append(k).append(" : ").append(cursor.getName()).append(" / ").append(cursor.getDuration()).append(" secondes\n");
            ++k;
        }
        return result.substring(0, result.length() - 1);
    }

    // COMMANDES

    @Override
    public void create(String name) {
        if (name == null) {
            throw new AssertionError("Paramètre invalide StdEditorModel create");
        }
        SubList sublist = (SubList) getRootPlaylist().getPlaylist();
        sublist.setName(name);
    }

    @Override
    public void load(File f) {
        if (f == null) {
            throw new AssertionError("Paramètre invalide StdEditorModel load");
        }
        IListBuilder playlistBuilder = new StdListBuilder(f);
        XMLPlaylistLoader.load(f, playlistBuilder);
        rootPlaylist = playlistBuilder.getPlaylist();
        String fileName = f.getAbsolutePath();
        String[] splitter = fileName.split("/");
        getRootPlaylist().setName(splitter[splitter.length - 1]);
    }


    @Override
    public void save() {
        XMLPlaylistSaver.save(this.getRootPlaylist());
    }

    @Override
    public void addFile(String path) throws IOException {
        if (path == null) {
            throw new AssertionError("Paramètre invalide StdEditorModel addFile");
        }
        path = path.replace(currentPath, ".");
        BufferedReader br = null;
        String[] splitter = path.split(POINT);
        String extension = splitter[splitter.length - 1 ];
        IMedia media;
        switch (extension) {
            case MTA :
                media = new LoadAudio().loadFile(path);
                break;
            case MTV :
                media = new LoadVideo().loadFile(path);
                break;
            default :
                System.out.println("Format non reconnu");
                return;
        }
        try {
            br = new BufferedReader(new FileReader(path));
            if (media != null) {
                int result = Integer.parseInt(br.readLine());
                media.setDuration(result);
                media.setName(br.readLine());
                media.setPath(path.replace("./", "../"));
            }
        } catch (NumberFormatException e) {
            return;
        } catch(FileNotFoundException exc ) {
            System.out.println("Erreur d'ouverture");
            return;
        } finally {
            if (br != null) {
                br.close();
            }
        }
        SubList cursor = (SubList) getCurrentPlaylist();
        cursor.add(media);
    }

    @Override
    public void addFilesFromFolder(String path) {

        File f = new File(path);
        if (f.isDirectory()){
            File[] tab = f.listFiles();
            if (tab != null) {
                for (int i = 0; i != tab.length; i++){
                    System.out.println(tab[i].getAbsolutePath());
                    if (tab[i].isDirectory()){
                        addFilesFromFolder(tab[i].getAbsolutePath());
                    } else {
                        try {
                            addFile(tab[i].getAbsolutePath());
                        } catch (IOException e) {
                            return;
                        }
                    }
                }
            }
        } else {
            System.out.println("Chemin du dossier incorrect");
        }
    }

    @Override
    public void addList(String path) {
        if (path == null) {
            throw new AssertionError("Paramètre invalide StdEditorModel addList");
        }
        path = path.replace(currentPath, "");
        path = path.substring(1);
        String[] splitter = path.split(POINT);
        String extension = splitter[splitter.length - 1 ];
        if (XPL.equals(extension)) {
            File f = new File(path);
            IListBuilder playlistBuilder = new StdListBuilder(f);
            XMLPlaylistLoader.load(f, playlistBuilder);
            SubList sublist = (SubList) playlistBuilder.getPlaylist().getPlaylist();
            SubList cursor = (SubList) getRootPlaylist().getPlaylist();
            for (int k = 0; k < getDepth(); ++k) {
                cursor = (SubList) cursor.getChild(getHeadPositions().get(k));
            }
            cursor.add(sublist);
        } else {
            System.out.println("Format non reconnu");
        }
    }

    /**
     * Augmente la profondeur de un
     */
    public void incrementDepth() {
        depth = depth + 1;
    }

    /**
     * Diminue la profondeur de un
     */
    public void decrementDepth() {
        depth = depth - 1;
    }

    /**
     * Permet d'aller en profondeur à l'indice index de la sous-liste en cours d'édition, si cela est possible
     * @param index l'indice où l'on souhaite plonger
     */
    public void enterList(int index) {
        SubList cursor = (SubList) getCurrentPlaylist();
        if (index > cursor.getContains().size() - 1 || index < 0) {
            System.out.println("Indice incorrect !");
            return;
        }
        IMedia m = cursor.getChild(index);
        if (!(m instanceof SubList)) {
            System.out.println("Ce n'est pas une sous-liste !");
            return;
        }
        incrementDepth();
        getHeadPositions().put(getDepth(), index);

    }

    /**
     * Permet de remonter à la liste parent de celle en cours d'édition, si cela est possible
     */
    public void ascendList() {
        if (getHeadPositions().size() == 1) {
            System.out.println("Vous êtes déjà à la racine !");
            return;
        }
        getHeadPositions().remove(getDepth());
        decrementDepth();
    }
}