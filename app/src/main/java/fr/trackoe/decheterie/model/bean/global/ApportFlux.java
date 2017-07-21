package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Haocheng on 27/03/2017.
 */

public class ApportFlux {
    private long depotId;
    private int fluxId;
    private double qtyComptage;
    private double qtyUDD;
    private boolean isSent;

    public ApportFlux() {

    }

    public ApportFlux(long depotId, int fluxId, double qtyComptage, double qtyUDD, boolean isSent) {
        this.depotId = depotId;
        this.fluxId = fluxId;
        this.qtyComptage = qtyComptage;
        this.qtyUDD = qtyUDD;
        this.isSent = isSent;
    }

    public ApportFlux(long depotId, int fluxId, double qtyComptage, double qtyUDD) {
        this.depotId = depotId;
        this.fluxId = fluxId;
        this.qtyComptage = qtyComptage;
        this.qtyUDD = qtyUDD;
    }

    public long getDepotId() {
        return depotId;
    }

    public void setDepotId(long depotId) {
        this.depotId = depotId;
    }

    public int getFluxId() {
        return fluxId;
    }

    public void setFluxId(int fluxId) {
        this.fluxId = fluxId;
    }

    public double getQtyComptage() {
        return qtyComptage;
    }

    public void setQtyComptage(double qtyComptage) {
        this.qtyComptage = qtyComptage;
    }

    public double getQtyUDD() {
        return qtyUDD;
    }

    public void setQtyUDD(double qtyUDD) {
        this.qtyUDD = qtyUDD;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }
}
