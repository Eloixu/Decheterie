package fr.trackoe.decheterie.service.cache;

import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;

import fr.trackoe.decheterie.BuildConfig;
import fr.trackoe.decheterie.Logger;
import fr.trackoe.decheterie.Utils;
import fr.trackoe.decheterie.service.OnAttachListener;
import fr.trackoe.decheterie.service.callback.DataAndErrorCallback;
import fr.trackoe.decheterie.service.callback.DataAndProcessAfterFailCallback;
import fr.trackoe.decheterie.service.callback.DataCallback;
import fr.trackoe.decheterie.service.parser.IParser;
import fr.trackoe.decheterie.service.parser.JSONParser;

/**
 * L'AfterProcesser effectue un traitement sur la donnée renvoyé par le URCache(un InputStream) avant de la renvoyé transformé au DataCallback à l'origine de la requete
 */
@SuppressLint("NewApi")
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class AfterProcesser {

    // protected WeakReference<DataCallback> _callback;
    protected DataCallback _callback;
    private static Logger logger = Logger.getLogger(AfterProcesser.class);

    public AfterProcesser(DataCallback callback) {
        // _callback = new WeakReference<DataCallback>(callback);
        _callback = callback;
    }

    public abstract void doProcessing(String filePath);

    /**
     * Le BitmapProcesser est spécialisé dans la transformation d'un stream venant du cache en image
     */
    @SuppressLint("NewApi")
    public static class BitmapAfterProcesser extends AfterProcesser {

        private WeakReference<View> _imgV;
        private int _viewWidth = -1;

        public BitmapAfterProcesser(DataCallback callback, int viewWidth) {
            super(callback);
            if (viewWidth > 0) {
                this._viewWidth = viewWidth;
            }
        }

        public BitmapAfterProcesser(View imgV, DataCallback callback, int viewWidth) {
            super(callback);
            if (viewWidth > 0) {
                this._viewWidth = viewWidth;
            }
            _imgV = new WeakReference<View>(imgV);
        }

        @Override
        public void doProcessing(String filePath) {
            if (_imgV != null && (_imgV.get() == null || _imgV.get() instanceof OnAttachListener && !((OnAttachListener) _imgV.get()).mustReceiveBitmap())) {
                Log.e("", "ON NE VA PAS DECODER PARCEQUE L IMAGE A DISPARU");
                return;
            }
            if (_callback != null && _callback != null && (_imgV == null || (_imgV.get() != null))) {

				/*
                 * if(_imgV != null && _imgV.get() != null) Log.e("", "imgV width : "+_imgV.get().getMeasuredWidth()); if(_imgV != null && _imgV.get() != null && _imgV.get().getMeasuredWidth()>0) _viewWidth = _imgV.get().getMeasuredWidth();
				 */
                if (Build.VERSION.SDK_INT >= 11) {
                    // Probleme de synchronisation qui charge les bmp dans les mauvaises imgview
                    // PASSAGE EN SERIAL Pour éviter l'exception :
                    // "java.util.concurrent.RejectedExecutionException: Task android.os.AsyncTask$3@41df39e8 rejected from java.util.concurrent.ThreadPoolExecutor@410f2c10[Running, pool size = 128, active threads = 128, queued tasks = 10, completed tasks = 64]"
                    new Bitmapdecoder(_callback, _viewWidth).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, filePath);
                } else {
                    new Bitmapdecoder(_callback, _viewWidth).execute(filePath);
                }
            }
        }

        public void doProcessing(Resources resources, int id) {
            if (_imgV != null && (_imgV.get() == null || _imgV.get() instanceof OnAttachListener && !((OnAttachListener) _imgV.get()).mustReceiveBitmap())) {
                Log.e("", "ON NE VA PAS DECODER PARCEQUE L IMAGE A DISPARU");
                return;
            }
            if (_callback != null && _callback != null && (_imgV == null || (_imgV.get() != null))) {

				/*
				 * if(_imgV != null && _imgV.get() != null) Log.e("", "imgV width : "+_imgV.get().getMeasuredWidth()); if(_imgV != null && _imgV.get() != null && _imgV.get().getMeasuredWidth()>0) _viewWidth = _imgV.get().getMeasuredWidth();
				 */
                if (Build.VERSION.SDK_INT >= 11) {
                    // Probleme de synchronisation qui charge les bmp dans les mauvaises imgview
                    // PASSAGE EN SERIAL Pour éviter l'exception :
                    // "java.util.concurrent.RejectedExecutionException: Task android.os.AsyncTask$3@41df39e8 rejected from java.util.concurrent.ThreadPoolExecutor@410f2c10[Running, pool size = 128, active threads = 128, queued tasks = 10, completed tasks = 64]"
                    new Bitmapdecoder(_callback, _viewWidth, resources, id).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
                } else {
                    new Bitmapdecoder(_callback, _viewWidth, resources, id).execute("");
                }
            } else {
                _callback = null;
            }

        }
    }

    /**
     * Le FileAfterProcesser après téléchargement d'un fichier (hors json et images)
     */
    public static class FileAfterProcesser extends AfterProcesser {

        /**
         * @param callback la DataCallback associé à la requete
         */
        public FileAfterProcesser(DataCallback callback) {
            super(callback);
        }

        @Override
        public void doProcessing(String fileName) {
            if (_callback != null) {
                _callback.dataLoaded(fileName);
            }
        }
    }

    public void doProcessingAfterFail(String filename) {
        if (_callback != null && _callback instanceof DataAndProcessAfterFailCallback) {
            ((DataAndProcessAfterFailCallback) _callback).dataProcessAfterFailed(true, "");
        }
        doProcessing(filename);

    }

    public static class Bitmapdecoder extends AsyncTask<String, Integer, Bitmap> {

        private DataCallback _callback;
        private int _viewWidth = -1;
        private Resources _res;
        private int _resId = -1;
        private final BitmapFactory.Options _options = new BitmapFactory.Options();
        private static BitmapFactory.Options _optionsSize = new BitmapFactory.Options();

        public Bitmapdecoder(DataCallback callback, int viewWidth) {
            _callback = callback;
            _viewWidth = viewWidth;
            Log.e("", "Bitmap Rendering.... ");
        }

        public Bitmapdecoder(DataCallback callback, int viewWidth, Resources resources, int id) {
            _callback = callback;
            _viewWidth = viewWidth;
            _res = resources;
            _resId = id;
            Log.e("", "Bitmap Rendering....");
        }

        private synchronized int computeBitmapSampleSize(String filename) {
            _optionsSize.inJustDecodeBounds = true;
            if (_res != null && _resId > 0) {
                BitmapFactory.decodeResource(_res, _resId, _optionsSize);
            } else {
                BitmapFactory.decodeFile(URCache.getCachePathForFilename(filename), _optionsSize);
            }
            int bmpwidth = _optionsSize.outWidth;
            return bmpwidth / _viewWidth;
            // return 16;
        }

        @Override
        protected Bitmap doInBackground(String... fileName) {
            // synchronized (Bitmapdecoder.class) {
            _options.inPurgeable = true;
            _options.inSampleSize = computeBitmapSampleSize(fileName[0]);
            try {
                if (_res != null && _resId > 0) {
                    return BitmapFactory.decodeResource(_res, _resId, _options);
                } else {
                    return BitmapFactory.decodeFile(URCache.getCachePathForFilename(fileName[0]), _options);
                }
            } catch (OutOfMemoryError e) {
                return null;
            }

            // }
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            Log.d("", "BItmapDecoder onPostExecute: result null?:" + (result == null) + " _callback return error?:" + (_callback instanceof DataAndErrorCallback));
            if (_callback != null && result != null) {
                _callback.dataLoaded(result);
            }
            if (result == null && _callback instanceof DataAndErrorCallback) {
                ((DataAndErrorCallback) _callback).dataLoadingFailed(true, "Cannot convert this file in a Bitmap");
            }
            if (result == null && _callback instanceof DataAndProcessAfterFailCallback) {
                ((DataAndProcessAfterFailCallback) _callback).dataLoadingFailed(true, "Cannot convert this file in a Bitmap");
            }
            _callback = null;
            _res = null;
        }
    }


    /**
     * Le JSONAfterProcesser parse et transforme le flux JSON en un objet metier grace au JSONParser associé
     */
    public static class JSONAfterProcesser extends AfterProcesser {

        private final JSONParser _parser;

        /**
         * @param callback la DataCallback associé à la requete
         * @param parser   le JSONParser qui sera utilisé pour transformer le flux JSON en la classe metier desirée
         */
        public JSONAfterProcesser(DataCallback callback, JSONParser parser) {
            super(callback);
            _parser = parser;
        }

        @Override
        public void doProcessing(String fileName) {
            if (_callback != null) {
                new FluxParserTask(_callback, _parser).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, fileName);
            } else {
                _callback = null;
            }
        }

        public static class FluxParserTask extends AsyncTask<String, Integer, Object> {

            public static final String FILE_NOT_PARSED_ERROR = "Cannot Parse This File";
            public static final String FILE_NOT_FOUND_ERROR = "File Not Found On Disk";

            private DataCallback _callback;
            private final IParser _parser;
            private boolean isError = true;
            private String errorMessage;

            public FluxParserTask(DataCallback _callback2, IParser parser) {
                _callback = _callback2;
                _parser = parser;
                isError = true;
            }

            @Override
            protected Object doInBackground(String... fileName) {
                try {
                    final String stream = Utils.convertStreamToString(URCache.getElementFromCache(fileName[0]));
                    try {
                        Object parse = _parser.parse(stream);
                        isError = false;
                        return parse;
                    } catch (Exception e) {
                        if (BuildConfig.DEBUG) {
                            logger.e("Cannot parse URCache stream", e);
                        }
                        if (_callback instanceof DataAndErrorCallback) {
                            try {
                                errorMessage = _parser.parseError(stream);
                            } catch (Exception e1) {
                                errorMessage = FILE_NOT_PARSED_ERROR;
                            }
                        } else {
                            return null;
                        }
                    }
                } catch (FileNotFoundException e) {
                    logger.e("File not found for stream", e);
                    if (_callback instanceof DataAndErrorCallback) {
                        errorMessage = FILE_NOT_FOUND_ERROR;
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object result) {
                if (_callback != null) {
                    if (_callback instanceof DataAndErrorCallback && isError) {
                        ((DataAndErrorCallback) _callback).dataLoadingFailed(true, errorMessage);
                    } else if (result != null) {
                        _callback.dataLoaded(result);
                    }
                } else {
                    _callback = null;
                }

                _callback = null;
            }
        }
    }

}
