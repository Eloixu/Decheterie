package fr.trackoe.decheterie.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import fr.trackoe.decheterie.R;
import fr.trackoe.decheterie.database.ModulesDB;
import fr.trackoe.decheterie.model.bean.global.Module;
import fr.trackoe.decheterie.model.bean.global.Modules;
import fr.trackoe.decheterie.ui.activity.ContainerActivity;

/**
 * Created by Remi on 09/12/2015.
 */
public class ModulesFragment extends Fragment {

    private ViewGroup modules_vg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        modules_vg = (ViewGroup) inflater.inflate(R.layout.modules_fragment, container, false);

        if(getActivity() != null) {
            verifyPositionPermissions(getActivity());
        }

        // Init Actionbar
        initActionBar();

        // Init Views
        initViews();

        // Init des listeners
        initListeners();

        return modules_vg;
    }

    /*
	 * Init Actionbar
	 */
    public void initActionBar() {
        if(getActivity() != null && getActivity() instanceof ContainerActivity) {
            ((ContainerActivity) getActivity()).showActionBarModules();
        }
    }

    /*
    Init Views
     */
    public void initViews() {
        Modules modules;
        ModulesDB modulesDB = new ModulesDB(getActivity());
        modulesDB.read();
        modules = modulesDB.getModules();
        modulesDB.close();

        if(modules.getListModules().isEmpty()) {
            modules_vg.findViewById(R.id.modules_no_content_layout).setVisibility(View.VISIBLE);
        } else {
            for (Module m : modules.getListModules()) {
                if(Integer.parseInt(m.getIdModule()) == getResources().getInteger(R.integer.module_formulaires_perso)) {
                    modules_vg.findViewById(R.id.modules_form_perso_layout).setVisibility(View.VISIBLE);
                    displayTextView(((TextView) modules_vg.findViewById(R.id.modules_form_perso_title)), m.getNom());
                }
            }
        }
    }

    public void displayTextView(final TextView tv, String message){
        tv.setText(message);

        tv.post(new Runnable() {
            @Override
            public void run() {
                if (tv.getLineCount() == 2) {
                    tv.setTextSize(19);
                } else if (tv.getLineCount() > 2) {
                    tv.setTextSize(17);
                }
            }
        });
    }

    /*
    Init Listeners
     */
    public void initListeners() {
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View header;
                final View body;
                final ImageView imageBody;
                final int idModule;

                switch (v.getId()) {
                    case R.id.modules_form_perso_layout:
                        header = modules_vg.findViewById(R.id.modules_form_perso_header);
                        body = modules_vg.findViewById(R.id.modules_form_perso_body);
                        imageBody = ((ImageView) modules_vg.findViewById(R.id.modules_form_perso_img));
                        idModule = getResources().getInteger(R.integer.module_formulaires_perso);
                        break;

                    default:
                        header = new View(getContext());
                        body = new View(getContext());
                        imageBody = new ImageView(getContext());
                        idModule = 0;
                        break;
                }

                header.setBackgroundResource(R.drawable.header_module_pressed);
                body.setBackgroundResource(R.drawable.bottom_module_pressed);
                imageBody.setColorFilter(ContextCompat.getColor(getActivity(), R.color.blue));

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        header.setBackgroundResource(R.drawable.header_module);
                        body.setBackgroundResource(R.drawable.bottom_module);
                        imageBody.setColorFilter(ContextCompat.getColor(getActivity(), R.color.white));

                        /*if(idModule == getResources().getInteger(R.integer.module_formulaires_perso)) {
                                displayMenuFormFragment();
                        }*/
                    }
                }, 200);
            }
        };


        // Click sur Formulaires persos
        modules_vg.findViewById(R.id.modules_form_perso_layout).setOnClickListener(clickListener);
    }

    private static final int REQUEST_PERM = 1;

    private static String[] PERMISSIONS_LOCATION = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    public static void verifyPositionPermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_LOCATION, REQUEST_PERM);
        }
    }
}
