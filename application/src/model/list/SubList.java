package model.list;

import java.util.ArrayList;
import java.util.List;

public class SubList extends model.list.List {

    // ATTRIBUTS
    private java.util.List<IList> contains;

    // CONSTRUCTEUR
    public SubList(int duration, String name) {
        super(duration, name);
        this.contains = new ArrayList<IList>();
    }

    public SubList() {
        this.setDuration(0);
        this.setName("new list");
    }
    
    public SubList(List<IList> contains, String name) {
        super(0, name);
    	this.setContains(contains);
    }

    // METHODES

    public List<IList> getContains() {
        return contains;
    }

    public IList getChild(int num) {
        if (num < 0) {
            throw new AssertionError("Paramètre invalide SubList getChild");
        }
        IList[] a = (IList[]) contains.toArray();
        return a[num];
    }

    public void setContains(List<IList> contains) {
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