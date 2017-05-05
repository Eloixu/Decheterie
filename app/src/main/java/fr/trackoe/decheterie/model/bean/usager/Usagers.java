package fr.trackoe.decheterie.model.bean.usager;

import java.util.ArrayList;

import fr.trackoe.decheterie.model.bean.global.ContenantBean;
import fr.trackoe.decheterie.model.bean.usager.Menage;
import fr.trackoe.decheterie.model.bean.usager.Usager;

/**
 * Created by Remi on 04/05/2017.
 */
public class Usagers extends ContenantBean {
    private ArrayList<Usager> listUsager;

    public Usagers() {
        this.listUsager = new ArrayList<>();
    }

    public ArrayList<Usager> getListUsager() {
        return listUsager;
    }

    public void setListUsager(ArrayList<Usager> listUsager) {
        this.listUsager = listUsager;
    }

    public void addUsager(int id, int idAccount, String nom, String dateMaj) {
        this.listUsager.add(new Usager(id, idAccount, nom, dateMaj));
    }
}
