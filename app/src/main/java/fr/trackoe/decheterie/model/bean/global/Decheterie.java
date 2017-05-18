package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Haocheng on 14/03/2017.
 */

public class Decheterie {
    private int id;
    private int idAccount;
    private String nom;
    private String consigneComptage;
    private String consigneAvSignature;
    private boolean apportFlux;

    public Decheterie() {

    }

    public Decheterie(int id, int idAccount, String nom, String consigneComptage, String consigneAvSignature, boolean apportFlux) {
        this.id = id;
        this.idAccount = idAccount;
        this.nom = nom;
        this.consigneComptage = consigneComptage;
        this.consigneAvSignature = consigneAvSignature;
        this.apportFlux = apportFlux;
    }

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

    public String getConsigneComptage() {
        return consigneComptage;
    }

    public void setConsigneComptage(String consigneComptage) {
        this.consigneComptage = consigneComptage;
    }

    public String getConsigneAvSignature() {
        return consigneAvSignature;
    }

    public void setConsigneAvSignature(String consigneAvSignature) {
        this.consigneAvSignature = consigneAvSignature;
    }

    public boolean isApportFlux() {
        return apportFlux;
    }

    public void setApportFlux(boolean apportFlux) {
        this.apportFlux = apportFlux;
    }

}
