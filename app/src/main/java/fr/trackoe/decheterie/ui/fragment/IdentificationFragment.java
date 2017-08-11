package fr.trackoe.decheterie.ui.fragment;

import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SerialPortServiceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Queue;

import fr.trackoe.decheterie.R;
import fr.trackoe.decheterie.configuration.Configuration;
import fr.trackoe.decheterie.database.DchCarteActiveDB;
import fr.trackoe.decheterie.database.DchCarteDB;
import fr.trackoe.decheterie.database.DchCarteEtatRaisonDB;
import fr.trackoe.decheterie.database.DchComptePrepayeDB;
import fr.trackoe.decheterie.database.HabitatDB;
import fr.trackoe.decheterie.database.LocalDB;
import fr.trackoe.decheterie.database.MenageDB;
import fr.trackoe.decheterie.database.UsagerDB;
import fr.trackoe.decheterie.database.UsagerHabitatDB;
import fr.trackoe.decheterie.database.UsagerMenageDB;
import fr.trackoe.decheterie.model.bean.global.Carte;
import fr.trackoe.decheterie.model.bean.global.CarteActive;
import fr.trackoe.decheterie.model.bean.global.CarteEtatRaison;
import fr.trackoe.decheterie.model.bean.global.ComptePrepaye;
import fr.trackoe.decheterie.model.bean.usager.Usager;
import fr.trackoe.decheterie.service.NoDoubleClickListener;
import fr.trackoe.decheterie.ui.activity.ContainerActivity;
import fr.trackoe.decheterie.ui.dialog.CustomDialogNormal;

/**
 * Created by Haocheng on 03/04/2017.
 */

public class IdentificationFragment extends Fragment {
    private ViewGroup identification_vg;
    private ContainerActivity parentActivity;
    private EditText editText_barcode;
    private ImageView scanner;
    private Button suivant;

    //scan attributs
    private SerialPortServiceManager mSeriport;
    private read_thread mreadTh;
    private byte[] read_buffer = new byte[1024];
    private byte[] write_buffer= new byte[1024];
    private static Object rxlock = new Object();
    Queue<String> rx_queue = new LinkedList<String>();
    private int       sum_rx = 0;
    public static final int Message_display = 0x010;
    public static byte[]  part_serialPortNode_ttyUSB0 = new String("/dev/" + "ttyUSB0" + "\0").getBytes();
    public static byte[]  part_serialPortNode_ttyUSB1 = new String("/dev/" + "ttyUSB1" + "\0").getBytes();
    public static byte[]  part_serialPortNode_ttyUSB2 = new String("/dev/" + "ttyUSB2" + "\0").getBytes();
    public static byte[]  part_serialPortNode_ttyUSB3 = new String("/dev/" + "ttyUSB3" + "\0").getBytes();
    public static int     part_baud = 9600;
    public static int     part_data_size = 8;
    public static int     part_stop_bit = 1;
    private String[]  node_string;
    private Handler mHandler = null;
    private String head_queue = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parentActivity = (ContainerActivity ) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("IdentificationFragment-->onCreateView()");
        identification_vg = (ViewGroup) inflater.inflate(R.layout.identification_fragment, container, false);
        editText_barcode = (EditText)  identification_vg.findViewById(R.id.identification_fragment_barcode_editText);
        scanner = (ImageView) identification_vg.findViewById(R.id.identification_fragment_scanner_imageView);
        suivant = (Button) identification_vg.findViewById(R.id.identification_fragment_suivant_button);


        // Init Actionbar
        //initActionBar();

        // Init Views
        initViews();

        // Init des listeners
        initListeners(container);

        addLayoutListener(identification_vg.findViewById(R.id.identification_fragment_global_linearLayout),suivant);

        try {
            mSeriport = new SerialPortServiceManager(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return identification_vg;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        try {
            initBarCodeScaner();
            initThread();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //clear the editText of the code bar each time enter in this fragment
        editText_barcode.setText("");

        super.onResume();
    }

    @Override
    public void onPause() {
        try {
            closeBarCodeReader();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onPause();
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
        parentActivity.setTitleToolbar(getResources().getString(R.string.title_identification_fragment));
        parentActivity.hideHamburgerButton();
        ((DrawerLocker) getActivity()).setDrawerEnabled(false);

        String lastNumCarte = Configuration.getLastNumCard();
        //if(lastNumCarte != null) editText_barcode.setText(lastNumCarte);
    }

    /*
    Init Listeners
     */
    public void initListeners(ViewGroup container) {
        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.initiateScan();

            }
        });


        suivant.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                DchCarteDB dchCarteDB = new DchCarteDB(getContext());
                dchCarteDB.open();
                DchCarteActiveDB dchCarteActiveDB = new DchCarteActiveDB(getContext());
                dchCarteActiveDB.open();
                DchComptePrepayeDB dchComptePrepayeDB = new DchComptePrepayeDB(getContext());
                dchComptePrepayeDB.open();
                UsagerDB usagerDB = new UsagerDB(getContext());
                usagerDB.open();
                UsagerHabitatDB usagerHabitatDB = new UsagerHabitatDB(getContext());
                usagerHabitatDB.open();
                HabitatDB habitatDB = new HabitatDB(getContext());
                habitatDB.open();
                UsagerMenageDB usagerMenageDB = new UsagerMenageDB(getContext());
                usagerMenageDB.open();
                MenageDB menageDB = new MenageDB(getContext());
                menageDB.open();
                LocalDB localDB = new LocalDB(getContext());
                localDB.open();
                DchCarteEtatRaisonDB dchCarteEtatRaisonDB = new DchCarteEtatRaisonDB(getContext());
                dchCarteEtatRaisonDB.open();

                if(editText_barcode.getText().toString().isEmpty()){

                }
                else {

                    //check if the card existes in the DB
                    Carte carte = dchCarteDB.getCarteByNumCarteAndAccountId(editText_barcode.getText().toString(), Configuration.getIdAccount());
                    if (carte == null) {
                        //pop-up2
                        CustomDialogNormal.Builder builder = new CustomDialogNormal.Builder(getContext());
                        builder.setMessage(R.string.pop_up_card_unregisted_message);
                        builder.setTitle(R.string.pop_up_card_unregisted_title);
                        builder.setPositiveButton(R.string.pop_up_card_unregisted_positive_button, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();


                            }
                        });

                        builder.setNegativeButton(R.string.pop_up_card_unregisted_negative_button, new android.content.DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        builder.create().show();
                    } else {
                        CarteActive carteActive = dchCarteActiveDB.getCarteActiveFromDchCarteId(carte.getId());
                        if (carteActive == null) {
                            //pop-up3
                            CustomDialogNormal.Builder builder = new CustomDialogNormal.Builder(getContext());
                            builder.setMessage(R.string.pop_up_card_not_associate_usager_message);
                            builder.setTitle(R.string.pop_up_card_not_associate_usager_title);
                            builder.setPositiveButton(R.string.pop_up_card_not_associate_usager_positive_button, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            });

                            builder.setNegativeButton(R.string.pop_up_card_not_associate_usager_negative_button, new android.content.DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            builder.create().show();

                        } else {
                            if (carteActive.isActive() == false) {
                                ComptePrepaye comptePrepaye = dchComptePrepayeDB.getComptePrepayeFromID(carteActive.getDchComptePrepayeId());
                                Usager usager = usagerDB.getUsagerFromID(comptePrepaye.getDchUsagerId());
                                CarteEtatRaison carteEtatRaison = dchCarteEtatRaisonDB.getCarteEtatRaisonFromID(carteActive.getDchCarteEtatRaisonId());
                                //pop-up1
                                CustomDialogNormal.Builder builder = new CustomDialogNormal.Builder(getContext());
                                builder.setMessage(R.string.pop_up_card_inactive_message1 + usager.getNom()
                                        + R.string.pop_up_card_inactive_message2 + carteEtatRaison.getRaison());
                                builder.setTitle(R.string.pop_up_card_inactive_title);
                                builder.setPositiveButton(null, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                    }
                                });

                                builder.setNegativeButton(R.string.pop_up_card_inactive_negative_button, new android.content.DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                builder.create().show();
                            } else {
                                //ok, turn to next page
                                /*Toast.makeText(getContext(), "Carte ok!",
                                        Toast.LENGTH_SHORT).show();*/
                                if (getActivity() != null && getActivity() instanceof ContainerActivity) {
                                    Configuration.setIsOuiClicked(false);
                                    DepotFragment depotFragment = DepotFragment.newInstance(editText_barcode.getText().toString(), true);
                                    ((ContainerActivity) getActivity()).changeMainFragment(depotFragment, true);
                                }

                                //save the num card
                                Configuration.saveLastNumCard(editText_barcode.getText().toString());



                            }

                        }
                    }
                }


                dchCarteDB.close();
                dchCarteActiveDB.close();
                dchComptePrepayeDB.close();
                usagerDB.close();
                usagerHabitatDB.close();
                habitatDB.close();
                usagerMenageDB.close();
                menageDB.close();
                localDB.close();
                dchCarteEtatRaisonDB.close();

            }
        });


    }

    public void initBarCodeScaner() {
        if(mSeriport.open(part_serialPortNode_ttyUSB0, part_baud, part_data_size, part_stop_bit) == -1){
            if(mSeriport.open(part_serialPortNode_ttyUSB1, part_baud, part_data_size, part_stop_bit) == -1){
                if(mSeriport.open(part_serialPortNode_ttyUSB2, part_baud, part_data_size, part_stop_bit) == -1){
                    mSeriport.open(part_serialPortNode_ttyUSB3, part_baud, part_data_size, part_stop_bit);
                }
            }
        }
    }

    public void initThread() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what)
                {
                    case Message_display:
                        int k = 0;
                        do {
                            synchronized (rxlock) {
                                head_queue = rx_queue.poll();
                            }
                            if(head_queue != null) {
                                editText_barcode.setText(head_queue);
                            } else {
                                break;
                            }
                        } while(k++ < 4);
                        break;
                    /*case ACTION_SCREEN_ON:
                        if(open_state_before == 1) {
                            node_open.performClick();
                        }
                        open_state_before = 0;
                        Log.d(TAG,"----------->Hand GET ACTION_SCREEN_ON!");
                        break;
                    case ACTION_SCREEN_OFF:
                        if(open_state_before == 1) {
                            node_close.performClick();
                        }
                        Log.d(TAG,"----------->Hand GET ACTION_SCREEN_OFF!");
                        break;*/
                    default:
                }
            }
        };
        mreadTh = new read_thread();
        mreadTh.start();
    }

    public class read_thread extends Thread {
        private int       ret_receive = 0;

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {

                if(mSeriport.isReady() > 0) {
                    ret_receive = mSeriport.wait_data();
                    if(ret_receive > 0){
                        ret_receive = mSeriport.read(read_buffer, 100, 10);// 10ms read
                        if (ret_receive > 0) {
                            String insert_data = null;
                            try {
                                insert_data = new String(read_buffer, 0, ret_receive, "ISO-8859-1");
                            } catch (UnsupportedEncodingException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            synchronized (rxlock) {
                                rx_queue.offer(insert_data);
                                sum_rx += ret_receive;
                            }
                            Message msg = new Message();
                            msg.what = Message_display;
                            mHandler.sendMessage(msg);
                        }
                    }
                }
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void closeBarCodeReader() {
        mSeriport.close();
    }

    /*
    * raise the components of barcode when then keyboard shows
    */
    public void addLayoutListener(final View main, final View scroll) {
        main.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                main.getWindowVisibleDisplayFrame(rect);
                int mainInvisibleHeight = main.getRootView().getHeight() - rect.bottom;
                if (mainInvisibleHeight > 100) {
                    int[] location = new int[2];
                    scroll.getLocationInWindow(location);
                    int srollHeight = (location[1] + scroll.getHeight()) - rect.bottom;
                    main.scrollTo(0, srollHeight);
                } else {
                    main.scrollTo(0, 0);
                }
            }
        });
    }

}
