package fr.trackoe.decheterie.ui.activity;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import fr.trackoe.decheterie.R;
import fr.trackoe.decheterie.Utils;
import fr.trackoe.decheterie.configuration.Configuration;
import fr.trackoe.decheterie.database.ModulesDB;
import fr.trackoe.decheterie.model.Const;
import fr.trackoe.decheterie.model.Datas;
import fr.trackoe.decheterie.model.bean.global.ApkInfos;
import fr.trackoe.decheterie.model.bean.global.Module;
import fr.trackoe.decheterie.model.bean.global.Modules;
import fr.trackoe.decheterie.model.bean.global.TabletteInfos;
import fr.trackoe.decheterie.model.bean.global.Users;
import fr.trackoe.decheterie.service.callback.DataCallback;
import fr.trackoe.decheterie.service.receiver.NetworkStateReceiver;
import fr.trackoe.decheterie.ui.fragment.AccueilFragment;
import fr.trackoe.decheterie.ui.fragment.LoginFragment;
import fr.trackoe.decheterie.ui.fragment.SettingsFragment;
import fr.trackoe.decheterie.ui.fragment.TabletteFragment;
import fr.trackoe.decheterie.widget.WriteUsersTask;

public class ContainerActivity extends FragmentActivity {
    private static final String CURRENT_FRAG_TAG = "CURRENT_FRAGMENT";
    private static final String APK_NAME = "decheterie.apk";
    private static final String DOWNLOAD = "/download/";
    private static final int PERMISSION_CAMERA = 998756485;
    private Context activity;

    private AlertDialog.Builder errorDialogBuilder;
    private AlertDialog errorDialog;

    private ActionBar actionbar;
    private LinearLayout actionbarLeftItem;
    private TextView actionbarLeftItemText;
    private TextView actionbarTitle;
    private LinearLayout actionbarRightItem;
    private TextView actionbarRightItemText;
    private LocationListener ls;
    private LocationManager locationManager;

    private ArrayList<String> urlsReleve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        activity = this;
        initSharedPreference(activity);
        initActionBar();

        if (getResources().getBoolean(R.bool.landscape)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }

        verifyStoragePermissions(ContainerActivity.this);

        launchOnlineAction();

        // Si on a déja un numéro de tablette on affiche directement l'écran de login
        if (Utils.isStringEmpty(Configuration.getNumeroTablette())) {
            //changeMainFragment(new TabletteFragment(), false, false, 0, 0, 0, 0);
            changeMainFragment(new AccueilFragment(), false, false, 0, 0, 0, 0);
        } else {
            //changeMainFragment(new LoginFragment(), false, false, 0, 0, 0, 0);
            changeMainFragment(new AccueilFragment(), false, false, 0, 0, 0, 0);
        }

        // Installation d'une nouvelle version de l'application
        if (Configuration.getIsApkReadyToInstall()) {
            displayDialogInstallNewApk();
        }

        registerReceiver(networkStateReceiver, new IntentFilter("INTERNET_OK"));

        getMACaddress();

    }

    public void launchOnlineAction() {
        if (activity != null && Utils.isInternetConnected(activity) && !Utils.isStringEmpty(Configuration.getNumeroTablette())) {
            loadAllWS();

            if (!Configuration.getIsApkReadyToInstall() && !Utils.isStringEmpty(Configuration.getUrlApk())) {
                downloadNewVersion(Configuration.getUrlApk());
            }
        }
    }

    NetworkStateReceiver networkStateReceiver = new NetworkStateReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
            launchOnlineAction();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkStateReceiver);
    }


    // Trigger sur connexion automatique au réseau: faire un refresh des infos
    public void loadAllWS() {
        if (!Utils.isStringEmpty(Configuration.getNumeroTablette())) {
            //loadApkUpdate();
            //loadInfosTablette(numTablette);
            //loadUsers(numTablette);
            //loadModules(numTablette);
            //loadListeVille(numTablette);
        }
    }

    // Récupération des informations
    // Récupération des utilisateurs
    public void loadUsers(String numTablette) {
        if (activity != null && Utils.isInternetConnected(activity)) {
            displayUsersLoader(false);
            Datas.loadUsers(activity, new DataCallback<Users>() {
                @Override
                public void dataLoaded(Users users) {
                    if (activity != null) {
                        if (users.ismSuccess()) {
                            // Insérer un user si il n'est pas présent dans la table
                            new WriteUsersTask(activity, users).execute();
                            Configuration.saveIsUsersSuccess(true);
                            displayUsersLoader(true);
                        } else {
                            // Modifier Gestion d'Affichage des erreurs
                            Configuration.saveUsersError(users.getmError());
                            Configuration.saveIsUsersSuccess(false);
                            displayUsersLoader(true);
                        }
                    }
                }
            }, numTablette);
        }
    }

    public void displayUsersLoader(boolean loaded) {
        Configuration.saveIsUsersLoaded(loaded);
        if (getCurrentFragment() instanceof SettingsFragment) {
            ((SettingsFragment) getCurrentFragment()).displayUsersLoader();
        }
    }


    // Récupération de la mise à jour de l'appli
    public void loadApkUpdate() {
        if (activity != null && Utils.isInternetConnected(activity)) {
            PackageInfo pInfo = null;
            try {
                pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                int versionCode = pInfo.versionCode;
                Datas.loadUploadApk(activity, new DataCallback<ApkInfos>() {
                    @Override
                    public void dataLoaded(ApkInfos apkInfos) {
                        if (activity != null && apkInfos.ismSuccess()) {
                            Configuration.saveUrlApk(apkInfos.getUrl());
                            Configuration.saveDescApk(apkInfos.getDescription());
                            if (!Configuration.getIsApkReadyToInstall()) {
                                downloadNewVersion(apkInfos.getUrl());
                            }
                        }
                    }
                }, versionCode);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // Récupération des infos liées à la tablette
    public void loadInfosTablette(final String numTablette) {
        if (activity != null && Utils.isInternetConnected(activity)) {
            displayTabletteLoader(false);
            Datas.loadInfos(activity, new DataCallback<TabletteInfos>() {
                @Override
                public void dataLoaded(final TabletteInfos infos) {
                    if (activity != null) {
                        // Stockage de la date
                        String dateMAJ;
                        Date d = Calendar.getInstance().getTime();
                        DateFormat df = new SimpleDateFormat(getString(R.string.settings_date_format));
                        dateMAJ = df.format(d);
                        Configuration.saveDateMAJ(dateMAJ);

                        if (infos.ismSuccess()) {
                            // Stocker dans les sharedPrefs
                            Configuration.saveNumeroTablette(numTablette);
                            Configuration.saveNomTablette(infos.getNomTablette());
                            Configuration.saveNomOpCl(infos.getNomClient());
                            Configuration.saveIdOpCl(infos.getClientId());
//                            Configuration.saveNewIdIntervention(activity, infos.getIdNewInter() + 1);
                            Configuration.saveIdAccount(infos.getAccountId());
                            Configuration.saveIsInfosTabletteSuccess(true);
                            displayTabletteLoader(true);
                            if (getCurrentFragment() instanceof TabletteFragment) {
                                changeMainFragment(new LoginFragment(), false);
                                SettingsFragment frag = new SettingsFragment();
                                Bundle bundle = new Bundle();
                                bundle.putBoolean(Const.IS_FIRST_LAUNCH, true);
                                frag.setArguments(bundle);
                                changeMainFragment(frag, true);
                            }
                            if (getCurrentFragment() instanceof SettingsFragment) {
                                ((SettingsFragment) getCurrentFragment()).setMajDate();
                            }
                        } else {
                            // Gestion d'Affichage des erreurs
                            showSimpleAlertDialog(ContainerActivity.this, getString(R.string.error_title_information), infos.getmError());
                            Configuration.saveInfosTabletteError(infos.getmError());
                            Configuration.saveIsInfosTabletteSuccess(false);
                            displayTabletteLoader(true);
                        }
                    }
                }
            }, numTablette);
        }
    }

    public void displayTabletteLoader(boolean loaded) {
        Configuration.saveIsInfosTabletteLoaded(loaded);
        if (getCurrentFragment() instanceof SettingsFragment) {
            ((SettingsFragment) getCurrentFragment()).displayTabletteLoader();
        }
    }

    // Récupération des modules
    public void loadModules(String numTablette) {
        if (activity != null && Utils.isInternetConnected(activity)) {
            displayModulesLoader(false);
            Datas.loadModules(activity, new DataCallback<Modules>() {
                @Override
                public void dataLoaded(Modules modules) {
                    if (activity != null) {
                        if (modules.ismSuccess()) {
                            ModulesDB modulesDB = new ModulesDB(activity);
                            modulesDB.open();
                            modulesDB.clearModules();
                            for (Module m : modules.getListModules()) {
                                try {
                                    modulesDB.insertModule(m);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            modulesDB.close();
                            Configuration.saveIsModulesSuccess(true);
                            displayModulesLoader(true);
                        } else {
                            // Gestion d'Affichage des erreurs
                            Configuration.saveModulesError(modules.getmError());
                            Configuration.saveIsModulesSuccess(false);
                            displayModulesLoader(true);
                        }
                    }
                }
            }, numTablette);
        }
    }

    public void displayModulesLoader(boolean loaded) {
        Configuration.saveIsModulesLoaded(loaded);
        if (getCurrentFragment() instanceof SettingsFragment) {
            ((SettingsFragment) getCurrentFragment()).displayModulesLoader();
        }
    }



    public Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentByTag(CURRENT_FRAG_TAG);
    }

    /*
	 * Navigation à travers les fragments
	 */
    public void changeMainFragment(Fragment frag, boolean addToBackStack) {
        changeMainFragment(frag, addToBackStack, false, R.anim.slide_in, R.anim.slide_out, R.anim.back_slide_in, R.anim.back_slide_out);
    }

    public void changeMainFragment(Fragment frag, boolean addToBackStack, boolean addFrag, int InAnimationRes, int OutAnimationRes, int backInAnimationRes, int backOutAnimationRes) {
        Utils.hideSoftKeyboard(this);
        if (!addToBackStack) {
            getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            InAnimationRes = 0;
            OutAnimationRes = 0;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        if (InAnimationRes > 0 || OutAnimationRes > 0 || backInAnimationRes > 0 || backOutAnimationRes > 0) {
            if (backInAnimationRes > 0 && backOutAnimationRes > 0) {
                transaction.setCustomAnimations(InAnimationRes, OutAnimationRes, backInAnimationRes, backOutAnimationRes);
            } else {
                transaction.setCustomAnimations(InAnimationRes, OutAnimationRes);
            }
        }

        if (addFrag) {
            transaction.add(R.id.fragment_container, frag, CURRENT_FRAG_TAG);
        } else {
            transaction.replace(R.id.fragment_container, frag, CURRENT_FRAG_TAG);
        }
        transaction.commit();
    }

    /*
	 * Definition OnBackPressed()
	 */
    @Override
    public void onBackPressed() {
        // Gestion rotation écran
        if (getResources().getBoolean(R.bool.landscape)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }

        // On cache le clavier
        Utils.hideSoftKeyboard(this);

        try {
            super.onBackPressed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
	 * Init SharedPreference (Récupération des données sauvegardées dans l'appli)
	 */
    public void initSharedPreference(Context ctx) {
        Configuration.initSharedPreference(ctx);
    }


    /*
	 * Alert Dialog
	 */
    public void showSimpleAlertDialog(Context ctx, String title, String message) {
        errorDialogBuilder = new AlertDialog.Builder(ctx);
        errorDialogBuilder.setTitle(title).setMessage(message).setPositiveButton(R.string.error_btn_ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        errorDialog = errorDialogBuilder.create();
        errorDialog.show();
        ((TextView) errorDialog.findViewById(android.R.id.message)).setGravity(Gravity.CENTER);
        if (getResources().getBoolean(R.bool.landscape)) {
            errorDialog.getWindow().setLayout(getResources().getDimensionPixelSize(R.dimen.error_width), LinearLayout.LayoutParams.WRAP_CONTENT);
        } else {
            errorDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }

    }

    public void showSimpleAlertDialogWithNegativeButton(Context ctx, String title, String message, DialogInterface.OnClickListener negativeButtonListener, DialogInterface.OnClickListener positiveButtonListener) {
        errorDialogBuilder = new AlertDialog.Builder(ctx);
        errorDialogBuilder.setTitle(title).setMessage(message).setPositiveButton(R.string.error_btn_ok, positiveButtonListener);
        errorDialogBuilder.setNegativeButton(R.string.error_btn_annuler, negativeButtonListener);

        errorDialog = errorDialogBuilder.create();
        errorDialog.show();
        ((TextView) errorDialog.findViewById(android.R.id.message)).setGravity(Gravity.CENTER);
        errorDialog.getWindow().setLayout(getResources().getDimensionPixelSize(R.dimen.error_width), LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public void showSimpleAlertDialogQuitApplication() {
        if(errorDialog != null) {
            errorDialog.dismiss();
        }
        showSimpleAlertDialogWithNegativeButton(activity, getString(R.string.error_title_information), getString(R.string.error_quit_application), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
    }

    public void showSimpleAlertDialogWithNegativeButton(Context ctx, String title, String message, String negativeButtonText, DialogInterface.OnClickListener negativeButtonListener, String positiveButtonText, DialogInterface.OnClickListener positiveButtonListener) {
        errorDialogBuilder = new AlertDialog.Builder(ctx);
        errorDialogBuilder.setTitle(title).setMessage(message).setPositiveButton(positiveButtonText, positiveButtonListener);
        errorDialogBuilder.setNegativeButton(negativeButtonText, negativeButtonListener);

        errorDialog = errorDialogBuilder.create();
        errorDialog.show();
        ((TextView) errorDialog.findViewById(android.R.id.message)).setGravity(Gravity.CENTER);
        errorDialog.getWindow().setLayout(getResources().getDimensionPixelSize(R.dimen.error_width), LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    /*
     ActionBar
     */
    public void initActionBar() {
        // Implémentation de l'ActionBar avec une CustomView
        actionbar = getActionBar();
        actionbar.setDisplayShowHomeEnabled(false);
        actionbar.setDisplayShowTitleEnabled(false);
        actionbar.setDisplayShowCustomEnabled(true);
        actionbar.setCustomView(R.layout.actionbar_activity);

        // Implémentation des TextViews qui vont être utiliser
        actionbarLeftItem = (LinearLayout) findViewById(R.id.actionbar_left);
        actionbarLeftItemText = (TextView) findViewById(R.id.actionbar_left_txt);
        actionbarTitle = (TextView) findViewById(R.id.actionbar_title);
        actionbarRightItem = (LinearLayout) findViewById(R.id.actionbar_right);
        actionbarRightItemText = (TextView) findViewById(R.id.actionbar_right_txt);
    }

    public void showBackArrowinNavBar(boolean visible) {
        if (visible) {
            findViewById(R.id.actionbar_left_arrow).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.actionbar_left_arrow).setVisibility(View.GONE);
        }
    }

    public void showToolboxinNavBar(boolean visible) {
        if (visible) {
            findViewById(R.id.actionbar_right_toolbox).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.actionbar_right_toolbox).setVisibility(View.GONE);
        }
    }

    public void showCamerainNavBar(boolean visible) {
        if (visible) {
            findViewById(R.id.actionbar_right_camera).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.actionbar_right_camera).setVisibility(View.GONE);
        }
    }

    public void showActionBarTablette() {
        // Affichage back arrow
        showBackArrowinNavBar(false);
        showToolboxinNavBar(false);
        showCamerainNavBar(false);

        actionbarTitle.setText(getString(R.string.app_name));

        // Définition des actions au click des items
        actionbarTitle.setVisibility(View.VISIBLE);
        actionbarRightItem.setVisibility(View.GONE);
        actionbarLeftItem.setVisibility(View.GONE);
    }

    public void showActionBarSettings(boolean firstLaunch) {
        // Affichage back arrow
        showBackArrowinNavBar(true);
        showToolboxinNavBar(false);
        showCamerainNavBar(false);

        actionbarTitle.setText(getString(R.string.ac_parametres));

        if (firstLaunch) {
            actionbarLeftItemText.setText(getString(R.string.ac_connexion));
        } else {
            actionbarLeftItemText.setText(getString(R.string.ac_retour));
        }

        actionbarLeftItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Définition des actions au click des items
        actionbarTitle.setVisibility(View.VISIBLE);
        actionbarRightItem.setVisibility(View.GONE);
        actionbarLeftItem.setVisibility(View.VISIBLE);
    }

    public void showActionBarLogin() {
        // Affichage back arrow
        showBackArrowinNavBar(false);
        showToolboxinNavBar(true);
        showCamerainNavBar(false);

        if (getResources().getBoolean(R.bool.landscape)) {
            actionbarTitle.setText(getString(R.string.ac_connexion_title));
        } else {
            actionbarTitle.setText(getString(R.string.ac_connexion_title_5p));
        }
        actionbarRightItemText.setText(getString(R.string.ac_parametres));

        actionbarRightItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMainFragment(new SettingsFragment(), true);
            }
        });

        // Définition des actions au click des items
        actionbarTitle.setVisibility(View.VISIBLE);
        actionbarRightItem.setVisibility(View.VISIBLE);
        actionbarLeftItem.setVisibility(View.GONE);
    }

    public void showActionBarModules() {
        // Affichage back arrow
        showBackArrowinNavBar(false);
        showToolboxinNavBar(true);
        showCamerainNavBar(false);

        if (getResources().getBoolean(R.bool.landscape)) {
            actionbarTitle.setText(getString(R.string.ac_modules));
        } else {
            actionbarTitle.setText(getString(R.string.ac_modules_5p));
        }
        actionbarRightItemText.setText(getString(R.string.ac_parametres));

        actionbarRightItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMainFragment(new SettingsFragment(), true);
            }
        });

        actionbarLeftItemText.setText(getString(R.string.ac_deconnexion));

        actionbarLeftItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configuration.removeIdUser();

                changeMainFragment(new LoginFragment(), false);
            }
        });

        // Définition des actions au click des items
        actionbarTitle.setVisibility(View.VISIBLE);
        actionbarRightItem.setVisibility(View.VISIBLE);
        actionbarLeftItem.setVisibility(View.VISIBLE);
    }

    public void getMACaddress() {
        Configuration.saveMACAddress(getUniqueCodeMac());
    }

    public String getUniqueCodeMac() {
        String macHash = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        return String.valueOf(macHash.hashCode());
    }

    public void displayDialogInstallNewApk() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String desc = getString(R.string.dialog_maj_dispo_mess);
                if (!Utils.isStringEmpty(Configuration.getDescApk())) {
                    desc += String.format(getString(R.string.dialog_maj_dispo_desc), Configuration.getDescApk());
                }

                showSimpleAlertDialogWithNegativeButton(ContainerActivity.this, getString(R.string.dialog_maj_dispo_title), desc, getString(R.string.dialog_maj_dispo_ko), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }, getString(R.string.dialog_maj_dispo_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        installNewApk();
                    }
                });
            }
        });

    }

    public void installNewApk() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + DOWNLOAD + APK_NAME)), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        Configuration.saveIsApkReadyToInstall(false);
        Configuration.removeUrlApk();
        Configuration.removeDescApk();
    }

    public void downloadNewVersion(final String apkurl) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(apkurl);
                    HttpURLConnection c = (HttpURLConnection) url.openConnection();
                    c.setRequestMethod("GET");
                    c.setDoOutput(false);
                    c.connect();

                    String PATH = Environment.getExternalStorageDirectory() + DOWNLOAD;
                    File file = new File(PATH);
                    file.mkdirs();
                    File outputFile = new File(file, APK_NAME);
                    FileOutputStream fos = new FileOutputStream(outputFile);

                    InputStream is = c.getInputStream();

                    byte[] buffer = new byte[1024];
                    int len1 = 0;
                    while ((len1 = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, len1);
                    }
                    fos.close();
                    is.close();

                    Configuration.saveIsApkReadyToInstall(true);
                    displayDialogInstallNewApk();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 2;
    private static final int REQUEST_NETWORK_STATE = 3;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static String[] PERMISSIONS_READ_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private static String[] PERMISSIONS_NETWORK_STATE = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

        permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_READ_STORAGE,
                    REQUEST_READ_EXTERNAL_STORAGE
            );
        }
    }

    public long generateCodeFromDateAndNumTablette() {
        String numTablette = Configuration.getNumeroTablette();
        long codeRet;
        if(Utils.isNumeric(numTablette)) {
            numTablette = numTablette;
        } else {
            numTablette = getUniqueCodeMac();
        }

        if(numTablette.length() > 8) {
            numTablette = numTablette.substring(0,7);
        }

        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat(getString(R.string.db_date_format));

        String date = df.format(d);

        codeRet = Long.parseLong(numTablette + date);

        return codeRet;
    }

}
