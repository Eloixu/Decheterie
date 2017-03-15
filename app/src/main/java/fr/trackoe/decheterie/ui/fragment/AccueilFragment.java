package fr.trackoe.decheterie.ui.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import fr.trackoe.decheterie.R;

import fr.trackoe.decheterie.configuration.Configuration;
import fr.trackoe.decheterie.ui.activity.ContainerActivity;

/**
 * Created by Trackoe on 13/03/2017.
 */

public class AccueilFragment extends Fragment {
    private ViewGroup accueil_vg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        accueil_vg = (ViewGroup) inflater.inflate(R.layout.accueil_fragment, container, false);
        // Init Actionbar
        initActionBar();

        // Init Views
        initViews();

        // Init des listeners
        initListeners();

        return accueil_vg;
    }

    @Override
    public void onResume() {
        super.onResume();
        TextView textView = (TextView) accueil_vg.findViewById(R.id.textView_nom_decheterie);
        textView.setText(Configuration.getNameDecheterie());
    }

    /*
        * Init Actionbar
        */
    public void initActionBar() {
        if(getActivity() != null && getActivity() instanceof ContainerActivity) {
            ((ContainerActivity) getActivity()).showActionBarLogin();
        }
    }

    /*
    Init Views
     */
    public void initViews() {

    }

    /*
    Init Listeners
     */
    public void initListeners() {
        accueil_vg.findViewById(R.id.btn_changer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity() != null && getActivity() instanceof  ContainerActivity) {
                    ((ContainerActivity) getActivity()).changeMainFragment(new DecheterieFragment(), true);
                }
            }
        });

    }
}
