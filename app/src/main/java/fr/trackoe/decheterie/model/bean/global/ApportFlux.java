package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Haocheng on 27/03/2017.
 */

public class ApportFlux {
    private long depotId;
    private int fluxId;
    private float qtyApporte;
    private boolean isSent;

    public ApportFlux() {

    }

    public ApportFlux(long depotId, int fluxId, float qtyApporte, boolean isSent) {
        this.depotId = depotId;
        this.fluxId = fluxId;
        this.qtyApporte = qtyApporte;
        this.isSent = isSent;
    }

    public ApportFlux(long depotId, int fluxId, float qtyApporte) {
        this.depotId = depotId;
        this.fluxId = fluxId;
        this.qtyApporte = qtyApporte;
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

    public float getQtyApporte() {
        return qtyApporte;
    }

    public void setQtyApporte(float qtyApporte) {
        this.qtyApporte = qtyApporte;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }
}
