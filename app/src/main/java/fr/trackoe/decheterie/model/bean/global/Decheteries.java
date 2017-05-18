package fr.trackoe.decheterie.model.bean.global;

import java.util.ArrayList;

import fr.trackoe.decheterie.model.bean.global.ContenantBean;
import fr.trackoe.decheterie.model.bean.global.Decheterie;

/**
 * Created by Haocheng on 18/05/2017.
 */

public class Decheteries extends ContenantBean {
    private ArrayList<Decheterie> listDecheterie;

    public Decheteries() {
        this.listDecheterie = new ArrayList<>();
    }

    public ArrayList<Decheterie> getListDecheterie() {
        return listDecheterie;
    }

    public void setListDecheterie(ArrayList<Decheterie> listDecheterie) {
        this.listDecheterie = listDecheterie;
    }

    public void addDecheterie(int id, int idAccount, String nom, String consigneComptage, String consigneAvSignature, boolean apportFlux) {
        this.listDecheterie.add(new Decheterie(id, idAccount, nom, consigneComptage, consigneAvSignature, apportFlux));
    }
}
