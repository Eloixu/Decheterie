package fr.trackoe.decheterie.service.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.trackoe.decheterie.model.bean.global.AccountFluxSettings;

/**
 * Created by Haocheng on 29/05/2017.
 */

public class AccountFluxSettingParser extends JSONParser<AccountFluxSettings> {
    @Override
    protected AccountFluxSettings parseData(Object jso) throws JSONException {
        AccountFluxSettings afs = new AccountFluxSettings();

        JSONObject infos = (JSONObject) jso;
        afs.setmSuccess(infos.getBoolean("success"));

        if(!afs.ismSuccess() && infos.has("message")){
            afs.setmError(infos.getString("message"));
        } else {
            JSONArray listeAccountSetting = infos.optJSONArray("account_flux_setting");
            if( listeAccountSetting != null) {
                for(int i =0; i < listeAccountSetting.length(); i++) {

                    JSONObject jobj = ((JSONObject) listeAccountSetting.get(i));

                    int     dchAccountSettingId     = jobj.has("id_account_setting")                ? jobj.getInt("id_account_setting") : -1;
                    int     dchFluxId               = jobj.has("id_flux")                           ? jobj.getInt("id_flux") : -1;
                    float   convertComptagePrUDD    = jobj.has("convert_comptage_pr_udd")           ? Float.parseFloat(jobj.getString("convert_comptage_pr_udd")) : -1;
                    boolean decompteUDD             = jobj.has("decompte_udd")                      ? jobj.getBoolean("decompte_udd") : false;
                    boolean decompteComptage        = jobj.has("decompte_comptage")                 ? jobj.getBoolean("decompte_comptage") : false;
                    float   coutUCPrPoint           = jobj.has("cout_uc_pr_point")                  ? Float.parseFloat(jobj.getString("cout_uc_pr_point")) : -1;

                    afs.addAccountFluxSetting(dchAccountSettingId, dchFluxId, convertComptagePrUDD, decompteUDD, decompteComptage, coutUCPrPoint);
                }
            }
        }

        return afs;
    }

}
