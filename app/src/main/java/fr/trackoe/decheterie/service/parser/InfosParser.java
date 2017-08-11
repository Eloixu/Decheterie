package fr.trackoe.decheterie.service.parser;

import fr.trackoe.decheterie.model.bean.global.TabletteInfos;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Remi on 30/11/2015.
 */
public class InfosParser extends JSONParser<TabletteInfos> {
    @Override
    protected TabletteInfos parseData(Object jso) throws JSONException {
        TabletteInfos tabletteInfos = new TabletteInfos();

        JSONObject infos = (JSONObject) jso;

        tabletteInfos.setmSuccess(infos.getBoolean("success"));

        if(!tabletteInfos.ismSuccess()){
            tabletteInfos.setmError(infos.getString("message"));
        } else {
            tabletteInfos.setNomTablette(infos.getString("nom_tablette"));
            //tabletteInfos.setNomClient(infos.getString("nom_op_cl"));
            //tabletteInfos.setClientId(infos.getInt("id_client_op"));
            if(infos.has("id_account") && infos.getString("id_account").matches("-?\\d+(\\.\\d+)?") ) {
                tabletteInfos.setAccountId(infos.getInt("id_account"));
            }
            //tabletteInfos.setIdOperateur(0);
            /*if(infos.has("id_operateur") && infos.getString("id_operateur").matches("-?\\d+(\\.\\d+)?")) {
                try {
                    tabletteInfos.setIdOperateur(infos.getInt("id_operateur"));
                } catch (Exception e) { }
            }*/
//            tabletteInfos.setIdNewInter(infos.getInt("max_id_intervention"));
        }

        return tabletteInfos;
    }
}
