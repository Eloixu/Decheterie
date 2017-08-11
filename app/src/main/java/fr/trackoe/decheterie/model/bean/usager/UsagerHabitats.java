package fr.trackoe.decheterie.model.bean.usager;

import java.util.ArrayList;

import fr.trackoe.decheterie.model.bean.global.ContenantBean;

/**
 * Created by Remi on 04/05/2017.
 */
public class UsagerHabitats extends ContenantBean {
    private ArrayList<UsagerHabitat> listUsagerHabitat;

    public UsagerHabitats() {
        this.listUsagerHabitat = new ArrayList<>();
    }

    public ArrayList<UsagerHabitat> getListUsagerHabitat() {
        return listUsagerHabitat;
    }

    public void setListUsagerHabitat(ArrayList<UsagerHabitat> listUsagerHabitat) {
        this.listUsagerHabitat = listUsagerHabitat;
    }

    public void addUsagerHabitat(int dchUsagerId, int habitatId) {
        this.listUsagerHabitat.add(new UsagerHabitat(dchUsagerId, habitatId));
    }
}
