package fr.trackoe.decheterie.service.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.trackoe.decheterie.Utils;
import fr.trackoe.decheterie.model.bean.global.CarteActives;

/**
 * Created by Haocheng on 22/05/2017.
 */

public class CarteActiveParser extends JSONParser<CarteActives> {
    @Override
    protected CarteActives parseData(Object jso) throws JSONException {
        CarteActives ca = new CarteActives();

        JSONObject infos = (JSONObject) jso;
        ca.setmSuccess(infos.getBoolean("success"));

        if(!ca.ismSuccess() && infos.has("message")){
            ca.setmError(infos.getString("message"));
        } else {
            JSONArray listeCarteActive = infos.optJSONArray("carte_active");
            if( listeCarteActive != null) {
                for(int i =0; i < listeCarteActive.length(); i++) {

                    JSONObject jobj = ((JSONObject) listeCarteActive.get(i));

                    int     carteId             = jobj.has("carte_id")                   ? jobj.getInt("carte_id") : -1;
                    boolean isActive            = jobj.has("is_active")                  ? jobj.getBoolean("is_active") : false;
                    int     carteEtatRaisonId   = jobj.has("carte_etat_raison_id") && !Utils.isStringEmpty(jobj.getString("carte_etat_raison_id"))       ? jobj.getInt("carte_etat_raison_id") : -1;
                    String  dateActivation      = jobj.has("date_activation")            ? jobj.getString("date_activation") : "";
                    String  dateDM              = jobj.has("date_dernier_modif")         ? jobj.getString("date_dernier_modif") : "";
                    int     comptePrepayeId     = jobj.has("compte_prepaye_id")          ? jobj.getInt("compte_prepaye_id") : -1;

                    ca.addCarteActive(carteId, dateActivation, dateDM, carteEtatRaisonId, isActive, comptePrepayeId);
                }
            }
        }

        return ca;
    }

}
