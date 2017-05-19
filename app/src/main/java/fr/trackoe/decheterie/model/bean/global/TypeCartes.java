package fr.trackoe.decheterie.model.bean.global;

import java.util.ArrayList;

/**
 * Created by Haocheng on 19/05/2017.
 */

public class TypeCartes extends ContenantBean {
    private ArrayList<TypeCarte> listTypeCarte;

    public TypeCartes() {
        this.listTypeCarte = new ArrayList<>();
    }

    public ArrayList<TypeCarte> getListTypeCarte() {
        return listTypeCarte;
    }

    public void setListTypeCarte(ArrayList<TypeCarte> listTypeCarte) {
        this.listTypeCarte = listTypeCarte;
    }

    public void addTypeCarte(int id, String nom) {
        this.listTypeCarte.add(new TypeCarte(id, nom));
    }
}
