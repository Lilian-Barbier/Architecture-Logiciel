package facade.player;

import model.list.IMedia;
import model.list.SubList;
import model.playlist.IPlaylistManager;
import model.playlist.Playlist;
import model.playlist.PlaylistManager;
import model.xml.XMLPlaylistManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StdPlayerModel implements  IPlayerModel {

    // ATTRIBUTS

    private PlaylistManager manager;
    private Playlist currentPlaylist;
    private int headDuration;
    private Thread chrono;
    private Map<Integer, Integer> map;
    private int depth;

    // CONSTRUCTEUR

    public StdPlayerModel() {
        manager = new XMLPlaylistManager();
        currentPlaylist = new Playlist();
        headDuration = 0;
        chrono = new Thread();
        map = new TreeMap<>();
        map.put(0,0);
        depth = 0;
    }

    // METHODES

    public Playlist getCurrentPlaylist() {
        return currentPlaylist;
    }

    public int getHeadDuration() {
        return headDuration;
    }

    public int getMediaDuration() {
        IMedia current = getCurrentFile();
        return current.getDuration();
    }

    public int getDepth() { return depth; }

    @Override
    public String getInfos() {
        int duration = 0;
        for (IMedia list : currentPlaylist.getPlaylist().subList(0, currentPlaylist.getPlaylist().size())) {
            duration = duration + list.getDuration();
        }
        IMedia current = getCurrentFile();
        return "playlist name = " + currentPlaylist.getName() +
                " total duration " + duration +
                " current file name " + current.getName() +
                " current file duration " + current.getDuration();
    }

    // COMMANDES

    public void setCurrentPlaylist(Playlist playlist) {
        if (playlist == null) {
            throw new AssertionError("Paramètre invalide StdPlayerModel setCurrentPlaylist");
        }
        currentPlaylist = playlist;
    }

    public void setHeadDuration(int duration) {
        if (duration > 0) {
            throw new AssertionError("Paramètre invalide StdPlayerModel setHeadDuration");
        }
        headDuration = duration;
    }

    public void incrementDepth() {
        depth = depth + 1;
    }

    public void decrementDepth() {
        depth = depth - 1;
    }

    @Override
    public void load(File f) {
        manager.load(f);
    }

    @Override
    public void play() throws InterruptedException {
        chrono.notify();
        while (headDuration <= getCurrentFile().getDuration()) {
            chrono.sleep(999);
            incrementHeadDuration();
        }
        if (headDuration == getCurrentFile().getDuration()) {
            if (currentPlaylist.getHead() == currentPlaylist.getPlaylist().size() - 1) {
                stop();
            }
            foreward();
        }
    }

    @Override
    public synchronized void pause() throws InterruptedException {
        chrono.wait();
    }

    @Override
    public synchronized void stop() throws InterruptedException {
        headDuration = 0;
        map = new TreeMap<>();
        map.put(0,0);
        depth = 0;
        chrono.wait();
    }

    @Override
    public synchronized void foreward() throws InterruptedException {
        IMedia currentMedia = getCurrentFile();
        SubList parentMedia = getParentCurrentFile();
        // FICHIER COURANT EST UNE SOUS LISTE
        if (currentMedia instanceof SubList) {
            while (currentMedia instanceof  SubList) {
                incrementDepth();
                map.put(getDepth(), 0);
                currentMedia = getCurrentFile();
            }
        // FICHIER COURANT N'EST PAS UNE SOUS LISTE
        } else {
            int old = map.get(depth);
            // SI LE FICHIER COURANT EST DANS UNE SOUS LISTE
            if (parentMedia != null) {
                // SI JE SUIS A LA FIN DE LA LISTE PARENT
                for (int k = getDepth(); k > 0; --k) {
                    if (old == parentMedia.getContains().size()) {
                        map.remove(depth);
                        decrementDepth();
                        old = map.get(depth);
                        map.put(depth, old + 1);
                        parentMedia = getParentCurrentFile();
                        old = old + 1;
                    }
                }
            // SINON
            } else {
                if (map.get(0) == this.currentPlaylist.getPlaylist().size() && map.size() == 1) {
                    stop();
                } else {
                    map.remove(depth);
                    map.put(depth, old + 1);
                }
            }
        }
    }

    @Override
    public synchronized void backward() {
        IMedia currentMedia = getCurrentFile();
        SubList parentMedia = getParentCurrentFile();
        // FICHIER COURANT EST UNE SOUS LISTE
        if (currentMedia instanceof SubList) {
            while (currentMedia instanceof  SubList) {
                decrementDepth();
                map.put(getDepth(), 0);
                currentMedia = getCurrentFile();
            }
            // FICHIER COURANT N'EST PAS UNE SOUS LISTE
        } else {
            int old = map.get(depth);
            // SI LE FICHIER COURANT EST DANS UNE SOUS LISTE
            if (parentMedia != null) {
                // SI JE SUIS AU DEBUT DE LA LISTE PARENT
                for (int k = getDepth(); k > 0; --k) {
                    if (old <= 0) {
                        map.remove(depth);
                        decrementDepth();
                        old = map.get(depth);
                        map.put(depth, old - 1);
                        old = old - 1;
                    }
                }
            // SINON
            } else {
                map.remove(depth);
                map.put(depth, old - 1);
            }
        }
    }

    @Override
    public synchronized void nextList() throws InterruptedException {
        if (map.get(0) == this.currentPlaylist.getPlaylist().size() && map.size() == 1) {
            stop();
        } else {
            IMedia currentMedia = getCurrentFile();
            if (currentMedia instanceof SubList) {
                map.remove(getDepth());
                decrementDepth();
                int old = map.get(depth);
                map.put(getDepth(), old + 1);
            }
        }
    }

    @Override
    public synchronized void previousList() {
        if (!(depth == 0 && map.get(depth) == 0)) {
            IMedia currentMedia = getCurrentFile();
            if (currentMedia instanceof SubList) {
                map.remove(getDepth());
                decrementDepth();
                int old = map.get(depth);
                map.put(getDepth(), old - 1);
            }
        }
    }

    public IMedia getCurrentFile() {
        IMedia media = this.currentPlaylist.getPlaylist().get(map.get(0));
        if (media instanceof SubList) {
            SubList sublist = (SubList) media;
            for (int k = 1; k <= getDepth(); ++k) {
                int head = map.get(k);
                sublist = (SubList) sublist.getChild(head);
            }
            return sublist;
        }
        return media;
    }

    public SubList getParentCurrentFile() {
        IMedia media = this.currentPlaylist.getPlaylist().get(map.get(0));
        if (media instanceof SubList) {
            SubList sublist = (SubList) media;
            for (int k = 1; k < getDepth(); ++k) {
                int head = map.get(k);
                sublist = (SubList) sublist.getChild(head);
            }
            return sublist;
        }
        return null;
    }

    public void incrementHeadDuration() {
        headDuration = headDuration + 1;
    }
}
