package fr.trackoe.decheterie.model.bean.global;

import java.util.ArrayList;

/**
 * Created by Haocheng on 24/05/2017.
 */

public class ChoixDecompteTotals extends ContenantBean {
    private ArrayList<ChoixDecompteTotal> listChoixDecompteTotal;

    public ChoixDecompteTotals() {
        this.listChoixDecompteTotal = new ArrayList<>();
    }

    public ArrayList<ChoixDecompteTotal> getListChoixDecompteTotal() {
        return listChoixDecompteTotal;
    }

    public void setListChoixDecompteTotal(ArrayList<ChoixDecompteTotal> listChoixDecompteTotal) {
        this.listChoixDecompteTotal = listChoixDecompteTotal;
    }

    public void addChoixDecompteTotal(int id, String nom) {
        this.listChoixDecompteTotal.add(new ChoixDecompteTotal(id, nom));
    }
}
