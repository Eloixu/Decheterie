package fr.trackoe.decheterie.model.bean.usager;

/**
 * Created by Remi on 05/04/2017.
 */
public class Usager {
    private int id;
    private int idAccount;
    private String nom;
    private String dateMaj;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDateMaj() {
        return dateMaj;
    }

    public void setDateMaj(String dateMaj) {
        this.dateMaj = dateMaj;
    }
}
