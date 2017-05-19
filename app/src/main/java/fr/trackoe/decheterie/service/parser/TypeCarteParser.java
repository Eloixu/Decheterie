package fr.trackoe.decheterie.service.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.trackoe.decheterie.model.bean.global.TypeCarte;
import fr.trackoe.decheterie.model.bean.global.TypeCartes;

/**
 * Created by Haocheng on 19/05/2017.
 */

public class TypeCarteParser extends JSONParser<TypeCartes> {

    @Override
    protected TypeCartes parseData(Object jso) throws JSONException {
        TypeCartes tc = new TypeCartes();

        JSONObject infos = (JSONObject) jso;
        tc.setmSuccess(infos.getBoolean("success"));

        if(!tc.ismSuccess() && infos.has("message")){
            tc.setmError(infos.getString("message"));
        } else {
            JSONArray listeTypeCarte = infos.optJSONArray("type_carte");
            if( listeTypeCarte != null) {
                for(int i =0; i < listeTypeCarte.length(); i++) {

                    JSONObject jobj = ((JSONObject) listeTypeCarte.get(i));

                    int id         = jobj.has("id")         ? jobj.getInt("id") : -1;
                    String nom     = jobj.has("nom")        ? jobj.getString("nom") : "";

                    tc.addTypeCarte(id, nom);
                }
            }
        }

        return tc;
    }

}

