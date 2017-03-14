package fr.trackoe.decheterie.ui.fragment;

/**
 * Created by Trackoe on 14/03/2017.
 */

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.support.v4.app.Fragment;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.trackoe.decheterie.R;

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
    private DecheterieDB decheterieDB = new DecheterieDB(getContext());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        decheterie_vg = (ViewGroup) inflater.inflate(R.layout.decheteries_frament, container, false);
        // Init Actionbar
        initActionBar();

        // Init Views
        initViews();

        // Init des listeners
        initListeners();

        return decheterie_vg;
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
        listView = (ListView) decheterie_vg.findViewById(R.id.listView);
        decheterieDB.open();
        decheterieList = new ArrayList<>();
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

                Decheterie decheterie = decheterieList.get(position);
                TextView name = (TextView)view.findViewById(R.id.dechetrie_name);
                name.setText(decheterie.getName());

                return view;
            }
        });
    }

    /*
    Init Listeners
     */
    public void initListeners() {

    }
}
