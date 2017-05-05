package fr.trackoe.decheterie.service.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.trackoe.decheterie.model.bean.usager.Habitats;

/**
 * Created by Remi on 02/05/2017.
 */
public class HabitatsParser extends JSONParser<Habitats> {
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

                    int idHabitat       = jobj.has("id")            ? jobj.getInt("id") : -1;
                    String adresse      = jobj.has("adresse")       ? jobj.getString("adresse") : "";
                    String cp           = jobj.has("cp")            ? jobj.getString("cp") : "";
                    String ville        = jobj.has("ville")         ? jobj.getString("ville") : "";
                    int nbLgt           = jobj.has("nb_lgts")       ? jobj.getInt("nb_lgts") : -1;
                    int nbHabitant      = jobj.has("nb_habitants")  ? jobj.getInt("nb_habitants") : -1;
                    int idAccount       = jobj.has("id_account")    ? jobj.getInt("id_account") : -1;
                    String nom          = jobj.has("nom")           ? jobj.getString("nom") : "";
                    String reference    = jobj.has("reference")     ? jobj.getString("reference") : "";
                    String coordonneesX = jobj.has("coordonneesX")  ? jobj.getString("coordonneesX") : "";
                    String coordonneesY = jobj.has("coordonneesY")  ? jobj.getString("coordonneesY") : "";
                    String complement   = jobj.has("complement")    ? jobj.getString("complement") : "";
                    String dernierMaj   = jobj.has("dernierMaj")    ? jobj.getString("dernierMaj") : "";
                    String numero       = jobj.has("numero")        ? jobj.getString("numero") : "";
                    boolean isActif     = jobj.has("is_actif")      ? jobj.getBoolean("is_actif") : true;
                    String activites    = jobj.has("activites")     ? jobj.getString("activites") : "";
                    String adresse2     = jobj.has("adresse2")      ? jobj.getString("adresse2") : "";
                    String remarque     = jobj.has("remarque")      ? jobj.getString("remarque") : "";
                    int idTypeHabitat   = jobj.has("id_type_habitat") ? jobj.getInt("id_type_habitat") : 1;

                    h.addHabitat(idHabitat, adresse, cp, ville, nbLgt,nbHabitant, idAccount, nom, reference, coordonneesX, coordonneesY,
                            complement, dernierMaj, numero, isActif, activites, adresse2, remarque, idTypeHabitat);
                }
            }
        }

        return h;
    }
}
