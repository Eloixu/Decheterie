package fr.trackoe.decheterie.service.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.trackoe.decheterie.model.bean.usager.Usagers;

/**
 * Created by Remi on 04/05/2017.
 */
public class UsagerParser extends JSONParser<Usagers> {
    @Override
    protected Usagers parseData(Object jso) throws JSONException {
        Usagers u = new Usagers();

        JSONObject infos = (JSONObject) jso;
        u.setmSuccess(infos.getBoolean("success"));

        if(!u.ismSuccess() && infos.has("message")){
            u.setmError(infos.getString("message"));
        } else {
            JSONArray listeUsager = infos.optJSONArray("usager");
            if( listeUsager != null) {
                for(int i =0; i < listeUsager.length(); i++) {

                    JSONObject jobj = ((JSONObject) listeUsager.get(i));

                    int id         = jobj.has("id")         ? jobj.getInt("id") : -1;
                    int idAccount  = jobj.has("id_account") ? jobj.getInt("id_account") : -1;
                    String nom     = jobj.has("nom")        ? jobj.getString("nom") : "";
                    String dateMaj = jobj.has("date_maj")   ? jobj.getString("date_maj") : "";

                    u.addUsager(id, idAccount, nom, dateMaj);
                }
            }
        }

        return u;
    }
}
