package fr.trackoe.decheterie.model.bean.global;

import java.util.ArrayList;

/**
 * Created by Haocheng on 22/05/2017.
 */

public class CarteEtatRaisons extends ContenantBean {
    private ArrayList<CarteEtatRaison> listCarteEtatRaison;

    public CarteEtatRaisons() {
        this.listCarteEtatRaison = new ArrayList<>();
    }

    public ArrayList<CarteEtatRaison> getListCarteEtatRaison() {
        return listCarteEtatRaison;
    }

    public void setListCarteEtatRaison(ArrayList<CarteEtatRaison> listCarteEtatRaison) {
        this.listCarteEtatRaison = listCarteEtatRaison;
    }

    public void addCarteEtatRaison(int id, String raison) {
        this.listCarteEtatRaison.add(new CarteEtatRaison(id, raison));
    }
}

