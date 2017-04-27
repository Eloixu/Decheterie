package fr.trackoe.decheterie.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
import fr.trackoe.decheterie.model.bean.global.Carte;
import fr.trackoe.decheterie.model.bean.global.CarteActive;
import fr.trackoe.decheterie.model.bean.global.ComptePrepaye;
import fr.trackoe.decheterie.model.bean.global.TypeCarte;
import fr.trackoe.decheterie.model.bean.usager.Habitat;
import fr.trackoe.decheterie.model.bean.usager.Local;
import fr.trackoe.decheterie.model.bean.usager.Menage;
import fr.trackoe.decheterie.model.bean.usager.Usager;
import fr.trackoe.decheterie.model.bean.usager.UsagerMenage;
import fr.trackoe.decheterie.ui.activity.ContainerActivity;
import fr.trackoe.decheterie.R;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by Haocheng on 27/04/2017.
 */

public class RechercherUsagerFragment extends Fragment {
    private ViewGroup RU_vg;
    ContainerActivity parentActivity;
    //spinner
    private Spinner spinner;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;
    //editText
    private EditText editTextNom;
    private EditText editTextAdresse;
    //button
    private Button btnRechercher;
    //listView
    private ListView listView;
    private ArrayList<Usager> usagerList;
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
    private UsagerDB usagerDB;
    private UsagerHabitatDB usagerHabitatDB;
    private UsagerMenageDB usagerMenageDB;
    private TypeHabitatDB typeHabitatDB;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("RechercherUsager-->onCreateView()");
        initAllDB();
        openAllDB();

        RU_vg = (ViewGroup) inflater.inflate(R.layout.rechercher_usager_fragment, container, false);
        spinner = (Spinner) RU_vg.findViewById(R.id.spinner_ru_filtre2);
        editTextNom = (EditText) RU_vg.findViewById(R.id.edittext_ru_filtre1);
        editTextAdresse = (EditText) RU_vg.findViewById(R.id.edittext_ru_filtre3);
        btnRechercher = (Button) RU_vg.findViewById(R.id.btn_ru_rechercher);
        listView = (ListView) RU_vg.findViewById(R.id.listView_ru_usager);

        // Init Actionbar
        //initActionBar();

        // Init Views
        initViews();

        // Init des listeners
        initListeners();

        closeAllDB();

        return RU_vg;
    }

    @Override
    public void onResume() {
        System.out.println("RechercherUsagerFragment-->onResume()");
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

        //set spinner data
        data_list = new ArrayList<String>();
        ArrayList<TypeCarte> listTypeCarte = dchTypeCarteDB.getAllTypeCarte();
        for(TypeCarte typeCarte: listTypeCarte){
            data_list.add(typeCarte.getNom());
        }

        //适配器 adapter
        arr_adapter= new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data_list);
        //设置样式 set style
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器 load the adapter
        spinner.setAdapter(arr_adapter);


    }

    /*
    Init Listeners
     */
    public void initListeners() {
        RU_vg.findViewById(R.id.btn_ru_rechercher).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAllDB();
                if(getActivity() != null && getActivity() instanceof  ContainerActivity) {
                    String nom = editTextNom.getText().toString();
                    String adresse = editTextAdresse.getText().toString();
                    String typeCarte = spinner.getSelectedItem().toString();

                    //usager list filtered by name
                    ArrayList<Usager> usagerList1 = usagerDB.getUsagerListByName(nom);
                    //usager list filtered by typeCarte
                    ArrayList<Usager> usagerList2 = new ArrayList();
                    ArrayList<Carte> listCarte = dchCarteDB.getCarteListByTypeCarteId(dchTypeCarteDB.getTypeCarteByName(typeCarte).getId());
                    ArrayList<CarteActive> listCarteActive = new ArrayList();
                    for(Carte carte: listCarte){
                        if(dchCarteActiveDB.getCarteActiveFromDchCarteId(carte.getId()) != null){
                            listCarteActive.add(dchCarteActiveDB.getCarteActiveFromDchCarteId(carte.getId()));
                        }
                    }
                    ArrayList<ComptePrepaye> listComptePrepaye = new ArrayList();
                    for(CarteActive carteActive: listCarteActive ){
                        listComptePrepaye.add(dchComptePrepayeDB.getComptePrepayeFromID(carteActive.getDchComptePrepayeId()));
                    }
                    for(ComptePrepaye comptePrepaye: listComptePrepaye){
                        usagerList2.add(usagerDB.getUsagerFromID(comptePrepaye.getDchUsagerId()));
                    }
                    //usager list filtered by adresse
                    ArrayList<Usager> usagerList3 = new ArrayList();
                    ArrayList<Habitat> habitatList = habitatDB.getHabitatListByAdresse(adresse);
                    ArrayList<Habitat> habitatActiveList = new ArrayList<Habitat>();
                    for(Habitat habitat: habitatList){
                        if(habitat.isActif()){
                            habitatActiveList.add(habitat);
                        }
                    }
                    for(Habitat habitat: habitatActiveList){
                        //case 1: usager---usager_habitat---habitat
                        //since the habitat is active, if we can find the usager_habitat by habitatId, it will and must be the only usager_habitat in the table
                        if(usagerHabitatDB.getUsagerHabitatByHabitatActiveId(habitat.getIdHabitat()) != null){
                            Usager u = usagerDB.getUsagerFromID(usagerHabitatDB.getUsagerHabitatByHabitatActiveId(habitat.getIdHabitat()).getDchUsagerId());
                            usagerList3.add(u);
                        }
                        //case 2: usager---usager_menage---menage---local---habitat
                        //case 3: neither case 1 nor case 2
                        else{
                            ArrayList<Local> localList = localDB.getLocalListByHabitatId(habitat.getIdHabitat());
                            ArrayList<Menage> menageList = new ArrayList();
                            for(Local local: localList){
                                Menage menage = menageDB.getMenageByLocalId(local.getIdLocal());
                                if(menage != null) menageList.add(menage);
                            }
                            ArrayList<Menage> menageActiveList = new ArrayList<Menage>();
                            for(Menage menage: menageList){
                                if(menage.isActif()) menageActiveList.add(menage);
                            }
                            for(Menage menageActive: menageActiveList){
                                UsagerMenage usagerMenage = usagerMenageDB.getUsagerMenageByMenageActiveId(menageActive.getId());
                                if(usagerMenage != null) usagerList3.add(usagerDB.getUsagerFromID(usagerMenage.getDchUsagerId()));
                            }
                        }
                    }

                    //combine usagerList 1 2 3 to usagerListTotal
                    ArrayList<Usager> usagerListTotal = new ArrayList();
                    usagerListTotal.addAll(usagerList1);
                    usagerListTotal.addAll(usagerList2);
                    usagerListTotal.addAll(usagerList3);


                    /*//show listView
                    usagerList = new ArrayList<>();
                    usagerList = usagerList1;
                    listView.setAdapter(new BaseAdapter() {
                        @Override
                        public int getCount() {
                            return usagerList.size();
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
                                view = View.inflate(getContext(),R.layout.rechercher_usager_listview_item,null);
                            }
                            else{
                                view = convertView;
                            }

                            final Usager usager = usagerList.get(position);
                            final TextView textViewUsagerName = (TextView)view.findViewById(R.id.textView_usager_name);
                            textViewUsagerName.setText(usager.getNom());
                            textViewUsagerName.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {

                                    String usagerName = usager.getNom();
                                    if(getActivity() != null && getActivity() instanceof  ContainerActivity) {
                                        //((ContainerActivity) getActivity()).changeMainFragment(new AccueilFragment(), true);
                                    }
                                }
                            });

                            return view;
                        }
                    });*/
                }
            closeAllDB();
            }
        });

    }

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
