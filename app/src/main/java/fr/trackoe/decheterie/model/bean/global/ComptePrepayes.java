package fr.trackoe.decheterie.model.bean.global;

import java.util.ArrayList;

/**
 * Created by Haocheng on 22/05/2017.
 */

public class ComptePrepayes extends ContenantBean {
    private ArrayList<ComptePrepaye> listComptePrepaye;

    public ComptePrepayes() {
        this.listComptePrepaye = new ArrayList<>();
    }

    public ArrayList<ComptePrepaye> getListComptePrepaye() {
        return listComptePrepaye;
    }

    public void setListComptePrepaye(ArrayList<ComptePrepaye> listComptePrepaye) {
        this.listComptePrepaye = listComptePrepaye;
    }

    public void addComptePrepaye(int id, int dchUsagerId, float qtyPoint, int nbDepotRestant) {
        this.listComptePrepaye.add(new ComptePrepaye(id, dchUsagerId, qtyPoint, nbDepotRestant));
    }
}
