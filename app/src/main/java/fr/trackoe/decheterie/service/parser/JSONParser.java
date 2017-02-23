package fr.trackoe.decheterie.service.parser;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;

import fr.trackoe.decheterie.BuildConfig;
import fr.trackoe.decheterie.Logger;
import fr.trackoe.decheterie.Utils;

/**
 * Cette classe est un extrait du JSONParser Json de Unibail V2
 *
 * @param <T>
 */
public abstract class JSONParser<T> implements IParser<T> {

    protected static Logger logger = Logger.getLogger(JSONParser.class);

    // Créer deux méthodes parseData(JSONObject obj) et parseData(JSONArray array) afin d'éviter de faire des
    // casts et appel de instanceof
    protected abstract T parseData(Object jso) throws JSONException;

    /**
     * Cette Methode n'est sense etre appelle que par une instance de AfterProcessing du URCache
     *
     * @param stream l'Inputsream correspondant au flux JSON
     * @return
     * @throws JSONException lance une exception en cas de probleme lors de la creation de l'objet JSON ou lors du parsing
     */
    public T parse(InputStream stream) throws Exception {
        return parse(Utils.convertStreamToString(stream));
    }

    public T parse(String stream) throws JSONException {
        Object jso = getJSonTokenerObject(stream);
        if (jso != null && (jso instanceof JSONObject || jso instanceof JSONArray)) {
            return parseData(jso);
        }
        throw new JSONException("");
    }

    public String parseError(String stream) throws JSONException {
        Object jso = getJSonTokenerObject(stream);
        JSONObject obj;
        if (jso != null && (jso instanceof JSONObject || jso instanceof JSONArray)) {
            obj = (JSONObject) jso;
            if (obj.has("code") && obj.has("message"))
                return obj.getString("message");
            else {
                throw new JSONException("");
            }
        } else {
            throw new JSONException("");
        }
    }

    protected Object getJSonTokenerObject(String jsonString) {
        if (jsonString == null)
            return null;
        try {
            return new JSONTokener(jsonString).nextValue();
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                logger.e("", e);
            }
        }
        return null;
    }

    protected JSONObject getJSONObject(JSONObject json, String key) {
        return getJSONObject(json, key, true);
    }

    protected JSONObject getJSONObject(JSONObject json, String key, boolean mandatory) {
        if (json == null || key == null)
            return null;
        try {
            return (json.has(key)) ? json.getJSONObject(key) : null;
        } catch (Exception e) {
            if (!mandatory) {
                return null;
            }
            if (BuildConfig.DEBUG) {
                logger.e("Cannot parse to JSONObject: " + key, e);
            }
        }
        return null;
    }

    protected JSONArray getJSONArray(JSONObject json, String key) {
        return getJSONArray(json, key, true);
    }

    protected JSONArray getJSONArray(JSONObject json, String key, boolean mandatory) {
        if (json == null || key == null)
            return null;
        try {
            return (json.has(key)) ? json.getJSONArray(key) : null;
        } catch (Exception e) {
            if (!mandatory) {
                return null;
            }
            if (BuildConfig.DEBUG) {
                logger.e("Cannot parse to JSONArray: " + key, e);
            }
        }
        return null;
    }

    protected String getString(JSONObject jso, String key, boolean mandatory, String defaultValue) throws JSONException {
        if (!jso.has(key)) {
            if (!mandatory) {
                return defaultValue;
            }
            throw new JSONException("Clé " + key + " absente alors qu'obligatoire. Flux : " + jso.toString());
        }
        String result;
        try {
            if (jso.isNull(key)) {
                result = null;
            } else {
                result = jso.getString(key);
                if (result != null) {
                    result = result.trim();
                }
            }
        } catch (JSONException e) {
            if (mandatory) {
                throw e;
            } else {
                return defaultValue;
            }
        }
        if (Utils.isStringEmpty(result) && mandatory) {
            throw new JSONException("Clé " + key + " vide alors qu'obligatoire. Flux : " + jso.toString());
        }
        return result;
    }

    protected boolean getBoolean(JSONObject jso, String key, boolean mandatory, boolean defaultValue) throws JSONException {

        if (!jso.has(key)) {
            if (!mandatory) {
                return defaultValue;
            }
            throw new JSONException("Clé " + key + " absente alors qu'obligatoire. Flux : " + jso.toString());
        }
        try {
            return jso.getBoolean(key);
        } catch (JSONException e) {
            if (mandatory) {
                throw e;
            } else {
                return defaultValue;
            }
        }
    }

    protected int getInt(JSONObject jso, String key, boolean mandatory, int defaultValue) throws JSONException {

        if (!jso.has(key)) {
            if (!mandatory) {
                return defaultValue;
            }
            throw new JSONException("Clé " + key + " absente alors qu'obligatoire. Flux : " + jso.toString());
        }
        try {
            return jso.getInt(key);
        } catch (JSONException e) {
            if (mandatory) {
                throw e;
            } else {
                return defaultValue;
            }
        }
    }

    protected long getLong(JSONObject jso, String key, boolean mandatory, long defaultValue) throws JSONException {
        if (!jso.has(key)) {
            if (!mandatory) {
                return defaultValue;
            }
            throw new JSONException("Clé " + key + " absente alors qu'obligatoire. Flux : " + jso.toString());
        }
        try {
            return jso.getLong(key);
        } catch (JSONException e) {
            if (mandatory) {
                throw e;
            } else {
                return defaultValue;
            }
        }
    }

    protected Double getDouble(JSONObject jso, String key, boolean mandatory, Double defaultValue) throws JSONException {
        if (!jso.has(key)) {
            if (!mandatory) {
                return defaultValue;
            }
            throw new JSONException("Clé " + key + " absente alors qu'obligatoire. Flux : " + jso.toString());
        }
        try {
            return jso.getDouble(key);
        } catch (JSONException e) {
            if (mandatory) {
                throw e;
            } else {
                return defaultValue;
            }
        }
    }

}
