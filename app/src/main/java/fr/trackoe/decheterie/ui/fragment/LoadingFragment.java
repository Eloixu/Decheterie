package fr.trackoe.decheterie.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

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

        launchHabitatAction();
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

    public void endDownload() {
        if(main_vg != null) {
            main_vg.findViewById(R.id.load_decheterie_progressbar).setVisibility(View.GONE);
            main_vg.findViewById(R.id.load_decheterie_img_check).setVisibility(View.VISIBLE);

            if (getActivity() != null) {
                speTv.setText(getString(R.string.chargement_success));
                ((ContainerActivity) getActivity()).copyDatabaseToSDCard(getContext());
            }
        }


    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }
}
