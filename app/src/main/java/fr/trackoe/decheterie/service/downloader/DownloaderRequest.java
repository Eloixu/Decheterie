package fr.trackoe.decheterie.service.downloader;

import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import java.util.Map;

/**
 * User: Vincent Date: 24/05/13 Time: 16:29
 */
public class DownloaderRequest {

    public interface DownloadStateListener {
        public void elementDownloaded(String url);

        public void elementNotChangedSinceLastDL(String url);

        public void elementFailed(String url);
    }

    private Messenger _messenger;
    private String _url;
    private String _filePath;
    private String _callback;
    private long _lastDLDate = 0;
    private int _timeOut = 0;
    private String _method = DownloadService.METHOD_GET;
    private String _postData = null;
    private Map<String, String> _extraHeaders = null;

    public DownloaderRequest(String url, String filePath, DownloadStateListener listener) {
        DownloaderHandler handler = new DownloaderHandler();
        handler.setListener(listener);
        this._messenger = new Messenger(handler);
        this._url = url;
        this._filePath = filePath;
        this._callback = url;
    }

    public DownloaderRequest(String url, String filePath, long lastDlDate, DownloadStateListener listener) {
        DownloaderHandler handler = new DownloaderHandler();
        handler.setListener(listener);
        this._messenger = new Messenger(handler);
        this._url = url;
        this._filePath = filePath;
        this._callback = url;
        this._lastDLDate = lastDlDate;
    }

    public long getLastDLDate() {
        return _lastDLDate;
    }

    public String getUrl() {
        return _url;
    }

    public String getFilePath() {
        return _filePath;
    }

    public String getCallback() {
        return _callback;
    }

    public Messenger getMessenger() {
        return _messenger;
    }

    public static class DownloaderHandler extends Handler {

        private DownloadStateListener _listener;

        public DownloaderHandler() {
            super();
        }

        @Override
        public void handleMessage(Message msg) {
            String callbackName = msg.getData().getString("callbackName");
            String url = msg.getData().getString("downloaded");
            boolean suceed = msg.getData().getBoolean("succes");
            int status_code = msg.getData().getInt(DownloadService.STATUSCODE_KEY);
            Log.d("DemoActivity", "status_code:" + status_code + "  " + url);
            if (_listener != null) {
                /*if (status_code == HttpStatus.SC_NOT_MODIFIED) {
					_listener.elementNotChangedSinceLastDL(url);
				} else*/
                if (suceed)
                    _listener.elementDownloaded(url);
                else
                    _listener.elementFailed(url);
            }
            _listener = null;

        }

        public void setListener(DownloadStateListener listener) {
            _listener = listener;
        }
    }

    public Map<String, String> getExtraHeaders() {
        return _extraHeaders;
    }

    public void setExtraHeaders(Map<String, String> extraHeaders) {
        this._extraHeaders = extraHeaders;
    }

    public String getPostData() {
        return _postData;
    }

    public void setPostData(String postData) {
        this._postData = postData;
    }

    public String getMethod() {
        return _method;
    }

    public void setMethod(String method) {
        this._method = method;
    }

    public int getTimeOut() {
        return _timeOut;
    }

    public void setTimeOut(int timeOut) {
        this._timeOut = timeOut;
    }
}
