package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Haocheng on 10/04/2017.
 */

public class AccountFluxSetting {
    private int dchAccountSettingId;
    private int dchFluxId;
    private float convertComptagePrUDD;
    private boolean decompteUDD;
    private boolean decompteComptage;
    private float coutUCPrPoint;

    public AccountFluxSetting() {

    }

    public AccountFluxSetting(int dchAccountSettingId, int dchFluxId, float convertComptagePrUDD, boolean decompteUDD, boolean decompteComptage, float coutUCPrPoint) {
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

    public float getConvertComptagePrUDD() {
        return convertComptagePrUDD;
    }

    public void setConvertComptagePrUDD(float convertComptagePrUDD) {
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

    public float getCoutUCPrPoint() {
        return coutUCPrPoint;
    }

    public void setCoutUCPrPoint(float coutUCPrPoint) {
        this.coutUCPrPoint = coutUCPrPoint;
    }
}
