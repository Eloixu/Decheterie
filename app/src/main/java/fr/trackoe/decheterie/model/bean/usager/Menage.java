package fr.trackoe.decheterie.model.bean.usager;

/**
 * Created by Haocheng on 10/04/2017.
 */

public class Menage {
    private int id;
    private int nbHabitants;
    private boolean actif;
    private int localId;
    private String dateDebut;
    private String dateFin;
    private boolean isProprietaire;

    public Menage() {

    }

    public Menage(int id, int nbHabitants, boolean actif, int localId, String dateDebut, String dateFin, boolean isProprietaire) {
        this.id = id;
        this.nbHabitants = nbHabitants;
        this.actif = actif;
        this.localId = localId;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.isProprietaire = isProprietaire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbHabitants() {
        return nbHabitants;
    }

    public void setNbHabitants(int nbHabitants) {
        this.nbHabitants = nbHabitants;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public int getLocalId() {
        return localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public boolean isProprietaire() {
        return isProprietaire;
    }

    public void setProprietaire(boolean proprietaire) {
        isProprietaire = proprietaire;
    }
}
