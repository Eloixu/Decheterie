package fr.trackoe.decheterie.service.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.trackoe.decheterie.model.bean.global.Decheteries;

/**
 * Created by Haocheng on 18/05/2017.
 */

public class DecheterieParser extends JSONParser<Decheteries> {
        @Override
        protected Decheteries parseData(Object jso) throws JSONException {
            Decheteries d = new Decheteries();

            JSONObject infos = (JSONObject) jso;
            d.setmSuccess(infos.getBoolean("success"));

            if(!d.ismSuccess() && infos.has("message")){
                d.setmError(infos.getString("message"));
            } else {
                JSONArray listeDecheterie = infos.optJSONArray("decheterie");
                if( listeDecheterie != null) {
                    for(int i =0; i < listeDecheterie.length(); i++) {

                        JSONObject jobj = ((JSONObject) listeDecheterie.get(i));

                        int id                      = jobj.has("id")                        ? jobj.getInt("id") : -1;
                        int idAccount               = jobj.has("id_dch_account")            ? jobj.getInt("id_dch_account") : -1;
                        String nom                  = jobj.has("nom")                       ? jobj.getString("nom") : "";
                        String consigneComptage     = jobj.has("consigne_comptage")         ? jobj.getString("consigne_comptage") : "";
                        String consigneAvSignature  = jobj.has("consigne_av_signature")     ? jobj.getString("consigne_av_signature") : "";
                        boolean apportFlux          = jobj.has("apport_flux")               ? jobj.getBoolean("apport_flux") : false;

                        d.addDecheterie(id, idAccount, nom, consigneComptage, consigneAvSignature, apportFlux);
                    }
                }
            }

            return d;
        }

}
