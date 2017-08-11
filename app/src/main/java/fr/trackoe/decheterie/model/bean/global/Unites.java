package fr.trackoe.decheterie.model.bean.global;

import java.util.ArrayList;

/**
 * Created by Haocheng on 24/05/2017.
 */

public class Unites extends ContenantBean {
    private ArrayList<Unite> listUnite;

    public Unites() {
        this.listUnite = new ArrayList<>();
    }

    public ArrayList<Unite> getListUnite() {
        return listUnite;
    }

    public void setListUnite(ArrayList<Unite> listUnite) {
        this.listUnite = listUnite;
    }

    public void addUnite(int id, String nom) {
        this.listUnite.add(new Unite(id, nom));
    }
}
