package fr.trackoe.decheterie.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import fr.trackoe.decheterie.R;
import fr.trackoe.decheterie.configuration.Configuration;
import fr.trackoe.decheterie.ui.activity.ContainerActivity;

import static java.lang.Thread.sleep;

/**
 * Created by Remi on 02/05/2017.
 */
public class LoadingFragment extends Fragment {
    private ViewGroup main_vg;
    private ProgressBar progressBar;
    private TextView speTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        main_vg = (ViewGroup) inflater.inflate(R.layout.loading_fragment, container, false);

        // Init Actionbar
        initActionBar();

        // Init Views
        initViews();

        return main_vg;
    }

    /*
	 * Init Actionbar
	 */
    public void initActionBar() {
        if (getActivity() != null && getActivity() instanceof ContainerActivity) {
//            ((ContainerActivity) getActivity()).showActionBarTablette();
        }
    }

    /*
    Init Views
     */
    public void initViews() {
        // Init views
        progressBar = (ProgressBar) main_vg.findViewById(R.id.load_spe_progress);
        speTv = (TextView) main_vg.findViewById(R.id.load_text_spe);

        //launchHabitatAction();
        launchCarteActiveAction();
        //getJson();
    }

    public void launchHabitatAction() {
        speTv.setText(getString(R.string.load_habitat_tv));
        progressBar.setProgress(0);

        ((ContainerActivity) getActivity()).loadTypeHabitat();
        ((ContainerActivity) getActivity()).loadHabitat(Configuration.getIdAccount());

    }

    public void launchLocalAction() {
        if(main_vg != null) {
            main_vg.findViewById(R.id.load_habitat_progressbar).setVisibility(View.GONE);
            main_vg.findViewById(R.id.load_habitat_img_check).setVisibility(View.VISIBLE);

            if(getActivity() != null) {
                speTv.setText(getString(R.string.load_local_tv));
                progressBar.setProgress(0);
                ((ContainerActivity) getActivity()).loadLocal(Configuration.getIdAccount());
            }
        }
    }

    public void launchMenageAction() {
        if(main_vg != null) {
            main_vg.findViewById(R.id.load_local_progressbar).setVisibility(View.GONE);
            main_vg.findViewById(R.id.load_local_img_check).setVisibility(View.VISIBLE);

            if (getActivity() != null) {
                speTv.setText(getString(R.string.load_menage_tv));
                progressBar.setProgress(0);
                ((ContainerActivity) getActivity()).loadMenage(Configuration.getIdAccount());
            }
        }
    }

    public void launchUsagerAction() {
        if(main_vg != null) {
            main_vg.findViewById(R.id.load_menage_progressbar).setVisibility(View.GONE);
            main_vg.findViewById(R.id.load_menage_img_check).setVisibility(View.VISIBLE);

            if (getActivity() != null) {
                speTv.setText(getString(R.string.load_usager_tv));
                progressBar.setProgress(0);
                ((ContainerActivity) getActivity()).loadUsager(Configuration.getIdAccount());
            }
        }
    }

    public void launchUsagerHabitatAction() {
        if(main_vg != null) {
            main_vg.findViewById(R.id.load_usager_progressbar).setVisibility(View.GONE);
            main_vg.findViewById(R.id.load_usager_img_check).setVisibility(View.VISIBLE);

            if (getActivity() != null) {
                speTv.setText(getString(R.string.load_usager_habitat_tv));
                progressBar.setProgress(0);
                ((ContainerActivity) getActivity()).loadUsagerHabitat(Configuration.getIdAccount());
            }
        }
    }

    public void launchUsagerMenageAction() {
        if(main_vg != null) {
            main_vg.findViewById(R.id.load_usager_habitat_progressbar).setVisibility(View.GONE);
            main_vg.findViewById(R.id.load_usager_habitat_img_check).setVisibility(View.VISIBLE);

            if (getActivity() != null) {
                speTv.setText(getString(R.string.load_usager_menage_tv));
                progressBar.setProgress(0);
                ((ContainerActivity) getActivity()).loadUsagerMenage(Configuration.getIdAccount());
            }
        }
    }

    public void launchDecheterieAction() {
        if(main_vg != null) {
            main_vg.findViewById(R.id.load_usager_menage_progressbar).setVisibility(View.GONE);
            main_vg.findViewById(R.id.load_usager_menage_img_check).setVisibility(View.VISIBLE);

            if (getActivity() != null) {
                speTv.setText(getString(R.string.load_decheterie_tv));
                progressBar.setProgress(0);
                ((ContainerActivity) getActivity()).loadDecheterie(Configuration.getIdAccount());
            }
        }
    }

    public void launchCarteAction() {
        if(main_vg != null) {
            main_vg.findViewById(R.id.load_decheterie_progressbar).setVisibility(View.GONE);
            main_vg.findViewById(R.id.load_decheterie_img_check).setVisibility(View.VISIBLE);

            if (getActivity() != null) {
                speTv.setText(getString(R.string.load_carte_tv));
                progressBar.setProgress(0);
                ((ContainerActivity) getActivity()).loadTypeCarte();
                ((ContainerActivity) getActivity()).loadCarte(Configuration.getIdAccount());
            }
        }
    }

    public void launchCarteActiveAction(){
        if(main_vg != null) {
            main_vg.findViewById(R.id.load_carte_progressbar).setVisibility(View.GONE);
            main_vg.findViewById(R.id.load_carte_img_check).setVisibility(View.VISIBLE);

            if (getActivity() != null) {
                speTv.setText(getString(R.string.load_carte_active_tv));
                progressBar.setProgress(0);
                ((ContainerActivity) getActivity()).loadCarteEtatRaison();
                ((ContainerActivity) getActivity()).loadCarteActive(Configuration.getIdAccount());
            }
        }
    }

    public void launchComptePrepayeAction(){
        if(main_vg != null) {
            main_vg.findViewById(R.id.load_carte_active_progressbar).setVisibility(View.GONE);
            main_vg.findViewById(R.id.load_carte_active_img_check).setVisibility(View.VISIBLE);

            if (getActivity() != null) {
                speTv.setText(getString(R.string.load_compte_prepaye_tv));
                progressBar.setProgress(0);
                ((ContainerActivity) getActivity()).loadComptePrepaye(Configuration.getIdAccount());
            }
        }
    }

    public void launchFluxAction(){
        if(main_vg != null) {
            main_vg.findViewById(R.id.load_compte_prepaye_progressbar).setVisibility(View.GONE);
            main_vg.findViewById(R.id.load_compte_prepaye_img_check).setVisibility(View.VISIBLE);

            if (getActivity() != null) {
                speTv.setText(getString(R.string.load_flux_tv));
                progressBar.setProgress(0);
                ((ContainerActivity) getActivity()).loadFlux(Configuration.getIdAccount());
            }
        }
    }

    public void launchDecheterieFluxAction(){
        if(main_vg != null) {
            main_vg.findViewById(R.id.load_flux_progressbar).setVisibility(View.GONE);
            main_vg.findViewById(R.id.load_flux_img_check).setVisibility(View.VISIBLE);

            if (getActivity() != null) {
                speTv.setText(getString(R.string.load_flux_tv));
                progressBar.setProgress(0);
                ((ContainerActivity) getActivity()).loadDecheterieFlux(Configuration.getIdAccount());
            }
        }
    }

    public void launchUniteAction(){
        if(main_vg != null) {
            main_vg.findViewById(R.id.load_decheterie_flux_progressbar).setVisibility(View.GONE);
            main_vg.findViewById(R.id.load_decheterie_flux_img_check).setVisibility(View.VISIBLE);

            if (getActivity() != null) {
                speTv.setText(getString(R.string.load_unite_tv));
                progressBar.setProgress(0);
                ((ContainerActivity) getActivity()).loadUnite();
            }
        }
    }

    public void launchAccountSettingAction(){
        if(main_vg != null) {
            main_vg.findViewById(R.id.load_unite_progressbar).setVisibility(View.GONE);
            main_vg.findViewById(R.id.load_unite_img_check).setVisibility(View.VISIBLE);

            if (getActivity() != null) {
                speTv.setText(getString(R.string.load_account_setting_tv));
                progressBar.setProgress(0);
                ((ContainerActivity) getActivity()).loadAccountSetting(Configuration.getIdAccount());
            }
        }
    }

    public void endDownload() {
        if(main_vg != null) {
            main_vg.findViewById(R.id.load_account_setting_progressbar).setVisibility(View.GONE);
            main_vg.findViewById(R.id.load_account_setting_img_check).setVisibility(View.VISIBLE);

            if (getActivity() != null) {
                speTv.setText(getString(R.string.chargement_success));
                progressBar.setVisibility(View.GONE);
                ((ContainerActivity) getActivity()).copyDatabaseToSDCard(getContext());
            }
        }


    }

    //get JSON Object from server
    public void getJson(){
        String parsedString = "";

        try {

            URL url = new URL("http://192.168.1.38:8080/ws/"+"wsTypeCarte");
            URLConnection conn = url.openConnection();

            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            InputStream is = httpConn.getInputStream();
            parsedString = convertinputStreamToString(is);
            JSONObject jo = new JSONObject(parsedString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String convertinputStreamToString(InputStream ists)
            throws IOException {
        if (ists != null) {
            StringBuilder sb = new StringBuilder();
            String line;

            try {
                BufferedReader r1 = new BufferedReader(new InputStreamReader(
                        ists, "UTF-8"));
                while ((line = r1.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } finally {
                ists.close();
            }
            return sb.toString();
        } else {
            return "";
        }
    }



    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }
}
