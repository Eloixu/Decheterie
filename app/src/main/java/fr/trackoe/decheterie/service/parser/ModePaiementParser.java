package fr.trackoe.decheterie.service.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.trackoe.decheterie.model.bean.global.ModePaiements;

/**
 * Created by Haocheng on 30/05/2017.
 */

public class ModePaiementParser extends JSONParser<ModePaiements> {

    @Override
    protected ModePaiements parseData(Object jso) throws JSONException {
        ModePaiements mp = new ModePaiements();

        JSONObject infos = (JSONObject) jso;
        mp.setmSuccess(infos.getBoolean("success"));

        if(!mp.ismSuccess() && infos.has("message")){
            mp.setmError(infos.getString("message"));
        } else {
            JSONArray listeModePaiement = infos.optJSONArray("mode_paiement");
            if( listeModePaiement != null) {
                for(int i =0; i < listeModePaiement.length(); i++) {

                    JSONObject jobj = ((JSONObject) listeModePaiement.get(i));

                    int     id         = jobj.has("id")         ? jobj.getInt("id") : -1;
                    String  mode       = jobj.has("mode")       ? jobj.getString("mode") : "";

                    mp.addModePaiement(id, mode);
                }
            }
        }

        return mp;
    }

}

