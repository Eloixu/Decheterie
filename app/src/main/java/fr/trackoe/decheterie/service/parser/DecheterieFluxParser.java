package fr.trackoe.decheterie.service.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.trackoe.decheterie.model.bean.global.DecheterieFluxs;

/**
 * Created by Haocheng on 23/05/2017.
 */

public class DecheterieFluxParser extends JSONParser<DecheterieFluxs> {
    @Override
    protected DecheterieFluxs parseData(Object jso) throws JSONException {
        DecheterieFluxs df = new DecheterieFluxs();

        JSONObject infos = (JSONObject) jso;
        df.setmSuccess(infos.getBoolean("success"));

        if(!df.ismSuccess() && infos.has("message")){
            df.setmError(infos.getString("message"));
        } else {
            JSONArray listeDecheterieFlux = infos.optJSONArray("decheterie_flux");
            if( listeDecheterieFlux != null) {
                for(int i =0; i < listeDecheterieFlux.length(); i++) {

                    JSONObject jobj = ((JSONObject) listeDecheterieFlux.get(i));

                    int idDecheterie   = jobj.has("decheterie_id")  ? jobj.getInt("decheterie_id") : -1;
                    int idFlux         = jobj.has("flux_id")        ? jobj.getInt("flux_id") : -1;

                    df.addDecheterieFlux(idDecheterie, idFlux);
                }
            }
        }

        return df;
    }
}

