package model.list;

public class ListBuilderStd implements ListBuilder {

    // ATTRIBUTS

    private List list;

    // CONSTRUCTEUR

    public ListBuilderStd(List list) {
        if (list == null) {
            throw new AssertionError("Paramètre invalide ListBuilderStd constructeur");
        }
        list = new SubList();
    }

    // METHODES

    public List getList() {
        return list;
    }

    // COMMANDES

    @Override
    public void startList() {

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
