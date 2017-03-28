package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Haocheng on 27/03/2017.
 */

public class Depot {
    private long id;
    private String nom;
    private String dateHeure;
    private int decheterieId;
    private int comptePrepayeId;
    private float qtyTotalUDD;
    private boolean statut;
    private boolean isSent;

    public Depot() {

    }

    public Depot(long id, String nom, String dateHeure, int decheterieId, int comptePrepayeId, float qtyTotalUDD, boolean statut, boolean isSent) {
        this.id = id;
        this.nom = nom;
        this.dateHeure = dateHeure;
        this.decheterieId = decheterieId;
        this.comptePrepayeId = comptePrepayeId;
        this.qtyTotalUDD = qtyTotalUDD;
        this.statut = statut;
        this.isSent = isSent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(String dateHeure) {
        this.dateHeure = dateHeure;
    }

    public int getDecheterieId() {
        return decheterieId;
    }

    public void setDecheterieId(int decheterieId) {
        this.decheterieId = decheterieId;
    }

    public int getComptePrepayeId() {
        return comptePrepayeId;
    }

    public void setComptePrepayeId(int comptePrepayeId) {
        this.comptePrepayeId = comptePrepayeId;
    }

    public float getQtyTotalUDD() {
        return qtyTotalUDD;
    }

    public void setQtyTotalUDD(float qtyTotalUDD) {
        this.qtyTotalUDD = qtyTotalUDD;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }
}
