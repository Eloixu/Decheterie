package fr.trackoe.decheterie.ui.fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.trackoe.decheterie.R;
import fr.trackoe.decheterie.Utils;
import fr.trackoe.decheterie.configuration.Configuration;
import fr.trackoe.decheterie.model.Const;
import fr.trackoe.decheterie.ui.activity.ContainerActivity;

/**
 * Created by Remi on 01/12/2015.
 */
public class SettingsFragment extends Fragment {
    private ViewGroup settings_vg;
    private boolean isFirstLaunch = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        settings_vg = (ViewGroup) inflater.inflate(R.layout.settings_fragment, container, false);

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

        // Modules
        displayModulesLoader();

        // Maj Apk
        if(Configuration.getIsApkReadyToInstall()) {
            settings_vg.findViewById(R.id.settings_version_btn).setVisibility(View.VISIBLE);
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

                    // Récupération des utilisateurs
                    getUtilisateurs(numTablette);

                    // Récupération des modules
                    getModules(numTablette);

                    // Maj Apk
                    loadApkUpdate();

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
    }

    public void getInfos(String num_tablette){
        if(getActivity() != null && getActivity() instanceof ContainerActivity){
            ((ContainerActivity) getActivity()).loadInfosTablette(num_tablette);
        }
    }

    public void getUtilisateurs(String num_tablette){
        if(getActivity() != null && getActivity() instanceof ContainerActivity){
            ((ContainerActivity) getActivity()).loadUsers(num_tablette);
        }
    }

    public void getModules(String num_tablette){
        if(getActivity() != null && getActivity() instanceof ContainerActivity){
            ((ContainerActivity) getActivity()).loadModules(num_tablette);
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

    public void displayModulesLoader() {
        if(Configuration.getIsModulesLoaded()) {
            if(Configuration.getIsModulesSuccess()) {
                settings_vg.findViewById(R.id.settings_ws_modules_error).setVisibility(View.INVISIBLE);
                settings_vg.findViewById(R.id.settings_ws_modules_error_img).setVisibility(View.GONE);
                settings_vg.findViewById(R.id.settings_ws_modules_progressbar).setVisibility(View.GONE);
                settings_vg.findViewById(R.id.settings_ws_modules_check_img).setVisibility(View.VISIBLE);
                settings_vg.findViewById(R.id.settings_ws_modules).setOnClickListener(null);
            } else {
                settings_vg.findViewById(R.id.settings_ws_modules_progressbar).setVisibility(View.GONE);
                settings_vg.findViewById(R.id.settings_ws_modules_check_img).setVisibility(View.GONE);
                settings_vg.findViewById(R.id.settings_ws_modules_error).setVisibility(View.VISIBLE);
                settings_vg.findViewById(R.id.settings_ws_modules_error_img).setVisibility(View.VISIBLE);
                settings_vg.findViewById(R.id.settings_ws_modules).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showSimpleAlertDialog(Configuration.getModulesError());
                    }
                });
            }
        } else {
            settings_vg.findViewById(R.id.settings_ws_modules_check_img).setVisibility(View.GONE);
            settings_vg.findViewById(R.id.settings_ws_modules_error).setVisibility(View.INVISIBLE);
            settings_vg.findViewById(R.id.settings_ws_modules_error_img).setVisibility(View.GONE);
            settings_vg.findViewById(R.id.settings_ws_modules_progressbar).setVisibility(View.VISIBLE);
            settings_vg.findViewById(R.id.settings_ws_modules).setOnClickListener(null);
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

}
