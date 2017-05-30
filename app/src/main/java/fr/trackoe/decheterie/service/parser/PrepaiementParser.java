package fr.trackoe.decheterie.service.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.trackoe.decheterie.model.bean.global.Prepaiements;

/**
 * Created by Haocheng on 30/05/2017.
 */

public class PrepaiementParser extends JSONParser<Prepaiements> {
    @Override
    protected Prepaiements parseData(Object jso) throws JSONException {
        Prepaiements p = new Prepaiements();

        JSONObject infos = (JSONObject) jso;
        p.setmSuccess(infos.getBoolean("success"));

        if(!p.ismSuccess() && infos.has("message")){
            p.setmError(infos.getString("message"));
        } else {
            JSONArray listePrepaiement = infos.optJSONArray("prepaiement");
            if( listePrepaiement != null) {
                for(int i =0; i < listePrepaiement.length(); i++) {

                    JSONObject jobj = ((JSONObject) listePrepaiement.get(i));

                    int     id                    = jobj.has("id")                    ? jobj.getInt("id") : -1;
                    String  date                  = jobj.has("date")                  ? jobj.getString("date") : "";
                    Float   qtyPointPrepaye       = jobj.has("qty_point_prepaye")     ? Float.parseFloat(jobj.getString("qty_point_prepaye")) : -1;
                    Float   coutsHT               = jobj.has("couts_HT")              ? Float.parseFloat(jobj.getString("couts_HT")) : -1;
                    Float   coutsTVA              = jobj.has("couts_TVA")             ? Float.parseFloat(jobj.getString("couts_TVA")) : -1;
                    Float   coutsTTC              = jobj.has("couts_TTC")             ? Float.parseFloat(jobj.getString("couts_TTC")) : -1;
                    int     idModePaiement        = jobj.has("id_mode_paiement")      ? jobj.getInt("id_mode_paiement") : -1;
                    int     idComptePrepaye       = jobj.has("id_compte_prepaye")     ? jobj.getInt("id_compte_prepaye") : -1;

                    p.addPrepaiement(id, date, qtyPointPrepaye, coutsHT, coutsTVA, coutsTTC, idModePaiement, idComptePrepaye);
                }
            }
        }

        return p;
    }

}
