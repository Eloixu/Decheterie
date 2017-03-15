package fr.trackoe.decheterie.configuration;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

import fr.trackoe.decheterie.R;
import fr.trackoe.decheterie.model.Const;
import fr.trackoe.decheterie.model.bean.global.Ville;

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

    public static void saveIdAccount(int idAccount) {
        params.edit().putInt(Const.ID_ACCOUNT, idAccount).commit();
    }

    public static String getDateMAJ() {
        return params.getString(Const.DATE_MAJ_SERVEUR, "");
    }

    public static void saveDateMAJ(String dateMAJ) {
        params.edit().putString(Const.DATE_MAJ_SERVEUR, dateMAJ).commit();
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

        return getWebServiceHost(ctx) + "ws_load_users.php?code_tablette=" + numTablette + "&token=" + mac;
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

    public String getUploadPhotoUrl(Context ctx) {
        return getWebServiceHost(ctx) + "ws_upload_photo.php" ;
    }


}
