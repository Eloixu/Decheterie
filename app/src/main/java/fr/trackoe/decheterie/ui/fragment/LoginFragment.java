package fr.trackoe.decheterie.ui.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import fr.trackoe.decheterie.Logger;
import fr.trackoe.decheterie.R;
import fr.trackoe.decheterie.Utils;
import fr.trackoe.decheterie.configuration.Configuration;
import fr.trackoe.decheterie.database.UsersDB;
import fr.trackoe.decheterie.model.bean.global.User;
import fr.trackoe.decheterie.ui.activity.ContainerActivity;

public class LoginFragment extends Fragment {

    private static Logger logger = Logger.getLogger(ContainerActivity.class);
    private ViewGroup login_vg;
    private EditText identifiantEditText;
    private EditText mdpEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        login_vg = (ViewGroup) inflater.inflate(R.layout.login_fragment, container, false);
        // Init Actionbar
        initActionBar();

        // Init Views
        initViews();

        // Init des listeners
        initListeners();

        return login_vg;
    }

    /*
	 * Init Actionbar
	 */
    public void initActionBar() {
        if(getActivity() != null && getActivity() instanceof ContainerActivity) {
            ((ContainerActivity) getActivity()).showActionBarLogin();
        }
    }

    /*
    Init Views
     */
    public void initViews() {
        identifiantEditText = (EditText) login_vg.findViewById(R.id.login_edittxt_identifiant);
        mdpEditText = (EditText) login_vg.findViewById(R.id.login_edittxt_password);

        mdpEditText.setTypeface(Typeface.DEFAULT);
        mdpEditText.setTransformationMethod(new PasswordTransformationMethod());

        if(!Utils.isStringEmpty(Configuration.getNameUser())) {
            identifiantEditText.setText(Configuration.getNameUser());
            mdpEditText.requestFocus();
        } else if(!Configuration.getInstance(getContext()).isProd()) {
            identifiantEditText.setText(getString(R.string.login));
            mdpEditText.setText(getString(R.string.mdp));
        }
    }

    /*
    Init Listeners
     */
    public void initListeners() {
        // Identifiant
        login_vg.findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login() {
        User user;
        UsersDB usersDB = new UsersDB(getActivity());
        usersDB.read();
        user = usersDB.getUserByIdentifiant(identifiantEditText.getText().toString());
        usersDB.close();

        if(user == null) {
            showSimpleAlertDialog(getString(R.string.error_identifiant));
        } else {
            String mdpCompare = user.getPassword();
            String mdp = mdpEditText.getText().toString();
            String mdpSHA1 = encryptPassword(mdp);
            if(mdpCompare != null && mdpCompare.equals(mdpSHA1)){
                // Save IdUser and go to ModulesFragment
                Configuration.saveIdUser(Integer.parseInt(user.getIdUser()));
                Configuration.saveNameUser(identifiantEditText.getText().toString());
                if(getActivity() != null && getActivity() instanceof  ContainerActivity) {
                    ((ContainerActivity) getActivity()).changeMainFragment(new ModulesFragment(), true);
                }
            } else {
                showSimpleAlertDialog(getString(R.string.error_mdp));
            }

        }
    }

    private static String encryptPassword(String password)
    {
        String sha1 = "";
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        }
        catch(NoSuchAlgorithmException e) { }
        catch(UnsupportedEncodingException e) { }
        return sha1;
    }

    private static String byteToHex(final byte[] hash)
    {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    public void showSimpleAlertDialog(String mError){
        if (getActivity() != null && getActivity() instanceof ContainerActivity) {
            ((ContainerActivity) getActivity()).showSimpleAlertDialog(getActivity(), getString(R.string.error_title_information), mError);
        }
    }

}
