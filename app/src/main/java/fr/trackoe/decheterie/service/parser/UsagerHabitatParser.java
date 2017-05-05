package fr.trackoe.decheterie.service.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.trackoe.decheterie.model.bean.usager.UsagerHabitats;
import fr.trackoe.decheterie.model.bean.usager.Usagers;

/**
 * Created by Remi on 04/05/2017.
 */
public class UsagerHabitatParser extends JSONParser<UsagerHabitats> {
    @Override
    protected UsagerHabitats parseData(Object jso) throws JSONException {
        UsagerHabitats u = new UsagerHabitats();

        JSONObject infos = (JSONObject) jso;
        u.setmSuccess(infos.getBoolean("success"));

        if(!u.ismSuccess() && infos.has("message")){
            u.setmError(infos.getString("message"));
        } else {
            JSONArray listeUsagerH = infos.optJSONArray("usager_habitat");
            if( listeUsagerH != null) {
                for(int i =0; i < listeUsagerH.length(); i++) {

                    JSONObject jobj = ((JSONObject) listeUsagerH.get(i));

                    int idUsager   = jobj.has("id_usager")  ? jobj.getInt("id_usager") : -1;
                    int idHabitat  = jobj.has("id_habitat") ? jobj.getInt("id_habitat") : -1;

                    u.addUsagerHabitat(idUsager, idHabitat);
                }
            }
        }

        return u;
    }
}
