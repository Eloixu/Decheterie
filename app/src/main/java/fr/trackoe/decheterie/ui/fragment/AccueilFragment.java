package fr.trackoe.decheterie.ui.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.idescout.sql.SqlScoutServer;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Queue;

import fr.trackoe.decheterie.R;

import fr.trackoe.decheterie.configuration.Configuration;
import fr.trackoe.decheterie.database.DchDepotDB;
import fr.trackoe.decheterie.database.DecheterieDB;
import fr.trackoe.decheterie.model.bean.global.Depot;
import fr.trackoe.decheterie.ui.activity.ContainerActivity;
import fr.trackoe.decheterie.ui.dialog.CustomDialogNormal;

import android.os.SerialPortServiceManager;





/**
 * Created by Haocheng on 13/03/2017.
 */

public class AccueilFragment extends Fragment {
    private ViewGroup accueil_vg;
    ContainerActivity parentActivity;
    FragmentTransaction fragmentTransaction;
    TextView textViewNomDecheterie;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("AccueilFragment-->onCreateView()");
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
        System.out.println("AccueilFragment-->onResume()");
        super.onResume();

        DchDepotDB dchDepotDB = new DchDepotDB(getContext());
        dchDepotDB.open();
        DecheterieDB decheterieDB = new DecheterieDB(getContext());
        decheterieDB.open();

        Button btnRecherche = (Button) accueil_vg.findViewById(R.id.btn_recherche);
        Button btnIdentification = (Button) accueil_vg.findViewById(R.id.btn_identification);
        Button btnListe = (Button) accueil_vg.findViewById(R.id.btn_liste);
        Button btnChanger = (Button) accueil_vg.findViewById(R.id.btn_changer);
        textViewNomDecheterie = (TextView) accueil_vg.findViewById(R.id.textView_nom_decheterie);
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

        //detect if there is a depot which is on statut "statut_en_cours" and the its decheterie matches the current decheterie id
        if(dchDepotDB.getDepotByStatut(getResources().getInteger(R.integer.statut_en_cours)) != null){
            if(dchDepotDB.getDepotByStatut(getResources().getInteger(R.integer.statut_en_cours)).getDecheterieId() == decheterieDB.getDecheterieByName(Configuration.getNameDecheterie()).getId()) {
                //pop-up ask if continue this depot
                CustomDialogNormal.Builder builder = new CustomDialogNormal.Builder(getContext());
                builder.setMessage("\nVous avez un dépot qui n'est pas encore terminé.\n" +
                        "Est-ce que vous voulez continuer à éditer ce dépot?");
                builder.setTitle("Information");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //设置你的操作事项
                        //turn to the page DepotFragment according to the depot
                        if (getActivity() != null && getActivity() instanceof ContainerActivity) {
                            //set a flag
                            Configuration.setIsOuiClicked(true);
                            ((ContainerActivity) getActivity()).changeMainFragment(new DepotFragment(), true);
                        }
                    }
                });

                builder.setNegativeButton("Non", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DchDepotDB dchDepotDB = new DchDepotDB(getContext());
                        dchDepotDB.open();

                        dialog.dismiss();

                        //change the depot "statut" to "statut_annuler"
                        Depot depot = dchDepotDB.getDepotByStatut(getResources().getInteger(R.integer.statut_en_cours));
                        dchDepotDB.changeDepotStatutByIdentifiant(depot.getId(), getResources().getInteger(R.integer.statut_annuler));

                        dchDepotDB.close();
                    }
                });

                builder.create().show();
            }
        }




        dchDepotDB.close();
        decheterieDB.close();

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
                    Configuration.setIsOuiClicked(false);
                    ((ContainerActivity) getActivity()).changeMainFragment(new DepotFragment(), true);
                    /*fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_container,new DepotFragment());
                    fragmentTransaction.commit();*/
                }
            }
        });

        accueil_vg.findViewById(R.id.btn_identification).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(getActivity() != null && getActivity() instanceof  ContainerActivity) {
                    Configuration.setIsOuiClicked(false);
                    ((ContainerActivity) getActivity()).changeMainFragment(new IdentificationFragment(), true);
                }
                return true;
            }
        });

        accueil_vg.findViewById(R.id.btn_changer).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                parentActivity.copyDatabaseToSDCard(getContext());
                return true;
            }
        });

        accueil_vg.findViewById(R.id.btn_recherche).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(getActivity() != null && getActivity() instanceof  ContainerActivity) {
                    ((ContainerActivity) getActivity()).changeMainFragment(new ApportProFragment(), true);
                }
                return true;
            }
        });

        accueil_vg.findViewById(R.id.btn_liste).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(getActivity() != null && getActivity() instanceof  ContainerActivity) {
                    ((ContainerActivity) getActivity()).changeMainFragment(new TestNFCFragment(), true);
                }
                return true;
            }
        });

    }




}
