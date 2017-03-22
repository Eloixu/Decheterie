package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Haocheng on 21/03/2017.
 */

public class Icon {
    private int id;
    private String nom;
    private String domaine;
    private String path;

    public Icon() {

    }

    public Icon(int id, String nom, String domaine, String path) {
        this.id = id;
        this.nom = nom;
        this.domaine = domaine;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String iconId) {
        this.path = path;
    }

    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }
}
