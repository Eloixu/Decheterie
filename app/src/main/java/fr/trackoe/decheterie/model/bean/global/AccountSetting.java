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
    private float coutUDDPrPoint;
    private float coutPoint;
    private String unitePoint;
    private String dateDebutParam;
    private String dateFinParam;
    private int dchChoixDecompteTotalId;
    private int nbDepotRestant;
    private boolean compteTotal;
    private float pointMinimum;
    private int nbDepotMinimum;

    public AccountSetting() {

    }

    public AccountSetting(int id, int dchAccountId, int dchTypeCarteId, int uniteDepotDecheterieId, boolean decompteDepot, boolean decompteUDD, boolean pageSignature, float coutUDDPrPoint, float coutPoint, String unitePoint, String dateDebutParam, String dateFinParam, int dchChoixDecompteTotalId, int nbDepotRestant) {
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

    public AccountSetting(int id, int dchAccountId, int dchTypeCarteId, int uniteDepotDecheterieId, boolean decompteDepot, boolean decompteUDD, boolean pageSignature, float coutUDDPrPoint, float coutPoint, String unitePoint, String dateDebutParam, String dateFinParam, int dchChoixDecompteTotalId, int nbDepotRestant, boolean compteTotal, float pointMinimum, int nbDepotMinimum) {
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
        this.compteTotal = compteTotal;
        this.pointMinimum = pointMinimum;
        this.nbDepotMinimum = nbDepotMinimum;
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

    public float getCoutUDDPrPoint() {
        return coutUDDPrPoint;
    }

    public void setCoutUDDPrPoint(float coutUDDPrPoint) {
        this.coutUDDPrPoint = coutUDDPrPoint;
    }

    public float getCoutPoint() {
        return coutPoint;
    }

    public void setCoutPoint(float coutPoint) {
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

    public boolean isCompteTotal() {
        return compteTotal;
    }

    public void setCompteTotal(boolean compteTotal) {
        this.compteTotal = compteTotal;
    }

    public float getPointMinimum() {
        return pointMinimum;
    }

    public void setPointMinimum(float pointMinimum) {
        this.pointMinimum = pointMinimum;
    }

    public int getNbDepotMinimum() {
        return nbDepotMinimum;
    }

    public void setNbDepotMinimum(int nbDepotMinimum) {
        this.nbDepotMinimum = nbDepotMinimum;
    }
}
