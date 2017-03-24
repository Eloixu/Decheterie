package fr.trackoe.decheterie.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import fr.trackoe.decheterie.database.DecheterieDB;
import fr.trackoe.decheterie.database.IconDB;
import fr.trackoe.decheterie.model.bean.global.Decheterie;
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

        // Init Actionbar
        //initActionBar();

        // Init Views
        initViews(inflater, container);

        // Init des listeners
        initListeners();

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
        IconDB iconDB = new IconDB(getContext());
        iconDB.open();
        ArrayList<Icon> iconList = iconDB.getAllIcons();
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
        iconDB.close();
        parentActivity.openDrawerWithDelay();

    }

    /*
    Init Listeners
     */
    public void initListeners() {
        final ImageView imgFinger = (ImageView) depot_vg.findViewById(R.id.imageView_finger);
        parentActivity = (ContainerActivity ) getActivity();
        imgFinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.openDrawer();
            }
        });

    }
}