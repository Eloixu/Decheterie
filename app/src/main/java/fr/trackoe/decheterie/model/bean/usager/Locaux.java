package fr.trackoe.decheterie.model.bean.usager;

import java.util.ArrayList;

import fr.trackoe.decheterie.model.bean.global.ContenantBean;
import fr.trackoe.decheterie.model.bean.usager.Habitat;
import fr.trackoe.decheterie.model.bean.usager.Local;

/**
 * Created by Remi on 04/05/2017.
 */
public class Locaux extends ContenantBean {
    private ArrayList<Local> listLocal;

    public Locaux() {
        this.listLocal = new ArrayList<>();
    }

    public ArrayList<Local> getListLocal() {
        return listLocal;
    }

    public void setListLocal(ArrayList<Local> listLocal) {
        this.listLocal = listLocal;
    }

    public void addLocal(int idLocal, int habitatId, String lot, String invariantDfip, String identifiantInterne, String batiment, String etagePorte) {
        this.listLocal.add(new Local(idLocal, habitatId, lot, invariantDfip, identifiantInterne, batiment, etagePorte));
    }
}
