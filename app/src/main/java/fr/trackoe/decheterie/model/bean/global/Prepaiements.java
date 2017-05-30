package fr.trackoe.decheterie.model.bean.global;

import java.util.ArrayList;

/**
 * Created by Haocheng on 30/05/2017.
 */

public class Prepaiements extends ContenantBean {
    private ArrayList<Prepaiement> listPrepaiement;

    public Prepaiements() {
        this.listPrepaiement = new ArrayList<>();
    }

    public ArrayList<Prepaiement> getListPrepaiement() {
        return listPrepaiement;
    }

    public void setListPrepaiement(ArrayList<Prepaiement> listPrepaiement) {
        this.listPrepaiement = listPrepaiement;
    }

    public void addPrepaiement(int id, String date, float qtyPointPrepaye, float coutsHT, float coutsTVA, float coutsTTC, int idModePaiement, int idComptePrepaye) {
        this.listPrepaiement.add(new Prepaiement(id, date, qtyPointPrepaye, coutsHT, coutsTVA, coutsTTC, idModePaiement, idComptePrepaye));
    }
}

