package fr.trackoe.decheterie.service.parser;

import fr.trackoe.decheterie.model.bean.global.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Remi on 30/11/2015.
 */
public class LoginParser extends JSONParser<User> {
    @Override
    protected User parseData(Object jso) throws JSONException {
        User user = new User();

        JSONObject obj = (JSONObject) jso;

        return user;
    }
}
