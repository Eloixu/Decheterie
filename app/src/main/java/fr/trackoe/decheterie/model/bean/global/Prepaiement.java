package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Haocheng on 30/05/2017.
 */

public class Prepaiement {
    private int id;
    private String date;
    private float qtyPointPrepaye;
    private float coutsHT;
    private float coutsTVA;
    private float coutsTTC;
    private int idModePaiement;
    private int idComptePrepaye;

    public Prepaiement() {

    }

    public Prepaiement(int id, String date, float qtyPointPrepaye, float coutsHT, float coutsTVA, float coutsTTC, int idModePaiement, int idComptePrepaye) {
        this.id = id;
        this.date = date;
        this.qtyPointPrepaye = qtyPointPrepaye;
        this.coutsHT = coutsHT;
        this.coutsTVA = coutsTVA;
        this.coutsTTC = coutsTTC;
        this.idModePaiement = idModePaiement;
        this.idComptePrepaye = idComptePrepaye;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getQtyPointPrepaye() {
        return qtyPointPrepaye;
    }

    public void setQtyPointPrepaye(float qtyPointPrepaye) {
        this.qtyPointPrepaye = qtyPointPrepaye;
    }

    public float getCoutsHT() {
        return coutsHT;
    }

    public void setCoutsHT(float coutsHT) {
        this.coutsHT = coutsHT;
    }

    public float getCoutsTVA() {
        return coutsTVA;
    }

    public void setCoutsTVA(float coutsTVA) {
        this.coutsTVA = coutsTVA;
    }

    public float getCoutsTTC() {
        return coutsTTC;
    }

    public void setCoutsTTC(float coutsTTC) {
        this.coutsTTC = coutsTTC;
    }

    public int getIdModePaiement() {
        return idModePaiement;
    }

    public void setIdModePaiement(int idModePaiement) {
        this.idModePaiement = idModePaiement;
    }

    public int getIdComptePrepaye() {
        return idComptePrepaye;
    }

    public void setIdComptePrepaye(int idComptePrepaye) {
        this.idComptePrepaye = idComptePrepaye;
    }
}
