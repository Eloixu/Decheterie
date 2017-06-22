package fr.trackoe.decheterie.service.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.trackoe.decheterie.model.bean.global.ComptePrepayes;

/**
 * Created by Haocheng on 22/05/2017.
 */

public class ComptePrepayeParser extends JSONParser<ComptePrepayes> {
    @Override
    protected ComptePrepayes parseData(Object jso) throws JSONException {
        ComptePrepayes cp = new ComptePrepayes();

        JSONObject infos = (JSONObject) jso;
        cp.setmSuccess(infos.getBoolean("success"));

        if(!cp.ismSuccess() && infos.has("message")){
            cp.setmError(infos.getString("message"));
        } else {
            JSONArray listeComptePrepaye = infos.optJSONArray("compte_prepaye");
            if( listeComptePrepaye != null) {
                for(int i =0; i < listeComptePrepaye.length(); i++) {

                    JSONObject jobj = ((JSONObject) listeComptePrepaye.get(i));

                    int     id                  = jobj.has("id")                       ? jobj.getInt("id") : -1;
                    int     usagerId            = jobj.has("id_dch_usager")            ? jobj.getInt("id_dch_usager") : -1;
                    int     nbDepotRestant      = jobj.has("nb_depot_restant")         ? jobj.getInt("nb_depot_restant") : 0;
                    float   qtyPoint            = jobj.has("qty_point")                ? Float.parseFloat(jobj.getString("qty_point")) : 0;

                    cp.addComptePrepaye(id, usagerId, qtyPoint,nbDepotRestant);
                }
            }
        }

        return cp;
    }

}

