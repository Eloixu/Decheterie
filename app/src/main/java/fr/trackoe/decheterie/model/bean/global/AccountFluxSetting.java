package fr.trackoe.decheterie.model.bean.global;

/**
 * Created by Haocheng on 10/04/2017.
 */

public class AccountFluxSetting {
    private int dchAccountSettingId;
    private int dchFluxId;
    private String convertComptagePrUDD;
    private String coutUCPrPoint;

    public AccountFluxSetting() {

    }

    public AccountFluxSetting(int dchAccountSettingId, int dchFluxId, String convertComptagePrUDD, String coutUCPrPoint) {
        this.dchAccountSettingId = dchAccountSettingId;
        this.dchFluxId = dchFluxId;
        this.convertComptagePrUDD = convertComptagePrUDD;
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

    public String getConvertComptagePrUDD() {
        return convertComptagePrUDD;
    }

    public void setConvertComptagePrUDD(String convertComptagePrUDD) {
        this.convertComptagePrUDD = convertComptagePrUDD;
    }

    public String getCoutUCPrPoint() {
        return coutUCPrPoint;
    }

    public void setCoutUCPrPoint(String coutUCPrPoint) {
        this.coutUCPrPoint = coutUCPrPoint;
    }
}
