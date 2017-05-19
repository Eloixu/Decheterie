package fr.trackoe.decheterie.model.bean.global;

import java.util.ArrayList;

/**
 * Created by Haocheng on 19/05/2017.
 */

public class Cartes extends ContenantBean {
    private ArrayList<Carte> listCarte;

    public Cartes() {
        this.listCarte = new ArrayList<>();
    }

    public ArrayList<Carte> getListCarte() {
        return listCarte;
    }

    public void setListCarte(ArrayList<Carte> listCarte) {
        this.listCarte = listCarte;
    }

    public void addCarte(int id, String numCarte, String numRFID, int dchTypeCarteId, int accountId) {
        this.listCarte.add(new Carte(id, numCarte, numRFID, dchTypeCarteId, accountId));
    }
}
