package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Haocheng on 27/03/2017.
 */

public class Depot {
    private long id;
    private String nom;
    private String dateHeure;
    private byte[] signature;
    private int decheterieId;
    private long carteActiveCarteId;
    private long comptePrepayeId;
    private float qtyTotalUDD;
    private int statut;
    private boolean isSent;

    public Depot() {

    }

    public Depot(long id, String nom, String dateHeure, byte[] signature, int decheterieId, long carteActiveCarteId, int comptePrepayeId, float qtyTotalUDD, int statut, boolean isSent) {
        this.id = id;
        this.nom = nom;
        this.dateHeure = dateHeure;
        this.signature = signature;
        this.decheterieId = decheterieId;
        this.carteActiveCarteId = carteActiveCarteId;
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

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public int getDecheterieId() {
        return decheterieId;
    }

    public void setDecheterieId(int decheterieId) {
        this.decheterieId = decheterieId;
    }

    public long getCarteActiveCarteId() {
        return carteActiveCarteId;
    }

    public void setCarteActiveCarteId(long carteActiveCarteId) {
        this.carteActiveCarteId = carteActiveCarteId;
    }

    public long getComptePrepayeId() {
        return comptePrepayeId;
    }

    public void setComptePrepayeId(long comptePrepayeId) {
        this.comptePrepayeId = comptePrepayeId;
    }

    public float getQtyTotalUDD() {
        return qtyTotalUDD;
    }

    public void setQtyTotalUDD(float qtyTotalUDD) {
        this.qtyTotalUDD = qtyTotalUDD;
    }

    public int getStatut() {
        return statut;
    }

    public void setStatut(int statut) {
        this.statut = statut;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }
}
