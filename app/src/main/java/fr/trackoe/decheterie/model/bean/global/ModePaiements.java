package fr.trackoe.decheterie.model.bean.global;

import java.util.ArrayList;

/**
 * Created by Haocheng on 30/05/2017.
 */

public class ModePaiements extends ContenantBean {
    private ArrayList<ModePaiement> listModePaiement;

    public ModePaiements() {
        this.listModePaiement = new ArrayList<>();
    }

    public ArrayList<ModePaiement> getListModePaiement() {
        return listModePaiement;
    }

    public void setListModePaiement(ArrayList<ModePaiement> listModePaiement) {
        this.listModePaiement = listModePaiement;
    }

    public void addModePaiement(int id, String mode) {
        this.listModePaiement.add(new ModePaiement(id, mode));
    }
}