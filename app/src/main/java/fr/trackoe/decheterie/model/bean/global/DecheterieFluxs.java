package fr.trackoe.decheterie.model.bean.global;

import java.util.ArrayList;

/**
 * Created by Haocheng on 23/05/2017.
 */

public class DecheterieFluxs extends ContenantBean {
    private ArrayList<DecheterieFlux> listDecheterieFlux;

    public DecheterieFluxs() {
        this.listDecheterieFlux = new ArrayList<>();
    }

    public ArrayList<DecheterieFlux> getListDecheterieFlux() {
        return listDecheterieFlux;
    }

    public void setListDecheterieFlux(ArrayList<DecheterieFlux> listDecheterieFlux) {
        this.listDecheterieFlux = listDecheterieFlux;
    }

    public void addDecheterieFlux(int decheterieId, int fluxId) {
        this.listDecheterieFlux.add(new DecheterieFlux(decheterieId, fluxId));
    }
}
