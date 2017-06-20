package fr.trackoe.decheterie.service;

import android.content.DialogInterface;
import android.os.SystemClock;
import android.view.View;

import java.util.Calendar;

/**
 * Created by Haocheng on 20/06/2017.
 */

public abstract class NoDoubleClickListener implements View.OnClickListener {
    public static final int MIN_CLICK_DELAY_TIME = 2000;
    private long lastClickTime;

    public abstract void onNoDoubleClick(View v);

    @Override
    public final void onClick(View v) {
        long currentTime = SystemClock.uptimeMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }


}
