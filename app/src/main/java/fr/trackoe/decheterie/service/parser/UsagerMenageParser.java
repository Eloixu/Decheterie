package fr.trackoe.decheterie.service.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.trackoe.decheterie.model.bean.usager.UsagerHabitats;
import fr.trackoe.decheterie.model.bean.usager.UsagerMenages;

/**
 * Created by Remi on 04/05/2017.
 */
public class UsagerMenageParser extends JSONParser<UsagerMenages> {
    @Override
    protected UsagerMenages parseData(Object jso) throws JSONException {
        UsagerMenages u = new UsagerMenages();

        JSONObject infos = (JSONObject) jso;
        u.setmSuccess(infos.getBoolean("success"));

        if(!u.ismSuccess() && infos.has("message")){
            u.setmError(infos.getString("message"));
        } else {
            JSONArray listeUsagerH = infos.optJSONArray("usager_menages");
            if( listeUsagerH != null) {
                for(int i =0; i < listeUsagerH.length(); i++) {

                    JSONObject jobj = ((JSONObject) listeUsagerH.get(i));

                    int idUsager   = jobj.has("id_usager")  ? jobj.getInt("id_usager") : -1;
                    int idMenage   = jobj.has("id_menage")  ? jobj.getInt("id_menage") : -1;

                    u.addUsagerMenage(idUsager, idMenage);
                }
            }
        }

        return u;
    }
}
