package fr.trackoe.decheterie.ui.fragment;

/**
 * Created by Trackoe on 14/03/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.trackoe.decheterie.R;
import fr.trackoe.decheterie.configuration.Configuration;
import fr.trackoe.decheterie.database.DecheterieDB;
import fr.trackoe.decheterie.model.bean.global.Decheterie;
import fr.trackoe.decheterie.ui.activity.ContainerActivity;

/**
 * Created by Trackoe on 13/03/2017.
 */

public class DecheterieFragment extends Fragment {
    private ViewGroup decheterie_vg;
    private ListView listView;
    private ArrayList<Decheterie> decheterieList;
    private DecheterieDB decheterieDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        decheterie_vg = (ViewGroup) inflater.inflate(R.layout.decheteries_fragment, container, false);
        listView = (ListView) decheterie_vg.findViewById(R.id.listView);
        // Init Actionbar
        //initActionBar();

        // Init Views
        initViews(inflater,container);

        // Init des listeners
        initListeners();

        return decheterie_vg;
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
    public void initViews(LayoutInflater inflater,ViewGroup container) {
        ((DrawerLocker) getActivity()).setDrawerEnabled(false);
        final View acceuilView = inflater.inflate(R.layout.accueil_fragment,container,false);
        decheterieDB = new DecheterieDB(getContext());
        decheterieDB.open();
        decheterieDB.clearDecheterie();
        String[] ds = {"Paris","Shanghai","Montpellier","Belfort","Paris France","Shanghai China","New York","New York City","San Francisco","Washington"};
        for(int i = 0; i < 10; i ++){
            Decheterie dt = new Decheterie();
            dt.setIdAccount(i + 1);
            dt.setName(ds[i]);
            dt.setConsigneComptage("");
            dt.setConsigneSignature("");
            dt.setApport(true);
            dt.setUniteTotal("");
            decheterieDB.insertDecheterie(dt);
        }

        decheterieList = decheterieDB.getAllDecheteries();
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return decheterieList.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                if(convertView==null){
                    view = View.inflate(getContext(),R.layout.item,null);
                }
                else{
                    view = convertView;
                }

                final Decheterie decheterie = decheterieList.get(position);
                final TextView name = (TextView)view.findViewById(R.id.dechetrie_name);
                name.setText(decheterie.getName());
                name.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Configuration.saveIdDecheterie(decheterie.getIdAccount());
                        fr.trackoe.decheterie.configuration.Configuration.saveNameDecheterie(decheterie.getName());
                        System.out.println(name.getText().toString());
                        String dechetrieName = name.getText().toString();
                        if(getActivity() != null && getActivity() instanceof  ContainerActivity) {
                            ((ContainerActivity) getActivity()).changeMainFragment(new AccueilFragment(), true);
                        }
                    }
                });

                return view;
            }
        });
        decheterieDB.close();

    }

    /*
    Init Listeners
     */
    public void initListeners() {
        System.out.println("DecheterieFragment initListeners()");
        EditText edittext_filtre = (EditText) decheterie_vg.findViewById(R.id.edittext_filtre);
        listView.setItemsCanFocus(false);
        TextWatcher listener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                decheterieDB = new DecheterieDB(getContext());
                decheterieDB.open();
                String name = s.toString();
                final ArrayList<Decheterie> decheteries = decheterieDB.getDecheteriesByName(name);

                listView.setVisibility(View.GONE);
                listView.setAdapter(new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return decheteries.size();
                    }

                    @Override
                    public Object getItem(int position) {
                        return null;
                    }

                    @Override
                    public long getItemId(int position) {
                        return 0;
                    }

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view;
                        if(convertView==null){
                            view = View.inflate(getContext(),R.layout.item,null);
                        }
                        else{
                            view = convertView;
                        }

                        final Decheterie decheterie = decheteries.get(position);
                        final TextView name = (TextView)view.findViewById(R.id.dechetrie_name);
                        name.setText(decheterie.getName());
                        name.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                System.out.println(name.getText().toString());
                                Configuration.saveIdDecheterie(decheterie.getIdAccount());
                                fr.trackoe.decheterie.configuration.Configuration.saveNameDecheterie(decheterie.getName());
                                System.out.println(name.getText().toString());
                                String dechetrieName = name.getText().toString();
                                if(getActivity() != null && getActivity() instanceof  ContainerActivity) {
                                    ((ContainerActivity) getActivity()).changeMainFragment(new AccueilFragment(), true);
                                }

                            }
                        });


                        return view;
                    }
                });
                decheterieDB.close();
                listView.setVisibility(View.VISIBLE);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        edittext_filtre.addTextChangedListener(listener);

        Log.d("tag","before setOnItemClickListener");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                System.out.println("hello");
                Log.d("tag","setOnItemClickListener");

            }
        });


    }
}
