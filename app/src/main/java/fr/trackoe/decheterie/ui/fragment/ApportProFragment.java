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
import java.io.Serializable;

import fr.trackoe.decheterie.R;
import fr.trackoe.decheterie.configuration.Configuration;
import fr.trackoe.decheterie.database.DchDepotDB;
import fr.trackoe.decheterie.database.DecheterieDB;
import fr.trackoe.decheterie.model.bean.global.ApportFlux;
import fr.trackoe.decheterie.model.bean.global.Depot;
import fr.trackoe.decheterie.ui.activity.ContainerActivity;

public class ApportProFragment extends Fragment {
    private ViewGroup apf_vg;
    ContainerActivity parentActivity;
    private ImageView imageSign;
    private PaintView mView;
    private long depotId;
    private Depot depot;
    private LinearLayout linearLayoutSignature;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("ApportProFragment-->onCreateView()");
        apf_vg = (ViewGroup) inflater.inflate(R.layout.apport_pro_fragment, container, false);


        //FrameLayout frameLayout = (FrameLayout) accueil_vg.findViewById(R.id.frameLayout_bottom_right_down);
        linearLayoutSignature = (LinearLayout) apf_vg.findViewById(R.id.linearLayout_signature);


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
        ((DrawerLocker) getActivity()).setDrawerEnabled(false);
        parentActivity = (ContainerActivity ) getActivity();
        parentActivity.hideHamburgerButton();

        //get the depotId sent From DepotFragment
        if (getArguments() != null) {
            DchDepotDB dchDepotDB = new DchDepotDB(getContext());
            dchDepotDB.open();

            depotId = getArguments().getLong("depotId");
            //depotId = 1183153170412171602L;
            Toast.makeText(getContext(), "depotId: " + depotId,
                    Toast.LENGTH_SHORT).show();
            depot =  dchDepotDB.getDepotByIdentifiant(depotId);

            dchDepotDB.close();
        }

        //detect if the signature picture has already existed
        if (depot.getSignature() == null){
            mView = new PaintView(getContext());
            linearLayoutSignature.addView(mView);
            mView.requestFocus();
        }
        else{

        }

    }

    /*
    Init Listeners
     */
    public void initListeners() {
        ImageView imageViewClear = (ImageView) apf_vg.findViewById(R.id.imageView_clear);
        imageViewClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mView.clear();
            }
        });

        Button btnValider = (Button) apf_vg.findViewById(R.id.btn_valider);
        btnValider.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //save the signature into DB
                DchDepotDB dchDepotDB = new DchDepotDB(getContext());
                dchDepotDB.open();

                Bitmap imageBitmap = mView.getCachebBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                depot.setSignature(baos.toByteArray());
                dchDepotDB.updateDepot(depot);

                //turn to page Accueil
                if(getActivity() != null && getActivity() instanceof  ContainerActivity) {
                    ((ContainerActivity) getActivity()).changeMainFragment(new AccueilFragment(), true);
                }

                dchDepotDB.close();

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

       /* public void setCacheBitmap(Bitmap bitmap){
            this.cachebBitmap = bitmap;
        }*/

        public PaintView(Context context) {
            super(context);
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

    public static ApportProFragment newInstance(long depotId) {
        ApportProFragment apportProFragment = new ApportProFragment();
        Bundle args = new Bundle();
        args.putLong("depotId", depotId);
        apportProFragment.setArguments(args);
        return apportProFragment;
    }

    public Drawable changeToDrawable(Bitmap bp)
     {
         Bitmap bm=bp;
         BitmapDrawable bd= new BitmapDrawable(getResources(), bm);
         return bd;
        }

    public long getDepotId(){
        return depotId;
    }




}