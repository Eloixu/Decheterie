package fr.trackoe.decheterie.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import fr.trackoe.decheterie.R;
import fr.trackoe.decheterie.configuration.Configuration;
import fr.trackoe.decheterie.database.DchDepotDB;
import fr.trackoe.decheterie.database.DecheterieDB;
import fr.trackoe.decheterie.model.bean.global.Depot;
import fr.trackoe.decheterie.ui.activity.ContainerActivity;

public class ApportProFragment extends Fragment {
    private ViewGroup accueil_vg;
    ContainerActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("ApportProFragment-->onCreateView()");
        accueil_vg = (ViewGroup) inflater.inflate(R.layout.apport_pro_fragment, container, false);
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
        System.out.println("ApportProFragment-->onResume()");
        super.onResume();

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

    }
}