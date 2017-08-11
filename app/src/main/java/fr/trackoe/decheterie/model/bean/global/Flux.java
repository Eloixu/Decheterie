package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Trackoe on 21/03/2017.
 */

public class Flux {
    private int id;
    private String nom;
    private int iconId;
    private int uniteComptageId;
    private int idAccount;

    public Flux() {

    }

    public Flux(int id, String nom, int iconId, int uniteComptageId, int idAccount) {
        this.id = id;
        this.nom = nom;
        this.iconId = iconId;
        this.uniteComptageId = uniteComptageId;
        this.idAccount = idAccount;
    }

    public Flux(String nom, int iconId, int uniteComptageId, int idAccount) {
        this.nom = nom;
        this.iconId = iconId;
        this.uniteComptageId = uniteComptageId;
        this.idAccount = idAccount;
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

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }
}
