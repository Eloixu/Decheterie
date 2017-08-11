package fr.trackoe.decheterie.ui.fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import fr.trackoe.decheterie.R;
import fr.trackoe.decheterie.Utils;
import fr.trackoe.decheterie.configuration.Configuration;
import fr.trackoe.decheterie.model.Const;
import fr.trackoe.decheterie.ui.activity.ContainerActivity;

/**
 * Created by Remi on 01/12/2015.
 */
public class SettingsFragment extends Fragment {
    ContainerActivity parentActivity;
    private ViewGroup settings_vg;
    private EditText majInterval;
    private MAJTimer majTimer;
    private MAJTimer defaultMAJTimer;
    private boolean isFirstLaunch = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parentActivity = (ContainerActivity) getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        settings_vg = (ViewGroup) inflater.inflate(R.layout.settings_fragment, container, false);
        majInterval = (EditText) settings_vg.findViewById(R.id.settings_db_refresh_editText);

        if (getArguments() != null && getArguments().containsKey(Const.IS_FIRST_LAUNCH)) {
            isFirstLaunch = getArguments().getBoolean(Const.IS_FIRST_LAUNCH);
        }

        // Init Actionbar
        //initActionBar();

        // Init Views
        initViews();

        // Init des listeners
        initListeners();

        // Récupération des formulaires
//        FormulaireBackup.retrieveDatas(getContext());
//        FormulaireBackup.copyDatabaseToSDCard(getContext());

        return settings_vg;
    }

    /*
	 * Init Actionbar
	 */
    /*public void initActionBar() {
        if(getActivity() != null && getActivity() instanceof ContainerActivity) {
            ((ContainerActivity) getActivity()).showActionBarSettings(isFirstLaunch);
        }
    }
*/
    public void setMajDate() {
        ((TextView) settings_vg.findViewById(R.id.settings_synchro)).setText(Configuration.getDateMAJ());
    }

    /*
    Init Views
     */
    public void initViews() {
        parentActivity.setTitleToolbar(getResources().getString(R.string.title_setting_fragment));
        ((DrawerLocker) getActivity()).setDrawerEnabled(false);
        parentActivity.hideHamburgerButton();

        // Infos tablette
        String infosTablette = String.format(getString(R.string.settings_tablette_format),
                Configuration.getNomTablette(),
                Configuration.getNumeroTablette(),
                Configuration.getNomOpCl());

        setMajDate();

        ((TextView) settings_vg.findViewById(R.id.settings_tablette_infos)).setText(infosTablette);

        ((TextView) settings_vg.findViewById(R.id.settings_hash_infos)).setText(getUniqueCode());

        // Version de l'appli
        try {
            if(getActivity() != null) {
                PackageInfo pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
                String version = pInfo.versionName;
                ((TextView) settings_vg.findViewById(R.id.settings_version)).setText(version);
                settings_vg.findViewById(R.id.settings_version).setVisibility(View.VISIBLE);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        // Connexion réseau
        String res;
        if(Utils.isInternetConnected(getContext())) {
            res = getString(R.string.settings_reseau_ok);
        } else {
            res = getString(R.string.settings_reseau_nok);
        }
        ((TextView) settings_vg.findViewById(R.id.settings_reseau)).setText(res);

        // Date de dernière synchro
        setMajDate();

        // Affichage de l'état de récupération des ws
        // Infos Tablette
        displayTabletteLoader();

        // Users
        displayUsersLoader();

        // Maj Apk
        if(Configuration.getIsApkReadyToInstall()) {
            settings_vg.findViewById(R.id.settings_version_btn).setVisibility(View.VISIBLE);
        }

        //set editText MAJ interval
        if(Configuration.getMAJInterval().isEmpty()){
            majInterval.setText(Configuration.getDefaultMAJInterval());
        }
        else{
            majInterval.setText(Configuration.getMAJInterval());
        }
    }

    /*
    Init Listeners
     */
    public void initListeners() {
        // clic sur le boutton "Forcer la synchronisation serveur"
        settings_vg.findViewById(R.id.settings_maj_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utils.isInternetConnected(getActivity())) {
                    String numTablette = Configuration.getNumeroTablette();
                    // Appel des WS
                    // Récupération des infos liées à la tablette
                    getInfos(numTablette);

                    // Maj Apk
                    loadApkUpdate();

                    //Maj tables
                    parentActivity.MAJData();


                } else {
                    // Afficher Popup d'erreur "Veuillez vous connecter à internet"
                    showSimpleAlertDialog(getString(R.string.error_no_internet));
                }
            }
        });

        // clic sur le boutton "télécharger la dernière version"
        settings_vg.findViewById(R.id.settings_version_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ContainerActivity) getActivity()).displayDialogInstallNewApk();
            }
        });

        TextWatcher listener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //stop the default maj timer
                if(!parentActivity.isDefaultMAJTimerStopped()){
                    parentActivity.stopDefaultMAJ();
                }

                int interval;
                try{
                    interval = Integer.parseInt(s.toString());
                    Configuration.saveMAJInterval(s.toString());
                }
                catch (Exception e){
                    interval = Integer.parseInt(Configuration.getDefaultMAJInterval()) ;
                }

                if(majTimer == null){
                    majTimer = new MAJTimer();
                    majTimer.doMAJ(interval);
                }
                else{
                    majTimer.stopMAJ();
                    majTimer.doMAJ(interval);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        majInterval.addTextChangedListener(listener);

    }

    public void getInfos(String num_tablette){
        if(getActivity() != null && getActivity() instanceof ContainerActivity){
            ((ContainerActivity) getActivity()).loadInfosTablette(num_tablette);
        }
    }

    public void getUtilisateurs(int idAccount){
        if(getActivity() != null && getActivity() instanceof ContainerActivity){
            ((ContainerActivity) getActivity()).loadUsers(idAccount);
        }
    }


    public void loadApkUpdate(){
        if(getActivity() != null && getActivity() instanceof ContainerActivity){
            ((ContainerActivity) getActivity()).loadApkUpdate();
        }
    }

    public void displayTabletteLoader() {
        try {
            if (Configuration.getIsInfosTabletteLoaded()) {
                if (Configuration.getIsInfosTabletteSuccess()) {
                    settings_vg.findViewById(R.id.settings_ws_infos_error).setVisibility(View.INVISIBLE);
                    settings_vg.findViewById(R.id.settings_ws_infos_error_img).setVisibility(View.GONE);
                    settings_vg.findViewById(R.id.settings_ws_infos_progressbar).setVisibility(View.GONE);
                    settings_vg.findViewById(R.id.settings_ws_infos_check_img).setVisibility(View.VISIBLE);
                    settings_vg.findViewById(R.id.settings_ws_infos).setOnClickListener(null);
                } else {
                    settings_vg.findViewById(R.id.settings_ws_infos_progressbar).setVisibility(View.GONE);
                    settings_vg.findViewById(R.id.settings_ws_infos_check_img).setVisibility(View.GONE);
                    settings_vg.findViewById(R.id.settings_ws_infos_error).setVisibility(View.VISIBLE);
                    settings_vg.findViewById(R.id.settings_ws_infos_error_img).setVisibility(View.VISIBLE);
                    settings_vg.findViewById(R.id.settings_ws_infos).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showSimpleAlertDialog(Configuration.getInfosTabletteError());
                        }
                    });
                }
            } else {
                settings_vg.findViewById(R.id.settings_ws_infos_check_img).setVisibility(View.GONE);
                settings_vg.findViewById(R.id.settings_ws_infos_error).setVisibility(View.INVISIBLE);
                settings_vg.findViewById(R.id.settings_ws_infos_error_img).setVisibility(View.GONE);
                settings_vg.findViewById(R.id.settings_ws_infos_progressbar).setVisibility(View.VISIBLE);
                settings_vg.findViewById(R.id.settings_ws_infos).setOnClickListener(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayUsersLoader() {
        if(Configuration.getIsUsersLoaded()) {
            if(Configuration.getIsUsersSuccess()) {
                settings_vg.findViewById(R.id.settings_ws_users_error).setVisibility(View.INVISIBLE);
                settings_vg.findViewById(R.id.settings_ws_users_error_img).setVisibility(View.GONE);
                settings_vg.findViewById(R.id.settings_ws_users_progressbar).setVisibility(View.GONE);
                settings_vg.findViewById(R.id.settings_ws_users_check_img).setVisibility(View.VISIBLE);
                settings_vg.findViewById(R.id.settings_ws_users).setOnClickListener(null);
            } else {
                settings_vg.findViewById(R.id.settings_ws_users_progressbar).setVisibility(View.GONE);
                settings_vg.findViewById(R.id.settings_ws_users_check_img).setVisibility(View.GONE);
                settings_vg.findViewById(R.id.settings_ws_users_error).setVisibility(View.VISIBLE);
                settings_vg.findViewById(R.id.settings_ws_users_error_img).setVisibility(View.VISIBLE);
                settings_vg.findViewById(R.id.settings_ws_users).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showSimpleAlertDialog(Configuration.getUsersError());
                    }
                });
            }
        } else {
            settings_vg.findViewById(R.id.settings_ws_users_check_img).setVisibility(View.GONE);
            settings_vg.findViewById(R.id.settings_ws_users_error).setVisibility(View.INVISIBLE);
            settings_vg.findViewById(R.id.settings_ws_users_error_img).setVisibility(View.GONE);
            settings_vg.findViewById(R.id.settings_ws_users_progressbar).setVisibility(View.VISIBLE);
            settings_vg.findViewById(R.id.settings_ws_users).setOnClickListener(null);
        }
    }


    public void showSimpleAlertDialog(String message) {
        if(getActivity() != null && getActivity() instanceof  ContainerActivity) {
            ((ContainerActivity) getActivity()).showSimpleAlertDialog(getActivity(), getString(R.string.error_title_information), message);
        }
    }

    public String getUniqueCode() {
        String macHash = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        return String.valueOf(macHash.hashCode()).substring(6);
    }

    public class MAJTimer{
        Handler handler;
        Runnable runnable;

        public MAJTimer(){
            handler = new Handler();
        }

        public void doMAJ(int interval){
            final int inter = interval *60 * 1000;

            runnable = new Runnable(){
                @Override
                public void run() {
                    parentActivity.MAJData();
                    handler.postDelayed(this, inter);
                }
            };
            handler.postDelayed(runnable, inter);
        }

        public void stopMAJ(){
            handler.removeCallbacks(runnable);
        }
    }


}
