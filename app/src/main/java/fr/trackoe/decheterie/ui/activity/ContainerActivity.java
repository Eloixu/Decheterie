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
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.idescout.sql.SqlScoutServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.trackoe.decheterie.R;
import fr.trackoe.decheterie.Utils;
import fr.trackoe.decheterie.configuration.Configuration;
import fr.trackoe.decheterie.database.DchAccountFluxSettingDB;
import fr.trackoe.decheterie.database.DchAccountSettingDB;
import fr.trackoe.decheterie.database.DchCarteActiveDB;
import fr.trackoe.decheterie.database.DchCarteDB;
import fr.trackoe.decheterie.database.DchCarteEtatRaisonDB;
import fr.trackoe.decheterie.database.DchChoixDecompteTotalDB;
import fr.trackoe.decheterie.database.DchComptePrepayeDB;
import fr.trackoe.decheterie.database.DchDecheterieFluxDB;
import fr.trackoe.decheterie.database.DchDepotDB;
import fr.trackoe.decheterie.database.DchFluxDB;
import fr.trackoe.decheterie.database.DchTypeCarteDB;
import fr.trackoe.decheterie.database.DchUniteDB;
import fr.trackoe.decheterie.database.DecheterieDB;
import fr.trackoe.decheterie.database.HabitatDB;
import fr.trackoe.decheterie.database.IconDB;
import fr.trackoe.decheterie.database.LocalDB;
import fr.trackoe.decheterie.database.MenageDB;
import fr.trackoe.decheterie.database.ModulesDB;
import fr.trackoe.decheterie.database.TypeHabitatDB;
import fr.trackoe.decheterie.database.UsagerDB;
import fr.trackoe.decheterie.database.UsagerHabitatDB;
import fr.trackoe.decheterie.database.UsagerMenageDB;
import fr.trackoe.decheterie.model.Const;
import fr.trackoe.decheterie.model.Datas;
import fr.trackoe.decheterie.model.bean.global.AccountFluxSetting;
import fr.trackoe.decheterie.model.bean.global.AccountSetting;
import fr.trackoe.decheterie.model.bean.global.AccountSettings;
import fr.trackoe.decheterie.model.bean.global.ApkInfos;
import fr.trackoe.decheterie.model.bean.global.Carte;
import fr.trackoe.decheterie.model.bean.global.CarteActive;
import fr.trackoe.decheterie.model.bean.global.CarteActives;
import fr.trackoe.decheterie.model.bean.global.CarteEtatRaison;
import fr.trackoe.decheterie.model.bean.global.CarteEtatRaisons;
import fr.trackoe.decheterie.model.bean.global.Cartes;
import fr.trackoe.decheterie.model.bean.global.ChoixDecompteTotals;
import fr.trackoe.decheterie.model.bean.global.ComptePrepaye;
import fr.trackoe.decheterie.model.bean.global.ComptePrepayes;
import fr.trackoe.decheterie.model.bean.global.DecheterieFlux;
import fr.trackoe.decheterie.model.bean.global.DecheterieFluxs;
import fr.trackoe.decheterie.model.bean.global.Decheteries;
import fr.trackoe.decheterie.model.bean.global.Depot;
import fr.trackoe.decheterie.model.bean.global.Flux;
import fr.trackoe.decheterie.model.bean.global.Fluxs;
import fr.trackoe.decheterie.model.bean.global.TypeCartes;
import fr.trackoe.decheterie.model.bean.global.Unites;
import fr.trackoe.decheterie.model.bean.usager.Habitats;
import fr.trackoe.decheterie.model.bean.global.Icon;
import fr.trackoe.decheterie.model.bean.usager.Locaux;
import fr.trackoe.decheterie.model.bean.usager.Menages;
import fr.trackoe.decheterie.model.bean.global.Module;
import fr.trackoe.decheterie.model.bean.global.Modules;
import fr.trackoe.decheterie.model.bean.global.TabletteInfos;
import fr.trackoe.decheterie.model.bean.global.TypeCarte;
import fr.trackoe.decheterie.model.bean.global.TypeHabitat;
import fr.trackoe.decheterie.model.bean.global.TypeHabitats;
import fr.trackoe.decheterie.model.bean.global.Unite;
import fr.trackoe.decheterie.model.bean.usager.UsagerHabitats;
import fr.trackoe.decheterie.model.bean.usager.UsagerMenages;
import fr.trackoe.decheterie.model.bean.usager.Usagers;
import fr.trackoe.decheterie.model.bean.global.Users;
import fr.trackoe.decheterie.model.bean.usager.Habitat;
import fr.trackoe.decheterie.model.bean.usager.Local;
import fr.trackoe.decheterie.model.bean.usager.Menage;
import fr.trackoe.decheterie.model.bean.usager.Usager;
import fr.trackoe.decheterie.model.bean.usager.UsagerHabitat;
import fr.trackoe.decheterie.model.bean.usager.UsagerMenage;
import fr.trackoe.decheterie.service.callback.DataAndErrorCallback;
import fr.trackoe.decheterie.service.callback.DataCallback;
import fr.trackoe.decheterie.service.receiver.NetworkStateReceiver;
import fr.trackoe.decheterie.ui.dialog.CustomDialogOnBackPressed;
import fr.trackoe.decheterie.ui.fragment.AccueilFragment;
import fr.trackoe.decheterie.ui.fragment.ApportProFragment;
import fr.trackoe.decheterie.ui.fragment.DepotFragment;
import fr.trackoe.decheterie.ui.fragment.DrawerLocker;
import fr.trackoe.decheterie.ui.fragment.IdentificationFragment;
import fr.trackoe.decheterie.ui.fragment.LoadingFragment;
import fr.trackoe.decheterie.ui.fragment.LoginFragment;
import fr.trackoe.decheterie.ui.fragment.RechercherUsagerFragment;
import fr.trackoe.decheterie.ui.fragment.SettingsFragment;
import fr.trackoe.decheterie.ui.fragment.TabletteFragment;
import fr.trackoe.decheterie.widget.WriteUsersTask;

public class ContainerActivity extends AppCompatActivity implements DrawerLocker{
    private static final String CURRENT_FRAG_TAG = "CURRENT_FRAGMENT";
    private static final String APK_NAME = "decheterie.apk";
    private static final String DOWNLOAD = "/download/";
    private static final int PERMISSION_CAMERA = 998756485;
    private Context activity;

    private AlertDialog.Builder errorDialogBuilder;
    private AlertDialog errorDialog;

    private ActionBar actionbar;
    Toolbar toolbar;
    private LinearLayout actionbarLeftItem;
    private TextView actionbarLeftItemText;
    private TextView actionbarTitle;
    private LinearLayout actionbarRightItem;
    private TextView actionbarRightItemText;
    private LocationListener ls;
    private LocationManager locationManager;
    private Button parametres;

    private ArrayList<String> urlsReleve;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    FragmentTransaction fragmentTransaction;

    private String barcode;
    private NfcAdapter mNfcAdapter;

    //NFC
    private List<Tag> mTags = new ArrayList<>();
    private String idCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SqlScoutServer.create(this, getPackageName());
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_container);

        //initDB();
        initDBTest();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) toolbar.getLayoutParams();
        layoutParams.height = 50;
        toolbar.setLayoutParams(layoutParams);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        hideHamburgerButton();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //disable the navigation drawer
        setDrawerEnabled(false);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        activity = this;
        initSharedPreference(activity);
        //initActionBar();
        parametres = (Button) findViewById(R.id.toolbar_parametres_button);
        parametres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    changeMainFragment(new SettingsFragment(), true);
            }
        });

        if (getResources().getBoolean(R.bool.landscape)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }

        verifyStoragePermissions(ContainerActivity.this);

        launchOnlineAction();

        /*// Si on a déja un numéro de tablette on affiche directement l'écran de login
        if (Utils.isStringEmpty(Configuration.getNumeroTablette())) {
            changeMainFragment(new TabletteFragment(), false, false, 0, 0, 0, 0);

        } else {
            //changeMainFragment(new LoginFragment(), false, false, 0, 0, 0, 0);
            changeMainFragment(new LoginFragment(), false, false, 0, 0, 0, 0);
        }*/

        changeMainFragment(new LoadingFragment(), false, false, 0, 0, 0, 0);

        // Installation d'une nouvelle version de l'application
        if (Configuration.getIsApkReadyToInstall()) {
            displayDialogInstallNewApk();
        }

        registerReceiver(networkStateReceiver, new IntentFilter("INTERNET_OK"));

        getMACaddress();

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(mNfcAdapter != null) {
            //Handle some NFC initialization here
            Toast.makeText(this, "NFC available on this device",
                    Toast.LENGTH_SHORT).show();
            if (!mNfcAdapter.isEnabled())
            {
                Toast.makeText(getApplicationContext(), "Please activate NFC and press Back to return to the application!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
            }
        }
        else {
            Toast.makeText(this, "NFC not available on this device",
                    Toast.LENGTH_SHORT).show();}

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

    // Récupération des habitats
    public void loadTypeHabitat() {
        if (activity != null && Utils.isInternetConnected(activity)) {
            Datas.loadTypeHabitat(activity, new DataAndErrorCallback<TypeHabitats>() {
                @Override
                public void dataLoadingFailed(boolean isInternetConnected, String errorMessage) {
                    Toast.makeText(activity, "typeHabitat loading failed",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void dataLoaded(TypeHabitats data) {
                    if (activity != null) {
                        if (data.ismSuccess()) {
                            try {
                                TypeHabitatDB thdb = new TypeHabitatDB(activity);
                                thdb.open();
                                thdb.clearTypeHabitat();
                                for (TypeHabitat th : data.getListTypeHabitat()) {
                                    thdb.insertTypeHabitat(th);
                                }
                                thdb.close();
                            } catch (Exception e) {
                            }
                        } else {
                        }
                    }
                }
            });

        }
    }

    public void loadHabitat(int idAccount) {
        if (activity != null && Utils.isInternetConnected(activity)) {
            Datas.loadAllHabitat(activity, new DataAndErrorCallback<Habitats>() {
                @Override
                public void dataLoadingFailed(boolean isInternetConnected, String errorMessage) {
                    Toast.makeText(activity, "habitat loading failed",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void dataLoaded(final Habitats data) {
                    try {
                        final HabitatDB hdb = new HabitatDB(activity);
                        hdb.open();
                        hdb.clearHabitat();
                        if(getCurrentFragment() instanceof LoadingFragment) {
                            ((LoadingFragment) getCurrentFragment()).getProgressBar().setMax(data.getListHabitat().size());
                        }
                        hdb.close();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    hdb.open();
                                    for(int i = 0; i < data.getListHabitat().size(); i++) {
                                        hdb.insertHabitat(data.getListHabitat().get(i));
                                        if(getCurrentFragment() instanceof LoadingFragment) {
                                            final int finalI = i;
                                            ((LoadingFragment) getCurrentFragment()).getProgressBar().setProgress(finalI);

                                        }
                                    }
                                    hdb.close();

                                    if(getCurrentFragment() instanceof LoadingFragment) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ((LoadingFragment) getCurrentFragment()).launchLocalAction();
                                            }
                                        });
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, idAccount);
        }
    }

    public void loadLocal(int idAccount) {
        if (activity != null && Utils.isInternetConnected(activity)) {
            Datas.loadAllLocal(activity, new DataAndErrorCallback<Locaux>() {
                @Override
                public void dataLoadingFailed(boolean isInternetConnected, String errorMessage) {
                    Toast.makeText(activity, "local loading failed",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void dataLoaded(final Locaux data) {
                    try {
                        final LocalDB ldb = new LocalDB(activity);
                        ldb.open();
                        ldb.clearLocal();
                        if(getCurrentFragment() instanceof LoadingFragment) {
                            ((LoadingFragment) getCurrentFragment()).getProgressBar().setMax(data.getListLocal().size());
                        }
                        ldb.close();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                ldb.open();
                                for(int i = 0; i < data.getListLocal().size(); i++) {
                                    ldb.insertLocal(data.getListLocal().get(i));
                                    if( getCurrentFragment() instanceof LoadingFragment) {
                                        final int finalI = i;
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ((LoadingFragment) getCurrentFragment()).getProgressBar().setProgress(finalI);
                                            }
                                        });
                                    }
                                }
                                ldb.close();

                                if(getCurrentFragment() instanceof LoadingFragment) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ((LoadingFragment) getCurrentFragment()).launchMenageAction();
                                        }
                                    });
                                }
                            }
                        }).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, idAccount);

        }
    }

    public void loadMenage(int idAccount) {
        if (activity != null && Utils.isInternetConnected(activity)) {
            Datas.loadAllMenage(activity, new DataAndErrorCallback<Menages>() {
                @Override
                public void dataLoadingFailed(boolean isInternetConnected, String errorMessage) {

                }

                @Override
                public void dataLoaded(final Menages data) {
                    try {
                        final MenageDB mdb = new MenageDB(activity);
                        mdb.open();
                        mdb.clearMenage();
                        if(getCurrentFragment() instanceof LoadingFragment) {
                            ((LoadingFragment) getCurrentFragment()).getProgressBar().setMax(data.getListMenage().size());
                        }
                        mdb.close();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mdb.open();
                                for(int i = 0; i < data.getListMenage().size(); i++) {
                                    mdb.insertMenage(data.getListMenage().get(i));
                                    if(getCurrentFragment() instanceof LoadingFragment) {
                                        final int finalI = i;
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ((LoadingFragment) getCurrentFragment()).getProgressBar().setProgress(finalI);
                                            }
                                        });
                                    }
                                }
                                mdb.close();

                                if(getCurrentFragment() instanceof LoadingFragment) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ((LoadingFragment) getCurrentFragment()).launchUsagerAction();
                                        }
                                    });
                                }
                            }
                        }).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, idAccount);
        }
    }

    public void loadUsager(int idAccount) {
        if (activity != null && Utils.isInternetConnected(activity)) {
            Datas.loadAllUsager(activity, new DataAndErrorCallback<Usagers>() {
                @Override
                public void dataLoadingFailed(boolean isInternetConnected, String errorMessage) {

                }

                @Override
                public void dataLoaded(final Usagers data) {
                    try {
                        final UsagerDB udb = new UsagerDB(activity);
                        udb.open();
                        udb.clearUsager();
                        if(getCurrentFragment() instanceof LoadingFragment) {
                            ((LoadingFragment) getCurrentFragment()).getProgressBar().setMax(data.getListUsager().size());
                        }
                        udb.close();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                udb.open();
                                for(int i = 0; i < data.getListUsager().size(); i++) {
                                    udb.insertUsager(data.getListUsager().get(i));
                                    if(getCurrentFragment() instanceof LoadingFragment) {
                                        final int finalI = i;
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ((LoadingFragment) getCurrentFragment()).getProgressBar().setProgress(finalI);
                                            }
                                        });
                                    }
                                }
                                udb.close();

                                if(getCurrentFragment() instanceof LoadingFragment) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ((LoadingFragment) getCurrentFragment()).launchUsagerHabitatAction();
                                        }
                                    });
                                }
                            }
                        }).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, idAccount);
        }
    }

    public void loadUsagerHabitat(int idAccount) {
        if (activity != null && Utils.isInternetConnected(activity)) {
            Datas.loadAllUsagerHabitat(activity, new DataAndErrorCallback<UsagerHabitats>() {
                @Override
                public void dataLoadingFailed(boolean isInternetConnected, String errorMessage) {

                }

                @Override
                public void dataLoaded(final UsagerHabitats data) {
                    try {
                        final UsagerHabitatDB udb = new UsagerHabitatDB(activity);
                        udb.open();
                        udb.clearUsagerHabitat();
                        if(getCurrentFragment() instanceof LoadingFragment) {
                            ((LoadingFragment) getCurrentFragment()).getProgressBar().setMax(data.getListUsagerHabitat().size());
                        }
                        udb.close();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                udb.open();
                                for(int i = 0; i < data.getListUsagerHabitat().size(); i++) {
                                    udb.insertUsagerHabitat(data.getListUsagerHabitat().get(i));
                                    if(getCurrentFragment() instanceof LoadingFragment) {
                                        final int finalI = i;
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ((LoadingFragment) getCurrentFragment()).getProgressBar().setProgress(finalI);
                                            }
                                        });
                                    }
                                }
                                udb.close();

                                if(getCurrentFragment() instanceof LoadingFragment) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ((LoadingFragment) getCurrentFragment()).launchUsagerMenageAction();
                                        }
                                    });
                                }
                            }
                        }).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, idAccount);

        }
    }

    public void loadUsagerMenage(int idAccount) {
        if (activity != null && Utils.isInternetConnected(activity)) {
            Datas.loadAllUsagerMenage(activity, new DataAndErrorCallback<UsagerMenages>() {
                @Override
                public void dataLoadingFailed(boolean isInternetConnected, String errorMessage) {

                }

                @Override
                public void dataLoaded(final UsagerMenages data) {
                    try {
                        final UsagerMenageDB udb = new UsagerMenageDB(activity);
                        udb.open();
                        udb.clearUsagerMenage();
                        if(getCurrentFragment() instanceof LoadingFragment) {
                            ((LoadingFragment) getCurrentFragment()).getProgressBar().setMax(data.getListUsagerMenage().size());
                        }
                        udb.close();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                udb.open();
                                for(int i = 0; i < data.getListUsagerMenage().size(); i++) {
                                    udb.insertUsagerMenage(data.getListUsagerMenage().get(i));
                                    if(getCurrentFragment() instanceof LoadingFragment) {
                                        final int finalI = i;
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ((LoadingFragment) getCurrentFragment()).getProgressBar().setProgress(finalI);
                                            }
                                        });
                                    }
                                }
                                udb.close();

                                if(getCurrentFragment() instanceof LoadingFragment) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ((LoadingFragment) getCurrentFragment()).launchDecheterieAction();
                                        }
                                    });
                                }
                            }
                        }).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, idAccount);

        }
    }

    public void loadDecheterie(int idAccount) {
        if (activity != null && Utils.isInternetConnected(activity)) {
            Datas.loadAllDecheterie(activity, new DataAndErrorCallback<Decheteries>() {
                @Override
                public void dataLoadingFailed(boolean isInternetConnected, String errorMessage) {
                    Toast.makeText(activity, "déchèterie loading failed",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void dataLoaded(final Decheteries data) {
                    try {
                        final DecheterieDB ddb = new DecheterieDB(activity);
                        ddb.open();
                        ddb.clearDecheterie();
                        if(getCurrentFragment() instanceof LoadingFragment) {
                            ((LoadingFragment) getCurrentFragment()).getProgressBar().setMax(data.getListDecheterie().size());
                        }
                        ddb.close();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                ddb.open();
                                for(int i = 0; i < data.getListDecheterie().size(); i++) {
                                    ddb.insertDecheterie(data.getListDecheterie().get(i));
                                    if(getCurrentFragment() instanceof LoadingFragment) {
                                        final int finalI = i;
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ((LoadingFragment) getCurrentFragment()).getProgressBar().setProgress(finalI);
                                            }
                                        });
                                    }
                                }
                                ddb.close();

                                if(getCurrentFragment() instanceof LoadingFragment) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ((LoadingFragment) getCurrentFragment()).launchCarteAction();
                                        }
                                    });
                                }
                            }
                        }).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, idAccount);
        }
    }

    public void loadTypeCarte() {
        if (activity != null && Utils.isInternetConnected(activity)) {
            Datas.loadTypeCarte(activity, new DataAndErrorCallback<TypeCartes>() {
                @Override
                public void dataLoadingFailed(boolean isInternetConnected, String errorMessage) {
                    Toast.makeText(activity, "typeCarte loading failed",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void dataLoaded(final TypeCartes data) {
                    try {
                        final DchTypeCarteDB tcdb = new DchTypeCarteDB(activity);
                        tcdb.open();
                        tcdb.clearTypeCarte();
                        if(getCurrentFragment() instanceof LoadingFragment) {
                            ((LoadingFragment) getCurrentFragment()).getProgressBar().setMax(data.getListTypeCarte().size());
                        }
                        tcdb.close();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                tcdb.open();
                                for(int i = 0; i < data.getListTypeCarte().size(); i++) {
                                    tcdb.insertTypeCarte(data.getListTypeCarte().get(i));
                                    if(getCurrentFragment() instanceof LoadingFragment) {
                                        final int finalI = i;
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ((LoadingFragment) getCurrentFragment()).getProgressBar().setProgress(finalI);
                                            }
                                        });
                                    }
                                }
                                tcdb.close();

                            }
                        }).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void loadCarte(int idAccount) {
        if (activity != null && Utils.isInternetConnected(activity)) {
            Datas.loadAllCarte(activity, new DataAndErrorCallback<Cartes>() {
                @Override
                public void dataLoadingFailed(boolean isInternetConnected, String errorMessage) {
                    Toast.makeText(activity, "carte loading failed",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void dataLoaded(final Cartes data) {
                    try {
                        final DchCarteDB cdb = new DchCarteDB(activity);
                        cdb.open();
                        cdb.clearCarte();
                        if(getCurrentFragment() instanceof LoadingFragment) {
                            ((LoadingFragment) getCurrentFragment()).getProgressBar().setMax(data.getListCarte().size());
                        }
                        cdb.close();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                cdb.open();
                                for(int i = 0; i < data.getListCarte().size(); i++) {
                                    cdb.insertCarte(data.getListCarte().get(i));
                                    if(getCurrentFragment() instanceof LoadingFragment) {
                                        final int finalI = i;
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ((LoadingFragment) getCurrentFragment()).getProgressBar().setProgress(finalI);
                                            }
                                        });
                                    }
                                }
                                cdb.close();

                                if(getCurrentFragment() instanceof LoadingFragment) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ((LoadingFragment) getCurrentFragment()).launchCarteActiveAction();
                                        }
                                    });
                                }
                            }
                        }).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, idAccount);
        }
    }

    public void loadCarteEtatRaison() {
        if (activity != null && Utils.isInternetConnected(activity)) {
            Datas.loadCarteEtatRaison(activity, new DataAndErrorCallback<CarteEtatRaisons>() {
                @Override
                public void dataLoadingFailed(boolean isInternetConnected, String errorMessage) {
                    Toast.makeText(activity, "carte etat raison loading failed",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void dataLoaded(final CarteEtatRaisons data) {
                    try {
                        final DchCarteEtatRaisonDB cerdb = new DchCarteEtatRaisonDB(activity);
                        cerdb.open();
                        cerdb.clearCarteEtatRaison();
                        if(getCurrentFragment() instanceof LoadingFragment) {
                            ((LoadingFragment) getCurrentFragment()).getProgressBar().setMax(data.getListCarteEtatRaison().size());
                        }
                        cerdb.close();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                cerdb.open();
                                for(int i = 0; i < data.getListCarteEtatRaison().size(); i++) {
                                    cerdb.insertCarteEtatRaison(data.getListCarteEtatRaison().get(i));
                                    if(getCurrentFragment() instanceof LoadingFragment) {
                                        final int finalI = i;
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ((LoadingFragment) getCurrentFragment()).getProgressBar().setProgress(finalI);
                                            }
                                        });
                                    }
                                }
                                cerdb.close();

                            }
                        }).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void loadCarteActive(int idAccount) {
        if (activity != null && Utils.isInternetConnected(activity)) {
            Datas.loadAllCarteActive(activity, new DataAndErrorCallback<CarteActives>() {
                @Override
                public void dataLoadingFailed(boolean isInternetConnected, String errorMessage) {
                    Toast.makeText(activity, "carteActive loading failed",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void dataLoaded(final CarteActives data) {
                    try {
                        final DchCarteActiveDB cadb = new DchCarteActiveDB(activity);
                        cadb.open();
                        cadb.clearCarteActive();
                        if(getCurrentFragment() instanceof LoadingFragment) {
                            ((LoadingFragment) getCurrentFragment()).getProgressBar().setMax(data.getListCarteActive().size());
                        }
                        cadb.close();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                cadb.open();
                                for(int i = 0; i < data.getListCarteActive().size(); i++) {
                                    cadb.insertCarteActive(data.getListCarteActive().get(i));
                                    if(getCurrentFragment() instanceof LoadingFragment) {
                                        final int finalI = i;
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ((LoadingFragment) getCurrentFragment()).getProgressBar().setProgress(finalI);
                                            }
                                        });
                                    }
                                }
                                cadb.close();

                                if(getCurrentFragment() instanceof LoadingFragment) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ((LoadingFragment) getCurrentFragment()).launchComptePrepayeAction();
                                        }
                                    });
                                }
                            }
                        }).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, idAccount);
        }
    }

    public void loadComptePrepaye(int idAccount) {
        if (activity != null && Utils.isInternetConnected(activity)) {
            Datas.loadAllComptePrepaye(activity, new DataAndErrorCallback<ComptePrepayes>() {
                @Override
                public void dataLoadingFailed(boolean isInternetConnected, String errorMessage) {
                    Toast.makeText(activity, "compte prépayé loading failed",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void dataLoaded(final ComptePrepayes data) {
                    try {
                        final DchComptePrepayeDB cpdb = new DchComptePrepayeDB(activity);
                        cpdb.open();
                        cpdb.clearComptePrepaye();
                        if(getCurrentFragment() instanceof LoadingFragment) {
                            ((LoadingFragment) getCurrentFragment()).getProgressBar().setMax(data.getListComptePrepaye().size());
                        }
                        cpdb.close();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                cpdb.open();
                                for(int i = 0; i < data.getListComptePrepaye().size(); i++) {
                                    cpdb.insertComptePrepaye(data.getListComptePrepaye().get(i));
                                    if(getCurrentFragment() instanceof LoadingFragment) {
                                        final int finalI = i;
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ((LoadingFragment) getCurrentFragment()).getProgressBar().setProgress(finalI);
                                            }
                                        });
                                    }
                                }
                                cpdb.close();

                                if(getCurrentFragment() instanceof LoadingFragment) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ((LoadingFragment) getCurrentFragment()).launchDecheterieFluxAction();
                                        }
                                    });
                                }
                            }
                        }).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, idAccount);
        }
    }

    public void loadFlux(int idAccount) {
        if (activity != null && Utils.isInternetConnected(activity)) {
            Datas.loadAllFlux(activity, new DataAndErrorCallback<Fluxs>() {
                @Override
                public void dataLoadingFailed(boolean isInternetConnected, String errorMessage) {
                    Toast.makeText(activity, "flux loading failed",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void dataLoaded(final Fluxs data) {
                    try {
                        final DchFluxDB fdb = new DchFluxDB(activity);
                        fdb.open();
                        fdb.clearFlux();
                        if(getCurrentFragment() instanceof LoadingFragment) {
                            ((LoadingFragment) getCurrentFragment()).getProgressBar().setMax(data.getListFlux().size());
                        }
                        fdb.close();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                fdb.open();
                                for(int i = 0; i < data.getListFlux().size(); i++) {
                                    fdb.insertFlux(data.getListFlux().get(i));
                                    if(getCurrentFragment() instanceof LoadingFragment) {
                                        final int finalI = i;
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ((LoadingFragment) getCurrentFragment()).getProgressBar().setProgress(finalI);
                                            }
                                        });
                                    }
                                }
                                fdb.close();

                                if(getCurrentFragment() instanceof LoadingFragment) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ((LoadingFragment) getCurrentFragment()).launchDecheterieFluxAction();
                                        }
                                    });
                                }
                            }
                        }).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, idAccount);
        }
    }

    public void loadDecheterieFlux(int idAccount) {
        if (activity != null && Utils.isInternetConnected(activity)) {
            Datas.loadAllDecheterieFlux(activity, new DataAndErrorCallback<DecheterieFluxs>() {
                @Override
                public void dataLoadingFailed(boolean isInternetConnected, String errorMessage) {

                }

                @Override
                public void dataLoaded(final DecheterieFluxs data) {
                    try {
                        final DchDecheterieFluxDB dfdb = new DchDecheterieFluxDB(activity);
                        dfdb.open();
                        dfdb.clearDecheterieFlux();
                        if(getCurrentFragment() instanceof LoadingFragment) {
                            ((LoadingFragment) getCurrentFragment()).getProgressBar().setMax(data.getListDecheterieFlux().size());
                        }
                        dfdb.close();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                dfdb.open();
                                for(int i = 0; i < data.getListDecheterieFlux().size(); i++) {
                                    dfdb.insertDecheterieFlux(data.getListDecheterieFlux().get(i));
                                    if(getCurrentFragment() instanceof LoadingFragment) {
                                        final int finalI = i;
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ((LoadingFragment) getCurrentFragment()).getProgressBar().setProgress(finalI);
                                            }
                                        });
                                    }
                                }
                                dfdb.close();

                                if(getCurrentFragment() instanceof LoadingFragment) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ((LoadingFragment) getCurrentFragment()).launchUniteAction();
                                        }
                                    });
                                }
                            }
                        }).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, idAccount);

        }
    }

    public void loadUnite() {
        if (activity != null && Utils.isInternetConnected(activity)) {
            Datas.loadAllUnite(activity, new DataAndErrorCallback<Unites>() {
                @Override
                public void dataLoadingFailed(boolean isInternetConnected, String errorMessage) {
                    Toast.makeText(activity, "unité loading failed",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void dataLoaded(final Unites data) {
                    try {
                        final DchUniteDB udb = new DchUniteDB(activity);
                        udb.open();
                        udb.clearUnite();
                        if(getCurrentFragment() instanceof LoadingFragment) {
                            ((LoadingFragment) getCurrentFragment()).getProgressBar().setMax(data.getListUnite().size());
                        }
                        udb.close();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                udb.open();
                                for(int i = 0; i < data.getListUnite().size(); i++) {
                                    udb.insertUnite(data.getListUnite().get(i));
                                    if(getCurrentFragment() instanceof LoadingFragment) {
                                        final int finalI = i;
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ((LoadingFragment) getCurrentFragment()).getProgressBar().setProgress(finalI);
                                            }
                                        });
                                    }
                                }
                                udb.close();

                                if(getCurrentFragment() instanceof LoadingFragment) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ((LoadingFragment) getCurrentFragment()).launchAccountSettingAction();
                                        }
                                    });
                                }
                            }
                        }).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void loadAccountSetting(int idAccount) {
        if (activity != null && Utils.isInternetConnected(activity)) {
            Datas.loadAllAccountSetting(activity, new DataAndErrorCallback<AccountSettings>() {
                @Override
                public void dataLoadingFailed(boolean isInternetConnected, String errorMessage) {
                    Toast.makeText(activity, "account setting loading failed",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void dataLoaded(final AccountSettings data) {
                    try {
                        final DchAccountSettingDB asdb = new DchAccountSettingDB(activity);
                        asdb.open();
                        asdb.clearAccountSetting();
                        if(getCurrentFragment() instanceof LoadingFragment) {
                            ((LoadingFragment) getCurrentFragment()).getProgressBar().setMax(data.getListAccountSetting().size());
                        }
                        asdb.close();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                asdb.open();
                                for(int i = 0; i < data.getListAccountSetting().size(); i++) {
                                    asdb.insertAccountSetting(data.getListAccountSetting().get(i));
                                    if(getCurrentFragment() instanceof LoadingFragment) {
                                        final int finalI = i;
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ((LoadingFragment) getCurrentFragment()).getProgressBar().setProgress(finalI);
                                            }
                                        });
                                    }
                                }
                                asdb.close();

                            }
                        }).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, idAccount);
        }
    }

    public void loadChoixDecompteTotal() {
        if (activity != null && Utils.isInternetConnected(activity)) {
            Datas.loadChoixDecompteTotal(activity, new DataAndErrorCallback<ChoixDecompteTotals>() {
                @Override
                public void dataLoadingFailed(boolean isInternetConnected, String errorMessage) {
                    Toast.makeText(activity, "ChoixDecompteTotal loading failed",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void dataLoaded(final ChoixDecompteTotals data) {
                    try {
                        final DchChoixDecompteTotalDB cdtdb = new DchChoixDecompteTotalDB(activity);
                        cdtdb.open();
                        cdtdb.clearChoixDecompteTotal();
                        if(getCurrentFragment() instanceof LoadingFragment) {
                            ((LoadingFragment) getCurrentFragment()).getProgressBar().setMax(data.getListChoixDecompteTotal().size());
                        }
                        cdtdb.close();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                cdtdb.open();
                                for(int i = 0; i < data.getListChoixDecompteTotal().size(); i++) {
                                    cdtdb.insertChoixDecompteTotal(data.getListChoixDecompteTotal().get(i));
                                    if(getCurrentFragment() instanceof LoadingFragment) {
                                        final int finalI = i;
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ((LoadingFragment) getCurrentFragment()).getProgressBar().setProgress(finalI);
                                            }
                                        });
                                    }
                                }
                                cdtdb.close();

                                if(getCurrentFragment() instanceof LoadingFragment) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ((LoadingFragment) getCurrentFragment()).endDownload();
                                        }
                                    });
                                }
                            }
                        }).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
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
            transaction.add(R.id.drawerLayout, frag, CURRENT_FRAG_TAG);
        } else {
            transaction.replace(R.id.drawerLayout, frag, CURRENT_FRAG_TAG);
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
            if( getCurrentFragment() instanceof DepotFragment) {
                //detect if the drawer is open
                if(isDrawerOpen()){
                    //close drawer
                    closeDrawer();
                }
                else {
                    //pop-up
                    CustomDialogOnBackPressed.Builder builder = new CustomDialogOnBackPressed.Builder(this);
                    builder.setMessage("Vous allez annuler le dépot en cours, confirmez l'annulation.");
                    builder.setTitle("Information");
                    builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            DchDepotDB dchDepotDB = new DchDepotDB(activity);
                            dchDepotDB.open();
                            dialog.dismiss();

                            Depot depot = dchDepotDB.getDepotByStatut(getResources().getInteger(R.integer.statut_en_cours));
                            dchDepotDB.changeDepotStatutByIdentifiant(depot.getId(),getResources().getInteger(R.integer.statut_annuler));

                            Configuration.setIsOuiClicked(false);

                            if(((DepotFragment) getCurrentFragment()).isComeFromRechercherUsagerFragment() ||((DepotFragment) getCurrentFragment()).isComeFromRUFInApportProFragment() ){
                                changeMainFragment(new RechercherUsagerFragment(), true);
                            }
                            else{
                                changeMainFragment(new IdentificationFragment(), true);
                            }


                            dchDepotDB.close();
                        }
                    });

                    builder.setNegativeButton("Non", new android.content.DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    builder.create().show();
                }

            }
            else if( getCurrentFragment() instanceof IdentificationFragment) {
                ((IdentificationFragment) getCurrentFragment()).closeBarCodeReader();
                changeMainFragment(new AccueilFragment(), true);
            }
            else if( getCurrentFragment() instanceof AccueilFragment) {
                //pop-up
                CustomDialogOnBackPressed.Builder builder = new CustomDialogOnBackPressed.Builder(this);
                builder.setMessage("Voulez-vous vous déconnecter?");
                builder.setMessageGravity(Gravity.CENTER);
                builder.setTitle("Information");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        changeMainFragment(new LoginFragment(), true);

                    }
                });

                builder.setNegativeButton("Non", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.create().show();
            }
            else if( getCurrentFragment() instanceof ApportProFragment) {
                ApportProFragment apportProFragment = (ApportProFragment) getCurrentFragment();

                if(((ApportProFragment) getCurrentFragment()).isComeFromRUFInApportProFragment()){
                    DepotFragment depotFragment = DepotFragment.newInstance(apportProFragment.getDepotId(),apportProFragment.getUsagerIdFromRUFInApportProFragment(),apportProFragment.getTypeCarteIdFromRUFInApportProFragment(),apportProFragment.getAccountIdFromRUFInApportProFragment(),apportProFragment.isComeFromRUFInApportProFragment(), true);
                    changeMainFragment(depotFragment, true);
                }
                else{
                    DepotFragment depotFragment = DepotFragment.newInstance(apportProFragment.getDepotId(),true);
                    changeMainFragment(depotFragment, true);
                }

            }
            else if( getCurrentFragment() instanceof LoginFragment) {
                finish();
            }
            else if( getCurrentFragment() instanceof RechercherUsagerFragment) {
                changeMainFragment(new AccueilFragment(), true);
            }
            else{
                super.onBackPressed();
            }
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

   /* public void showBackArrowinNavBar(boolean visible) {
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
    }*/

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
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            if(data != null) {
                IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                String contents = data.getStringExtra("SCAN_RESULT");
                ((EditText) findViewById(R.id.identification_fragment_barcode_editText)).setText(contents);
            }
        }
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

    public void setDrawerEnabled(boolean enabled) {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED :
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        drawerLayout.setDrawerLockMode(lockMode);
        //toggle.setDrawerIndicatorEnabled(enabled);
    }

    public void initDB(){
        System.out.println("ContainerActivity --> OnCreate() --> initDB()");
        IconDB iconDB = new IconDB(this);
        iconDB.open();
        iconDB.clearIcon();
        DchFluxDB dchFluxDB = new DchFluxDB(this);
        dchFluxDB.open();
        dchFluxDB.clearFlux();
        DchDecheterieFluxDB dchDecheterieFluxDB = new DchDecheterieFluxDB(this);
        dchDecheterieFluxDB.open();
        dchDecheterieFluxDB.clearDecheterieFlux();
        UsagerDB usagerDB = new UsagerDB(this);
        usagerDB.open();
        usagerDB.clearUsager();
        DchComptePrepayeDB dchComptePrepayeDB = new DchComptePrepayeDB(this);
        dchComptePrepayeDB.open();
        dchComptePrepayeDB.clearComptePrepaye();
        DchTypeCarteDB typeCarteDB = new DchTypeCarteDB(this);
        typeCarteDB.open();
        typeCarteDB.clearTypeCarte();
        DchCarteActiveDB dchCarteActiveDB = new DchCarteActiveDB(this);
        dchCarteActiveDB.open();
        dchCarteActiveDB.clearCarteActive();
        DchCarteDB dchCarteDB = new DchCarteDB(this);
        dchCarteDB.open();
        dchCarteDB.clearCarte();
        DchCarteEtatRaisonDB dchCarteEtatRaisonDB = new DchCarteEtatRaisonDB(this);
        dchCarteEtatRaisonDB.open();
        dchCarteEtatRaisonDB.clearCarteEtatRaison();
        DchAccountSettingDB dchAccountSettingDB = new DchAccountSettingDB(this);
        dchAccountSettingDB.open();
        dchAccountSettingDB.clearAccountSetting();
        DchUniteDB dchUniteDB = new DchUniteDB(this);
        dchUniteDB.open();
        dchUniteDB.clearUnite();
        DchAccountFluxSettingDB dchAccountFluxSettingDB = new DchAccountFluxSettingDB(this);
        dchAccountFluxSettingDB.open();
        dchAccountFluxSettingDB.clearAccountFluxSetting();
        TypeHabitatDB typeHabitatDB = new TypeHabitatDB(this);
        typeHabitatDB.open();
        typeHabitatDB.clearTypeHabitat();
        HabitatDB habitatDB = new HabitatDB(this);
        habitatDB.open();
        habitatDB.clearHabitat();
        MenageDB menageDB = new MenageDB(this);
        menageDB.open();
        menageDB.clearMenage();
        LocalDB localDB = new LocalDB(this);
        localDB.open();
        localDB.clearLocal();
        UsagerHabitatDB usagerHabitatDB = new UsagerHabitatDB(this);
        usagerHabitatDB.open();
        usagerHabitatDB.clearUsagerHabitat();
        UsagerMenageDB usagerMenageDB = new UsagerMenageDB(this);
        usagerMenageDB.open();
        usagerMenageDB.clearUsagerMenage();


        //add All icons into DBB
        String icons[] = {"amiante","biodechets","bouteille_plus_conserve","carton_plus_papier","carton","deee","depots_sauvage","encombrants","feuilles","gaz","journaux","metal","meuble","piles_plus_electromenager","plastique","pneu","produits_chimiques_2","produits_chimiques","sac_plastique","sac","verre","vetements"};
        for(int i = 0; i < icons.length; i ++){
            Icon icon = new Icon();
            icon.setNom(icons[i]);
            icon.setDomaine("");
            icon.setPath("");
            iconDB.insertIcon(icon);
        }
        ArrayList<Icon> iconList = iconDB.getAllIcons();
        for(int i = 0; i < iconList.size(); i ++){
            System.out.println(iconList.get(i).getNom());
        }
        iconDB.close();

        //add flux into DBB
        dchFluxDB.insertFlux(new Flux("Amiante", 1, 3, 0));
        dchFluxDB.insertFlux(new Flux("Biodéchèts", 2, 3, 0));
        dchFluxDB.insertFlux(new Flux("Bouteille + conserve", 3, 3, 0));
        dchFluxDB.insertFlux(new Flux("Carton + papier", 4, 3, 0));
        dchFluxDB.insertFlux(new Flux("Carton", 5, 3, 0));
        dchFluxDB.insertFlux(new Flux("DEEE", 6, 3, 0));
        dchFluxDB.insertFlux(new Flux("Dépots sauvage", 7, 3, 0));
        dchFluxDB.insertFlux(new Flux("Encombrants", 8, 3, 0));
        dchFluxDB.insertFlux(new Flux("Feuilles", 9, 3, 0));
        dchFluxDB.insertFlux(new Flux("Gaz", 10, 3, 0));
        dchFluxDB.insertFlux(new Flux("Journaux", 11, 3, 0));
        dchFluxDB.insertFlux(new Flux("Metal", 12, 3, 0));
        dchFluxDB.insertFlux(new Flux("Meuble", 13, 3, 0));
        dchFluxDB.insertFlux(new Flux("Piles + electroménager", 14, 3, 0));
        dchFluxDB.insertFlux(new Flux("Plastique", 15, 3, 0));
        dchFluxDB.insertFlux(new Flux("Pneu", 16, 3, 0));
        dchFluxDB.insertFlux(new Flux("Produits chimiques 2", 17, 3, 0));
        dchFluxDB.insertFlux(new Flux("Produits chimiques", 18, 3, 0));
        dchFluxDB.insertFlux(new Flux("Sac plastique", 19, 3, 0));
        dchFluxDB.insertFlux(new Flux("Sac", 20, 3, 0));
        dchFluxDB.insertFlux(new Flux("Verre", 21, 3, 0));
        dchFluxDB.insertFlux(new Flux("Vêtements", 22, 3, 0));
        dchFluxDB.close();

        //add dechetrie_flux into DBB
        dchDecheterieFluxDB.insertDecheterieFlux(new DecheterieFlux(1, 1));
        dchDecheterieFluxDB.insertDecheterieFlux(new DecheterieFlux(1, 2));
        dchDecheterieFluxDB.insertDecheterieFlux(new DecheterieFlux(1, 3));
        dchDecheterieFluxDB.insertDecheterieFlux(new DecheterieFlux(2, 1));
        dchDecheterieFluxDB.insertDecheterieFlux(new DecheterieFlux(3, 2));
        dchDecheterieFluxDB.insertDecheterieFlux(new DecheterieFlux(4, 3));
        dchDecheterieFluxDB.close();

        //add usager into DBB
        usagerDB.insertUsager(new Usager(1,0,"Michael Jordan",null));
        usagerDB.insertUsager(new Usager(2,0,"Sir Isaac Newton",null));
        usagerDB.insertUsager(new Usager(3,0,"Jeanne Moreau",null));
        usagerDB.insertUsager(new Usager(4,0,"Stephen King",null));
        usagerDB.insertUsager(new Usager(5,0,"J.K. Rowling",null));
        usagerDB.insertUsager(new Usager(6,0,"Winston Churchill",null));
        usagerDB.insertUsager(new Usager(7,0,"Thomas Edison",null));
        usagerDB.insertUsager(new Usager(8,0,"Oprah Winfrey",null));
        usagerDB.insertUsager(new Usager(9,0,"Walt Disney",null));
        usagerDB.insertUsager(new Usager(10,0,"Steven Spielberg",null));
        usagerDB.insertUsager(new Usager(11,0,"Harrison Ford",null));
        usagerDB.insertUsager(new Usager(12,0,"Theodor Seuss Geisel",null));
        usagerDB.insertUsager(new Usager(13,0,"Charles Darwin",null));
        usagerDB.insertUsager(new Usager(14,0,"Richard Feloni ",null));
        usagerDB.insertUsager(new Usager(15,0,"R.H. Macy",null));
        usagerDB.close();

        //add comptePrepaye into DBB
        dchComptePrepayeDB.insertComptePrepaye(new ComptePrepaye(1,1,100,10));
        dchComptePrepayeDB.insertComptePrepaye(new ComptePrepaye(2,2,50,3));
        dchComptePrepayeDB.insertComptePrepaye(new ComptePrepaye(3,3,35,3));
        dchComptePrepayeDB.insertComptePrepaye(new ComptePrepaye(4,4,15,3));
        dchComptePrepayeDB.insertComptePrepaye(new ComptePrepaye(5,5,13,3));
        dchComptePrepayeDB.insertComptePrepaye(new ComptePrepaye(6,6,100,10));
        dchComptePrepayeDB.insertComptePrepaye(new ComptePrepaye(7,6,50,3));
        dchComptePrepayeDB.insertComptePrepaye(new ComptePrepaye(8,6,35,3));
        dchComptePrepayeDB.insertComptePrepaye(new ComptePrepaye(9,6,15,3));
        dchComptePrepayeDB.insertComptePrepaye(new ComptePrepaye(10,6,13,3));
        dchComptePrepayeDB.insertComptePrepaye(new ComptePrepaye(11,11,100,10));
        dchComptePrepayeDB.insertComptePrepaye(new ComptePrepaye(12,12,50,3));
        dchComptePrepayeDB.insertComptePrepaye(new ComptePrepaye(13,13,35,3));
        dchComptePrepayeDB.insertComptePrepaye(new ComptePrepaye(14,14,15,3));
        dchComptePrepayeDB.insertComptePrepaye(new ComptePrepaye(15,15,13,3));
        dchComptePrepayeDB.close();

        //add typeCarte into DBB
        typeCarteDB.insertTypeCarte(new TypeCarte(1,"typeCarte1"));
        typeCarteDB.insertTypeCarte(new TypeCarte(2,"typeCarte2"));
        typeCarteDB.insertTypeCarte(new TypeCarte(3,"typeCarte3"));
        typeCarteDB.close();

        //add carte into BDD
        dchCarteDB.insertCarte(new Carte(1,"2f4913f3",null,1,0));
        dchCarteDB.insertCarte(new Carte(2,"ndjkndqjf",null,1,0));
        dchCarteDB.insertCarte(new Carte(3,"2f4913f2",null,3,0));
        dchCarteDB.insertCarte(new Carte(4,"565xwvv",null,3,0));
        dchCarteDB.insertCarte(new Carte(5,"vfnsdj",null,2,0));
        dchCarteDB.insertCarte(new Carte(6,"2f4913f3",null,3,1));
        dchCarteDB.insertCarte(new Carte(7,"cdwfjnqe",null,3,0));
        dchCarteDB.insertCarte(new Carte(8,"3jndqlik",null,1,0));
        dchCarteDB.insertCarte(new Carte(9,"dnvjidesqf",null,3,0));
        dchCarteDB.insertCarte(new Carte(10,"skdnqnkji",null,1,0));
        dchCarteDB.insertCarte(new Carte(11,"nqsjncisjq",null,1,0));
        dchCarteDB.insertCarte(new Carte(12,"cjdsnfiqeq",null,1,0));
        dchCarteDB.insertCarte(new Carte(13,"xcvfjdns",null,1,0));
        dchCarteDB.insertCarte(new Carte(14,"fjdsnfj",null,3,0));
        dchCarteDB.insertCarte(new Carte(15,"vnjsdjfhdsb",null,3,0));
        dchCarteDB.close();

        //add carteActive into DBB
        dchCarteActiveDB.insertCarteActive(new CarteActive(1,null,null,1,true,1));
        dchCarteActiveDB.insertCarteActive(new CarteActive(2,null,null,1,true,2));
        dchCarteActiveDB.insertCarteActive(new CarteActive(3,null,null,1,true,3));
        dchCarteActiveDB.insertCarteActive(new CarteActive(4,null,null,2,false,3));
        dchCarteActiveDB.insertCarteActive(new CarteActive(5,null,null,2,true,3));
        dchCarteActiveDB.insertCarteActive(new CarteActive(6,null,null,2,true,4));
        dchCarteActiveDB.insertCarteActive(new CarteActive(8,null,null,3,true,6));
        dchCarteActiveDB.insertCarteActive(new CarteActive(9,null,null,3,true,7));
        dchCarteActiveDB.insertCarteActive(new CarteActive(10,null,null,3,true,9));
        dchCarteActiveDB.insertCarteActive(new CarteActive(11,null,null,2,true,11));
        dchCarteActiveDB.insertCarteActive(new CarteActive(12,null,null,2,true,12));
        dchCarteActiveDB.insertCarteActive(new CarteActive(13,null,null,3,true,13));
        dchCarteActiveDB.insertCarteActive(new CarteActive(14,null,null,3,true,14));
        dchCarteActiveDB.insertCarteActive(new CarteActive(15,null,null,3,false,15));
        dchCarteActiveDB.close();

        //add carteEtatRaison into BDD
        dchCarteEtatRaisonDB.insertCarteEtatRaison(new CarteEtatRaison(1,"Raison 1."));
        dchCarteEtatRaisonDB.insertCarteEtatRaison(new CarteEtatRaison(2,"Raison 2."));
        dchCarteEtatRaisonDB.insertCarteEtatRaison(new CarteEtatRaison(2,"Raison 3."));
        dchCarteEtatRaisonDB.close();

        //add accountSetting into BDD
        dchAccountSettingDB.insertAccountSetting(new AccountSetting(1,0,1,1,true,true,true,0,0,"m3","170101","171230",0,0));
        dchAccountSettingDB.insertAccountSetting(new AccountSetting(2,0,1,1,false,false,true,0,0,"m3","170101","170410",0,0));
        dchAccountSettingDB.insertAccountSetting(new AccountSetting(3,0,3,2,false,false,true,0,0,"m3","170101","170410",0,0));
        dchAccountSettingDB.insertAccountSetting(new AccountSetting(4,0,3,2,false,false,false,0,0,"m3","170101","171230",0,0));
        dchAccountSettingDB.close();

        //add accountFluxSetting into BDD
        dchAccountFluxSettingDB.insertAccountFluxSetting(new AccountFluxSetting(1,1,0,false,true,0));//line 1
        dchAccountFluxSettingDB.insertAccountFluxSetting(new AccountFluxSetting(1,2,0,true,false,0));//line 2
        dchAccountFluxSettingDB.insertAccountFluxSetting(new AccountFluxSetting(1,3,1,true,true,0));//line 1 line 3
        dchAccountFluxSettingDB.insertAccountFluxSetting(new AccountFluxSetting(4,1,0,false,false,0));
        dchAccountFluxSettingDB.insertAccountFluxSetting(new AccountFluxSetting(4,2,0,false,false,0));
        dchAccountFluxSettingDB.insertAccountFluxSetting(new AccountFluxSetting(4,3,0,false,false,0));
        dchAccountFluxSettingDB.close();

        //add unite into BDD
        dchUniteDB.insertUnite(new Unite(1,"m3"));
        dchUniteDB.insertUnite(new Unite(2,"kg"));
        dchUniteDB.insertUnite(new Unite(3,"kg")); //unité Apporté
        dchUniteDB.close();

        //add typeHabitat into BDD
        typeHabitatDB.insertTypeHabitat(new TypeHabitat(1,"Individuel"));
        typeHabitatDB.insertTypeHabitat(new TypeHabitat(2,"Collectif"));
        typeHabitatDB.insertTypeHabitat(new TypeHabitat(3,"Administration"));
        typeHabitatDB.insertTypeHabitat(new TypeHabitat(4,"Professionnel"));
        typeHabitatDB.insertTypeHabitat(new TypeHabitat(5,"Divers"));
        typeHabitatDB.close();

        //add habitat into BDD
        habitatDB.insertHabitat(new Habitat(1,"Avenue Anatole France","75007","Paris",1,1,"SARL Gentil","3882AB23","0","0",null,null,"2",true,null,null,null,4,1));
        habitatDB.insertHabitat(new Habitat(2,"Rue des abricots","75000","Paris",2,7,"Saint-maur",null,"0","0",null,null,"10",true,null,null,null,2,1));
        habitatDB.insertHabitat(new Habitat(3,"Rue de Paris","13000","Marseille",3,1,"Renard","A12ZE","0","0",null,null,"13",false,null,null,null,1,1));
        habitatDB.insertHabitat(new Habitat(4,"Rue samuel de champlain","61000","alencon",1,4,"Aluna","REF875412","0","0",null,null,"15",false,null,null,null,1,1));
        habitatDB.insertHabitat(new Habitat(5,"Chemin de l'ile demoiselle","77410","ANNET-SUR-MARNE",1,3,"WEISS",null,"0","0",null,null,"13",false,null,null,null,4,1));
        habitatDB.insertHabitat(new Habitat(6,"RUE DU GENERAL DE GAULLE","77230","DAMMARTIN-EN-GOELE",1,1,"IMMEUBLE",null,"0","0",null,null,"33",false,null,null,null,2,1));
        habitatDB.insertHabitat(new Habitat(7,"RUE DU MONCEL","77410","ANNET-SUR-MARNE",11,0,"RESIDENCE FLAUBERT",null,"0","0",null,null,"7",true,null,null,null,4,1));
        habitatDB.insertHabitat(new Habitat(8,"Rue de Leupe","90400","Sevenans",3,1,"Jenny","A12ZE","0","0",null,null,null,true,null,null,null,1,1));
        habitatDB.insertHabitat(new Habitat(9,"Avenue Albert Einstein","69100","Villeurbanne",1,4,"Durant","REF875412","0","0",null,null,"15",true,null,null,null,1,1));
        habitatDB.insertHabitat(new Habitat(10,"Rue de Ménilmontant","75020","Paris",1,3,"Lebrun",null,"0","0",null,null,"161",true,null,null,null,4,1));
        habitatDB.insertHabitat(new Habitat(11,"Rue de la République","93100","Montreuil",1,1,"IMMEUBLE",null,"0","0","bis",null,"79",true,null,null,null,2,1));
        habitatDB.insertHabitat(new Habitat(12,"Rue de Rivoli","75001","Paris",11,0,"Bill",null,"0","0",null,null,"99",true,null,null,null,4,1));
        habitatDB.close();

        //add menage into BDD
        menageDB.insertMenage(new Menage(1,"RISPE","Arnaud","arispe@optae.fr",3,"Ref 12",true,"0123456789","M",1));
        menageDB.insertMenage(new Menage(2,"COQUET","Remi","rcoquet@trackoe.fr",3,"124AD",true,"0776583366","M",2));
        menageDB.insertMenage(new Menage(3,"Haddock","Jean","dhnsj@dsk.dslqm",5,"3882AB23",true,"0605040302","M",3));
        menageDB.insertMenage(new Menage(4,"Jean","Louis","arispe@optae.fr",0,null,true,"0345678991","M",3));
        menageDB.insertMenage(new Menage(5,"Guillaume","David","alain@terieur.fr",3,null,true,"0634217690","M",2));
        menageDB.insertMenage(new Menage(6,"Terieur","Alain","arispe@optae.fr",3,null,false,"0123456789","M",1));
        menageDB.insertMenage(new Menage(7,"Norris","Chuck","norris.chuck@gmail.com",3,null,false,"0123456789","M",1));
        menageDB.close();

        //add local into DBB
        localDB.insertLocal(new Local(1,6,null,null,null,null,null));
        localDB.insertLocal(new Local(2,2,null,null,null,null,null));
        localDB.insertLocal(new Local(3,2,null,null,null,null,null));
        localDB.close();

        //add usagerHabitat into BDD
        usagerHabitatDB.insertUsagerHabitat(new UsagerHabitat(1,1));//habitat1(actif)
        usagerHabitatDB.insertUsagerHabitat(new UsagerHabitat(1,4));//habitat4(not actif)
        usagerHabitatDB.insertUsagerHabitat(new UsagerHabitat(1,5));//habitat5(not actif)
        usagerHabitatDB.insertUsagerHabitat(new UsagerHabitat(2,2));
        //usagerHabitatDB.insertUsagerHabitat(new UsagerHabitat(3,3));
        usagerHabitatDB.insertUsagerHabitat(new UsagerHabitat(11,8));
        usagerHabitatDB.insertUsagerHabitat(new UsagerHabitat(12,9));
        usagerHabitatDB.insertUsagerHabitat(new UsagerHabitat(13,10));
        usagerHabitatDB.insertUsagerHabitat(new UsagerHabitat(14,11));
        usagerHabitatDB.insertUsagerHabitat(new UsagerHabitat(15,12));

        usagerHabitatDB.insertUsagerHabitat(new UsagerHabitat(2,2));
        usagerHabitatDB.close();

        //add usagerMenage into BDD
        usagerMenageDB.insertUsagerMenage(new UsagerMenage(3,3));//menage3 habitat2(actif)
        usagerMenageDB.insertUsagerMenage(new UsagerMenage(3,6));//menage6 habitat3(not actif)
        usagerMenageDB.insertUsagerMenage(new UsagerMenage(3,7));//menage7 habitat6(not actif)
        usagerMenageDB.close();
    }

    public void initDBTest(){
        DchAccountSettingDB dchAccountSettingDB = new DchAccountSettingDB(this);
        dchAccountSettingDB.open();
        dchAccountSettingDB.clearAccountSetting();
        DchAccountFluxSettingDB dchAccountFluxSettingDB = new DchAccountFluxSettingDB(this);
        dchAccountFluxSettingDB.open();
        dchAccountFluxSettingDB.clearAccountFluxSetting();
        IconDB iconDB = new IconDB(this);
        iconDB.open();
        iconDB.clearIcon();
        DchFluxDB dchFluxDB = new DchFluxDB(this);
        dchFluxDB.open();
        dchFluxDB.clearFlux();

        //add accountSetting into BDD
        dchAccountSettingDB.insertAccountSetting(new AccountSetting(1,118,1,1,false,true,true,1,1,"m3","170101","171230",0,0));
        dchAccountSettingDB.insertAccountSetting(new AccountSetting(2,118,2,1,false,true,false,1,1,"m3","170101","171230",0,0));
        dchAccountSettingDB.insertAccountSetting(new AccountSetting(3,118,3,1,true,false,false,1,1,"m3","170101","171230",0,0));
        dchAccountSettingDB.close();

        //add accountFluxSetting into BDD
        dchAccountFluxSettingDB.insertAccountFluxSetting(new AccountFluxSetting(1,1,0,true,false,0));
        dchAccountFluxSettingDB.insertAccountFluxSetting(new AccountFluxSetting(2,2,0,true,false,0));
        dchAccountFluxSettingDB.insertAccountFluxSetting(new AccountFluxSetting(3,3,0,false,false,0));
        dchAccountFluxSettingDB.close();

        //add All icons into DBB
        String icons[] = {"amiante","biodechets","bouteille_plus_conserve","carton_plus_papier","carton","deee","depots_sauvage","encombrants","feuilles","gaz","journaux","metal","meuble","piles_plus_electromenager","plastique","pneu","produits_chimiques_2","produits_chimiques","sac_plastique","sac","verre","vetements"};
        for(int i = 0; i < icons.length; i ++){
            Icon icon = new Icon();
            icon.setNom(icons[i]);
            icon.setDomaine("");
            icon.setPath("");
            iconDB.insertIcon(icon);
        }
        ArrayList<Icon> iconList = iconDB.getAllIcons();
        for(int i = 0; i < iconList.size(); i ++){
            System.out.println(iconList.get(i).getNom());
        }
        iconDB.close();

        //add flux into DBB
        dchFluxDB.insertFlux(new Flux("Amiante", 1, 3, 118));
        dchFluxDB.insertFlux(new Flux("Biodéchèts", 2, 3, 118));
        dchFluxDB.insertFlux(new Flux("Bouteille + conserve", 3, 3, 118));
        dchFluxDB.insertFlux(new Flux("Carton + papier", 4, 3, 118));
        dchFluxDB.insertFlux(new Flux("Carton", 5, 3, 118));
        dchFluxDB.insertFlux(new Flux("DEEE", 6, 3, 118));
        dchFluxDB.insertFlux(new Flux("Dépots sauvage", 7, 3, 118));
        dchFluxDB.insertFlux(new Flux("Encombrants", 8, 3, 118));
        dchFluxDB.insertFlux(new Flux("Feuilles", 9, 3, 118));
        dchFluxDB.insertFlux(new Flux("Gaz", 10, 3, 118));
        dchFluxDB.insertFlux(new Flux("Journaux", 11, 3, 118));
        dchFluxDB.insertFlux(new Flux("Metal", 12, 3, 118));
        dchFluxDB.insertFlux(new Flux("Meuble", 13, 3, 118));
        dchFluxDB.insertFlux(new Flux("Piles + electroménager", 14, 3, 118));
        dchFluxDB.insertFlux(new Flux("Plastique", 15, 3, 118));
        dchFluxDB.insertFlux(new Flux("Pneu", 16, 3, 118));
        dchFluxDB.insertFlux(new Flux("Produits chimiques 2", 17, 3, 118));
        dchFluxDB.insertFlux(new Flux("Produits chimiques", 18, 3, 118));
        dchFluxDB.insertFlux(new Flux("Sac plastique", 19, 3, 118));
        dchFluxDB.insertFlux(new Flux("Sac", 20, 3, 118));
        dchFluxDB.insertFlux(new Flux("Verre", 21, 3, 118));
        dchFluxDB.insertFlux(new Flux("Vêtements", 22, 3, 118));
        dchFluxDB.close();


    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    public void hideHamburgerButton(){
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
    }

    public void showHamburgerButton(){
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
    }

    //open the drawer in a few time
    public void openDrawerWithDelay(){
        new Handler().postDelayed(new Runnable(){

            public void run() {
                if( getCurrentFragment() instanceof DepotFragment) {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }

            }

        }, 1000);
    }

    public void openDrawer(){
                drawerLayout.openDrawer(Gravity.LEFT);
    }

    public void closeDrawer(){
        drawerLayout.closeDrawer(Gravity.LEFT);
    }

    public void changeToolbarIcon(){
        toolbar.setNavigationIcon(R.drawable.user);
    }

    public boolean isDrawerOpen(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            return true;
        }
        else{
            return false;
        }
    }

    //save the BDD datas into a document
    public static void copyDatabaseToSDCard(Context ctx) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = data + "/data/" + ctx.getPackageName() + "/databases/decheterie.db";
                String backupDBPath = "decheterie.db";
                File currentDB = new File(currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
                Toast.makeText(ctx, "BDD updated.",
                        Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {

        }
    }

    public String getBarcode(){
        return barcode;
    }

    //NFC

    private void resolveIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            } else {
                // Unknown tag type
                byte[] empty = new byte[0];
                byte[] id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
                Tag tag = (Tag) intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                byte[] payload = dumpTagData(tag).getBytes();
                NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, id, payload);
                NdefMessage msg = new NdefMessage(new NdefRecord[] { record });
                msgs = new NdefMessage[] { msg };
                mTags.add(tag);

                String idCardInformation[] = dumpTagData(tag).split(":");
                idCard = idCardInformation[1];

                ((EditText) findViewById(R.id.identification_fragment_barcode_editText)).setText(idCard.replace(" ", ""));

            }

        }


    }



    private String dumpTagData(Tag tag) {
        StringBuilder sb = new StringBuilder();
        byte[] id = tag.getId();
        //sb.append("ID (hex): ").append(toHex(id)).append('\n');
        sb.append("ID (reversed hex): ").append(toReversedHex(id));
        //sb.append("ID (dec): ").append(toDec(id)).append('\n');
        //sb.append("ID (reversed dec): ").append(toReversedDec(id)).append('\n');

        /*String prefix = "android.nfc.tech.";
        sb.append("Technologies: ");
        for (String tech : tag.getTechList()) {
            sb.append(tech.substring(prefix.length()));
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        for (String tech : tag.getTechList()) {
            if (tech.equals(MifareClassic.class.getName())) {
                sb.append('\n');
                String type = "Unknown";
                try {
                    MifareClassic mifareTag;
                    try {
                        mifareTag = MifareClassic.get(tag);
                    } catch (Exception e) {
                        // Fix for Sony Xperia Z3/Z5 phones
                        tag = cleanupTag(tag);
                        mifareTag = MifareClassic.get(tag);
                    }
                    switch (mifareTag.getType()) {
                        case MifareClassic.TYPE_CLASSIC:
                            type = "Classic";
                            break;
                        case MifareClassic.TYPE_PLUS:
                            type = "Plus";
                            break;
                        case MifareClassic.TYPE_PRO:
                            type = "Pro";
                            break;
                    }
                    sb.append("Mifare Classic type: ");
                    sb.append(type);
                    sb.append('\n');

                    sb.append("Mifare size: ");
                    sb.append(mifareTag.getSize() + " bytes");
                    sb.append('\n');

                    sb.append("Mifare sectors: ");
                    sb.append(mifareTag.getSectorCount());
                    sb.append('\n');

                    sb.append("Mifare blocks: ");
                    sb.append(mifareTag.getBlockCount());
                } catch (Exception e) {
                    sb.append("Mifare classic error: " + e.getMessage());
                }
            }

            if (tech.equals(MifareUltralight.class.getName())) {
                sb.append('\n');
                MifareUltralight mifareUlTag = MifareUltralight.get(tag);
                String type = "Unknown";
                switch (mifareUlTag.getType()) {
                    case MifareUltralight.TYPE_ULTRALIGHT:
                        type = "Ultralight";
                        break;
                    case MifareUltralight.TYPE_ULTRALIGHT_C:
                        type = "Ultralight C";
                        break;
                }
                sb.append("Mifare Ultralight type: ");
                sb.append(type);
            }
        }*/

        return sb.toString();
    }

    private Tag cleanupTag(Tag oTag) {
        if (oTag == null)
            return null;

        String[] sTechList = oTag.getTechList();

        Parcel oParcel = Parcel.obtain();
        oTag.writeToParcel(oParcel, 0);
        oParcel.setDataPosition(0);

        int len = oParcel.readInt();
        byte[] id = null;
        if (len >= 0) {
            id = new byte[len];
            oParcel.readByteArray(id);
        }
        int[] oTechList = new int[oParcel.readInt()];
        oParcel.readIntArray(oTechList);
        Bundle[] oTechExtras = oParcel.createTypedArray(Bundle.CREATOR);
        int serviceHandle = oParcel.readInt();
        int isMock = oParcel.readInt();
        IBinder tagService;
        if (isMock == 0) {
            tagService = oParcel.readStrongBinder();
        } else {
            tagService = null;
        }
        oParcel.recycle();

        int nfca_idx = -1;
        int mc_idx = -1;
        short oSak = 0;
        short nSak = 0;

        for (int idx = 0; idx < sTechList.length; idx++) {
            if (sTechList[idx].equals(NfcA.class.getName())) {
                if (nfca_idx == -1) {
                    nfca_idx = idx;
                    if (oTechExtras[idx] != null && oTechExtras[idx].containsKey("sak")) {
                        oSak = oTechExtras[idx].getShort("sak");
                        nSak = oSak;
                    }
                } else {
                    if (oTechExtras[idx] != null && oTechExtras[idx].containsKey("sak")) {
                        nSak = (short) (nSak | oTechExtras[idx].getShort("sak"));
                    }
                }
            } else if (sTechList[idx].equals(MifareClassic.class.getName())) {
                mc_idx = idx;
            }
        }

        boolean modified = false;

        if (oSak != nSak) {
            oTechExtras[nfca_idx].putShort("sak", nSak);
            modified = true;
        }

        if (nfca_idx != -1 && mc_idx != -1 && oTechExtras[mc_idx] == null) {
            oTechExtras[mc_idx] = oTechExtras[nfca_idx];
            modified = true;
        }

        if (!modified) {
            return oTag;
        }

        Parcel nParcel = Parcel.obtain();
        nParcel.writeInt(id.length);
        nParcel.writeByteArray(id);
        nParcel.writeInt(oTechList.length);
        nParcel.writeIntArray(oTechList);
        nParcel.writeTypedArray(oTechExtras, 0);
        nParcel.writeInt(serviceHandle);
        nParcel.writeInt(isMock);
        if (isMock == 0) {
            nParcel.writeStrongBinder(tagService);
        }
        nParcel.setDataPosition(0);

        Tag nTag = Tag.CREATOR.createFromParcel(nParcel);

        nParcel.recycle();

        return nTag;
    }

    private String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = bytes.length - 1; i >= 0; --i) {
            int b = bytes[i] & 0xff;
            if (b < 0x10)
                sb.append('0');
            sb.append(Integer.toHexString(b));
            if (i > 0) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    private String toReversedHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; ++i) {
            if (i > 0) {
                sb.append(" ");
            }
            int b = bytes[i] & 0xff;
            if (b < 0x10)
                sb.append('0');
            sb.append(Integer.toHexString(b));
        }
        return sb.toString();
    }

    private long toDec(byte[] bytes) {
        long result = 0;
        long factor = 1;
        for (int i = 0; i < bytes.length; ++i) {
            long value = bytes[i] & 0xffl;
            result += value * factor;
            factor *= 256l;
        }
        return result;
    }

    private long toReversedDec(byte[] bytes) {
        long result = 0;
        long factor = 1;
        for (int i = bytes.length - 1; i >= 0; --i) {
            long value = bytes[i] & 0xffl;
            result += value * factor;
            factor *= 256l;
        }
        return result;
    }

    //lanced when card attaches to the back of tablette
    @Override
    public void onNewIntent(Intent intent) {
        if(getCurrentFragment() instanceof IdentificationFragment) {
            setIntent(intent);
            resolveIntent(intent);
        }
    }

    /* private int getPictureId(String pictureName){
        int pictureId = 0;
        switch(pictureName){
            case "amiante": pictureId = R.drawable.amiante; break;
            case "biodechets": pictureId = R.drawable.biodechets; break;
            case "bouteille_plus_conserve": pictureId = R.drawable.bouteille_plus_conserve; break;
            case "carton_plus_papier": pictureId = R.drawable.carton_plus_papier; break;
            case "carton": pictureId = R.drawable.carton; break;
            case "deee": pictureId = R.drawable.deee; break;
            case "depots_sauvage": pictureId = R.drawable.depots_sauvage; break;
            case "encombrants": pictureId = R.drawable.encombrants; break;
            case "feuilles": pictureId = R.drawable.feuilles; break;
            case "gaz": pictureId = R.drawable.gaz; break;
            case "journaux": pictureId = R.drawable.journaux; break;
            case "metal": pictureId = R.drawable.metal; break;
            case "meuble": pictureId = R.drawable.meuble; break;
            case "piles_plus_electromenager": pictureId = R.drawable.piles_plus_electromenager; break;
            case "plastique": pictureId = R.drawable.plastique; break;
            case "pneu": pictureId = R.drawable.pneu; break;
            case "produits_chimiques_2": pictureId = R.drawable.produits_chimiques_2; break;
            case "produits_chimiques": pictureId = R.drawable.produits_chimiques; break;
            case "sac_plastique": pictureId = R.drawable.sac_plastique; break;
            case "sac": pictureId = R.drawable.sac; break;
            case "verre": pictureId = R.drawable.verre; break;
            case "vetements": pictureId = R.drawable.vetements; break;
        }
        return pictureId;
    }*/

}
