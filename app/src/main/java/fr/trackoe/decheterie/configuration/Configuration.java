package fr.trackoe.decheterie.configuration;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fr.trackoe.decheterie.R;
import fr.trackoe.decheterie.model.Const;
import fr.trackoe.decheterie.model.bean.global.AccountSetting;
import fr.trackoe.decheterie.model.bean.global.ApportFlux;
import fr.trackoe.decheterie.model.bean.global.ComptePrepaye;
import fr.trackoe.decheterie.model.bean.global.ComptePrepayes;
import fr.trackoe.decheterie.model.bean.global.Depot;
import fr.trackoe.decheterie.model.bean.global.Ville;
import fr.trackoe.decheterie.model.bean.usager.Habitat;
import fr.trackoe.decheterie.model.bean.usager.Local;
import fr.trackoe.decheterie.model.bean.usager.Locaux;
import fr.trackoe.decheterie.model.bean.usager.Menage;
import fr.trackoe.decheterie.model.bean.usager.Menages;
import fr.trackoe.decheterie.model.bean.usager.Usager;
import fr.trackoe.decheterie.model.bean.usager.UsagerHabitat;
import fr.trackoe.decheterie.model.bean.usager.UsagerHabitats;
import fr.trackoe.decheterie.model.bean.usager.UsagerMenage;
import fr.trackoe.decheterie.model.bean.usager.UsagerMenages;
import fr.trackoe.decheterie.model.bean.usager.Usagers;

/**
 * Created by Remi on 30/11/2015.
 */
public abstract class Configuration {
    private static Configuration applicationConfig;
    private static SharedPreferences params;

    public abstract boolean isProd();

    public static Configuration getInstance(Context ctx) {
        if (ctx != null) {
            applicationConfig = ctx.getResources().getBoolean(R.bool.is_prod) ? new ConfigurationProd() : new ConfigurationTest();
        }
        return applicationConfig;
    }

    /*
	 * Shared Preferences
	 */
    public static void initSharedPreference(Context ctx) {
        params = getSharedPreference(ctx);
    }

    public static SharedPreferences getSharedPreference(Context ctx) {
        return ctx.getSharedPreferences(Const.CONTENANTS_PARAMS, Context.MODE_PRIVATE);
    }

    public static void saveIdDecheterie(int idDecheterie) {
        params.edit().putInt(Const.ID_DECHETERIE, idDecheterie).commit();
    }

    public static void saveNameDecheterie(String nameDecheterie) {
        params.edit().putString(Const.NAME_DECHETERIE, nameDecheterie).commit();
    }

    public static String getNameDecheterie() {
        return params.getString(Const.NAME_DECHETERIE, "");
    }

    public static void saveDateMaj(String dateMaj) {
        params.edit().putString(Const.DATE_MAJ, dateMaj).commit();
    }

    public static void saveLastNumCard(String numCarte) {
        params.edit().putString(Const.LAST_NUM_CARD, numCarte).commit();
    }

    public static String getLastNumCard() {
        return params.getString(Const.LAST_NUM_CARD, "");
    }

    public static Boolean getIsOuiClicked() {
        return params.getBoolean(Const.IS_OUI_CLICKED, false);
    }

    public static void setIsOuiClicked(boolean IsOuiClicked) {
        params.edit().putBoolean(Const.IS_OUI_CLICKED, IsOuiClicked).commit();
    }

    public static int getIdAccount() {
//        return params.getInt(Const.ID_ACCOUNT, -1);
        return 119;
    }

    public static void saveIdAccount(int idAccount) {
        params.edit().putInt(Const.ID_ACCOUNT, idAccount).commit();
    }

    public static void saveIdUser(int idUser) {
        params.edit().putInt(Const.ID_USER, idUser).commit();
    }

    public static String getNameUser() {
        return params.getString(Const.NAME_USER, "");
    }

    public static void saveNameUser(String nameUser) {
        params.edit().putString(Const.NAME_USER, nameUser).commit();
    }

    public static void removeIdUser() {
        params.edit().remove(Const.ID_USER).commit();
    }

    public static String getNumeroTablette() {
        return params.getString(Const.NUMERO_TABLETTE, "");
    }

    public static void saveNumeroTablette(String numeroTablette) {
        params.edit().putString(Const.NUMERO_TABLETTE, numeroTablette).commit();
    }

    public static String getMACAddress() {
        return params.getString(Const.MAC_ADDRESS, "");
    }

    public static void saveMACAddress(String mac) {
        params.edit().putString(Const.MAC_ADDRESS, mac).commit();
    }

    public static String getChecksumVille() {
        return params.getString(Const.CHECKSUM_VILLE, "0");
    }

    public static void saveChecksumVille(String checksum) {
        params.edit().putString(Const.CHECKSUM_VILLE, checksum).commit();
    }

    public static String getChecksumRue() {
        return params.getString(Const.CHECKSUM_RUE, "0");
    }

    public static void saveChecksumRue(String checksum) {
        params.edit().putString(Const.CHECKSUM_RUE, checksum).commit();
    }

    public static String getNomTablette() {
        return params.getString(Const.NOM_TABLETTE, "");
    }

    public static void saveNomTablette(String nomTablette) {
        params.edit().putString(Const.NOM_TABLETTE, nomTablette).commit();
    }

    public static void saveIsApkReadyToInstall(boolean b) {
        params.edit().putBoolean(Const.IS_APK_READY_TO_INSTALL, b).commit();
    }

    public static boolean getIsApkReadyToInstall() {
        return params.getBoolean(Const.IS_APK_READY_TO_INSTALL, false);
    }

    public static String getNomOpCl() {
        return params.getString(Const.NOM_OP_CL, "");
    }

    public static void saveNomOpCl(String nomOpCl) {
        params.edit().putString(Const.NOM_OP_CL, nomOpCl).commit();
    }

    public static void saveIdOpCl(int idOpCl) {
        params.edit().putInt(Const.ID_OP_CL, idOpCl).commit();
    }

    public static String getDateMAJ() {
        //return params.getString(Const.DATE_MAJ_SERVEUR, "");
        return "20170101000000";
    }

    public static void saveDateMAJ(String dateMAJ) {
        params.edit().putString(Const.DATE_MAJ_SERVEUR, dateMAJ).commit();
    }

    public static void saveDateMAJCarte(String dateMAJCarte) {
        params.edit().putString(Const.DATE_MAJ_CARTE, dateMAJCarte).commit();
    }

    public static String getDateMAJCarte() {
        return params.getString(Const.DATE_MAJ_CARTE, "");
    }

    public static String getUrlApk() {
        return params.getString(Const.URL_APK, "");
    }

    public static void saveUrlApk(String urlApk) {
        params.edit().putString(Const.URL_APK, urlApk).commit();
    }

    public static void removeUrlApk() {
        params.edit().remove(Const.URL_APK).commit();
    }

    public static String getDescApk() {
        return params.getString(Const.DESC_APK, "");
    }

    public static void saveDescApk(String descApk) {
        params.edit().putString(Const.DESC_APK, descApk).commit();
    }

    public static void removeDescApk() {
        params.edit().remove(Const.DESC_APK).commit();
    }

    public static boolean getIsInfosTabletteLoaded() {
        return params.getBoolean(Const.IS_WS_INFOS_TABLETTE_LOADED, false);
    }

    public static void saveIsInfosTabletteLoaded(boolean b) {
        params.edit().putBoolean(Const.IS_WS_INFOS_TABLETTE_LOADED, b).commit();
    }

    public static boolean getIsInfosTabletteSuccess() {
        return params.getBoolean(Const.IS_WS_INFOS_TABLETTE_SUCCESS, false);
    }

    public static void saveIsInfosTabletteSuccess(boolean b) {
        params.edit().putBoolean(Const.IS_WS_INFOS_TABLETTE_SUCCESS, b).commit();
    }

    public static String getInfosTabletteError() {
        return params.getString(Const.INFOS_TABLETTE_ERROR, "");
    }

    public static void saveInfosTabletteError(String s) {
        params.edit().putString(Const.INFOS_TABLETTE_ERROR, s).commit();
    }

    public static boolean getIsUsersLoaded() {
        return params.getBoolean(Const.IS_WS_USERS_LOADED, false);
    }

    public static void saveIsUsersLoaded(boolean b) {
        params.edit().putBoolean(Const.IS_WS_USERS_LOADED, b).commit();
    }

    public static boolean getIsUsersSuccess() {
        return params.getBoolean(Const.IS_WS_USERS_SUCCESS, false);
    }

    public static void saveIsUsersSuccess(boolean b) {
        params.edit().putBoolean(Const.IS_WS_USERS_SUCCESS, b).commit();
    }

    public static String getUsersError() {
        return params.getString(Const.USERS_ERROR, "");
    }

    public static void saveUsersError(String s) {
        params.edit().putString(Const.USERS_ERROR, s).commit();
    }

    public static boolean getIsModulesLoaded() {
        return params.getBoolean(Const.IS_MODULES_LOADED, false);
    }

    public static void saveIsModulesLoaded(boolean b) {
        params.edit().putBoolean(Const.IS_MODULES_LOADED, b).commit();
    }

    public static boolean getIsModulesSuccess() {
        return params.getBoolean(Const.IS_MODULES_SUCCESS, false);
    }

    public static void saveIsModulesSuccess(boolean b) {
        params.edit().putBoolean(Const.IS_MODULES_SUCCESS, b).commit();
    }

    public static String getModulesError() {
        return params.getString(Const.MODULES_ERROR, "");
    }

    public static void saveModulesError(String s) {
        params.edit().putString(Const.MODULES_ERROR, s).commit();
    }

    public static boolean getIsProdEnvWS() {
        return params.getBoolean(Const.IS_PROD_ENV_WS, false);
    }

    public static void saveIsProdEnvWS(boolean b) {
        params.edit().putBoolean(Const.IS_PROD_ENV_WS, b).commit();
    }

    /*
    Définition de l'host pour appel aux Webservices
     */
    public String getWebServiceHost(Context ctx) {
        return (isProd() || getIsProdEnvWS()) ? "http://trackoe.fr/android-ws/prod/" + ctx.getString(R.string.ws_version_directory) + "/" : "http://trackoe.fr/android-ws/dev/" + ctx.getString(R.string.ws_version_directory) + "/";
        //return "http://trackoe.fr/android-ws/dev/" + ctx.getString(R.string.ws_version_directory) + "/";
        //return "http://trackoe.fr/android-ws/demo/v3/";
    }

    public String getWebServiceContenantHost(Context ctx) {
        return(isProd()? "" : "http://192.168.1.39:8080/ws/" ) ;
//        return(isProd()? "" : "http://172.20.10.13:8080/ws/" ) ;
//        return(isProd()? "" : "http://localhost:8080/dev/ws/" ) ;
//        return(isProd()? "" : "http://contenant.trackoe.fr/demo/ws/") ;
//        return (isProd() || getIsProdEnvWS()) ? "http://contenant.trackoe.fr/prod/ws/" : "http://contenant.trackoe.fr/dev/ws/";
    }

    //    Récupération des users
    public String getUsersUrl(Context ctx, String numTablette) {
        if (numTablette == null) {
            numTablette = "";
        }

        String mac = getMACAddress();

        try {
            numTablette = URLEncoder.encode(numTablette, "utf-8");
            mac = URLEncoder.encode(mac, "utf-8");
        } catch (UnsupportedEncodingException e) {
        }

        return getWebServiceHost(ctx) + "ws_load_users_from_id_account.php?code_tablette=" + numTablette + "&token=" + mac;
    }

    //    Récupération de l'abonnement via le code tablette
    public String getAbonnementUrl(Context ctx, String numTablette) {
        numTablette = getEncodedParam(numTablette);
        String mac = getEncodedParam(getMACAddress());

        try {
            numTablette =  URLEncoder.encode(numTablette, "utf-8");
            mac = URLEncoder.encode(mac, "utf-8");
        } catch (UnsupportedEncodingException e) {
        }

        return getWebServiceHost(ctx) + "ws_check_decheterie_abonnement.php?code_tablette=" + numTablette + "&token=" + mac;
    }

    //    Récupération des villes
    public String getVillesUrl(Context ctx, String numTablette, String checksum) {
        if (numTablette == null) {
            numTablette = "";
        }

        if (checksum == null) {
            checksum = "";
        }

        String mac = getMACAddress();

        try {
            numTablette = URLEncoder.encode(numTablette, "utf-8");
            checksum = URLEncoder.encode(checksum, "utf-8");
            mac = URLEncoder.encode(mac, "utf-8");
        } catch (UnsupportedEncodingException e) {
        }

        return getWebServiceHost(ctx) + "ws_load_ville.php?code_tablette=" + numTablette + "&token=" + mac + "&checksum=" + checksum;
    }

    //    Récupération des rues
    public String getRuesUrl(Context ctx, ArrayList<Ville> listeVille, String checksum) {
        if (checksum == null) {
            checksum = "";
        }

        String mac = getMACAddress();

        String arrayVille = "";
        if(listeVille != null) {
            for(Ville v : listeVille){
                arrayVille += "&id_ville[]=" + v.getIdVille();
            }
        }

        try {
            checksum = URLEncoder.encode(checksum, "utf-8");
            mac = URLEncoder.encode(mac, "utf-8");
        } catch (UnsupportedEncodingException e) {
        }

        return getWebServiceHost(ctx) + "ws_load_rues.php?token=" + mac + "&checksum=" + checksum + arrayVille;
    }



    //    Récupération des infos liées à la tablette
    public String getInfosUrl(Context ctx, String numTablette) {
        if (numTablette == null) {
            numTablette = "";
        }

        String mac = getMACAddress();

        try {
            numTablette =  URLEncoder.encode(numTablette, "utf-8");
            mac = URLEncoder.encode(mac, "utf-8");
        } catch (UnsupportedEncodingException e) {
        }

        return getWebServiceHost(ctx) + "ws_load_infos.php?code_tablette=" + numTablette + "&token=" + mac;
    }

    //    Récupération des modules
    public String getModulesUrl(Context ctx, String numTablette) {
        if (numTablette == null) {
            numTablette = "";
        }

        String mac = getMACAddress();

        try {
            numTablette = URLEncoder.encode(numTablette, "utf-8");
            mac = URLEncoder.encode(mac, "utf-8");
        } catch (UnsupportedEncodingException e) {
        }

        return getWebServiceHost(ctx) + "ws_load_modules.php?code_tablette=" + numTablette + "&token=" + mac;
    }

    //    Test Url
    public String getTestUrl(Context ctx, int value) {
        return getWebServiceContenantHost(ctx) + "ws_load_value.php?value=" + value;
    }

    public String getTypeHabitatUrl(Context ctx) {
        return getWebServiceContenantHost(ctx) + "wsTypeHabitat";
    }

    public String getAllHabitatUrl(Context ctx, int idAccount) {
        return getWebServiceContenantHost(ctx) + "wsAllHabitat?idAccount=" + idAccount;
    }

    public String getAllLocalUrl(Context ctx, int idAccount) {
        return getWebServiceContenantHost(ctx) + "wsAllLocal?idAccount=" + idAccount;
    }

    public String getAllMenageUrl(Context ctx, int idAccount) {
        return getWebServiceContenantHost(ctx) + "wsAllMenage?idAccount=" + idAccount;
    }

    public String getAllUsagerUrl(Context ctx, int idAccount) {
        return getWebServiceContenantHost(ctx) + "wsAllUsager?idAccount=" + idAccount;
    }

    public String getAllUsagerHabitatUrl(Context ctx, int idAccount) {
        return getWebServiceContenantHost(ctx) + "wsAllUsagerHabitat?idAccount=" + idAccount;
    }

    public String getAllUsagerMenageUrl(Context ctx, int idAccount) {
        return getWebServiceContenantHost(ctx) + "wsAllUsagerMenage?idAccount=" + idAccount;
    }

    public String getAllDecheterieUrl(Context ctx, int idAccount) {
        return getWebServiceContenantHost(ctx) + "wsAllDecheterie?idAccount=" + idAccount;
    }

    public String getTypeCarteUrl(Context ctx) {
        return getWebServiceContenantHost(ctx) + "wsTypeCarte";
    }

    public String getAllCarteUrl(Context ctx, int idAccount) {
        return getWebServiceContenantHost(ctx) + "wsAllCarte?idAccount=" + idAccount;
    }

    public String getAllCarteActiveUrl(Context ctx, int idAccount) {
        return getWebServiceContenantHost(ctx) + "wsAllCarteActive?idAccount=" + idAccount;
    }

    public String getCarteEtatRaisonUrl(Context ctx) {
        return getWebServiceContenantHost(ctx) + "wsCarteEtatRaison";
    }

    public String getAllComptePrepayeUrl(Context ctx, int idAccount) {
        return getWebServiceContenantHost(ctx) + "wsAllComptePrepaye?idAccount=" + idAccount;
    }

    public String getAllFluxUrl(Context ctx, int idAccount) {
        return getWebServiceContenantHost(ctx) + "wsAllFlux?idAccount=" + idAccount;
    }

    public String getAllDecheterieFluxUrl(Context ctx, int idAccount) {
        return getWebServiceContenantHost(ctx) + "wsAllDecheterieFlux?idAccount=" + idAccount;
    }

    public String getAllUniteUrl(Context ctx) {
        return getWebServiceContenantHost(ctx) + "wsAllUnite";
    }

    public String getAllAccountSettingUrl(Context ctx, int idAccount) {
        return getWebServiceContenantHost(ctx) + "wsAllAccountSetting?idAccount=" + idAccount;
    }

    public String getAllAccountFluxSettingUrl(Context ctx, int idAccount) {
        return getWebServiceContenantHost(ctx) + "wsAllAccountFluxSetting?idAccount=" + idAccount;
    }

    public String getChoixDecompteTotalUrl(Context ctx) {
        return getWebServiceContenantHost(ctx) + "wsChoixDecompteTotal";
    }

    public String getAllPrepaiementUrl(Context ctx, int idAccount) {
        return getWebServiceContenantHost(ctx) + "wsAllPrepaiement?idAccount=" + idAccount;
    }

    public String getModePaiementUrl(Context ctx) {
        return getWebServiceContenantHost(ctx) + "wsModePaiement";
    }
    public String getDateMAJCarteUrl(Context ctx, int idAccount) {
        return getWebServiceContenantHost(ctx) + "wsDateMAJCarte?idAccount=" + idAccount;
    }
    public String getMAJUsagerUrl(Context ctx, int idAccount, String dateMAJ) {
        return getWebServiceContenantHost(ctx) + "wsMAJUsager?idAccount=" + idAccount + "&dateMAJ=" + dateMAJ;
    }
    public String getMAJUsagerHabitatUrl(Context ctx, Usagers usagers) {
        ArrayList<Usager> usagerList = usagers.getListUsager();
        String usagerIdList = "";
        int index = 1;
        for(Usager usager: usagerList){
            if(index == 1) {
                usagerIdList = usagerIdList + usager.getId();
            }
            else{
                usagerIdList = usagerIdList + "&usagerIdList=" + usager.getId();
            }
            index ++;
        }
        return getWebServiceContenantHost(ctx) + "wsMAJUsagerHabitat?usagerIdList=" + usagerIdList;
    }
    public String getMAJHabitatUrl(Context ctx, UsagerHabitats usagerHabitats) {
        ArrayList<UsagerHabitat> usagerHabitatList = usagerHabitats.getListUsagerHabitat();
        ArrayList<Integer> habitatIdIntegerList = new ArrayList<>();
        int habitatId = 0;
        for(UsagerHabitat usagerHabitat: usagerHabitatList){
            if(usagerHabitat.getHabitatId() != habitatId){
                habitatId = usagerHabitat.getHabitatId();
                habitatIdIntegerList.add(habitatId);
            }
        }
        String habitatIdList = "";
        int index = 1;
        for(int id : habitatIdIntegerList){
            if(index == 1) {
                habitatIdList = habitatIdList + id;
            }
            else{
                habitatIdList = habitatIdList + "&habitatIdList=" + id;
            }
            index ++;
        }
        return getWebServiceContenantHost(ctx) + "wsMAJHabitat?habitatIdList=" + habitatIdList;
    }
    public String getMAJHabitatUrl(Context ctx, Locaux locaux) {
        ArrayList<Local> localList = locaux.getListLocal();
        String habitatIdList = "";
        int index = 1;
        for(Local local : localList){
            if(index == 1) {
                habitatIdList = habitatIdList + local.getHabitatId();
            }
            else{
                habitatIdList = habitatIdList + "&habitatIdList=" + local.getHabitatId();
            }
            index ++;
        }
        return getWebServiceContenantHost(ctx) + "wsMAJHabitat?habitatIdList=" + habitatIdList;
    }
    public String getMAJUsagerMenageUrl(Context ctx, Usagers usagers) {
        ArrayList<Usager> usagerList = usagers.getListUsager();
        String usagerIdList = "";
        int index = 1;
        for(Usager usager: usagerList){
            if(index == 1) {
                usagerIdList = usagerIdList + usager.getId();
            }
            else{
                usagerIdList = usagerIdList + "&usagerIdList=" + usager.getId();
            }
            index ++;
        }
        return getWebServiceContenantHost(ctx) + "wsMAJUsagerMenage?usagerIdList=" + usagerIdList;
    }
    public String getMAJMenageUrl(Context ctx, UsagerMenages usagerMenages) {
        ArrayList<UsagerMenage> usagerMenageList = usagerMenages.getListUsagerMenage();
        ArrayList<Integer> menageIdIntegerList = new ArrayList<>();
        int menageId = 0;
        for(UsagerMenage usagerMenage: usagerMenageList){
            if(usagerMenage.getMenageId() != menageId){
                menageId = usagerMenage.getMenageId();
                menageIdIntegerList.add(menageId);
            }
        }
        String menageIdList = "";
        int index = 1;
        for(int id : menageIdIntegerList){
            if(index == 1) {
                menageIdList = menageIdList + id;
            }
            else{
                menageIdList = menageIdList + "&menageIdList=" + id;
            }
            index ++;
        }
        return getWebServiceContenantHost(ctx) + "wsMAJMenage?menageIdList=" + menageIdList;
    }
    public String getMAJLocalUrl(Context ctx, Menages menages) {
        ArrayList<Menage> menageList = menages.getListMenage();
        String localIdList = "";
        int index = 1;
        for(Menage menage : menageList){
            if(menage.getLocalId() == 0 || menage.getLocalId() == -1) continue;

            if(index == 1) {
                localIdList = localIdList + menage.getLocalId();
            }
            else{
                localIdList = localIdList + "&localIdList=" + menage.getLocalId();
            }
            index ++;
        }
        return getWebServiceContenantHost(ctx) + "wsMAJLocal?localIdList=" + localIdList;
    }
    public String getMAJComptePrepayeUrl(Context ctx, Usagers usagers) {
        ArrayList<Usager> usagerList = usagers.getListUsager();
        String usagerIdList = "";
        int index = 1;
        for(Usager usager: usagerList){
            if(index == 1) {
                usagerIdList = usagerIdList + usager.getId();
            }
            else{
                usagerIdList = usagerIdList + "&usagerIdList=" + usager.getId();
            }
            index ++;
        }
        return getWebServiceContenantHost(ctx) + "wsMAJComptePrepaye?usagerIdList=" + usagerIdList;
    }
    public String getMAJCarteActiveUrl(Context ctx, ComptePrepayes comptePrepayes) {
        ArrayList<ComptePrepaye> comptePrepayeList = comptePrepayes.getListComptePrepaye();
        String comptePrepayeIdList = "";
        int index = 1;
        for(ComptePrepaye comptePrepaye: comptePrepayeList){
            if(index == 1) {
                comptePrepayeIdList = comptePrepayeIdList + comptePrepaye.getId();
            }
            else{
                comptePrepayeIdList = comptePrepayeIdList + "&comptePrepayeIdList=" + comptePrepaye.getId();
            }
            index ++;
        }
        return getWebServiceContenantHost(ctx) + "wsMAJCarteActive?comptePrepayeIdList=" + comptePrepayeIdList;
    }

    // Url permettant de envoyer le depot au serveur
    public String getDepotUrlSansSignature(Context ctx, Depot depot, AccountSetting accountSetting, ArrayList<ApportFlux> listAF)  throws Exception{
        DateFormat fmt =new SimpleDateFormat(ctx.getString(R.string.db_date_format));
        Date date = fmt.parse(depot.getDateHeure());
        String dateHeureStr = new SimpleDateFormat(ctx.getString(R.string.ws_date_format)).format(date);
        String apportFluxList = "";
        if(listAF != null) {
            for(ApportFlux af : listAF){
                apportFluxList = apportFluxList + "&idFlux=" + af.getFluxId() + "&qtyComptage=" + af.getQtyComptage() + "&qtyUDD=" + af.getQtyUDD();
            }
        }
        return getWebServiceContenantHost(ctx) + "wsAllDepot?" + "nom=" + depot.getNom() + "&dateHeure=" + dateHeureStr + "&decheterieId=" + depot.getDecheterieId() + "&carteActiveCarteId=" + depot.getCarteActiveCarteId() + "&comptePrepayeId=" + depot.getComptePrepayeId() + "&qtyTotalUDD=" + depot.getQtyTotalUDD() +"&accountSettingId=" + accountSetting.getId() + "&apportFluxList=" + apportFluxList;
    }

    public String getEncodedParam(String param) {
        String ret = "";
        if(param != null) {
            try {
                ret = URLEncoder.encode(param, "utf-8");
            } catch (UnsupportedEncodingException e) {
                ret = param;
            }
        }
        return ret;
    }


    //    Url de mise à jour de l'appli
    public String getApkUpdateUrl(Context ctx, int versionCode) {
        return getWebServiceHost(ctx) + "ws_check_app_decheterie_update.php?version_code=" + versionCode;
    }

    public String getUploadImgSignature(Context ctx) {
        return getWebServiceHost(ctx) + "ws_upload_img_signature.php" ;
    }


}
