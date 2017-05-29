package fr.trackoe.decheterie.service.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.trackoe.decheterie.model.bean.global.Cartes;

/**
 * Created by Haocheng on 19/05/2017.
 */

public class CarteParser extends JSONParser<Cartes> {
    @Override
    protected Cartes parseData(Object jso) throws JSONException {
        Cartes c = new Cartes();

        JSONObject infos = (JSONObject) jso;
        c.setmSuccess(infos.getBoolean("success"));

        if(!c.ismSuccess() && infos.has("message")){
            c.setmError(infos.getString("message"));
        } else {
            JSONArray listeCarte = infos.optJSONArray("carte");
            if( listeCarte != null) {
                for(int i =0; i < listeCarte.length(); i++) {

                    JSONObject jobj = ((JSONObject) listeCarte.get(i));

                    int     id                  = jobj.has("id")            ? jobj.getInt("id") : -1;
                    String numCarte             = jobj.has("num_carte")     ? jobj.getString("num_carte") : "";
                    String numRFID              = jobj.has("num_RFID")      ? jobj.getString("num_RFID") : "";
                    int    dchTypeCarteId       = jobj.has("type_carte_id") ? jobj.getInt("type_carte_id") : -1;
                    int    dchAccountId         = jobj.has("account_id")    ? jobj.getInt("account_id") : -1;

                    c.addCarte(id, numCarte, numRFID, dchTypeCarteId, dchAccountId);
                }
            }
        }

        return c;
    }

}
