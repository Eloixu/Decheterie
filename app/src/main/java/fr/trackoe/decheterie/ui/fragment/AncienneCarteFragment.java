package fr.trackoe.decheterie.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import fr.trackoe.decheterie.R;
import fr.trackoe.decheterie.ui.activity.ContainerActivity;

/**
 * Created by Haocheng on 31/03/2017.
 */

public class AncienneCarteFragment extends Fragment {
    private ViewGroup ancienne_vg;
    ContainerActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("AncienneCarteFragment-->onCreateView()");
        ancienne_vg = (ViewGroup) inflater.inflate(R.layout.ancienne_carte_fragment, container, false);

        // Init Actionbar
        //initActionBar();

        // Init Views
        initViews();

        // Init des listeners
        initListeners(container);

        return ancienne_vg;
    }

    @Override
    public void onResume() {
        System.out.println("AncienneCarteFragment-->onResume()");
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
        parentActivity.showHamburgerButton();
        parentActivity.changeToolbarIcon();
        ((DrawerLocker) getActivity()).setDrawerEnabled(true);



        parentActivity.openDrawerWithDelay();
    }

    /*
    Init Listeners
     */
    public void initListeners(ViewGroup container) {
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



}
