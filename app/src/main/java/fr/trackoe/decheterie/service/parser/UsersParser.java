package fr.trackoe.decheterie.service.parser;

import fr.trackoe.decheterie.model.bean.global.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Remi on 30/11/2015.
 */
public class UsersParser extends JSONParser<Users> {
    @Override
    protected Users parseData(Object jso) throws JSONException {
        Users users = new Users();

        JSONObject infos = (JSONObject) jso;
        users.setmSuccess(infos.getBoolean("success"));

        if(!users.ismSuccess()){
            users.setmError(infos.getString("message"));
        } else {
            JSONArray listeUsers = infos.optJSONArray("listeUsers");
            if( listeUsers != null) {
                for(int i =0; i < listeUsers.length(); i++) {
                    String idUser;
                    String login;
                    String password;
                    String nom;
                    String prenom;
                    boolean isAutorisationChgtInter = false;

                    idUser = ((JSONObject) listeUsers.get(i)).getString("id");
                    login = ((JSONObject) listeUsers.get(i)).getString("login");
                    password = ((JSONObject) listeUsers.get(i)).getString("password");
                    nom = ((JSONObject) listeUsers.get(i)).getString("nom");
                    prenom = ((JSONObject) listeUsers.get(i)).getString("prenom");
                    if(((JSONObject) listeUsers.get(i)).has("autorisation_chgt_inter")) {
                        isAutorisationChgtInter = ((JSONObject) listeUsers.get(i)).getBoolean("autorisation_chgt_inter");
                    }

                    users.addUser(idUser, login, password, nom, prenom, isAutorisationChgtInter);
                }
            }
        }

        return users;
    }
}
