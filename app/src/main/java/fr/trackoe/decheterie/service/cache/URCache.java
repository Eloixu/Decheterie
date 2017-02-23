package fr.trackoe.decheterie.service.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import fr.trackoe.decheterie.Logger;
import fr.trackoe.decheterie.Utils;
import fr.trackoe.decheterie.service.cache.AfterProcesser.BitmapAfterProcesser;
import fr.trackoe.decheterie.service.cache.AfterProcesser.FileAfterProcesser;
import fr.trackoe.decheterie.service.callback.DataAndCacheCallback;
import fr.trackoe.decheterie.service.callback.DataCallback;
import fr.trackoe.decheterie.service.downloader.Downloader;
import fr.trackoe.decheterie.service.downloader.DownloaderRequest;
import fr.trackoe.decheterie.service.parser.JSONParser;

/**
 * User: Vincent Date: 27/05/13 Time: 14:58
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class URCache {

    private static Logger logger = Logger.getLogger(URCache.class);

    private static ConcurrentHashMap<String, ElementDownloadAndExpirationDelay> _elementsInfos = null;
    private static ArrayList<DownloaderRequest> _reqList = new ArrayList<DownloaderRequest>();
    private static String CACHE_FOLDER = null;
    private static String CACHE_FOLDER_PERSISTENT = "persistent/";
    private static String CACHELIST_FILENAME = "CACHELIST_FILENAME";

    private final static String PICTURE_FILE_HEADER = "pic";
    private final static int PICTURE_CACHE_LIMIT = 35;

    /**
     * Initialise le dossier du cache et charge en memoire les informations(date du dernier dl et delai d'expiration) des elemenst cachés
     *
     * @param ctx le Context de l'Activity, de l'Application
     */
    public static void init(Context ctx) {
        CACHE_FOLDER = ctx.getExternalCacheDir() + "/";
        final Object obj = getCacheList();
        if (obj != null) {
            _elementsInfos = (ConcurrentHashMap<String, ElementDownloadAndExpirationDelay>) obj;
        } else {
            _elementsInfos = new ConcurrentHashMap<String, ElementDownloadAndExpirationDelay>();
        }
    }

    /**
     * Retourne le path d'un fichier dans le cache depuis son nom
     *
     * @param filename le nom du fichier cherché.ATTENTION c'est bien le nom du fichier qu'il faut utiliser et non l'url de la ressource
     * @return le path absolu permettant de lire le fichier
     */
    public static String getCachePathForFilename(String filename) {
        return CACHE_FOLDER + filename;
    }

    public static String getPersistentCachePathForFilename(String filename) {
        return CACHE_FOLDER + CACHE_FOLDER_PERSISTENT + filename;
    }

    /**
     * Telecharge une image si besoin et l'encode en Bitmap
     *
     * @param ctx       le Context de l'Activity, de l'Application
     * @param url       l'url de la ressource graphique
     * @param elemDelay le delai d'expiration de l'image (en ms)
     * @param callback  le DataCallback<Bitmap> qui recevra les notification de progression de la requete
     */
    public static void getImage(Context ctx, String url, final long elemDelay, final DataCallback callback) {
        getElement(ctx, url, elemDelay, callback, false, new AfterProcesser.BitmapAfterProcesser(callback, Utils.getScreenWidth(ctx)), null);
    }

    public static void getImage(final Context ctx, final String url, final View imgV, final long elemDelay, final DataCallback callback) {

        String newurl = url + "?width=" + Utils.getScreenWidth(ctx);
        getElement(ctx, newurl, true, elemDelay, CacheConst.CONNECTION_TIMEOUT, callback, false, new AfterProcesser.BitmapAfterProcesser(imgV, callback, Utils.getScreenWidth(ctx)), null);

    }

    public static void getImage(final Context ctx, final String url, int viewWidth, final View imgV, final long elemDelay, final DataCallback callback) {

        String newurl = url + "?width=" + Utils.getScreenWidth(ctx);
        getElement(ctx, newurl, true, elemDelay, CacheConst.CONNECTION_TIMEOUT, callback, false, new AfterProcesser.BitmapAfterProcesser(imgV, callback, viewWidth), null);

    }

    /**
     * Telecharge une image si besoin et l'encode en Bitmap
     *
     * @param ctx       le Context de l'Activity, de l'Application
     * @param url       l'url de la ressource graphique
     * @param viewWidth la largeur de l'imageview pourlaquelle l image est destiné
     * @param elemDelay le delai d'expiration de l'image (en ms)
     * @param callback  le DataCallback<Bitmap> qui recevra les notification de progression de la requete
     */
    public static void getImage(Context ctx, String url, int viewWidth, final long elemDelay, final DataCallback callback) {
        if (viewWidth <= 0) {
            viewWidth = Utils.getScreenWidth(ctx);
        }
        getElement(ctx, url, elemDelay, callback, false, new AfterProcesser.BitmapAfterProcesser(callback, viewWidth), null);
    }

    /**
     * Telecharge un flux JSON si besoin, le parse et le transforme en l'objet metier souhaite
     *
     * @param ctx       le Context de l'Activity, de l'Application
     * @param url       l'url du flux
     * @param elemDelay le delai d'expiration du flux (en ms)
     * @param callback  le DataCallback<Objet Metier> qui recevra les notification de progression de la requete
     * @param parser    le parser qui permettra de transformer le flux JSON en l'objet metier souhaité
     */
    public static void getFlux(Context ctx, String url, final long elemDelay, final DataCallback callback, JSONParser parser) {
        getElement(ctx, url, elemDelay, callback, false, new AfterProcesser.JSONAfterProcesser(callback, parser), null);
    }

    /**
     * Telecharge un flux JSON si besoin, le parse et le transforme en l'objet metier souhaite
     *
     * @param ctx         le Context de l'Activity, de l'Application
     * @param url         l'url du flux
     * @param elemDelay   le delai d'expiration du flux (en ms)
     * @param bypassCache Forcer le noCache
     * @param callback    le DataCallback<Objet Metier> qui recevra les notification de progression de la requete
     * @param parser      le parser qui permettra de transformer le flux JSON en l'objet metier souhaité
     */
    public static void getFlux(Context ctx, String url, final long elemDelay, boolean bypassCache, final DataCallback callback, JSONParser parser) {
        getElement(ctx, url, elemDelay, callback, bypassCache, new AfterProcesser.JSONAfterProcesser(callback, parser), null);
    }


    /**
     * Telecharge un flux JSON si besoin, le parse et le transforme en l'objet metier souhaite
     *
     * @param ctx          le Context de l'Activity, de l'Application
     * @param url          l'url du flux
     * @param elemDelay    le delai d'expiration du flux (en ms)
     * @param bypassCache  Forcer le noCache
     * @param callback     le DataCallback<Objet Metier> qui recevra les notification de progression de la requete
     * @param parser       le parser qui permettra de transformer le flux JSON en l'objet metier souhaité
     * @param extraHeaders le header à utiliser
     */
    public static void getFlux(Context ctx, String url, final long elemDelay, boolean bypassCache, final DataCallback callback, JSONParser parser, Map<String, String> extraHeaders) {
        getElement(ctx, url, elemDelay, callback, bypassCache, new AfterProcesser.JSONAfterProcesser(callback, parser), extraHeaders);
    }


    public static void getFile(Context ctx, String url, final long elemDelay, final DataCallback callback) {
        getElement(ctx, url, elemDelay, callback, false, new FileAfterProcesser(callback), null);
    }

    private static void getElement(final Context ctx, String url, final long elemDelay, final DataCallback callback, boolean bypassCache, final AfterProcesser processer, Map<String, String> extraHeaders) {
        getElement(ctx, url, false, elemDelay, 0, callback, bypassCache, processer, extraHeaders);
    }

    private static void getElement(final Context ctx, String url, final long elemDelay, int timeOut, final DataCallback callback, boolean bypassCache, final AfterProcesser processer, Map<String, String> extraHeaders) {
        getElement(ctx, url, false, elemDelay, timeOut, callback, bypassCache, processer, extraHeaders);
    }

    private static void getElement(final Context ctx, String url, boolean isAPic, final long elemDelay, int timeOut, final DataCallback callback, boolean bypassCache, final AfterProcesser processer, Map<String, String> extraHeaders) {
        if (CACHE_FOLDER == null) {
            init(ctx);
        }

        final String filename = (isAPic ? PICTURE_FILE_HEADER : "") + convertUrlToPath(url);
        // Si l'element est dans le cache ET (qu'il n'est pas expire OU que le device n'est pas connecte à internet) alors on le retourne

        if (url.startsWith("local:") || !bypassCache && isElementPresent(filename) && (isElementValid(filename) || !Utils.isInternetConnected(ctx)) && isElementPresentInFileSystem(filename)) {
            if (url.startsWith("local:")) {
                String realfileName = url.replace("local:", "");
                realfileName = realfileName.substring(0, realfileName.lastIndexOf("."));
                int id = ctx.getResources().getIdentifier(realfileName, "drawable", ctx.getPackageName());
                ((BitmapAfterProcesser) processer).doProcessing(ctx.getResources(), id);
            } else {
                processer.doProcessing(filename);
            }

        } else {
            // On previent la requete que l'element n'est pas en cache et que l'on va lancer une requete asynchrone
            if (callback != null && callback instanceof DataAndCacheCallback) {
                ((DataAndCacheCallback) callback).notInCache();
            }
            DownloaderRequest req = null;

            DownloaderRequest.DownloadStateListener listener = new DownloaderRequest.DownloadStateListener() {
                @Override
                public void elementDownloaded(String url) {
                    updateElemInfos(filename, elemDelay);
                    processer.doProcessing(filename);
                }

                @Override
                public void elementNotChangedSinceLastDL(String url) {
                    updateLastDlDateOf(filename);
                    processer.doProcessing(filename);
                }

                // si le download fail, on tente quand meme, au cas ou le fichier soit deja pr�sent
                @Override
                public void elementFailed(String url) {
                    processer.doProcessingAfterFail(filename);
                }
            };
            if (isElementPresent(filename)) {
                req = new DownloaderRequest(url, getCachePathForFilename(filename), getLastDlDateOf(filename), listener);
            } else {
                req = new DownloaderRequest(url, getCachePathForFilename(filename), listener);
            }
            req.setTimeOut(timeOut);

            if (extraHeaders != null) {
                req.setExtraHeaders(extraHeaders);
            }
            _reqList.add(req);
            Downloader.callRemoteDownloader(ctx, req);
        }
    }

    // Not for pics
    public static long getLastDownloadDate(String url) {
        String filename = convertUrlToPath(url);
        return getLastDlDateOf(filename);
    }

    private static long getLastDlDateOf(String filename) {
        long lastDLDate = 0;
        final ElementDownloadAndExpirationDelay infos = _elementsInfos.get(filename);

        if (infos != null) {
            lastDLDate = infos.getDownloadDate();
        }
        return lastDLDate;
    }

    private static boolean updateLastDlDateOf(String filename) {
        boolean succed = true;
        try {
            _elementsInfos.get(filename).setDownloadDate(System.currentTimeMillis());
        } catch (Exception e) {
            succed = false;
        }
        return succed;
    }

    public static boolean updateElemInfos(String filename, long delay) {
        boolean succed = true;
        try {
            if (!_elementsInfos.containsKey(filename)) {
                _elementsInfos.put(filename, new ElementDownloadAndExpirationDelay(System.currentTimeMillis(), delay));
                removeElemsIfNeeded();
            } else {
                _elementsInfos.get(filename).setDownloadDate(System.currentTimeMillis());
                _elementsInfos.get(filename).setExpirationDelay(delay);
            }
        } catch (Exception e) {
            succed = false;
        }
        return succed;
    }

    private static void removeElemsIfNeeded() {
        Iterator<String> ite = _elementsInfos.keySet().iterator();
        int picNb = 0;
        while (ite.hasNext()) {
            String filename = ite.next();
            if (filename.startsWith(PICTURE_FILE_HEADER)) {
                picNb++;
            }
        }
        Log.e("URCACHE", "Le cache a " + picNb + " images");
        if (picNb > PICTURE_CACHE_LIMIT) {
            String oldestfile = "";
            long oldestdate = Long.MAX_VALUE;
            ite = _elementsInfos.keySet().iterator();
            while (ite.hasNext()) {
                String filename = ite.next();
                if (filename.startsWith(PICTURE_FILE_HEADER)) {
                    long date = _elementsInfos.get(filename).getDownloadDate() + _elementsInfos.get(filename).getExpirationDelay();
                    if (date < oldestdate) {
                        oldestdate = date;
                        oldestfile = filename;
                    }
                }
            }
            // On ne supprime que si le fichier est expir�
            if (oldestdate < System.currentTimeMillis()) {
                _elementsInfos.remove(oldestfile);
                removeImage(oldestfile);
            }
        }

    }

    public static InputStream getElementFromCache(String filename) throws FileNotFoundException {
        FileInputStream stream = new FileInputStream(getCachePathForFilename(filename));
        return stream;
    }

    public static boolean isElementPresentInFileSystem(String filename) {
        try {
            return new File(getCachePathForFilename(filename)).exists();
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isElementPresent(String filename) {
        return _elementsInfos.containsKey(filename);
    }

    private static boolean isElementExpired(String filename) {
        ElementDownloadAndExpirationDelay infos = null;
        infos = _elementsInfos.get(filename);
        long expir = infos.getDownloadDate() + infos.getExpirationDelay();
        long now = System.currentTimeMillis();
        return expir < now;
    }

    private static boolean isElementValid(String filename) {
        return !isElementExpired(filename);
    }

    /**
     * Converti une url en un nom de fichier compatible avec le filesystem
     *
     * @param url l'url à convertir
     * @return un nom de fichier compatible avec le filesystem
     */
    public static String convertUrlToPath(String url) {
        String baseUrl = url.replace("/", "").replace(":", "").replace("?", "").replace("=", "").replace("&", "").replace(" ", "");
        if(baseUrl.length() > 80) {
            baseUrl = baseUrl.substring(0, 80);
        }
        return md5hash(baseUrl);
    }

    private static String md5hash(String url) {
        String resUrl = url;
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(url.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2) {
                    h = "0" + h;
                }
                hexString.append(h);
            }
            resUrl = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.w("Error when trying to hash url : " + url, e);
        }
        return resUrl;
    }

    /**
     * Cette methode doit etre appele durant le removeDownloadListener de l'activité afin que les listener de telechargement soient supprimés
     *
     * @param ctx le Context de l'Activity
     */
    public static void removeDownloadListener(Context ctx) {
        // for (int i = 0; i < _reqList.size(); i++) {
        // Downloader.removeListener(ctx, _reqList.get(i));
        // }
        // _reqList.clear();
    }

    public static void saveCache() {
        saveCacheList(_elementsInfos);
    }

    private static Object getCacheList() {
        Object obj = null;
        try {
            ObjectInputStream stream = new ObjectInputStream(new FileInputStream(getPersistentCachePathForFilename(CACHELIST_FILENAME)));
            obj = stream.readObject();
            stream.close();
        } catch (Exception e) {
//            logger.e("Error : ", e);
        }
        return obj;
    }

    private static void saveCacheList(AbstractMap<String, ElementDownloadAndExpirationDelay> list) {
        new SavingCacheTask().execute(new ConcurrentHashMap<String, ElementDownloadAndExpirationDelay>(list));

    }

    public static class SavingCacheTask extends AsyncTask<AbstractMap<String, ElementDownloadAndExpirationDelay>, Void, Void> {

        @Override
        protected Void doInBackground(AbstractMap<String, ElementDownloadAndExpirationDelay>... params) {
            try {
                File f = new File(getPersistentCachePathForFilename(CACHELIST_FILENAME));
                if (!f.getParentFile().exists()) {
                    f.getParentFile().mkdirs();
                }
                ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(getPersistentCachePathForFilename(CACHELIST_FILENAME)));
                stream.writeObject(params[0]);
                stream.close();
            } catch (IOException e) {
                logger.e(null, e);
            }
            return null;

        }
    }

    /**
     * Sauvegarde une image pour la garder dans l'application Celle-ci peut être interne à l'application ou téléchargée
     *
     * @param filename Nom du fichier de l'image
     * @param bitmap   Image
     */
    public static void saveImage(String filename, Bitmap bitmap) {
        try {
            FileOutputStream out = new FileOutputStream(CACHE_FOLDER + filename);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.close();
        } catch (Exception e) {
        }
    }

    /**
     * Récupère une image sauvée dans le cache de l'application
     *
     * @param filename Nom du fichier de l'image
     * @return bitmap Image
     */
    public static Bitmap getImage(String filename) {
        return BitmapFactory.decodeFile(CACHE_FOLDER + filename);
    }

    /**
     * Supprime une image sauvée dans le cache de l'application
     *
     * @param filename Nom du fichier de l'image
     * @return bitmap Image
     */
    public static void removeImage(String filename) {
        File f = new File(CACHE_FOLDER + filename);
        if (f.isFile()) {
            f.delete();
        }
    }

    /*
     * PARTIE CACHE D OBJETS PERSO
     */
    public static Object getObject(String userKey) {
        Object obj = null;
        if (userKey != null) {
            try {
                String cacheKey = getCacheKey(userKey);
                ObjectInputStream stream = new ObjectInputStream(new FileInputStream(getPersistentCachePathForFilename(cacheKey)));
                obj = stream.readObject();
                stream.close();
            } catch (Exception e) {
            }
        }
        return obj;
    }

    public static void putObject(String userKey, Object obj) {
        if (userKey != null) {
            if (obj != null) {
                String cacheKey = getCacheKey(userKey);
                new SavingObjectCacheTask(cacheKey).execute(obj);
            }
        }
    }

    public static boolean removeObject(String userKey) {
        String cacheKey = getCacheKey(userKey);
        String path = getPersistentCachePathForFilename(cacheKey);
        File f = new File(path);
        if (f.isFile()) {
            return f.delete();
        }
        return false;
    }

    private static String getCacheKey(String key) {
        String ret = convertUrlToPath(key);
        if (CACHELIST_FILENAME.equals(ret)) { // Pour proteger
            return ret + "2";
        }
        return ret;
    }

    public static class SavingObjectCacheTask extends AsyncTask<Object, Void, Void> {
        private String key = null;

        public SavingObjectCacheTask(String cacheKey) {
            key = cacheKey;
        }

        @Override
        protected Void doInBackground(Object... params) {
            try {
                File f = new File(getPersistentCachePathForFilename(key));
                if (!f.getParentFile().exists()) {
                    f.getParentFile().mkdirs();
                }
                ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(getPersistentCachePathForFilename(key)));
                stream.writeObject(params[0]);
                stream.close();
            } catch (IOException e) {
                logger.e(null, e);
            }
            return null;
        }
    }

}