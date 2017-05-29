package fr.trackoe.decheterie.service.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.trackoe.decheterie.model.bean.usager.Locaux;

/**
 * Created by Remi on 02/05/2017.
 */
public class LocalParser extends JSONParser<Locaux> {
    @Override
    protected Locaux parseData(Object jso) throws JSONException {
        Locaux l = new Locaux();

        JSONObject infos = (JSONObject) jso;
        l.setmSuccess(infos.getBoolean("success"));

        if(!l.ismSuccess() && infos.has("message")){
            l.setmError(infos.getString("message"));
        } else {
            JSONArray listeLocal = infos.optJSONArray("local");
            if( listeLocal != null) {
                for(int i =0; i < listeLocal.length(); i++) {

                    JSONObject jobj = ((JSONObject) listeLocal.get(i));

                    int         id                      = jobj.has("id")                    ? jobj.getInt("id") : -1;
                    int         idHabitat               = jobj.has("id_habitat")            ? jobj.getInt("id_habitat") : -1;
                    String      lot                     = jobj.has("lot")                   ? jobj.getString("lot") : "";
                    String      invariantDfip           = jobj.has("invariant_dfip")        ? jobj.getString("invariant_dfip") : "";
                    String      identifiantInterne      = jobj.has("identifiant_interne")   ? jobj.getString("identifiant_interne") : "";
                    String      batiment                = jobj.has("batiment")              ? jobj.getString("batiment") : "";
                    String      etagePorte              = jobj.has("etage_porte")           ? jobj.getString("etage_porte") : "";

                    l.addLocal(id, idHabitat, lot, invariantDfip, identifiantInterne, batiment, etagePorte);
                }
            }
        }

        return l;
    }
}
