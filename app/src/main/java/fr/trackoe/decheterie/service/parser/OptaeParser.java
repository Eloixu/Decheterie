package fr.trackoe.decheterie.service.parser;

import org.json.JSONException;
import org.json.JSONObject;

import fr.trackoe.decheterie.model.bean.global.ContenantBean;

/**
 * Created by Remi on 17/12/2015.
 */
public class OptaeParser extends JSONParser<ContenantBean> {
    @Override
    protected ContenantBean parseData(Object jso) throws JSONException {
        ContenantBean cb = new ContenantBean();

        JSONObject infos = (JSONObject) jso;
        cb.setmSuccess(infos.getBoolean("success"));

        if(!cb.ismSuccess()) {
             cb.setmError(infos.getString("message"));
        }

        return cb;
    }
}
