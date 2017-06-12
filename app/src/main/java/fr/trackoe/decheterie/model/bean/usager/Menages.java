package fr.trackoe.decheterie.model.bean.usager;

import java.util.ArrayList;

import fr.trackoe.decheterie.model.bean.global.ContenantBean;
import fr.trackoe.decheterie.model.bean.usager.Habitat;
import fr.trackoe.decheterie.model.bean.usager.Menage;

/**
 * Created by Remi on 04/05/2017.
 */
public class Menages extends ContenantBean {
    private ArrayList<Menage> listMenage;

    public Menages() {
        this.listMenage = new ArrayList<>();
    }

    public ArrayList<Menage> getListMenage() {
        return listMenage;
    }

    public void setListMenage(ArrayList<Menage> listMenage) {
        this.listMenage = listMenage;
    }

    public void addMenage(int id, int nbHabitants, boolean actif, int localId, String dateDebut, String dateFin, boolean isProprietaire) {
        this.listMenage.add(new Menage(id, nbHabitants, actif, localId, dateDebut, dateFin, isProprietaire));
    }
}
