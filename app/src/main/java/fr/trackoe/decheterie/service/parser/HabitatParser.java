package fr.trackoe.decheterie.service.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.trackoe.decheterie.model.bean.usager.Habitats;

/**
 * Created by Remi on 02/05/2017.
 */
public class HabitatParser extends JSONParser<Habitats> {
    @Override
    protected Habitats parseData(Object jso) throws JSONException {
        Habitats h = new Habitats();

        JSONObject infos = (JSONObject) jso;
        h.setmSuccess(infos.getBoolean("success"));

        if(!h.ismSuccess() && infos.has("message")){
            h.setmError(infos.getString("message"));
        } else {
            JSONArray listeHabitat = infos.optJSONArray("habitat");
            if( listeHabitat != null) {
                for(int i =0; i < listeHabitat.length(); i++) {

                    JSONObject jobj = ((JSONObject) listeHabitat.get(i));

                    int     idHabitat       = jobj.has("id")                    ? jobj.getInt("id") : null;
                    String  adresse         = jobj.has("adresse")               ? jobj.getString("adresse") : "";
                    String  cp              = jobj.has("cp")                    ? jobj.getString("cp") : "";
                    String  ville           = jobj.has("ville")                 ? jobj.getString("ville") : "";
                    int     nbLgt           = jobj.has("nb_lgts")               ? jobj.getInt("nb_lgts") : null;
                    int     nbHabitant      = jobj.has("nb_habitants")          ? jobj.getInt("nb_habitants") : null;
                    String  reference       = jobj.has("reference")             ? jobj.getString("reference") : "";
                    String  coordonneesX    = jobj.has("coordonneesX")          ? jobj.getString("coordonneesX") : "";
                    String  coordonneesY    = jobj.has("coordonneesY")          ? jobj.getString("coordonneesY") : "";
                    String  complement      = jobj.has("complement")            ? jobj.getString("complement") : "";
                    String  dernierMaj      = jobj.has("dernierMaj")            ? jobj.getString("dernierMaj") : "";
                    String  numero          = jobj.has("numero")                ? jobj.getString("numero") : "";
                    boolean isActif         = jobj.has("is_actif")              ? jobj.getBoolean("is_actif") : null;
                    String  adresse2        = jobj.has("adresse2")              ? jobj.getString("adresse2") : "";
                    String  remarque        = jobj.has("remarque")              ? jobj.getString("remarque") : "";
                    int     idTypeHabitat   = jobj.has("id_type_habitat")       ? jobj.getInt("id_type_habitat") : null;
                    int     idAccount       = jobj.has("id_account")            ? jobj.getInt("id_account") : null;
                    String  date_debut      = jobj.has("date_debut")            ? jobj.getString("date_debut") : "";
                    String  date_fin        = jobj.has("date_fin")              ? jobj.getString("date_fin") : "";

                    h.addHabitat(idHabitat, adresse, cp, ville, nbLgt,nbHabitant, reference, coordonneesX, coordonneesY,
                            complement, dernierMaj, numero, isActif, adresse2, remarque, idTypeHabitat, idAccount, date_debut, date_fin);
                }
            }
        }

        return h;
    }
}
