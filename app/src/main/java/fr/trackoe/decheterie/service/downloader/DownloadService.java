package fr.trackoe.decheterie.service.downloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;

import fr.trackoe.decheterie.Logger;

public class DownloadService extends Service {

    private static Logger logger = Logger.getLogger(DownloadService.class);

    private static final int buffer_size = 8192;

    public static final String URL_KEY = "url";
    public static final String PATH_KEY = "path";
    public static final String METHOD_KEY = "method";
    public static final String TIMEOUT_KEY = "timeout";
    public static final String POST_DATA_KEY = "postData";
    public static final String EXTRA_HEADERS_KEY = "headers";
    public static final String LASTDLDATE_KEY = "lastDLDate";
    public static final String CALLBACK_KEY = "callbackName";
    public static final String ACTION_KEY = "ACTION_KEY";
    public static final String STATUSCODE_KEY = "STATUSCODE_KEY";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";

    private static final String IF_MODIFIED_HEADER = "If-Modified-Since";
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.UK);

    private final ArrayList<ServiceHandler> _serviceHandlers = new ArrayList<ServiceHandler>();
    private static Map<String, ArrayList<Messenger>> _consumerMessengers = new ConcurrentHashMap<String, ArrayList<Messenger>>();
    private final ArrayList<String> _downloadingUrls = new ArrayList<String>();

    @Override
    public void onCreate() {
        super.onCreate();
        // logger.d("onCreate");
    }

    private final class ServiceHandler extends Handler {

        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            String callbackName = null;
            if (msg.getData().containsKey(CALLBACK_KEY)) {
                callbackName = msg.getData().getString(CALLBACK_KEY);
            }

            String url = null, path = null, method = METHOD_GET, postData = null;
            long lastDL = 0;
            Map extraHeaders = null; // Non typé car sur certaine version
            // d'Harmony le typage fait planter le
            // cast
            url = msg.getData().containsKey(URL_KEY) ? msg.getData().getString(URL_KEY) : null;
            path = msg.getData().containsKey(PATH_KEY) ? msg.getData().getString(PATH_KEY) : null;

            lastDL = msg.getData().containsKey(LASTDLDATE_KEY) ? msg.getData().getLong(LASTDLDATE_KEY) : 0;
            method = msg.getData().containsKey(METHOD_KEY) ? msg.getData().getString(METHOD_KEY) : METHOD_GET;
            int timeOut = msg.getData().containsKey(TIMEOUT_KEY) ? msg.getData().getInt(TIMEOUT_KEY) : 0;
            postData = msg.getData().containsKey(POST_DATA_KEY) ? msg.getData().getString(POST_DATA_KEY) : null;
            extraHeaders = (Map) (msg.getData().containsKey(EXTRA_HEADERS_KEY) ? msg.getData().getSerializable(EXTRA_HEADERS_KEY) : null);
            if (url != null && path != null) {
                downloadFile(msg.getData().getString(URL_KEY), msg.getData().getString(PATH_KEY), callbackName, lastDL, method, timeOut, postData, extraHeaders);
            }
        }
    }

    public void downloadFile(String fileURL, String fileName, String callbackName, long lastDownloadDate, String method, int timeOut, String postData, Map headers) {
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        // logger.d("downloading file:"+fileURL);
        _downloadingUrls.add(fileURL);
        boolean succeed = true;

        InputStream is;

        int status_code = -1;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(fileURL.replaceAll(" ", "%20")).openConnection();
            if (METHOD_POST.equals(method)) {
                connection.setDoOutput(true);
                connection.setRequestMethod(METHOD_POST);
            } else {
                // GET par défaut
                connection.setDoOutput(false);
            }

            if (timeOut > 0) {
                connection.setConnectTimeout(timeOut);
            }

            // GESTION DES HEADERS
            String onehourago = dateFormat.format(new Date(lastDownloadDate));
            // logger.d("", "onehourago:"+onehourago);
            connection.addRequestProperty(IF_MODIFIED_HEADER, onehourago);
            if (headers != null) {
                for (Object key : headers.keySet()) {
                    try {
                        connection.addRequestProperty((String) key, (String) headers.get(key));
                    } catch (ClassCastException e) {
                        logger.e("ExtraHeaders incorrect, IL FAUT une map de STRING,STRING !", e);
                        // IL FAUT une map de STRING,STRING !
                    }
                }
            }

            if (METHOD_POST.equals(method) && postData != null) {
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(postData);
                writer.close();
            }
            ;
            status_code = connection.getResponseCode();
            // logger.d("satus_code:"+status_code);

            // si le contenu n'a pas changé depuis le dernier téléchargement
            /*if (status_code == HttpStatus.SC_NOT_MODIFIED) {
				succeed = true;
				_downloadingUrls.remove(fileURL);
				transmitCallbackToRequester(callbackName, status_code, fileURL, succeed);
				return;
			}*/

            is = connection.getInputStream();
            File f = new File(fileName);
            f.getParentFile().mkdirs();
            FileOutputStream os = new FileOutputStream(f);
            byte[] bytes = new byte[buffer_size];

            while (true) {
                try {
                    int count = is.read(bytes, 0, buffer_size);
                    if (count == -1) {
                        break;
                    }
                    os.write(bytes, 0, count);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            connection.disconnect();
            is.close();
            os.close();
            // logger.d(fileName + " saved");
            succeed = true;

        } catch (Exception e) {
            // logger.d(fileURL,e);
            succeed = false;
        }
        _downloadingUrls.remove(fileURL);
        transmitCallbackToRequester(callbackName, status_code, fileURL, succeed);

    }

    private void transmitCallbackToRequester(String callbackName, int status_code, String fileURL, boolean succeed) {
        if (callbackName != null) {
            final Message mess = Message.obtain();
            mess.getData().putString(CALLBACK_KEY, callbackName);
            mess.getData().putInt(STATUSCODE_KEY, status_code);
            mess.getData().putString("downloaded", fileURL);
            mess.getData().putBoolean("succes", succeed);
            try {
                if (_consumerMessengers.get(callbackName) != null) {
                    for (Messenger messenger : _consumerMessengers.get(callbackName)) {
                        messenger.send(mess);
                    }
                }
                _consumerMessengers.remove(callbackName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ((HandlerThread) Thread.currentThread()).quit();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startIds) {
        if (intent == null || intent.getExtras() == null) {
            return START_STICKY;
        }
        final Bundle bundle = intent.getExtras();

        String callback_name = null;
        if (bundle.containsKey(CALLBACK_KEY)) {
            callback_name = bundle.getString(CALLBACK_KEY);
        }

        String action = null;
        if (bundle.containsKey(ACTION_KEY)) {
            action = bundle.getString(ACTION_KEY);
        }

        if (callback_name != null && action != null) {
            // logger.d("action: "+action+"  remove de "+ callback_name);
            _consumerMessengers.remove(callback_name);

            return START_STICKY;
        }

        if (callback_name != null && intent.getParcelableExtra(Downloader.MESSENGER_NAME) != null) {
            if (!_consumerMessengers.containsKey(callback_name)) {
                _consumerMessengers.put(callback_name, new ArrayList<Messenger>());
            }
            _consumerMessengers.get(callback_name).add((Messenger) intent.getParcelableExtra(Downloader.MESSENGER_NAME));
        }
        // pas de limitations sur le POST
        if (!_downloadingUrls.contains(bundle.getString(URL_KEY)) || METHOD_POST.equals(bundle.getString(METHOD_KEY))) {
            ServiceHandler handler = getAvailableServiceHandler();
            Message msg = handler.obtainMessage();
            Bundle data = new Bundle();
            data.putString(URL_KEY, bundle.getString(URL_KEY));
            data.putString(PATH_KEY, bundle.getString(PATH_KEY));
            data.putString(METHOD_KEY, bundle.getString(METHOD_KEY));
            data.putString(POST_DATA_KEY, bundle.getString(POST_DATA_KEY));
            data.putSerializable(EXTRA_HEADERS_KEY, bundle.getSerializable(EXTRA_HEADERS_KEY));

            if (callback_name != null) {
                data.putString(CALLBACK_KEY, bundle.getString(CALLBACK_KEY));
            }

            if (bundle.containsKey(LASTDLDATE_KEY)) {
                data.putLong(LASTDLDATE_KEY, bundle.getLong(LASTDLDATE_KEY));
            }

            if (bundle.containsKey(TIMEOUT_KEY)) {
                data.putInt(TIMEOUT_KEY, bundle.getInt(TIMEOUT_KEY));
            }
            msg.setData(data);
            handler.sendMessage(msg);
        }

        return START_STICKY;
    }

    private ServiceHandler getAvailableServiceHandler() {
        HandlerThread thread = new HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_LESS_FAVORABLE);
        thread.start();
        ServiceHandler handler = new ServiceHandler(thread.getLooper());
        _serviceHandlers.add(handler);
        return handler;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
