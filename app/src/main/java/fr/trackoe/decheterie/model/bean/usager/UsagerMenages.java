package fr.trackoe.decheterie.model.bean.usager;

import java.util.ArrayList;

import fr.trackoe.decheterie.model.bean.global.ContenantBean;

/**
 * Created by Remi on 04/05/2017.
 */
public class UsagerMenages extends ContenantBean {
    private ArrayList<UsagerMenage> listUsagerMenage;

    public UsagerMenages() {
    }

    public ArrayList<UsagerMenage> getListUsagerMenage() {
        return listUsagerMenage;
    }

    public void setListUsagerMenage(ArrayList<UsagerMenage> listUsagerMenage) {
        this.listUsagerMenage = listUsagerMenage;
    }

    public void addUsagerMenage(int dchUsagerId, int menageId) {
        this.listUsagerMenage.add(new UsagerMenage(dchUsagerId, menageId));
    }
}
