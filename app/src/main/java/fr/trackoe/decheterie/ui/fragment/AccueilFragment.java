package fr.trackoe.decheterie.ui.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.idescout.sql.SqlScoutServer;

import fr.trackoe.decheterie.R;

import fr.trackoe.decheterie.configuration.Configuration;
import fr.trackoe.decheterie.ui.activity.ContainerActivity;

/**
 * Created by Trackoe on 13/03/2017.
 */

public class AccueilFragment extends Fragment {
    private ViewGroup accueil_vg;
    ContainerActivity parentActivity;
    FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        accueil_vg = (ViewGroup) inflater.inflate(R.layout.accueil_fragment, container, false);
        // Init Actionbar
        //initActionBar();

        // Init Views
        initViews();

        // Init des listeners
        initListeners();

        return accueil_vg;
    }

    @Override
    public void onResume() {
        super.onResume();
        Button btnRecherche = (Button) accueil_vg.findViewById(R.id.btn_recherche);
        Button btnIdentification = (Button) accueil_vg.findViewById(R.id.btn_identification);
        Button btnListe = (Button) accueil_vg.findViewById(R.id.btn_liste);
        Button btnChanger = (Button) accueil_vg.findViewById(R.id.btn_changer);
        TextView textViewNomDecheterie = (TextView) accueil_vg.findViewById(R.id.textView_nom_decheterie);
        if(Configuration.getNameDecheterie().isEmpty()||Configuration.getNameDecheterie()==null){
            textViewNomDecheterie.setText("Aucune déchèterie sélectionnée");
            btnIdentification.setClickable(false);
            btnIdentification.setBackgroundResource(R.drawable.button_grey);
            btnRecherche.setClickable(false);
            btnRecherche.setBackgroundResource(R.drawable.button_grey);
            btnListe.setClickable(false);
            btnListe.setBackgroundResource(R.drawable.button_grey);
        }
        else {
            textViewNomDecheterie.setText(Configuration.getNameDecheterie());
        }

    }

    /*
        * Init Actionbar
        */
    /*public void initActionBar() {
        if(getActivity() != null && getActivity() instanceof ContainerActivity) {
            ((ContainerActivity) getActivity()).showActionBarLogin();
        }
    }*/

    /*
    Init Views
     */
    public void initViews() {
        ((DrawerLocker) getActivity()).setDrawerEnabled(false);
        parentActivity = (ContainerActivity ) getActivity();
        parentActivity.hideHamburgerButton();

    }

    /*
    Init Listeners
     */
    public void initListeners() {
        //set listener for  button "changer déchèterie"
        accueil_vg.findViewById(R.id.btn_changer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity() != null && getActivity() instanceof  ContainerActivity) {
                    ((ContainerActivity) getActivity()).changeMainFragment(new DecheterieFragment(), true);
                    /*fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_container,new DepotFragment());
                    fragmentTransaction.commit();*/
                }
            }
        });

        //set listener for  button "Identification d'une carte"
        accueil_vg.findViewById(R.id.btn_identification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity() != null && getActivity() instanceof  ContainerActivity) {
                    ((ContainerActivity) getActivity()).changeMainFragment(new DepotFragment(), true);
                    /*fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_container,new DepotFragment());
                    fragmentTransaction.commit();*/
                }
            }
        });

    }
}
