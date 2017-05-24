package fr.trackoe.decheterie.service.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.trackoe.decheterie.model.bean.global.Unites;

/**
 * Created by Haocheng on 24/05/2017.
 */

public class UniteParser extends JSONParser<Unites> {
    @Override
    protected Unites parseData(Object jso) throws JSONException {
        Unites u = new Unites();

        JSONObject infos = (JSONObject) jso;
        u.setmSuccess(infos.getBoolean("success"));

        if(!u.ismSuccess() && infos.has("message")){
            u.setmError(infos.getString("message"));
        } else {
            JSONArray listeUnite = infos.optJSONArray("unite");
            if( listeUnite != null) {
                for(int i =0; i < listeUnite.length(); i++) {

                    JSONObject jobj = ((JSONObject) listeUnite.get(i));

                    int id                      = jobj.has("id")                        ? jobj.getInt("id") : -1;
                    String nom                  = jobj.has("nom")                       ? jobj.getString("nom") : "";

                    u.addUnite(id, nom);
                }
            }
        }

        return u;
    }

}
