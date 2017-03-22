package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Trackoe on 21/03/2017.
 */

public class Flux {
    private int id;
    private String nom;
    private int icon_id;

    public Flux() {

    }

    public Flux(int id, String nom, int icon_id) {
        this.id = id;
        this.nom = nom;
        this.icon_id = icon_id;
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

    public int getIcon_id() {
        return icon_id;
    }

    public void setIcon_id(int icon_id) {
        this.icon_id = icon_id;
    }
}
