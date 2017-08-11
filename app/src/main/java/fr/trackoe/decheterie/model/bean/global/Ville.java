package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Remi on 18/05/2016.
 */
public class Ville {
    private int idVille;
    private String nom;
    private String cp;

    public Ville() {
    }

    public Ville(int idVille, String nom, String cp) {
        this.idVille = idVille;
        this.nom = nom;
        this.cp = cp;
    }

    public int getIdVille() {
        return idVille;
    }

    public void setIdVille(int idVille) {
        this.idVille = idVille;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    @Override
    public String toString() {
        return nom;
    }
}
