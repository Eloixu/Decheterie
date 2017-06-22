package fr.trackoe.decheterie.service.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.trackoe.decheterie.model.bean.usager.Usagers;

/**
 * Created by Remi on 04/05/2017.
 */
public class UsagerParser extends JSONParser<Usagers> {
    @Override
    protected Usagers parseData(Object jso) throws JSONException {
        Usagers u = new Usagers();

        JSONObject infos = (JSONObject) jso;
        u.setmSuccess(infos.getBoolean("success"));

        if(!u.ismSuccess() && infos.has("message")){
            u.setmError(infos.getString("message"));
        } else {
            JSONArray listeUsager = infos.optJSONArray("usager");
            if( listeUsager != null) {
                for(int i =0; i < listeUsager.length(); i++) {

                    JSONObject jobj = ((JSONObject) listeUsager.get(i));

                    int     id              = jobj.has("id")            ? jobj.getInt("id") : null;
                    int     idAccount       = jobj.has("id_account")    ? jobj.getInt("id_account") : null;
                    String  nom             = jobj.has("nom")           ? jobj.getString("nom") : "";
                    String  dateMaj         = jobj.has("date_maj")      ? jobj.getString("date_maj") : "";
                    String  prenom          = jobj.has("prenom")        ? jobj.getString("prenom") : "";
                    String  email           = jobj.has("email")         ? jobj.getString("email") : "";
                    String  civilite        = jobj.has("civilite")      ? jobj.getString("civilite") : "";
                    String  reference       = jobj.has("reference")     ? jobj.getString("reference") : "";
                    String  raisonSociale   = jobj.has("raison_sociale")? jobj.getString("raison_sociale") : "";
                    String  activite        = jobj.has("activite")      ? jobj.getString("activite") : "";
                    String  telephone1      = jobj.has("telephone1")    ? jobj.getString("telephone1") : "";
                    String  telephone2      = jobj.has("telephone2")    ? jobj.getString("telephone2") : "";
                    String  password        = jobj.has("password")      ? jobj.getString("password") : "";
                    String  commentaire     = jobj.has("commentaire")   ? jobj.getString("commentaire") : "";
                    boolean isActif         = jobj.has("is_actif")      ? jobj.getBoolean("is_actif") : true;
                    String  siren           = jobj.has("siren")         ? jobj.getString("siren") : "";
                    String  siret           = jobj.has("siret")         ? jobj.getString("siret") : "";
                    String  codeApe         = jobj.has("code_ape")      ? jobj.getString("code_ape") : "";
                    String  soumisRS        = jobj.has("soumis_rs")     ? jobj.getString("soumis_rs") : "";

                    u.addUsager(id, idAccount, nom, dateMaj, prenom, email, civilite, reference, raisonSociale, activite,
                            telephone1, telephone2, password, commentaire, isActif, siren, siret, codeApe, soumisRS);
                }
            }
        }

        return u;
    }
}
