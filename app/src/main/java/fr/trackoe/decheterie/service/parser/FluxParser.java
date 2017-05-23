package fr.trackoe.decheterie.service.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.trackoe.decheterie.model.bean.global.Fluxs;

/**
 * Created by Haocheng on 23/05/2017.
 */

public class FluxParser extends JSONParser<Fluxs> {
    @Override
    protected Fluxs parseData(Object jso) throws JSONException {
        Fluxs f = new Fluxs();

        JSONObject infos = (JSONObject) jso;
        f.setmSuccess(infos.getBoolean("success"));

        if(!f.ismSuccess() && infos.has("message")){
            f.setmError(infos.getString("message"));
        } else {
            JSONArray listeFlux = infos.optJSONArray("flux");
            if( listeFlux != null) {
                for(int i =0; i < listeFlux.length(); i++) {

                    JSONObject jobj = ((JSONObject) listeFlux.get(i));

                    int id                      = jobj.has("id")                        ? jobj.getInt("id") : -1;
                    int idAccount               = jobj.has("id_dch_account")            ? jobj.getInt("id_dch_account") : -1;
                    String nom                  = jobj.has("nom")                       ? jobj.getString("nom") : "";
                    int idIcon                  = jobj.has("id_icon")                   ? jobj.getInt("id_icon") : -1;
                    int idUniteComptage         = jobj.has("id_unite_comptage")         ? jobj.getInt("id_unite_comptage") : -1;

                    f.addFlux(id, nom, idIcon, idUniteComptage, idAccount);
                }
            }
        }

        return f;
    }

}
