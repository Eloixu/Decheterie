package fr.trackoe.decheterie.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import fr.trackoe.decheterie.R;
import fr.trackoe.decheterie.configuration.Configuration;
import fr.trackoe.decheterie.database.DchAccountSettingDB;
import fr.trackoe.decheterie.database.DchApportFluxDB;
import fr.trackoe.decheterie.database.DchCarteDB;
import fr.trackoe.decheterie.database.DchDecheterieFluxDB;
import fr.trackoe.decheterie.database.DchDepotDB;
import fr.trackoe.decheterie.database.DchFluxDB;
import fr.trackoe.decheterie.database.DchTypeCarteDB;
import fr.trackoe.decheterie.database.DecheterieDB;
import fr.trackoe.decheterie.database.IconDB;
import fr.trackoe.decheterie.model.bean.global.AccountSetting;
import fr.trackoe.decheterie.model.bean.global.ApportFlux;
import fr.trackoe.decheterie.model.bean.global.Carte;
import fr.trackoe.decheterie.model.bean.global.Decheterie;
import fr.trackoe.decheterie.model.bean.global.Depot;
import fr.trackoe.decheterie.model.bean.global.Flux;
import fr.trackoe.decheterie.model.bean.global.Icon;
import fr.trackoe.decheterie.ui.activity.ContainerActivity;
import fr.trackoe.decheterie.ui.dialog.CustomDialog;

public class DepotFragment extends Fragment {
    private ViewGroup depot_vg;
    private LinearLayout galleryFlux;
    private LinearLayout galleryFluxChoisi;
    private Depot depot;
    private long depotId;
    private Carte carte;
    private boolean pageSignature = false;
    private AccountSetting accountSetting;
    ContainerActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        depot_vg = (ViewGroup) inflater.inflate(R.layout.depot_fragment, container, false);
        parentActivity = (ContainerActivity ) getActivity();

        DecheterieDB decheterieDB = new DecheterieDB(getContext());
        decheterieDB.open();
        DchDepotDB dchDepotDB = new DchDepotDB(getContext());
        dchDepotDB.open();
        DchCarteDB dchCarteDB = new DchCarteDB(getContext());
        dchCarteDB.open();

        //get the numCarte sent From IdentifigationFragment
        getNumCarteFromIdentificationFragment();
        //get the depotId sent From ApportProFragment
        getDepotIdFromApportProFragment();

        //detect if "oui" is clicked
        //the case that we continue to edit the depot incompleted
        if(Configuration.getIsOuiClicked()){
            //get the depot on "statut en_cours"
            System.out.println("oui is clicked");
            depot = dchDepotDB.getDepotByStatut(getResources().getInteger(R.integer.statut_en_cours));
            depotId = depot.getId();
            carte = dchCarteDB.getCarteFromID(depot.getCarteActiveCarteId());
            setPageSignatureFromCarte();

            //detect if the current depot existe in the BDD
            if(dchDepotDB.getDepotByIdentifiant(depotId) == null) {
                //add depot into BDD
                dchDepotDB.insertDepot(depot);
            }
            else{
                //update depot in BDD
            }

            //initViews
            initViewsNotNormal(inflater,container);

        }
        //the most normal case
        else if(!Configuration.getIsOuiClicked() && depotId == 0){
            //create a new depot
            System.out.println("oui isn't clicked");
            depotId = parentActivity.generateCodeFromDateAndNumTablette();
            Date d = new Date();
            SimpleDateFormat df = new SimpleDateFormat(getString(R.string.db_date_format));
            String dateTime = df.format(d);
            int decheterieId = decheterieDB.getDecheterieByName(Configuration.getNameDecheterie()).getId();
            long carteActiveCarteId = carte.getId();
            int comptePrepayeId = 0;
            float qtyTotalUDD = 0;
            String depotNom = "";
            int statut = getResources().getInteger(R.integer.statut_en_cours);
            Boolean isSent = false;

            depot = new Depot();
            depot.setId(depotId);
            depot.setDateHeure(dateTime);
            depot.setDecheterieId(decheterieId);
            depot.setCarteActiveCarteId(carteActiveCarteId);
            depot.setComptePrepayeId(comptePrepayeId);
            depot.setQtyTotalUDD(qtyTotalUDD);
            depot.setNom(depotNom);
            depot.setStatut(statut);
            depot.setSent(isSent);

            //detect if the current depot existe in the BDD
            if(dchDepotDB.getDepotByIdentifiant(depotId) == null) {
                //add depot into BDD
                dchDepotDB.insertDepot(depot);
            }
            else{
                //update depot in BDD
            }

            //initViews
            initViews(inflater, container);
        }
        //the case when click "back" dans ApportProFragment
        else if(!Configuration.getIsOuiClicked() && depotId != 0){
            depot = dchDepotDB.getDepotByIdentifiant(depotId);
            carte = dchCarteDB.getCarteFromID(depot.getCarteActiveCarteId());
            setPageSignatureFromCarte();
            //initViews
            initViewsNotNormal(inflater,container);
        }


        //show depot information
        showDepotDetails();




        decheterieDB.close();
        dchDepotDB.close();
        dchCarteDB.close();



        /*// Init Actionbar
        initActionBar();*/

        // Init Views
        //initViews(inflater, container);

        // Init des listeners
        initListeners(container);



        return depot_vg;
    }

    /*
    * Init Actionbar
    */
    /*public void initActionBar() {
        if (getActivity() != null && getActivity() instanceof ContainerActivity) {
            ((ContainerActivity) getActivity()).showActionBarLogin();
        }
    }*/

    /*
    Init Views under the condition that "oui" isn't clicked
     */
    public void initViews(LayoutInflater inflater, ViewGroup container) {
        parentActivity = (ContainerActivity ) getActivity();
        parentActivity.showHamburgerButton();
        parentActivity.changeToolbarIcon();
        ((DrawerLocker) getActivity()).setDrawerEnabled(true);
        galleryFlux = (LinearLayout) depot_vg.findViewById(R.id.id_gallery_flux);
        galleryFluxChoisi = (LinearLayout) depot_vg.findViewById(R.id.id_gallery_flux_choisi);

        final long depotId = this.depotId;

        //open the DBB
        IconDB iconDB = new IconDB(getContext());
        iconDB.open();
        DecheterieDB decheterieDB = new DecheterieDB(getContext());
        decheterieDB.open();
        DchFluxDB dchFluxDB = new DchFluxDB(getContext());
        dchFluxDB.open();
        DchDecheterieFluxDB dchDecheterieFluxDB = new DchDecheterieFluxDB(getContext());
        dchDecheterieFluxDB.open();
        DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
        dchApportFluxDB.open();
        DchDepotDB dchDepotDB = new DchDepotDB(getContext());
        dchDepotDB.open();

        //the list of icons which associate with the actual decheterie
        ArrayList<Icon> iconList = new ArrayList();

        if(Configuration.getNameDecheterie().isEmpty()||Configuration.getNameDecheterie()==null){

        }
        else{
            Decheterie decheterie = decheterieDB.getDecheterieByName(Configuration.getNameDecheterie());
            ArrayList<Flux> fluxList = dchDecheterieFluxDB.getAllFluxByDecheterieId(decheterie.getId(), getContext());
            for(Flux flux: fluxList){
                Icon icon = iconDB.getIconByIdentifiant(flux.getIcon_id());
                iconList.add(icon);
            }
        }

        //add the icons associated to the DepotFragment
        for (int i = 0; i < iconList.size(); i++)
        {
            final View view = inflater.inflate(R.layout.depot_fragment_flux_item, container, false);
            final View viewCopy = inflater.inflate(R.layout.depot_fragment_flux_item, container, false);
            final ImageView img = (ImageView) view.findViewById(R.id.imageView_flux_item);
            final ImageView imgCopy = (ImageView) viewCopy.findViewById(R.id.imageView_flux_item);

            final Icon currentIcon = iconList.get(i);
            final String iconName = currentIcon.getNom();

            img.setBackgroundResource(getResources().getIdentifier(iconName, "drawable", getContext().getPackageName()));
            imgCopy.setBackgroundResource(getResources().getIdentifier(iconName, "drawable", getContext().getPackageName()));
            TextView txt = (TextView) view.findViewById(R.id.textView_flux_item);
            TextView txtCopy = (TextView) viewCopy.findViewById(R.id.textView_flux_item);
            txt.setText(iconList.get(i).getNom());
            txtCopy.setText(iconList.get(i).getNom());
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    galleryFlux.removeView(view);
                    galleryFluxChoisi.addView(viewCopy);

                    DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                    dchApportFluxDB.open();
                    DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                    dchFluxDB.open();

                    //imgInDialog.setBackgroundResource(getResources().getIdentifier(iconName, "drawable", getContext().getPackageName()));

                    final CustomDialog.Builder builder = new CustomDialog.Builder(getContext());
                    builder.setMessage("Vous avez sélectionné flux " + iconName);
                    builder.setTitle(iconName);
                    builder.setIconName(iconName);
                    //show the qty from BDD
                    if(dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()) == null){

                    }
                    else{
                        builder.setEditTextQtyFlux(""+dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()).getQtyApporte());
                    }
                    builder.setPositiveButton("valider", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //设置你的操作事项
                            DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                            dchFluxDB.open();
                            DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                            dchApportFluxDB.open();


                            //save the qty into BDD
                            int fluxId = dchFluxDB.getFluxByIconId(currentIcon.getId()).getId();
                            EditText editTextQuantiteFlux = builder.getEditTextQuantiteFlux();
                            float qtyApporte;
                            if(editTextQuantiteFlux.getText().toString().isEmpty()||editTextQuantiteFlux.getText().toString() ==""){
                                qtyApporte = 0;
                            }
                            else {
                                qtyApporte = Float.parseFloat(editTextQuantiteFlux.getText().toString());
                            }

                            ApportFlux apportFlux = new ApportFlux(depotId,fluxId,qtyApporte,false);
                            dchApportFluxDB.insertApportFlux(apportFlux);
                            dchFluxDB.close();
                            dchApportFluxDB.close();

                        }
                    });

                    builder.setNegativeButton("",
                            new android.content.DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                                    dchFluxDB.open();
                                    DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                                    dchApportFluxDB.open();

                                    dialog.dismiss();
                                    galleryFluxChoisi.removeView(viewCopy);
                                    galleryFlux.addView(view);
                                    dchApportFluxDB.deleteApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId());

                                    dchFluxDB.close();
                                    dchApportFluxDB.close();
                                }
                            });

                    builder.create().show();


                    imgCopy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                            dchApportFluxDB.open();
                            DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                            dchFluxDB.open();

                            //imgInDialog.setBackgroundResource(getResources().getIdentifier(iconName, "drawable", getContext().getPackageName()));
                            final CustomDialog.Builder builder = new CustomDialog.Builder(getContext());
                            builder.setMessage("Vous avez sélectionné flux " + iconName);
                            builder.setTitle(iconName);
                            builder.setIconName(iconName);
                            //show the qty from BDD
                            if(dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()) == null){

                            }
                            else{
                                builder.setEditTextQtyFlux(""+dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()).getQtyApporte());
                            }
                            builder.setPositiveButton("valider", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    //设置你的操作事项
                                    DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                                    dchApportFluxDB.open();
                                    DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                                    dchFluxDB.open();

                                    int fluxId = dchFluxDB.getFluxByIconId(currentIcon.getId()).getId();
                                    EditText editTextQuantiteFlux = builder.getEditTextQuantiteFlux();
                                    float qtyApporte;

                                    if(editTextQuantiteFlux.getText().toString().isEmpty()||editTextQuantiteFlux.getText().toString() ==""){
                                        qtyApporte = 0;
                                    }
                                    else {
                                        qtyApporte = Float.parseFloat(editTextQuantiteFlux.getText().toString());
                                    }

                                    if(dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()) == null){
                                        //save the qty into BDD
                                        ApportFlux apportFlux = new ApportFlux(depotId,fluxId,qtyApporte,false);
                                        dchApportFluxDB.insertApportFlux(apportFlux);
                                    }
                                    else{
                                        //update the data in BDD
                                        dchApportFluxDB.updateQtyApporte(depotId, fluxId, qtyApporte);
                                    }

                                    dchApportFluxDB.close();
                                    dchFluxDB.close();


                                }
                            });

                            builder.setNegativeButton("",
                                    new android.content.DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                                            dchFluxDB.open();
                                            DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                                            dchApportFluxDB.open();

                                            dialog.dismiss();
                                            galleryFluxChoisi.removeView(viewCopy);
                                            galleryFlux.addView(view);

                                            dchApportFluxDB.deleteApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId());

                                            dchFluxDB.close();
                                            dchApportFluxDB.close();
                                        }
                                    });

                            builder.create().show();

                            dchApportFluxDB.close();
                            dchFluxDB.close();

                        }
                    });

                    dchApportFluxDB.close();
                    dchFluxDB.close();
                }
            });
            galleryFlux.addView(view);
        }

        //close the DBB
        iconDB.close();
        decheterieDB.close();
        dchFluxDB.close();
        dchDecheterieFluxDB.close();
        dchApportFluxDB.close();
        dchDepotDB.close();

        parentActivity.openDrawerWithDelay();

    }

    /*
    Init Views only under the condition that "oui" is clicked
     */
    public void initViewsNotNormal(LayoutInflater inflater, ViewGroup container) {
        parentActivity = (ContainerActivity) getActivity();
        parentActivity.showHamburgerButton();
        parentActivity.changeToolbarIcon();
        ((DrawerLocker) getActivity()).setDrawerEnabled(true);
        galleryFlux = (LinearLayout) depot_vg.findViewById(R.id.id_gallery_flux);
        galleryFluxChoisi = (LinearLayout) depot_vg.findViewById(R.id.id_gallery_flux_choisi);


        final long depotId = this.depotId;

        //open the DBB
        IconDB iconDB = new IconDB(getContext());
        iconDB.open();
        DecheterieDB decheterieDB = new DecheterieDB(getContext());
        decheterieDB.open();
        DchFluxDB dchFluxDB = new DchFluxDB(getContext());
        dchFluxDB.open();
        DchDecheterieFluxDB dchDecheterieFluxDB = new DchDecheterieFluxDB(getContext());
        dchDecheterieFluxDB.open();
        DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
        dchApportFluxDB.open();
        DchDepotDB dchDepotDB = new DchDepotDB(getContext());
        dchDepotDB.open();

        //the list of icons which associate with the actual decheterie
        ArrayList<Icon> iconListTotal = new ArrayList();
        ArrayList<Icon> iconListOfGalleryFlux = new ArrayList();
        ArrayList<Icon> iconListOfGalleryFluxChoisi = new ArrayList();

        if(Configuration.getNameDecheterie().isEmpty()||Configuration.getNameDecheterie()==null){

        }
        else{
            Decheterie decheterie = decheterieDB.getDecheterieByName(Configuration.getNameDecheterie());
            ArrayList<Flux> fluxList = dchDecheterieFluxDB.getAllFluxByDecheterieId(decheterie.getId(), getContext());
            for(Flux flux: fluxList){
                Icon icon = iconDB.getIconByIdentifiant(flux.getIcon_id());
                iconListTotal.add(icon);
            }
        }


        //get the flux which associate to the depot in the bottom scroll
        ArrayList<ApportFlux> apportFluxList = dchApportFluxDB.getListeApportFluxByDepotId(depotId);
        ArrayList<Flux> fluxList = new ArrayList<>();
        for(ApportFlux af: apportFluxList){
            Flux f = dchFluxDB.getFluxByIdentifiant(af.getFluxId());
            fluxList.add(f);
        }
        for(Flux flux: fluxList){
            Icon icon = iconDB.getIconByIdentifiant(flux.getIcon_id());
            iconListOfGalleryFluxChoisi.add(icon);
        }

        //get the rest flux which are in the top scroll
        /*for(Icon iconTotal: iconListTotal){
            for(Icon iconChoisi: iconListOfGalleryFluxChoisi) {
                if (iconTotal.getId() != iconChoisi.getId()) {
                    iconListOfGalleryFlux.add(iconTotal);
                }
            }
        }*/
        int[] arrIconTotalIdList = new int[iconListTotal.size()];
        int[] arrIconChoisiIdList = new int[iconListOfGalleryFluxChoisi.size()];
        for(int i = 0; i < iconListTotal.size(); i ++){
            arrIconTotalIdList[i] = iconListTotal.get(i).getId();
        }
        for(int i = 0; i < iconListOfGalleryFluxChoisi.size(); i ++){
            arrIconChoisiIdList[i] = iconListOfGalleryFluxChoisi.get(i).getId();
        }
        HashSet<Integer> map = new HashSet<Integer>();
        for (int i : arrIconChoisiIdList)
            map.add(i);
        for (int i : arrIconTotalIdList) {
            if (!map.contains(i)){
                // found not duplicate!
                iconListOfGalleryFlux.add(iconDB.getIconByIdentifiant(i));
            }

        }



        //add the flux in "iconListOfGalleryFlux" in "galleryFlux"
        for (int i = 0; i < iconListOfGalleryFlux.size(); i++)
        {
            final View view = inflater.inflate(R.layout.depot_fragment_flux_item, container, false);
            final View viewCopy = inflater.inflate(R.layout.depot_fragment_flux_item, container, false);
            final ImageView img = (ImageView) view.findViewById(R.id.imageView_flux_item);
            final ImageView imgCopy = (ImageView) viewCopy.findViewById(R.id.imageView_flux_item);

            final Icon currentIcon = iconListOfGalleryFlux.get(i);
            final String iconName = currentIcon.getNom();

            img.setBackgroundResource(getResources().getIdentifier(iconName, "drawable", getContext().getPackageName()));
            imgCopy.setBackgroundResource(getResources().getIdentifier(iconName, "drawable", getContext().getPackageName()));
            TextView txt = (TextView) view.findViewById(R.id.textView_flux_item);
            TextView txtCopy = (TextView) viewCopy.findViewById(R.id.textView_flux_item);
            txt.setText(iconListOfGalleryFlux.get(i).getNom());
            txtCopy.setText(iconListOfGalleryFlux.get(i).getNom());
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    galleryFlux.removeView(view);
                    galleryFluxChoisi.addView(viewCopy);

                    DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                    dchApportFluxDB.open();
                    DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                    dchFluxDB.open();

                    //imgInDialog.setBackgroundResource(getResources().getIdentifier(iconName, "drawable", getContext().getPackageName()));

                    final CustomDialog.Builder builder = new CustomDialog.Builder(getContext());
                    builder.setMessage("Vous avez sélectionné flux " + iconName);
                    builder.setTitle(iconName);
                    builder.setIconName(iconName);
                    //show the qty from BDD
                    if(dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()) == null){

                    }
                    else{
                        builder.setEditTextQtyFlux(""+dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()).getQtyApporte());
                    }
                    builder.setPositiveButton("valider", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //设置你的操作事项
                            DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                            dchFluxDB.open();
                            DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                            dchApportFluxDB.open();


                            //save the qty into BDD
                            int fluxId = dchFluxDB.getFluxByIconId(currentIcon.getId()).getId();
                            EditText editTextQuantiteFlux = builder.getEditTextQuantiteFlux();
                            float qtyApporte;
                            if(editTextQuantiteFlux.getText().toString().isEmpty()||editTextQuantiteFlux.getText().toString() ==""){
                                qtyApporte = 0;
                            }
                            else {
                                qtyApporte = Float.parseFloat(editTextQuantiteFlux.getText().toString());
                            }

                            ApportFlux apportFlux = new ApportFlux(depotId,fluxId,qtyApporte,false);
                            dchApportFluxDB.insertApportFlux(apportFlux);
                            dchFluxDB.close();
                            dchApportFluxDB.close();

                        }
                    });

                    builder.setNegativeButton("",
                            new android.content.DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                                    dchFluxDB.open();
                                    DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                                    dchApportFluxDB.open();

                                    dialog.dismiss();
                                    galleryFluxChoisi.removeView(viewCopy);
                                    galleryFlux.addView(view);
                                    dchApportFluxDB.deleteApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId());

                                    dchFluxDB.close();
                                    dchApportFluxDB.close();
                                }
                            });

                    builder.create().show();


                    imgCopy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                            dchApportFluxDB.open();
                            DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                            dchFluxDB.open();

                            //imgInDialog.setBackgroundResource(getResources().getIdentifier(iconName, "drawable", getContext().getPackageName()));
                            final CustomDialog.Builder builder = new CustomDialog.Builder(getContext());
                            builder.setMessage("Vous avez sélectionné flux " + iconName);
                            builder.setTitle(iconName);
                            builder.setIconName(iconName);
                            //show the qty from BDD
                            if(dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()) == null){

                            }
                            else{
                                builder.setEditTextQtyFlux(""+dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()).getQtyApporte());
                            }
                            builder.setPositiveButton("valider", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    //设置你的操作事项
                                    DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                                    dchApportFluxDB.open();
                                    DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                                    dchFluxDB.open();

                                    int fluxId = dchFluxDB.getFluxByIconId(currentIcon.getId()).getId();
                                    EditText editTextQuantiteFlux = builder.getEditTextQuantiteFlux();
                                    float qtyApporte;

                                    if(editTextQuantiteFlux.getText().toString().isEmpty()||editTextQuantiteFlux.getText().toString() ==""){
                                        qtyApporte = 0;
                                    }
                                    else {
                                        qtyApporte = Float.parseFloat(editTextQuantiteFlux.getText().toString());
                                    }

                                    if(dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()) == null){
                                        //save the qty into BDD
                                        ApportFlux apportFlux = new ApportFlux(depotId,fluxId,qtyApporte,false);
                                        dchApportFluxDB.insertApportFlux(apportFlux);
                                    }
                                    else{
                                        //update the data in BDD
                                        dchApportFluxDB.updateQtyApporte(depotId, fluxId, qtyApporte);
                                    }

                                    dchApportFluxDB.close();
                                    dchFluxDB.close();


                                }
                            });

                            builder.setNegativeButton("",
                                    new android.content.DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                                            dchFluxDB.open();
                                            DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                                            dchApportFluxDB.open();

                                            dialog.dismiss();
                                            galleryFluxChoisi.removeView(viewCopy);
                                            galleryFlux.addView(view);

                                            dchApportFluxDB.deleteApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId());

                                            dchFluxDB.close();
                                            dchApportFluxDB.close();
                                        }
                                    });

                            builder.create().show();

                            dchApportFluxDB.close();
                            dchFluxDB.close();

                        }
                    });

                    dchApportFluxDB.close();
                    dchFluxDB.close();
                }
            });
            galleryFlux.addView(view);
        }

        //add the flux in "iconListOfGalleryFluxChoisi" to the "galleryFluxChoisi"
        for (int i = 0; i < iconListOfGalleryFluxChoisi.size(); i++){
            final View view = inflater.inflate(R.layout.depot_fragment_flux_item, container, false);
            final View viewCopy = inflater.inflate(R.layout.depot_fragment_flux_item, container, false);
            final ImageView img = (ImageView) view.findViewById(R.id.imageView_flux_item);
            final ImageView imgCopy = (ImageView) viewCopy.findViewById(R.id.imageView_flux_item);

            final Icon currentIcon = iconListOfGalleryFluxChoisi.get(i);
            final String iconName = currentIcon.getNom();

            img.setBackgroundResource(getResources().getIdentifier(iconName, "drawable", getContext().getPackageName()));
            imgCopy.setBackgroundResource(getResources().getIdentifier(iconName, "drawable", getContext().getPackageName()));
            TextView txt = (TextView) view.findViewById(R.id.textView_flux_item);
            TextView txtCopy = (TextView) viewCopy.findViewById(R.id.textView_flux_item);
            txt.setText(iconListOfGalleryFluxChoisi.get(i).getNom());
            txtCopy.setText(iconListOfGalleryFluxChoisi.get(i).getNom());

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                    dchApportFluxDB.open();
                    DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                    dchFluxDB.open();

                    final CustomDialog.Builder builder = new CustomDialog.Builder(getContext());
                    builder.setMessage("Vous avez sélectionné flux " + iconName);
                    builder.setTitle(iconName);
                    builder.setIconName(iconName);
                    builder.setEditTextQtyFlux(""+dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()).getQtyApporte());
                    builder.setPositiveButton("valider", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //设置你的操作事项
                            DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                            dchApportFluxDB.open();
                            DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                            dchFluxDB.open();

                            int fluxId = dchFluxDB.getFluxByIconId(currentIcon.getId()).getId();
                            EditText editTextQuantiteFlux = builder.getEditTextQuantiteFlux();
                            float qtyApporte;

                            if(editTextQuantiteFlux.getText().toString().isEmpty()||editTextQuantiteFlux.getText().toString() ==""){
                                qtyApporte = 0;
                            }
                            else {
                                qtyApporte = Float.parseFloat(editTextQuantiteFlux.getText().toString());
                            }

                            if(dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()) == null){
                                //save the qty into BDD
                                ApportFlux apportFlux = new ApportFlux(depotId,fluxId,qtyApporte,false);
                                dchApportFluxDB.insertApportFlux(apportFlux);
                            }
                            else{
                                //update the data in BDD
                                dchApportFluxDB.updateQtyApporte(depotId, fluxId, qtyApporte);
                            }

                            dchApportFluxDB.close();
                            dchFluxDB.close();

                        }
                    });

                    builder.setNegativeButton("", new android.content.DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                            dchFluxDB.open();
                            DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                            dchApportFluxDB.open();

                            dialog.dismiss();
                            galleryFluxChoisi.removeView(view);
                            galleryFlux.addView(viewCopy);

                            dchApportFluxDB.deleteApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId());

                            dchFluxDB.close();
                            dchApportFluxDB.close();
                        }
                    });

                    builder.create().show();




                    imgCopy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                            dchApportFluxDB.open();
                            DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                            dchFluxDB.open();

                            galleryFlux.removeView(viewCopy);
                            galleryFluxChoisi.addView(view);

                            //imgInDialog.setBackgroundResource(getResources().getIdentifier(iconName, "drawable", getContext().getPackageName()));
                            final CustomDialog.Builder builder = new CustomDialog.Builder(getContext());
                            builder.setMessage("Vous avez sélectionné flux " + iconName);
                            builder.setTitle(iconName);
                            builder.setIconName(iconName);/*
                            //show the qty from BDD
                            if(dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()) == null){

                            }
                            else{
                                builder.setEditTextQtyFlux(""+dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()).getQtyApporte());
                            }*/
                            builder.setPositiveButton("valider", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    //设置你的操作事项
                                    DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                                    dchApportFluxDB.open();
                                    DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                                    dchFluxDB.open();

                                    int fluxId = dchFluxDB.getFluxByIconId(currentIcon.getId()).getId();
                                    EditText editTextQuantiteFlux = builder.getEditTextQuantiteFlux();
                                    float qtyApporte;

                                    if(editTextQuantiteFlux.getText().toString().isEmpty()||editTextQuantiteFlux.getText().toString() ==""){
                                        qtyApporte = 0;
                                    }
                                    else {
                                        qtyApporte = Float.parseFloat(editTextQuantiteFlux.getText().toString());
                                    }

                                    if(dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()) == null){
                                        //save the qty into BDD
                                        ApportFlux apportFlux = new ApportFlux(depotId,fluxId,qtyApporte,false);
                                        dchApportFluxDB.insertApportFlux(apportFlux);
                                    }
                                    else{
                                        //update the data in BDD
                                        dchApportFluxDB.updateQtyApporte(depotId, fluxId, qtyApporte);
                                    }

                                    dchApportFluxDB.close();
                                    dchFluxDB.close();


                                }
                            });

                            builder.setNegativeButton("",
                                    new android.content.DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                                            dchFluxDB.open();
                                            DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                                            dchApportFluxDB.open();

                                            dialog.dismiss();
                                            galleryFluxChoisi.removeView(view);
                                            galleryFlux.addView(viewCopy);

                                            dchApportFluxDB.deleteApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId());

                                            dchFluxDB.close();
                                            dchApportFluxDB.close();
                                        }
                                    });

                            builder.create().show();


                            dchApportFluxDB.close();
                            dchFluxDB.close();

                        }
                    });


                    dchApportFluxDB.close();
                    dchFluxDB.close();


                }

            });


            galleryFluxChoisi.addView(view);
        }




        //close the DBB
        iconDB.close();
        decheterieDB.close();
        dchFluxDB.close();
        dchDecheterieFluxDB.close();
        dchApportFluxDB.close();
        dchDepotDB.close();

        parentActivity.openDrawerWithDelay();

    }



    /*
    Init Listeners
     */
    public void initListeners(ViewGroup container) {
        final ImageView imgFinger = (ImageView) depot_vg.findViewById(R.id.imageView_finger);
        Button btnValider = (Button) depot_vg.findViewById(R.id.btn_valider);
        parentActivity = (ContainerActivity ) getActivity();
        imgFinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.openDrawer();
            }
        });
        //set the listener of the button "valider"
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DchDepotDB dchDepotDB = new DchDepotDB(getContext());
                dchDepotDB.open();
                //if the drawer is open then close it
                if(parentActivity.isDrawerOpen()){
                    parentActivity.closeDrawer();
                }

                //update the table "depot" and change the row "statut" to statut_termine
                dchDepotDB.changeDepotStatutByIdentifiant(depotId, getResources().getInteger(R.integer.statut_termine));

                Configuration.setIsOuiClicked(false);

                //detect the pageSignature
                if(pageSignature) {
                    if (getActivity() != null && getActivity() instanceof ContainerActivity) {
                        ApportProFragment apportProFragment = ApportProFragment.newInstance(depotId);
                        ((ContainerActivity) getActivity()).changeMainFragment(apportProFragment, true);
                    }
                }
                else{
                    if (getActivity() != null && getActivity() instanceof ContainerActivity) {
                        ((ContainerActivity) getActivity()).changeMainFragment(new AccueilFragment(), true);
                    }
                }

                dchDepotDB.close();
            }
        });

        //set the swipe listener
        final GestureDetector gesture = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                           float velocityY) {
                        System.out.println("onFling() has been called!");
                        final int SWIPE_MIN_DISTANCE = 120;
                        final int SWIPE_MAX_OFF_PATH = 250;
                        final int SWIPE_THRESHOLD_VELOCITY = 200;
                        try {
                            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                                return false;
                            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                System.out.println("Right to Left + drawerOpen: " + parentActivity.isDrawerOpen());
                                if(parentActivity.isDrawerOpen()) {
                                    parentActivity.closeDrawer();
                                }
                            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                System.out.println("Left to Right");
                            }
                        } catch (Exception e) {
                            // nothing
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                });

        container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });

    }

    public static DepotFragment newInstance(String numCarte) {
        DepotFragment depotFragment = new DepotFragment();
        Bundle args = new Bundle();
        args.putString("numCarte", numCarte);
        depotFragment.setArguments(args);
        return depotFragment;
    }

    public static DepotFragment newInstance(long depotId) {
        DepotFragment depotFragment = new DepotFragment();
        Bundle args = new Bundle();
        args.putLong("depotId", depotId);
        depotFragment.setArguments(args);
        return depotFragment;
    }

    public void getDepotIdFromApportProFragment(){
        if (getArguments() != null) {
            depotId = getArguments().getLong("depotId");
            Toast.makeText(getContext(), "depotId From ApportProFragment: " + depotId,
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void getNumCarteFromIdentificationFragment(){
        if (getArguments() != null) {
            DchCarteDB dchCarteDB = new DchCarteDB(getContext());
            dchCarteDB.open();

            String numCarte = getArguments().getString("numCarte");
            if(numCarte != null) {
                Toast.makeText(getContext(), "numCarte: " + numCarte,
                        Toast.LENGTH_SHORT).show();
                carte = dchCarteDB.getCarteByNumCarteAndAccountId(numCarte, 0);
                setPageSignatureFromCarte();
            }

            dchCarteDB.close();
        }
    }

    public void setPageSignatureFromCarte(){
        DchAccountSettingDB dchAccountSettingDB = new DchAccountSettingDB(getContext());
        dchAccountSettingDB.open();

        if(carte != null){
            int typeCarteId = carte.getDchTypeCarteId();
            int accountId = carte.getDchAccountId();
            Date d = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
            int date = Integer.parseInt(df.format(d));
            ArrayList<AccountSetting> accountSettingList = dchAccountSettingDB.getListeAccountSettingByAccountIdAndTypeCarteId(accountId, typeCarteId);
            if (accountSettingList != null) {
                for (AccountSetting as : accountSettingList) {
                    int dateDebut = Integer.parseInt(as.getDateDebutParam());
                    int dateFin = Integer.parseInt(as.getDateFinParam());
                    if (date >= dateDebut && date <= dateFin) {
                        accountSetting = as;
                        pageSignature = accountSetting.isPageSignature();
                    }

                }
            }
        }

        dchAccountSettingDB.close();
    }

    public void showDepotDetails(){
        System.out.println("id: " + depot.getId());
        System.out.println("dateHeure: " + depot.getDateHeure());
        System.out.println("signature: " + depot.getSignature());
        System.out.println("decheterieId: " + depot.getDecheterieId());
        System.out.println("compte_prepaye_id: " + depot.getComptePrepayeId());
        System.out.println("qty_total_UDD: " + depot.getQtyTotalUDD());
        System.out.println("nom: " + depot.getNom());
        System.out.println("statut: " + depot.getStatut());
        System.out.println("is_sent: " + depot.isSent());

    }


}