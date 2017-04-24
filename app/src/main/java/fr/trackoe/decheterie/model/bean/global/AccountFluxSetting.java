package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Haocheng on 10/04/2017.
 */

public class AccountFluxSetting {
    private int dchAccountSettingId;
    private int dchFluxId;
    private boolean convertComptagePrUDD;
    private boolean decompteUDD;
    private boolean decompteComptage;
    private String coutUCPrPoint;

    public AccountFluxSetting() {

    }

    public AccountFluxSetting(int dchAccountSettingId, int dchFluxId, boolean convertComptagePrUDD, boolean decompteUDD, boolean decompteComptage, String coutUCPrPoint) {
        this.dchAccountSettingId = dchAccountSettingId;
        this.dchFluxId = dchFluxId;
        this.convertComptagePrUDD = convertComptagePrUDD;
        this.decompteUDD = decompteUDD;
        this.decompteComptage = decompteComptage;
        this.coutUCPrPoint = coutUCPrPoint;
    }

    public int getDchAccountSettingId() {
        return dchAccountSettingId;
    }

    public void setDchAccountSettingId(int dchAccountSettingId) {
        this.dchAccountSettingId = dchAccountSettingId;
    }

    public int getDchFluxId() {
        return dchFluxId;
    }

    public void setDchFluxId(int dchFluxId) {
        this.dchFluxId = dchFluxId;
    }

    public boolean isConvertComptagePrUDD() {
        return convertComptagePrUDD;
    }

    public void setConvertComptagePrUDD(boolean convertComptagePrUDD) {
        this.convertComptagePrUDD = convertComptagePrUDD;
    }

    public boolean isDecompteUDD() {
        return decompteUDD;
    }

    public void setDecompteUDD(boolean decompteUDD) {
        this.decompteUDD = decompteUDD;
    }

    public boolean isDecompteComptage() {
        return decompteComptage;
    }

    public void setDecompteComptage(boolean decompteComptage) {
        this.decompteComptage = decompteComptage;
    }

    public String getCoutUCPrPoint() {
        return coutUCPrPoint;
    }

    public void setCoutUCPrPoint(String coutUCPrPoint) {
        this.coutUCPrPoint = coutUCPrPoint;
    }
}
