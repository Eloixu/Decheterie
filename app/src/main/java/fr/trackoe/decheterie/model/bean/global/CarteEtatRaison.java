package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Haocheng on 10/04/2017.
 */

public class CarteEtatRaison {
    private int id;
    private String raison;

    public CarteEtatRaison() {

    }

    public CarteEtatRaison(int id, String raison) {
        this.id = id;
        this.raison = raison;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRaison() {
        return raison;
    }

    public void setRaison(String raison) {
        this.raison = raison;
    }
}
