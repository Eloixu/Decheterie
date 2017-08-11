package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Haocheng on 06/04/2017.
 */

public class CarteActive {
    private long dchCarteId;
    private String dateActivation;
    private String dateDernierMotif;
    private int dchCarteEtatRaisonId;
    private boolean isActive;
    private long dchComptePrepayeId;

    public CarteActive() {

    }

    public CarteActive(long dchCarteId, String dateActivation, String dateDernierMotif, int dchCarteEtatRaisonId, boolean isActive, long dchComptePrepayeId) {
        this.dchCarteId = dchCarteId;
        this.dateActivation = dateActivation;
        this.dateDernierMotif = dateDernierMotif;
        this.dchCarteEtatRaisonId = dchCarteEtatRaisonId;
        this.isActive = isActive;
        this.dchComptePrepayeId = dchComptePrepayeId;
    }

    public long getDchCarteId() {
        return dchCarteId;
    }

    public void setDchCarteId(long dchCarteId) {
        this.dchCarteId = dchCarteId;
    }

    public String getDateActivation() {
        return dateActivation;
    }

    public void setDateActivation(String dateActivation) {
        this.dateActivation = dateActivation;
    }

    public String getDateDernierMotif() {
        return dateDernierMotif;
    }

    public void setDateDernierMotif(String dateDernierMotif) {
        this.dateDernierMotif = dateDernierMotif;
    }

    public int getDchCarteEtatRaisonId() {
        return dchCarteEtatRaisonId;
    }

    public void setDchCarteEtatRaisonId(int dchCarteEtatRaisonId) {
        this.dchCarteEtatRaisonId = dchCarteEtatRaisonId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public long getDchComptePrepayeId() {
        return dchComptePrepayeId;
    }

    public void setDchComptePrepayeId(long dchComptePrepayeId) {
        this.dchComptePrepayeId = dchComptePrepayeId;
    }
}
