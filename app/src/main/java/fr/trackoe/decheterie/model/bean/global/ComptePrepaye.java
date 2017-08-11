package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Haocheng on 27/03/2017.
 */

public class ComptePrepaye {
    private long id;
    private int dchUsagerId;
    private float qtyPoint;
    private int nbDepotRestant;

    public ComptePrepaye() {

    }

    public ComptePrepaye(long id, int dchUsagerId, float qtyPoint, int nbDepotRestant) {
        this.id = id;
        this.dchUsagerId = dchUsagerId;
        this.qtyPoint = qtyPoint;
        this.nbDepotRestant = nbDepotRestant;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDchUsagerId() {
        return dchUsagerId;
    }

    public void setDchUsagerId(int dchUsagerId) {
        this.dchUsagerId = dchUsagerId;
    }

    public float getQtyPoint() {
        return qtyPoint;
    }

    public void setQtyPoint(float qtyPoint) {
        this.qtyPoint = qtyPoint;
    }

    public int getNbDepotRestant() {
        return nbDepotRestant;
    }

    public void setNbDepotRestant(int nbDepotRestant) {
        this.nbDepotRestant = nbDepotRestant;
    }
}
