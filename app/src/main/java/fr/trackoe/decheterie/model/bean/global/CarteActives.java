package fr.trackoe.decheterie.model.bean.global;

import java.util.ArrayList;

/**
 * Created by Haocheng on 22/05/2017.
 */

public class CarteActives extends ContenantBean {
    private ArrayList<CarteActive> listCarteActive;

    public CarteActives() {
        this.listCarteActive = new ArrayList<>();
    }

    public ArrayList<CarteActive> getListCarteActive() {
        return listCarteActive;
    }

    public void setListCarteActive(ArrayList<CarteActive> listCarteActive) {
        this.listCarteActive = listCarteActive;
    }

    public void addCarteActive(int dchCarteId, String dateActivation, String dateDernierMotif, int dchCarteEtatRaisonId, boolean isActive, int dchComptePrepayeId) {
        this.listCarteActive.add(new CarteActive(dchCarteId, dateActivation, dateDernierMotif, dchCarteEtatRaisonId, isActive, dchComptePrepayeId));
    }
}

