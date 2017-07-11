package fr.trackoe.decheterie.service.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.trackoe.decheterie.model.bean.usager.Menages;

/**
 * Created by Remi on 04/05/2017.
 */
public class MenageParser extends JSONParser<Menages> {
    @Override
    protected Menages parseData(Object jso) throws JSONException {
        Menages m = new Menages();

        JSONObject infos = (JSONObject) jso;
        m.setmSuccess(infos.getBoolean("success"));

        if(!m.ismSuccess() && infos.has("message")){
            m.setmError(infos.getString("message"));
        } else {
            JSONArray listeMenage = infos.optJSONArray("menage");
            if( listeMenage != null) {
                for(int i =0; i < listeMenage.length(); i++) {

                    JSONObject jobj = ((JSONObject) listeMenage.get(i));

                    int         id              = jobj.has("id")                ? jobj.getInt("id") : 0;
                    int         idLocal         = jobj.has("id_local")          ? jobj.getInt("id_local") : 0;
                    int         nbHabitant      = jobj.has("nb_habitants")      ? jobj.getInt("nb_habitants") : -1;
                    boolean     isActif         = jobj.has("actif")             ? jobj.getBoolean("actif") : false;
                    String      dateDebut       = jobj.has("date_debut")        ? jobj.getString("date_debut") : "";
                    String      dateFin         = jobj.has("date_fin")          ? jobj.getString("date_fin") : "";
                    boolean     isProprietaire  = jobj.has("is_proprietaire")   ? jobj.getBoolean("is_proprietaire") : false;

                    m.addMenage(id, nbHabitant, isActif, idLocal, dateDebut, dateFin, isProprietaire);
                }
            }
        }

        return m;
    }
}
