package fr.trackoe.decheterie.service.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.trackoe.decheterie.model.bean.global.CarteEtatRaisons;

/**
 * Created by Haocheng on 22/05/2017.
 */

public class CarteEtatRaisonParser extends JSONParser<CarteEtatRaisons> {

    @Override
    protected CarteEtatRaisons parseData(Object jso) throws JSONException {
        CarteEtatRaisons cer = new CarteEtatRaisons();

        JSONObject infos = (JSONObject) jso;
        cer.setmSuccess(infos.getBoolean("success"));

        if(!cer.ismSuccess() && infos.has("message")){
            cer.setmError(infos.getString("message"));
        } else {
            JSONArray listeCarteEtatRaison = infos.optJSONArray("carte_etat_raison");
            if( listeCarteEtatRaison != null) {
                for(int i =0; i < listeCarteEtatRaison.length(); i++) {

                    JSONObject jobj = ((JSONObject) listeCarteEtatRaison.get(i));

                    int     id            = jobj.has("id")            ? jobj.getInt("id") : -1;
                    String raison         = jobj.has("raison")        ? jobj.getString("raison") : "";

                    cer.addCarteEtatRaison(id, raison);
                }
            }
        }

        return cer;
    }

}
