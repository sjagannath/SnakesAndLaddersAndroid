package com.eleventures.snakesandladders.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class AutoDismissDialog extends ProgressDialog implements DialogInterface.OnShowListener, DialogInterface.OnDismissListener {

    private final int mTimerDuration, DEFAULT_TIMER = 2000;
    private Timer mTimer;

    public AutoDismissDialog(Context context, int timer, @Nullable DialogInterface.OnShowListener showListener,
                             @Nullable DialogInterface.OnDismissListener dismissListener) {
        super(context);
        mTimerDuration = timer <= 0? DEFAULT_TIMER: timer*1000;
        setOnShowListener(showListener == null? this: showListener);
        setOnDismissListener(dismissListener == null? this: dismissListener);
    }


    @Override
    public void onShow(DialogInterface dialog) {
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(isShowing()) dismiss();
            }
        }, mTimerDuration);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        mTimer.cancel();
        mTimer = null;
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        setCancelable(false);
        return super.onCreateOptionsMenu(menu);
    }
}
