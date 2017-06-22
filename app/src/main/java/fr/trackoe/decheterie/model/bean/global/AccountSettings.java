package fr.trackoe.decheterie.model.bean.global;

import java.util.ArrayList;

/**
 * Created by Haocheng on 24/05/2017.
 */

public class AccountSettings extends ContenantBean {
    private ArrayList<AccountSetting> listAccountSetting;

    public AccountSettings() {
        this.listAccountSetting = new ArrayList<>();
    }

    public ArrayList<AccountSetting> getListAccountSetting() {
        return listAccountSetting;
    }

    public void setListAccountSetting(ArrayList<AccountSetting> listAccountSetting) {
        this.listAccountSetting = listAccountSetting;
    }

    public void addAccountSetting(int id, int dchAccountId, int dchTypeCarteId, int uniteDepotDecheterieId, boolean decompteDepot, boolean decompteUDD, boolean pageSignature, float coutUDDPrPoint, float coutPoint, String unitePoint, String dateDebutParam, String dateFinParam, int dchChoixDecompteTotalId, int nbDepotRestant, boolean compteTotal, float pointMinimum, int nbDepotMinimum) {
        this.listAccountSetting.add(new AccountSetting(id, dchAccountId, dchTypeCarteId, uniteDepotDecheterieId, decompteDepot, decompteUDD, pageSignature, coutUDDPrPoint, coutPoint, unitePoint, dateDebutParam, dateFinParam, dchChoixDecompteTotalId, nbDepotRestant, compteTotal, pointMinimum, nbDepotMinimum));
    }
}
