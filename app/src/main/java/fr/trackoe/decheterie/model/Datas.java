package fr.trackoe.decheterie.model;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import fr.trackoe.decheterie.configuration.Configuration;
import fr.trackoe.decheterie.model.bean.global.AccountFluxSettings;
import fr.trackoe.decheterie.model.bean.global.AccountSettings;
import fr.trackoe.decheterie.model.bean.global.ApkInfos;
import fr.trackoe.decheterie.model.bean.global.CarteActives;
import fr.trackoe.decheterie.model.bean.global.CarteEtatRaisons;
import fr.trackoe.decheterie.model.bean.global.Cartes;
import fr.trackoe.decheterie.model.bean.global.ChoixDecompteTotals;
import fr.trackoe.decheterie.model.bean.global.ComptePrepayes;
import fr.trackoe.decheterie.model.bean.global.ContenantBean;
import fr.trackoe.decheterie.model.bean.global.DecheterieFluxs;
import fr.trackoe.decheterie.model.bean.global.Decheteries;
import fr.trackoe.decheterie.model.bean.global.Fluxs;
import fr.trackoe.decheterie.model.bean.global.Modules;
import fr.trackoe.decheterie.model.bean.global.TabletteInfos;
import fr.trackoe.decheterie.model.bean.global.TypeCartes;
import fr.trackoe.decheterie.model.bean.global.TypeHabitats;
import fr.trackoe.decheterie.model.bean.global.Unites;
import fr.trackoe.decheterie.model.bean.global.Users;
import fr.trackoe.decheterie.model.bean.usager.Habitats;
import fr.trackoe.decheterie.model.bean.usager.Locaux;
import fr.trackoe.decheterie.model.bean.usager.Menages;
import fr.trackoe.decheterie.model.bean.usager.UsagerHabitats;
import fr.trackoe.decheterie.model.bean.usager.UsagerMenages;
import fr.trackoe.decheterie.model.bean.usager.Usagers;
import fr.trackoe.decheterie.service.cache.CacheConst;
import fr.trackoe.decheterie.service.cache.URCache;
import fr.trackoe.decheterie.service.callback.DataAndErrorCallback;
import fr.trackoe.decheterie.service.callback.DataCallback;
import fr.trackoe.decheterie.service.parser.AccountFluxSettingParser;
import fr.trackoe.decheterie.service.parser.AccountSettingParser;
import fr.trackoe.decheterie.service.parser.ApkInfosParser;
import fr.trackoe.decheterie.service.parser.CarteActiveParser;
import fr.trackoe.decheterie.service.parser.CarteEtatRaisonParser;
import fr.trackoe.decheterie.service.parser.CarteParser;
import fr.trackoe.decheterie.service.parser.ChoixDecompteTotalParser;
import fr.trackoe.decheterie.service.parser.ComptePrepayeParser;
import fr.trackoe.decheterie.service.parser.DecheterieFluxParser;
import fr.trackoe.decheterie.service.parser.DecheterieParser;
import fr.trackoe.decheterie.service.parser.FluxParser;
import fr.trackoe.decheterie.service.parser.HabitatsParser;
import fr.trackoe.decheterie.service.parser.InfosParser;
import fr.trackoe.decheterie.service.parser.LocalParser;
import fr.trackoe.decheterie.service.parser.MenageParser;
import fr.trackoe.decheterie.service.parser.ModulesParser;
import fr.trackoe.decheterie.service.parser.OptaeParser;
import fr.trackoe.decheterie.service.parser.TypeCarteParser;
import fr.trackoe.decheterie.service.parser.TypeHabitatParser;
import fr.trackoe.decheterie.service.parser.UniteParser;
import fr.trackoe.decheterie.service.parser.UsagerHabitatParser;
import fr.trackoe.decheterie.service.parser.UsagerMenageParser;
import fr.trackoe.decheterie.service.parser.UsagerParser;
import fr.trackoe.decheterie.service.parser.UsersParser;

/**
 * Created by Remi on 30/11/2015.
 */
public class Datas {
    public static void loadUploadApk(Context ctx, DataCallback<ApkInfos> callback, int versionCode) {
        String url = Configuration.getInstance(ctx).getApkUpdateUrl(ctx, versionCode);
        URCache.getFlux(ctx, url, CacheConst.CACHE_HOME_TIMEOUT, callback, new ApkInfosParser());
    }

    // Récupération des users
    public static void loadUsers(Context ctx, DataCallback<Users> callback, String numTablette) {
        String url = Configuration.getInstance(ctx).getUsersUrl(ctx, numTablette);
        URCache.getFlux(ctx, url, CacheConst.CACHE_HOME_TIMEOUT, callback, new UsersParser());
    }

    // Récupération de l'abonnement
    public static void loadAbonnement(Context ctx, DataCallback<ContenantBean> callback, String numTablette) {
        String url = Configuration.getInstance(ctx).getAbonnementUrl(ctx, numTablette);
        URCache.getFlux(ctx, url, CacheConst.CACHE_HOME_TIMEOUT, callback, new OptaeParser());
    }

    // Récupération des infos
    public static void loadInfos(Context ctx, DataCallback<TabletteInfos> callback, String numTablette) {
        String url = Configuration.getInstance(ctx).getInfosUrl(ctx, numTablette);
        URCache.getFlux(ctx, url, CacheConst.CACHE_HOME_TIMEOUT, callback, new InfosParser());
    }

    // Récupération des modules
    public static void loadModules(Context ctx, DataCallback<Modules> callback, String numTablette) {
        String url = Configuration.getInstance(ctx).getModulesUrl(ctx, numTablette);
        URCache.getFlux(ctx, url, CacheConst.CACHE_HOME_TIMEOUT, callback, new ModulesParser());
    }

    // Test WS
    public static void testUrl(Context ctx, DataCallback<OptaeParser> callback, String numTablette) {
        String url = Configuration.getInstance(ctx).getModulesUrl(ctx, numTablette);
        URCache.getFlux(ctx, url, CacheConst.CACHE_HOME_TIMEOUT, callback, new ModulesParser());
    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.INTERNET
    };

    public static void verifyPermissions(Context ctx) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(ctx, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    (Activity) ctx,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    // Récupération des types habitats
    public static void loadTypeHabitat(Context ctx, DataAndErrorCallback<TypeHabitats> callback) {
        String url = Configuration.getInstance(ctx).getTypeHabitatUrl(ctx);
        URCache.getFlux(ctx, url, CacheConst.CACHE_HOME_TIMEOUT, callback, new TypeHabitatParser());
    }

    // Récupération des habitats
    public static void loadAllHabitat(Context ctx, DataAndErrorCallback<Habitats> callback, int idAccount) {
        String url = Configuration.getInstance(ctx).getAllHabitatUrl(ctx, idAccount);
        URCache.getFlux(ctx, url, CacheConst.CACHE_HOME_TIMEOUT, callback, new HabitatsParser());
    }

    // Récupération des locaux
    public static void loadAllLocal(Context ctx, DataAndErrorCallback<Locaux> callback, int idAccount) {
        String url = Configuration.getInstance(ctx).getAllLocalUrl(ctx, idAccount);
        URCache.getFlux(ctx, url, CacheConst.CACHE_HOME_TIMEOUT, callback, new LocalParser());
    }

    // Récupération des ménages
    public static void loadAllMenage(Context ctx, DataAndErrorCallback<Menages> callback, int idAccount) {
        String url = Configuration.getInstance(ctx).getAllMenageUrl(ctx, idAccount);
        URCache.getFlux(ctx, url, CacheConst.CACHE_HOME_TIMEOUT, callback, new MenageParser());
    }

    // Récupération des usagers
    public static void loadAllUsager(Context ctx, DataAndErrorCallback<Usagers> callback, int idAccount) {
        String url = Configuration.getInstance(ctx).getAllUsagerUrl(ctx, idAccount);
        URCache.getFlux(ctx, url, CacheConst.CACHE_HOME_TIMEOUT, callback, new UsagerParser());
    }

    // Récupération de la liaison usager habitat
    public static void loadAllUsagerHabitat(Context ctx, DataAndErrorCallback<UsagerHabitats> callback, int idAccount) {
        String url = Configuration.getInstance(ctx).getAllUsagerHabitatUrl(ctx, idAccount);
        URCache.getFlux(ctx, url, CacheConst.CACHE_HOME_TIMEOUT, callback, new UsagerHabitatParser());
    }

    // Récupération de la liaison usager ménage
    public static void loadAllUsagerMenage(Context ctx, DataAndErrorCallback<UsagerMenages> callback, int idAccount) {
        String url = Configuration.getInstance(ctx).getAllUsagerMenageUrl(ctx, idAccount);
        URCache.getFlux(ctx, url, CacheConst.CACHE_HOME_TIMEOUT, callback, new UsagerMenageParser());
    }

    // Récupération des déchèteries
    public static void loadAllDecheterie(Context ctx, DataAndErrorCallback<Decheteries> callback, int idAccount) {
        String url = Configuration.getInstance(ctx).getAllDecheterieUrl(ctx, idAccount);
        URCache.getFlux(ctx, url, CacheConst.CACHE_HOME_TIMEOUT, callback, new DecheterieParser());
    }

    // Récupération des typeCartes
    public static void loadTypeCarte(Context ctx, DataAndErrorCallback<TypeCartes> callback) {
        String url = Configuration.getInstance(ctx).getTypeCarteUrl(ctx);
        URCache.getFlux(ctx, url, CacheConst.CACHE_HOME_TIMEOUT, callback, new TypeCarteParser());
    }

    // Récupération des cartes
    public static void loadAllCarte(Context ctx, DataAndErrorCallback<Cartes> callback, int idAccount) {
        String url = Configuration.getInstance(ctx).getAllCarteUrl(ctx, idAccount);
        URCache.getFlux(ctx, url, CacheConst.CACHE_HOME_TIMEOUT, callback, new CarteParser());
    }

    // Récupération des carteActives
    public static void loadAllCarteActive(Context ctx, DataAndErrorCallback<CarteActives> callback, int idAccount) {
        String url = Configuration.getInstance(ctx).getAllCarteActiveUrl(ctx, idAccount);
        URCache.getFlux(ctx, url, CacheConst.CACHE_HOME_TIMEOUT, callback, new CarteActiveParser());
    }

    // Récupération des carteEtatRaisons
    public static void loadCarteEtatRaison(Context ctx, DataAndErrorCallback<CarteEtatRaisons> callback) {
        String url = Configuration.getInstance(ctx).getCarteEtatRaisonUrl(ctx);
        URCache.getFlux(ctx, url, CacheConst.CACHE_HOME_TIMEOUT, callback, new CarteEtatRaisonParser());
    }

    // Récupération des compte prépayés
    public static void loadAllComptePrepaye(Context ctx, DataAndErrorCallback<ComptePrepayes> callback, int idAccount) {
        String url = Configuration.getInstance(ctx).getAllComptePrepayeUrl(ctx, idAccount);
        URCache.getFlux(ctx, url, CacheConst.CACHE_HOME_TIMEOUT, callback, new ComptePrepayeParser());
    }

    // Récupération des fluxs
    public static void loadAllFlux(Context ctx, DataAndErrorCallback<Fluxs> callback, int idAccount) {
        String url = Configuration.getInstance(ctx).getAllFluxUrl(ctx, idAccount);
        URCache.getFlux(ctx, url, CacheConst.CACHE_HOME_TIMEOUT, callback, new FluxParser());
    }

    // Récupération de la liaison déchèterie flux
    public static void loadAllDecheterieFlux(Context ctx, DataAndErrorCallback<DecheterieFluxs> callback, int idAccount) {
        String url = Configuration.getInstance(ctx).getAllDecheterieFluxUrl(ctx, idAccount);
        URCache.getFlux(ctx, url, CacheConst.CACHE_HOME_TIMEOUT, callback, new DecheterieFluxParser());
    }

    // Récupération des fluxs
    public static void loadAllUnite(Context ctx, DataAndErrorCallback<Unites> callback) {
        String url = Configuration.getInstance(ctx).getAllUniteUrl(ctx);
        URCache.getFlux(ctx, url, CacheConst.CACHE_HOME_TIMEOUT, callback, new UniteParser());
    }

    // Récupération des account settings
    public static void loadAllAccountSetting(Context ctx, DataAndErrorCallback<AccountSettings> callback, int idAccount) {
        String url = Configuration.getInstance(ctx).getAllAccountSettingUrl(ctx, idAccount);
        URCache.getFlux(ctx, url, CacheConst.CACHE_HOME_TIMEOUT, callback, new AccountSettingParser());
    }

    // Récupération des ChoixDecompteTotals
    public static void loadChoixDecompteTotal(Context ctx, DataAndErrorCallback<ChoixDecompteTotals> callback) {
        String url = Configuration.getInstance(ctx).getChoixDecompteTotalUrl(ctx);
        URCache.getFlux(ctx, url, CacheConst.CACHE_HOME_TIMEOUT, callback, new ChoixDecompteTotalParser());
    }

    // Récupération des account flux settings
    public static void loadAllAccountFluxSetting(Context ctx, DataAndErrorCallback<AccountFluxSettings> callback, int idAccount) {
        String url = Configuration.getInstance(ctx).getAllAccountFluxSettingUrl(ctx, idAccount);
        URCache.getFlux(ctx, url, CacheConst.CACHE_HOME_TIMEOUT, callback, new AccountFluxSettingParser());
    }


}
