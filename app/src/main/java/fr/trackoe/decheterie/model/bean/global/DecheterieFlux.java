package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Haocheng on 24/03/2017.
 */

public class DecheterieFlux {
    private int decheterieId;
    private int fluxId;

    public DecheterieFlux() {

    }

    public DecheterieFlux(int decheterieId, int fluxId) {
        this.decheterieId = decheterieId;
        this.fluxId = fluxId;
    }

    public int getDecheterieId() {
        return decheterieId;
    }

    public void setDecheterieId(int decheterieId) {
        this.decheterieId = decheterieId;
    }

    public int getFluxId() {
        return fluxId;
    }

    public void setFluxId(int fluxId) {
        this.fluxId = fluxId;
    }
}
