package fr.trackoe.decheterie.service.parser;

import org.json.JSONException;
import org.json.JSONObject;

import fr.trackoe.decheterie.model.bean.global.ApkInfos;

/**
 * Created by Remi on 30/11/2015.
 */
public class ApkInfosParser extends JSONParser<ApkInfos> {
    @Override
    protected ApkInfos parseData(Object jso) throws JSONException {
        ApkInfos apkInfos = new ApkInfos();

        JSONObject infos = (JSONObject) jso;

        apkInfos.setmSuccess(infos.getBoolean("success"));

        if(!apkInfos.ismSuccess()){
            apkInfos.setmError(infos.getString("message"));
        } else {
            apkInfos.setUrl(infos.getString("lien"));
            apkInfos.setDescription(infos.getString("commentaire"));
        }

        return apkInfos;
    }
}
