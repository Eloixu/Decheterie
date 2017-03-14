package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Trackoe on 14/03/2017.
 */

public class Decheterie {
    private int idAccount;
    private String name;
    private String consigneComptage;
    private String consigneSignature;
    private boolean apport;
    private String uniteTotal;

    public Decheterie() {

    }

    public Decheterie(int idAccount, String name, String consigneComptage, String consigneSignature, boolean apport, String uniteTotal) {
        this.idAccount = idAccount;
        this.name = name;
        this.consigneComptage = consigneComptage;
        this.consigneSignature = consigneSignature;
        this.apport = apport;
        this.uniteTotal = uniteTotal;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConsigneComptage() {
        return consigneComptage;
    }

    public void setConsigneComptage(String consigneComptage) {
        this.consigneComptage = consigneComptage;
    }

    public String getConsigneSignature() {
        return consigneSignature;
    }

    public void setConsigneSignature(String consigneSignature) {
        this.consigneSignature = consigneSignature;
    }

    public boolean isApport() {
        return apport;
    }

    public void setApport(boolean apport) {
        this.apport = apport;
    }

    public String getUniteTotal() {
        return uniteTotal;
    }

    public void setUniteTotal(String uniteTotal) {
        this.uniteTotal = uniteTotal;
    }
}
