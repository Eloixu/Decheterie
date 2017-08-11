package fr.trackoe.decheterie.service.parser;

import fr.trackoe.decheterie.model.bean.global.Modules;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Remi on 30/11/2015.
 */
public class ModulesParser extends JSONParser<Modules> {
    @Override
    protected Modules parseData(Object jso) throws JSONException {
        Modules modules = new Modules();

        JSONObject infos = (JSONObject) jso;
        modules.setmSuccess(infos.getBoolean("success"));

        if(!modules.ismSuccess()){
            modules.setmError(infos.getString("message"));
        } else {
            JSONArray listeModules = infos.optJSONArray("listeModules");
            if( listeModules != null) {
                for(int i =0; i < listeModules.length(); i++) {
                    String idModules = (String) ((JSONObject) listeModules.get(i)).getString("id");
                    String nom = (String) ((JSONObject) listeModules.get(i)).getString("nom");
                    String formulaire = (String) ((JSONObject) listeModules.get(i)).getString("formulaire");
                    String version = (String) ((JSONObject) listeModules.get(i)).getString("version");
                    boolean isForm = ((JSONObject) listeModules.get(i)).getBoolean("is_form");

                    modules.addModule(idModules, nom, formulaire, version, isForm);
                }
            }
        }

        return modules;
    }
}
