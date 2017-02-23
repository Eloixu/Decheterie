package fr.trackoe.decheterie.model.bean.global;

import java.util.ArrayList;

/**
 * Created by Remi on 18/05/2016.
 */
public class Rues extends ContenantBean {
    private ArrayList<Rue> listRues;
    private String checksum;

    public Rues() {
        this.listRues = new ArrayList<Rue>();
    }

    public ArrayList<Rue> getListRues() {
        return listRues;
    }

    public void setListRues(ArrayList<Rue> listRues) {
        this.listRues = listRues;
    }

    public void addRue(int idRue, String nom, int idVille) {
        this.listRues.add(new Rue(idRue, nom, idVille));
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }
}
