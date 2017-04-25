package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Haocheng on 10/04/2017.
 */

public class AccountSetting {
    private int id;
    private int dchAccountId;
    private int dchTypeCarteId;
    private int uniteDepotDecheterieId;
    private boolean decompteDepot;
    private boolean decompteUDD;
    private boolean pageSignature;
    private String coutUDDPrPoint;
    private String coutPoint;
    private String unitePoint;
    private String dateDebutParam;
    private String dateFinParam;
    private int dchChoixDecompteTotalId;
    private int nbDepotRestant;

    public AccountSetting() {

    }

    public AccountSetting(int id, int dchAccountId, int dchTypeCarteId, int uniteDepotDecheterieId, boolean decompteDepot, boolean decompteUDD, boolean pageSignature, String coutUDDPrPoint, String coutPoint, String unitePoint, String dateDebutParam, String dateFinParam, int dchChoixDecompteTotalId, int nbDepotRestant) {
        this.id = id;
        this.dchAccountId = dchAccountId;
        this.dchTypeCarteId = dchTypeCarteId;
        this.uniteDepotDecheterieId = uniteDepotDecheterieId;
        this.decompteDepot = decompteDepot;
        this.decompteUDD = decompteUDD;
        this.pageSignature = pageSignature;
        this.coutUDDPrPoint = coutUDDPrPoint;
        this.coutPoint = coutPoint;
        this.unitePoint = unitePoint;
        this.dateDebutParam = dateDebutParam;
        this.dateFinParam = dateFinParam;
        this.dchChoixDecompteTotalId = dchChoixDecompteTotalId;
        this.nbDepotRestant = nbDepotRestant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDchAccountId() {
        return dchAccountId;
    }

    public void setDchAccountId(int dchAccountId) {
        this.dchAccountId = dchAccountId;
    }

    public int getDchTypeCarteId() {
        return dchTypeCarteId;
    }

    public void setDchTypeCarteId(int dchTypeCarteId) {
        this.dchTypeCarteId = dchTypeCarteId;
    }

    public int getUniteDepotDecheterieId() {
        return uniteDepotDecheterieId;
    }

    public void setUniteDepotDecheterieId(int uniteDepotDecheterieId) {
        this.uniteDepotDecheterieId = uniteDepotDecheterieId;
    }

    public boolean isDecompteDepot() {
        return decompteDepot;
    }

    public void setDecompteDepot(boolean decompteDepot) {
        this.decompteDepot = decompteDepot;
    }

    public boolean isDecompteUDD() {
        return decompteUDD;
    }

    public void setDecompteUDD(boolean decompteUDD) {
        this.decompteUDD = decompteUDD;
    }

    public boolean isPageSignature() {
        return pageSignature;
    }

    public void setPageSignature(boolean pageSignature) {
        this.pageSignature = pageSignature;
    }

    public String getCoutUDDPrPoint() {
        return coutUDDPrPoint;
    }

    public void setCoutUDDPrPoint(String coutUDDPrPoint) {
        this.coutUDDPrPoint = coutUDDPrPoint;
    }

    public String getCoutPoint() {
        return coutPoint;
    }

    public void setCoutPoint(String coutPoint) {
        this.coutPoint = coutPoint;
    }

    public String getUnitePoint() {
        return unitePoint;
    }

    public void setUnitePoint(String unitePoint) {
        this.unitePoint = unitePoint;
    }

    public String getDateDebutParam() {
        return dateDebutParam;
    }

    public void setDateDebutParam(String dateDebutParam) {
        this.dateDebutParam = dateDebutParam;
    }

    public String getDateFinParam() {
        return dateFinParam;
    }

    public void setDateFinParam(String dateFinParam) {
        this.dateFinParam = dateFinParam;
    }

    public int getDchChoixDecompteTotalId() {
        return dchChoixDecompteTotalId;
    }

    public void setDchChoixDecompteTotalId(int dchChoixDecompteTotalId) {
        this.dchChoixDecompteTotalId = dchChoixDecompteTotalId;
    }

    public int getNbDepotRestant() {
        return nbDepotRestant;
    }

    public void setNbDepotRestant(int nbDepotRestant) {
        this.nbDepotRestant = nbDepotRestant;
    }
}
