package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Remi on 18/05/2016.
 */
public class Rue {
    private int idRue;
    private String nom;
    private int idVille;

    public Rue() {
    }

    public Rue(int idRue, String nom, int idVille) {
        this.idRue = idRue;
        this.nom = nom;
        this.idVille = idVille;
    }

    public int getIdRue() {
        return idRue;
    }

    public void setIdRue(int idRue) {
        this.idRue = idRue;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getIdVille() {
        return idVille;
    }

    public void setIdVille(int idVille) {
        this.idVille = idVille;
    }
}
