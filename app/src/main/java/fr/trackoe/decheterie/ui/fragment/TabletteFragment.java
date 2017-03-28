package fr.trackoe.decheterie.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import fr.trackoe.decheterie.R;
import fr.trackoe.decheterie.Utils;
import fr.trackoe.decheterie.configuration.Configuration;
import fr.trackoe.decheterie.model.Datas;
import fr.trackoe.decheterie.model.bean.global.ContenantBean;
import fr.trackoe.decheterie.service.callback.DataCallback;
import fr.trackoe.decheterie.ui.activity.ContainerActivity;

/**
 * Created by Remi on 01/12/2015.
 */
public class TabletteFragment extends Fragment {
    private ViewGroup tablette_vg;
    private EditText numTabletteEdittxt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tablette_vg = (ViewGroup) inflater.inflate(R.layout.tablette_fragment, container, false);

        // Init Actionbar
        //initActionBar();

        // Init Views
        initViews();

        // Init des listeners
        initListeners();

        return tablette_vg;
    }

    /*
	 * Init Actionbar
	 */
    /*public void initActionBar() {
        if (getActivity() != null && getActivity() instanceof ContainerActivity) {
            ((ContainerActivity) getActivity()).showActionBarTablette();
        }
    }*/

    /*
    Init Views
     */
    public void initViews() {
        numTabletteEdittxt = (EditText) tablette_vg.findViewById(R.id.tablette_edittxt);

        // Affichage du Switch
        if(Configuration.getInstance(getContext()).isProd()) {
            tablette_vg.findViewById(R.id.settings_env_layout).setVisibility(View.GONE);
        } else {
            tablette_vg.findViewById(R.id.settings_env_layout).setVisibility(View.VISIBLE);
            ((Switch) tablette_vg.findViewById(R.id.settings_env_switch)).setChecked(Configuration.getInstance(getContext()).getIsProdEnvWS());
            ((TextView) tablette_vg.findViewById(R.id.settings_env_url)).setText(Configuration.getInstance(getContext()).getWebServiceHost(getContext()));
        }
    }

    /*
    Init Listeners
     */
    public void initListeners() {
        // clic sur le boutton "OK"
        tablette_vg.findViewById(R.id.tablette_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String numTablette = numTabletteEdittxt.getText().toString();
                if (Utils.isStringEmpty(numTablette)) {
                    // Afficher Popup d'erreur "Veuillez saisir un numéro"
                    showSimpleAlertDialog(getString(R.string.error_num_tablette_empty));
                } else if (numTablette.length() > getResources().getInteger(R.integer.num_size_45)) {
                    // Afficher Popup d'erreur "Numéro trop long"
                    showSimpleAlertDialog(getString(R.string.error_num_tablette_too_long));
                } else if(getActivity() != null && Utils.isInternetConnected(getActivity())) {

                    Datas.loadAbonnement(getContext(), new DataCallback<ContenantBean>() {
                        @Override
                        public void dataLoaded(ContenantBean rep) {
                            if (rep.ismSuccess()) {
                                // Appel des WS
                                // Récupération des infos liées à la tablette
                                getInfos(numTablette);

                                // Récupération des utilisateurs
                                getUtilisateurs(numTablette);

                                // Récupération des modules
                                getModules(numTablette);
                            } else {
                                showSimpleAlertDialog(rep.getmError());
                            }
                        }
                    }, numTablette);
                } else {
                    // Afficher Popup d'erreur "Veuillez vous connecter à internet"
                    showSimpleAlertDialog(getString(R.string.error_no_internet));
                }
            }
        });

        // Gestion du switch choix de l'environnement
        ((Switch) tablette_vg.findViewById(R.id.settings_env_switch)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Configuration.getInstance(getContext()).saveIsProdEnvWS(isChecked);
                ((TextView) tablette_vg.findViewById(R.id.settings_env_url)).setText(Configuration.getInstance(getContext()).getWebServiceHost(getContext()));
            }
        });


    }

    public void getInfos(String numTablette){
        if(getActivity() != null && getActivity() instanceof ContainerActivity){
            ((ContainerActivity) getActivity()).loadInfosTablette(numTablette);
        }
    }

    public void getUtilisateurs(String numTablette){
        if(getActivity() != null && getActivity() instanceof ContainerActivity){
            ((ContainerActivity) getActivity()).loadUsers(numTablette);
        }
    }
    public void getModules(String numTablette){
        if(getActivity() != null && getActivity() instanceof ContainerActivity){
            ((ContainerActivity) getActivity()).loadModules(numTablette);
        }
    }

    public void showSimpleAlertDialog(String message) {
        if(getActivity() != null && getActivity() instanceof  ContainerActivity) {
            ((ContainerActivity) getActivity()).showSimpleAlertDialog(getActivity(), getString(R.string.error_title_information), message);
        }
    }

}
