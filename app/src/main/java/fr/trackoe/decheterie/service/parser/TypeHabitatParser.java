package fr.trackoe.decheterie.service.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.trackoe.decheterie.model.bean.global.TypeHabitat;
import fr.trackoe.decheterie.model.bean.global.TypeHabitats;
import fr.trackoe.decheterie.model.bean.global.Users;

/**
 * Created by Remi on 30/11/2015.
 */
public class TypeHabitatParser extends JSONParser<TypeHabitats> {
    @Override
    protected TypeHabitats parseData(Object jso) throws JSONException {
        TypeHabitats th = new TypeHabitats();

        JSONObject infos = (JSONObject) jso;
        th.setmSuccess(infos.getBoolean("success"));

        if(!th.ismSuccess() && infos.has("message")){
            th.setmError(infos.getString("message"));
        } else {
            JSONArray listeTypeHabitat = infos.optJSONArray("habitat");
            if( listeTypeHabitat != null) {
                for(int i =0; i < listeTypeHabitat.length(); i++) {
                    int id;
                    String type;

                    id = ((JSONObject) listeTypeHabitat.get(i)).optInt("id");
                    type = ((JSONObject) listeTypeHabitat.get(i)).getString("type");

                    th.addTypeHabitat(id, type);
                }
            }
        }

        return th;
    }
}
