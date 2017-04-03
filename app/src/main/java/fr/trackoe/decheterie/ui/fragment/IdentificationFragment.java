package fr.trackoe.decheterie.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SerialPortServiceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Queue;

import fr.trackoe.decheterie.R;
import fr.trackoe.decheterie.ui.activity.ContainerActivity;

/**
 * Created by Haocheng on 03/04/2017.
 */

public class IdentificationFragment extends Fragment {
    private ViewGroup identification_vg;
    ContainerActivity parentActivity;
    EditText editText_barcode;

    //scan attributs
    private SerialPortServiceManager   mSeriport;
    private read_thread mreadTh;
    private byte[] read_buffer = new byte[1024];
    private byte[] write_buffer= new byte[1024];
    private static Object rxlock = new Object();
    Queue<String> rx_queue = new LinkedList<String>();
    private int       sum_rx = 0;
    public static final int Message_display = 0x010;
    public static byte[]  part_serialPortNode = new String("/dev/" + "ttyUSB1" + "\0").getBytes();
    public static int     part_baud = 9600;
    public static int     part_data_size = 8;
    public static int     part_stop_bit = 1;
    private String[]  node_string;
    private Handler mHandler = null;
    private String head_queue = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("IdentificationFragment-->onCreateView()");
        identification_vg = (ViewGroup) inflater.inflate(R.layout.identification_fragment, container, false);

        editText_barcode = (EditText)  identification_vg.findViewById(R.id.editText_barcode);


        // Init Actionbar
        //initActionBar();

        // Init Views
        initViews();

        // Init des listeners
        initListeners(container);

        mSeriport = new SerialPortServiceManager(0);
        mSeriport.open(part_serialPortNode, part_baud, part_data_size, part_stop_bit);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                synchronized (rxlock) {
                    head_queue = rx_queue.poll();
                }
                if(head_queue != null) {
                    editText_barcode.setText(head_queue);
                } else {

                }


            }
        };

        mreadTh = new read_thread();
        mreadTh.start();

        return identification_vg;
    }

    @Override
    public void onResume() {
        System.out.println("IdentificationFragment-->onResume()");
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
        parentActivity = (ContainerActivity) getActivity();



        parentActivity.openDrawerWithDelay();
    }

    /*
    Init Listeners
     */
    public void initListeners(ViewGroup container) {



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


}