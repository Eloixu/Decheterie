package fr.trackoe.decheterie.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import fr.trackoe.decheterie.R;
import fr.trackoe.decheterie.Utils;
import fr.trackoe.decheterie.configuration.Configuration;
import fr.trackoe.decheterie.database.DchAccountFluxSettingDB;
import fr.trackoe.decheterie.database.DchAccountSettingDB;
import fr.trackoe.decheterie.database.DchApportFluxDB;
import fr.trackoe.decheterie.database.DchCarteActiveDB;
import fr.trackoe.decheterie.database.DchCarteDB;
import fr.trackoe.decheterie.database.DchCarteEtatRaisonDB;
import fr.trackoe.decheterie.database.DchChoixDecompteTotalDB;
import fr.trackoe.decheterie.database.DchComptePrepayeDB;
import fr.trackoe.decheterie.database.DchDecheterieFluxDB;
import fr.trackoe.decheterie.database.DchDepotDB;
import fr.trackoe.decheterie.database.DchFluxDB;
import fr.trackoe.decheterie.database.DchTypeCarteDB;
import fr.trackoe.decheterie.database.DchUniteDB;
import fr.trackoe.decheterie.database.DecheterieDB;
import fr.trackoe.decheterie.database.HabitatDB;
import fr.trackoe.decheterie.database.IconDB;
import fr.trackoe.decheterie.database.LocalDB;
import fr.trackoe.decheterie.database.MenageDB;
import fr.trackoe.decheterie.database.ModulesDB;
import fr.trackoe.decheterie.database.TypeHabitatDB;
import fr.trackoe.decheterie.database.UsagerDB;
import fr.trackoe.decheterie.database.UsagerHabitatDB;
import fr.trackoe.decheterie.database.UsagerMenageDB;
import fr.trackoe.decheterie.model.Datas;
import fr.trackoe.decheterie.model.bean.global.AccountFluxSetting;
import fr.trackoe.decheterie.model.bean.global.AccountSetting;
import fr.trackoe.decheterie.model.bean.global.ApportFlux;
import fr.trackoe.decheterie.model.bean.global.Carte;
import fr.trackoe.decheterie.model.bean.global.CarteActive;
import fr.trackoe.decheterie.model.bean.global.ComptePrepaye;
import fr.trackoe.decheterie.model.bean.global.ContenantBean;
import fr.trackoe.decheterie.model.bean.global.Decheterie;
import fr.trackoe.decheterie.model.bean.global.Depot;
import fr.trackoe.decheterie.model.bean.global.Flux;
import fr.trackoe.decheterie.model.bean.global.Icon;
import fr.trackoe.decheterie.model.bean.usager.Habitat;
import fr.trackoe.decheterie.model.bean.usager.Local;
import fr.trackoe.decheterie.model.bean.usager.Menage;
import fr.trackoe.decheterie.model.bean.usager.Usager;
import fr.trackoe.decheterie.model.bean.usager.UsagerHabitat;
import fr.trackoe.decheterie.model.bean.usager.UsagerMenage;
import fr.trackoe.decheterie.service.callback.DataCallback;
import fr.trackoe.decheterie.ui.activity.ContainerActivity;
import fr.trackoe.decheterie.ui.dialog.CustomDialogFlux;
import fr.trackoe.decheterie.ui.dialog.CustomDialogReturnAccueil;
import fr.trackoe.decheterie.ui.dialog.SoftKeyboardStateWatcher;

public class DepotFragment extends Fragment {
    ContainerActivity parentActivity;
    private ViewGroup depot_vg;
    private Depot depot;
    private long  depotId;
    private Carte carte;
    private AccountSetting  accountSetting;
    private ComptePrepaye   comptePrepaye;
    private boolean pageSignature = false;

    private LinearLayout    linearLayoutVolumeTotal;
    private LinearLayout    galleryFlux;
    private LinearLayout    galleryFluxChoisi;
    private TextView        textViewVolumeTotal;
    private EditText        editTextVolumeTotal;
    private TextView        textViewUniteVolumeTotal;
    private SoftKeyboardStateWatcher softKeyboardStateWatcher;
    private SoftKeyboardStateWatcher.SoftKeyboardStateListener keyboardListener;

    //parameters in NavagationDrawer(totalDecompte in DepotFragment)
    private String  nomInND;
    private boolean isUsagerMenageInND;
    private String  adresseInND;
    private String  numeroCarteInND;
    private float   apportRestantInND;
    private String  uniteApportRestantInND;
    private float   totalDecompte;


    private boolean isComeFromSettingFragment           = false;
    //parameters from apportProFragment
    private boolean isComeFromApportProFragment         = false;

    private boolean isComeFromPopUp                     = false;
    private boolean isComeFromIdentificationFragment    = false;

    //parameters from rechercherUsagerFragment
    private boolean isComeFromRechercherUsagerFragment  = false;
    private int     usagerIdFromRUF;
    private int     typeCarteIdFromRUF;

    //private boolean CCPU;
    private String nomUniteDecompte;

    //DB
    private DchAccountFluxSettingDB dchAccountFluxSettingDB;
    private DchAccountSettingDB dchAccountSettingDB;
    private DchApportFluxDB dchApportFluxDB;
    private DchCarteActiveDB dchCarteActiveDB;
    private DchCarteDB dchCarteDB;
    private DchCarteEtatRaisonDB dchCarteEtatRaisonDB;
    private DchChoixDecompteTotalDB dchChoixDecompteTotalDB;
    private DchComptePrepayeDB dchComptePrepayeDB;
    private DchDecheterieFluxDB dchDecheterieFluxDB;
    private DchDepotDB dchDepotDB;
    private DchFluxDB dchFluxDB;
    private DchTypeCarteDB dchTypeCarteDB;
    private DchUniteDB dchUniteDB;
    private DecheterieDB decheterieDB;
    private HabitatDB habitatDB;
    private IconDB iconDB;
    private LocalDB localDB;
    private MenageDB menageDB;
    private ModulesDB modulesDB;
    private TypeHabitatDB typeHabitatDB;
    private UsagerDB usagerDB;
    private UsagerHabitatDB usagerHabitatDB;
    private UsagerMenageDB usagerMenageDB;

    //the views of navigation drawer
    private ViewGroup nd_vg;
    private LinearLayout ndLinearLayoutLine1;
    private LinearLayout ndLinearLayoutLine2;
    private LinearLayout ndLinearLayoutLine3;
    private LinearLayout ndLinearLayoutLine4;
    private LinearLayout ndLinearLayoutLine5;
    private LinearLayout ndLinearLayoutLine6;
    private LinearLayout ndLinearLayoutLine7;
    private TextView ndTextViewLine1Title;
    private TextView ndTextViewLine2Title;
    private TextView ndTextViewLine3Title;
    private TextView ndTextViewLine4Title;
    private TextView ndTextViewLine5Title;
    private TextView ndTextViewLine6Title;
    private TextView ndTextViewLine7Title;
    private TextView ndTextViewLine1Value;
    private TextView ndTextViewLine2Value;
    private TextView ndTextViewLine3Value;
    private TextView ndTextViewLine4Value;
    private TextView ndTextViewLine5Value;
    private TextView ndTextViewLine6Value;
    private TextView ndTextViewLine7Value;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        System.out.println("DepotFragment --> onAttach()");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("DepotFragment --> onCreate()");

        initAllIsComeFrom();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("DepotFragment --> onCreateView()");

        depot_vg                    = (ViewGroup)           inflater.inflate(R.layout.depot_fragment, container, false);
        parentActivity              = (ContainerActivity)   getActivity();
        linearLayoutVolumeTotal     = (LinearLayout)        depot_vg.findViewById(R.id.depot_fragment_volume_total_linearlayout);
        textViewVolumeTotal         = (TextView)            depot_vg.findViewById(R.id.depot_fragment_volume_total_textView);
        editTextVolumeTotal         = (EditText)            depot_vg.findViewById(R.id.depot_fragment_volume_total_editText);
        textViewUniteVolumeTotal    = (TextView)            depot_vg.findViewById(R.id.depot_fragment_unite_volume_total_textView);
        //editTextVolumeTotal.setKeyListener(null);
        addTextChangedListener(editTextVolumeTotal);
        softKeyboardStateWatcher = new SoftKeyboardStateWatcher(parentActivity.findViewById(R.id.fragment_container));
        addKeyBoardListener();

        //parentActivity.getSupportFragmentManager().getBackStackEntryCount();

        initAllDB();
        openAllDB();

        //the case that we continue to edit the depot incompleted
        //*** pop-up ---> DepotFragment ***
        if(isComeFromPopUp){
            //get the depot on "statut en_cours"
            depot = dchDepotDB.getDepotByStatut(getResources().getInteger(R.integer.statut_en_cours));
            depotId = depot.getId();
            carte = dchCarteDB.getCarteFromID(depot.getCarteActiveCarteId());
            comptePrepaye = dchComptePrepayeDB.getComptePrepayeFromID(dchCarteActiveDB.getCarteActiveFromDchCarteId(depot.getCarteActiveCarteId()).getDchComptePrepayeId());
            setPageSignature();

            dchDepotDB.insertDepot(depot);

            //set nomUniteDecompte
            nomUniteDecompte = dchUniteDB.getUniteFromID(accountSetting.getUniteDepotDecheterieId()).getNom();

            //initViews
            initViewsNotNormal(inflater,container);

        }
        //the most normal case
        // *** "Idendification d'une carte" ---> DepotFragment ***
        else if(isComeFromIdentificationFragment){
            //get the numCarte sent From IdentifigationFragment
            getNumCarteFromIdentificationFragment();
            //create a new depot
            depotId = parentActivity.generateCodeFromDateAndNumTablette();
            String dateTime = "";
            int decheterieId = decheterieDB.getDecheterieByName(Configuration.getNameDecheterie()).getId();
            long carteActiveCarteId = carte.getId();
            long comptePrepayeId = dchCarteActiveDB.getCarteActiveFromDchCarteId(carteActiveCarteId).getDchComptePrepayeId();
            comptePrepaye = dchComptePrepayeDB.getComptePrepayeFromID(comptePrepayeId);
            float qtyTotalUDD = 0;
            String depotNom = "";
            int statut = getResources().getInteger(R.integer.statut_en_cours);
            Boolean isSent = false;

            depot = new Depot();
            depot.setId(depotId);
            //depot.setDateHeure(dateTime);
            depot.setDecheterieId(decheterieId);
            depot.setCarteActiveCarteId(carteActiveCarteId);
            depot.setComptePrepayeId(comptePrepayeId);
            //depot.setQtyTotalUDD(qtyTotalUDD);
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

            //set nomUniteDecompte
            nomUniteDecompte = dchUniteDB.getUniteFromID(accountSetting.getUniteDepotDecheterieId()).getNom();

            //initViews
            initViews(inflater, container);
        }
        //the case when click "back" in ApportProFragment
        // *** ApportProFragment ---> DepotFragment ***
        else if(isComeFromApportProFragment||isComeFromSettingFragment){
            //initViews
            initViewsNotNormal(inflater,container);
        }
        //the case
        // *** rechercherUsagerFragment ---> DepotFragment ***
        else if(isComeFromRechercherUsagerFragment){
            setParametersFromRUF();
            //create a new depot
            depotId = parentActivity.generateCodeFromDateAndNumTablette();
            setPageSignature();

            String dateTime = null;
            int decheterieId = decheterieDB.getDecheterieByName(Configuration.getNameDecheterie()).getId();
            long carteActiveCarteId = -1;
            long comptePrepayeId = dchComptePrepayeDB.getComptePrepayeFromUsagerId(usagerIdFromRUF).getId();
            comptePrepaye = dchComptePrepayeDB.getComptePrepayeFromID(comptePrepayeId);
            float qtyTotalUDD = 0;
            String depotNom = null;
            int statut = getResources().getInteger(R.integer.statut_en_cours);
            Boolean isSent = false;

            depot = new Depot();
            depot.setId(depotId);
            //depot.setDateHeure(dateTime);
            depot.setDecheterieId(decheterieId);
            depot.setCarteActiveCarteId(carteActiveCarteId);
            depot.setComptePrepayeId(comptePrepayeId);
            //depot.setQtyTotalUDD(qtyTotalUDD);
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

            //set nomUniteDecompte
            nomUniteDecompte = dchUniteDB.getUniteFromID(accountSetting.getUniteDepotDecheterieId()).getNom();

            //initViews
            initViews(inflater, container);
        }

        //init the views of the navigation drawer
        initViewsNavigationDrawer(inflater,container);

        //show the dialog if dch_account_setting.pt_minimum >= 0 et dch_compte_prepaye.qty_point < dch_account_setting.pt_minimum
        showReturnAccueilDialog();

        //show depot information
        showDepotDetails();

        closeAllDB();

        /*// Init Actionbar
        initActionBar();*/

        // Init Views
        //initViews(inflater, container);

        // Init des listeners
        initListeners(container);



        return depot_vg;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("DepotFragment --> onActivityCreated()");
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("DepotFragment --> onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("DepotFragment --> onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("DepotFragment --> onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();

        //delete the keyBoard when exit the DepotFragment
        softKeyboardStateWatcher.removeSoftKeyboardStateListener(keyboardListener);

        System.out.println("DepotFragment --> onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("DepotFragment --> onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("DepotFragment --> onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        System.out.println("DepotFragment --> onDetach()");
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
    Init Views under the condition when the depot is created for the first time
     */
    public void initViews(LayoutInflater inflater, ViewGroup container) {
        parentActivity.showHamburgerButton();
        parentActivity.changeToolbarIcon();
        ((DrawerLocker) getActivity()).setDrawerEnabled(true);
        initEidtTextVolumeTotal();
        galleryFlux = (LinearLayout) depot_vg.findViewById(R.id.depot_fragment_gallery_flux_linearLayout);
        galleryFluxChoisi = (LinearLayout) depot_vg.findViewById(R.id.depot_fragment_gallery_flux_choisi_linearLayout);

        final long depotId = this.depotId;

        //open the DBB
        IconDB iconDB = new IconDB(getContext());
        iconDB.open();
        DecheterieDB decheterieDB = new DecheterieDB(getContext());
        decheterieDB.open();
        DchFluxDB dchFluxDB = new DchFluxDB(getContext());
        dchFluxDB.open();
        final DchDecheterieFluxDB dchDecheterieFluxDB = new DchDecheterieFluxDB(getContext());
        dchDecheterieFluxDB.open();
        DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
        dchApportFluxDB.open();
        final DchDepotDB dchDepotDB = new DchDepotDB(getContext());
        dchDepotDB.open();
        DchAccountFluxSettingDB dchAccountFluxSettingDB = new DchAccountFluxSettingDB(getContext());
        dchAccountFluxSettingDB.open();

        //initialize the "volume total"
        setVisibilityViewOfVolumeTotal();
        editTextVolumeTotal.setText(getResources().getString(R.string.dialog_flux_layout_qty_calcul_textView_text));
        textViewUniteVolumeTotal.setText(nomUniteDecompte);


        //the list of icons which associate with the actual decheterie
        ArrayList<Flux> fluxList = new ArrayList<>();

        if(Configuration.getNameDecheterie().isEmpty()||Configuration.getNameDecheterie()==null){

        }
        else{
            Decheterie decheterie = decheterieDB.getDecheterieByName(Configuration.getNameDecheterie());
            fluxList = dchDecheterieFluxDB.getAllFluxByDecheterieId(decheterie.getId(), getContext());
        }

        //add the fluxs associated to the Decheterie
        for (final Flux flux: fluxList)
        {
            final View      view     =  inflater.inflate(R.layout.depot_fragment_flux_item, container, false);
            final View      viewCopy =  inflater.inflate(R.layout.depot_fragment_flux_item, container, false);
            final ImageView img      = (ImageView) view.findViewById(R.id.depot_fragment_flux_item_imageView);
            final ImageView imgCopy  = (ImageView) viewCopy.findViewById(R.id.depot_fragment_flux_item_imageView);

            final Icon currentIcon = iconDB.getIconByIdentifiant(flux.getIconId());
            final String iconName = currentIcon.getNom();
            final AccountFluxSetting accountFluxSetting = dchAccountFluxSettingDB.getAccountFluxSettingByAccountSettingIdAndFluxId(accountSetting.getId(), dchFluxDB.getFluxByIconId(currentIcon.getId()).getId());

            img.setBackgroundResource(getResources().getIdentifier(iconName, "drawable", getContext().getPackageName()));
            imgCopy.setBackgroundResource(getResources().getIdentifier(iconName, "drawable", getContext().getPackageName()));
            TextView txt     = (TextView) view.findViewById(R.id.depot_fragment_flux_item_textView);
            TextView txtCopy = (TextView) viewCopy.findViewById(R.id.depot_fragment_flux_item_textView);
            txt.setText(flux.getNom());
            txtCopy.setText(flux.getNom());
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    galleryFlux.removeView(view);
                    galleryFluxChoisi.addView(viewCopy);

                    DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                    dchApportFluxDB.open();
                    DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                    dchFluxDB.open();
                    DchUniteDB dchUniteDB = new DchUniteDB(getContext());
                    dchUniteDB.open();

                    //imgInDialog.setBackgroundResource(getResources().getIdentifier(iconName, "drawable", getContext().getPackageName()));

                    final CustomDialogFlux.Builder builder = new CustomDialogFlux.Builder(getContext());
                    builder.setMessage(getResources().getString(R.string.pop_up_message1) + flux.getNom());
                    builder.setTitle(flux.getNom());
                    builder.setIconName(iconName);
                    if(nomUniteDecompte != null) builder.setUniteDecompte(nomUniteDecompte);
                    String nomUniteApporte = dchUniteDB.getUniteFromID(dchFluxDB.getFluxByIconId(currentIcon.getId()).getUniteComptageId()).getNom();
                    if(nomUniteApporte != null) builder.setUniteApporte(nomUniteApporte);
                    builder.setCCPU(accountFluxSetting.getConvertComptagePrUDD());

                    //show lines from line1 line2 line3
                    final boolean[] lineVisbility = checkAFS(dchFluxDB.getFluxByIconId(currentIcon.getId()).getId());
                    if(lineVisbility[0]) builder.setVisibilityLine1(true);
                    if(lineVisbility[1]) builder.setVisibilityLine2(true);
                    if(lineVisbility[2]) builder.setVisibilityLine3(true);

                    //show the qty from BDD
                    if(dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()) != null){
                        if(lineVisbility[0]) builder.setEditTextQtyApporte("" + dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()).getQtyComptage());
                        if(lineVisbility[1]) builder.setEditTextQtyDecompte("" + dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()).getQtyUDD());
                        if(lineVisbility[2]) builder.setTextViewQtyCalculLine3("" + dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()).getQtyUDD());
                    }

                    builder.setPositiveButton(R.string.flux_positive_button, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //设置你的操作事项
                            DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                            dchFluxDB.open();
                            DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                            dchApportFluxDB.open();
                            DchDepotDB dchDepotDB = new DchDepotDB(getContext());
                            dchDepotDB.open();


                            //save the qty into BDD
                            int fluxId = dchFluxDB.getFluxByIconId(currentIcon.getId()).getId();
                            EditText editTextQuantiteApporte     = builder.getEditTextQuantiteApporte();
                            EditText editTextQuantiteDecompte    = builder.getEditTextQuantiteDecompte();
                            TextView textViewQuantiteCalculLine3 = builder.getTextViewQuantiteCalculLine3();
                            float qtyApporte = -1;
                            float qtyDecompte = -1;
                            if(lineVisbility[0]){
                                if(editTextQuantiteApporte.getText().toString().isEmpty()||editTextQuantiteApporte.getText().toString() ==""||editTextQuantiteApporte.getText().toString().equals(".")){
                                    qtyApporte = 0;
                                }
                                else {
                                    qtyApporte = Float.parseFloat(editTextQuantiteApporte.getText().toString());
                                }
                            }
                            if(lineVisbility[1]){
                                if(editTextQuantiteDecompte.getText().toString().isEmpty()||editTextQuantiteDecompte.getText().toString() ==""||editTextQuantiteDecompte.getText().toString().equals(".")){
                                    qtyDecompte = 0;
                                }
                                else {
                                    qtyDecompte = Float.parseFloat(editTextQuantiteDecompte.getText().toString());
                                }
                            }
                            if(lineVisbility[2]) {
                                if (textViewQuantiteCalculLine3.getText().toString().isEmpty() || textViewQuantiteCalculLine3.getText().toString() == "") {
                                    qtyDecompte = 0;
                                } else {
                                    qtyDecompte = Float.parseFloat(textViewQuantiteCalculLine3.getText().toString());
                                }
                            }
                            ApportFlux apportFlux = new ApportFlux(depotId,fluxId,qtyApporte,qtyDecompte,false);
                            dchApportFluxDB.insertApportFlux(apportFlux);


                            //refresh the qtyTotal of this depot
                            ArrayList<ApportFlux> listApportFlux = dchApportFluxDB.getListeApportFluxByDepotId(depotId);
                            float qtyDecompteTotal = 0;
                            for(ApportFlux af: listApportFlux){
                                float qty = af.getQtyUDD();
                                qtyDecompteTotal = qtyDecompteTotal + qty;
                            }
                            depot.setQtyTotalUDD(qtyDecompteTotal);
                            dchDepotDB.updateDepot(depot);

                            totalDecompte = qtyDecompteTotal;
                            //textViewVolumeTotal.setText(Html.fromHtml(PREFIX_VOLUME_TOTAL + qtyDecompteTotal + POSTFIX_VOLUME_TOTAL + nomUniteDecompte));
                            editTextVolumeTotal.setText("" + qtyDecompteTotal);
                            textViewUniteVolumeTotal.setText(nomUniteDecompte);

                            dchFluxDB.close();
                            dchApportFluxDB.close();
                            dchDepotDB.close();

                            closeKeyBoard();


                        }
                    });

                    builder.setNegativeButton("",
                            new android.content.DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                                    dchFluxDB.open();
                                    DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                                    dchApportFluxDB.open();
                                    DchDepotDB dchDepotDB = new DchDepotDB(getContext());
                                    dchDepotDB.open();



                                    dialog.dismiss();
                                    galleryFluxChoisi.removeView(viewCopy);
                                    galleryFlux.addView(view);
                                    dchApportFluxDB.deleteApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId());

                                    //refresh the volume total
                                    ArrayList<ApportFlux> listApportFlux = dchApportFluxDB.getListeApportFluxByDepotId(depotId);
                                    float qtyDecompteTotal = 0;
                                    for(ApportFlux af: listApportFlux){
                                        float qty = af.getQtyUDD();
                                        qtyDecompteTotal = qtyDecompteTotal + qty;
                                    }
                                    depot.setQtyTotalUDD(qtyDecompteTotal);
                                    dchDepotDB.updateDepot(depot);

                                    totalDecompte = qtyDecompteTotal;
                                    //textViewVolumeTotal.setText(Html.fromHtml(PREFIX_VOLUME_TOTAL + qtyDecompteTotal + POSTFIX_VOLUME_TOTAL + nomUniteDecompte));
                                    editTextVolumeTotal.setText("" + qtyDecompteTotal);
                                    textViewUniteVolumeTotal.setText(nomUniteDecompte);

                                    dchFluxDB.close();
                                    dchApportFluxDB.close();
                                    dchDepotDB.close();

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
                            DchUniteDB dchUniteDB = new DchUniteDB(getContext());
                            dchUniteDB.open();


                            //imgInDialog.setBackgroundResource(getResources().getIdentifier(iconName, "drawable", getContext().getPackageName()));
                            final CustomDialogFlux.Builder builder = new CustomDialogFlux.Builder(getContext());
                            builder.setMessage(getResources().getString(R.string.pop_up_message1) + flux.getNom());
                            builder.setTitle(flux.getNom());
                            builder.setIconName(iconName);
                            if(nomUniteDecompte != null) builder.setUniteDecompte(nomUniteDecompte);
                            String nomUniteApporte = dchUniteDB.getUniteFromID(dchFluxDB.getFluxByIconId(currentIcon.getId()).getUniteComptageId()).getNom();
                            if(nomUniteApporte != null) builder.setUniteApporte(nomUniteApporte);
                            builder.setCCPU(accountFluxSetting.getConvertComptagePrUDD());

                            //show lines from line1 line2 line3
                            final boolean[] lineVisbility = checkAFS(dchFluxDB.getFluxByIconId(currentIcon.getId()).getId());
                            if(lineVisbility[0]) builder.setVisibilityLine1(true);
                            if(lineVisbility[1])  builder.setVisibilityLine2(true);
                            if(lineVisbility[2]) builder.setVisibilityLine3(true);

                            //show the qty from BDD
                            if(dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()) != null){
                                if(lineVisbility[0]) builder.setEditTextQtyApporte(""+dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()).getQtyComptage());
                                if(lineVisbility[1]) builder.setEditTextQtyDecompte(""+dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()).getQtyUDD());
                                if(lineVisbility[2]) builder.setTextViewQtyCalculLine3("" + dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()).getQtyUDD());
                            }

                            builder.setPositiveButton(R.string.flux_positive_button, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    //设置你的操作事项
                                    DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                                    dchApportFluxDB.open();
                                    DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                                    dchFluxDB.open();
                                    DchDepotDB dchDepotDB = new DchDepotDB(getContext());
                                    dchDepotDB.open();

                                    int fluxId = dchFluxDB.getFluxByIconId(currentIcon.getId()).getId();
                                    EditText editTextQuantiteApporte = builder.getEditTextQuantiteApporte();
                                    EditText editTextQuantiteDecompte = builder.getEditTextQuantiteDecompte();
                                    TextView textViewQuantiteCalculLine3 = builder.getTextViewQuantiteCalculLine3();
                                    float qtyApporte = -1;
                                    float qtyDecompte = -1;

                                    if(lineVisbility[0]){
                                        if(editTextQuantiteApporte.getText().toString().isEmpty()||editTextQuantiteApporte.getText().toString() ==""||editTextQuantiteApporte.getText().toString().equals(".")){
                                            qtyApporte = 0;
                                        }
                                        else {
                                            qtyApporte = Float.parseFloat(editTextQuantiteApporte.getText().toString());
                                        }
                                    }
                                    if(lineVisbility[1]) {
                                        if (editTextQuantiteDecompte.getText().toString().isEmpty() || editTextQuantiteDecompte.getText().toString() == ""||editTextQuantiteDecompte.getText().toString().equals(".")) {
                                            qtyDecompte = 0;
                                        } else {
                                            qtyDecompte = Float.parseFloat(editTextQuantiteDecompte.getText().toString());
                                        }
                                    }
                                    if(lineVisbility[2]) {
                                        if (textViewQuantiteCalculLine3.getText().toString().isEmpty() || textViewQuantiteCalculLine3.getText().toString() == "") {
                                            qtyDecompte = 0;
                                        } else {
                                            qtyDecompte = Float.parseFloat(textViewQuantiteCalculLine3.getText().toString());
                                        }
                                    }

                                    if(dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()) == null){
                                        //save the qty into BDD
                                        ApportFlux apportFlux = new ApportFlux(depotId,fluxId,qtyApporte,qtyDecompte);
                                        dchApportFluxDB.insertApportFlux(apportFlux);
                                    }
                                    else{
                                        //update the data in BDD
                                        ApportFlux apportFlux = new ApportFlux(depotId,fluxId,qtyApporte,qtyDecompte);
                                        dchApportFluxDB.updateApportFlux(apportFlux);
                                    }

                                    //refresh the qtyTotal of this depot
                                    ArrayList<ApportFlux> listApportFlux = dchApportFluxDB.getListeApportFluxByDepotId(depotId);
                                    float qtyDecompteTotal = 0;
                                    for(ApportFlux af: listApportFlux){
                                        float qty = af.getQtyUDD();
                                        qtyDecompteTotal = qtyDecompteTotal + qty;
                                    }
                                    depot.setQtyTotalUDD(qtyDecompteTotal);
                                    dchDepotDB.updateDepot(depot);

                                    totalDecompte = qtyDecompteTotal;
                                    //textViewVolumeTotal.setText(Html.fromHtml(PREFIX_VOLUME_TOTAL + qtyDecompteTotal + POSTFIX_VOLUME_TOTAL + nomUniteDecompte));
                                    editTextVolumeTotal.setText("" + qtyDecompteTotal);
                                    textViewUniteVolumeTotal.setText(nomUniteDecompte);


                                    dchApportFluxDB.close();
                                    dchFluxDB.close();
                                    dchDepotDB.close();

                                    closeKeyBoard();

                                }
                            });

                            builder.setNegativeButton("",
                                    new android.content.DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                                            dchFluxDB.open();
                                            DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                                            dchApportFluxDB.open();
                                            DchDepotDB dchDepotDB = new DchDepotDB(getContext());
                                            dchDepotDB.open();

                                            dialog.dismiss();
                                            galleryFluxChoisi.removeView(viewCopy);
                                            galleryFlux.addView(view);
                                            dchApportFluxDB.deleteApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId());

                                            //refresh the volume total
                                            ArrayList<ApportFlux> listApportFlux = dchApportFluxDB.getListeApportFluxByDepotId(depotId);
                                            float qtyDecompteTotal = 0;
                                            for(ApportFlux af: listApportFlux){
                                                float qty = af.getQtyUDD();
                                                qtyDecompteTotal = qtyDecompteTotal + qty;
                                            }
                                            depot.setQtyTotalUDD(qtyDecompteTotal);
                                            dchDepotDB.updateDepot(depot);

                                            totalDecompte = qtyDecompteTotal;
                                            //textViewVolumeTotal.setText(Html.fromHtml(PREFIX_VOLUME_TOTAL + qtyDecompteTotal + POSTFIX_VOLUME_TOTAL + nomUniteDecompte));
                                            editTextVolumeTotal.setText("" + qtyDecompteTotal);
                                            textViewUniteVolumeTotal.setText(nomUniteDecompte);

                                            dchFluxDB.close();
                                            dchApportFluxDB.close();
                                            dchDepotDB.close();
                                        }
                                    });

                            builder.create().show();

                            dchApportFluxDB.close();
                            dchFluxDB.close();
                            dchUniteDB.close();

                        }
                    });

                    dchApportFluxDB.close();
                    dchFluxDB.close();
                    dchUniteDB.close();
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
        dchAccountFluxSettingDB.close();

        parentActivity.openDrawerWithDelay();

    }

    /*
    Init Views only under the condition that "oui" is clicked
     */
    public void initViewsNotNormal(LayoutInflater inflater, ViewGroup container) {
        parentActivity.showHamburgerButton();
        parentActivity.changeToolbarIcon();
        ((DrawerLocker) getActivity()).setDrawerEnabled(true);
        initEidtTextVolumeTotal();
        galleryFlux       = (LinearLayout) depot_vg.findViewById(R.id.depot_fragment_gallery_flux_linearLayout);
        galleryFluxChoisi = (LinearLayout) depot_vg.findViewById(R.id.depot_fragment_gallery_flux_choisi_linearLayout);


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
        DchAccountFluxSettingDB dchAccountFluxSettingDB = new DchAccountFluxSettingDB(getContext());
        dchAccountFluxSettingDB.open();

        //initialize the "volume total"
        setVisibilityViewOfVolumeTotal();
        if(depot.getQtyTotalUDD() == 0){
            //textViewVolumeTotal.setText(Html.fromHtml(INITIAL_VOLUME_TOTAL + nomUniteDecompte));
            editTextVolumeTotal.setText("" + 0.0);
            textViewUniteVolumeTotal.setText(nomUniteDecompte);
        }
        else{
            totalDecompte = depot.getQtyTotalUDD();

            //textViewVolumeTotal.setText(Html.fromHtml(PREFIX_VOLUME_TOTAL + depot.getQtyTotalUDD() + POSTFIX_VOLUME_TOTAL + nomUniteDecompte));
            editTextVolumeTotal.setText("" + depot.getQtyTotalUDD());
            textViewUniteVolumeTotal.setText(nomUniteDecompte);
        }

        //the list of icons which associate with the actual decheterie
        ArrayList<Flux> fluxListTotal               = new ArrayList();
        ArrayList<Flux> fluxListOfGalleryFlux       = new ArrayList();
        ArrayList<Flux> fluxListOfGalleryFluxChoisi = new ArrayList();

        if(Configuration.getNameDecheterie().isEmpty()||Configuration.getNameDecheterie()==null){

        }
        else{
            Decheterie decheterie = decheterieDB.getDecheterieByName(Configuration.getNameDecheterie());
            fluxListTotal = dchDecheterieFluxDB.getAllFluxByDecheterieId(decheterie.getId(), getContext());
        }


        //get the fluxs which associate to the depot in the bottom scroll
        ArrayList<ApportFlux> apportFluxList = dchApportFluxDB.getListeApportFluxByDepotId(depotId);
        for(ApportFlux af: apportFluxList){
            Flux f = dchFluxDB.getFluxByIdentifiant(af.getFluxId());
            fluxListOfGalleryFluxChoisi.add(f);
        }

        //get the rest flux which are in the top scroll
        int[] arrFluxTotalIdList  = new int[fluxListTotal.size()];
        int[] arrFluxChoisiIdList = new int[fluxListOfGalleryFluxChoisi.size()];
        for(int i = 0; i < fluxListTotal.size(); i ++){
            arrFluxTotalIdList[i] = fluxListTotal.get(i).getId();
        }
        for(int i = 0; i < fluxListOfGalleryFluxChoisi.size(); i ++){
            arrFluxChoisiIdList[i] = fluxListOfGalleryFluxChoisi.get(i).getId();
        }
        HashSet<Integer> map = new HashSet<Integer>();
        for (int i : arrFluxChoisiIdList)
            map.add(i);
        for (int i : arrFluxTotalIdList) {
            if (!map.contains(i)){
                // found not duplicate!
                    fluxListOfGalleryFlux.add(dchFluxDB.getFluxByIdentifiant(i));
            }

        }



        //add the flux in "iconListOfGalleryFlux" into "galleryFlux"(top scroll)
        for (final Flux flux: fluxListOfGalleryFlux)
        {
            final View      view         = inflater.inflate(R.layout.depot_fragment_flux_item, container, false);
            final View      viewCopy     = inflater.inflate(R.layout.depot_fragment_flux_item, container, false);
            final ImageView img          = (ImageView) view.findViewById(R.id.depot_fragment_flux_item_imageView);
            final ImageView imgCopy      = (ImageView) viewCopy.findViewById(R.id.depot_fragment_flux_item_imageView);

            final Icon currentIcon = iconDB.getIconByIdentifiant(flux.getIconId());
            final String iconName  = currentIcon.getNom();
            final AccountFluxSetting accountFluxSetting = dchAccountFluxSettingDB.getAccountFluxSettingByAccountSettingIdAndFluxId(accountSetting.getId(), dchFluxDB.getFluxByIconId(currentIcon.getId()).getId());

            img.    setBackgroundResource(getResources().getIdentifier(iconName, "drawable", getContext().getPackageName()));
            imgCopy.setBackgroundResource(getResources().getIdentifier(iconName, "drawable", getContext().getPackageName()));
            TextView txt     = (TextView) view.findViewById(R.id.depot_fragment_flux_item_textView);
            TextView txtCopy = (TextView) viewCopy.findViewById(R.id.depot_fragment_flux_item_textView);
            txt.    setText(flux.getNom());
            txtCopy.setText(flux.getNom());

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    galleryFlux.removeView(view);
                    galleryFluxChoisi.addView(viewCopy);

                    DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                    dchApportFluxDB.open();
                    DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                    dchFluxDB.open();
                    DchUniteDB dchUniteDB = new DchUniteDB(getContext());
                    dchUniteDB.open();

                    //imgInDialog.setBackgroundResource(getResources().getIdentifier(iconName, "drawable", getContext().getPackageName()));

                    final CustomDialogFlux.Builder builder = new CustomDialogFlux.Builder(getContext());
                    builder.setMessage(getResources().getString(R.string.pop_up_message1) + flux.getNom());
                    builder.setTitle(flux.getNom());
                    builder.setIconName(iconName);
                    if(nomUniteDecompte != null) builder.setUniteDecompte(nomUniteDecompte);
                    String nomUniteApporte = dchUniteDB.getUniteFromID(dchFluxDB.getFluxByIconId(currentIcon.getId()).getUniteComptageId()).getNom();
                    if(nomUniteApporte != null) builder.setUniteApporte(nomUniteApporte);
                    builder.setCCPU(accountFluxSetting.getConvertComptagePrUDD());

                    //show lines from line1 line2 line3
                    final boolean[] lineVisbility = checkAFS(dchFluxDB.getFluxByIconId(currentIcon.getId()).getId());
                    if(lineVisbility[0]) builder.setVisibilityLine1(true);
                    if(lineVisbility[1])  builder.setVisibilityLine2(true);
                    if(lineVisbility[2]) builder.setVisibilityLine3(true);

                    //show the qty from BDD
                    if(dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()) != null){
                        if(lineVisbility[0]) builder.setEditTextQtyApporte(""+dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()).getQtyComptage());
                        if(lineVisbility[1]) builder.setEditTextQtyDecompte(""+dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()).getQtyUDD());
                        if(lineVisbility[2]) builder.setTextViewQtyCalculLine3("" + dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()).getQtyUDD());
                    }
                    builder.setPositiveButton(R.string.flux_positive_button, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //设置你的操作事项
                            DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                            dchFluxDB.open();
                            DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                            dchApportFluxDB.open();
                            DchDepotDB dchDepotDB = new DchDepotDB(getContext());
                            dchDepotDB.open();

                            //save the qty into BDD
                            int fluxId = dchFluxDB.getFluxByIconId(currentIcon.getId()).getId();
                            EditText editTextQuantiteApporte = builder.getEditTextQuantiteApporte();
                            EditText editTextQuantiteDecompte = builder.getEditTextQuantiteDecompte();
                            TextView textViewQuantiteCalculLine3 = builder.getTextViewQuantiteCalculLine3();
                            float qtyApporte = -1;
                            float qtyDecompte = -1;
                            if(lineVisbility[0]){
                                if(editTextQuantiteApporte.getText().toString().isEmpty()||editTextQuantiteApporte.getText().toString() ==""){
                                    qtyApporte = 0;
                                }
                                else {
                                    qtyApporte = Float.parseFloat(editTextQuantiteApporte.getText().toString());
                                }
                            }
                            if(lineVisbility[1]){
                                if(editTextQuantiteDecompte.getText().toString().isEmpty()||editTextQuantiteDecompte.getText().toString() ==""){
                                    qtyDecompte = 0;
                                }
                                else {
                                    qtyDecompte = Float.parseFloat(editTextQuantiteDecompte.getText().toString());
                                }
                            }
                            if(lineVisbility[2]) {
                                if (textViewQuantiteCalculLine3.getText().toString().isEmpty() || textViewQuantiteCalculLine3.getText().toString() == "") {
                                    qtyDecompte = 0;
                                } else {
                                    qtyDecompte = Float.parseFloat(textViewQuantiteCalculLine3.getText().toString());
                                }
                            }
                            ApportFlux apportFlux = new ApportFlux(depotId,fluxId,qtyApporte,qtyDecompte,false);
                            dchApportFluxDB.insertApportFlux(apportFlux);

                            //refresh the qtyTotal of this depot
                            ArrayList<ApportFlux> listApportFlux = dchApportFluxDB.getListeApportFluxByDepotId(depotId);
                            float qtyDecompteTotal = 0;
                            for(ApportFlux af: listApportFlux){
                                float qty = af.getQtyUDD();
                                qtyDecompteTotal = qtyDecompteTotal + qty;
                            }
                            depot.setQtyTotalUDD(qtyDecompteTotal);
                            dchDepotDB.updateDepot(depot);

                            totalDecompte = qtyDecompteTotal;

                            //textViewVolumeTotal.setText(Html.fromHtml(PREFIX_VOLUME_TOTAL + qtyDecompteTotal + POSTFIX_VOLUME_TOTAL + nomUniteDecompte));
                            editTextVolumeTotal.setText("" + qtyDecompteTotal);
                            textViewUniteVolumeTotal.setText(nomUniteDecompte);

                            dchFluxDB.close();
                            dchApportFluxDB.close();
                            dchDepotDB.close();

                            closeKeyBoard();

                        }
                    });

                    builder.setNegativeButton("",
                            new android.content.DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                                    dchFluxDB.open();
                                    DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                                    dchApportFluxDB.open();
                                    DchDepotDB dchDepotDB = new DchDepotDB(getContext());
                                    dchDepotDB.open();

                                    dialog.dismiss();
                                    galleryFluxChoisi.removeView(viewCopy);
                                    galleryFlux.addView(view);
                                    dchApportFluxDB.deleteApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId());

                                    //refresh the qtyTotal of this depot
                                    ArrayList<ApportFlux> listApportFlux = dchApportFluxDB.getListeApportFluxByDepotId(depotId);
                                    float qtyDecompteTotal = 0;
                                    for(ApportFlux af: listApportFlux){
                                        float qty = af.getQtyUDD();
                                        qtyDecompteTotal = qtyDecompteTotal + qty;
                                    }
                                    depot.setQtyTotalUDD(qtyDecompteTotal);
                                    dchDepotDB.updateDepot(depot);

                                    totalDecompte = qtyDecompteTotal;

                                    //textViewVolumeTotal.setText(Html.fromHtml(PREFIX_VOLUME_TOTAL + qtyDecompteTotal + POSTFIX_VOLUME_TOTAL + nomUniteDecompte));
                                    editTextVolumeTotal.setText("" + qtyDecompteTotal);
                                    textViewUniteVolumeTotal.setText(nomUniteDecompte);

                                    dchFluxDB.close();
                                    dchApportFluxDB.close();
                                    dchDepotDB.close();
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
                            DchUniteDB dchUniteDB = new DchUniteDB(getContext());
                            dchUniteDB.open();


                            //imgInDialog.setBackgroundResource(getResources().getIdentifier(iconName, "drawable", getContext().getPackageName()));
                            final CustomDialogFlux.Builder builder = new CustomDialogFlux.Builder(getContext());
                            builder.setMessage(getResources().getString(R.string.pop_up_message1) + flux.getNom());
                            builder.setTitle(flux.getNom());
                            builder.setIconName(iconName);
                            if(nomUniteDecompte != null) builder.setUniteDecompte(nomUniteDecompte);
                            String nomUniteApporte = dchUniteDB.getUniteFromID(dchFluxDB.getFluxByIconId(currentIcon.getId()).getUniteComptageId()).getNom();
                            if(nomUniteApporte != null) builder.setUniteApporte(nomUniteApporte);
                            builder.setCCPU(accountFluxSetting.getConvertComptagePrUDD());

                            //show lines from line1 line2 line3
                            final boolean[] lineVisbility = checkAFS(dchFluxDB.getFluxByIconId(currentIcon.getId()).getId());
                            if(lineVisbility[0]) builder.setVisibilityLine1(true);
                            if(lineVisbility[1])  builder.setVisibilityLine2(true);
                            if(lineVisbility[2]) builder.setVisibilityLine3(true);
                            //show the qty from BDD
                            if(dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()) != null){
                                if(lineVisbility[0]) builder.setEditTextQtyApporte(""+dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()).getQtyComptage());
                                if(lineVisbility[1]) builder.setEditTextQtyDecompte(""+dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()).getQtyUDD());
                                if(lineVisbility[2]) builder.setTextViewQtyCalculLine3("" + dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()).getQtyUDD());
                            }
                            builder.setPositiveButton(R.string.flux_positive_button, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    //设置你的操作事项
                                    DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                                    dchApportFluxDB.open();
                                    DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                                    dchFluxDB.open();
                                    DchDepotDB dchDepotDB = new DchDepotDB(getContext());
                                    dchDepotDB.open();

                                    int fluxId = dchFluxDB.getFluxByIconId(currentIcon.getId()).getId();
                                    EditText editTextQuantiteApporte = builder.getEditTextQuantiteApporte();
                                    EditText editTextQuantiteDecompte = builder.getEditTextQuantiteDecompte();
                                    TextView textViewQuantiteCalculLine3 = builder.getTextViewQuantiteCalculLine3();
                                    float qtyApporte = -1;
                                    float qtyDecompte = -1;

                                    if(lineVisbility[0]){
                                        if(editTextQuantiteApporte.getText().toString().isEmpty()||editTextQuantiteApporte.getText().toString() ==""){
                                            qtyApporte = 0;
                                        }
                                        else {
                                            qtyApporte = Float.parseFloat(editTextQuantiteApporte.getText().toString());
                                        }
                                    }
                                    if(lineVisbility[1]) {
                                        if (editTextQuantiteDecompte.getText().toString().isEmpty() || editTextQuantiteDecompte.getText().toString() == "") {
                                            qtyDecompte = 0;
                                        } else {
                                            qtyDecompte = Float.parseFloat(editTextQuantiteDecompte.getText().toString());
                                        }
                                    }
                                    if(lineVisbility[2]) {
                                        if (textViewQuantiteCalculLine3.getText().toString().isEmpty() || textViewQuantiteCalculLine3.getText().toString() == "") {
                                            qtyDecompte = 0;
                                        } else {
                                            qtyDecompte = Float.parseFloat(textViewQuantiteCalculLine3.getText().toString());
                                        }
                                    }

                                    if(dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()) == null){
                                        //save the qty into BDD
                                        ApportFlux apportFlux = new ApportFlux(depotId,fluxId,qtyApporte,qtyDecompte);
                                        dchApportFluxDB.insertApportFlux(apportFlux);
                                    }
                                    else{
                                        //update the data in BDD
                                        ApportFlux apportFlux = new ApportFlux(depotId,fluxId,qtyApporte,qtyDecompte);
                                        dchApportFluxDB.updateApportFlux(apportFlux);
                                    }

                                    //refresh the qtyTotal of this depot
                                    ArrayList<ApportFlux> listApportFlux = dchApportFluxDB.getListeApportFluxByDepotId(depotId);
                                    float qtyDecompteTotal = 0;
                                    for(ApportFlux af: listApportFlux){
                                        float qty = af.getQtyUDD();
                                        qtyDecompteTotal = qtyDecompteTotal + qty;
                                    }
                                    depot.setQtyTotalUDD(qtyDecompteTotal);
                                    dchDepotDB.updateDepot(depot);

                                    totalDecompte = qtyDecompteTotal;

                                    //textViewVolumeTotal.setText(Html.fromHtml(PREFIX_VOLUME_TOTAL + qtyDecompteTotal + POSTFIX_VOLUME_TOTAL + nomUniteDecompte));
                                    editTextVolumeTotal.setText("" + qtyDecompteTotal);
                                    textViewUniteVolumeTotal.setText(nomUniteDecompte);

                                    dchApportFluxDB.close();
                                    dchFluxDB.close();
                                    dchDepotDB.close();

                                    closeKeyBoard();


                                }
                            });

                            builder.setNegativeButton("",
                                    new android.content.DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                                            dchFluxDB.open();
                                            DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                                            dchApportFluxDB.open();
                                            DchDepotDB dchDepotDB = new DchDepotDB(getContext());
                                            dchDepotDB.open();

                                            dialog.dismiss();
                                            galleryFluxChoisi.removeView(viewCopy);
                                            galleryFlux.addView(view);
                                            dchApportFluxDB.deleteApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId());

                                            //refresh the qtyTotal of this depot
                                            ArrayList<ApportFlux> listApportFlux = dchApportFluxDB.getListeApportFluxByDepotId(depotId);
                                            float qtyDecompteTotal = 0;
                                            for(ApportFlux af: listApportFlux){
                                                float qty = af.getQtyUDD();
                                                qtyDecompteTotal = qtyDecompteTotal + qty;
                                            }
                                            depot.setQtyTotalUDD(qtyDecompteTotal);
                                            dchDepotDB.updateDepot(depot);

                                            totalDecompte = qtyDecompteTotal;

                                            //textViewVolumeTotal.setText(Html.fromHtml(PREFIX_VOLUME_TOTAL + qtyDecompteTotal + POSTFIX_VOLUME_TOTAL + nomUniteDecompte));
                                            editTextVolumeTotal.setText("" + qtyDecompteTotal);
                                            textViewUniteVolumeTotal.setText(nomUniteDecompte);

                                            dchFluxDB.close();
                                            dchApportFluxDB.close();
                                            dchDepotDB.close();
                                        }
                                    });

                            builder.create().show();

                            dchApportFluxDB.close();
                            dchFluxDB.close();
                            dchUniteDB.close();

                        }
                    });

                    dchApportFluxDB.close();
                    dchFluxDB.close();
                    dchUniteDB.close();
                }
            });
            galleryFlux.addView(view);
        }

        //add the flux in "iconListOfGalleryFluxChoisi" to the "galleryFluxChoisi"(bottom scroll)
        for (final Flux flux: fluxListOfGalleryFluxChoisi){
            final View      view        = inflater.inflate(R.layout.depot_fragment_flux_item, container, false);
            final View      viewCopy    = inflater.inflate(R.layout.depot_fragment_flux_item, container, false);
            final ImageView img         = (ImageView) view.findViewById(R.id.depot_fragment_flux_item_imageView);
            final ImageView imgCopy     = (ImageView) viewCopy.findViewById(R.id.depot_fragment_flux_item_imageView);

            final Icon   currentIcon    = iconDB.getIconByIdentifiant(flux.getIconId());
            final String iconName       = currentIcon.getNom();
            final AccountFluxSetting accountFluxSetting = dchAccountFluxSettingDB.getAccountFluxSettingByAccountSettingIdAndFluxId(accountSetting.getId(), dchFluxDB.getFluxByIconId(currentIcon.getId()).getId());


            img.    setBackgroundResource(getResources().getIdentifier(iconName, "drawable", getContext().getPackageName()));
            imgCopy.setBackgroundResource(getResources().getIdentifier(iconName, "drawable", getContext().getPackageName()));
            TextView txt     = (TextView) view.findViewById(R.id.depot_fragment_flux_item_textView);
            TextView txtCopy = (TextView) viewCopy.findViewById(R.id.depot_fragment_flux_item_textView);
            txt.    setText(flux.getNom());
            txtCopy.setText(flux.getNom());

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                    dchApportFluxDB.open();
                    DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                    dchFluxDB.open();
                    DchUniteDB dchUniteDB = new DchUniteDB(getContext());
                    dchUniteDB.open();


                    final CustomDialogFlux.Builder builder = new CustomDialogFlux.Builder(getContext());
                    builder.setMessage(getResources().getString(R.string.pop_up_message1) + flux.getNom());
                    builder.setTitle(flux.getNom());
                    builder.setIconName(iconName);
                    if(nomUniteDecompte != null) builder.setUniteDecompte(nomUniteDecompte);
                    String nomUniteApporte = dchUniteDB.getUniteFromID(dchFluxDB.getFluxByIconId(currentIcon.getId()).getUniteComptageId()).getNom();
                    if(nomUniteApporte != null) builder.setUniteApporte(nomUniteApporte);
                    builder.setCCPU(accountFluxSetting.getConvertComptagePrUDD());

                    //show lines from line1 line2 line3
                    final boolean[] lineVisbility = checkAFS(dchFluxDB.getFluxByIconId(currentIcon.getId()).getId());
                    if(lineVisbility[0]) builder.setVisibilityLine1(true);
                    if(lineVisbility[1])  builder.setVisibilityLine2(true);
                    if(lineVisbility[2]) builder.setVisibilityLine3(true);
                    //show the qty from BDD
                    if(dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()) != null){
                        if(lineVisbility[0]) builder.setEditTextQtyApporte(""+dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()).getQtyComptage());
                        if(lineVisbility[1]) builder.setEditTextQtyDecompte(""+dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()).getQtyUDD());
                        if(lineVisbility[2]) builder.setTextViewQtyCalculLine3("" + dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()).getQtyUDD());
                    }

                    builder.setPositiveButton(R.string.flux_positive_button, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //设置你的操作事项
                            DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                            dchApportFluxDB.open();
                            DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                            dchFluxDB.open();
                            DchDepotDB dchDepotDB = new DchDepotDB(getContext());
                            dchDepotDB.open();

                            int fluxId = dchFluxDB.getFluxByIconId(currentIcon.getId()).getId();
                            EditText editTextQuantiteApporte = builder.getEditTextQuantiteApporte();
                            EditText editTextQuantiteDecompte = builder.getEditTextQuantiteDecompte();
                            TextView textViewQuantiteCalculLine3 = builder.getTextViewQuantiteCalculLine3();
                            float qtyApporte  = -1;
                            float qtyDecompte = -1;

                            if(lineVisbility[0]){
                                if(editTextQuantiteApporte.getText().toString().isEmpty()||editTextQuantiteApporte.getText().toString() ==""){
                                    qtyApporte = 0;
                                }
                                else {
                                    qtyApporte = Float.parseFloat(editTextQuantiteApporte.getText().toString());
                                }
                            }
                            if(lineVisbility[1]) {
                                if (editTextQuantiteDecompte.getText().toString().isEmpty() || editTextQuantiteDecompte.getText().toString() == "") {
                                    qtyDecompte = 0;
                                } else {
                                    qtyDecompte = Float.parseFloat(editTextQuantiteDecompte.getText().toString());
                                }
                            }
                            if(lineVisbility[2]) {
                                if (textViewQuantiteCalculLine3.getText().toString().isEmpty() || textViewQuantiteCalculLine3.getText().toString() == "") {
                                    qtyDecompte = 0;
                                } else {
                                    qtyDecompte = Float.parseFloat(textViewQuantiteCalculLine3.getText().toString());
                                }
                            }


                            if(dchApportFluxDB.getApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId()) == null){
                                //save the qty into BDD
                                ApportFlux apportFlux = new ApportFlux(depotId,fluxId,qtyApporte,qtyDecompte);
                                dchApportFluxDB.insertApportFlux(apportFlux);
                            }
                            else{
                                //update the data in BDD
                                ApportFlux apportFlux = new ApportFlux(depotId,fluxId,qtyApporte,qtyDecompte);
                                dchApportFluxDB.updateApportFlux(apportFlux);
                            }

                            //refresh the qtyTotal of this depot
                            ArrayList<ApportFlux> listApportFlux = dchApportFluxDB.getListeApportFluxByDepotId(depotId);
                            float qtyDecompteTotal = 0;
                            for(ApportFlux af: listApportFlux){
                                float qty = af.getQtyUDD();
                                qtyDecompteTotal = qtyDecompteTotal + qty;
                            }
                            depot.setQtyTotalUDD(qtyDecompteTotal);
                            dchDepotDB.updateDepot(depot);

                            totalDecompte = qtyDecompteTotal;

                            //textViewVolumeTotal.setText(Html.fromHtml(PREFIX_VOLUME_TOTAL + qtyDecompteTotal + POSTFIX_VOLUME_TOTAL + nomUniteDecompte));
                            editTextVolumeTotal.setText("" + qtyDecompteTotal);
                            textViewUniteVolumeTotal.setText(nomUniteDecompte);

                            dchApportFluxDB.close();
                            dchFluxDB.close();
                            dchDepotDB.close();

                            closeKeyBoard();

                        }
                    });

                    builder.setNegativeButton("", new android.content.DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                            dchFluxDB.open();
                            DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                            dchApportFluxDB.open();
                            DchDepotDB dchDepotDB = new DchDepotDB(getContext());
                            dchDepotDB.open();

                            dialog.dismiss();
                            galleryFluxChoisi.removeView(view);
                            galleryFlux.addView(viewCopy);
                            dchApportFluxDB.deleteApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId());

                            //refresh the qtyTotal of this depot
                            ArrayList<ApportFlux> listApportFlux = dchApportFluxDB.getListeApportFluxByDepotId(depotId);
                            float qtyDecompteTotal = 0;
                            for(ApportFlux af: listApportFlux){
                                float qty = af.getQtyUDD();
                                qtyDecompteTotal = qtyDecompteTotal + qty;
                            }
                            depot.setQtyTotalUDD(qtyDecompteTotal);
                            dchDepotDB.updateDepot(depot);

                            totalDecompte = qtyDecompteTotal;

                            //textViewVolumeTotal.setText(Html.fromHtml(PREFIX_VOLUME_TOTAL + qtyDecompteTotal + POSTFIX_VOLUME_TOTAL + nomUniteDecompte));
                            editTextVolumeTotal.     setText("" + qtyDecompteTotal);
                            textViewUniteVolumeTotal.setText(nomUniteDecompte);

                            dchFluxDB.close();
                            dchApportFluxDB.close();
                            dchDepotDB.close();
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
                            DchUniteDB dchUniteDB = new DchUniteDB(getContext());
                            dchUniteDB.open();

                            galleryFlux.removeView(viewCopy);
                            galleryFluxChoisi.addView(view);

                            //imgInDialog.setBackgroundResource(getResources().getIdentifier(iconName, "drawable", getContext().getPackageName()));
                            final CustomDialogFlux.Builder builder = new CustomDialogFlux.Builder(getContext());
                            builder.setMessage(getResources().getString(R.string.pop_up_message1) + flux.getNom());
                            builder.setTitle(flux.getNom());
                            builder.setIconName(iconName);
                            if(nomUniteDecompte != null) builder.setUniteDecompte(nomUniteDecompte);
                            String nomUniteApporte = dchUniteDB.getUniteFromID(dchFluxDB.getFluxByIconId(currentIcon.getId()).getUniteComptageId()).getNom();
                            if(nomUniteApporte != null) builder.setUniteApporte(nomUniteApporte);
                            builder.setCCPU(accountFluxSetting.getConvertComptagePrUDD());

                            //show lines from line1 line2 line3
                            final boolean[] lineVisbility = checkAFS(dchFluxDB.getFluxByIconId(currentIcon.getId()).getId());
                            if(lineVisbility[0]) builder.setVisibilityLine1(true);
                            if(lineVisbility[1])  builder.setVisibilityLine2(true);
                            if(lineVisbility[2]) builder.setVisibilityLine3(true);

                            builder.setPositiveButton(R.string.flux_positive_button, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    //设置你的操作事项
                                    DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                                    dchApportFluxDB.open();
                                    DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                                    dchFluxDB.open();
                                    DchDepotDB dchDepotDB = new DchDepotDB(getContext());
                                    dchDepotDB.open();

                                    int fluxId = dchFluxDB.getFluxByIconId(currentIcon.getId()).getId();
                                    EditText editTextQuantiteApporte = builder.getEditTextQuantiteApporte();
                                    EditText editTextQuantiteDecompte = builder.getEditTextQuantiteDecompte();
                                    TextView textViewQuantiteCalculLine3 = builder.getTextViewQuantiteCalculLine3();
                                    float qtyApporte = -1;
                                    float qtyDecompte = -1;
                                    if(lineVisbility[0]){
                                        if(editTextQuantiteApporte.getText().toString().isEmpty()||editTextQuantiteApporte.getText().toString() ==""){
                                            qtyApporte = 0;
                                        }
                                        else {
                                            qtyApporte = Float.parseFloat(editTextQuantiteApporte.getText().toString());
                                        }
                                    }
                                    if(lineVisbility[1]){
                                        if(editTextQuantiteDecompte.getText().toString().isEmpty()||editTextQuantiteDecompte.getText().toString() ==""){
                                            qtyDecompte = 0;
                                        }
                                        else {
                                            qtyDecompte = Float.parseFloat(editTextQuantiteDecompte.getText().toString());
                                        }
                                    }
                                    if(lineVisbility[2]) {
                                        if (textViewQuantiteCalculLine3.getText().toString().isEmpty() || textViewQuantiteCalculLine3.getText().toString() == "") {
                                            qtyDecompte = 0;
                                        } else {
                                            qtyDecompte = Float.parseFloat(textViewQuantiteCalculLine3.getText().toString());
                                        }
                                    }
                                    ApportFlux apportFlux = new ApportFlux(depotId,fluxId,qtyApporte,qtyDecompte,false);
                                    dchApportFluxDB.insertApportFlux(apportFlux);

                                    //refresh the qtyTotal of this depot
                                    ArrayList<ApportFlux> listApportFlux = dchApportFluxDB.getListeApportFluxByDepotId(depotId);
                                    float qtyDecompteTotal = 0;
                                    for(ApportFlux af: listApportFlux){
                                        float qty = af.getQtyUDD();
                                        qtyDecompteTotal = qtyDecompteTotal + qty;
                                    }
                                    depot.setQtyTotalUDD(qtyDecompteTotal);
                                    dchDepotDB.updateDepot(depot);

                                    totalDecompte = qtyDecompteTotal;

                                    editTextVolumeTotal.     setText("" + qtyDecompteTotal);
                                    textViewUniteVolumeTotal.setText(nomUniteDecompte);

                                    dchApportFluxDB.close();
                                    dchFluxDB.close();
                                    dchDepotDB.close();

                                    closeKeyBoard();


                                }
                            });

                            builder.setNegativeButton("",
                                    new android.content.DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            DchFluxDB dchFluxDB = new DchFluxDB(getContext());
                                            dchFluxDB.open();
                                            DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                                            dchApportFluxDB.open();
                                            DchDepotDB dchDepotDB = new DchDepotDB(getContext());
                                            dchDepotDB.open();

                                            dialog.dismiss();
                                            galleryFluxChoisi.removeView(view);
                                            galleryFlux.addView(viewCopy);
                                            dchApportFluxDB.deleteApportFluxByDepotIdAndFluxId(depotId, dchFluxDB.getFluxByIconId(currentIcon.getId()).getId());

                                            //refresh the qtyTotal of this depot
                                            ArrayList<ApportFlux> listApportFlux = dchApportFluxDB.getListeApportFluxByDepotId(depotId);
                                            float qtyDecompteTotal = 0;
                                            for(ApportFlux af: listApportFlux){
                                                float qty = af.getQtyUDD();
                                                qtyDecompteTotal = qtyDecompteTotal + qty;
                                            }
                                            depot.setQtyTotalUDD(qtyDecompteTotal);
                                            dchDepotDB.updateDepot(depot);

                                            totalDecompte = qtyDecompteTotal;

                                            editTextVolumeTotal.setText("" + qtyDecompteTotal);
                                            textViewUniteVolumeTotal.setText(nomUniteDecompte);

                                            dchFluxDB.close();
                                            dchApportFluxDB.close();
                                            dchDepotDB.close();
                                        }
                                    });

                            builder.create().show();


                            dchApportFluxDB.close();
                            dchFluxDB.close();
                            dchUniteDB.close();

                        }
                    });


                    dchApportFluxDB.close();
                    dchFluxDB.close();
                    dchUniteDB.close();


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
        dchAccountFluxSettingDB.close();

        parentActivity.openDrawerWithDelay();

    }



    /*
    Init Listeners
     */
    public void initListeners(ViewGroup container) {
        Button btnValider = (Button) depot_vg.findViewById(R.id.depot_fragment_valider_button);
        parentActivity = (ContainerActivity ) getActivity();

        //set the listener of the button "valider"
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DchDepotDB dchDepotDB = new DchDepotDB(getContext());
                dchDepotDB.open();
                DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                dchApportFluxDB.open();

                //if the drawer is open then close it
                if(parentActivity.isDrawerOpen()){
                    parentActivity.closeDrawer();
                }

                Configuration.setIsOuiClicked(false);

                //detect the pageSignature
                if(pageSignature) {
                    if(isComeFromRechercherUsagerFragment){//RechercherUsagerFragment --->DepotFragment ---> ApportProFragment
                        if (getActivity() != null && getActivity() instanceof ContainerActivity) {
                            ApportProFragment apportProFragment = ApportProFragment.newInstance(depotId,usagerIdFromRUF,typeCarteIdFromRUF,isComeFromRechercherUsagerFragment,nomInND,isUsagerMenageInND,adresseInND,apportRestantInND,uniteApportRestantInND,totalDecompte,accountSetting.getId(),comptePrepaye.getId());
                            ((ContainerActivity) getActivity()).changeMainFragment(apportProFragment, true);
                        }
                    }
                    else{//IdentificationFragment ---> DepotFragment ---> ApportProFragment
                        if (getActivity() != null && getActivity() instanceof ContainerActivity) {
                            ApportProFragment apportProFragment = ApportProFragment.newInstance(depotId,nomInND,isUsagerMenageInND,adresseInND,numeroCarteInND,apportRestantInND,uniteApportRestantInND,totalDecompte,accountSetting.getId(),comptePrepaye.getId());
                            ((ContainerActivity) getActivity()).changeMainFragment(apportProFragment, true);
                        }
                    }
                }
                else{
                    //update the table "depot" and change the row "statut" to statut_termine
                    depot.setStatut(getResources().getInteger(R.integer.statut_termine));
                    depot.setDateHeure(getDateHeure());
                    dchDepotDB.updateDepot(depot);
                    //send the depot to server
                    if (Utils.isInternetConnected(getContext())) {
                        parentActivity.sendDepot(depot, accountSetting, dchApportFluxDB.getListeApportFluxByDepotId(depot.getId()));
                    }
                    //recalculate the comptePreapaye
                    recaculateComptePrepaye();

                    if (getActivity() != null && getActivity() instanceof ContainerActivity) {
                        ((ContainerActivity) getActivity()).changeMainFragment(new AccueilFragment(), false);
                    }

                }


                dchDepotDB.close();
                dchApportFluxDB.close();

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

    public static DepotFragment newInstance(boolean isComeFromPopUp) {
        DepotFragment depotFragment = new DepotFragment();
        Bundle args = new Bundle();
        args.putBoolean  (   "isComeFromPopUp",                         isComeFromPopUp                         );
        depotFragment.setArguments(args);
        return depotFragment;
    }

    public static DepotFragment newInstance(String numCarte, boolean isComeFromIdentificationFragment) {
        DepotFragment depotFragment = new DepotFragment();
        Bundle args = new Bundle();
        args.putString  (   "numCarte",                                 numCarte                                );
        args.putBoolean (   "isComeFromIdentificationFragment",         isComeFromIdentificationFragment        );
        depotFragment.setArguments(args);
        return depotFragment;
    }

    public static DepotFragment newInstance(int usagerId, int typeCarteId, boolean isComeFromRechercherUsagerFragment) {
        DepotFragment depotFragment = new DepotFragment();
        Bundle args = new Bundle();
        args.putInt     (   "usagerIdFromRechercherUsagerFragment",     usagerId                                );
        args.putInt     (   "typeCarteIdFromRechercherUsagerFragment",  typeCarteId                             );
        args.putBoolean (   "isComeFromRechercherUsagerFragment",       isComeFromRechercherUsagerFragment      );
        depotFragment.setArguments(args);
        return depotFragment;
    }

    public void getNumCarteFromIdentificationFragment(){
        if (getArguments() != null) {
            DchCarteDB dchCarteDB = new DchCarteDB(getContext());
            dchCarteDB.open();

            String numCarte = getArguments().getString("numCarte");
            if(numCarte != null) {
                Toast.makeText(getContext(), "numCarte: " + numCarte,
                        Toast.LENGTH_SHORT).show();
                carte = dchCarteDB.getCarteByNumCarteAndAccountId(numCarte, Configuration.getIdAccount());
                setPageSignature();
            }

            dchCarteDB.close();
        }
    }


    public void setPageSignature(){
        DchAccountSettingDB dchAccountSettingDB = new DchAccountSettingDB(getContext());
        dchAccountSettingDB.open();

        if(carte != null){
            int typeCarteId = carte.getDchTypeCarteId();
            int accountId = Configuration.getIdAccount();
            Date d = new Date();
            SimpleDateFormat df = new SimpleDateFormat(getString(R.string.normal_date_format));
            int date = Integer.parseInt(df.format(d));
            ArrayList<AccountSetting> accountSettingList = dchAccountSettingDB.getListeAccountSettingByAccountIdAndTypeCarteId(accountId, typeCarteId);
            if (accountSettingList != null) {
                for (AccountSetting as : accountSettingList) {
                    int dateDebut = Integer.parseInt(as.getDateDebutParam());
                    int dateFin = Integer.parseInt(as.getDateFinParam());
                    if (date >= dateDebut && date <= dateFin) {
                        accountSetting = as;
                        pageSignature  = accountSetting.isPageSignature();
                    }

                }
            }
        }
        else{
            //set the object accountSetting
            Date d = new Date();
            SimpleDateFormat df = new SimpleDateFormat(getString(R.string.normal_date_format));
            int date = Integer.parseInt(df.format(d));
            ArrayList<AccountSetting> accountSettingList = dchAccountSettingDB.getListeAccountSettingByAccountIdAndTypeCarteId(Configuration.getIdAccount(), typeCarteIdFromRUF);
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

    public void setParametersFromRUF(){
        int       usagerId                              = getArguments().getInt     (   "usagerIdFromRechercherUsagerFragment"      );
        int       typeCarteId                           = getArguments().getInt     (   "typeCarteIdFromRechercherUsagerFragment"   );

        this.usagerIdFromRUF                        =    usagerId;
        this.typeCarteIdFromRUF                     =    typeCarteId;

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

    public String getDateHeure(){
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat(getString(R.string.db_date_format));
        String dateHeure = df.format(d);

        return dateHeure;
    }

    /*//check if all the convert_comptage_pr_UDD of each flux equals to 0
    public boolean checkCCPU(){
        DecheterieDB decheterieDB = new DecheterieDB(getContext());
        decheterieDB.open();
        DchDecheterieFluxDB dchDecheterieFluxDB = new DchDecheterieFluxDB(getContext());
        dchDecheterieFluxDB.open();
        DchAccountFluxSettingDB dchAccountFluxSettingDB = new DchAccountFluxSettingDB(getContext());
        dchAccountFluxSettingDB.open();
        DchUniteDB dchUniteDB = new DchUniteDB(getContext());
        dchUniteDB.open();

        boolean result = true;

        Decheterie decheterie = decheterieDB.getDecheterieByName(Configuration.getNameDecheterie());
        ArrayList<Flux> fluxList = dchDecheterieFluxDB.getAllFluxByDecheterieId(decheterie.getId(), getContext());
        for(Flux flux: fluxList){
            AccountFluxSetting afs = dchAccountFluxSettingDB.getAccountFluxSettingByAccountSettingIdAndFluxId(accountSetting.getId(), flux.getId());
            if(afs != null && afs.getConvertComptagePrUDD() == 0){
                result = false;
            }
        }

        decheterieDB.close();
        dchDecheterieFluxDB.close();
        dchAccountFluxSettingDB.close();

        if(!result){
            nomUniteDecompte = dchUniteDB.getUniteFromID(accountSetting.getUniteDepotDecheterieId()).getNom();
        }

        dchUniteDB.close();
        return result;

    }*/

    //check the accountFluxSetting of each flux, return a table of boolean: [line1, line2, line3]
    public boolean[] checkAFS(int fluxId){
        openAllDB();
        boolean[] lineVisibility = new boolean[3];
        AccountFluxSetting afs = dchAccountFluxSettingDB.getAccountFluxSettingByAccountSettingIdAndFluxId(accountSetting.getId(), fluxId);

        if(afs.isDecompteComptage()){
            //+line1
            lineVisibility[0] = true;
            if(afs.getConvertComptagePrUDD() > 0 && afs.isDecompteUDD()){
                //+line3
                lineVisibility[2] = true;
            }
        }

        if (afs.isDecompteUDD() && afs.getConvertComptagePrUDD() == 0)
            //+line2
            lineVisibility[1] = true;

        closeAllDB();
        return lineVisibility;
    }

    public void initViewsNavigationDrawer(LayoutInflater inflater, ViewGroup container){
        ndLinearLayoutLine1     = (LinearLayout)    parentActivity.findViewById(R.id.linearLayout_line1);
        ndLinearLayoutLine2     = (LinearLayout)    parentActivity.findViewById(R.id.linearLayout_line2);
        ndLinearLayoutLine3     = (LinearLayout)    parentActivity.findViewById(R.id.linearLayout_line3);
        ndLinearLayoutLine4     = (LinearLayout)    parentActivity.findViewById(R.id.linearLayout_line4);
        ndLinearLayoutLine5     = (LinearLayout)    parentActivity.findViewById(R.id.linearLayout_line5);
        ndLinearLayoutLine6     = (LinearLayout)    parentActivity.findViewById(R.id.linearLayout_line6);
        ndLinearLayoutLine7     = (LinearLayout)    parentActivity.findViewById(R.id.linearLayout_line7);
        ndTextViewLine1Title    = (TextView)        parentActivity.findViewById(R.id.textView_line1_title);
        ndTextViewLine2Title    = (TextView)        parentActivity.findViewById(R.id.textView_line2_title);
        ndTextViewLine3Title    = (TextView)        parentActivity.findViewById(R.id.textView_line3_title);
        ndTextViewLine4Title    = (TextView)        parentActivity.findViewById(R.id.textView_line4_title);
        ndTextViewLine5Title    = (TextView)        parentActivity.findViewById(R.id.textView_line5_title);
        ndTextViewLine6Title    = (TextView)        parentActivity.findViewById(R.id.textView_line6_title);
        ndTextViewLine7Title    = (TextView)        parentActivity.findViewById(R.id.textView_line7_title);
        ndTextViewLine1Value    = (TextView)        parentActivity.findViewById(R.id.textView_line1_value);
        ndTextViewLine2Value    = (TextView)        parentActivity.findViewById(R.id.textView_line2_value);
        ndTextViewLine3Value    = (TextView)        parentActivity.findViewById(R.id.textView_line3_value);
        ndTextViewLine4Value    = (TextView)        parentActivity.findViewById(R.id.textView_line4_value);
        ndTextViewLine5Value    = (TextView)        parentActivity.findViewById(R.id.textView_line5_value);
        ndTextViewLine6Value    = (TextView)        parentActivity.findViewById(R.id.textView_line6_value);
        ndTextViewLine7Value    = (TextView)        parentActivity.findViewById(R.id.textView_line7_value);


        if(carte != null){
            CarteActive carteActive = dchCarteActiveDB.getCarteActiveFromDchCarteId(carte.getId());
            ComptePrepaye comptePrepaye = dchComptePrepayeDB.getComptePrepayeFromID(carteActive.getDchComptePrepayeId());
            Usager usager = usagerDB.getUsagerFromID(comptePrepaye.getDchUsagerId());
            ArrayList<UsagerHabitat> usagerHabitatList = usagerHabitatDB.getListUsagerHabitatByUsagerId(usager.getId());

            //save the comptePrepayeId into DB
            depot.setComptePrepayeId(carteActive.getDchComptePrepayeId());
            dchDepotDB.updateDepot(depot);

            //case 1
            if(usagerHabitatList.size() != 0){
                Habitat habitat = new Habitat();
                //find the habitat who is actif
                for(UsagerHabitat usagerHabitat: usagerHabitatList ){
                    Habitat h = habitatDB.getHabitatFromID(usagerHabitat.getHabitatId());
                    if(h.isActif()){
                        habitat = h;
                        break;
                    }
                }
                if(habitat.isActif()) {
                    ndLinearLayoutLine2.setVisibility(View.VISIBLE);
                    ndLinearLayoutLine3.setVisibility(View.VISIBLE);
                    ndLinearLayoutLine4.setVisibility(View.VISIBLE);
                    ndLinearLayoutLine5.setVisibility(View.VISIBLE);
                    ndLinearLayoutLine6.setVisibility(View.VISIBLE);
                    ndLinearLayoutLine7.setVisibility(View.VISIBLE);
                    ndTextViewLine1Title.setText(R.string.text_nom);
                    String nom = usager.getNom();
                    nomInND = nom;
                    ndTextViewLine1Value.setText((nom == null || nom.isEmpty())? "-" : nom);
                    ndLinearLayoutLine2.setVisibility(View.GONE);
                    ndTextViewLine3Title.setText(R.string.text_type_usager);
                    String type = typeHabitatDB.getTypeHabitatFromID(habitat.getIdTypeHabitat()).getType();
                    ndTextViewLine3Value.setText((type == null || type.isEmpty())? "-" : type);
                    ndTextViewLine4Title.setText(R.string.text_adresse);
                    adresseInND = (habitat.getNumero() == null ? "" : habitat.getNumero() + " ") + (habitat.getComplement() == null ? "" : habitat.getComplement() + " ") + (habitat.getAdresse() == null ? "" : habitat.getAdresse()) + "\n"
                            + (habitat.getCp() == null ? "" : habitat.getCp() + ", ") + (habitat.getVille() == null ? "" : habitat.getVille());
                    ndTextViewLine4Value.setText((habitat.getNumero() == null ? "" : habitat.getNumero() + " ") + (habitat.getComplement() == null ? "" : habitat.getComplement() + " ") + (habitat.getAdresse() == null ? "" : habitat.getAdresse()) + "\n"
                            + (habitat.getCp() == null ? "" : habitat.getCp() + ", ") + (habitat.getVille() == null ? "" : habitat.getVille()));
                    ndTextViewLine5Title.setText(R.string.text_carte);
                    String typeCarte = dchTypeCarteDB.getTypeCarteFromID(carte.getDchTypeCarteId()).getNom();
                    String numCarte = carte.getNumCarte();
                    numeroCarteInND = numCarte;
                    ndTextViewLine5Value.setText(( (typeCarte == null || typeCarte.isEmpty()) ? "-\n" : typeCarte + "\n")
                                                + ((numCarte == null || numCarte.isEmpty()) ? "N° -" : "N° " + numCarte));
                    if (accountSetting.isDecompteDepot()) {
                        ndTextViewLine6Title.setText(R.string.text_nb_depot_restant);
                        ndTextViewLine6Value.setText(comptePrepaye.getNbDepotRestant() + "");
                    } else {
                        ndLinearLayoutLine6.setVisibility(View.GONE);
                    }
                    if (accountSetting.isDecompteUDD()) {
                        ndTextViewLine7Title.setText(R.string.text_apport_restant);
                        String unitePoint = accountSetting.getUnitePoint();
                        apportRestantInND = comptePrepaye.getQtyPoint();
                        uniteApportRestantInND = unitePoint;
                        ndTextViewLine7Value.setText(comptePrepaye.getQtyPoint() + "" + ((unitePoint == null || unitePoint.isEmpty()) ? " -" : " " + unitePoint));
                    } else {
                        ndLinearLayoutLine7.setVisibility(View.GONE);
                    }
                }
            }
            //case 2
            else {
                ArrayList<UsagerMenage> usagerMenageList = usagerMenageDB.getListUsagerMenageByUsagerId(usager.getId());
                if(usagerMenageList.size() != 0){
                    Habitat habitat = new Habitat();
                    Menage menage = new Menage();
                    Local local = new Local();
                    //find the menage actif then the habitat actif
                    for(UsagerMenage usagerMenage: usagerMenageList ){
                        Menage m = menageDB.getMenageById(usagerMenage.getMenageId());
                        if(m.isActif()){
                            menage = m;
                            Local l = localDB.getLocalById(m.getLocalId());
                            Habitat h = habitatDB.getHabitatFromID(l.getHabitatId());
                            if(h.isActif()){
                                habitat = h;
                                local = l;
                            }
                            break;
                        }

                    }
                    if(menage.isActif()) {
                        ndLinearLayoutLine2.setVisibility(View.VISIBLE);
                        ndLinearLayoutLine3.setVisibility(View.VISIBLE);
                        ndLinearLayoutLine4.setVisibility(View.VISIBLE);
                        ndLinearLayoutLine5.setVisibility(View.VISIBLE);
                        ndLinearLayoutLine6.setVisibility(View.VISIBLE);
                        ndLinearLayoutLine7.setVisibility(View.VISIBLE);
                        ndTextViewLine1Title.setText(R.string.text_nom);
                        String nom = usager.getNom();
                        nomInND = nom;
                        ndTextViewLine1Value.setText((nom == null || nom.isEmpty())? "-" : nom);
                        ndTextViewLine2Title.setText(R.string.text_prenom);
                        String prenom = usager.getPrenom();
                        isUsagerMenageInND = true;
                        ndTextViewLine2Value.setText((prenom == null || prenom.isEmpty())? "-" : prenom);
                        ndTextViewLine3Title.setText(R.string.text_type_usager);
                        ndTextViewLine3Value.setText(R.string.text_particulier);
                        if(habitat.isActif()) {
                            ndTextViewLine4Title.setText(R.string.text_adresse);
                            adresseInND = (habitat.getNumero() == null ? "" : habitat.getNumero() + " ") + (habitat.getComplement() == null ? "" : habitat.getComplement() + " ") + (habitat.getAdresse() == null ? "" : habitat.getAdresse()) + "\n"
                                    + (habitat.getCp() == null ? "" : habitat.getCp() + ", ") + (habitat.getVille() == null ? "" : habitat.getVille());
                            ndTextViewLine4Value.setText((habitat.getNumero() == null ? "" : habitat.getNumero() + " ") + (habitat.getComplement() == null ? "" : habitat.getComplement() + " ") + (habitat.getAdresse() == null ? "" : habitat.getAdresse()) + "\n"
                                    + (habitat.getCp() == null ? "" : habitat.getCp() + ", ") + (habitat.getVille() == null ? "" : habitat.getVille()));
                        }
                        else{
                            ndLinearLayoutLine4.setVisibility(View.GONE);
                        }
                        ndTextViewLine5Title.setText(R.string.text_carte);
                        String typeCarte = dchTypeCarteDB.getTypeCarteFromID(carte.getDchTypeCarteId()).getNom();
                        String numCarte = carte.getNumCarte();
                        numeroCarteInND = numCarte;
                        ndTextViewLine5Value.setText(( (typeCarte == null || typeCarte.isEmpty()) ? "-\n" : typeCarte + "\n")
                                + ((numCarte == null || numCarte.isEmpty()) ? "N° -" : "N° " + numCarte));
                        if (accountSetting.isDecompteDepot()) {
                            ndTextViewLine6Title.setText(R.string.text_nb_depot_restant);
                            ndTextViewLine6Value.setText(comptePrepaye.getNbDepotRestant() + "");
                        } else {
                            ndLinearLayoutLine6.setVisibility(View.GONE);
                        }
                        if (accountSetting.isDecompteUDD()) {
                            ndTextViewLine7Title.setText(R.string.text_apport_restant);
                            String unitePoint = accountSetting.getUnitePoint();
                            apportRestantInND = comptePrepaye.getQtyPoint();
                            uniteApportRestantInND = unitePoint;
                            ndTextViewLine7Value.setText(comptePrepaye.getQtyPoint() + "" + ((unitePoint == null || unitePoint.isEmpty()) ? " -" : " " + unitePoint));
                        } else {
                            ndLinearLayoutLine7.setVisibility(View.GONE);
                        }
                    }
                }
                //case 3
                else{
                    ndLinearLayoutLine2.setVisibility(View.GONE);
                    ndLinearLayoutLine3.setVisibility(View.GONE);
                    ndLinearLayoutLine4.setVisibility(View.GONE);
                    ndLinearLayoutLine5.setVisibility(View.GONE);
                    ndLinearLayoutLine6.setVisibility(View.GONE);
                    ndLinearLayoutLine7.setVisibility(View.GONE);
                    ndTextViewLine1Title.setText(R.string.text_nom);
                    ndTextViewLine1Value.setText(usager.getNom());
                }
            }
        }
        //RechercherUsagerFragment ---> DepotFragment
        else{
            ndLinearLayoutLine2.setVisibility(View.VISIBLE);
            ndLinearLayoutLine3.setVisibility(View.VISIBLE);
            ndLinearLayoutLine4.setVisibility(View.VISIBLE);
            ndLinearLayoutLine5.setVisibility(View.VISIBLE);
            ndLinearLayoutLine6.setVisibility(View.VISIBLE);
            ndLinearLayoutLine7.setVisibility(View.VISIBLE);

            //get the latest ComptePrepaye of the usager
            ComptePrepaye               comptePrepaye       = dchComptePrepayeDB.getComptePrepayeFromUsagerId(usagerIdFromRUF);
            Usager                      usager              = usagerDB.getUsagerFromID(usagerIdFromRUF);
            ArrayList<UsagerHabitat>    usagerHabitatList   = usagerHabitatDB.getListUsagerHabitatByUsagerId(usager.getId());

            //save the comptePrepayeId into DB
            depot.setComptePrepayeId(comptePrepaye.getId());
            dchDepotDB.updateDepot(depot);

            //case 1
            if(usagerHabitatList.size() != 0){
                Habitat habitat = new Habitat();
                //find the habitat who is actif
                for(UsagerHabitat usagerHabitat: usagerHabitatList ){
                    Habitat h = habitatDB.getHabitatFromID(usagerHabitat.getHabitatId());
                    if(h.isActif()){
                        habitat = h;
                        break;
                    }
                }
                if(habitat.isActif()) {
                    ndLinearLayoutLine2.setVisibility(View.VISIBLE);
                    ndLinearLayoutLine3.setVisibility(View.VISIBLE);
                    ndLinearLayoutLine4.setVisibility(View.VISIBLE);
                    ndLinearLayoutLine5.setVisibility(View.VISIBLE);
                    ndLinearLayoutLine6.setVisibility(View.VISIBLE);
                    ndLinearLayoutLine7.setVisibility(View.VISIBLE);
                    ndTextViewLine1Title.setText(R.string.text_nom);
                    String nom = usager.getNom();
                    nomInND = nom;
                    ndTextViewLine1Value.setText((nom == null || nom.isEmpty())? "-" : nom);
                    ndLinearLayoutLine2.setVisibility(View.GONE);
                    ndTextViewLine3Title.setText(R.string.text_type_usager);
                    String type = typeHabitatDB.getTypeHabitatFromID(habitat.getIdTypeHabitat()).getType();
                    ndTextViewLine3Value.setText((type == null || type.isEmpty())? "-" : type);
                    ndTextViewLine4Title.setText(R.string.text_adresse);
                    adresseInND = (habitat.getNumero() == null ? "" : habitat.getNumero() + " ") + (habitat.getComplement() == null ? "" : habitat.getComplement() + " ") + (habitat.getAdresse() == null ? "" : habitat.getAdresse()) + "\n"
                            + (habitat.getCp() == null ? "" : habitat.getCp() + ", ") + (habitat.getVille() == null ? "" : habitat.getVille());
                    ndTextViewLine4Value.setText((habitat.getNumero() == null ? "" : habitat.getNumero() + " ") + (habitat.getComplement() == null ? "" : habitat.getComplement() + " ") + (habitat.getAdresse() == null ? "" : habitat.getAdresse()) + "\n"
                            + (habitat.getCp() == null ? "" : habitat.getCp() + ", ") + (habitat.getVille() == null ? "" : habitat.getVille()));
                    ndTextViewLine5Title.setText(R.string.text_carte);
                    String typeCarte = dchTypeCarteDB.getTypeCarteFromID(typeCarteIdFromRUF).getNom();
                    String numCarte = "";
                    ArrayList<CarteActive> carteActiveList = dchCarteActiveDB.getCarteActiveListByComptePrepayeId(comptePrepaye.getId());
                    for(CarteActive ca: carteActiveList){
                        if(ca.isActive()){
                            Carte carte = dchCarteDB.getCarteFromID(ca.getDchCarteId());
                            if(numCarte.isEmpty()||numCarte==null) {
                                numCarte = "N° " + carte.getNumCarte() + "   active";
                            }
                            else{
                                numCarte = numCarte + "\n" + "N° " + carte.getNumCarte() + "   active";
                            }
                        }
                    }
                    ndTextViewLine5Value.setText(( (typeCarte == null || typeCarte.isEmpty()) ? "-\n" : typeCarte + "\n")
                            + ((numCarte == null || numCarte.isEmpty()) ? "N° -" : numCarte));
                    if (accountSetting.isDecompteDepot()) {
                        ndTextViewLine6Title.setText(R.string.text_nb_depot_restant);
                        ndTextViewLine6Value.setText(comptePrepaye.getNbDepotRestant() + "");
                    } else {
                        ndLinearLayoutLine6.setVisibility(View.GONE);
                    }
                    if (accountSetting.isDecompteUDD()) {
                        ndTextViewLine7Title.setText(R.string.text_apport_restant);
                        String unitePoint = accountSetting.getUnitePoint();
                        apportRestantInND = comptePrepaye.getQtyPoint();
                        uniteApportRestantInND = unitePoint;
                        ndTextViewLine7Value.setText(comptePrepaye.getQtyPoint() + "" + ((unitePoint == null || unitePoint.isEmpty()) ? " -" : " " + unitePoint));
                    } else {
                        ndLinearLayoutLine7.setVisibility(View.GONE);
                    }
                }
            }
            //case 2
            else {
                ArrayList<UsagerMenage> usagerMenageList = usagerMenageDB.getListUsagerMenageByUsagerId(usager.getId());
                if(usagerMenageList.size() != 0){
                    Habitat habitat = new Habitat();
                    Menage menage = new Menage();
                    Local local = new Local();
                    //find the menage actif then the habitat actif
                    for(UsagerMenage usagerMenage: usagerMenageList ){
                        Menage m = menageDB.getMenageById(usagerMenage.getMenageId());
                        if(m.isActif()){
                            menage = m;
                            Local l = localDB.getLocalById(m.getLocalId());
                            Habitat h = habitatDB.getHabitatFromID(l.getHabitatId());
                            if(h.isActif()){
                                habitat = h;
                                local = l;
                            }
                            break;
                        }

                    }
                    if(menage.isActif()) {
                        ndLinearLayoutLine2.setVisibility(View.VISIBLE);
                        ndLinearLayoutLine3.setVisibility(View.VISIBLE);
                        ndLinearLayoutLine4.setVisibility(View.VISIBLE);
                        ndLinearLayoutLine5.setVisibility(View.VISIBLE);
                        ndLinearLayoutLine6.setVisibility(View.VISIBLE);
                        ndLinearLayoutLine7.setVisibility(View.VISIBLE);
                        ndTextViewLine1Title.setText(R.string.text_nom);
                        String nom = usager.getNom();
                        nomInND = nom;
                        ndTextViewLine1Value.setText((nom == null || nom.isEmpty())? "-" : nom);
                        ndTextViewLine2Title.setText(R.string.text_prenom);
                        String prenom = usager.getPrenom();
                        isUsagerMenageInND = true;
                        ndTextViewLine2Value.setText((prenom == null || prenom.isEmpty())? "-" : prenom);
                        ndTextViewLine3Title.setText(R.string.text_type_usager);
                        ndTextViewLine3Value.setText(R.string.text_particulier);
                        if(habitat.isActif()) {
                            ndTextViewLine4Title.setText(R.string.text_adresse);
                            adresseInND = (habitat.getNumero() == null ? "" : habitat.getNumero() + " ") + (habitat.getComplement() == null ? "" : habitat.getComplement() + " ") + (habitat.getAdresse() == null ? "" : habitat.getAdresse()) + "\n"
                                    + (habitat.getCp() == null ? "" : habitat.getCp() + ", ") + (habitat.getVille() == null ? "" : habitat.getVille());
                            ndTextViewLine4Value.setText((habitat.getNumero() == null ? "" : habitat.getNumero() + " ") + (habitat.getComplement() == null ? "" : habitat.getComplement() + " ") + (habitat.getAdresse() == null ? "" : habitat.getAdresse()) + "\n"
                                    + (habitat.getCp() == null ? "" : habitat.getCp() + ", ") + (habitat.getVille() == null ? "" : habitat.getVille()));
                        }
                        else{
                            ndLinearLayoutLine4.setVisibility(View.GONE);
                        }
                        ndTextViewLine5Title.setText(R.string.text_carte);
                        String typeCarte = dchTypeCarteDB.getTypeCarteFromID(typeCarteIdFromRUF).getNom();
                        String numCarte = "";
                        ArrayList<CarteActive> carteActiveList = dchCarteActiveDB.getCarteActiveListByComptePrepayeId(comptePrepaye.getId());
                        for(CarteActive ca: carteActiveList){
                            if(ca.isActive()){
                                Carte carte = dchCarteDB.getCarteFromID(ca.getDchCarteId());
                                if(numCarte.isEmpty()||numCarte==null) {
                                    numCarte = "N° " + carte.getNumCarte() + "   active";
                                }
                                else{
                                    numCarte = numCarte + "\n" + "N° " + carte.getNumCarte() + "   active";
                                }
                            }
                        }
                        ndTextViewLine5Value.setText(( (typeCarte == null || typeCarte.isEmpty()) ? "-\n" : typeCarte + "\n")
                                + ((numCarte == null || numCarte.isEmpty()) ? "N° -" : numCarte));
                        if (accountSetting.isDecompteDepot()) {
                            ndTextViewLine6Title.setText(R.string.text_nb_depot_restant);
                            ndTextViewLine6Value.setText(comptePrepaye.getNbDepotRestant() + "");
                        } else {
                            ndLinearLayoutLine6.setVisibility(View.GONE);
                        }
                        if (accountSetting.isDecompteUDD()) {
                            ndTextViewLine7Title.setText(R.string.text_apport_restant);
                            String unitePoint = accountSetting.getUnitePoint();
                            apportRestantInND = comptePrepaye.getQtyPoint();
                            uniteApportRestantInND = unitePoint;
                            ndTextViewLine7Value.setText(comptePrepaye.getQtyPoint() + "" + ((unitePoint == null || unitePoint.isEmpty()) ? " -" : " " + unitePoint));
                        } else {
                            ndLinearLayoutLine7.setVisibility(View.GONE);
                        }
                    }
                }
                //case 3
                else{
                    ndLinearLayoutLine2.setVisibility(View.GONE);
                    ndLinearLayoutLine3.setVisibility(View.GONE);
                    ndLinearLayoutLine4.setVisibility(View.GONE);
                    ndLinearLayoutLine5.setVisibility(View.GONE);
                    ndLinearLayoutLine6.setVisibility(View.GONE);
                    ndLinearLayoutLine7.setVisibility(View.GONE);
                    ndTextViewLine1Title.setText(R.string.text_nom);
                    ndTextViewLine1Value.setText(usager.getNom());
                }
            }
        }


    }

    public void initAllIsComeFrom(){
        if (getArguments() != null && getArguments().getBoolean("isComeFromApportProFragment")){
            this.isComeFromApportProFragment        = true;
        }
        if (getArguments() != null && getArguments().getBoolean("isComeFromRechercherUsagerFragment")){
            this.isComeFromRechercherUsagerFragment = true;
        }
        if (getArguments() != null && getArguments().getBoolean("isComeFromPopUp")){
            this.isComeFromPopUp                    = true;
        }
        if (getArguments() != null && getArguments().getBoolean("isComeFromIdentificationFragment")){
            this.isComeFromIdentificationFragment   = true;
        }
    }


    /*public void sendDepot(Depot d, AccountSetting a, ArrayList<ApportFlux> listAF){
        try {
            //send Depot(without signature) to server
            Datas.uploadDepot(getContext(), new DataCallback<ContenantBean>() {
                @Override
                public void dataLoaded(ContenantBean data) {
                    if (!data.ismSuccess()) {
                        data.getmError();
                    }
                    else{
                        depot.setSent(true);

                        try {
                            DchDepotDB dchDepotDB = new DchDepotDB(getContext());
                            dchDepotDB.open();

                            dchDepotDB.updateDepot(depot);

                            dchDepotDB.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, d, a, listAF);
        } catch(Exception e){
            e.printStackTrace();
        }
    }*/

    private void addTextChangedListener(final EditText editText){
        TextWatcher listener3 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //update the depot in BD
                DchDepotDB dchDepotDB = new DchDepotDB(getContext());
                dchDepotDB.open();

                float vt;
                if(editText.getText().toString().isEmpty() || editText.getText().toString() == null || editText.getText().toString().equals(".")){
                    vt = 0;
                }
                else{
                    vt = Float.parseFloat(editText.getText().toString());
                }
                depot.setQtyTotalUDD(vt);
                dchDepotDB.updateDepot(depot);
                totalDecompte = vt;

                dchDepotDB.close();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        editText.addTextChangedListener(listener3);
    }

    //keyBoard open/close listener
    public void addKeyBoardListener(){
        keyboardListener = new SoftKeyboardStateWatcher.SoftKeyboardStateListener() {
            @Override
            public void onSoftKeyboardOpened(int keyboardHeightInPx) {

            }

            @Override
            public void onSoftKeyboardClosed() {
                if(parentActivity.getCurrentFragment() instanceof DepotFragment ) {

                    if (editTextVolumeTotal != null) {
                        if (editTextVolumeTotal.getText().toString().isEmpty() || editTextVolumeTotal.getText().toString() == null || editTextVolumeTotal.getText().toString().equals(".")) {
                            editTextVolumeTotal.setText(R.string.depot_fragment_init_volume_total_editText_text);
                        } else {
                            float vt = Float.parseFloat(editTextVolumeTotal.getText().toString());
                            editTextVolumeTotal.setText(vt + "");
                        }
                        editTextVolumeTotal.clearFocus();
                    }

                }

            }
        };

        // Add listener
        softKeyboardStateWatcher.addSoftKeyboardStateListener(keyboardListener);
    }

    public void closeKeyBoard(){

        editTextVolumeTotal.clearFocus();

        View view = parentActivity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)parentActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    public void showReturnAccueilDialog(){
        AccountSetting a = accountSetting;
        ComptePrepaye  c;

        //init c
        if(carte != null) {
            long carteActiveCarteId = carte.getId();
            long comptePrepayeId = dchCarteActiveDB.getCarteActiveFromDchCarteId(carteActiveCarteId).getDchComptePrepayeId();
            c = dchComptePrepayeDB.getComptePrepayeFromID(comptePrepayeId);
        }
        else{//RechercherUsagerFragment ---> DepotFragment
            c = dchComptePrepayeDB.getComptePrepayeFromUsagerId(usagerIdFromRUF);
        }

        if(a.getPointMinimum() >= 0 && c.getQtyPoint() < a.getPointMinimum()){
            depot.setStatut(getResources().getInteger(R.integer.statut_annuler));
            dchDepotDB.updateDepot(depot);
            //show dialog and return to accueilFragment
            ReturnAccueilDialog().show();
        }

        if(a.getNbDepotMinimum() >= 0 && c.getNbDepotRestant() < a.getNbDepotMinimum()){
            depot.setStatut(getResources().getInteger(R.integer.statut_annuler));
            dchDepotDB.updateDepot(depot);
            //show dialog and return to accueilFragment
            ReturnAccueilDialog().show();
        }

    }

    public CustomDialogFlux ReturnAccueilDialog(){
        CustomDialogReturnAccueil.Builder builder = new CustomDialogReturnAccueil.Builder(getContext());
        builder.setMessage(getResources().getString(R.string.pop_up_return_to_accueil_message));
        builder.setTitle(getResources().getString(R.string.pop_up_return_to_accueil_title));
        builder.setPositiveButton(null, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton(getResources().getString(R.string.pop_up_return_to_accueil_negative_button), new android.content.DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                parentActivity.changeMainFragment(new AccueilFragment(), true);
                dialog.dismiss();
            }
        });
        builder.setWidth(50);//the percentage of the tablette's width
        builder.setHeight(60);
        builder.setWidthScale(23);
        builder.setCancelableTouchOutside(false);
        builder.setCancelable(false);

        return builder.create();
    }

    public void recaculateComptePrepaye(){

        if(accountSetting.isDecompteDepot()){
            comptePrepaye.setNbDepotRestant(comptePrepaye.getNbDepotRestant() - 1);
        }
        if(accountSetting.isDecompteUDD()){
            comptePrepaye.setQtyPoint(comptePrepaye.getQtyPoint() - depot.getQtyTotalUDD()/accountSetting.getCoutUDDPrPoint());
        }

        DchComptePrepayeDB dchComptePrepayeDB = new DchComptePrepayeDB(getContext());
        dchComptePrepayeDB.open();
        dchComptePrepayeDB.updateComptePrepaye(comptePrepaye);
        dchComptePrepayeDB.close();
    }

    public void initEidtTextVolumeTotal(){
        if(accountSetting.isCompteTotal() && accountSetting.isDecompteUDD()) {// if compteTotal and decompteUDD both are true, the volume total could be editable
            editTextVolumeTotal.setBackgroundResource(R.drawable.bg_edittext);
        }
        else{
            editTextVolumeTotal.setKeyListener(null);
        }
    }

    public void setVisibilityViewOfVolumeTotal(){
        boolean volumeTotalVisibility = accountSetting.isDecompteUDD();
        if(volumeTotalVisibility){
            linearLayoutVolumeTotal.setVisibility(View.VISIBLE);
        }
        else{//hide the view of "volume total"
            linearLayoutVolumeTotal.setVisibility(View.GONE);
        }
    }


    public long getDepotId() {
        return depotId;
    }

    public void reInitializeAllIsComeFrom(){
        isComeFromPopUp                  = false;
        isComeFromApportProFragment      = false;
        isComeFromIdentificationFragment = false;
        isComeFromSettingFragment        = false;
    }

    public void setComeFromApportProFragment(boolean comeFromApportProFragment) {
        isComeFromApportProFragment = comeFromApportProFragment;
    }

    public void setComeFromSettingFragment(boolean isComeFromSettingFragment) {
        this.isComeFromSettingFragment = isComeFromSettingFragment;
    }

    /*private DchAccountFluxSettingDB dchAccountFluxSettingDB;
    private DchAccountSettingDB dchAccountSettingDB;
    private DchApportFluxDB dchApportFluxDB;
    private DchCarteActiveDB dchCarteActiveDB;
    private DchCarteDB dchCarteDB;
    private DchCarteEtatRaisonDB dchCarteEtatRaisonDB;
    private DchChoixDecompteTotalDB dchChoixDecompteTotalDB;
    private DchComptePrepayeDB dchComptePrepayeDB;
    private DchDecheterieFluxDB dchDecheterieFluxDB;
    private DchDepotDB dchDepotDB;
    private DchFluxDB dchFluxDB;
    private DchTypeCarteDB dchTypeCarteDB;
    private DchUniteDB dchUniteDB;
    private DecheterieDB decheterieDB;
    private HabitatDB habitatDB;
    private IconDB iconDB;
    private LocalDB localDB;
    private MenageDB menageDB;
    private ModulesDB modulesDB;
    private UsagerDB usagerDB;
    private UsagerHabitatDB usagerHabitatDB;
    private UsagerMenageDB usagerMenageDB;
    private TypeHabitatDB typeHabitatDB;*/

    public void initAllDB(){
        dchAccountFluxSettingDB = new DchAccountFluxSettingDB(getContext());
        dchAccountSettingDB = new DchAccountSettingDB(getContext());
        dchApportFluxDB = new DchApportFluxDB(getContext());
        dchCarteActiveDB = new DchCarteActiveDB(getContext());
        dchCarteDB = new DchCarteDB(getContext());
        dchCarteEtatRaisonDB = new DchCarteEtatRaisonDB(getContext());
        dchChoixDecompteTotalDB = new DchChoixDecompteTotalDB(getContext());
        dchComptePrepayeDB = new DchComptePrepayeDB(getContext());
        dchDecheterieFluxDB = new DchDecheterieFluxDB(getContext());
        dchDepotDB = new DchDepotDB(getContext());
        dchFluxDB = new DchFluxDB(getContext());
        dchTypeCarteDB = new DchTypeCarteDB(getContext());
        dchUniteDB = new DchUniteDB(getContext());
        decheterieDB = new DecheterieDB(getContext());
        habitatDB = new HabitatDB(getContext());
        iconDB = new IconDB(getContext());
        localDB = new LocalDB(getContext());
        menageDB = new MenageDB(getContext());
        modulesDB = new ModulesDB(getContext());
        usagerDB = new UsagerDB(getContext());
        usagerHabitatDB = new UsagerHabitatDB(getContext());
        usagerMenageDB = new UsagerMenageDB(getContext());
        typeHabitatDB = new TypeHabitatDB(getContext());
    }

    public void openAllDB(){
        dchAccountFluxSettingDB.open();
        dchAccountSettingDB.open();
        dchApportFluxDB.open();
        dchCarteActiveDB.open();
        dchCarteDB.open();
        dchCarteEtatRaisonDB.open();
        dchChoixDecompteTotalDB.open();
        dchComptePrepayeDB.open();
        dchDecheterieFluxDB.open();
        dchDepotDB.open();
        dchFluxDB.open();
        dchTypeCarteDB.open();
        dchUniteDB.open();
        decheterieDB.open();
        habitatDB.open();
        iconDB.open();
        localDB.open();
        menageDB.open();
        modulesDB.open();
        typeHabitatDB.open();
        usagerDB.open();
        usagerHabitatDB.open();
        usagerMenageDB.open();

    }

    public void closeAllDB(){
        dchAccountFluxSettingDB.close();
        dchAccountSettingDB.close();
        dchApportFluxDB.close();
        dchCarteActiveDB.close();
        dchCarteDB.close();
        dchCarteEtatRaisonDB.close();
        dchChoixDecompteTotalDB.close();
        dchComptePrepayeDB.close();
        dchDecheterieFluxDB.close();
        dchDepotDB.close();
        dchFluxDB.close();
        dchTypeCarteDB.close();
        dchUniteDB.close();
        decheterieDB.close();
        habitatDB.close();
        iconDB.close();
        localDB.close();
        menageDB.close();
        modulesDB.close();
        typeHabitatDB.close();
        usagerDB.close();
        usagerHabitatDB.close();
        usagerMenageDB.close();
    }

}