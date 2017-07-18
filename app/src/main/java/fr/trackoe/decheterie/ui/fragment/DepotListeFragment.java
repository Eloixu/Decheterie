package fr.trackoe.decheterie.ui.fragment;

/**
 * Created by Haocheng on 28/06/2017.
 */

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fr.trackoe.decheterie.R;
import fr.trackoe.decheterie.Utils;
import fr.trackoe.decheterie.configuration.Configuration;
import fr.trackoe.decheterie.database.DchAccountSettingDB;
import fr.trackoe.decheterie.database.DchApportFluxDB;
import fr.trackoe.decheterie.database.DchCarteActiveDB;
import fr.trackoe.decheterie.database.DchCarteDB;
import fr.trackoe.decheterie.database.DchDepotDB;
import fr.trackoe.decheterie.model.bean.global.AccountSetting;
import fr.trackoe.decheterie.model.bean.global.ApportFlux;
import fr.trackoe.decheterie.model.bean.global.Carte;
import fr.trackoe.decheterie.model.bean.global.CarteActive;
import fr.trackoe.decheterie.model.bean.global.ComptePrepaye;
import fr.trackoe.decheterie.model.bean.global.Depot;
import fr.trackoe.decheterie.service.adapter.DepotListAdapter;
import fr.trackoe.decheterie.ui.activity.ContainerActivity;
import fr.trackoe.decheterie.ui.dialog.CustomDialogNormal;


public class DepotListeFragment extends Fragment {
    ContainerActivity parentActivity;
    private ViewGroup depotListe_vg;
    private ListView lvDepot;
    private DepotListAdapter adapter;
    private Button btnAfficherDepotNonSynchro;
    private Button btnSynchronisatrion;
    private boolean isAfficherDepotNonSynchro = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity = (ContainerActivity ) getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        depotListe_vg = (ViewGroup) inflater.inflate(R.layout.depot_liste_fragment, container, false);
        btnAfficherDepotNonSynchro = (Button)depotListe_vg.findViewById(R.id.btn_afficher_depot_non_synchro);
        btnSynchronisatrion        = (Button)depotListe_vg.findViewById(R.id.btn_synchro);


        /*// Init Actionbar
        initActionBar();*/


        // Init Views
        initViews(initListDepot());

        // Init des listeners
        initListeners();

        return depotListe_vg;
    }

/*
    *//**
     * Init action bar.
     *//*
    public void initActionBar() {
        if (getActivity() != null && getActivity() instanceof ContainerActivity) {
            ((ContainerActivity) getActivity()).showActionBarMenuFormulaire();
        }
    }*/


    /**
     * Init views.
     */
    public void initViews(ArrayList<Depot> listeDepots) {
        if(listeDepots.isEmpty()) {
            depotListe_vg.findViewById(R.id.liste_depot_lv).setVisibility(View.GONE);
            depotListe_vg.findViewById(R.id.liste_aucun_depot_view).setVisibility(View.VISIBLE);
        } else {
            lvDepot = (ListView) depotListe_vg.findViewById(R.id.liste_depot_lv);
            adapter = new DepotListAdapter(getContext(), listeDepots);
            lvDepot.setAdapter(adapter);
            depotListe_vg.findViewById(R.id.liste_depot_lv).setVisibility(View.VISIBLE);
            depotListe_vg.findViewById(R.id.liste_aucun_depot_view).setVisibility(View.GONE);
        }

        if(lvDepot != null) {
            lvDepot.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        DchDepotDB dchDepotDB = new DchDepotDB(getContext());
                        dchDepotDB.open();
                        Depot depot = dchDepotDB.getDepotByIdentifiant(Long.valueOf(((TextView) view.findViewById(R.id.hide_id_depot)).getText().toString()));
                        dchDepotDB.close();

                        if (!depot.isSent() && Utils.isInternetConnected(getContext())){
                            sendDepot(depot);
                            Toast.makeText(getContext(), getString(R.string.form_toast_envoi_depot), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return false;
                }
            });
        }

    }

    public ArrayList<Depot> initListDepot(){
        DchDepotDB depotDB = new DchDepotDB(getContext());
        depotDB.read();
        ArrayList<Depot> listeDepots = depotDB.getAllDepot();
        depotDB.close();

        return listeDepots;
    }

    /**
     * Init listeners.
     */
    public void initListeners() {
        btnSynchronisatrion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //check the internet
                if(!Utils.isInternetConnected(getContext())){//pop up
                    parentActivity.showCustomDialogNormal(getResources().getString(R.string.depot_liste_fragment_pop_up_non_internet_title),getResources().getString(R.string.depot_liste_fragment_pop_up_non_internet_message),null,getResources().getString(R.string.depot_liste_fragment_pop_up_non_internet_negative_button));
                }else{//send all the depots which are not sent
                    DchDepotDB dchDepotDB = new DchDepotDB(getContext());
                    dchDepotDB.open();
                    ArrayList<Depot> depotList = dchDepotDB.getDepotListByIsSent(false);
                    dchDepotDB.close();

                    if(!depotList.isEmpty()) {
                        for (Depot depot : depotList) {
                            sendDepot(depot);
                        }
                        Toast.makeText(getContext(), getString(R.string.form_toast_envoi_depot), Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });

        btnAfficherDepotNonSynchro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DchDepotDB dchDepotDB = new DchDepotDB(getContext());
                dchDepotDB.read();
                if(dchDepotDB.getDepotListByIsSent(false).size() == 0){
                    parentActivity.showCustomDialogNormal(getResources().getString(R.string.depot_liste_fragment_pop_up_non_synchro_title),getResources().getString(R.string.depot_liste_fragment_pop_up_non_synchro_message),null,getResources().getString(R.string.depot_liste_fragment_pop_up_non_synchro_negative_button));
                }
                else{
                    initViews(dchDepotDB.getDepotListByIsSent(false));
                    notifyDataSetChanged();
                }
                dchDepotDB.close();

                isAfficherDepotNonSynchro = true;
            }
        });
    }

    public void notifyDataSetChanged() {
        if(adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public void sendDepot(Depot depot){
        DchCarteActiveDB dchCarteActiveDB = new DchCarteActiveDB(getContext());
        dchCarteActiveDB.open();
        DchCarteDB dchCarteDB = new DchCarteDB(getContext());
        dchCarteDB.open();
        DchAccountSettingDB dchAccountSettingDB = new DchAccountSettingDB(getContext());
        dchAccountSettingDB.open();

        //get the apportFluxList associated to the depot
        AccountSetting accountSetting = new AccountSetting();

        DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
        dchApportFluxDB.open();
        ArrayList<ApportFlux> apportFluxList = dchApportFluxDB.getListeApportFluxByDepotId(depot.getId());
        dchApportFluxDB.close();
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat(getString(R.string.normal_date_format));
        int date = Integer.parseInt(df.format(d));

        if(depot.getCarteActiveCarteId() == 0 || depot.getCarteActiveCarteId() == 0){//send the depot which is created in the way: RechercherUsagerFragment ---> DepotFragment
            Carte carte = new Carte();
            //take the first carteActive associated to the depot.id_dch_carte_active
            ArrayList<CarteActive> carteActiveList = dchCarteActiveDB.getCarteActiveListByComptePrepayeId(depot.getComptePrepayeId());
            for(CarteActive ca: carteActiveList){
                if(ca.isActive()){
                    carte = dchCarteDB.getCarteFromID(ca.getDchCarteId());
                    break;
                }
            }
            ArrayList<AccountSetting> accountSettingList = dchAccountSettingDB.getListeAccountSettingByAccountIdAndTypeCarteId(carte.getDchAccountId(),carte.getDchTypeCarteId());
            if (accountSettingList != null) {
                for (AccountSetting as : accountSettingList) {
                    int dateDebut = Integer.parseInt(as.getDateDebutParam());
                    int dateFin = Integer.parseInt(as.getDateFinParam());
                    if (date >= dateDebut && date <= dateFin) {
                        accountSetting = as;
                    }
                }
            }
            parentActivity.sendDepot(depot,accountSetting,apportFluxList);
        }
        else{//send the normal created depot
            Carte carte = dchCarteDB.getCarteFromID(depot.getCarteActiveCarteId());
            ArrayList<AccountSetting> accountSettingList = dchAccountSettingDB.getListeAccountSettingByAccountIdAndTypeCarteId(carte.getDchAccountId(),carte.getDchTypeCarteId());
            if (accountSettingList != null) {
                for (AccountSetting as : accountSettingList) {
                    int dateDebut = Integer.parseInt(as.getDateDebutParam());
                    int dateFin = Integer.parseInt(as.getDateFinParam());
                    if (date >= dateDebut && date <= dateFin) {
                        accountSetting = as;
                    }
                }
            }
            parentActivity.sendDepot(depot,accountSetting,apportFluxList);
        }

        dchCarteActiveDB.close();
        dchCarteDB.close();
        dchAccountSettingDB.close();

    }

    public boolean isAfficherDepotNonSynchro() {
        return isAfficherDepotNonSynchro;
    }
}
