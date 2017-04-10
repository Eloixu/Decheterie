package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Haocheng on 06/04/2017.
 */

public class Carte {
    private long id;
    private String numCarte;
    private String numRFID;
    private int dchTypeCarteId;
    private int dchAccountId;

    public Carte() {

    }

    public Carte(long id, String numCarte, String numRFID, int dchTypeCarteId, int dchAccountId) {
        this.id = id;
        this.numCarte = numCarte;
        this.numRFID = numRFID;
        this.dchTypeCarteId = dchTypeCarteId;
        this.dchAccountId = dchAccountId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumCarte() {
        return numCarte;
    }

    public void setNumCarte(String numCarte) {
        this.numCarte = numCarte;
    }

    public String getNumRFID() {
        return numRFID;
    }

    public void setNumRFID(String numRFID) {
        this.numRFID = numRFID;
    }

    public int getDchTypeCarteId() {
        return dchTypeCarteId;
    }

    public void setDchTypeCarteId(int dchTypeCarteId) {
        this.dchTypeCarteId = dchTypeCarteId;
    }

    public int getDchAccountId() {
        return dchAccountId;
    }

    public void setDchAccountId(int dchAccountId) {
        this.dchAccountId = dchAccountId;
    }
}
