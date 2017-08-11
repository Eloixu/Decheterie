package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Haocheng on 11/04/2017.
 */

public class Unite {
    private int id;
    private String nom;

    public Unite() {

    }

    public Unite(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
