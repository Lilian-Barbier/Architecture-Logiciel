package model.list;

public class ListBuilderStd implements ListBuilder {

    // ATTRIBUTS

    private IList list;

    // CONSTRUCTEUR

    public ListBuilderStd(IList list) {
        if (list == null) {
            throw new AssertionError("Paramètre invalide ListBuilderStd constructeur");
        }
        this.list = list;
    }

    public ListBuilderStd() {
        this.list = new List();
    }

    // METHODES

    public IList getList() {
        return list;
    }

    // COMMANDES

    @Override
    public void startList() {
    }

    @Override
    public void stopList() {

    }

    @Override
    public void startAudio() {

    }

    @Override
    public void stopAudio() {

    }

    @Override
    public void startVideo() {

    }

    @Override
    public void stopVideo() {

    }

    @Override
    public void setName(String name) {
        if (name == null) {
            throw new AssertionError("Paramètre invalide ListBuilderStd setName");
        }
        list.setName(name);
    }

    @Override
    public void setDuration(int duration) {
        if (duration < 0) {
            throw new AssertionError("Paramètre invalide ListBuilderStd setDuration");
        }
        list.setDuration(duration);
    }
}
