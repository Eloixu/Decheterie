package fr.trackoe.decheterie.ui.fragment;

/**
 * Created by Haocheng on 28/06/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fr.trackoe.decheterie.R;
import fr.trackoe.decheterie.database.DchDepotDB;
import fr.trackoe.decheterie.model.bean.global.Depot;
import fr.trackoe.decheterie.service.adapter.DepotListAdapter;


public class DepotListeFragment extends Fragment {
    private ViewGroup depotListe_vg;
    private ArrayList<Depot> listeDepots;
    private ListView lvDepot;
    private DepotListAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        depotListe_vg = (ViewGroup) inflater.inflate(R.layout.depot_liste_fragment, container, false);

        /*// Init Actionbar
        initActionBar();*/

        // Init Views
        initViews();

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
    public void initViews() {
        DchDepotDB depotDB = new DchDepotDB(getContext());
        depotDB.read();
        listeDepots = depotDB.getAllDepot();
        depotDB.close();

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
            /*lvDepot.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        DchDepotDB dchDepotDB = new DchDepotDB(getContext());
                        dchDepotDB.open();
                        Depot depot = dchDepotDB.getReleve(Long.valueOf(((TextView) view.findViewById(R.id.hide_id_releve)).getText().toString()));
                        rDB.updateIsSent(r.getIdReleve(), false);
                        rDB.updateStatut(r.getIdReleve(), getResources().getInteger(R.integer.statut_en_attente_envoi));
                        rDB.updateURL(r.getIdReleve(), r.getUrl().replace("ws_create_releve", "ws_update_releve"));
                        rDB.close();

                        ((ContainerActivity) getActivity()).sendReleve();


                        Toast.makeText(getContext(), getString(R.string.form_toast_envoi_formulaire), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return false;
                }
            });*/
        }

    }

    /**
     * Init listeners.
     */
    public void initListeners() {

    }

    public void notifyDataSetChanged() {
        if(adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

}
