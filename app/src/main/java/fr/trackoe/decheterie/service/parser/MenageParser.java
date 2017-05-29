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

                    int         id              = jobj.has("id")            ? jobj.getInt("id") : -1;
                    int         idLocal         = jobj.has("id_local")      ? jobj.getInt("id_local") : -1;
                    String      nom             = jobj.has("nom")           ? jobj.getString("nom") : "";
                    String      prenom          = jobj.has("prenom")        ? jobj.getString("prenom") : "";
                    String      email           = jobj.has("email")         ? jobj.getString("email") : "";
                    int         nbHabitant      = jobj.has("nb_habitants")  ? jobj.getInt("nb_habitants") : -1;
                    String      reference       = jobj.has("reference")     ? jobj.getString("reference") : "";
                    boolean     isActif         = jobj.has("actif")         ? jobj.getBoolean("actif") : true;
                    String      telephone       = jobj.has("telephone")     ? jobj.getString("telephone") : "";
                    String      civilite        = jobj.has("civilite")      ? jobj.getString("civilite") : "";

                    m.addMenage(id, nom, prenom, email, nbHabitant, reference, isActif, telephone, civilite, idLocal);
                }
            }
        }

        return m;
    }
}
