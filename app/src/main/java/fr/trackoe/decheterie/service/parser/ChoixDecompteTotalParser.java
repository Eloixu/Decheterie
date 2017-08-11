package fr.trackoe.decheterie.service.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.trackoe.decheterie.model.bean.global.ChoixDecompteTotals;

/**
 * Created by Haocheng on 24/05/2017.
 */

public class ChoixDecompteTotalParser extends JSONParser<ChoixDecompteTotals> {

    @Override
    protected ChoixDecompteTotals parseData(Object jso) throws JSONException {
        ChoixDecompteTotals cdt = new ChoixDecompteTotals();

        JSONObject infos = (JSONObject) jso;
        cdt.setmSuccess(infos.getBoolean("success"));

        if(!cdt.ismSuccess() && infos.has("message")){
            cdt.setmError(infos.getString("message"));
        } else {
            JSONArray listeChoixDecompteTotal = infos.optJSONArray("choix_decompte_total");
            if( listeChoixDecompteTotal != null) {
                for(int i =0; i < listeChoixDecompteTotal.length(); i++) {

                    JSONObject jobj = ((JSONObject) listeChoixDecompteTotal.get(i));

                    int     id         = jobj.has("id")         ? jobj.getInt("id") : -1;
                    String  nom        = jobj.has("nom")        ? jobj.getString("nom") : "";

                    cdt.addChoixDecompteTotal(id, nom);
                }
            }
        }

        return cdt;
    }

}
