package model.list;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class SubList extends Media {

    // ATTRIBUTS

    /**
     * L'ensemble des IMedia contenu dans la SubList
     */
    private List<IMedia> contains;

    // CONSTRUCTEUR

    public SubList(int duration, String name) {
        super(duration, name);
        this.contains = new ArrayList<>();
    }

    public SubList() {
        this.setDuration(0);
        this.setName("new list");
    }
    
    public SubList(List<IMedia> contains, String name) {
        super(0, name);
    	this.setContains(contains);
    }

    // METHODES

    public List<IMedia> getContains() {
        return contains;
    }

    public IMedia getChild(int num) {
        if (num < 0) {
            throw new AssertionError("Paramètre invalide SubList getChild");
        }
        IMedia[] a = (IMedia[]) contains.toArray();
        return a[num];
    }

    public void setContains(List<IMedia> contains) {
        if (contains == null) {
            throw new AssertionError("Paramètre invalide SubList setContains");
        }
        this.contains = contains;
    }

    public void add(IMedia list) {
        if (list == null) {
            throw new AssertionError("Paramètre invalide SubList add");
        }
        this.setDuration(this.getDuration() + list.getDuration());
        contains.add(list);
    }

    public void remove(IMedia list) {
        if (list == null) {
            throw new AssertionError("Paramètre invalide SubList remove");
        }
        contains.remove(list);
    }
    
	@Override
	public String getInfos() {
        return " SubList duration : " + getDuration() +
                "; SubList name : " + getName();
    }
}