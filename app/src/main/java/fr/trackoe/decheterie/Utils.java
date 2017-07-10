package fr.trackoe.decheterie;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.view.inputmethod.InputMethodManager;

/**
 * Created with IntelliJ IDEA. User: Vincent Date: 27/05/13 Time: 15:34 To change this template use File | Settings | File Templates.
 */
public class Utils {

    /**
     * Indique si une connexion de donnees est possible.
     *
     * @param ctx le Context de l' application, de l'activity
     * @return true si le device a acces à internet, false sinon
     */
    public static boolean isInternetConnected(Context ctx) {
        try {
            ConnectivityManager connectivityMng = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            return connectivityMng.getActiveNetworkInfo() != null && connectivityMng.getActiveNetworkInfo().isConnected();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Indique si une connexion de donnees WIFI est possible.
     *
     * @param ctx le Context de l' application, de l'activity
     * @return true si le device a acces à internet grace au WIFI, false sinon
     */
    public static boolean isWifiConnected(Context ctx) {
        ConnectivityManager connectivityMng = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityMng.getActiveNetworkInfo() != null && connectivityMng.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }

    private static int _screenWidth = 0;
    private static int _screenHeight = 0;

    public static int getScreenWidth(Context ctx) {
        if (_screenWidth < 1) {
            _screenWidth = ctx.getResources().getDisplayMetrics().widthPixels;
        }
        return _screenWidth;
    }

    public static int getScreenHeight(Context ctx) {
        if (_screenHeight < 1) {
            _screenHeight = ctx.getResources().getDisplayMetrics().heightPixels;
        }
        return _screenHeight;
    }

    public static String convertStreamToString(InputStream stream) {
        String responseAsString = null;

        byte[] b;
        try {
            b = new byte[stream.available()];
            stream.read(b);
            responseAsString = convertStreamToString(b);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return responseAsString;
    }

    public static String convertStreamToString(byte[] stream) {
        String responseAsString = null;

        try {
            responseAsString = new String(stream, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            responseAsString = new String(stream);
        }

        return responseAsString;
    }

    public static boolean isStringEmpty(String s) {
        return s == null || s.equals("") || s.equals(" ") || s.equals("null");
    }

    public static String getVersionNumber(Context ctx) {
        try {
            return ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), PackageManager.GET_CONFIGURATIONS).versionName;
        } catch (NameNotFoundException e) {
            return "???";
        }
    }

    public static void call(Context ctx, String phoneNb) {
        String url = "tel:" + phoneNb;
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
        try {
            ctx.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendMail(Context ctx, String desti) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{desti});
        try {
            ctx.startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (Exception e) {
        }
    }

    public static void hideSoftKeyboard(Activity ctx) {
        try {
            InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Activity.INPUT_METHOD_SERVICE);
            // imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            if (imm != null && ctx.getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(ctx.getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {
        }
        // InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        // if (inputMethodManager != null && getCurrentFocus() != null) {
        // inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        // }
    }

    public static Boolean isMobileAvailable(Context appcontext) {
        TelephonyManager tel = (TelephonyManager) appcontext.getSystemService(Context.TELEPHONY_SERVICE);
        return ((tel.getNetworkOperator() != null && tel.getNetworkOperator().equals("")) ? false : true);
    }

    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    //Date --> yyyyMMddHHmmss
    public static String changeDateToString(Date date){
        try {
            return (new SimpleDateFormat("yyyyMMddHHmmss")).format(date);
        }
        catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    //yyyyMMddHHmmss --> Date
    public static Date changeStringToDate(String string){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Date d = sdf.parse(string);
            return d;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
