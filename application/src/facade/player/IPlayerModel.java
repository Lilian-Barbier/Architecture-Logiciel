package facade.player;

import java.io.File;

public interface IPlayerModel {

    // METHODES

    String getInfos();
    void getChild();
    void getParent();

    // COMMANDES

    void load(File f);
    void play() throws InterruptedException;
    void pause() throws InterruptedException;
    void stop() throws InterruptedException;
    void foreward() throws InterruptedException;
    void backward();
    void nextList();
    void previousList();
}
