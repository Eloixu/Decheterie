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

import java.util.ArrayList;

import fr.trackoe.decheterie.R;
import fr.trackoe.decheterie.configuration.Configuration;
import fr.trackoe.decheterie.database.DchApportFluxDB;
import fr.trackoe.decheterie.database.DchDecheterieFluxDB;
import fr.trackoe.decheterie.database.DchDepotDB;
import fr.trackoe.decheterie.database.DchFluxDB;
import fr.trackoe.decheterie.database.DecheterieDB;
import fr.trackoe.decheterie.database.IconDB;
import fr.trackoe.decheterie.model.bean.global.Decheterie;
import fr.trackoe.decheterie.model.bean.global.Flux;
import fr.trackoe.decheterie.model.bean.global.Icon;
import fr.trackoe.decheterie.ui.activity.ContainerActivity;
import fr.trackoe.decheterie.ui.dialog.CustomDialog;

public class DepotFragment extends Fragment {
    private ViewGroup depot_vg;
    private LinearLayout galleryFlux;
    private LinearLayout galleryFluxChoisi;
    ContainerActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        depot_vg = (ViewGroup) inflater.inflate(R.layout.depot_fragment, container, false);

        /*// Init Actionbar
        initActionBar();*/

        // Init Views
        initViews(inflater, container);

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
    Init Views
     */
    public void initViews(LayoutInflater inflater, ViewGroup container) {
        parentActivity = (ContainerActivity ) getActivity();
        parentActivity.showHamburgerButton();
        parentActivity.changeToolbarIcon();
        ((DrawerLocker) getActivity()).setDrawerEnabled(true);
        galleryFlux = (LinearLayout) depot_vg.findViewById(R.id.id_gallery_flux);
        galleryFluxChoisi = (LinearLayout) depot_vg.findViewById(R.id.id_gallery_flux_choisi);

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

            final String iconName = iconList.get(i).getNom();

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

                    //imgInDialog.setBackgroundResource(getResources().getIdentifier(iconName, "drawable", getContext().getPackageName()));

                    CustomDialog.Builder builder = new CustomDialog.Builder(getContext());
                    builder.setMessage("Vous avez sélectionné flux " + iconName);
                    builder.setTitle(iconName);
                    builder.setIconName(iconName);
                    builder.setPositiveButton("valider", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //设置你的操作事项
                        }
                    });

                    builder.setNegativeButton("Supprimer le flux",
                            new android.content.DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    galleryFluxChoisi.removeView(viewCopy);
                                    galleryFlux.addView(view);
                                }
                            });

                    builder.create().show();


                    imgCopy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //imgInDialog.setBackgroundResource(getResources().getIdentifier(iconName, "drawable", getContext().getPackageName()));
                            CustomDialog.Builder builder = new CustomDialog.Builder(getContext());
                            builder.setMessage("Vous avez sélectionné flux " + iconName);
                            builder.setTitle(iconName);
                            builder.setIconName(iconName);
                            builder.setPositiveButton("valider", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    //设置你的操作事项
                                }
                            });

                            builder.setNegativeButton("Supprimer le flux",
                                    new android.content.DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            galleryFluxChoisi.removeView(viewCopy);
                                            galleryFlux.addView(view);
                                        }
                                    });

                            builder.create().show();

                        }
                    });
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
    Init Listeners
     */
    public void initListeners(ViewGroup container) {
        final ImageView imgFinger = (ImageView) depot_vg.findViewById(R.id.imageView_finger);
        parentActivity = (ContainerActivity ) getActivity();
        imgFinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.openDrawer();
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
}