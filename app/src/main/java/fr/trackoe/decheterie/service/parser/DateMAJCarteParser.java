package fr.trackoe.decheterie.service.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.trackoe.decheterie.model.bean.global.ContenantBean;
import fr.trackoe.decheterie.model.bean.global.DateMAJCarte;

/**
 * Created by Haocheng on 11/07/2017.
 */

public class DateMAJCarteParser extends JSONParser<DateMAJCarte> {
    @Override
    protected DateMAJCarte parseData(Object jso) throws JSONException {
        DateMAJCarte c = new DateMAJCarte();

        JSONObject infos = (JSONObject) jso;
        c.setmSuccess(infos.getBoolean("success"));

        if(!c.ismSuccess() && infos.has("message")){
            c.setmError(infos.getString("message"));
        } else {
            JSONObject jobj = infos.optJSONObject("dch_account");
            if( jobj != null) {

                    String date = jobj.has("date_maj_carte")? jobj.getString("date_maj_carte") : "";

                    c.setDateMAJCarte(date);
                }
            }

        return c;
    }

}