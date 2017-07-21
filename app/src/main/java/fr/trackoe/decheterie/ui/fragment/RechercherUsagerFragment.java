package fr.trackoe.decheterie.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import fr.trackoe.decheterie.R;
import fr.trackoe.decheterie.Utils;
import fr.trackoe.decheterie.database.DchTypeCarteDB;
import fr.trackoe.decheterie.database.UsagerDB;
import fr.trackoe.decheterie.model.bean.global.TypeCarte;
import fr.trackoe.decheterie.model.bean.usager.UsagerFilter;
import fr.trackoe.decheterie.ui.activity.ContainerActivity;

/**
 * Created by Haocheng on 27/04/2017.
 */

public class RechercherUsagerFragment extends Fragment {
    private ViewGroup RU_vg;
    ContainerActivity parentActivity;

    private ArrayList<UsagerFilter> listeUsagerFilter;

    private Spinner spinner;
    private ArrayAdapter<TypeCarte> spinAdapterTC;
    //editText
    private EditText editTextNom;
    private EditText editTextAdresse;

    //listView
    private ListView listView;
    private UsagerAdresseAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity = (ContainerActivity ) getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("RechercherUsager-->onCreateView()");

        RU_vg = (ViewGroup) inflater.inflate(R.layout.rechercher_usager_fragment, container, false);
        editTextNom = (EditText) RU_vg.findViewById(R.id.rechercher_usager_fragment_filtre1_editText);
        spinner = (Spinner) RU_vg.findViewById(R.id.rechercher_usager_fragment_filtre2_spinner);
        editTextAdresse = (EditText) RU_vg.findViewById(R.id.rechercher_usager_fragment_filtre3_editText);
        listView = (ListView) RU_vg.findViewById(R.id.rechercher_usager_fragment_usager_listView);

        // Init Views
        initViews();

        // Init des listeners
        initListeners();

        return RU_vg;
    }

    @Override
    public void onResume() {
        System.out.println("RechercherUsagerFragment-->onResume()");
        super.onResume();
    }

    /*
    Init Views
     */
    public void initViews() {
        parentActivity.setTitleToolbar(getResources().getString(R.string.title_recherche_usager_fragment));
        ((DrawerLocker) getActivity()).setDrawerEnabled(false);
        parentActivity.hideHamburgerButton();

        DchTypeCarteDB dchTypeCarteDB = new DchTypeCarteDB(getContext());
        dchTypeCarteDB.open();
        ArrayList<TypeCarte> listTypeCarte = dchTypeCarteDB.getAllTypeCarte();
        dchTypeCarteDB.close();

        spinAdapterTC = new ArrayAdapter<TypeCarte>(getContext(), R.layout.spinner_item, listTypeCarte);
        spinAdapterTC.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinAdapterTC);

        filterResult();

        adapter = new UsagerAdresseAdapter(getContext(), listeUsagerFilter);
        listView.setAdapter(adapter);
    }

    /*
    Init Listeners
     */
    public void initListeners() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterResult();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s.length() > 1) {
                    filterResult();
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        editTextNom.addTextChangedListener(tw);
        editTextAdresse.addTextChangedListener(tw);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getActivity() != null && getActivity() instanceof ContainerActivity) {
                    DepotFragment depotFragment = DepotFragment.newInstance(Integer.valueOf(((TextView) view.findViewById(R.id.ruv_id)).getText().toString()), ((TypeCarte) spinner.getSelectedItem()).getId(), true);
                    ((ContainerActivity) getActivity()).changeMainFragment(depotFragment, true);
                }
            }
        });

    }

    public void filterResult() {
        String nomUsager = editTextNom.getText().toString();
        int idTypeCarte = ((TypeCarte) spinner.getSelectedItem()).getId();
        String adresse = editTextAdresse.getText().toString();

        UsagerDB udb = new UsagerDB(getContext());
        udb.read();
        listeUsagerFilter = udb.filterResult(nomUsager, idTypeCarte, adresse);
        udb.close();

        try {
            adapter.setListeU(listeUsagerFilter);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class UsagerAdresseAdapter extends BaseAdapter {
    private Context ctx;
    private ArrayList<UsagerFilter> listeU;

    public UsagerAdresseAdapter(Context ctx, ArrayList<UsagerFilter> listeU) {
        this.ctx = ctx;
        this.listeU = listeU;
    }

    public void setListeU(ArrayList<UsagerFilter> listeU) {
        this.listeU = listeU;
    }

    @Override
    public int getCount() {
        return listeU.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;

        UsagerFilter uf = listeU.get(position);

        rowView = inflater.inflate(R.layout.rechercher_usager_fragment_listview_item, parent, false);
        ((TextView) rowView.findViewById(R.id.ruv_nom)).setText(uf.getNom());
        ((TextView) rowView.findViewById(R.id.ruv_id)).setText(String.valueOf(uf.getId()));

        //adresse
        String sville = "";
        if(!Utils.isStringEmpty(uf.getH1Ville())) {
            sville = uf.getH1Numero() +
                    uf.getH1Complement() + " " +
                    uf.getH1Adresse() + ", " +
                    uf.getH1Cp() + ", " +
                    uf.getH1Ville();
        } else if(!Utils.isStringEmpty(uf.getH2Ville())) {
            sville = uf.getH2Numero() +
                    uf.getH2Complement() + " " +
                    uf.getH2Adresse() + ", " +
                    uf.getH2Cp() + ", " +
                    uf.getH2Ville();
        }

        ((TextView) rowView.findViewById(R.id.ruv_adresse)).setText(sville);

        return rowView;
    }
}
