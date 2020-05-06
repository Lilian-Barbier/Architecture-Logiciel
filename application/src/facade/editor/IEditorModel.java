package facade.editor;

import java.io.File;
import java.io.IOException;

public interface IEditorModel {

    // METHODES

    String getInfos();
    void getChild();
    void getParent();

    // COMMANDES

    void create(String name);
    void load(File f);
    void save();
    void addFile(String path) throws IOException;
    void addFilesFromFolder(String path) throws IOException;
    void addList(String path) throws IOException;

}
