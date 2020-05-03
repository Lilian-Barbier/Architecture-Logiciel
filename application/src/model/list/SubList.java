package model.list;

import java.util.SortedSet;
import java.util.TreeSet;

public class SubList extends List {

    // ATTRIBUTS

    private SortedSet<IList> contains;

    // CONSTRUCTEUR
    public SubList(int duration, String name) {
        super(duration, name);
        this.contains = new TreeSet<IList>();
    }

    public SubList() {
        this.setDuration(0);
        this.setName("new list");
    }

    // METHODES

    public SortedSet<IList> getContains() {
        return contains;
    }

    public IList getChild(int num) {
        if (num < 0) {
            throw new AssertionError("Paramètre invalide SubList getChild");
        }
        IList[] a = (IList[]) contains.toArray();
        return a[num];
    }

    public void setContains(SortedSet<IList> contains) {
        if (contains == null) {
            throw new AssertionError("Paramètre invalide SubList setContains");
        }
        this.contains = contains;
    }

    public void add(IList list) {
        if (list == null) {
            throw new AssertionError("Paramètre invalide SubList add");
        }
        this.setDuration(this.getDuration() + list.getDuration());
        contains.add(list);
    }

    public void remove(IList list) {
        if (list == null) {
            throw new AssertionError("Paramètre invalide SubList remove");
        }
        contains.remove(list);
    }
}