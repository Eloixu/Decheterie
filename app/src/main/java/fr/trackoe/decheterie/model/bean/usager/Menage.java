package fr.trackoe.decheterie.model.bean.usager;

/**
 * Created by Haocheng on 10/04/2017.
 */

public class Menage {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private int nbHabitats;
    private String reference;
    private int habitatId;
    private boolean actif;
    private String telephone;
    private String civilite;

    public Menage() {

    }

    public Menage(int id, String nom, String prenom, String email, int nbHabitats, String reference, int habitatId, boolean actif, String telephone, String civilite) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.nbHabitats = nbHabitats;
        this.reference = reference;
        this.habitatId = habitatId;
        this.actif = actif;
        this.telephone = telephone;
        this.civilite = civilite;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNbHabitats() {
        return nbHabitats;
    }

    public void setNbHabitats(int nbHabitats) {
        this.nbHabitats = nbHabitats;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public int getHabitatId() {
        return habitatId;
    }

    public void setHabitatId(int habitatId) {
        this.habitatId = habitatId;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCivilite() {
        return civilite;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }
}
