package fr.trackoe.decheterie;

import android.util.Log;

public class Logger {

    // Le préfix utilisé sur le tag des logs
    private static String TAG_PREFIX = "optaecontenants-";

    // Le niveau de log utilisé sur l'application
    private static LogLevel logLevel = LogLevel.INFO;

    private enum LogLevel {
        VERBOSE(0), DEBUG(1), INFO(2), WARNING(3), ERROR(4);

        private int code;

        private LogLevel(int c) {
            code = c;
        }

        public int getCode() {
            return code;
        }
    }

    public static Logger getLogger(Class clazz) {
        return new Logger(TAG_PREFIX + clazz.getName());
    }

    private String tag = null;

    private Logger(String tag) {
        this.tag = tag;
    }

    public void i(String msg, Throwable t) {
        if (logLevel.getCode() <= LogLevel.INFO.getCode()) {
            Log.i(tag, msg, t);
        }
    }

    public void i(String msg) {
        if (logLevel.getCode() <= LogLevel.INFO.getCode()) {
            Log.i(tag, msg);
        }
    }

    /*
     * DEBUG
     */
    public void d(String msg, Throwable t) {
        if (BuildConfig.DEBUG) {
            if (logLevel.getCode() <= LogLevel.DEBUG.getCode()) {
                Log.d(tag, msg, t);
            }
        }
    }

    public void d(String msg) {
        if (BuildConfig.DEBUG) {
            if (logLevel.getCode() <= LogLevel.DEBUG.getCode()) {
                Log.d(tag, msg);
            }
        }
    }

    /*
     * ERROR
     */
    public void e(String msg, Throwable t) {
        if (logLevel.getCode() <= LogLevel.ERROR.getCode()) {
            Log.e(tag, msg, t);
        }
    }

    public void e(String msg) {
        if (logLevel.getCode() <= LogLevel.ERROR.getCode()) {
            Log.e(tag, msg);
        }
    }

    /*
     * VERBOSE
     */
    public void v(String msg, Throwable t) {
        if (logLevel.getCode() <= LogLevel.VERBOSE.getCode()) {
            Log.v(tag, msg, t);
        }
    }

    public void v(String msg) {
        if (logLevel.getCode() <= LogLevel.VERBOSE.getCode()) {
            Log.v(tag, msg);
        }
    }

    /*
     * WARN
     */
    public void w(String msg, Throwable t) {
        if (logLevel.getCode() <= LogLevel.WARNING.getCode()) {
            Log.w(tag, msg, t);
        }
    }

    public void w(String msg) {
        if (logLevel.getCode() <= LogLevel.WARNING.getCode()) {
            Log.w(tag, msg);
        }
    }
}
