package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Haocheng on 27/03/2017.
 */

public class Depot {
    private int id;
    private String nom;
    private String dateHeure;
    private int decheterieId;
    private int comptePrepayeId;
    private float qtyTotalUDD;

    public Depot() {

    }

    public Depot(int id, String nom, String dateHeure, int decheterieId, int comptePrepayeId, float qtyTotalUDD) {
        this.id = id;
        this.nom = nom;
        this.dateHeure = dateHeure;
        this.decheterieId = decheterieId;
        this.comptePrepayeId = comptePrepayeId;
        this.qtyTotalUDD = qtyTotalUDD;
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

}
