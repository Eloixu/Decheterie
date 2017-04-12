package fr.trackoe.decheterie.model.bean.usager;

/**
 * Created by Haocheng on 10/04/2017.
 */

public class Local {
    private int idLocal;
    private int habitatId;
    private int menageId;
    private String lot;
    private String invariantDfip;
    private String identifiantInterne;
    private String batiment;
    private String etagePorte;

    public Local() {

    }

    public Local(int idLocal, int habitatId, int menageId, String lot, String invariantDfip, String identifiantInterne, String batiment, String etagePorte) {
        this.idLocal = idLocal;
        this.habitatId = habitatId;
        this.menageId = menageId;
        this.lot = lot;
        this.invariantDfip = invariantDfip;
        this.identifiantInterne = identifiantInterne;
        this.batiment = batiment;
        this.etagePorte = etagePorte;
    }

    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }

    public int getHabitatId() {
        return habitatId;
    }

    public void setHabitatId(int habitatId) {
        this.habitatId = habitatId;
    }

    public int getMenageId() {
        return menageId;
    }

    public void setMenageId(int menageId) {
        this.menageId = menageId;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getInvariantDfip() {
        return invariantDfip;
    }

    public void setInvariantDfip(String invariantDfip) {
        this.invariantDfip = invariantDfip;
    }

    public String getIdentifiantInterne() {
        return identifiantInterne;
    }

    public void setIdentifiantInterne(String identifiantInterne) {
        this.identifiantInterne = identifiantInterne;
    }

    public String getBatiment() {
        return batiment;
    }

    public void setBatiment(String batiment) {
        this.batiment = batiment;
    }

    public String getEtagePorte() {
        return etagePorte;
    }

    public void setEtagePorte(String etagePorte) {
        this.etagePorte = etagePorte;
    }
}
