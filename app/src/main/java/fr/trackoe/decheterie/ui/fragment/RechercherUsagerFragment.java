package fr.trackoe.decheterie.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
import fr.trackoe.decheterie.model.bean.usager.UsagerHabitat;
import fr.trackoe.decheterie.model.bean.usager.UsagerMenage;
import fr.trackoe.decheterie.ui.activity.ContainerActivity;
import fr.trackoe.decheterie.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Haocheng on 27/04/2017.
 */

public class RechercherUsagerFragment extends Fragment {
    private ViewGroup RU_vg;
    ContainerActivity parentActivity;

    //the list of all the usager in the DB
    private ArrayList<Usager> allUsagerList;
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
    private ArrayList<Usager> usagerListFinal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parentActivity = (ContainerActivity ) getActivity();
        /*UsagerDB usagerDB = new UsagerDB(getContext());
        usagerDB.open();
        allUsagerList = usagerDB.getAllUsager();
        usagerDB.close();*/
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("RechercherUsager-->onCreateView()");

        RU_vg = (ViewGroup) inflater.inflate(R.layout.rechercher_usager_fragment, container, false);
        editTextNom = (EditText) RU_vg.findViewById(R.id.rechercher_usager_fragment_filtre1_editText);
        spinner = (Spinner) RU_vg.findViewById(R.id.rechercher_usager_fragment_filtre2_spinner);
        editTextAdresse = (EditText) RU_vg.findViewById(R.id.rechercher_usager_fragment_filtre3_editText);
        btnRechercher = (Button) RU_vg.findViewById(R.id.rechercher_usager_fragment_rechercher_button);
        listView = (ListView) RU_vg.findViewById(R.id.rechercher_usager_fragment_usager_listView);

        // Init Actionbar
        //initActionBar();

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
        parentActivity.setTitleToolbar(getResources().getString(R.string.title_recherche_usager_fragment));
        ((DrawerLocker) getActivity()).setDrawerEnabled(false);
        parentActivity.hideHamburgerButton();

        //hide the button "rechercher"
        RU_vg.findViewById(R.id.rechercher_usager_fragment_rechercher_button).setVisibility(View.GONE);

        //set spinner data
        data_list = new ArrayList<String>();
            DchTypeCarteDB dchTypeCarteDB = new DchTypeCarteDB(getContext());
            dchTypeCarteDB.open();
        ArrayList<TypeCarte> listTypeCarte = dchTypeCarteDB.getAllTypeCarte();
            dchTypeCarteDB.close();
        for(TypeCarte typeCarte: listTypeCarte){
            data_list.add(typeCarte.getNom());
        }

        //adapter
        arr_adapter= new ArrayAdapter<String>(getContext(), R.layout.spinner_item, data_list);
        //set style
        arr_adapter.setDropDownViewResource(R.layout.spinner_item);
        //load the adapter
        spinner.setAdapter(arr_adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);

                RU_vg.findViewById(R.id.rechercher_usager_fragment_rechercher_button).callOnClick();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    /*
    Init Listeners
     */
    public void initListeners() {
        RU_vg.findViewById(R.id.rechercher_usager_fragment_rechercher_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity() != null && getActivity() instanceof  ContainerActivity) {
                    String nom = editTextNom.getText().toString();
                    String adresse = editTextAdresse.getText().toString();
                    String typeCarte = spinner.getSelectedItem().toString();

                    //usager list filtered by name "usagerList1"
                    ArrayList<Usager> usagerList1 = new ArrayList();
                    ArrayList<Integer> usagerIdList = new ArrayList();
                    String nameList[] = nom.split(" ");
                    int count = 1;
                        UsagerDB usagerDB = new UsagerDB(getContext());
                        usagerDB.open();
                    for(String name: nameList){
                        ArrayList<Usager> ul = usagerDB.getUsagerListByName(name);
                        ArrayList<Integer> usagerIdListCopy = new ArrayList();
                        for(Usager usager: ul){
                            usagerIdListCopy.add(usager.getId());
                        }
                        usagerIdListCopy = removeRepeatedElements(usagerIdListCopy);
                        if(count == 1){
                            usagerIdList = usagerIdListCopy;
                        }
                        else if(count > 1){
                            usagerIdList = getCommonElementsBetweenTwoList(usagerIdList,usagerIdListCopy);
                        }
                        count ++;
                    }
                    for(Integer usagerId: usagerIdList){
                        usagerList1.add(usagerDB.getUsagerFromID(usagerId));
                    }
                    //usager list filtered by typeCarte "usagerList2"
                    ArrayList<Usager> usagerList2 = new ArrayList();
                        DchCarteDB dchCarteDB = new DchCarteDB(getContext());
                        dchCarteDB.open();
                        DchTypeCarteDB dchTypeCarteDB = new DchTypeCarteDB(getContext());
                        dchTypeCarteDB.open();
                    ArrayList<Carte> listCarte = dchCarteDB.getCarteListByTypeCarteId(dchTypeCarteDB.getTypeCarteByName(typeCarte).getId());
                        dchCarteDB.close();
                        dchTypeCarteDB.close();
                    ArrayList<CarteActive> listCarteActive = new ArrayList();
                        DchCarteActiveDB dchCarteActiveDB = new DchCarteActiveDB(getContext());
                        dchCarteActiveDB.open();
                    for(Carte carte: listCarte){
                        if(dchCarteActiveDB.getCarteActiveFromDchCarteId(carte.getId()) != null){
                            listCarteActive.add(dchCarteActiveDB.getCarteActiveFromDchCarteId(carte.getId()));
                        }
                    }
                        dchCarteActiveDB.close();
                    ArrayList<ComptePrepaye> listComptePrepaye = new ArrayList();
                        DchComptePrepayeDB dchComptePrepayeDB = new DchComptePrepayeDB(getContext());
                        dchComptePrepayeDB.open();
                    for(CarteActive carteActive: listCarteActive ){
                        listComptePrepaye.add(dchComptePrepayeDB.getComptePrepayeFromID(carteActive.getDchComptePrepayeId()));
                    }
                        dchComptePrepayeDB.close();
                    for(ComptePrepaye comptePrepaye: listComptePrepaye){
                        usagerList2.add(usagerDB.getUsagerFromID(comptePrepaye.getDchUsagerId()));
                    }
                    //usager list filtered by adresse
                    ArrayList<Usager> usagerList3 = new ArrayList();
                    //ArrayList<Habitat> habitatList = habitatDB.getHabitatListByAdresse(adresse);
                    ArrayList<Habitat> habitatList = getHabitatListByAdresseTotal(adresse);
                    ArrayList<Habitat> habitatActiveList = new ArrayList();
                    for(Habitat habitat: habitatList){
                        if(habitat.isActif()){
                            habitatActiveList.add(habitat);
                        }
                    }
                        UsagerHabitatDB usagerHabitatDB = new UsagerHabitatDB(getContext());
                        usagerHabitatDB.open();
                        LocalDB localDB = new LocalDB(getContext());
                        localDB.open();
                        MenageDB menageDB = new MenageDB(getContext());
                        menageDB.open();
                        UsagerMenageDB usagerMenageDB = new UsagerMenageDB(getContext());
                        usagerMenageDB.open();
                    for(Habitat habitat: habitatActiveList){
                        //case 1: usager---usager_habitat---habitat
                        //since the habitat is active, if we can find the usager_habitat by habitatId, it will and must be the only usager_habitat in the table
                        if(usagerHabitatDB.getUsagerHabitatByHabitatActiveId(habitat.getIdHabitat()) != null){
                            Usager u = usagerDB.getUsagerFromID(usagerHabitatDB.getUsagerHabitatByHabitatActiveId(habitat.getIdHabitat()).getDchUsagerId());
                            usagerList3.add(u);
                        }
                        //case 2: usager---usager_menage---menage---local---habitat
                        //case 3: neither case 1 nor case 2

                        ArrayList<Local> localList = localDB.getLocalListByHabitatId(habitat.getIdHabitat());
                        ArrayList<Menage> menageList = new ArrayList();
                        for(Local local: localList){
                            Menage menage = menageDB.getMenageByLocalId(local.getIdLocal());
                            if(menage != null) menageList.add(menage);
                        }
                        ArrayList<Menage> menageActiveList = new ArrayList();
                        for(Menage menage: menageList){
                            if(menage.isActif()) menageActiveList.add(menage);
                        }
                        for(Menage menageActive: menageActiveList){
                            UsagerMenage usagerMenage = usagerMenageDB.getUsagerMenageByMenageActiveId(menageActive.getId());
                            if(usagerMenage != null) usagerList3.add(usagerDB.getUsagerFromID(usagerMenage.getDchUsagerId()));
                        }

                    }
                        usagerHabitatDB.close();
                        localDB.close();
                        menageDB.close();
                        usagerMenageDB.close();

                    //transfer the 3 list to arrayList<int>
                    ArrayList<Integer> usagerIdListA = new ArrayList();
                    ArrayList<Integer> usagerIdListB = new ArrayList();
                    ArrayList<Integer> usagerIdListC = new ArrayList();
                    for(Usager usager: usagerList1){
                        if(usager != null) usagerIdListA.add(usager.getId());
                    }
                    for(Usager usager: usagerList2){
                        if(usager != null) usagerIdListB.add(usager.getId());
                    }
                    for(Usager usager: usagerList3){
                        if(usager != null) usagerIdListC.add(usager.getId());
                    }
                    //remove the repeated elements in each list
                    usagerIdListA = removeRepeatedElements(usagerIdListA);
                    usagerIdListB = removeRepeatedElements(usagerIdListB);
                    usagerIdListC = removeRepeatedElements(usagerIdListC);
                    //get the common elements among the three list
                    ArrayList<Integer> usagerIdListABCCommon = new ArrayList(usagerIdListC);
                    ArrayList<Integer> usagerIdListABCommon = new ArrayList(usagerIdListB);
                    usagerIdListABCommon.retainAll(usagerIdListA);
                    usagerIdListABCCommon.retainAll(usagerIdListABCommon);
                    //get the final usagerListFinal
                    usagerListFinal = new ArrayList<>();
                    for(int usagerId: usagerIdListABCCommon){
                        usagerListFinal.add(usagerDB.getUsagerFromID(usagerId));
                    }
                        usagerDB.close();
                    if(usagerListFinal.size() == 0){
                        /*Toast.makeText(getContext(), "Aucun usager trouvé.",
                                Toast.LENGTH_SHORT).show();*/
                    }

                    //show listView
                    listView.setAdapter(new BaseAdapter() {
                        @Override
                        public int getCount() {
                            return usagerListFinal.size();
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
                                view = View.inflate(getContext(),R.layout.rechercher_usager_fragment_listview_item,null);
                            }
                            else{
                                view = convertView;
                            }

                            final Usager usager = usagerListFinal.get(position);
                            final TextView textViewUsagerName = (TextView)view.findViewById(R.id.rechercher_usager_fragment_listview_item_usager_name_textView);
                            final TextView textViewUsagerId = (TextView) view.findViewById(R.id.rechercher_usager_fragment_listview_item_usager_id_textView);
                            Habitat habitat = null;
                            String adresse = null;
                                UsagerHabitatDB usagerHabitatDB = new UsagerHabitatDB(getContext());
                                usagerHabitatDB.open();
                                UsagerMenageDB usagerMenageDB = new UsagerMenageDB(getContext());
                                usagerMenageDB.open();
                            if(usagerHabitatDB.getListUsagerHabitatByUsagerId(usager.getId()).size() != 0){
                                ArrayList<UsagerHabitat> usagerHabitatList = usagerHabitatDB.getListUsagerHabitatByUsagerId(usager.getId());
                                    HabitatDB habitatDB = new HabitatDB(getContext());
                                    habitatDB.open();
                                for(UsagerHabitat usagerHabitat: usagerHabitatList){
                                    Habitat h = habitatDB.getHabitatFromID(usagerHabitat.getHabitatId());
                                    if(h.isActif()){
                                        habitat = h;
                                        adresse = (habitat.getNumero() == null ? "" : habitat.getNumero() + " ") + (habitat.getComplement() == null ? "" : habitat.getComplement() + " ") + (habitat.getAdresse() == null ? "" : habitat.getAdresse() + ", ")
                                                + (habitat.getCp() == null ? "" : habitat.getCp() + ", ") + (habitat.getVille() == null ? "" : habitat.getVille());
                                    }
                                }
                                    habitatDB.close();
                            }
                            else if(usagerMenageDB.getListUsagerMenageByUsagerId(usager.getId()).size() != 0){
                                ArrayList<UsagerMenage> usagerMenageList = usagerMenageDB.getListUsagerMenageByUsagerId(usager.getId());
                                Menage menage = null;
                                    MenageDB menageDB = new MenageDB(getContext());
                                    menageDB.open();
                                for(UsagerMenage usagerMenage: usagerMenageList){
                                    Menage m = menageDB.getMenageById(usagerMenage.getMenageId());
                                    if(m.isActif()) menage = m;
                                }
                                    menageDB.close();
                                Local local = null;
                                    LocalDB localDB = new LocalDB(getContext());
                                    localDB.open();
                                if(menage != null) local = localDB.getLocalById(menage.getLocalId());
                                    localDB.close();
                                Habitat h = null;
                                    HabitatDB habitatDB = new HabitatDB(getContext());
                                    habitatDB.open();
                                if(local != null) h = habitatDB.getHabitatFromID(local.getHabitatId());
                                    habitatDB.close();
                                if(h != null){
                                    if(h.isActif()) habitat = h;
                                    adresse = (habitat.getNumero() == null ? "" : habitat.getNumero() + " ") + (habitat.getComplement() == null ? "" : habitat.getComplement() + " ") + (habitat.getAdresse() == null ? "" : habitat.getAdresse() + ", ")
                                            + (habitat.getCp() == null ? "" : habitat.getCp() + ", ") + (habitat.getVille() == null ? "" : habitat.getVille());
                                }

                            }
                                usagerHabitatDB.close();
                                usagerMenageDB.close();

                            textViewUsagerName.setText(Html.fromHtml("<font color='#000000'><big>" + usager.getNom() + "</big></font><br> " + (adresse==null? "" : "\n" + adresse)));
                            textViewUsagerId.setText(usager.getId() + "");
                            textViewUsagerName.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    String usagerName = usager.getNom();
                                    String typeCarte = spinner.getSelectedItem().toString();
                                        DchTypeCarteDB dchTypeCarteDB = new DchTypeCarteDB(getContext());
                                        dchTypeCarteDB.open();
                                    int typeCarteId = dchTypeCarteDB.getTypeCarteByName(typeCarte).getId();
                                        dchTypeCarteDB.close();
                                    //detect if the is_active of the usager's carteActives
                                    int accountId = -1;
                                        DchComptePrepayeDB dchComptePrepayeDB = new DchComptePrepayeDB(getContext());
                                        dchComptePrepayeDB.open();
                                    ComptePrepaye comptePrepaye = dchComptePrepayeDB.getComptePrepayeFromUsagerId(usager.getId());
                                        dchComptePrepayeDB.close();
                                        DchCarteActiveDB dchCarteActiveDB = new DchCarteActiveDB(getContext());
                                        dchCarteActiveDB.open();
                                    ArrayList<CarteActive> carteActiveList = dchCarteActiveDB.getCarteActiveListByComptePrepayeId(comptePrepaye.getId());
                                        dchCarteActiveDB.close();
                                        DchCarteDB dchCarteDB = new DchCarteDB(getContext());
                                        dchCarteDB.open();
                                    for(CarteActive ca: carteActiveList){
                                        if(ca.isActive()){
                                            Carte carte = dchCarteDB.getCarteFromID(ca.getDchCarteId());
                                            accountId = carte.getDchAccountId();
                                            break;
                                        }
                                    }
                                        dchCarteDB.close();
                                    if(accountId == -1){
                                        //pop-up
                                        /*Toast.makeText(getContext(), "Cet usager n'a aucune carte qui est active",
                                                Toast.LENGTH_SHORT).show();*/
                                    }
                                    else {
                                        if (getActivity() != null && getActivity() instanceof ContainerActivity) {
                                            DepotFragment depotFragment = DepotFragment.newInstance(usager.getId(), typeCarteId, true);
                                            ((ContainerActivity) getActivity()).changeMainFragment(depotFragment, true);
                                        }
                                    }
                                }
                            });


                            return view;
                        }
                    });
                }
            }
        });

        TextWatcher listener1 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                RU_vg.findViewById(R.id.rechercher_usager_fragment_rechercher_button).callOnClick();
                //close the keyboard when there is nothoing in the editText
                if(s.toString().isEmpty()){
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editTextNom.getWindowToken(), 0) ;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        editTextNom.addTextChangedListener(listener1);

        TextWatcher listener3 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                RU_vg.findViewById(R.id.rechercher_usager_fragment_rechercher_button).callOnClick();
                //close the keyboard when there is nothoing in the editText
                if(s.toString().isEmpty()){
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editTextAdresse.getWindowToken(), 0) ;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        editTextAdresse.addTextChangedListener(listener3);

    }
    //1,3,7,7,9,9,9 ---> 1,3,7,9
    public ArrayList<Integer> removeRepeatedElements(ArrayList<Integer> al){
        Set<Integer> hs = new HashSet<>();
        hs.addAll(al);
        al.clear();
        al.addAll(hs);

        return al;
    }

    //[1,2,3,4,5] [1,3,5,7,9] --->[1,3,5]
    public ArrayList<Integer> getCommonElementsBetweenTwoList(ArrayList<Integer> a1, ArrayList<Integer> a2){
        ArrayList<Integer> usagerIdListCommon = new ArrayList(a2);
        usagerIdListCommon.retainAll(a1);

        return usagerIdListCommon;
    }

    public ArrayList<Habitat> getHabitatListByAdresseTotal(String adresseTotal){
        ArrayList<Habitat> habitatList =new ArrayList<>();
        ArrayList<Integer> habitatIdList = new ArrayList<>();
        String adresses[] = adresseTotal.split(" ");
        Integer count = 1;
            HabitatDB habitatDB = new HabitatDB(getContext());
            habitatDB.open();
        for(String adresse: adresses){
            ArrayList<Integer> hl =new ArrayList<>();

            for(Habitat habitat: habitatDB.getHabitatListByAdresse(adresse)){
                hl.add(habitat.getIdHabitat());
            }
            for(Habitat habitat: habitatDB.getHabitatListByCP(adresse)){
                hl.add(habitat.getIdHabitat());
            }
            for(Habitat habitat: habitatDB.getHabitatListByVille(adresse)){
                hl.add(habitat.getIdHabitat());
            }
            for(Habitat habitat: habitatDB.getHabitatListByNumero(adresse)){
                hl.add(habitat.getIdHabitat());
            }
            for(Habitat habitat: habitatDB.getHabitatListByComplement(adresse)){
                hl.add(habitat.getIdHabitat());
            }

            hl = removeRepeatedElements(hl);
            if(count == 1){
                habitatIdList = hl;
            }
            else if(count > 1){
                habitatIdList = getCommonElementsBetweenTwoList(habitatIdList,hl);
            }
            count ++;
        }


        for(Integer habitatId: habitatIdList){
            habitatList.add(habitatDB.getHabitatFromID(habitatId));
        }

            habitatDB.close();

        return habitatList;
    }





}
