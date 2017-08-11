package fr.trackoe.decheterie.service.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import fr.trackoe.decheterie.model.bean.global.CarteActives;
import fr.trackoe.decheterie.model.bean.global.ComptePrepayes;
import fr.trackoe.decheterie.model.bean.usager.Habitats;
import fr.trackoe.decheterie.model.bean.usager.Locaux;
import fr.trackoe.decheterie.model.bean.usager.Menages;
import fr.trackoe.decheterie.model.bean.usager.UsagerHabitats;
import fr.trackoe.decheterie.model.bean.usager.UsagerMAJs;
import fr.trackoe.decheterie.model.bean.usager.UsagerMenages;
import fr.trackoe.decheterie.model.bean.usager.Usagers;

/**
 * Created by Haocheng on 27/07/2017.
 */

public class UsagerMAJParser extends JSONParser<UsagerMAJs> {
    @Override
    protected UsagerMAJs parseData(Object jso) throws JSONException {
        UsagerMAJs uMAJ = new UsagerMAJs();

        JSONObject infos = (JSONObject) jso;
        uMAJ.setmSuccess(infos.getBoolean("success"));

        if(!uMAJ.ismSuccess() && infos.has("message")){
            uMAJ.setmError(infos.getString("message"));
        } else {
            Usagers usagers = (new UsagerParser()).parseData(jso);
            uMAJ.setUsagers(usagers);

            UsagerHabitats usagerHabitats = (new UsagerHabitatParser()).parseData(jso);
            uMAJ.setUsagerHabitats(usagerHabitats);

            UsagerMenages usagerMenages = (new UsagerMenageParser()).parseData(jso);
            uMAJ.setUsagerMenages(usagerMenages);

            Menages menages = (new MenageParser()).parseData(jso);
            uMAJ.setMenages(menages);

            Locaux locaux = (new LocalParser()).parseData(jso);
            uMAJ.setLocaux(locaux);

            Habitats habitats = (new HabitatParser()).parseData(jso);
            uMAJ.setHabitats(habitats);

            ComptePrepayes comptePrepayes = (new ComptePrepayeParser()).parseData(jso);
            uMAJ.setComptePrepayes(comptePrepayes);

            CarteActives carteActives = (new CarteActiveParser()).parseData(jso);
            uMAJ.setCarteActives(carteActives);
        }

        return uMAJ;
    }

}

