package fr.trackoe.decheterie.service.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.trackoe.decheterie.model.bean.global.AccountSettings;

/**
 * Created by Haocheng on 24/05/2017.
 */

public class AccountSettingParser extends JSONParser<AccountSettings> {
    @Override
    protected AccountSettings parseData(Object jso) throws JSONException {
        AccountSettings as = new AccountSettings();

        JSONObject infos = (JSONObject) jso;
        as.setmSuccess(infos.getBoolean("success"));

        if(!as.ismSuccess() && infos.has("message")){
            as.setmError(infos.getString("message"));
        } else {
            JSONArray listeAccountSetting = infos.optJSONArray("account_setting");
            if( listeAccountSetting != null) {
                for(int i =0; i < listeAccountSetting.length(); i++) {

                    JSONObject jobj = ((JSONObject) listeAccountSetting.get(i));

                    int     id                      = jobj.has("id")                                ? jobj.getInt("id") : -1;
                    int     idAccount               = jobj.has("id_account")                        ? jobj.getInt("id_account") : -1;
                    int     idTypeCarte             = jobj.has("id_type_carte")                     ? jobj.getInt("id_type_carte") : -1;
                    int     idUDD                   = jobj.has("id_unite_depot_decheterie")         ? jobj.getInt("id_unite_depot_decheterie") : -1;
                    boolean decompteDepot           = jobj.has("decompte_depot")                    ? jobj.getBoolean("decompte_depot") : false;
                    boolean decompteUDD             = jobj.has("decompte_udd")                      ? jobj.getBoolean("decompte_udd") : false;
                    boolean pageSignature           = jobj.has("page_signature")                    ? jobj.getBoolean("page_signature") : false;
                    float   coutUDDPrPoint          = jobj.has("cout_udd_pr_point")                 ? Float.parseFloat(jobj.getString("cout_udd_pr_point")) : -1;
                    float   coutPoint               = jobj.has("cout_point")                        ? Float.parseFloat(jobj.getString("cout_point")) : -1;
                    String  unitePoint              = jobj.has("unite_point")                       ? jobj.getString("unite_point") : "";
                    String  dateDebutParam          = jobj.has("date_debut_param")                  ? jobj.getString("date_debut_param") : "";
                    String  dateFinParam            = jobj.has("date_fin_param")                    ? jobj.getString("date_fin_param") : "";
                    int     idChoixDecompteTotal    = jobj.has("id_choix_decompte_total")           ? jobj.getInt("id_choix_decompte_total") : -1;
                    int     nbDepotRestant          = jobj.has("nb_depot_restant")                  ? jobj.getInt("nb_depot_restant") : -1;
                    boolean compteTotal             = jobj.has("compte_total")                      ? jobj.getBoolean("compte_total") : false;
                    float   pointMinimum            = jobj.has("point_minimum")                     ? Float.parseFloat(jobj.getString("point_minimum")) : -1;
                    int     nbDepotMinimum          = jobj.has("nb_depot_minimum")                  ? jobj.getInt("nb_depot_minimum") : 0;

                    as.addAccountSetting(id, idAccount, idTypeCarte, idUDD, decompteDepot, decompteUDD, pageSignature, coutUDDPrPoint, coutPoint, unitePoint, dateDebutParam, dateFinParam, idChoixDecompteTotal, nbDepotRestant, compteTotal, pointMinimum, nbDepotMinimum);
                }
            }
        }

        return as;
    }

}
