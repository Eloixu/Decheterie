package fr.trackoe.decheterie.model.bean.global;

import java.util.ArrayList;

/**
 * Created by Haocheng on 23/05/2017.
 */

public class Fluxs extends ContenantBean {
    private ArrayList<Flux> listFlux;

    public Fluxs() {
        this.listFlux = new ArrayList<>();
    }

    public ArrayList<Flux> getListFlux() {
        return listFlux;
    }

    public void setListFlux(ArrayList<Flux> listFlux) {
        this.listFlux = listFlux;
    }

    public void addFlux(int id, String nom, int iconId, int uniteComptageId, int idAccount) {
        this.listFlux.add(new Flux(id, nom, iconId, uniteComptageId, idAccount));
    }
}

