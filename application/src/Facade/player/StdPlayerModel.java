package Facade.player;

import model.list.IList;
import model.list.Playlist;

import java.io.File;

public class StdPlayerModel implements  IPlayerModel {

    // ATTRIBUTS

    private Playlist currentPlaylist;
    private Playlist parentPlaylist;
    private int headDuration;
    private  Thread chrono;

    // CONSTRUCTEUR

    public StdPlayerModel() {
        currentPlaylist = new Playlist();
        parentPlaylist = new Playlist();
        headDuration = 0;
        chrono = new Thread();
    }

    // METHODES

    Playlist getCurrentPlaylist() {
        return currentPlaylist;
    }

    Playlist getParentPlaylist() {
        return parentPlaylist;
    }

    int getHeadDuration() {
        return headDuration;
    }

    @Override
    public String getInfos() {
        int duration = 0;
        for (IList list : currentPlaylist.getPlaylist().subList(0, currentPlaylist.getPlaylist().size())) {
            duration = duration + list.getDuration();
        }
        IList current = currentPlaylist.getCurrentFile();
        return "playlist name = " + currentPlaylist.getName() +
                " total duration " + duration +
                " current file name " + current.getName() +
                " current file duration " + current.getDuration();
    }

    @Override
    public void getChild() {

    }

    @Override
    public void getParent() {

    }

    // COMMANDES

    void setCurrentPlaylist(Playlist playlist) {
        if (playlist == null) {
            throw new AssertionError("Paramètre invalide StdPlayerModel setCurrentPlaylist");
        }        currentPlaylist = playlist;
    }

    void setParentPlaylist(Playlist playlist) {
        if (playlist == null) {
            throw new AssertionError("Paramètre invalide StdPlayerModel setParentPlaylist");
        }
        parentPlaylist = playlist;
    }

    void setHeadDuration(int duration) {
        if (duration > 0) {
            throw new AssertionError("Paramètre invalide StdPlayerModel setHeadDuration");
        }
    }


    @Override
    public void load(File f) {

    }

    @Override
    public void play() throws InterruptedException {
        chrono.notify();
        while (headDuration <= currentPlaylist.getCurrentFile().getDuration()) {
            chrono.sleep(999);
            incrementHeadDuration();
        }
        if (headDuration == currentPlaylist.getCurrentFile().getDuration()) {
            next();
        }
    }

    @Override
    public void pause() throws InterruptedException {
        chrono.wait();
    }

    @Override
    public void stop() {
        headDuration = 0;
        currentPlaylist.setHead(0);
    }

    @Override
    public void foreward() {
        int current = currentPlaylist.getHead();
        if (current < currentPlaylist.getPlaylist().size()) {
            currentPlaylist.setHead(current + 1);
        } else {
            stop();
        }
    }

    @Override
    public void backward() {
        int current = currentPlaylist.getHead();
        if (current > 0) {
            currentPlaylist.setHead(current - 1);
        }
    }

    @Override
    public void next() {

    }

    @Override
    public void previous() {

    }

    void incrementHeadDuration() {
        headDuration = headDuration + 1;
    }
}
