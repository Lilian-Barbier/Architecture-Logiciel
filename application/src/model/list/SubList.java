package model.list;

import java.util.SortedSet;
import java.util.TreeSet;

public class SubList implements List {

    // ATTRIBUTS

    private int duration;
    private String name;
    private SortedSet<List> contains;

    // CONSTRUCTEUR
    public SubList() {
        this.contains = new TreeSet<List>();
    }

    // METHODES

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public String getName() {
        return name;
    }

    public SortedSet<List> getContains() {
        return contains;
    }

    public List getChild(int num) {
        if (num < 0) {
            throw new AssertionError("Paramètre invalide SubList getChild");
        }
        List[] a = (List[]) contains.toArray();
        return a[num];
    }

    // COMMANDES

    @Override
    public void setDuration(int duration) {
        if (duration < 0) {
            throw new AssertionError("Paramètre invalide SubList setDuration");
        }
        this.duration = duration;
    }

    @Override
    public void setName(String name) {
        if (name == null) {
            throw new AssertionError("Paramètre invalide SubList setName");
        }
        this.name = name;
    }

    public void setContains(SortedSet<List> contains) {
        if (contains == null) {
            throw new AssertionError("Paramètre invalide SubList setContains");
        }
        this.contains = contains;
    }

    public void add(List list) {
        if (list == null) {
            throw new AssertionError("Paramètre invalide SubList add");
        }
        duration = duration + list.getDuration();
        contains.add(list);
    }

    public void remove(List list) {
        if (list == null) {
            throw new AssertionError("Paramètre invalide SubList remove");
        }
        contains.remove(list);
    }
}