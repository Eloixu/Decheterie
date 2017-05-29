package fr.trackoe.decheterie.model.bean.global;

import java.util.ArrayList;

/**
 * Created by Haocheng on 29/05/2017.
 */

public class AccountFluxSettings extends ContenantBean {
    private ArrayList<AccountFluxSetting> listAccountFluxSetting;

    public AccountFluxSettings() {
        this.listAccountFluxSetting = new ArrayList<>();
    }

    public ArrayList<AccountFluxSetting> getListAccountFluxSetting() {
        return listAccountFluxSetting;
    }

    public void setListAccountFluxSetting(ArrayList<AccountFluxSetting> listAccountFluxSetting) {
        this.listAccountFluxSetting = listAccountFluxSetting;
    }

    public void addAccountFluxSetting(int dchAccountSettingId, int dchFluxId, float convertComptagePrUDD, boolean decompteUDD, boolean decompteComptage, float coutUCPrPoint) {
        this.listAccountFluxSetting.add(new AccountFluxSetting(dchAccountSettingId, dchFluxId, convertComptagePrUDD, decompteUDD, decompteComptage, coutUCPrPoint));
    }
}
