package fr.trackoe.decheterie.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import fr.trackoe.decheterie.R;
import fr.trackoe.decheterie.ui.activity.ContainerActivity;

/**
 * Created by Haocheng on 04/04/2017.
 */

public class TestNFCFragment extends Fragment {
    private ViewGroup testNFC_vg;
    ContainerActivity parentActivity;

    //NFC
    //The array lists to hold our messages
    private ArrayList<String> messagesToSendArray = new ArrayList<>();
    private ArrayList<String> messagesReceivedArray = new ArrayList<>();

    private EditText txtBoxAddMessage;
    private TextView txtReceivedMessages;
    private TextView txtMessagesToSend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        testNFC_vg = (ViewGroup) inflater.inflate(R.layout.test_nfc_fragment, container, false);
        parentActivity = (ContainerActivity) getActivity();

        txtBoxAddMessage = (EditText) testNFC_vg.findViewById(R.id.txtBoxAddMessage);
        txtMessagesToSend = (TextView) testNFC_vg.findViewById(R.id.txtMessageToSend);
        txtReceivedMessages = (TextView) testNFC_vg.findViewById(R.id.txtMessagesReceived);
        Button btnAddMessage = (Button) testNFC_vg.findViewById(R.id.buttonAddMessage);

        btnAddMessage.setText("Add Message");
        updateTextViews();

        // Init Actionbar
        //initActionBar();

        // Init Views
        initViews();

        // Init des listeners
        initListeners(container);

        return testNFC_vg;
    }

    @Override
    public void onResume() {
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

    }

    /*
    Init Listeners
     */
    public void initListeners(ViewGroup container) {


    }

    public  void updateTextViews() {
        txtMessagesToSend.setText("Messages To Send:\n");
        //Populate Our list of messages we want to send
        if(messagesToSendArray.size() > 0) {
            for (int i = 0; i < messagesToSendArray.size(); i++) {
                txtMessagesToSend.append(messagesToSendArray.get(i));
                txtMessagesToSend.append("\n");
            }
        }

        txtReceivedMessages.setText("Messages Received:\n");
        //Populate our list of messages we have received
        if (messagesReceivedArray.size() > 0) {
            for (int i = 0; i < messagesReceivedArray.size(); i++) {
                txtReceivedMessages.append(messagesReceivedArray.get(i));
                txtReceivedMessages.append("\n");
            }
        }
    }



}

