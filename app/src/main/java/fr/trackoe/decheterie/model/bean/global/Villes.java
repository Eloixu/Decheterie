package fr.trackoe.decheterie.model.bean.global;

import java.util.ArrayList;

/**
 * Created by Remi on 18/05/2016.
 */
public class Villes extends ContenantBean {
    private ArrayList<Ville> listVilles;
    private String checksum;

    public Villes() {
        this.listVilles = new ArrayList<Ville>();
    }

    public ArrayList<Ville> getListVilles() {
        return listVilles;
    }

    public void setListVilles(ArrayList<Ville> listVilles) {
        this.listVilles = listVilles;
    }

    public void addVille(int idVille, String nom, String cp) {
        this.listVilles.add(new Ville(idVille, nom, cp));
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }
}
