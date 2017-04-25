package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Trackoe on 21/03/2017.
 */

public class Flux {
    private int id;
    private String nom;
    private int iconId;
    private int uniteComptageId;

    public Flux() {

    }

    public Flux(int id, String nom, int iconId, int uniteComptageId) {
        this.id = id;
        this.nom = nom;
        this.iconId = iconId;
        this.uniteComptageId = uniteComptageId;
    }

    public Flux(String nom, int iconId, int uniteComptageId) {
        this.nom = nom;
        this.iconId = iconId;
        this.uniteComptageId = uniteComptageId;
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

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public int getUniteComptageId() {
        return uniteComptageId;
    }

    public void setUniteComptageId(int uniteComptageId) {
        this.uniteComptageId = uniteComptageId;
    }
}
