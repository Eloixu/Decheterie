package fr.trackoe.decheterie.model;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;

import fr.trackoe.decheterie.configuration.Configuration;
import fr.trackoe.decheterie.model.bean.global.ApkInfos;
import fr.trackoe.decheterie.model.bean.global.ContenantBean;
import fr.trackoe.decheterie.model.bean.global.Modules;
import fr.trackoe.decheterie.model.bean.global.Rues;
import fr.trackoe.decheterie.model.bean.global.TabletteInfos;
import fr.trackoe.decheterie.model.bean.global.Users;
import fr.trackoe.decheterie.model.bean.global.Ville;
import fr.trackoe.decheterie.model.bean.global.Villes;
import fr.trackoe.decheterie.service.cache.CacheConst;
import fr.trackoe.decheterie.service.cache.URCache;
import fr.trackoe.decheterie.service.callback.DataCallback;
import fr.trackoe.decheterie.service.parser.ApkInfosParser;
import fr.trackoe.decheterie.service.parser.InfosParser;
import fr.trackoe.decheterie.service.parser.ModulesParser;
import fr.trackoe.decheterie.service.parser.OptaeParser;
import fr.trackoe.decheterie.service.parser.UsersParser;

/**
 * Created by Remi on 30/11/2015.
 */
public class Datas {
    public static void loadUploadApk(Context ctx, DataCallback<ApkInfos> callback, int versionCode) {
        String url = Configuration.getInstance(ctx).getApkUpdateUrl(ctx, versionCode);
        URCache.getFlux(ctx, url, CacheConst.CACHE_HOME_TIMEOUT, callback, new ApkInfosParser());
    }

    // Récupérations des users
    public static void loadUsers(Context ctx, DataCallback<Users> callback, String numTablette) {
        String url = Configuration.getInstance(ctx).getUsersUrl(ctx, numTablette);
        URCache.getFlux(ctx, url, CacheConst.CACHE_HOME_TIMEOUT, callback, new UsersParser());
    }

    // Récupérations de l'abonnement
    public static void loadAbonnement(Context ctx, DataCallback<ContenantBean> callback, String numTablette) {
        String url = Configuration.getInstance(ctx).getAbonnementUrl(ctx, numTablette);
        URCache.getFlux(ctx, url, CacheConst.CACHE_HOME_TIMEOUT, callback, new OptaeParser());
    }

    // Récupérations des infos
    public static void loadInfos(Context ctx, DataCallback<TabletteInfos> callback, String numTablette) {
        String url = Configuration.getInstance(ctx).getInfosUrl(ctx, numTablette);
        URCache.getFlux(ctx, url, CacheConst.CACHE_HOME_TIMEOUT, callback, new InfosParser());
    }

    // Récupérations des modules
    public static void loadModules(Context ctx, DataCallback<Modules> callback, String numTablette) {
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

}
