package fr.trackoe.decheterie.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fr.trackoe.decheterie.R;
import fr.trackoe.decheterie.configuration.Configuration;
import fr.trackoe.decheterie.database.DchAccountSettingDB;
import fr.trackoe.decheterie.database.DchApportFluxDB;
import fr.trackoe.decheterie.database.DchCarteActiveDB;
import fr.trackoe.decheterie.database.DchCarteDB;
import fr.trackoe.decheterie.database.DchComptePrepayeDB;
import fr.trackoe.decheterie.database.DchDepotDB;
import fr.trackoe.decheterie.database.DecheterieDB;
import fr.trackoe.decheterie.model.Datas;
import fr.trackoe.decheterie.model.bean.global.AccountSetting;
import fr.trackoe.decheterie.model.bean.global.ApportFlux;
import fr.trackoe.decheterie.model.bean.global.ComptePrepaye;
import fr.trackoe.decheterie.model.bean.global.ContenantBean;
import fr.trackoe.decheterie.model.bean.global.Depot;
import fr.trackoe.decheterie.service.callback.DataCallback;
import fr.trackoe.decheterie.ui.activity.ContainerActivity;

/**
 * Created by Haocheng.
 */


public class ApportProFragment extends Fragment {
    ContainerActivity parentActivity;
    private ViewGroup apf_vg;
    private ImageView imageSign;
    private PaintView mView;
    private Depot depot;
    private LinearLayout linearLayoutSignature;
    private long depotId;

    //the views
    private TextView apportProFragmentTopUpLine1TitleTextView;
    private TextView apportProFragmentTopUpLine2TitleTextView;
    private TextView apportProFragmentTopUpLine3TitleTextView;
    private TextView apportProFragmentBottomLeftUpLine1TitleTextView;
    private TextView apportProFragmentBottomLeftUpLine2TitleTextView;
    private TextView apportProFragmentBottomLeftUpLine3TitleTextView;
    private TextView apportProFragmentTopUpLine1ValueTextView;
    private TextView apportProFragmentTopUpLine2ValueTextView;
    private TextView apportProFragmentTopUpLine3ValueTextView;
    private TextView apportProFragmentBottomLeftUpLine1ValueTextView;
    private TextView apportProFragmentBottomLeftUpLine2ValueTextView;
    private TextView apportProFragmentBottomLeftUpLine3ValueTextView;
    private TextView apportProFragmentSignatureInformationTextView;


    //parameters in NavagationDrawer(totalDecompte,accountSettingId in DepotFragment) from DepotFragment
    private String nomInND;
    private boolean isUsagerMenageInND;
    private String adresseInND;
    private String numeroCarteInND;
    private float apportRestantInND;
    private String uniteApportRestantInND;
    private float totalDecompte;
    private int accountSettingId;
    private long comptePrepayeId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parentActivity = (ContainerActivity ) getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("ApportProFragment-->onCreateView()");
        apf_vg = (ViewGroup) inflater.inflate(R.layout.apport_pro_fragment, container, false);

        //FrameLayout frameLayout = (FrameLayout) accueil_vg.findViewById(R.id.frameLayout_bottom_right_down);
        linearLayoutSignature = (LinearLayout) apf_vg.findViewById(R.id.apport_pro_fragment_signature_linearLayout);


        // Init Actionbar
        //initActionBar();

        // Init Views
        initViews();

        // Init des listeners
        initListeners();


        return apf_vg;
    }

    @Override
    public void onResume() {
        System.out.println("ApportProFragment-->onResume()");
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
        parentActivity.setTitleToolbar(getResources().getString(R.string.title_apport_pro_fragment));
        ((DrawerLocker) getActivity()).setDrawerEnabled(false);
        parentActivity.hideHamburgerButton();

        DecheterieDB decheterieDB = new DecheterieDB(getContext());
        decheterieDB.open();

        apportProFragmentTopUpLine1TitleTextView        = (TextView) apf_vg.findViewById(R.id.apport_pro_fragment_top_up_line1_title_textView           );
        apportProFragmentTopUpLine2TitleTextView        = (TextView) apf_vg.findViewById(R.id.apport_pro_fragment_top_up_line2_title_textView           );
        apportProFragmentTopUpLine3TitleTextView        = (TextView) apf_vg.findViewById(R.id.apport_pro_fragment_top_up_line3_title_textView           );
        apportProFragmentBottomLeftUpLine1TitleTextView = (TextView) apf_vg.findViewById(R.id.apport_pro_fragment_bottom_left_up_line1_title_textView   );
        apportProFragmentBottomLeftUpLine2TitleTextView = (TextView) apf_vg.findViewById(R.id.apport_pro_fragment_bottom_left_up_line2_title_textView   );
        apportProFragmentBottomLeftUpLine3TitleTextView = (TextView) apf_vg.findViewById(R.id.apport_pro_fragment_bottom_left_up_line3_title_textView   );
        apportProFragmentTopUpLine1ValueTextView        = (TextView) apf_vg.findViewById(R.id.apport_pro_fragment_top_up_line1_value_textView           );
        apportProFragmentTopUpLine2ValueTextView        = (TextView) apf_vg.findViewById(R.id.apport_pro_fragment_top_up_line2_value_textView           );
        apportProFragmentTopUpLine3ValueTextView        = (TextView) apf_vg.findViewById(R.id.apport_pro_fragment_top_up_line3_value_textView           );
        apportProFragmentBottomLeftUpLine1ValueTextView = (TextView) apf_vg.findViewById(R.id.apport_pro_fragment_bottom_left_up_line1_value_textView   );
        apportProFragmentBottomLeftUpLine2ValueTextView = (TextView) apf_vg.findViewById(R.id.apport_pro_fragment_bottom_left_up_line2_value_textView   );
        apportProFragmentBottomLeftUpLine3ValueTextView = (TextView) apf_vg.findViewById(R.id.apport_pro_fragment_bottom_left_up_line3_value_textView   );
        apportProFragmentSignatureInformationTextView   = (TextView) apf_vg.findViewById(R.id.apport_pro_fragment_signature_information_textView        );

        //get the depotId sent From DepotFragment
        if (getArguments() != null) {
            DchDepotDB dchDepotDB = new DchDepotDB(getContext());
            dchDepotDB.open();

            depotId = getArguments().getLong("depotId");
            Toast.makeText(getContext(), "depotId: " + depotId, Toast.LENGTH_SHORT).show();
            depot =  dchDepotDB.getDepotByIdentifiant(depotId);

            dchDepotDB.close();
        }

        //get the nomInND, isUsagerMenageInND, adresseInND, numeroCarteInND, apportRestantInND, uniteApportRestantInND, totalDecompte, accountSettingId sent from DepotFragment
        if (getArguments() != null) {
            nomInND                 = getArguments().getString  (   "nomInND"               );
            isUsagerMenageInND      = getArguments().getBoolean (   "isUsagerMenageInND"    );
            adresseInND             = getArguments().getString  (   "adresseInND"           );
            numeroCarteInND         = getArguments().getString  (   "numeroCarteInND"       );
            apportRestantInND       = getArguments().getFloat   (   "apportRestantInND"     );
            uniteApportRestantInND  = getArguments().getString  (   "uniteApportRestantInND");
            totalDecompte           = getArguments().getFloat   (   "totalDecompte"         );
            accountSettingId        = getArguments().getInt     (   "accountSettingId"      );
            comptePrepayeId         = getArguments().getLong    (   "comptePrepayeId"       );
        }

        //detect if the signature picture has already existed
        if (depot.getSignature() == null){
            mView = new PaintView(getContext());
            linearLayoutSignature.addView(mView);
            mView.requestFocus();
        }
        else{
            /*byte[] byteSignature = depot.getSignature();
            Bitmap bmpSignature = BitmapFactory.decodeByteArray(byteSignature, 0, byteSignature.length);
            Bitmap mutableBmpSignature = bmpSignature.copy(Bitmap.Config.ARGB_8888, true);
            mView = new PaintView(getContext(), mutableBmpSignature);
            linearLayoutSignature.addView(mView);
            mView.requestFocus();*/
        }

        //set values
        if(isUsagerMenageInND) {
            apportProFragmentTopUpLine1TitleTextView    .setText(   R.string.apport_pro_fragment_top_up_line1_title_textView_text1      );
        }
        else{
            apportProFragmentTopUpLine1TitleTextView    .setText(   R.string.apport_pro_fragment_top_up_line1_title_textView_text2      );
        }
        apportProFragmentTopUpLine1ValueTextView        .setText(   nomInND                                                             );
        apportProFragmentTopUpLine2ValueTextView        .setText(   adresseInND                                                         );
        apportProFragmentTopUpLine3ValueTextView        .setText(   numeroCarteInND                                                     );
        apportProFragmentBottomLeftUpLine1ValueTextView .setText(   apportRestantInND + " " + uniteApportRestantInND                    );
        apportProFragmentBottomLeftUpLine2ValueTextView .setText(   totalDecompte + " " + uniteApportRestantInND                        );
        apportProFragmentBottomLeftUpLine3ValueTextView .setText(   (apportRestantInND - totalDecompte) + " " + uniteApportRestantInND  );
        apportProFragmentSignatureInformationTextView   .setText(   getResources().getText(R.string.depot_fragment_signature_information_text_1) + decheterieDB.getDecheterieByIdentifiant(depot.getDecheterieId()).getNom() + getResources().getText(R.string.depot_fragment_signature_information_text_2) + getCurrentDate() + getResources().getText(R.string.depot_fragment_signature_information_text_3) );

        decheterieDB.close();

    }

    /*
    Init Listeners
     */
    public void initListeners() {
        ImageView imageViewClear = (ImageView) apf_vg.findViewById(R.id.apport_pro_fragment_clear_imageView);
        imageViewClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mView.clear();
            }
        });

        Button btnValider = (Button) apf_vg.findViewById(R.id.apport_pro_fragment_valider_button);
        btnValider.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //save the signature into DB
                DchDepotDB dchDepotDB = new DchDepotDB(getContext());
                dchDepotDB.open();
                DchAccountSettingDB dchAccountSettingDB = new DchAccountSettingDB(getContext());
                dchAccountSettingDB.open();
                DchApportFluxDB dchApportFluxDB = new DchApportFluxDB(getContext());
                dchApportFluxDB.open();

                Bitmap imageBitmap = mView.getCachebBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                depot.setSignature(baos.toByteArray());
                depot.setDateHeure(getDateHeure());

                //update the table "depot" and change the row "statut" to statut_termine
                depot.setStatut(getResources().getInteger(R.integer.statut_termine));


                depot.setDateHeure(getDateHeure());

                dchDepotDB.updateDepot(depot);


                sendDepot(depot, dchAccountSettingDB.getAccountSettingFromID(accountSettingId), dchApportFluxDB.getListeApportFluxByDepotId(depot.getId()));
                recaculateComptePrepaye();

                dchDepotDB.close();
                dchAccountSettingDB.close();
                dchApportFluxDB.close();

                //turn to page Accueil
                if(getActivity() != null && getActivity() instanceof  ContainerActivity) {
                    ((ContainerActivity) getActivity()).changeMainFragment(new AccueilFragment(), false);
                }
            }
        });

    }

    public PaintView getMView(){
        return mView;
    }

    class PaintView extends View implements Serializable {
        private Paint paint;
        private Canvas cacheCanvas;
        private Bitmap cachebBitmap;
        private Path path;

        public Bitmap getCachebBitmap() {
            return cachebBitmap;
        }

        public void setCacheBitmap(Bitmap bitmap){
            this.cachebBitmap = bitmap;
        }

        public PaintView(Context context) {
            super(context);
            init();
        }

        public PaintView(Context context, Bitmap cachebBitmap) {
            super(context);
            this.cachebBitmap = cachebBitmap;
            init();
        }

        private void init() {
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStrokeWidth(3);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
            path = new Path();
            cachebBitmap = Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888);
            cacheCanvas = new Canvas(cachebBitmap);
            cacheCanvas.drawColor(Color.WHITE);
        }

        public void clear() {
            if (cacheCanvas != null) {
                paint.setColor(Color.WHITE);
                cacheCanvas.drawPaint(paint);
                paint.setColor(Color.BLACK);
                cacheCanvas.drawColor(Color.WHITE);
                invalidate();
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // canvas.drawColor(BRUSH_COLOR);
            canvas.drawBitmap(cachebBitmap, 0, 0, null);
            canvas.drawPath(path, paint);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {

            int curW = cachebBitmap != null ? cachebBitmap.getWidth() : 0;
            int curH = cachebBitmap != null ? cachebBitmap.getHeight() : 0;
            if (curW >= w && curH >= h) {
                return;
            }

            if (curW < w)
                curW = w;
            if (curH < h)
                curH = h;

            Bitmap newBitmap = Bitmap.createBitmap(curW, curH, Bitmap.Config.ARGB_8888);
            Canvas newCanvas = new Canvas();
            newCanvas.setBitmap(newBitmap);
            if (cachebBitmap != null) {
                newCanvas.drawBitmap(cachebBitmap, 0, 0, null);
            }
            cachebBitmap = newBitmap;
            cacheCanvas = newCanvas;
        }

        private float cur_x, cur_y;

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    cur_x = x;
                    cur_y = y;
                    path.moveTo(cur_x, cur_y);
                    break;
                }
                case MotionEvent.ACTION_MOVE: {
                    path.quadTo(cur_x, cur_y, x, y);
                    cur_x = x;
                    cur_y = y;
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    cacheCanvas.drawPath(path, paint);
                    path.reset();
                    break;
                }
            }
            invalidate();
            return true;
        }
    }


    public static ApportProFragment newInstance(long depotId, String nomInND, boolean isUsagerMenageInND, String adresseInND, String numeroCarteInND, float apportRestantInND, String uniteApportRestantInND, float totalDecompte, int accountSettingId, long comptePrepayeId) {
        ApportProFragment apportProFragment = new ApportProFragment();
        Bundle args = new Bundle();
        args.putLong    (   "depotId",                  depotId                 );
        args.putString  (   "nomInND",                  nomInND                 );
        args.putBoolean (   "isUsagerMenageInND",       isUsagerMenageInND      );
        args.putString  (   "adresseInND",              adresseInND             );
        args.putString  (   "numeroCarteInND",          numeroCarteInND         );
        args.putFloat   (   "apportRestantInND",        apportRestantInND       );
        args.putString  (   "uniteApportRestantInND",   uniteApportRestantInND  );
        args.putFloat   (   "totalDecompte",            totalDecompte           );
        args.putInt     (   "accountSettingId",         accountSettingId        );
        args.putLong    (   "comptePrepayeId",          comptePrepayeId         );

        apportProFragment.setArguments(args);
        return apportProFragment;
    }

    public static ApportProFragment newInstance(long depotId, int usagerIdFromRechercherUsagerFragment, int typeCarteIdFromRechercherUsagerFragment, boolean isComeFromRechercherUsagerFragment, String nomInND, boolean isUsagerMenageInND, String adresseInND, float apportRestantInND, String uniteApportRestantInND, float totalDecompte, int accountSettingId, long comptePrepayeId) {
        ApportProFragment apportProFragment = new ApportProFragment();
        Bundle args = new Bundle();
        args.putLong    (   "depotId",                                  depotId                                 );
        args.putInt     (   "usagerIdFromRechercherUsagerFragment",     usagerIdFromRechercherUsagerFragment    );
        args.putInt     (   "typeCarteIdFromRechercherUsagerFragment",  typeCarteIdFromRechercherUsagerFragment );
        args.putBoolean (   "isComeFromRechercherUsagerFragment",       isComeFromRechercherUsagerFragment      );

        args.putString  (   "nomInND",                  nomInND                 );
        args.putBoolean (   "isUsagerMenageInND",       isUsagerMenageInND      );
        args.putString  (   "adresseInND",              adresseInND             );
        //args.putString  (   "numeroCarteInND",          numeroCarteInND         );
        args.putFloat   (   "apportRestantInND",        apportRestantInND       );
        args.putString  (   "uniteApportRestantInND",   uniteApportRestantInND  );
        args.putFloat   (   "totalDecompte",            totalDecompte           );
        args.putInt     (   "accountSettingId",         accountSettingId        );
        args.putLong    (   "comptePrepayeId",          comptePrepayeId         );
        apportProFragment.setArguments(args);
        return apportProFragment;
    }

    public Drawable changeToDrawable(Bitmap bp)
     {
         Bitmap bm=bp;
         BitmapDrawable bd= new BitmapDrawable(getResources(), bm);
         return bd;
        }

    public String getDateHeure(){
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat(getString(R.string.db_date_format));
        String dateHeure = df.format(d);

        return dateHeure;
    }

    public String getCurrentDate(){
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat(getString(R.string.signature_date_format));
        String date = df.format(d);

        return date;
    }

    public void sendDepot(Depot d, AccountSetting a, ArrayList<ApportFlux> listAF){
        try {
            //send Depot(without signature) to server
            parentActivity.sendDepot(d,a,listAF);

            //send the signature of depot to server
            //File f = new File(Environment.getExternalStorageDirectory() + "/Pictures/Signature", "signature" + d.getDateHeure()+".PNG");
            File f = new File(Environment.getExternalStorageDirectory(), "signature" + d.getDateHeure()+".PNG");
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(depot.getSignature());
            fos.flush();
            fos.close();
            Datas.uploadFile(getContext(),f);

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void recaculateComptePrepaye(){
        DchAccountSettingDB dchAccountSettingDB = new DchAccountSettingDB(getContext());
        dchAccountSettingDB.open();
        DchComptePrepayeDB dchComptePrepayeDB = new DchComptePrepayeDB(getContext());
        dchComptePrepayeDB.open();

        AccountSetting accountSetting = dchAccountSettingDB.getAccountSettingFromID(accountSettingId);
        ComptePrepaye comptePrepaye   = dchComptePrepayeDB.getComptePrepayeFromID(comptePrepayeId);

        if(accountSetting.isDecompteDepot()){
            comptePrepaye.setNbDepotRestant(comptePrepaye.getNbDepotRestant() - 1);
        }
        if(accountSetting.isDecompteUDD()){
            comptePrepaye.setQtyPoint(comptePrepaye.getQtyPoint() - depot.getQtyTotalUDD()/accountSetting.getCoutUDDPrPoint());
        }
        dchComptePrepayeDB.updateComptePrepaye(comptePrepaye);

        dchAccountSettingDB.close();
        dchComptePrepayeDB.close();
    }
}