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
        this.setName("new_list");
        this.contains = new ArrayList<>();
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
            throw new AssertionError("Paramètre invalide SubList getChild (<0)");
        }
        if (num > contains.size()) {
            throw new AssertionError("Paramètre invalide SubList getChild (>size)");
        }
        
        return contains.get(num);
    }

    public void setContains(List<IMedia> contains) {
        if (contains == null) {
            throw new AssertionError("Paramètre invalide SubList setContains");
        }
        this.contains = contains;
    }

    public void add(IMedia media) {
        if (media == null) {
            throw new AssertionError("Paramètre invalide SubList add");
        }
        this.setDuration(this.getDuration() + media.getDuration());
        contains.add(media);
    }

    public void remove(IMedia media) {
        if (media == null) {
            throw new AssertionError("Paramètre invalide SubList remove");
        }
        contains.remove(media);
    }
    
	@Override
	public String getInfos() {
        return " SubList duration : " + getDuration() +
                "; SubList name : " + getName();
    }

    /**
     * Renvoie la taille de la sous-liste
     * @return this.contains.size()
     */
	public Integer size() {
		return contains.size();
	}
}