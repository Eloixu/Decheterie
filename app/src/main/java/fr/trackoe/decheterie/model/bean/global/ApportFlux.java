package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Haocheng on 27/03/2017.
 */

public class ApportFlux {
    private int depotId;
    private int fluxId;
    private float qtyApporte;

    public ApportFlux() {

    }

    public ApportFlux(int depotId, int fluxId, float qtyApporte) {
        this.depotId = depotId;
        this.fluxId = fluxId;
        this.qtyApporte = qtyApporte;
    }

    public int getDepotId() {
        return depotId;
    }

    public void setDepotId(int depotId) {
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
}
