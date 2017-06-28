package fr.trackoe.decheterie.service.adapter;

/**
 * Created by Haocheng on 28/06/2017.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import fr.trackoe.decheterie.R;
import fr.trackoe.decheterie.Utils;
import fr.trackoe.decheterie.database.DchCarteActiveDB;
import fr.trackoe.decheterie.database.DchCarteDB;
import fr.trackoe.decheterie.database.DchComptePrepayeDB;
import fr.trackoe.decheterie.database.DchDepotDB;
import fr.trackoe.decheterie.database.UsagerDB;
import fr.trackoe.decheterie.model.bean.global.Depot;
import fr.trackoe.decheterie.model.bean.usager.Usager;
import fr.trackoe.decheterie.ui.activity.ContainerActivity;


/**
 * Affiche les depots par ordre décroissant de date de réalisation
 *
 * Adapter utilisé dans
 * @see fr.trackoe.decheterie.ui.fragment.DepotListeFragment
 *
 * Action possible:
 *
 *
 *
 * 2 types de vue:
 * - Soit on affiche la date
 * - Soit on affiche le détail d'un relevé
 *
 * @author rcoquet
 * @version 1.0
 *
 */

public class DepotListAdapter extends BaseAdapter {
    private final Context ctx;
    private ArrayList<Depot> depots;
    private HashMap<Integer, String> mapDates;
    private HashMap<Integer, Depot> mapDepot;
    public int countDates;

    /**
     * Instantiates a new Formulaires list adapter.
     *
     * @param ctx     the ctx
     * @param depots  la liste des depots
     */
    public DepotListAdapter(Context ctx, ArrayList<Depot> depots) {
        this.ctx = ctx;
        this.depots = depots;
        mapDepot = new HashMap<Integer, Depot>();
        mapDates = new HashMap<Integer, String>();
        initMap();

    }

    /**
     * Permet de trier, d'organiser, et de regrouper les depots par dates
     * Facilite l'affichage soit de la date, soit du détail d'un depot
     */
    public void initMap() {
        mapDepot.clear();
        mapDates.clear();

        countDates = 0;

        for (int i = 0; i < depots.size(); i++) {
            fillMap(depots.get(i), i);
        }

        if(ctx != null && depots.isEmpty() && ((ContainerActivity) ctx).findViewById(R.id.liste_aucun_depot_view) != null) {
            ((ContainerActivity) ctx).findViewById(R.id.liste_aucun_depot_view).setVisibility(View.VISIBLE);
        }
    }

    /**
     * Mécanisme permettant de générer les map
     *
     * @param d depot
     * @param i index
     */
    public void fillMap(Depot d, int i) {

        if(mapDates.containsValue(d.getDateHeure().substring(0,8))) {
            mapDepot.put(i + countDates, d);
        } else {
            mapDates.put(i + countDates, d.getDateHeure().substring(0,8));
            countDates ++;
            mapDepot.put(i + countDates, d);
        }
    }

    @Override
    public int getCount() {
        return mapDepot.size() + mapDates.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;

        DchDepotDB dchDepotDB = new DchDepotDB(ctx);
        dchDepotDB.open();
        DchComptePrepayeDB dchComptePrepayeDB = new DchComptePrepayeDB(ctx);
        dchComptePrepayeDB.open();
        UsagerDB usagerDB = new UsagerDB(ctx);
        usagerDB.open();
        DchCarteActiveDB dchCarteActiveDB = new DchCarteActiveDB(ctx);
        dchCarteActiveDB.open();
        DchCarteDB dchCarteDB = new DchCarteDB(ctx);
        dchCarteDB.open();

        if(mapDates.containsKey(position)) {//add date title
            rowView = inflater.inflate(R.layout.view_date_depot, parent, false);
            SimpleDateFormat format = new SimpleDateFormat(ctx.getString(R.string.depot_date_format_in));
            try {
                Date d = format.parse(mapDates.get(position));
                SimpleDateFormat f = new SimpleDateFormat(ctx.getString(R.string.intervention_date_format_out));
                String s = f.format(d);
                if(s.length() > 2) {
                    s = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
                }
                if(position == 0) {
                    s = ctx.getString(R.string.inter_liste_date_realisation) + s;
                }
                ((TextView) rowView.findViewById(R.id.depot_date_textView)).setText(s);
            } catch (ParseException e) {
                ((TextView) rowView.findViewById(R.id.depot_date_textView)).setText(mapDates.get(position));
            }
        } else {//add depot information
            final Depot depot;
            depot = mapDepot.get(position);

            rowView = inflater.inflate(R.layout.view_depot_traite, parent, false);

            ((TextView) rowView.findViewById(R.id.hide_id_depot)).setText(String.valueOf(depot.getId()));

            // Init views
            Usager usager = usagerDB.getUsagerFromID(dchComptePrepayeDB.getComptePrepayeFromID(depot.getComptePrepayeId()).getDchUsagerId());
            ((TextView) rowView.findViewById(R.id.depot_rv_nom_usager_textView)).setText(usager.getNom() + " " + usager.getPrenom());
            if(depot.getCarteActiveCarteId() == 0 || depot.getCarteActiveCarteId() == -1) {

            } else {
                ((TextView) rowView.findViewById(R.id.depot_rv_num_carte_textView)).setText(dchCarteDB.getCarteFromID(depot.getCarteActiveCarteId()).getNumCarte());
            }
            SimpleDateFormat format = new SimpleDateFormat(ctx.getString(R.string.db_date_format));
            try {
                Date d = format.parse(depot.getDateHeure());
                SimpleDateFormat f = new SimpleDateFormat(ctx.getString(R.string.releve_date_format_out));
                ((TextView) rowView.findViewById(R.id.depot_rv_date_textView)).setText(f.format(d));
            } catch (ParseException e) {
                ((TextView) rowView.findViewById(R.id.depot_rv_date_textView)).setText(depot.getDateHeure());
            }


            if(position %2 == 0) {
                ((RelativeLayout) rowView.findViewById(R.id.inter_view_container)).setBackgroundColor(ContextCompat.getColor(ctx, R.color.interGrey));
            }

            // Afficher l'image
            if(depot.getStatut() == ctx.getResources().getInteger(R.integer.statut_en_cours)) {
                rowView.findViewById(R.id.inter_view_img_ko).setVisibility(View.GONE);
                rowView.findViewById(R.id.inter_view_img_ok).setVisibility(View.GONE);
                rowView.findViewById(R.id.inter_view_img_edit).setVisibility(View.VISIBLE);
            } else if(!depot.isSent()) {
                rowView.findViewById(R.id.inter_view_img_ok).setVisibility(View.GONE);
                rowView.findViewById(R.id.inter_view_img_edit).setVisibility(View.GONE);
                rowView.findViewById(R.id.inter_view_img_ko).setVisibility(View.VISIBLE);
            } else if(depot.isSent()){
                rowView.findViewById(R.id.inter_view_img_edit).setVisibility(View.GONE);
                rowView.findViewById(R.id.inter_view_img_ko).setVisibility(View.GONE);
                rowView.findViewById(R.id.inter_view_img_ok).setVisibility(View.VISIBLE);
            }

        }

        dchDepotDB.close();
        dchComptePrepayeDB.close();
        usagerDB.close();
        dchCarteActiveDB.close();
        dchCarteDB.close();

        return rowView;
    }


}
